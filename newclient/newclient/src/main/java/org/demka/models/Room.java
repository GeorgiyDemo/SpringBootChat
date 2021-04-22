package org.demka.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Модель комнаты
 */
public class Room {

    private final static String NO_NEW_MESSAGE_FLAG = " ";
    private final static String NEW_MESSAGE_FLAG = "●";
    private final StringProperty creatorId;
    private final StringProperty name;
    private final IntegerProperty timeCreated;
    private final ListProperty<String> users;
    private final StringProperty id;
    private final StringProperty newMessagesFlag;
    private final List<Message> messages;

    /**
     * Конструктор комнаты
     *
     * @param creatorId   - идентификатор пользователя-создателя комнаты
     * @param name        - имя комнаты
     * @param timeCreated - UNIX-время создания комнаты
     * @param users       - список идентификаторов создателей
     * @param id          - идентификатор комнаты
     */
    public Room(String creatorId, String name, int timeCreated, List<String> users, String id) {
        this.creatorId = new SimpleStringProperty(creatorId);
        this.name = new SimpleStringProperty(name);
        this.timeCreated = new SimpleIntegerProperty(timeCreated);
        ObservableList<String> userData = FXCollections.observableArrayList();
        userData.addAll(users);
        this.users = new SimpleListProperty<>(userData);
        this.id = new SimpleStringProperty(id);
        this.messages = new ArrayList<>();
        this.newMessagesFlag = new SimpleStringProperty(NO_NEW_MESSAGE_FLAG);
    }

    /**
     * Выставление флага прочитанных сообщений для комнаты
     *
     * @param flag булевое значение
     */
    public void setNewMessageFlag(boolean flag) {
        if (flag)
            newMessagesFlag.setValue(NEW_MESSAGE_FLAG);
        else
            newMessagesFlag.setValue(NO_NEW_MESSAGE_FLAG);
    }

    /**
     * New messages flag string string property.
     *
     * @return the string property
     */
    public StringProperty newMessagesFlagString() {
        return newMessagesFlag;
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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name.get();
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
     * Add message.
     *
     * @param message the message
     */
    public void addMessage(Message message) {
        this.messages.add(message);
    }

    /**
     * Gets messages.
     *
     * @return the messages
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * Gets users.
     *
     * @return the users
     */
    public ObservableList<String> getUsers() {
        return users.get();
    }

}
