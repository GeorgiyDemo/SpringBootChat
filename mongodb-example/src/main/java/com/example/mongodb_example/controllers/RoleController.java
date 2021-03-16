package com.example.mongodb_example.controllers;

import com.example.mongodb_example.converters.RoleConverter;
import com.example.mongodb_example.dto.RoleDTO;
import com.example.mongodb_example.models.RoleModel;
import com.example.mongodb_example.repositories.RoleMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/info")
    public List<RoleModel> getAllRolesInfo() {
        List<RoleModel> allRolesList = roleMongoRepository.findAll();
        System.out.println(allRolesList.toString());
        return allRolesList;
    }

    @GetMapping("/info/{roleId}")
    public Optional<RoleModel> getRoleInfoById(@PathVariable String roleId) {
        Optional<RoleModel> localeRole = roleMongoRepository.findById(roleId);
        return localeRole;
    }

}
