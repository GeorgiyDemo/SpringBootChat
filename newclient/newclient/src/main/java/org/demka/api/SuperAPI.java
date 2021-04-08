package org.demka.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.demka.exceptions.EmptyAPIResponseException;
import org.demka.exceptions.FalseServerFlagException;
import org.demka.exceptions.LongpollListenerException;
import org.demka.exceptions.RoomNotFoundException;
import org.demka.models.Message;
import org.demka.models.Room;
import org.demka.models.User;
import org.demka.utils.HTTPRequest;
import org.demka.utils.String2Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SuperAPI {

    //Да, логгер публичный и в интерфейсе, и что ты мне сделаешь?
    Logger logger = LoggerFactory.getLogger(MyAPI.class);
    String ServerURL = "http://149.248.54.195:8080";

    /**
     * TODO: Регистрация пользователя в системе
     * @param name
     * @param login
     * @param password
     */
    static Map<String, Object> Registration(String name, String login, String password) {

        String URL = String.format("%s/user/register", ServerURL);
        Map<String,String> params = new HashMap<>();
        params.put("login", login);
        params.put("password", String2Hash.convert(password));
        params.put("username", name);
        String response = HTTPRequest.sendPOST(URL, params);
        Map<String, Object> resultMap = new HashMap<>();

        //TODO: ОЧЕНЬ ОПАСНАЯ ЛОГИКА, ЕСЛИ ИНЕТ ПОЯВИТСЯ, ТО СМЕРТЬ СМЕРТЬ (?)
        if (response == null){
            resultMap.put("result", false);
            resultMap.put("error","Нет ответа от сервера");
            logger.error("Не получили ответ от сервера во время регистрации пользователя "+name);
            return resultMap;
        }

        //Парсим результат
        JsonObject jsonResult = JsonParser.parseString(response).getAsJsonObject();
        Boolean authResult = jsonResult.get("result").getAsBoolean();
        resultMap.put("result", authResult);

        if (!authResult) {
            String errDescription = jsonResult.get("description").getAsString();
            resultMap.put("error", errDescription);
            logger.info("Сервер возвратил ошибку регистрации: "+errDescription);
            return resultMap;
        }

        logger.info("Пользователь "+name+" успешно зарегистрировался");
        return resultMap;
    }

    /**
     * Авторизация пользователя в системе
     * с помощью пары логин-пароль
     * @param login
     * @param password
     * @return
     * @throws EmptyAPIResponseException
     */
    boolean Auth(String login, String password) throws EmptyAPIResponseException;

    /**
     * Авторизация пользователя в системе
     * с помощью ключа API
     * @param key
     * @return
     * @throws EmptyAPIResponseException
     */
    boolean Auth(String key) throws EmptyAPIResponseException;

    /**
     * Получение всех чат-комнат пользователя
     * @return
     */
    List<Room> getUserRooms() throws FalseServerFlagException, EmptyAPIResponseException;


    /**
     * Получение объекта комнаты, в которой состоит пользователь, по её id
     * @param roomId
     * @return
     */
    Room getRoomInfo(String roomId) throws RoomNotFoundException, EmptyAPIResponseException;

    /**
     * Получение истории сообщений по конкретной комнате
     * @param roomId
     * @return
     */
    List<Message> getRoomMessagesHistory(String roomId) throws FalseServerFlagException, EmptyAPIResponseException;


    /**
     * Создание комнаты
     * @param roomName
     * @param usersString
     * @return
     * @throws FalseServerFlagException
     * @throws EmptyAPIResponseException
     */
    public boolean createRoom(String roomName, String usersString) throws FalseServerFlagException, EmptyAPIResponseException;

    /**
     * Поиск пользователей в системе
     * @param searchString
     * @return
     */
    List<User> getUsers(String searchString) throws FalseServerFlagException, EmptyAPIResponseException;

    /**
     * Отправка сообщения
     * @param text
     * @return
     * @throws FalseServerFlagException
     * @throws EmptyAPIResponseException
     */
    Message writeMessage(String text) throws FalseServerFlagException, EmptyAPIResponseException;

    /**
     * Получение сервера лонгпула
     * @throws EmptyAPIResponseException
     * @throws FalseServerFlagException
     */
    void getLongpollServer() throws EmptyAPIResponseException, FalseServerFlagException;

    /**
     * Слушатель лонгпула
     * @return
     * @throws LongpollListenerException
     * @throws EmptyAPIResponseException
     */
    List<Message> longpollListener() throws LongpollListenerException, EmptyAPIResponseException;
}
