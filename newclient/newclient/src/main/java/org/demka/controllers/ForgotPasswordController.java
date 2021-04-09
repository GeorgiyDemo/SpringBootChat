package org.demka.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import org.demka.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForgotPasswordController extends SuperFullController {

    @FXML
    JFXPasswordField NewPasswordTextField;

    @FXML
    JFXPasswordField MasterKeyTextField;

    @FXML
    JFXTextField LoginTextField;

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
    }
}
