package sample.utils;


import java.io.IOException;


public class APIRequest {

    private final static String ServerURL = "http://127.0.0.1";

    private String userName;
    private String userKey;
    private String userId;
    private String currentRoomId;

    private String longpollTs;
    private String longpollSubUrl;
    private String longpollKey;

    public APIRequest(String login, String password) {

        this.Auth(login, password);
        System.out.printf("Авторизация пользователя {self.username} прошла успешно");
    }

    /*
    Регистрация пользователя
     */
    public static void Registration(String name, String login, String password) {
        String URL = String.format("%s/register?name=%s&login=%s&password=%s", ServerURL, name, login, password);
        try {
            String response = HTTPRequest.Get(URL);
            System.out.printf(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean Auth(String login, String passwd) {
        String URL = String.format("MEOW");

        /*
        r = requests.get(f"{self.URL}/auth?login={LOGIN}&password={PASSWORD}").json()
        print(r)
        if not r["result"]:
        raise ValueError("Не удалось авторизоваться")

        self.user_id = r["user_id"]
        self.user_key = r["key"]
        self.username = r["name"]

         */
        return true;

    }
}
