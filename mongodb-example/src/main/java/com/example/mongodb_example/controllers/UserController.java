package com.example.mongodb_example.controllers;


import com.example.mongodb_example.converters.RoleConverter;
import com.example.mongodb_example.converters.UserConverter;
import com.example.mongodb_example.dto.UserDTO;
import com.example.mongodb_example.models.RoleModel;
import com.example.mongodb_example.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.bind.annotation.*;
import com.example.mongodb_example.repositories.UserMongoRepository;

import java.util.List;
import java.util.Optional;

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


    @GetMapping("/info")
    public List<UserModel> getAllUsersInfo() {

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

    /*
    @GetMapping("/info/{userId}")
    public UserModel getUserInfo(@PathVariable String userId) {
        UserModel localeUser = userMongoRepository.findFirstByid(userId);
        return localeUser;
    }
     */

}
