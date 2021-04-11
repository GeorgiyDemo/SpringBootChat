package org.demka.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.demka.exceptions.EmptyAPIResponseException;
import org.demka.exceptions.FalseServerFlagException;
import org.demka.exceptions.LongPollListenerException;
import org.demka.exceptions.RoomNotFoundException;
import org.demka.models.Message;
import org.demka.models.Room;
import org.demka.models.User;
import org.demka.utils.String2HashUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SuperAPI {

    Logger logger = LoggerFactory.getLogger(MyAPI.class);
    String serverURL = "http://149.248.54.195:8080";

    /**
     * Восстановление пароля
     *
     * @param login       - логин пользователя
     * @param newPassword - новый пароль пользователя
     * @param masterKey   - мастер-ключ пользователя
     * @return
     */
    static Map<String, Object> resetPassword(String login, String newPassword, String masterKey) {
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
     * @return
     */
    static Map<String, Object> registration(String name, String login, String password, String masterKey) {

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

    /**
     * Авторизация пользователя в системе
     * с помощью пары логин-пароль
     *
     * @param login    - логин пользователя
     * @param password - пароль пользоваетля
     * @return
     * @throws EmptyAPIResponseException
     */
    boolean auth(String login, String password) throws EmptyAPIResponseException;

    /**
     * Авторизация пользователя в системе
     * с помощью ключа API
     *
     * @param key - ключ API пользователя
     * @return
     * @throws EmptyAPIResponseException
     */
    boolean auth(String key) throws EmptyAPIResponseException;

    /**
     * Получение всех чат-комнат пользователя
     *
     * @return
     * @throws FalseServerFlagException
     * @throws EmptyAPIResponseException
     */
    List<Room> getUserRooms() throws FalseServerFlagException, EmptyAPIResponseException;


    /**
     * Получение объекта комнаты, в которой состоит пользователь, по её id
     *
     * @param roomId - идентификатор комнаты
     * @return
     * @throws RoomNotFoundException
     * @throws EmptyAPIResponseException
     */
    Room getRoomInfo(String roomId) throws RoomNotFoundException, EmptyAPIResponseException;

    /**
     * Получение истории сообщений по конкретной комнате
     *
     * @param roomId - идентификатор комнаты
     * @return
     * @throws FalseServerFlagException
     * @throws EmptyAPIResponseException
     */
    List<Message> getRoomMessagesHistory(String roomId) throws FalseServerFlagException, EmptyAPIResponseException;


    /**
     * Создание комнаты
     *
     * @param roomName    - название комнаты
     * @param usersString - строка с идентификаторами пользователей-участинков комнаты
     * @return
     * @throws FalseServerFlagException
     * @throws EmptyAPIResponseException
     */
    boolean createRoom(String roomName, String usersString) throws FalseServerFlagException, EmptyAPIResponseException;

    /**
     * Поиск пользователей в системе по имени
     *
     * @param searchExp - паттерн имени пользователя
     * @return
     * @throws FalseServerFlagException
     * @throws EmptyAPIResponseException
     */
    List<User> getUsers(String searchExp) throws FalseServerFlagException, EmptyAPIResponseException;


    /**
     * Получение объектов пользователей по id комнаты, в которой они состоят
     *
     * @param roomId - идентификатор комнаты
     * @return
     * @throws FalseServerFlagException
     * @throws EmptyAPIResponseException
     */
    List<User> getUsersByRoom(String roomId) throws FalseServerFlagException, EmptyAPIResponseException;

    /**
     * Отправка сообщения в текущую комнату
     *
     * @param text - текст сообщения
     * @return
     * @throws FalseServerFlagException
     * @throws EmptyAPIResponseException
     */
    Message writeMessage(String text) throws FalseServerFlagException, EmptyAPIResponseException;

    /**
     * Получение сервера лонгпула
     * Выставляет longpollTs, longpollSubUrl, longpollKey
     *
     * @throws EmptyAPIResponseException
     * @throws FalseServerFlagException
     */
    void getLongPollServer() throws EmptyAPIResponseException, FalseServerFlagException;

    /**
     * Слушатель лонгпула
     *
     * @return
     * @throws LongPollListenerException
     */
    List<Message> longPollListener() throws LongPollListenerException;
}
