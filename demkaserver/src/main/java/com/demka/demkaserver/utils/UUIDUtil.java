package com.demka.demkaserver.utils;

import java.util.UUID;

/**
 * The type Uuid util.
 */
public class UUIDUtil {

    /**
     * Генерация id для записи в СУБД
     *
     * @return - новый сгенерированный id
     */
    public static String newId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * Генерация ключа для использования в качестве ключа пользователя или лонгпула
     *
     * @return - новый сгенерированный ключ
     */
    public static String newKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * Генерация URL для обращения к лонгпулу
     *
     * @return - новый сгенерированный URL
     */
    public static String newURL() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
