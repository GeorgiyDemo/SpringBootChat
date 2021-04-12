package org.demka.controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.demka.App;
import org.demka.api.MyAPI;
import org.demka.utils.AuthUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Контроллер-обработчик формы авторизации
 */
public class LoginController extends SuperFullController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @FXML
    private Label wrongAuth;
    @FXML
    private JFXTextField loginTextField;
    @FXML
    private JFXPasswordField passwordTextField;
    @FXML
    private JFXCheckBox autoLoginCheckBox;

    /**
     * Нажатие на кнопку авторизации в программе
     */
    @FXML
    public void mainButtonClick() {

        logger.info("Нажатие на button авторизации в программе");
        String login = loginTextField.getText();
        String password = passwordTextField.getText();

        if ((login.equals("")) || (password.equals(""))) {
            logger.info("Попытка входа без ввода данных");
        } else {
            //Пытаемся авторизоваться
            MyAPI bufSession = new MyAPI(login, password, this.app);
            //Если удалось произвести авторизацию
            if (bufSession.getIsAuthenticated()) {
                app.setMyAPI(bufSession);
                //Если пользователь выбрал "Запомнить меня"
                if (autoLoginCheckBox.isSelected()) {
                    AuthUtil authUtil = app.getAuthUtil();
                    authUtil.writeKey(bufSession.getUserKey());
                }

                app.getPrimaryStage().setTitle("DEMKAChat - Сообщения [" + bufSession.getUserName() + "]");
                app.MainChat();
                logger.info("Пользователь успешно авторизовался");
            } else {
                wrongAuth.setOpacity(1);
                logger.info("Не удалось авторизоваться");
            }


        }

    }

    /**
     * Нажатие на ссылку перехода на форму регистрации
     */
    @FXML
    public void regLinkClicked() {
        app.UserRegistration();
        logger.info("Переход на форму регистрации");
    }

    /**
     * Нажатие на ссылку восстановления пароля
     */
    @FXML
    public void forgotPasswordLinkClicked() {
        app.ForgotPassword();
        logger.info("Переход на форму восстановления пароля");
    }

    /**
     * Метод инициализации (вызывается с Main)
     *
     * @param app
     */
    @Override
    public void initialize(App app) {
        this.app = app;
    }
}
