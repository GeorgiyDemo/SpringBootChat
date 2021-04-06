package org.demka.controllers;

import org.demka.App;

/**
 * Контроллер, возникающий при ошибке подключения к серверу (или получения от него пустого ответа)
 */
public class ConnectionErrorController extends SuperFullController {
    /**
     * Метод инициализации (вызывается с Main)
     *
     * @param mainApp
     */
    public static boolean isActive = false;
    @Override
    public void initialize(App mainApp) {
        this.mainApp = mainApp;
        isActive = true;
    }
}
