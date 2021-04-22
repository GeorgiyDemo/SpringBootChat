package org.demka.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Модель пользователя
 */
public class User {
    private final StringProperty id;
    private final StringProperty name;
    private final IntegerProperty timeCreated;

    /**
     * Конструктор пользователя
     *
     * @param id          - идентификатор пользователя
     * @param name        - имя пользователя
     * @param timeCreated - время создания пользователя
     */
    public User(String id, String name, Integer timeCreated) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.timeCreated = new SimpleIntegerProperty(timeCreated);
    }

    /**
     * Gets name property.
     *
     * @return the name property
     */
    public StringProperty getNameProperty() {
        return name;
    }

    /**
     * Gets id property.
     *
     * @return the id property
     */
    public StringProperty getIdProperty() {
        return id;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id.get();
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name.get();
    }
}
