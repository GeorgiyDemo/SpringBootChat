package com.demka.demkaserver.controllers;

import com.demka.demkaserver.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class RoomsController {

    /**
     * TODO: Создание комнаты для общения пользователей
     * @return
     */
    @GetMapping(value = "/createRoom")
    public ResponseEntity<List<User>> createRoom() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * TODO: Получение инфорации о комнате
     * @return
     */
    @GetMapping(value = "/getRoomInfo")
    public ResponseEntity<List<User>> getRoomInfo() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * TODO: Получение истории сообщений определенной комнаты
     * @return
     */
    @GetMapping(value = "/getRoomMessagesHistory")
    public ResponseEntity<List<User>> getRoomMessagesHistory() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}

