package org.demka.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Класс для работы с UNIX-временем
 */
public class UNIXTimeUtil {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.systemDefault());

    /**
     * Конвертирует UNIX-время в обычное время
     *
     * @param time - UNIX-время
     * @return string
     */
    public static String convert(long time) {
        return formatter.format(Instant.ofEpochSecond(time));
    }
}
