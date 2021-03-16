package com.example.mongodb_example.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;


@Document(collection="Users")
public class UserModel {

    @Id
    private String id;

    private String name;

    private Integer age;

    private String email;

    public UserModel(String name, Integer age, String email){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.age = age;
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
