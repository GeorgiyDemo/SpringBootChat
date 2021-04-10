package org.demka.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.demka.App;
import org.demka.api.SuperAPI;
import org.demka.utils.Validators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ForgotPasswordController extends SuperFullController {

    @FXML
    JFXPasswordField NewPasswordTextField;

    @FXML
    JFXPasswordField MasterKeyTextField;

    @FXML
    JFXTextField LoginTextField;

    @FXML
    Label ErrorDescription;

    private String errorString;
    private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);

    /**
     * Метод инициализации (вызывается с Main)
     *
     * @param mainApp
     */
    @Override
    public void initialize(App mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void backButtonClicked(){
        mainApp.UserAuthorisation();
        logger.info("Выход из формы смены пароля");
    }

    @FXML
    private void resetButtonClicked(){
        logger.info("Нажатие на button сброса пароля");

        String login = LoginTextField.getText();
        String newPassword = NewPasswordTextField.getText();
        String masterKey = MasterKeyTextField.getText();

        if (dataValidator(login, newPassword, masterKey)){
            logger.info("Валидация данных со стороны клиента прошла успешно");
            //Получаем ответ от сервера
            Map<String,Object> regResult = SuperAPI.ResetPassword(login, newPassword, masterKey);
            if ((boolean) regResult.get("result")){
                ErrorDescription.setOpacity(0.0);
                ErrorDescription.setText("");
                logger.info("Успешно сменили пароль пользователя");
                mainApp.SuccessUserRegistration();
            }
            else{
                errorString = (String) regResult.get("error");
                ErrorDescription.setText(errorString);
                logger.info("Валидация со стороны сервера не прошла: "+errorString);
                ErrorDescription.setOpacity(1.0);
            }

        }
        else{
            logger.info("Валидация данных со стороны клиента не прошла: "+errorString);
            ErrorDescription.setText(errorString);
            ErrorDescription.setOpacity(1.0);
        }


    }

    private Boolean dataValidator(String eMail, String password, String masterKey){


        if ((eMail == null) || (eMail.equals(""))) {
            errorString = "E-mail не может быть пустым";
            return false;
        }

        if ((password == null) || (password.equals(""))) {
            errorString = "Новый пароль не может быть пустым";
            return false;
        }

        if ((masterKey == null) || (masterKey.equals(""))) {
            errorString = "Мастер-пароль не может быть пустым";
            return false;
        }

        if (!Validators.emailValidator(eMail)){
            errorString = "Некорректный e-mail";
            return false;
        }

        if (password.length() < 8){
            errorString = "Пароль должен быть не менее 8 символов";
            return false;
        }

        if (masterKey.length() < 8){
            errorString = "Мастер-пароль должен быть не менее 8 символов";
            return false;
        }

        return true;
    }
}
