package sample.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sample.utils.MyLogger;

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
