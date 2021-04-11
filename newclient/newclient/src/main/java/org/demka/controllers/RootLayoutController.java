package org.demka.controllers;

import org.demka.App;

public class RootLayoutController extends SuperFullController {

    /**
     * Метод инициализации (вызывается с Main)
     *
     * @param mainApp
     */
    @Override
    public void initialize(App mainApp) {
        this.mainApp = mainApp;
    }
}
