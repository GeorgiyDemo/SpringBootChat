package org.demka.controllers;

import javafx.fxml.FXML;
import org.demka.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForgotPasswordController extends SuperFullController {
    /**
     * Метод инициализации (вызывается с Main)
     *
     * @param mainApp
     */
    private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);
    @Override
    public void initialize(App mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void backButtonClicked(){
        mainApp.UserAuthorisation();
        logger.info("Выход из формы смены пароля");
    }
}
