package com.demka.demkaserver.utils;

import java.time.Instant;

/**
 * The type Time util.
 */
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
