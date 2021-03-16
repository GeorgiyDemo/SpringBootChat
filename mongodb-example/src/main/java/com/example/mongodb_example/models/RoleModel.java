package com.example.mongodb_example.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Roles")
public class RoleModel {

    private String name;

    public RoleModel(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "RoleModel{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
