package org.demka.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import org.demka.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Desktop;

//Этот контроллер и вьюха мне максимально не нужны, но методичка требует
public class AboutMeController extends SuperPartController {

    @FXML
    private Hyperlink SiteLink;
    private static final Logger logger = LoggerFactory.getLogger(AboutMeController.class);
    /**
     * Метод инициализации (вызывается с Main)
     *
     * @param mainApp
     * @param dialogStage
     */
    @Override
    public void initialize(App mainApp, Stage dialogStage) {
        this.mainApp = mainApp;
        this.dialogStage = dialogStage;
    }

    @FXML
    private void okButtonClicked(){
        logger.info("Выход из подменю с информацией об авторе и программе");
        dialogStage.close();
    }

    @FXML
    private void siteLinkClicked(){
        String linkText = SiteLink.getText();
        logger.info("Нажатие на ссылку "+linkText);
        if(Desktop.isDesktopSupported())
        {
            try {
                Desktop.getDesktop().browse(new URI(linkText));
                logger.info("Открыли ссылку "+linkText+" в браузере");
            } catch (IOException | URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
    }

}
