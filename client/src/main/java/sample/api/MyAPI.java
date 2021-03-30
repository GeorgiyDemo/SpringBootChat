package sample.api;


import com.google.gson.*;
import sample.Main;
import sample.exceptions.EmptyAPIResponseException;
import sample.exceptions.FalseServerFlagException;
import sample.exceptions.LongpollListenerException;
import sample.exceptions.RoomNotFoundException;
import sample.models.Message;
import sample.models.Room;
import sample.models.User;
import sample.utils.HTTPRequest;
import sample.utils.MyLogger;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyAPI implements SuperAPI {

    private Main mainApp;

    private String userName;
    private String userKey;
    private String userId;

    private String longpollTs;
    private String longpollSubUrl;
    private String longpollKey;
    private String currentRoomId;

    private boolean isAuthenticated;


    public MyAPI(String login, String password, Main mainApp) {
        this.mainApp = mainApp;
        try {
            isAuthenticated = this.Auth(login, password);
        } catch (EmptyAPIResponseException e) {
            e.printStackTrace();
        }
        MyLogger.logger.info("Инициализировали MyAPI");
    }


    /**
     * Авторизация пользователя в системе
     * @param login
     * @param password
     * @return
     * @throws EmptyAPIResponseException
     */
    @Override
    public boolean Auth(String login, String password) throws EmptyAPIResponseException {
        login = URLEncoder.encode(login, StandardCharsets.UTF_8);
        password = URLEncoder.encode(password, StandardCharsets.UTF_8);

        String URL = String.format("%s/user/auth?login=%s&password=%s", ServerURL, login, password);
        String response = HTTPRequest.sendGET(URL);
        if (response != null) {
            JsonObject jsonResult = JsonParser.parseString(response).getAsJsonObject();
            boolean authResult = jsonResult.get("result").getAsBoolean();
            if (authResult) {
                System.out.println(jsonResult);
                JsonObject userData = jsonResult.get("body").getAsJsonObject();
                this.userId = userData.get("id").getAsString();
                this.userKey = userData.get("key").getAsString();
                this.userName = userData.get("name").getAsString();
                MyLogger.logger.info("Auth - Авторизация прошла успешно");
                return true;
            }
            MyLogger.logger.info("Auth - Авторизация не удалась");
            return false;

        }
        throw new EmptyAPIResponseException(mainApp, "Auth - получили пустой ответ от сервера");
    }

    /**
     * Получение всех чат-комнат пользователя
     * @return
     * @throws FalseServerFlagException
     * @throws EmptyAPIResponseException
     */
    @Override
    public List<Room> getUserRooms() throws FalseServerFlagException, EmptyAPIResponseException {


        //Список комнат, который метод отдаёт
        List<Room> resultList = new ArrayList<Room>();

        //Запрашиваем данные по URL
        String URL = String.format("%s/room/getByUser?key=%s", ServerURL, userKey);
        String response = HTTPRequest.sendGET(URL);
        //Если ответ есть
        if (response != null) {
            JsonObject jsonResult = JsonParser.parseString(response).getAsJsonObject();
            if (jsonResult.get("result").getAsBoolean()) {

                JsonArray bufList = jsonResult.get("body").getAsJsonArray();

                for (int i = 0; i < bufList.size(); i++) {
                    JsonObject currentRoom = bufList.get(i).getAsJsonObject();

                    String roomId = currentRoom.get("id").getAsString();
                    String creatorId = currentRoom.get("creatorId").getAsString();
                    String roomName = currentRoom.get("name").getAsString();

                    List<String> usersList = new ArrayList<>();
                    JsonArray usersArray  = currentRoom.get("users").getAsJsonArray();
                    for (int j = 0; j < usersArray.size(); j++) {
                        usersList.add(usersArray.get(j).getAsString());
                    }

                    int timeCreated = currentRoom.get("timeCreated").getAsInt();
                    Room room = new Room(creatorId,roomName,timeCreated,usersList,roomId);
                    resultList.add(room);
                }

                MyLogger.logger.info("getUserRooms - Получили список комнат для пользователя "+userId);
                return resultList;
            }
            else{
                throw new FalseServerFlagException(URL,response,"getUserRooms - Не удалось получить список комнат для пользователя "+userId);
            }
        }
        else{
            throw new EmptyAPIResponseException(mainApp, "getUserRooms - получили пустой ответ от сервера");
        }
    }

    /**
     * Получение объекта комнаты, в которой состоит пользователь, по её id
     * @param roomId
     * @return
     */
    @Override
    public Room getRoomInfo(String roomId) throws RoomNotFoundException, EmptyAPIResponseException {

        //Запрашиваем данные по URL
        String URL = String.format("%s/room/get?roomId=%s&key=%s", ServerURL, roomId, userKey);
        String response = HTTPRequest.sendGET(URL);
        //Если ответ есть
        if (response != null) {
            JsonObject jsonResult = JsonParser.parseString(response).getAsJsonObject();
            if (jsonResult.get("result").getAsBoolean()) {

                JsonObject currentRoom = jsonResult.get("body").getAsJsonObject();
                String creatorId = currentRoom.get("creatorId").getAsString();
                String roomName = currentRoom.get("name").getAsString();
                List<String> usersList = new ArrayList<>();
                JsonArray usersArray  = currentRoom.get("users").getAsJsonArray();
                for (int i = 0; i < usersArray.size(); i++) {
                    usersList.add(usersArray.get(i).getAsString());
                }
                int timeCreated = currentRoom.get("timeCreated").getAsInt();
                MyLogger.logger.info("getRoomInfo - отдали данные для комнаты с id "+roomId);
                return new Room(creatorId,roomName,timeCreated,usersList,roomId);
            }
            else {
                throw new RoomNotFoundException("getRoomInfo - Запрашиваемой комнаты с id "+roomId+" не существует");
            }
        }
        else {
            throw new EmptyAPIResponseException(mainApp, "getRoomInfo - получили пустой ответ от сервера");
        }

    }

    /**
     * Получение истории сообщений по конкретной комнате
     * @param roomId
     * @return
     */
    @Override
    public List<Message> getRoomMessagesHistory(String roomId) throws FalseServerFlagException, EmptyAPIResponseException {
        //Список сообщений, который метод отдаёт
        List<Message> resultList = new ArrayList<Message>();

        //Запрашиваем данные по URL
        String URL = String.format("%s/messages/get?roomId=%s&key=%s", ServerURL, roomId, userKey);
        String response = HTTPRequest.sendGET(URL);
        //Если ответ есть
        if (response != null) {
            JsonObject jsonResult = JsonParser.parseString(response).getAsJsonObject();
            if (jsonResult.get("result").getAsBoolean()) {

                JsonArray bufList = jsonResult.get("body").getAsJsonArray();

                for (int i = 0; i < bufList.size(); i++) {
                    JsonObject currentMessage = bufList.get(i).getAsJsonObject();

                    String messageId = currentMessage.get("id").getAsString();
                    String messageRoom = currentMessage.get("roomId").getAsString();
                    String messageText = currentMessage.get("text").getAsString();
                    Integer messageTimeCreated = currentMessage.get("timeCreated").getAsInt();
                    String messageUserId = currentMessage.get("userId").getAsString();
                    String messageUserName = currentMessage.get("userName").getAsString();

                    Message bufMessage = new Message(messageUserId, messageUserName, messageText, messageRoom, messageTimeCreated, messageId );
                    resultList.add(bufMessage);
                }
                MyLogger.logger.info("getRoomMessagesHistory - Получили список сообщений для комнаты "+roomId);
                return resultList;
            }
            else {
                throw new FalseServerFlagException(URL, response, "getRoomMessagesHistory - Не удалось получить список сообщений для комнаты " + roomId);
            }
        }
        else{
            throw new EmptyAPIResponseException(mainApp, "getRoomMessagesHistory - Получили пустой ответ от сервера");
        }
    }

    /**
     * Создание комнаты
     * @param roomName
     * @param usersString
     * @return
     */
    @Override
    public boolean createRoom(String roomName, String usersString) throws FalseServerFlagException, EmptyAPIResponseException {
        //Запрашиваем данные по URL
        String URL = String.format("%s/room/create", ServerURL);

        Map<String,String> params = new HashMap<>();
        params.put("users", usersString);
        params.put("roomName", roomName);
        params.put("key", userKey);

        String response = HTTPRequest.sendPOST(URL, params);

        //Если ответ есть
        if (response != null) {
            JsonObject jsonResult = JsonParser.parseString(response).getAsJsonObject();
            if (jsonResult.get("result").getAsBoolean()) {
                MyLogger.logger.info("createRoom - Успешно создали новую комнату");
                return  true;
            }
            else {
                throw new FalseServerFlagException(URL, response, "createRoom - Не удалось создать новую комнату");
            }
        }
        else{
            throw new EmptyAPIResponseException(mainApp, "createRoom - Получили пустой ответ от сервера");
        }
    }

    /**
     * Поиск пользователей в системе
     *
     * @param searchString
     * @return
     */

    @Override
    public List<User> getUsers(String searchString) throws FalseServerFlagException, EmptyAPIResponseException {

        List<User> resultList = new ArrayList<User>();
        String URL = "";
        if (searchString == null){
            URL = String.format("%s/user/search?key=%s", ServerURL, userKey);
        }
        else{
            URL = String.format("%s/user/search?key=%s&searchName=%s", ServerURL, userKey, searchString);
        }
        String response = HTTPRequest.sendGET(URL);
        //Если ответ есть
        if (response != null) {
            JsonObject jsonResult = JsonParser.parseString(response).getAsJsonObject();
            if (jsonResult.get("result").getAsBoolean()) {

                JsonArray bufList = jsonResult.get("body").getAsJsonArray();
                for (int i = 0; i < bufList.size(); i++) {
                    JsonObject currentUser = bufList.get(i).getAsJsonObject();

                    String userId = currentUser.get("id").getAsString();
                    String userName = currentUser.get("name").getAsString();
                    Integer userTimeCreated = currentUser.get("timeCreated").getAsInt();

                    User bufUser = new User(userId, userName, userTimeCreated);
                    resultList.add(bufUser);
                }
                MyLogger.logger.info("getUsers - получили "+resultList.size()+" пользователей");
                return resultList;
            }
            else{
                throw  new FalseServerFlagException(URL,response, "getUsers - Не удалось отправить новое сообщение");
            }
        }
        else {
            throw  new EmptyAPIResponseException(mainApp, "getUsers - получили пустой ответ от сервера");
        }
    }

    @Override
    public Message writeMessage(String text) throws FalseServerFlagException, EmptyAPIResponseException {

        //Запрашиваем данные по URL
        String URL = String.format("%s/messages/send", ServerURL);

        Map<String,String> params = new HashMap<>();
        params.put("key", userKey);
        params.put("text", text);
        params.put("roomId", currentRoomId);

        String response = HTTPRequest.sendPOST(URL, params);
        //Если ответ есть
        if (response != null) {
            JsonObject jsonResult = JsonParser.parseString(response).getAsJsonObject();
            if (jsonResult.get("result").getAsBoolean()) {

                JsonObject newMessageJsonObject = jsonResult.get("body").getAsJsonObject();

                    String messageId = newMessageJsonObject.get("id").getAsString();
                    String roomId = newMessageJsonObject.get("roomId").getAsString();
                    String messageText = newMessageJsonObject.get("text").getAsString();
                    Integer timeCreated = newMessageJsonObject.get("timeCreated").getAsInt();
                    String messageUserId = newMessageJsonObject.get("userId").getAsString();
                    String messageUserName = newMessageJsonObject.get("userName").getAsString();

                    MyLogger.logger.info("writeMessage - Отправили новое сообщение "+messageText +" "+messageId);
                    return new Message(messageUserId, messageUserName, messageText,roomId,timeCreated,messageId);
            }

            else{
                throw  new FalseServerFlagException(URL,response, "writeMessage - Не удалось отправить новое сообщение");
            }

        }
        else {
            throw  new EmptyAPIResponseException(mainApp, "writeMessage - получили пустой ответ от сервера");
        }

    }

    /**
     * Получение LongPoll'а
     * Выставляет longpollTs, longpollSubUrl, longpollKey
     * @throws EmptyAPIResponseException
     * @throws FalseServerFlagException
     */
    @Override
    public void getLongpollServer() throws EmptyAPIResponseException, FalseServerFlagException {
        //Запрашиваем данные по URL
        String URL = String.format("%s/longpoll/getServer?key=%s", ServerURL, userKey);
        String response = HTTPRequest.sendGET(URL);
        //Если ответ есть
        if (response != null) {
            JsonObject jsonResult = JsonParser.parseString(response).getAsJsonObject();
            if (jsonResult.get("result").getAsBoolean()) {

                JsonObject credentials = jsonResult.get("body").getAsJsonObject();

                this.longpollTs = credentials.get("ts").getAsString();
                this.longpollSubUrl = credentials.get("url").getAsString();
                this.longpollKey = credentials.get("key").getAsString();
                MyLogger.logger.info("getLongpollServer - получили конфиг, инициализировались");
            }
            else {
                throw new FalseServerFlagException(URL,response,"getLongpollServer - Не удалось получить конфиг, не инициализировались");
            }
        }
        else {
            throw new EmptyAPIResponseException(mainApp, "getLongpollServer - Получили пустой ответ от сервера");
        }
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
            String URL = String.format("%s/longpoll/updates/%s?key=%s&ts=%s", ServerURL, longpollSubUrl, longpollKey, longpollTs);
            MyLogger.logger.info("longpollListener - отправили запрос, ожидаем новые сообщения..");
            String response = HTTPRequest.sendGET(URL);

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

                        String messageId = currentMessage.get("id").getAsString();
                        String messageRoom = currentMessage.get("roomId").getAsString();
                        String messageText = currentMessage.get("text").getAsString();
                        Integer messageTimeCreated = currentMessage.get("timeCreated").getAsInt();
                        String messageUserId = currentMessage.get("userId").getAsString();
                        String messageUserName = currentMessage.get("userName").getAsString();

                        Message bufMessage = new Message(messageUserId, messageUserName, messageText, messageRoom, messageTimeCreated, messageId);
                        resultList.add(bufMessage);
                    }
                    MyLogger.logger.info("longpollListener - получили новые сообщения");
                }
                else{
                    throw new LongpollListenerException("Ответ result = false. Нужна повторная авторизация (или же мы передали что-то некорректно");
                }
            }
            else{
                throw new LongpollListenerException("Лонгпул ничего не получил от сервера. Что-то сломалось");
            }

            return resultList;
        }
    }


    public boolean getIsAuthenticated(){
        return isAuthenticated;
    }

    public String getCurrentRoomId() {
        return currentRoomId;
    }

    public String getUserName() {
        return userName;
    }

    public void setCurrentRoomId(String currentRoomId) {
        this.currentRoomId = currentRoomId;
    }
}


