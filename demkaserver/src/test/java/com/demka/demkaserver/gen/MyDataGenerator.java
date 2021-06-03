package com.demka.demkaserver.gen;

import com.demka.demkaserver.entities.database.LongPollDBEntity;
import com.demka.demkaserver.entities.database.MessageDBEntity;
import com.demka.demkaserver.entities.database.RoomDBEntity;
import com.demka.demkaserver.entities.database.UserDBEntity;
import com.demka.demkaserver.services.LongPollService;
import com.demka.demkaserver.services.MessageService;
import com.demka.demkaserver.services.RoomService;
import com.demka.demkaserver.services.UserService;
import com.demka.demkaserver.utils.TimeUtil;
import com.demka.demkaserver.utils.UUIDUtil;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

/**
 * The type My data generator.
 */
public class MyDataGenerator {

    /**
     * Генерирует объект пользователя для тестирования
     *
     * @param userService the user service
     * @return user db entity
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
     * @param roomService the room service
     * @return room db entity
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

    /**
     * Генерирует объект сообщения для тестирования
     *
     * @param messageService the message service
     * @return message db entity
     */
    public static MessageDBEntity createdMessageGen(MessageService messageService) {
        Faker faker = new Faker();
        String userId = UUIDUtil.newId();
        String username = faker.name().username();
        String text = String.format("%s %s", faker.hipster().word(), faker.hipster().word());
        String roomId = UUIDUtil.newId();
        return messageService.create(userId, username, text, roomId);
    }

    /**
     * Генерирует объект лонгпула для тестирования
     *
     * @param longPollService the long poll service
     * @return long poll db entity
     */
    public static LongPollDBEntity createdLongPollGen(LongPollService longPollService) {
        String userId = UUIDUtil.newId();
        long currentTs = TimeUtil.unixTime();
        return longPollService.create(userId, currentTs);
    }

}
