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
import org.demka.utils.ComputerIdentifier;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private MyAPI APISession;
    private AuthUtil authUtil;

    /**
     * Точка входа в программу
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void setAPISession(MyAPI APISession) {
        this.APISession = APISession;
    }

    public MyAPI getAPISession() {
        return APISession;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }

    public AuthUtil getAuthUtil() {
        return authUtil;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Выставляем RootLayout
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Авторизация");
        System.out.println(ComputerIdentifier.generateLicenseKey());
        this.authUtil = new AuthUtil();
        initRootLayout();
        //Читаем токен из файла
        String key = authUtil.readKey();
        if (key != null){
            APISession = new MyAPI(key, this);
        }

        //Если пользователь не авторизован
        if  (!APISession.getIsAuthenticated()){
            authUtil.writeKey("");
            UserAuthorisation();
        }
        //Если уже успешно авторизовался
        else {
            this.primaryStage.setTitle("Сообщения ["+APISession.getUserName()+"]");
            MainChat();
        }

    }

    /**
     * Логика авторизации в программе
     */
    public void UserAuthorisation() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/LoginView.fxml"));
            AnchorPane mainPage = loader.load();
            rootLayout.setCenter(mainPage);
            AuthorisationController controller = loader.getController();
            controller.initialize(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Логика регистрации в программе
     */
    public void UserRegistration() {
        try {
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
    public void NewRoom(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/CreateNewRoomView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Создание диалога");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            CreateNewRoomController controller = loader.getController();
            controller.initialize(this, dialogStage);
            dialogStage.showAndWait();;

        }catch(IOException e){
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

}