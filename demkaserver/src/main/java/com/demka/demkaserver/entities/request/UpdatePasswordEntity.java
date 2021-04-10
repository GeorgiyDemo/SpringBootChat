package com.demka.demkaserver.entities.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность для обновления пароля пользователя
 * Используется при приёме данных
 */
@Data
@NoArgsConstructor
public class UpdatePasswordEntity {
    private String email;
    private String masterKey;
    private String newPassword;

    @Override
    public String toString() {
        return "UpdatePasswordEntity{" +
                "email='" + email + '\'' +
                ", masterKey='" + masterKey + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}