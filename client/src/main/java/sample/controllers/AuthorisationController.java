package sample.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sample.Main;
import sample.api.MyAPI;
import sample.utils.AuthUtil;
import sample.utils.MyLogger;

import java.io.IOException;

public class AuthorisationController extends SuperFullController {

    @FXML
    private Label AppName;
    @FXML
    private Label WrongAuth;
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
            MyAPI bufSession = new MyAPI(login, password, this.mainApp);
            //Если удалось произвести авторизацию
            if (bufSession.getIsAuthenticated()) {
                mainApp.setAPISession(bufSession);
                //TODO: Если пользователь жмякнул ЗАПОМНИТЬ МЕНЯ
                if (true) {
                    AuthUtil authUtil = mainApp.getAuthUtil();
                    authUtil.writeKey(bufSession.getUserKey());
                }
                mainApp.MainChat();
                MyLogger.logger.info("Пользователь успешно авторизовался");
            }
            else{
                WrongAuth.setOpacity(1);
                MyLogger.logger.info("Не удалось авторизоваться");
            }


        }

    }

    /**
     * Метод инициализации (вызывается с Main)
     * @param mainApp
     */
    @Override
    public void initialize(Main mainApp) {
        this.mainApp = mainApp;
    }
}
