package sample.controllers;

import sample.Main;

/**
 * Контроллер, возникающий при ошибке подключения к серверу (или получения от него пустого ответа)
 */
public class ConnectionErrorController extends SuperFullController {
    /**
     * Метод инициализации (вызывается с Main)
     *
     * @param mainApp
     */
    @Override
    public void initialize(Main mainApp) {
        this.mainApp = mainApp;
    }
}
