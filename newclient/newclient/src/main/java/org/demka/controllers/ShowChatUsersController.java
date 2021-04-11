package org.demka.controllers;

import javafx.stage.Stage;
import org.demka.App;

public class ShowChatUsersController extends SuperPartController {

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
