package com.demka.demkaserver.controllers;

import com.demka.demkaserver.entities.converters.UserSearchConverter;
import com.demka.demkaserver.entities.database.RoomDBEntity;
import com.demka.demkaserver.entities.database.UserDBEntity;
import com.demka.demkaserver.entities.response.UserSearchResponseEntity;
import com.demka.demkaserver.services.MessageService;
import com.demka.demkaserver.services.RoomService;
import com.demka.demkaserver.services.UserService;
import com.demka.demkaserver.utils.GenResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;
    private final UserService userService;
    private final MessageService messageService;

    @Autowired
    public RoomController(RoomService roomService, UserService userService, MessageService messageService) {
        this.roomService = roomService;
        this.userService = userService;
        this.messageService = messageService;
    }

    /**
     * Создание комнаты для общения пользователей
     *
     * @param data - данные в JSON. Поля:
     *             key - ключ пользователя
     *             roomName - название комнаты
     *             users - строка с id пользователей, состоящих в беседе
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createRoom(@RequestBody Map<String, String> data) {

        String key = data.get("key");
        String roomName = data.get("roomName");
        String usersString = data.get("users");

        //Проверка переданных полей
        if ((key == null) || (roomName == null) || (usersString == null)) {
            return new ResponseEntity<>(GenResponseUtil.ResponseError("Не все значения были переданы"), HttpStatus.BAD_REQUEST);
        }

        List<String> users = Arrays.asList(usersString.split(","));

        //Получаем id создателя через key + проверка ключа
        Optional<UserDBEntity> creatorUserOptional = userService.findByKey(key);
        //Если вдруг пользователь с key не найден
        if (creatorUserOptional.isEmpty()) {
            return new ResponseEntity<>(GenResponseUtil.ResponseError("Не удалось авторизоваться по указанному ключу"), HttpStatus.FORBIDDEN);
        }

        //Проверка на существование пользователей
        for (String userId : users) {
            if (userService.find(userId).isEmpty()) {
                return new ResponseEntity<>(GenResponseUtil.ResponseError("Пользователя с id " + userId + " не существует"), HttpStatus.BAD_REQUEST);
            }
        }

        UserDBEntity creatorUser = creatorUserOptional.get();
        String creatorId = creatorUser.getId();
        RoomDBEntity newRoom = roomService.create(creatorId, roomName, users);

        //Формируем сообщение, что комната создана
        String messageText = "*Комната была создана*";
        messageService.create(creatorId, creatorUser.getName(), messageText, newRoom.getId());

        return new ResponseEntity<>(GenResponseUtil.ResponseOK(newRoom), HttpStatus.OK);
    }

    /**
     * Получение инфорации о комнате
     *
     * @param key    - ключ пользователя
     * @param roomId - id комнаты
     * @return
     */
    @GetMapping(value = "/get")
    public ResponseEntity<Map<String, Object>> getRoomInfo(@RequestParam String key, @RequestParam String roomId) {


        //Проверка ключа
        if (!userService.checkUserKey(key)) {
            return new ResponseEntity<>(GenResponseUtil.ResponseError("Не удалось авторизоваться по указанному ключу"), HttpStatus.FORBIDDEN);
        }

        Optional<RoomDBEntity> roomResult = roomService.find(roomId);
        //Если комнаты с таким id не существует
        if (roomResult.isEmpty()) {
            return new ResponseEntity<>(GenResponseUtil.ResponseError("Комнаты с id " + roomId + " не существует"), HttpStatus.NOT_FOUND);
        }

        //Если существует
        return new ResponseEntity<>(GenResponseUtil.ResponseOK(roomResult.get()), HttpStatus.OK);
    }


    /**
     * Получение всех комнат, в которых состоит пользователь
     *
     * @param key - ключ пользователя
     * @return
     */
    @GetMapping(value = "/getByUser")
    public ResponseEntity<Map<String, Object>> getByUser(@RequestParam String key) {

        //Проверка ключа
        Optional<UserDBEntity> currentUserOptional = userService.findByKey(key);
        if (currentUserOptional.isEmpty()) {
            return new ResponseEntity<>(GenResponseUtil.ResponseError("Не удалось авторизоваться по указанному ключу"), HttpStatus.FORBIDDEN);
        }

        UserDBEntity currentUser = currentUserOptional.get();
        List<RoomDBEntity> userRooms = roomService.findUserRooms(currentUser.getId());
        return new ResponseEntity<>(GenResponseUtil.ResponseOK(userRooms), HttpStatus.OK);
    }

    /**
     * Получение пользователей, которые состоят в комнате
     * @param roomId - идентификатор чат-комнаты
     * @return
     */
    @GetMapping(value = "/getUsers")
    public ResponseEntity<Map<String, Object>> getRoomUsers(@RequestParam String key, @RequestParam String roomId) {

        //Проверка ключа
        if (!userService.checkUserKey(key)) {
            return new ResponseEntity<>(GenResponseUtil.ResponseError("Не удалось авторизоваться по указанному ключу"), HttpStatus.FORBIDDEN);
        }

        Optional<RoomDBEntity> roomResult = roomService.find(roomId);
        //Если комнаты с таким id не существует
        if (roomResult.isEmpty()) {
            return new ResponseEntity<>(GenResponseUtil.ResponseError("Комнаты с id " + roomId + " не существует"), HttpStatus.NOT_FOUND);
        }

        RoomDBEntity currentRoom = roomResult.get();
        List<UserSearchResponseEntity> roomUsersList = new ArrayList<>();
        for (String userId: currentRoom.getUsers()) {
            Optional<UserDBEntity> currentUserOptional = userService.find(userId);

            //Если пользователь найден - добавляем в список результатов
            if (currentUserOptional.isPresent()){
                UserDBEntity currentUser = currentUserOptional.get();
                roomUsersList.add(UserSearchConverter.convert(currentUser));
            }
        }

        return new ResponseEntity<>(GenResponseUtil.ResponseOK(roomUsersList), HttpStatus.OK);
    }
}
