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
     * Отправка сообщения в опеределенную комнату room_id
     * @param data
     * @return
     */
    @PostMapping(value = "/send")
    public ResponseEntity<HashMap<String, Object>> sendMessage(@RequestBody Map<String, String> data) {
        HashMap<String, Object> map = new HashMap<>();
        String key = data.get("key");
        String messageText = data.get("text");
        String roomId = data.get("room_id");

        //Получаем объект создателя через key + проверка ключа
        Optional<UserDBEntity> creatorUser = userService.findByKey(key);
        if (creatorUser.isEmpty()){
            map.put("result", false);
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }

        //Проверка на комнату
        if (roomService.find(roomId).isEmpty()){
            map.put("result", false);
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        UserDBEntity creator = creatorUser.get();
        MessageDBEntity newMessage = messageService.create(creator.getId(),creator.getName(), messageText, roomId);
        map.put("result", true);
        map.put("body", newMessage);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    /**
     * Получение истории сообщений определенной комнаты
     * @param key
     * @param roomId
     * @return
     */
    @GetMapping(value = "/get")
    public ResponseEntity<HashMap<String, Object>> getMessagesByRoom(@RequestParam String key, @RequestParam String roomId) {

        HashMap<String, Object> map = new HashMap<>();

        //Проверка ключа
        if (!userService.checkUserKey(key)){
            map.put("result", false);
            return new ResponseEntity(map, HttpStatus.UNAUTHORIZED);
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
