
package sample.controller;

import sample.Main;

/**
 * Класс, от которого наследуются все контроллеры
 */
public class SuperController {
    protected Main mainApp;
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
    }
}
