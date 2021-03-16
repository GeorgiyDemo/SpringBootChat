package com.example.mongodb_example.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Roles")
public class RoleModel {

    @Id
    private String id;

    private String name;

    public RoleModel(String id, String name) {
        this.id = id;
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
    public void setName(String id, String name) {
        this.name = name;
    }
}
