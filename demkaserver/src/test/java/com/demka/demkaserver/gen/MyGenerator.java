package com.demka.demkaserver.gen;

import com.demka.demkaserver.entities.database.RoomDBEntity;
import com.demka.demkaserver.entities.database.UserDBEntity;
import com.demka.demkaserver.services.RoomService;
import com.demka.demkaserver.services.UserService;
import com.demka.demkaserver.utils.UUIDUtil;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

public class MyGenerator {
    /**
     * Генерация данных пользователя системы для тестирования
     *
     * @return
     */
    public static UserDBEntity createdUserGen(UserService userService) {
        Faker faker = new Faker();
        String login = faker.internet().emailAddress();
        String password = UUIDUtil.newKey();
        String username = faker.name().username();
        String masterPassword = UUIDUtil.newKey();
        return userService.create(login, password, username, masterPassword);
    }

    /**
     * Генерирует объект комнаты для тестирования
     *
     * @return
     */
    public static RoomDBEntity createdURoomGen(RoomService roomService) {
        Faker faker = new Faker();
        String creatorId = UUIDUtil.newId();
        String roomName = String.format("%s %s", faker.hipster().word(), faker.hipster().word());
        List<String> users = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            users.add(UUIDUtil.newId());
        return roomService.create(creatorId, roomName, users);
    }

}
