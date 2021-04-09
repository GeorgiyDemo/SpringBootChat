package org.demka.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import org.demka.App;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Desktop;

//Этот контроллер и вьюха мне максимально не нужны, но методичка требует
public class AboutMeController extends SuperPartController {

    @FXML
    private Hyperlink SiteLink;
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
        dialogStage.close();
    }

    @FXML
    private void siteLinkClicked(){
        if(Desktop.isDesktopSupported())
        {
            try {
                Desktop.getDesktop().browse(new URI(SiteLink.getText()));
            } catch (IOException | URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
    }

}
