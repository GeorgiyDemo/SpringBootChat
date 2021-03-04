package sample.api;

import sample.exceptions.LongpollListenerException;
import sample.exceptions.RoomNotFoundException;
import sample.models.Message;
import sample.models.Room;
import sample.utils.HTTPRequest;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public interface API {


    public final static String ServerURL = "http://127.0.0.1:5000";
    /**
     * TODO: Регистрация пользователя в системе
     * @param name
     * @param login
     * @param password
     */
    public static void Registration(String name, String login, String password) {
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
    public boolean Auth(String login, String password);

    /**
     * Получение всех чат-комнат пользователя
     * @return
     */
    public List<Room> getUserRooms();


    /**
     * Получение объекта комнаты, в которой состоит пользователь, по её id
     * @param roomId
     * @return
     */
    public Room getRoomInfo(String roomId) throws RoomNotFoundException;

    /**
     * Получение истории сообщений по конкретной комнате
     * @param roomId
     * @return
     */
    public List<Message> getRoomMessagesHistory(String roomId);


    //TODO: создание комнаты
    public boolean createRoom();

    //TODO: отправка сообщения
    public boolean writeMessage(String text);

    public boolean getLongpollServer();

    public List<Message> longpollListener() throws LongpollListenerException;
}
