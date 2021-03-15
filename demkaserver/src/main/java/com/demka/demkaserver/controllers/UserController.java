package com.demka.demkaserver.controllers;

import com.demka.demkaserver.models.User;
import com.demka.demkaserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
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



    @PostMapping(value = "/persons")
    public ResponseEntity<?> create(@RequestBody User person) {
        User newPerson = userService.create(person);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @GetMapping(value = "/persons")
    public ResponseEntity<List<User>> read() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "/persons/{id}")
    public ResponseEntity<User> read(@PathVariable(name = "id") int id) {
        final User person = userService.read(id);
        return person != null ? new ResponseEntity<>(person, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Обновление пользователей
     *
     * @param id
     * @param newPerson
     * @return
     */
    @PutMapping(value = "/persons/{id}")
    public ResponseEntity<User> put(@PathVariable(name = "id") int id, @RequestBody User newPerson) {

        //Если успешно обновлили данные
        if (userService.update(newPerson, id)) {
            //id персоны чтоб отдавался
            User newPersonWithId = userService.read(id);
            return new ResponseEntity<>(newPersonWithId, HttpStatus.OK);
        }
        //Если не получилось обновить данные
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Удаление пользователей
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/persons/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        final User person = userService.read(id);
        //Если есть такой пользователь
        if (person != null) {
            userService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        //Если такого пользователя нет
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
