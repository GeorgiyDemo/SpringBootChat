package com.example.mongodb_example.dto;

import lombok.Data;
import java.util.Date;

@Data
public class RoleDTO implements SuperDTO {
    private String id;
    private String name;
}