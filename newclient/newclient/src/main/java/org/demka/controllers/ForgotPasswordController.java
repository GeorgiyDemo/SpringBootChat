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
 * Контроллер-обработчик формы восстановления пароля
 */
public class ForgotPasswordController extends SuperFullController {

    private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);
    @FXML
    JFXPasswordField newPasswordTextField;
    @FXML
    JFXPasswordField masterKeyTextField;
    @FXML
    JFXTextField loginTextField;
    @FXML
    Label errorDescription;
    private String errorString;

    /**
     * Метод инициализации (вызывается с Main)
     *
     * @param app
     */
    @Override
    public void initialize(App app) {
        errorDescription.setOpacity(0.0);
        this.app = app;
    }

    /**
     * Кнопка выхода из смены пароля
     */
    @FXML
    private void backButtonClicked() {
        app.UserAuthorisation();
        logger.info("Выход из формы смены пароля");
    }

    /**
     * Кнопка сброса пароля
     */
    @FXML
    private void resetButtonClicked() {
        logger.info("Нажатие на button сброса пароля");

        String login = loginTextField.getText();
        String newPassword = newPasswordTextField.getText();
        String masterKey = masterKeyTextField.getText();

        if (dataValidator(login, newPassword, masterKey)) {
            logger.info("Валидация данных со стороны клиента прошла успешно");
            //Получаем ответ от сервера
            Map<String, Object> regResult = SuperAPI.resetPassword(login, newPassword, masterKey);
            if ((boolean) regResult.get("result")) {
                errorDescription.setOpacity(0.0);
                errorDescription.setText("");
                logger.info("Успешно сменили пароль пользователя");
                app.SuccessUserAction("Успешная смена пароля", "Успешно сменили пароль!");
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
     * Фильтрация входных данных для смены пароля
     *
     * @param email     - e-mail пользвателя
     * @param password  - новый пароль пользователя
     * @param masterKey - мастер-ключ пользователя
     * @return
     */
    private Boolean dataValidator(String email, String password, String masterKey) {

        if ((email == null) || (email.equals(""))) {
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

        return true;
    }
}
