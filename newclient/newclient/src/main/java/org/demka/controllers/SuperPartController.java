package org.demka.controllers;

import javafx.stage.Stage;
import org.demka.App;

/**
 * Класс, от которого наследуются все контроллеры, layout которых открывается поверх основного окна
 */
public abstract class SuperPartController {
    protected App app;
    protected Stage dialogStage;

    /**
     * Метод инициализации (вызывается с Main)
     *
     * @param app
     * @param dialogStage
     */
    public abstract void initialize(App app, Stage dialogStage);
}
