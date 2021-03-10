package sample.controllers;

import sample.Main;

/**
 * Класс, от которого наследуются все контроллеры, layout которых открывается на всё окно
 */
public abstract class SuperFullController {
    protected Main mainApp;

    /**
     * Метод инициализации (вызывается с Main)
     * @param mainApp
     */
    public abstract void initialize(Main mainApp);
}
