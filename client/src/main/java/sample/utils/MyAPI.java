package sample.utils;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sample.models.Room;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class MyAPI {

    private String userName;
    private String userKey;
    private String userId;
    private String currentRoomId;

    private String longpollTs;
    private String longpollSubUrl;
    private String longpollKey;

    private boolean isAuthenticated;
    private final static String ServerURL = "http://127.0.0.1:5000";

    public MyAPI(String login, String password) {
        isAuthenticated = this.Auth(login, password);
        MyLogger.logger.info("Инициализировали MyAPI");
    }

    /*
    Регистрация пользователя
     */
    public static void Registration(String name, String login, String password) {
        login = URLEncoder.encode(login, StandardCharsets.UTF_8);
        password = URLEncoder.encode(password, StandardCharsets.UTF_8);

        String URL = String.format("%s/register?name=%s&login=%s&password=%s", ServerURL, name, login, password);
        String response = HTTPRequest.Get(URL);
        System.out.println(response);
    }

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
        MyLogger.logger.info("Auth - получили пустой ответ от сервера");
        return false;
    }

    /**
     * Получение чат-комнат пользователя
     * @return
     */
    public List<Room> getUserRooms(){

        Gson gson = new Gson();

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
                    System.out.println(bufList.get(i));
                    Room room = gson.fromJson(bufList.get(i), Room.class);
                    resultList.add(room);
                }
                MyLogger.logger.info("getUserRooms - Получили список комнат для пользователя "+userId);
                return resultList;
            }

            MyLogger.logger.info("getUserRooms - Не удалось получить список комнат для пользователя "+userId);
            return resultList;

        }

        MyLogger.logger.info("getUserRooms - получили пустой ответ от сервера");
        return resultList;

    }

    public boolean getIsAuthenticated(){
        return isAuthenticated;
    }
}


