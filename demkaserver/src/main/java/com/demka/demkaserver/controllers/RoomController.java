package com.demka.demkaserver.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class RoomController {

    /*

     //TODO: Создание комнаты для общения пользователей
    @GetMapping(value = "/createRoom")
    public ResponseEntity<List<User>> createRoom() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //TODO: Получение инфорации о комнате
    @GetMapping(value = "/getRoomInfo")
    public ResponseEntity<List<User>> getRoomInfo() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //TODO: Получение истории сообщений определенной комнаты
    @GetMapping(value = "/getRoomMessagesHistory")
    public ResponseEntity<List<User>> getRoomMessagesHistory() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

     */
}
