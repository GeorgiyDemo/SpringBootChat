package org.demka.utils;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * Класс для обработки начатия на Menu без Menuitem'ов
 */
public class MyMenuActionUtil {
    /**
     * Биндинг нажатия на Menu
     *
     * @param menu - экземпляр JavaFX controls menu
     */
    public static void onAction(Menu menu) {
        final MenuItem menuItem = new MenuItem();
        menu.getItems().add(menuItem);
        menu.addEventHandler(Menu.ON_SHOWN, event -> menu.hide());
        menu.addEventHandler(Menu.ON_SHOWING, event -> menu.fire());
    }
}
