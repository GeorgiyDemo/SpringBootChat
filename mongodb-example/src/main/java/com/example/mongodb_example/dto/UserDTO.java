package com.example.mongodb_example.dto;

import lombok.Data;

@Data
public class UserDTO implements SuperDTO {
    private String _id;
    private String name;
    private Integer age;
    private String email;
}

