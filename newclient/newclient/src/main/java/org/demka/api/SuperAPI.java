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

/**
 * The interface Super api.
 */
public interface SuperAPI {

    /**
     * The constant logger.
     */
    Logger logger = LoggerFactory.getLogger(MyAPI.class);
    /**
     * The constant serverURL.
     */
    String serverURL = "http://127.0.0.1:8080";

    /**
     * Восстановление пароля
     *
     * @param login       - логин пользователя
     * @param newPassword - новый пароль пользователя
     * @param masterKey   - мастер-ключ пользователя
     * @return map
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
     * @return map
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
     * @return boolean
     * @throws EmptyAPIResponseException the empty api response exception
     */
    boolean auth(String login, String password) throws EmptyAPIResponseException;

    /**
     * Авторизация пользователя в системе
     * с помощью ключа API
     *
     * @param key - ключ API пользователя
     * @return boolean
     * @throws EmptyAPIResponseException the empty api response exception
     */
    boolean auth(String key) throws EmptyAPIResponseException;

    /**
     * Получение всех чат-комнат пользователя
     *
     * @return user rooms
     * @throws FalseServerFlagException  the false server flag exception
     * @throws EmptyAPIResponseException the empty api response exception
     */
    List<Room> getUserRooms() throws FalseServerFlagException, EmptyAPIResponseException;


    /**
     * Получение объекта комнаты, в которой состоит пользователь, по её id
     *
     * @param roomId - идентификатор комнаты
     * @return room info
     * @throws RoomNotFoundException     the room not found exception
     * @throws EmptyAPIResponseException the empty api response exception
     */
    Room getRoomInfo(String roomId) throws RoomNotFoundException, EmptyAPIResponseException;

    /**
     * Получение истории сообщений по конкретной комнате
     *
     * @param roomId - идентификатор комнаты
     * @return room messages history
     * @throws FalseServerFlagException  the false server flag exception
     * @throws EmptyAPIResponseException the empty api response exception
     */
    List<Message> getRoomMessagesHistory(String roomId) throws FalseServerFlagException, EmptyAPIResponseException;


    /**
     * Создание комнаты
     *
     * @param roomName    - название комнаты
     * @param usersString - строка с идентификаторами пользователей-участинков комнаты
     * @return boolean
     * @throws FalseServerFlagException  the false server flag exception
     * @throws EmptyAPIResponseException the empty api response exception
     */
    boolean createRoom(String roomName, String usersString) throws FalseServerFlagException, EmptyAPIResponseException;

    /**
     * Поиск пользователей в системе по имени
     *
     * @param searchExp - паттерн имени пользователя
     * @return users
     * @throws FalseServerFlagException  the false server flag exception
     * @throws EmptyAPIResponseException the empty api response exception
     */
    List<User> getUsers(String searchExp) throws FalseServerFlagException, EmptyAPIResponseException;


    /**
     * Получение объектов пользователей по id комнаты, в которой они состоят
     *
     * @param roomId - идентификатор комнаты
     * @return users by room
     * @throws FalseServerFlagException  the false server flag exception
     * @throws EmptyAPIResponseException the empty api response exception
     */
    List<User> getUsersByRoom(String roomId) throws FalseServerFlagException, EmptyAPIResponseException;

    /**
     * Отправка сообщения в текущую комнату
     *
     * @param text - текст сообщения
     * @return message
     * @throws FalseServerFlagException  the false server flag exception
     * @throws EmptyAPIResponseException the empty api response exception
     */
    Message writeMessage(String text) throws FalseServerFlagException, EmptyAPIResponseException;

    /**
     * Получение сервера лонгпула
     * Выставляет longpollTs, longpollSubUrl, longpollKey
     *
     * @throws EmptyAPIResponseException the empty api response exception
     * @throws FalseServerFlagException  the false server flag exception
     */
    void getLongPollServer() throws EmptyAPIResponseException, FalseServerFlagException;

    /**
     * Слушатель лонгпула
     *
     * @return list
     * @throws LongPollListenerException the long poll listener exception
     */
    List<Message> longPollListener() throws LongPollListenerException;
}
