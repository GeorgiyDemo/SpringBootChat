package org.demka.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.demka.App;
import org.demka.api.MyAPI;
import org.demka.api.SuperAPI;
import org.demka.utils.String2Hash;
import org.demka.utils.Validators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

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

    @FXML
    private Label ErrorDescription;

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private String regErrorString;

    private Boolean dataValidator(String userName, String email, String password){

        //Проверка на пустоту
        if ((userName == null) || (userName.equals(""))){
            regErrorString = "Ник не может быть пустым";
            return false;
        }

        if ((email == null) || (email.equals(""))) {
            regErrorString = "E-mail не может быть пустым";
            return false;
        }

        if ((password == null) || (password.equals(""))) {
            regErrorString = "Пароль не может быть пустым";
            return false;
        }


        if (userName.length() > 14){
            regErrorString = "Ник слишком длинный";
            return false;
        }

        if (!Validators.emailValidator(email)){
            regErrorString = "Некорректный e-mail";
            return false;
        }

        if (password.length() < 8){
            regErrorString = "Пароль должен быть не менее 8 символов";
            return false;
        }
        return true;
    }

    @FXML
    private void regButtonClicked(){
        logger.info("Нажатие на button регистрации");
        String userName = UserNameTextField.getText();
        String userEmail = EmailTextField.getText();
        String userPassword = PasswordTextField.getText();
        if (dataValidator(userName, userEmail, userPassword)){
            logger.info("Валидация данных со стороны клиента прошла успешно");
            //Получаем ответ от сервера
            Map<String,Object> regResult = SuperAPI.Registration(userName, userEmail, userPassword);
            if ((boolean) regResult.get("result")){
                ErrorDescription.setOpacity(0.0);
                ErrorDescription.setText("");
                logger.info("Успешно зарегистрировали пользователя");
                mainApp.SuccessUserRegistration();
            }
            else{
                regErrorString = (String) regResult.get("error");
                ErrorDescription.setText(regErrorString);
                logger.info("Валидация со стороны сервера не прошла: "+regErrorString);
                ErrorDescription.setOpacity(1.0);
            }

        }
        else{
            logger.info("Валидация данных со стороны клиента не прошла: "+regErrorString);
            ErrorDescription.setText(regErrorString);
            ErrorDescription.setOpacity(1.0);
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
        ErrorDescription.setOpacity(0.0);
    }
}
