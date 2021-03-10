package sample.controllers;

import javafx.stage.Stage;
import sample.Main;

/**
 * Класс, от которого наследуются все контроллеры, layout которых открывается на всё окно
 */
public abstract class SuperPartController {
    protected Main mainApp;

    /**
     * Метод инициализации (вызывается с Main)
     * @param mainApp
     * @param dialogStage
     */
    public abstract void  initialize(Main mainApp, Stage dialogStage);
}
