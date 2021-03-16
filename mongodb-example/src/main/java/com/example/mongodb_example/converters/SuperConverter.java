package com.example.mongodb_example.converters;

import com.example.mongodb_example.dto.RoleDTO;
import com.example.mongodb_example.models.Role;

public interface SuperConverter {
    public static Role transform(final RoleDTO roleDTO) {
        return new Role(roleDTO.getName());
    }
}
