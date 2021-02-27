package sample.utils;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sample.models.ChatRoom;

import java.io.IOException;
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
        System.out.printf(response);
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
                this.userId = jsonResult.get("user_id").getAsString();
                this.userKey = jsonResult.get("user_key").getAsString();
                this.userName = jsonResult.get("user_name").getAsString();
                MyLogger.logger.info("AUTH - Авторизация прошла успешно");
                return true;
            }
            MyLogger.logger.info("AUTH - Авторизация не удалась");
            return false;

        }
        MyLogger.logger.info("AUTH - получили пустой ответ от сервера");
        return false;
    }

    public List<ChatRoom> getUserRooms(){

        List<ChatRoom> resultList = new ArrayList<ChatRoom>();
        String URL = String.format("%s/getUserRooms?user_id=%s&key=%s", ServerURL, userId, userKey);
        System.out.println(URL);
        String response = HTTPRequest.Get(URL);
        if (response != null) {
            JsonObject jsonResult = JsonParser.parseString(response).getAsJsonObject();
            if (jsonResult.get("result").getAsBoolean()) {
                System.out.println(jsonResult);

            }
            MyLogger.logger.info("AUTH - Авторизация не удалась");

        }
        MyLogger.logger.info("AUTH - получили пустой ответ от сервера");


    return resultList;
    }

    public boolean getIsAuthenticated(){
        return isAuthenticated;
    }
}


