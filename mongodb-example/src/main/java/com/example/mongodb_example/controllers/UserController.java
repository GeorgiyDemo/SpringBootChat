package com.example.mongodb_example.controllers;


import com.example.mongodb_example.converters.RoleConverter;
import com.example.mongodb_example.converters.UserConverter;
import com.example.mongodb_example.dto.UserDTO;
import com.example.mongodb_example.models.RoleModel;
import com.example.mongodb_example.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mongodb_example.repositories.UserMongoRepository;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMongoRepository userMongoRepository;

    @PostMapping("/create")
    public UserDTO createUser(@RequestBody UserDTO user) {
        UserModel buf = UserConverter.transform(user);
        userMongoRepository.save(buf);
        return user;
    }

    /*
    @GetMapping("/info/{userId}")
    public UserModel getUserInfo(@PathVariable String userId) {
        UserModel localeUser = userMongoRepository.findFirstByid(userId);
        return localeUser;
    }
     */

}
