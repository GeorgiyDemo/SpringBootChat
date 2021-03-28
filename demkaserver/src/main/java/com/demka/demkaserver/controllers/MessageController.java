package com.demka.demkaserver.controllers;

import com.demka.demkaserver.services.MessageService;
import com.demka.demkaserver.services.RoomService;
import com.demka.demkaserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    /*
    //TODO: Отправка сообщения в опеределенную комнату room_id
    @GetMapping(value = "/send")
    public ResponseEntity<List<User>> writeMessage() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    */

    //TODO: Получение истории сообщений определенной комнаты
    @GetMapping(value = "/get")
    public ResponseEntity<HashMap<String, Object>> getMessagesByRoom(@RequestParam String key, @RequestParam String roomId) {
        //TODO: ПРоверка на существование авторизации
        
        //TODO: Проверка на существование комнпты

        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
