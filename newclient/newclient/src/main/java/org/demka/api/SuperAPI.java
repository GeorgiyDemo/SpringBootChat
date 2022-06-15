package org.demka.api;

import org.demka.exceptions.EmptyAPIResponseException;
import org.demka.exceptions.FalseServerFlagException;
import org.demka.exceptions.LongPollListenerException;
import org.demka.exceptions.RoomNotFoundException;
import org.demka.models.Message;
import org.demka.models.Room;
import org.demka.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public interface SuperAPI {


    /**
     * The constant logger.
     */
    Logger logger = LoggerFactory.getLogger(MyAPI.class);

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
