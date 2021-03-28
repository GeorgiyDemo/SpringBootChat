package com.demka.demkaserver.controllers;

import com.demka.demkaserver.entities.database.UserDBEntity;
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
import java.util.Optional;

@RestController
@RequestMapping("/longpoll")
public class LongPollController {

    @Autowired
    private UserService userService;

    //TODO: Получение данных для лонгпула по определенному user_id и его user_key
    @GetMapping(value = "/getServer")
    public ResponseEntity<HashMap<String, Object>> getLongPollServer(@RequestParam String key) {
        HashMap<String, Object> map = new HashMap<>();


        //Получаем объект пользователя через key + проверка ключа
        Optional<UserDBEntity> userOptional = userService.findByKey(key);
        if (userOptional.isEmpty()){
            map.put("result", false);
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }

        UserDBEntity user = userOptional.get();


        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /*
    //TODO: Получение данных для лонгпула по определенному user_id и его user_key
    @GetMapping(value = "/LongPoll/<server>")
    public ResponseEntity<List<User>> longpoll() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

     */

}
