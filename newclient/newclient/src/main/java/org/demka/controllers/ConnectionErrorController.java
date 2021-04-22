package org.demka.controllers;

import org.demka.App;

/**
 * Контроллер, возникающий при ошибке подключения к серверу (или получения от него пустого ответа)
 */
public class ConnectionErrorController extends SuperFullController {

    /**
     * The constant isActive.
     */
    public static boolean isActive = false;

    /**
     * Метод инициализации (вызывается с Main)
     *
     * @param app
     */
    @Override
    public void initialize(App app) {
        this.app = app;
        isActive = true;
    }
}
