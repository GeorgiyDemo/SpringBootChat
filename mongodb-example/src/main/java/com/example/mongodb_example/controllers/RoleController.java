package com.example.mongodb_example.controllers;

import com.example.mongodb_example.converters.RoleConverter;
import com.example.mongodb_example.dto.RoleDTO;
import com.example.mongodb_example.models.RoleModel;
import com.example.mongodb_example.repositories.RoleMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleMongoRepository roleMongoRepository;

    @PostMapping("/create")
    public RoleDTO createRole(@RequestBody RoleDTO role) {
        RoleModel buf = RoleConverter.transform(role);
        roleMongoRepository.save(buf);
        return role;
    }

    /*
    @GetMapping("/info/{roleId}")
    public RoleModel getRoleInfo(@PathVariable String roleId) {
        RoleModel localeRole = roleMongoRepository.findFirstByid(roleId);
        return localeRole;
    }
     */
}
