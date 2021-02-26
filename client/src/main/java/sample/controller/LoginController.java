package sample.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.Main;
import sample.MyLogger;

import java.io.IOException;

public class LoginController {

    @FXML
    private Label AppName;
    @FXML
    private JFXTextField LoginTextField;
    @FXML
    private JFXTextField PasswordTextField;

    private Main mainApp;

    @FXML
    public void mainButtonClick() throws IOException {
        //alert.initOwner(root.getPrimaryStage );

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main.fxml"));
        Parent root = loader.load();
        LoginController controller = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("ЧАТ");
        stage.show();

        MyLogger.logger.info("Нажатие на button авторизации в программе");
    }

    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
    }



}
