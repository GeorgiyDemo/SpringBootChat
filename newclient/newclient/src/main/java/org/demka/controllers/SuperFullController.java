package org.demka.controllers;

import org.demka.App;

/**
 * Класс, от которого наследуются все контроллеры, layout которых открывается на всё окно
 */
public abstract class SuperFullController {
    protected App mainApp;

    /**
     * Метод инициализации (вызывается с Main)
     * @param mainApp
     */
    public abstract void initialize(App mainApp);
}
