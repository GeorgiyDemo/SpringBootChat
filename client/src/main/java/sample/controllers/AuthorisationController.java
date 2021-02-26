package sample.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sample.utils.MyAPI;
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

        MyLogger.logger.info("Нажатие на button авторизации в программе");
        String login = LoginTextField.getText();
        String password = PasswordTextField.getText();

        if ((login.equals("")) || (password.equals(""))){
            MyLogger.logger.info("Попытка входа без ввода данных");
        }
        else{
            //Пытаемся авторизоваться
            MyAPI bufSession = new MyAPI(login,password);
            //Если удалось произвести авторизацию
            if (bufSession.getIsAuthenticated()) {
                mainApp.setAPISession(bufSession);
                mainApp.MainChat();
                MyLogger.logger.info("Пользователь успешно авторизовался");
            }
            else{
                MyLogger.logger.info("Не удалось авторизоваться");
            }


        }

    }
}
