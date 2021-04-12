package org.demka.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.demka.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Контроллер-обработчик успешной операции пользователя
 */
public class SuccessActionController extends SuperFullController {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);


    @FXML
    private Label mainText;

    /**
     * Метод инициализации (вызывается с Main)
     *
     * @param app
     */
    @Override
    public void initialize(App app) {
        this.app = app;
    }

    /**
     * Нажатие на кнопку формы авторизации
     */
    @FXML
    private void okButtonClicked() {
        logger.info("Нажатие на button возврата на формы авторизации");
        app.UserAuthorisation();
    }

    public void setMainText(String text) {
        mainText.setText(text);
    }
}
