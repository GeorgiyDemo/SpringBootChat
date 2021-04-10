package com.demka.demkaserver.entities.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность для отдачи данных при авторизации пользователя
 * Является частным случаем UserDBEntity без полей-паролей, но с ключем API
 * Используется при отдаче данных
 */
@Data
@NoArgsConstructor
public class UserAuthResponseEntity {
    private String id;
    private String name;
    private String key;
    private Long timeCreated;

    @Override
    public String toString() {
        return "UserAuthResponseEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", timeCreated=" + timeCreated +
                '}';
    }
}