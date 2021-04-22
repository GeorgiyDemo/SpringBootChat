package org.demka.controllers;

import org.demka.App;

/**
 * Класс, от которого наследуются все контроллеры, layout которых открывается на всё окно
 */
public abstract class SuperFullController {
    /**
     * The App.
     */
    protected App app;

    /**
     * Метод инициализации (вызывается с Main)
     *
     * @param app the app
     */
    public abstract void initialize(App app);
}
