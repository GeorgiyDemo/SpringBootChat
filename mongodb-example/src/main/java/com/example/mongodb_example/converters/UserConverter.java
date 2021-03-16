package com.example.mongodb_example.converters;
import com.example.mongodb_example.dto.UserDTO;
import com.example.mongodb_example.models.UserModel;


public class UserConverter implements SuperConverter {
    public static UserModel transform(final UserDTO userDTO) {
        UserModel buf = new UserModel(userDTO.get_id(), userDTO.getName(), userDTO.getAge(), userDTO.getEmail());
        return buf;
    }
}