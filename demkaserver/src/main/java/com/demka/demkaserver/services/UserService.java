package com.demka.demkaserver.services;

import com.demka.demkaserver.models.User;

import java.util.List;

public interface UserService {

    User create(User person);
    List<User> readAll();
    User read(int id);
    boolean update(User person, int id);
    boolean delete(int id);
}
