package com.demka.demkaserver.utils;

import java.time.Instant;

public class TimeUtil {
    /**
     * Возвращает текущее UNIX-время системы
     *
     * @return Текущее UNIX-время
     */
    public static long unixTime() {
        return Instant.now().getEpochSecond();
    }
}
