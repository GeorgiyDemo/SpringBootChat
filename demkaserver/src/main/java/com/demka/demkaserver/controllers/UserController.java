package com.demka.demkaserver.controllers;

import com.demka.demkaserver.entities.UserEntity;
import com.demka.demkaserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * TODO: Авторизация пользователя
     *
     * @return
     */
    @GetMapping("/auth")
    public ResponseEntity<?> auth(@RequestParam String login, @RequestParam String password) {

        if (userService.checkAuth(login, password)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    /*

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserEntity user) {
        userService.create(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/info")
    public ResponseEntity<List<UserEntity>> getAllUsersInfo() {

        //Query query = new Query(Criteria.where("_id").ne());
        //query.fields().include("_id");

        List<UserModel> allUsersList = userMongoRepository.findAll();
        System.out.println(allUsersList.toString());
        return allUsersList;
    }

    @GetMapping("/info/{userId}")
    public Optional<UserModel> getUSerInfoById(@PathVariable String userId) {
        Optional<UserModel> localeUser = userMongoRepository.findById(userId);
        return localeUser;
    }

     //TODO: Регистрация пользователя
    @PostMapping(value = "/register")
    public ResponseEntity<List<User>> register() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

     //TODO: Поиск пользователя в системе (для последующего чата)
    @GetMapping(value = "/search")
    public ResponseEntity<List<User>> search() {
        final List<User> persons = userService.readAll();
        return persons != null && !persons.isEmpty() ? new ResponseEntity<>(persons, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    */
}
