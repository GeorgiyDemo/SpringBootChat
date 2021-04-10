package com.demka.demkaserver.entities.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность для отдачи данных при поиске пользователя
 * Является частным случаем UserDBEntity без полей-паролей и без ключа API
 * Используется при отдаче данных
 */
@Data
@NoArgsConstructor
public class UserSearchResponseEntity {
    private String id;
    private String name;
    private Long timeCreated;

    @Override
    public String toString() {
        return "UserSearchResponseEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", timeCreated=" + timeCreated +
                '}';
    }
}