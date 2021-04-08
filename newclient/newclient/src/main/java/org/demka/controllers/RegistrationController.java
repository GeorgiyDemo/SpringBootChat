package org.demka.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import org.demka.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistrationController extends SuperFullController {

    @FXML
    private JFXButton BackButton;
    @FXML
    private JFXButton RegButton;

    @FXML
    private JFXTextField UserNameTextField;
    @FXML
    private JFXTextField EmailTextField;
    @FXML
    private JFXPasswordField PasswordTextField;

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private String regErrorString;

    private Boolean dataValidator(String userName, String email, String password){
        //Пароль должен быть не меньше 8 символов
        if ((userName == null) || (userName.equals(""))){
            regErrorString = "Ник не может быть пустым";
            return false;
        }

        if (userName.length() > 14){
            regErrorString = "Ник слишком длинный";
            return false;
        }

        if ((email == null) || (email.equals(""))) {
            regErrorString = "E-mail не может быть пустым";
            return false;
        }

        if (())


    }

    @FXML
    private void regButtonClicked(){
        logger.info("Нажатие на button регистрации");
        String userName = UserNameTextField.getText();
        String userEmail = EmailTextField.getText();
        String userPassword = PasswordTextField.getText();
        if (dataValidator(userName, userEmail, userPassword)){
            logger.info("Валидация данных со стороны клиента прошла успешно");

        }
        else{
            logger.info("Валидация данных со стороны клиента не прошла успешно");
        }

    }

    @FXML
    private void backButtonClicked(){
        logger.info("Возврат в меню авторизации");
        mainApp.UserAuthorisation();
    }

    /**
     * Метод инициализации (вызывается с Main)
     * @param mainApp
     */
    @Override
    public void initialize(App mainApp) {
        this.mainApp = mainApp;
    }
}
