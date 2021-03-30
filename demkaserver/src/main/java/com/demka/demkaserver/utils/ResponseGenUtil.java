package com.demka.demkaserver.utils;

import java.util.HashMap;
import java.util.Map;

public class ResponseGenUtil {
    /**
     * Генерирует HashMap для успешно обработанного запроса
     * @param bodyObject - любой объект, который надо передать в body
     * @return
     */
    public static HashMap<String,Object> OKResponse(Object bodyObject){
        HashMap<String,Object> map = new HashMap<>();
        map.put("result", true);
        map.put("body", bodyObject);
        return map;
    }

    /**
     * Генерирует HashMap для некорректного запроса пользователя
     * @param description - описание того, почему это некорректный запрос
     * @return
     */
    public static HashMap<String,Object> ErrorResponse(String description){
        HashMap<String,Object> map = new HashMap<>();
        map.put("result", false);
        map.put("description", description);
        return map;
    }
}
