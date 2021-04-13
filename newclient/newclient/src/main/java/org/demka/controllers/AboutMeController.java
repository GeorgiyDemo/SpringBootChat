package org.demka.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import org.demka.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Контроллер-обработчик окна "О программе/об авторе"
 */
public class AboutMeController extends SuperPartController {

    private static final Logger logger = LoggerFactory.getLogger(AboutMeController.class);

    @FXML
    private Hyperlink siteLink;

    /**
     * Метод инициализации (вызывается с Main)
     * @param app
     * @param dialogStage
     */
    @Override
    public void initialize(App app, Stage dialogStage) {
        this.app = app;
        this.dialogStage = dialogStage;
    }

    /**
     * Нажатие на button выхода из текущего окна
     */
    @FXML
    private void okButtonClicked() {
        logger.info("Выход из подменю с информацией об авторе и программе");
        dialogStage.close();
    }

    /**
     * Нажатие на ссылку "Об авторе"
     */
    @FXML
    private void siteLinkClicked() {
        String linkText = siteLink.getText();
        logger.info("Нажатие на ссылку " + linkText);
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(linkText));
                logger.info("Открыли ссылку " + linkText + " в браузере");
            } catch (IOException | URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
    }

}
