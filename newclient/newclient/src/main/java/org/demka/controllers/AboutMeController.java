package org.demka.controllers;

import javafx.stage.Stage;
import org.demka.App;

//Этот контроллер и вьюха мне максимально не нужны, но методичка требует
public class AboutMeController extends SuperPartController {

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
}
