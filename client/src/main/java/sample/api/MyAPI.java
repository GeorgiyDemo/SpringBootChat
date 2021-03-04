package sample.api;


import com.google.gson.*;
import sample.exceptions.LongpollListenerException;
import sample.exceptions.RoomNotFoundException;
import sample.models.Message;
import sample.models.Room;
import sample.utils.HTTPRequest;
import sample.utils.MyLogger;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class MyAPI implements API  {

    private String userName;
    private String userKey;
    private String userId;

    private String longpollTs;
    private String longpollSubUrl;
    private String longpollKey;

    private boolean isAuthenticated;

    private static String currentRoomId;

    public MyAPI(String login, String password) {
        isAuthenticated = this.Auth(login, password);
        MyLogger.logger.info("Инициализировали MyAPI");
    }




    /**
     * Авторизация пользователя в системе
     * @param login
     * @param password
     * @return
     */
    @Override
    public boolean Auth(String login, String password) {
        login = URLEncoder.encode(login, StandardCharsets.UTF_8);
        password = URLEncoder.encode(password, StandardCharsets.UTF_8);

        String URL = String.format("%s/auth?login=%s&password=%s", ServerURL, login, password);
        String response = HTTPRequest.Get(URL);
        if (response != null) {
            JsonObject jsonResult = JsonParser.parseString(response).getAsJsonObject();
            boolean authResult = jsonResult.get("result").getAsBoolean();
            if (authResult) {
                System.out.println(jsonResult);
                JsonObject userData = jsonResult.get("body").getAsJsonObject();
                this.userId = userData.get("_id").getAsString();
                this.userKey = userData.get("key").getAsString();
                this.userName = userData.get("name").getAsString();
                MyLogger.logger.info("Auth - Авторизация прошла успешно");
                return true;
            }
            MyLogger.logger.info("Auth - Авторизация не удалась");
            return false;

        }
        MyLogger.logger.error("Auth - получили пустой ответ от сервера");
        return false;
    }

    /**
     * Получение всех чат-комнат пользователя
     * @return
     */
    @Override
    public List<Room> getUserRooms(){


        //Список комнат, который метод отдаёт
        List<Room> resultList = new ArrayList<Room>();

        //Запрашиваем данные по URL
        String URL = String.format("%s/getUserRooms?user_id=%s&user_key=%s", ServerURL, userId, userKey);
        String response = HTTPRequest.Get(URL);
        //Если ответ есть
        if (response != null) {
            JsonObject jsonResult = JsonParser.parseString(response).getAsJsonObject();
            if (jsonResult.get("result").getAsBoolean()) {

                JsonArray bufList = jsonResult.get("body").getAsJsonArray();

                for (int i = 0; i < bufList.size(); i++) {
                    JsonObject currentRoom = bufList.get(i).getAsJsonObject();

                    String roomId = currentRoom.get("_id").getAsString();
                    String creatorId = currentRoom.get("creator_id").getAsString();
                    String roomName = currentRoom.get("name").getAsString();

                    List<String> usersList = new ArrayList<>();
                    JsonArray usersArray  = currentRoom.get("users").getAsJsonArray();
                    for (int j = 0; j < usersArray.size(); j++) {
                        usersList.add(usersArray.get(j).getAsString());
                    }

                    int timeCreated = currentRoom.get("time_created").getAsInt();
                    Room room = new Room(creatorId,roomName,timeCreated,usersList,roomId);
                    resultList.add(room);
                }

                MyLogger.logger.info("getUserRooms - Получили список комнат для пользователя "+userId);
                return resultList;
            }

            MyLogger.logger.error("getUserRooms - Не удалось получить список комнат для пользователя "+userId);
            return resultList;

        }

        MyLogger.logger.error("getUserRooms - получили пустой ответ от сервера");
        return resultList;

    }

    /**
     * Получение объекта комнаты, в которой состоит пользователь, по её id
     * @param roomId
     * @return
     */
    @Override
    public Room getRoomInfo(String roomId) throws RoomNotFoundException {

        //Запрашиваем данные по URL
        //TODO: url фиксануть
        String URL = String.format("%s/getRoomInfo?room_id=%s&user_key=%s", ServerURL, roomId, userKey);
        String response = HTTPRequest.Get(URL);
        //Если ответ есть
        if (response != null) {
            JsonObject jsonResult = JsonParser.parseString(response).getAsJsonObject();
            if (jsonResult.get("result").getAsBoolean()) {

                JsonObject currentRoom = jsonResult.get("body").getAsJsonObject();
                String creatorId = currentRoom.get("creator_id").getAsString();
                String roomName = currentRoom.get("name").getAsString();
                List<String> usersList = new ArrayList<>();
                JsonArray usersArray  = currentRoom.get("users").getAsJsonArray();
                for (int i = 0; i < usersArray.size(); i++) {
                    usersList.add(usersArray.get(i).getAsString());
                }
                int timeCreated = currentRoom.get("time_created").getAsInt();
                MyLogger.logger.info("getRoomInfo - отдали данные для комнаты с id "+roomId);
                return new Room(creatorId,roomName,timeCreated,usersList,roomId);
            }
            else {
                MyLogger.logger.error("getRoomInfo - Запрашиваемой комнаты с id "+roomId+" не существует");
                throw new RoomNotFoundException("Запрашиваемой комнаты с id "+roomId+" не существует");
            }

        }
        else {
            MyLogger.logger.error("getRoomInfo - получили пустой ответ от сервера");
            throw new RoomNotFoundException("Получили пустой ответ от сервера");
        }

    }

    /**
     * Получение истории сообщений по конкретной комнате
     * @param roomId
     * @return
     */
    @Override
    public List<Message> getRoomMessagesHistory(String roomId){
        //Список комнат, который метод отдаёт
        List<Message> resultList = new ArrayList<Message>();

        //Запрашиваем данные по URL
        String URL = String.format("%s/getRoomMessagesHistory?room_id=%s&user_key=%s", ServerURL, roomId, userKey);
        String response = HTTPRequest.Get(URL);
        //Если ответ есть
        if (response != null) {
            JsonObject jsonResult = JsonParser.parseString(response).getAsJsonObject();
            if (jsonResult.get("result").getAsBoolean()) {

                JsonArray bufList = jsonResult.get("body").getAsJsonArray();

                for (int i = 0; i < bufList.size(); i++) {
                    JsonObject currentMessage = bufList.get(i).getAsJsonObject();

                    String messageId = currentMessage.get("_id").getAsString();
                    String messageRoom = currentMessage.get("room_id").getAsString();
                    String messageText = currentMessage.get("text").getAsString();
                    Integer messageTimeCreated = currentMessage.get("time_created").getAsInt();
                    String messageUserFrom = currentMessage.get("user_from").getAsString();

                    Message bufMessage = new Message(messageUserFrom,messageText, messageRoom, messageTimeCreated, messageId );
                    resultList.add(bufMessage);
                }

                MyLogger.logger.info("getRoomMessagesHistory - Получили список сообщений для комнаты "+roomId);
                return resultList;
            }

            MyLogger.logger.error("getRoomMessagesHistory - Не удалось получить список сообщений для комнаты "+roomId);
            return resultList;

        }

        MyLogger.logger.error("getRoomMessagesHistory - Получили пустой ответ от сервера");
        return resultList;

    }

    //TODO: создание комнаты
    @Override
    public boolean createRoom(){
        return false;
    }
    //TODO: отправка сообщения
    @Override
    public boolean writeMessage(String text){
        return false;
    }

    /**
     * Получение LongPoll'а
     * Выставляет longpollTs, longpollSubUrl, longpollKey
     * @return
     */
    @Override
    public boolean getLongpollServer(){
        //Запрашиваем данные по URL
        String URL = String.format("%s/getLongPollServer?user_id=%s&user_key=%s", ServerURL, userId, userKey);
        String response = HTTPRequest.Get(URL);
        //Если ответ есть
        if (response != null) {
            JsonObject jsonResult = JsonParser.parseString(response).getAsJsonObject();
            if (jsonResult.get("result").getAsBoolean()) {

                JsonObject credentials = jsonResult.get("body").getAsJsonObject();

                this.longpollTs = credentials.get("ts").getAsString();
                this.longpollSubUrl = credentials.get("url").getAsString();
                this.longpollKey = credentials.get("key").getAsString();

                MyLogger.logger.info("getLongpollServer - получили конфиг, инициализировались");
                return true;
            }
            MyLogger.logger.error("getLongpollServer - Не удалось получить конфиг, не инициализировались");
            return false;
        }

        MyLogger.logger.error("getLongpollServer - Получили пустой ответ от сервера");
        return false;

    }

    /**
     * Слушатель лонгпула
     * @return
     * @throws LongpollListenerException
     */
    @Override
    public List<Message> longpollListener() throws LongpollListenerException {

        //Если лонгпул не инициализирован
        if (longpollKey == null || longpollTs == null || longpollSubUrl == null){
            MyLogger.logger.error("longpollListener - LongPool не инициалзирован");
            throw new LongpollListenerException("Лонгпул не был иницилизирован! Нужно использовать метод getLongpollServer для иницализации");
        }

        //Если же инициализация прошла успешно
        else{

            //Список результатов сообщений
            List<Message> resultList = new ArrayList<>();

            //Запрашиваем данные по URL
            String URL = String.format("%s/%s?key=%s&ts=%s", ServerURL, longpollSubUrl, longpollKey, longpollTs);
            MyLogger.logger.info("longpollListener - отправили запрос, ожидаем новые сообщения..");
            String response = HTTPRequest.Get(URL);

            //Если ответ есть
            if (response != null) {
                JsonObject jsonResult = JsonParser.parseString(response).getAsJsonObject();
                if (jsonResult.get("result").getAsBoolean()) {

                    //Данные, которые получили
                    JsonObject newData = jsonResult.get("body").getAsJsonObject();
                    //Обновляем ts
                    this.longpollTs = newData.get("ts").getAsString();

                    //Работем с сообщениями
                    JsonArray newMessages = newData.get("updates").getAsJsonArray();
                    for (int i = 0; i < newMessages.size(); i++) {

                        JsonObject currentMessage = newMessages.get(i).getAsJsonObject();

                        String messageId = currentMessage.get("_id").getAsString();
                        String messageRoom = currentMessage.get("room_id").getAsString();
                        String messageText = currentMessage.get("text").getAsString();
                        Integer messageTimeCreated = currentMessage.get("time_created").getAsInt();
                        String messageUserFrom = currentMessage.get("user_from").getAsString();

                        Message bufMessage = new Message(messageUserFrom,messageText, messageRoom, messageTimeCreated, messageId);
                        resultList.add(bufMessage);
                    }
                    MyLogger.logger.info("longpollListener - получили новые сообщения");
                }
                else{
                    MyLogger.logger.error("longpollListener - result == false");
                    throw new LongpollListenerException("Что-то не так с лонгпулом. Ответ result == false");
                }
            }
            else{
                MyLogger.logger.error("longpollListener - время ожидания истекло");
                throw new LongpollListenerException("Лонгпул ничего не получил от сервера. Что-то сломалось или время ожидания истекло");
            }

            return resultList;
        }
    }


    public boolean getIsAuthenticated(){
        return isAuthenticated;
    }

    public static String getCurrentRoomId() {
        return currentRoomId;
    }

    public static void setCurrentRoomId(String currentRoomId) {
        MyAPI.currentRoomId = currentRoomId;
    }
}


