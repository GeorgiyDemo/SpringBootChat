package org.demka.controllers;

import org.demka.App;

/**
 * Базовый контроллер
 */
public class RootLayoutController extends SuperFullController {

    /**
     * Метод инициализации (вызывается с Main)
     *
     * @param app
     */
    @Override
    public void initialize(App app) {
        this.app = app;
    }
}
