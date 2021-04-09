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

public class LoginController extends SuperFullController {

    @FXML
    private Label AppName;
    @FXML
    private Label WrongAuth;
    @FXML
    private JFXTextField LoginTextField;
    @FXML
    private JFXPasswordField PasswordTextField;
    @FXML
    private JFXCheckBox AutoLoginCheckBox;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @FXML
    public void mainButtonClick() {

        logger.info("Нажатие на button авторизации в программе");
        String login = LoginTextField.getText();
        String password = PasswordTextField.getText();

        if ((login.equals("")) || (password.equals(""))){
            logger.info("Попытка входа без ввода данных");
        }
        else{
            //Пытаемся авторизоваться
            MyAPI bufSession = new MyAPI(login, password, this.mainApp);
            //Если удалось произвести авторизацию
            if (bufSession.getIsAuthenticated()) {
                mainApp.setAPISession(bufSession);
                //Если пользователь выбрал "Запомнить меня"
                if (AutoLoginCheckBox.isSelected()) {
                    AuthUtil authUtil = mainApp.getAuthUtil();
                    authUtil.writeKey(bufSession.getUserKey());
                }

                mainApp.getPrimaryStage().setTitle("DEMKAChat - Сообщения ["+bufSession.getUserName()+"]");
                mainApp.MainChat();
                logger.info("Пользователь успешно авторизовался");
            }
            else{
                WrongAuth.setOpacity(1);
                logger.info("Не удалось авторизоваться");
            }


        }

    }

    @FXML
    public void regLinkClicked(){
        mainApp.UserRegistration();
        logger.info("Переход на форму регистрации");
    }

    @FXML
    public void forgotPasswordLinkClicked(){
        mainApp.ForgotPassword();
        logger.info("Переход на форму восстановления пароля");
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
