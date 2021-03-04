package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.api.MyAPI;
import sample.controllers.*;
import sample.utils.MyLogger;

import java.io.IOException;


public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private MyAPI APISession;

    /**
     * Точка входа в программу
     *
     * @param args
     */
    public static void main(String[] args) {
        MyLogger log = new MyLogger();
        if (log.deploy())
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

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Выставляем RootLayout
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("DEMKA Messenger");
        initRootLayout();

        //TODO: ЭТО ХАРДКОД, ПОТОМ ВЫНЕСТИ В SQLITE или куда-то
        //TODO: Проверка на всякое барахло по типу существующей авторизации и т д
        String BUFFLOGIN = "demka@mail.ru";
        String BUFFPASSWRD = "3845";
        APISession = new MyAPI(BUFFLOGIN,BUFFPASSWRD, this);

        //Если пользователь не авторизован
        if  (!APISession.getIsAuthenticated()){
            UserAuthorisation();
        }
        //Если уже успешно авторизовался
        else {
            MainChat();
        }

    }

    /**
     * Логика авторизации в программе
     */
    public void UserAuthorisation() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/LoginView.fxml"));
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
            loader.setLocation(getClass().getResource("/views/RegistrationView.fxml"));
            AnchorPane mainPage = loader.load();
            rootLayout.setCenter(mainPage);
            RegController controller = loader.getController();
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
            loader.setLocation(getClass().getResource("/views/MainChatView.fxml"));
            AnchorPane mainPage = loader.load();
            rootLayout.setCenter(mainPage);
            MainChatController controller = loader.getController();
            controller.initialize(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ConnectionError() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/ConnectionErrorView.fxml"));
            AnchorPane mainPage = loader.load();
            rootLayout.setCenter(mainPage);
            ConnectionErrorController controller = loader.getController();
            controller.initialize(this);
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
            loader.setLocation(getClass().getResource("/views/RootLayoutView.fxml"));
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
