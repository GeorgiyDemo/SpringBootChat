package com.example.mongodb_example.converters;

import com.example.mongodb_example.dto.RoleDTO;
import com.example.mongodb_example.models.RoleModel;
import com.example.mongodb_example.models.UserModel;

public class RoleConverter implements SuperConverter {

    public static RoleModel transform(final RoleDTO roleDTO) {
        RoleModel buf = new RoleModel(roleDTO.getName());
        return buf;
    }
}