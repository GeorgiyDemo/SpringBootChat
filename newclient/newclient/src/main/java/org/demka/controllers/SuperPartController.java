package org.demka.controllers;

import javafx.stage.Stage;
import org.demka.App;

/**
 * Класс, от которого наследуются все контроллеры, layout которых открывается поверх основного окна
 */
public abstract class SuperPartController {
    /**
     * The App.
     */
    protected App app;
    /**
     * The Dialog stage.
     */
    protected Stage dialogStage;

    /**
     * Метод инициализации (вызывается с Main)
     *
     * @param app         the app
     * @param dialogStage the dialog stage
     */
    public abstract void initialize(App app, Stage dialogStage);
}
