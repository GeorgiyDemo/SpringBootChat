package org.demka.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.demka.utils.String2HashUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * The interface Super api.
 */
public abstract class AbstractAPI implements SuperAPI {

    /**
     * The constant serverURL.
     */
    private static String serverURL = null;

    static String getServerUrl() {
        if (serverURL == null) {
            String response = HTTPRequest.sendGET("https://georgiydemo.github.io/host.json");
            JsonObject jsonResult = JsonParser.parseString(response).getAsJsonObject();
            String hostName = jsonResult.get("site").getAsString();
            serverURL = hostName + "/SpringBootChat";
        }
        return serverURL;
    }

    public static Map<String, Object> resetPassword(String login, String newPassword, String masterKey) {
        String URL = String.format("%s/user/reset", serverURL);
        Map<String, String> params = new HashMap<>();
        params.put("email", login);
        params.put("newPassword", String2HashUtil.convert(newPassword));
        params.put("masterKey", String2HashUtil.convert(masterKey));

        Gson gson = new Gson();
        String jsonString = gson.toJson(params);
        String response = HTTPRequest.sendPUT(URL, jsonString);
        Map<String, Object> resultMap = new HashMap<>();

        if (response == null) {
            resultMap.put("result", false);
            resultMap.put("error", "Нет ответа от сервера");
            logger.error("Не получили ответ от сервера во время смены пароля пользователя с логином " + login);
            return resultMap;
        }

        //Парсим результат
        JsonObject jsonResult = JsonParser.parseString(response).getAsJsonObject();
        Boolean authResult = jsonResult.get("result").getAsBoolean();
        resultMap.put("result", authResult);

        if (!authResult) {
            String errDescription = jsonResult.get("description").getAsString();
            resultMap.put("error", errDescription);
            logger.info("Сервер возвратил ошибку при смене пароля: " + errDescription);
            return resultMap;
        }
        logger.info("Пользователь " + login + " успешно сменил пароль");
        return resultMap;
    }

    /**
     * Регистрация пользователя в системе
     *
     * @param name      - ник
     * @param login     - логин (e-mail) пользователя
     * @param password  - пароль пользователя
     * @param masterKey - мастер-пароль пользователя
     * @return map
     */

    public static Map<String, Object> registration(String name, String login, String password, String masterKey) {

        String URL = String.format("%s/user/register", serverURL);
        Map<String, String> params = new HashMap<>();
        params.put("login", login);
        params.put("username", name);
        params.put("password", String2HashUtil.convert(password));
        params.put("masterKey", String2HashUtil.convert(masterKey));
        String response = HTTPRequest.sendPOST(URL, params);
        Map<String, Object> resultMap = new HashMap<>();

        if (response == null) {
            resultMap.put("result", false);
            resultMap.put("error", "Нет ответа от сервера");
            logger.error("Не получили ответ от сервера во время регистрации пользователя " + name);
            return resultMap;
        }

        //Парсим результат
        JsonObject jsonResult = JsonParser.parseString(response).getAsJsonObject();
        Boolean authResult = jsonResult.get("result").getAsBoolean();
        resultMap.put("result", authResult);

        if (!authResult) {
            String errDescription = jsonResult.get("description").getAsString();
            resultMap.put("error", errDescription);
            logger.info("Сервер возвратил ошибку регистрации: " + errDescription);
            return resultMap;
        }

        logger.info("Пользователь " + name + " успешно зарегистрировался");
        return resultMap;
    }

}
