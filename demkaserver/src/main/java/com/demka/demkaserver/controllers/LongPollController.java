package com.demka.demkaserver.controllers;

import com.demka.demkaserver.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class LongPollController {

    /**
     * TODO: Получение данных для лонгпула по определенному user_id и его user_key
     * @return
     */
    @GetMapping(value = "/getLongPollServer")
    public ResponseEntity<List<User>> getLongPollServer() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * TODO: Получение данных для лонгпула по определенному user_id и его user_key
     * @return
     */
    @GetMapping(value = "/LongPoll/<server>")
    public ResponseEntity<List<User>> longpoll() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
