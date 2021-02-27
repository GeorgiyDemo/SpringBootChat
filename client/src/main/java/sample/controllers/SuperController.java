package sample.controllers;

import sample.Main;

/**
 * Класс, от которого наследуются все контроллеры
 */
public abstract class SuperController {
    protected Main mainApp;

    public abstract void initialize(Main mainApp);
}
