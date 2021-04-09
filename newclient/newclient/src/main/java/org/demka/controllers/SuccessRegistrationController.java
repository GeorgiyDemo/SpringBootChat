package org.demka.controllers;

import javafx.fxml.FXML;
import org.demka.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuccessRegistrationController extends SuperFullController {
    /**
     * Метод инициализации (вызывается с Main)
     *
     * @param mainApp
     */

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Override
    public void initialize(App mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void okButtonClicked(){
        logger.info("Нажатие на button возврата на формы авторизации");
        mainApp.UserAuthorisation();
    }
}
