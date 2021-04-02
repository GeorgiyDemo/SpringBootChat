package sample.api;

import sample.exceptions.EmptyAPIResponseException;
import sample.exceptions.FalseServerFlagException;
import sample.exceptions.LongpollListenerException;
import sample.exceptions.RoomNotFoundException;
import sample.models.Message;
import sample.models.Room;
import sample.models.User;
import sample.utils.HTTPRequest;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SuperAPI {

    String ServerURL = "http://127.0.0.1:8080";

    /**
     * TODO: Регистрация пользователя в системе
     * @param name
     * @param login
     * @param password
     */
    static void Registration(String name, String login, String password) {

        String URL = String.format("%s/user/register", ServerURL);
        Map<String,String> params = new HashMap<>();
        params.put("login", login);
        params.put("password", password);
        params.put("username", name);

        String response = HTTPRequest.sendPOST(URL, params);
        System.out.println(response);
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
