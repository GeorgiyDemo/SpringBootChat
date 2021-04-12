package org.demka.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.demka.App;
import org.demka.api.SuperAPI;
import org.demka.utils.ValidatorsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Контроллер-обработчик регистрации пользователя
 */
public class RegistrationController extends SuperFullController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @FXML
    private JFXTextField userNameTextField;
    @FXML
    private JFXTextField emailTextField;
    @FXML
    private JFXPasswordField passwordTextField;
    @FXML
    private JFXPasswordField masterKey;
    @FXML
    private Label errorDescription;
    private String errorString;

    /**
     * Валидация данных регистрации пользователя
     *
     * @param userName  - ник пользователя
     * @param email     - e-mail пользователя
     * @param password  - пароль пользователя
     * @param masterKey - мастер-ключ пользователя
     * @return
     */
    private Boolean dataValidator(String userName, String email, String password, String masterKey) {

        //Проверка на пустоту
        if ((userName == null) || (userName.equals(""))) {
            errorString = "Ник не может быть пустым";
            return false;
        }

        if ((email == null) || (email.equals(""))) {
            errorString = "E-mail не может быть пустым";
            return false;
        }

        if ((password == null) || (password.equals(""))) {
            errorString = "Пароль не может быть пустым";
            return false;
        }

        if ((masterKey == null) || (masterKey.equals(""))) {
            errorString = "Мастер-пароль не может быть пустым";
            return false;
        }

        if (userName.length() > 14) {
            errorString = "Ник слишком длинный";
            return false;
        }

        if (!ValidatorsUtil.emailValidator(email)) {
            errorString = "Некорректный e-mail";
            return false;
        }

        if (password.length() < 8) {
            errorString = "Пароль должен быть не менее 8 символов";
            return false;
        }

        if (masterKey.length() < 8) {
            errorString = "Мастер-пароль должен быть не менее 8 символов";
            return false;
        }

        if (masterKey.equals(password)) {
            errorString = "Пароль и мастер-пароль не болжны совпадать";
            return false;
        }

        return true;
    }

    /**
     * Обработчик нажатия на кнопку регистрации
     */
    @FXML
    private void regButtonClicked() {
        logger.info("Нажатие на button регистрации");
        String userName = userNameTextField.getText();
        String userEmail = emailTextField.getText();
        String userPassword = passwordTextField.getText();
        String masterKey = this.masterKey.getText();
        if (dataValidator(userName, userEmail, userPassword, masterKey)) {
            logger.info("Валидация данных со стороны клиента прошла успешно");
            //Получаем ответ от сервера
            Map<String, Object> regResult = SuperAPI.registration(userName, userEmail, userPassword, masterKey);

            if ((boolean) regResult.get("result")) {
                errorDescription.setOpacity(0.0);
                errorDescription.setText("");
                logger.info("Успешно зарегистрировали пользователя");
                app.SuccessUserAction("Успешная регистрация", "Регистрация прошла успешно!");
            } else {
                errorString = (String) regResult.get("error");
                errorDescription.setText(errorString);
                logger.info("Валидация со стороны сервера не прошла: " + errorString);
                errorDescription.setOpacity(1.0);
            }

        } else {
            logger.info("Валидация данных со стороны клиента не прошла: " + errorString);
            errorDescription.setText(errorString);
            errorDescription.setOpacity(1.0);
        }


    }


    /**
     * Обработчик нажатия на кнопку возврата на форму авторизации
     */
    @FXML
    private void backButtonClicked() {
        logger.info("Возврат в меню авторизации");
        app.UserAuthorisation();
    }

    /**
     * Метод инициализации (вызывается с Main)
     *
     * @param app
     */
    @Override
    public void initialize(App app) {
        this.app = app;
        errorDescription.setOpacity(0.0);
    }
}
