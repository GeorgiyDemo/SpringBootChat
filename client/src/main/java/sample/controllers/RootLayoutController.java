package sample.controllers;

import sample.Main;

public class RootLayoutController extends SuperController {

    @Override
    public void initialize(Main mainApp) {
        this.mainApp = mainApp;
    }
}
