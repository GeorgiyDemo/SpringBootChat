package com.demka.demkaserver.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class MessageController {

    /*
    //TODO: Отправка сообщения в опеределенную комнату room_id
    @GetMapping(value = "/writeMessage")
    public ResponseEntity<List<User>> writeMessage() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    */
}
