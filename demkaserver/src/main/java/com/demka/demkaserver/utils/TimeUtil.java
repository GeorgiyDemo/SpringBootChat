package com.demka.demkaserver.utils;

import java.time.Instant;

public class TimeUtil {
    public static long unixTime(){
        return Instant.now().getEpochSecond();
    }
}
