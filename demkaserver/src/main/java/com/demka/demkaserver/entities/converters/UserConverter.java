package com.demka.demkaserver.entities.converters;

import com.demka.demkaserver.entities.database.UserDBEntity;
import com.demka.demkaserver.entities.response.UserAuthResponseEntity;

/**
 * The type User converter.
 */
public class UserConverter {

    /**
     * Логика конвертации с UserDBEntity в UserAuthResponseEntity, который отдается при авторизации пользователя
     * Это нужно для того, чтоб избавиться от полей, которые не нужны в ответе (т.е. пароль, мастер-ключ и т д)
     *
     * @param dbEntity - сущность UserDBEntity
     * @return user auth response entity
     */
    public static UserAuthResponseEntity convert(UserDBEntity dbEntity) {
        UserAuthResponseEntity responseEntity = new UserAuthResponseEntity();
        responseEntity.setId(dbEntity.getId());
        responseEntity.setName(dbEntity.getName());
        responseEntity.setKey(dbEntity.getKey());
        responseEntity.setTimeCreated(dbEntity.getTimeCreated());
        return responseEntity;
    }
}
