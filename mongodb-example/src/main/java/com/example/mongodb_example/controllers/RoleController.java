package com.example.mongodb_example.controllers;

import com.example.mongodb_example.models.User;
import com.example.mongodb_example.models.Role;
import com.example.mongodb_example.repositories.RoleMongoRepository;
import com.example.mongodb_example.repositories.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleMongoRepository roleMongoRepository;

    @PostMapping("/create")
    public Role createRole(@RequestBody Role role) {
        roleMongoRepository.save(role);
        return role;
    }

    /*
    @GetMapping("/info/{roleId}")
    public Role getRoleInfo(@PathVariable String roleId) {
        Role localeRole = roleMongoRepository.findFirstByid(roleId);
        return localeRole;
    }
     */
}
