package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import sample.controller.LoginController;
import sample.controller.RegController;
import sample.controller.RootLayoutController;
import sample.models.ChatRoom;

import java.io.IOException;


public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("DEMKA Messenger");
        initRootLayout();
        //TODO: Проверка на всякое барахло по типу существующей авторизации и т д
        Authorisation();
    }

    //TODO ЧТО НЕ ТАК?
    public void Authorisation() {

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/login.fxml"));
            AnchorPane mainPage = (AnchorPane) loader.load();
            rootLayout.setCenter(mainPage);
            LoginController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Registration(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/registration.fxml"));
            AnchorPane persons = (AnchorPane) loader.load();

            rootLayout.setCenter(persons);

            RegController controller = loader.getController();
            controller.setMainApp(this);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     * Базовый Layout
     */
    public void initRootLayout(){

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
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

    /**
     * Точка входа в программу
     * @param args
     */
    public static void main(String[] args) {
        MyLogger log = new MyLogger();
        if (log.deploy())
            launch(args);
    }

}
