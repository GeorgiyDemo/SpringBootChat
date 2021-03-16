package com.example.mongodb_example.controllers;


import com.example.mongodb_example.models.Role;
import com.example.mongodb_example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mongodb_example.repositories.UserMongoRepository;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMongoRepository userMongoRepository;

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        userMongoRepository.save(user);
        return user;
    }

    /*
    @GetMapping("/info/{userId}")
    public User getUserInfo(@PathVariable String userId) {
        User localeUser = userMongoRepository.findFirstByid(userId);
        return localeUser;
    }
     */

}
