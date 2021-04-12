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

    public StringProperty getNameProperty() {
        return name;
    }

    public StringProperty getIdProperty() {
        return id;
    }

    public String getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }
}
