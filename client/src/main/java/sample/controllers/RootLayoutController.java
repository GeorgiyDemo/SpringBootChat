package sample.controllers;

import sample.Main;

public class RootLayoutController extends SuperController {

    /**
     * Метод инициализации (вызывается с Main)
     * @param mainApp
     */
    @Override
    public void initialize(Main mainApp) {
        this.mainApp = mainApp;
    }
}
