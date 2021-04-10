package org.demka.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.demka.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuccessActionController extends SuperFullController {
    /**
     * Метод инициализации (вызывается с Main)
     *
     * @param mainApp
     */

    @FXML
    private Label MainText;

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Override
    public void initialize(App mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void okButtonClicked(){
        logger.info("Нажатие на button возврата на формы авторизации");
        mainApp.UserAuthorisation();
    }

    public void setMainText(String text){
        MainText.setText(text);
    }
}
