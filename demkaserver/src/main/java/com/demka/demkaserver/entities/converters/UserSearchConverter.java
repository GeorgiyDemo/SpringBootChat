package com.demka.demkaserver.entities.converters;

import com.demka.demkaserver.entities.database.UserDBEntity;
import com.demka.demkaserver.entities.response.UserSearchResponseEntity;

public class UserSearchConverter {

    /**
     * Логика конвертации с UserDBEntity в UserSearchResponseEntity, который отдается при поиске пользователя в системе
     * Это нужно для того, чтоб избавиться от всех полей, которые не нужны в ответе (т.е. пароль, мастер-ключ и т д)
     *
     * @param dbEntity - сущность UserDBEntity
     * @return
     */
    public static UserSearchResponseEntity convert(UserDBEntity dbEntity) {
        UserSearchResponseEntity responseEntity = new UserSearchResponseEntity();

        responseEntity.setId(dbEntity.getId());
        responseEntity.setName(dbEntity.getName());
        responseEntity.setTimeCreated(dbEntity.getTimeCreated());

        return responseEntity;
    }
}
