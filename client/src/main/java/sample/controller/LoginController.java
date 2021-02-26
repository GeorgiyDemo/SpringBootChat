package sample.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.MyLogger;

import java.io.IOException;

public class LoginController extends SuperController {

    @FXML
    private Label AppName;
    @FXML
    private JFXTextField LoginTextField;
    @FXML
    private JFXTextField PasswordTextField;

    @FXML
    public void mainButtonClick() throws IOException {
        //alert.initOwner(root.getPrimaryStage );
        mainApp.MainChat();

        MyLogger.logger.info("Нажатие на button авторизации в программе");
    }
}
