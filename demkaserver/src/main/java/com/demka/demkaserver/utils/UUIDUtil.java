package com.demka.demkaserver.utils;

import java.util.UUID;

public class UUIDUtil {

    public static String newId(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static String newKey(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
