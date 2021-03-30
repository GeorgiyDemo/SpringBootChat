package com.demka.demkaserver.controllers;

import com.demka.demkaserver.entities.database.MessageDBEntity;
import com.demka.demkaserver.entities.database.RoomDBEntity;
import com.demka.demkaserver.entities.database.UserDBEntity;
import com.demka.demkaserver.services.MessageService;
import com.demka.demkaserver.services.RoomService;
import com.demka.demkaserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    /**
     * Отправка сообщения в опеределенную комнату roomId
     * @param data - данные в JSON. Поля:
     *             key - ключ пользователя
     *             text - текст сообщения
     *             roomId - id комнаты, куда отправляется сообщение
     * @return
     */
    @PostMapping(value = "/send")
    public ResponseEntity<Map<String, Object>> sendMessage(@RequestBody Map<String, String> data) {

        Map<String, Object> map = new HashMap<>();
        String key = data.get("key");
        String messageText = data.get("text");
        String roomId = data.get("roomId");

        //Проверка переданных полей
        if ((key == null) || (messageText == null) || (roomId == null)){
            map.put("result", false);
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        //Получаем объект пользователя через key + проверка ключа
        Optional<UserDBEntity> roomUserOptional = userService.findByKey(key);
        if (roomUserOptional.isEmpty()){
            map.put("result", false);
            return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
        }

        //Проверка на комнату
        Optional<RoomDBEntity> currentRoomOptional = roomService.find(roomId);
        if (currentRoomOptional.isEmpty()){
            map.put("result", false);
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        RoomDBEntity currentRoom = currentRoomOptional.get();
        UserDBEntity roomUser = roomUserOptional.get();

        //Проверка на то, может ли пользователь вообще писать в эту комнату
        if (!currentRoom.getUsers().contains(roomUser.getId())){
            map.put("result", false);
            return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
        }

        MessageDBEntity newMessage = messageService.create(roomUser.getId(),roomUser.getName(), messageText, roomId);
        map.put("result", true);
        map.put("body", newMessage);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * Получение истории сообщений определенной комнаты
     * @param key - ключ пользователя
     * @param roomId - id комнаты
     * @return
     */
    @GetMapping(value = "/get")
    public ResponseEntity<Map<String, Object>> getMessagesByRoom(@RequestParam String key, @RequestParam String roomId) {

        Map<String, Object> map = new HashMap<>();

        //Проверка ключа
        if (!userService.checkUserKey(key)){
            map.put("result", false);
            return new ResponseEntity(map, HttpStatus.FORBIDDEN);
        }

        //Проверка на существование комнаты
        Optional<RoomDBEntity> currentRoom = roomService.find(roomId);
        if (currentRoom.isEmpty()){
            map.put("result", false);
            return new ResponseEntity(map, HttpStatus.BAD_REQUEST);
        }

        //Если существует
        List<MessageDBEntity> messagesList = messageService.findByRoom(roomId);
        map.put("result", true);
        map.put("body", messagesList);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
