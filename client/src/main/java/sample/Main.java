package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.controllers.LoginController;
import sample.controllers.MainChatController;
import sample.controllers.RegController;
import sample.controllers.RootLayoutController;
import sample.utils.MyLogger;

import java.io.IOException;


public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

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

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("DEMKA Messenger");
        initRootLayout();
        //TODO: Проверка на всякое барахло по типу существующей авторизации и т д
        Authorisation();
    }

    /**
     * Логика авторизации в программе
     */
    public void Authorisation() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/login.fxml"));
            AnchorPane mainPage = loader.load();
            rootLayout.setCenter(mainPage);
            LoginController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Логика регистрации в программе
     */
    public void Registration() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/registration.fxml"));
            AnchorPane mainPage = loader.load();
            rootLayout.setCenter(mainPage);
            RegController controller = loader.getController();
            controller.setMainApp(this);
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
            loader.setLocation(getClass().getResource("/views/MainChat.fxml"));
            AnchorPane mainPage = loader.load();
            rootLayout.setCenter(mainPage);
            MainChatController controller = loader.getController();
            controller.setMainApp(this);
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
            loader.setLocation(getClass().getResource("/views/RootLayout.fxml"));
            rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
