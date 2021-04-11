package org.demka.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class UNIXTime2String {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.systemDefault());

    public static String convert(long time) {
        return formatter.format(Instant.ofEpochSecond(time));
    }
}
