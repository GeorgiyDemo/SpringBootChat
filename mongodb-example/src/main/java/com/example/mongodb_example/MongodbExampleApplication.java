package com.example.mongodb_example;

import com.example.mongodb_example.models.Role;
import com.example.mongodb_example.models.User;
import com.example.mongodb_example.repositories.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.Collections;

@SpringBootApplication
public class MongodbExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongodbExampleApplication.class, args);
    }

    /*

    @Autowired
    private UserMongoRepository userMongoRepository;


    @EventListener(ApplicationReadyEvent.class)
    public void afterTheStart() {
        System.out.println("the application started...");
        Role u = new Role();
        u.setName("USER");
        u.setPrivilege("USER_PRIVILEGE");

        User user = new User();
        user.setAge(25);
        user.setEmail("test1@gmail.com");
        user.setName("test1_name");
        user.setRoles(Collections.singletonList(u));

        System.out.println("saving user to the db");
        userMongoRepository.save(user);
        System.out.println("getting users from the db");
        userMongoRepository.findAll().forEach(System.out::println);

        System.out.println("getting user by email");
        System.out.println(userMongoRepository.findFirstByEmail("test1@gmail.com"));;
    }

     */

}
