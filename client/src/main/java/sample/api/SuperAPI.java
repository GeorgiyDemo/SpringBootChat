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
import java.util.List;

public interface SuperAPI {

    String ServerURL = "http://127.0.0.1:5000";

    /**
     * TODO: Регистрация пользователя в системе
     * @param name
     * @param login
     * @param password
     */
    static void Registration(String name, String login, String password) {
        login = URLEncoder.encode(login, StandardCharsets.UTF_8);
        password = URLEncoder.encode(password, StandardCharsets.UTF_8);

        String URL = String.format("%s/register?name=%s&login=%s&password=%s", ServerURL, name, login, password);
        String response = HTTPRequest.Get(URL);
        System.out.println(response);
    }

    /**
     * Авторизация пользователя в системе
     * @param login
     * @param password
     * @return
     */
    boolean Auth(String login, String password) throws EmptyAPIResponseException;

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
     * TODO: Создание комнаты
     * @return
     */
    boolean createRoom();

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
