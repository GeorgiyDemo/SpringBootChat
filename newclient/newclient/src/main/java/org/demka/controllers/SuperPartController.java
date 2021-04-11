package org.demka.controllers;

import javafx.stage.Stage;
import org.demka.App;

/**
 * Класс, от которого наследуются все контроллеры, layout которых открывается на всё окно
 */
public abstract class SuperPartController {
    protected App mainApp;
    protected Stage dialogStage;

    /**
     * Метод инициализации (вызывается с Main)
     *
     * @param mainApp
     * @param dialogStage
     */
    public abstract void initialize(App mainApp, Stage dialogStage);
}
