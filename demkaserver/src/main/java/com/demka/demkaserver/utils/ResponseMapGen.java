package com.demka.demkaserver.utils;

import java.util.HashMap;
import java.util.Map;

public class ResponseMapGen {
    public static HashMap<String,Object> OKResponse(Object bodyObject){
        HashMap<String,Object> map = new HashMap<>();
        map.put("result", true);
        map.put("body", bodyObject);
        return map;
    }

    public static HashMap<String,Object> ErrorResponse(String description){
        HashMap<String,Object> map = new HashMap<>();
        map.put("result", false);
        map.put("description", description);
        return map;
    }
}
