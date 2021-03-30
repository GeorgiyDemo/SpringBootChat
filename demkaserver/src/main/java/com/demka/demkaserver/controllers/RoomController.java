package com.demka.demkaserver.controllers;

import com.demka.demkaserver.entities.converters.UserConverter;
import com.demka.demkaserver.entities.database.RoomDBEntity;
import com.demka.demkaserver.entities.database.UserDBEntity;
import com.demka.demkaserver.services.MessageService;
import com.demka.demkaserver.services.RoomService;
import com.demka.demkaserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    /**
     * Создание комнаты для общения пользователей
     * @param data - данные в JSON. Поля:
     *             key - ключ пользователя
     *             roomName - название комнаты
     *             users - строка с id пользователей, состоящих в беседе
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createRoom(@RequestBody Map<String, String> data) {

        Map<String, Object> map = new HashMap<>();
        String key = data.get("key");
        String roomName = data.get("roomName");
        String usersString = data.get("users");

        //Проверка переданных полей
        if ((key == null) || (roomName == null) || (usersString == null)){
            map.put("result", false);
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        List<String> users = Arrays.asList(usersString.split(","));

        //Получаем id создателя через key + проверка ключа
        Optional<UserDBEntity> creatorUserOptional = userService.findByKey(key);
        //Если вдруг пользователь с key не найден
        if (creatorUserOptional.isEmpty()){
            map.put("result", false);
            return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
        }

        //Проверка на существование пользователей
        for (String userId: users) {
            if (userService.find(userId).isEmpty()){
                map.put("result", false);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }
        }

        UserDBEntity creatorUser = creatorUserOptional.get();
        String creatorId = creatorUser.getId();
        RoomDBEntity newRoom = roomService.create(creatorId, roomName, users);

        //Формируем сообщение, что комната создана
        String messageText = "*Комната была создана*";
        messageService.create(creatorId,creatorUser.getName(), messageText, newRoom.getId());

        map.put("result", true);
        map.put("body", newRoom);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * Получение инфорации о комнате
     * @param key - ключ пользователя
     * @param roomId - id комнаты
     * @return
     */
    @GetMapping(value = "/get")
    public ResponseEntity<Map<String, Object>> getRoomInfo(@RequestParam String key, @RequestParam String roomId) {

        Map<String, Object> map = new HashMap<>();

        //Проверка ключа
        if (!userService.checkUserKey(key)){
            map.put("result", false);
            return new ResponseEntity(map, HttpStatus.FORBIDDEN);
        }

        Optional<RoomDBEntity> roomResult = roomService.find(roomId);

        //Если комнаты с таким id не существует
        if (roomResult.isEmpty()){
            map.put("result", false);
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }

        //Если существует
        map.put("result", true);
        map.put("body", roomResult.get());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    /**
     * Получение всех комнат, в которых состоит пользователь
     * @param key - ключ пользователя
     * @return
     */
    @GetMapping(value = "/getByUser")
    public ResponseEntity<Map<String, Object>> getByUser(@RequestParam String key) {

        Map<String, Object> map = new HashMap<>();

        //Проверка ключа
        Optional<UserDBEntity> currentUserOptional = userService.findByKey(key);
        if (currentUserOptional.isEmpty()){
            map.put("result", false);
            return new ResponseEntity(map, HttpStatus.FORBIDDEN);
        }

        UserDBEntity currentUser = currentUserOptional.get();
        List<RoomDBEntity> userRooms = roomService.findUserRooms(currentUser.getId());
        map.put("result", true);
        map.put("body", userRooms);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
