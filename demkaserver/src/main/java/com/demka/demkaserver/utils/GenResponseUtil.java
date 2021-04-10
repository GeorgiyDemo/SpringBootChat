package com.demka.demkaserver.utils;

import java.util.HashMap;

public class GenResponseUtil {
    /**
     * Генерирует HashMap для успешно обработанного запроса
     *
     * @param bodyObject - любой объект, который надо передать в body ответа
     * @return
     */
    public static HashMap<String, Object> ResponseOK(Object bodyObject) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("result", true);
        map.put("body", bodyObject);
        return map;
    }

    /**
     * Генерирует HashMap для некорректного запроса пользователя
     *
     * @param description - описание того, почему это некорректный запрос
     * @return
     */
    public static HashMap<String, Object> ResponseError(String description) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("result", false);
        map.put("description", description);
        return map;
    }
}
