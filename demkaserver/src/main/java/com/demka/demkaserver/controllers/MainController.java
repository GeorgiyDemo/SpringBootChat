package com.demka.demkaserver.controllers;

import com.demka.demkaserver.config.MongoConfig;
import com.demka.demkaserver.models.User;

import com.demka.demkaserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {
    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    /** TODO: Авторизация пользователя
     * @return
     */
    @GetMapping(value = "/auth")
    public ResponseEntity<List<User>> auth() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * TODO: Регистрация пользователя
     * @return
     */
    @GetMapping(value = "/register")
    public ResponseEntity<List<User>> register() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * TODO: Поиск пользователя в системе (для последующего чата)
     * @return
     */
    @GetMapping(value = "/search")
    public ResponseEntity<List<User>> search() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * TODO: Отправка сообщения в опеределенную комнату room_id
     * @return
     */
    @GetMapping(value = "/writeMessage")
    public ResponseEntity<List<User>> writeMessage() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


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
