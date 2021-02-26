package sample.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sample.utils.MyLogger;

import java.io.IOException;

public class AuthorisationController extends SuperController {

    @FXML
    private Label AppName;
    @FXML
    private JFXTextField LoginTextField;
    @FXML
    private JFXTextField PasswordTextField;

    @FXML
    public void mainButtonClick() throws IOException {
        if ((LoginTextField.getText().equals("")) || (PasswordTextField.getText().equals(""))){
            MyLogger.logger.info("Попытка входа без ввода данных!");
        }
        else{
            mainApp.MainChat();
            MyLogger.logger.info("Нажатие на button авторизации в программе");
        }

    }
}
