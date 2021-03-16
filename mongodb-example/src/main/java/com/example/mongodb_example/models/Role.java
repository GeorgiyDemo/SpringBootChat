package com.example.mongodb_example.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Roles")
public class Role {

    private String name;
    public Role(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                '}';
    }
}
