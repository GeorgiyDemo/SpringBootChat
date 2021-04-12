package org.demka;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.demka.api.MyAPI;
import org.demka.controllers.*;
import org.demka.utils.AuthUtil;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private MyAPI myAPI;
    private AuthUtil authUtil;

    /**
     * Точка входа в программу
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        myStart(primaryStage);
    }

    /**
     * Метод для отрисовки окна при инициализации/перезапуске.
     * Нужен отдельный метод из-за того, что при отсутствии интернет-соединения
     * и последующем его появлении, необходимо перезапустить приложение
     *
     * @param primaryStage
     */
    public void myStart(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("DEMKAChat - Авторизация");
        this.authUtil = new AuthUtil();
        initRootLayout();
        //Читаем токен из файла
        String key = authUtil.readKey();
        if (key != null) {
            myAPI = new MyAPI(key, this);
        }

        //Если пользователь не авторизован
        if (!myAPI.getIsAuthenticated()) {
            authUtil.writeKey("");
            UserAuthorisation();
        }
        //Если уже успешно авторизовался
        else {
            this.primaryStage.setTitle("DEMKAChat - Сообщения [" + myAPI.getUserName() + "]");
            MainChat();
        }
    }

    /**
     * Логика авторизации в программе
     */
    public void UserAuthorisation() {

        try {
            this.primaryStage.setTitle("DEMKAChat - Авторизация");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/LoginView.fxml"));
            AnchorPane mainPage = loader.load();
            rootLayout.setCenter(mainPage);
            LoginController controller = loader.getController();
            controller.initialize(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Показывает окно регистрации в программе
     */
    public void UserRegistration() {
        try {
            this.primaryStage.setTitle("DEMKAChat - Регистрация");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/RegistrationView.fxml"));
            AnchorPane mainPage = loader.load();
            rootLayout.setCenter(mainPage);
            RegistrationController controller = loader.getController();
            controller.initialize(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Показывает окно смены пароля в программе
     */
    public void ForgotPassword() {
        try {
            this.primaryStage.setTitle("DEMKAChat - Забыли пароль");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/ForgotPasswordView.fxml"));
            AnchorPane mainPage = loader.load();
            rootLayout.setCenter(mainPage);
            ForgotPasswordController controller = loader.getController();
            controller.initialize(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Динамическое окно успешного действия пользователя
     *
     * @param windowTitle - заголовок показываемого окна
     * @param mainText    - текст, который отображается на форме
     */
    public void SuccessUserAction(String windowTitle, String mainText) {
        try {
            this.primaryStage.setTitle("DEMKAChat - " + windowTitle);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/SuccessActionView.fxml"));
            AnchorPane mainPage = loader.load();
            rootLayout.setCenter(mainPage);
            SuccessActionController controller = loader.getController();
            controller.setMainText(mainText);
            controller.initialize(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Показывает информацию о программе и авторе
     */
    public void AboutMe() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/AboutMeView.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("О программе");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            AboutMeController controller = loader.getController();
            controller.initialize(this, dialogStage);
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Основное окно с чатами
     */
    public void MainChat() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/MainChatView.fxml"));
            AnchorPane mainPage = loader.load();
            rootLayout.setCenter(mainPage);
            MainChatController controller = loader.getController();
            controller.initialize(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Логика ошибки соединения
     */
    public void ConnectionError() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/ConnectionErrorView.fxml"));
            AnchorPane mainPage = loader.load();
            rootLayout.setCenter(mainPage);
            ConnectionErrorController controller = loader.getController();
            controller.initialize(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Окно создания новой комнаты
     */
    public void NewRoom() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/CreateNewRoomView.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Создание диалога");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            CreateNewRoomController controller = loader.getController();
            controller.initialize(this, dialogStage);
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Показывает участников текущей комнаты
     */
    public void showCurrentRoomUsers() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/ShowChatUsersView.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            ShowChatUsersController controller = loader.getController();
            controller.initialize(this, dialogStage);
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Базовый Layout
     */
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/RootLayoutView.fxml"));
            rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            RootLayoutController controller = loader.getController();
            controller.initialize(this);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MyAPI getMyAPI() {
        return myAPI;
    }

    public void setMyAPI(MyAPI myAPI) {
        this.myAPI = myAPI;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public AuthUtil getAuthUtil() {
        return authUtil;
    }

}