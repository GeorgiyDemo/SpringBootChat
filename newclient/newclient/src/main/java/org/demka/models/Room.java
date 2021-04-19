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

    private final StringProperty creator_id;
    private final StringProperty name;
    private final IntegerProperty time_created;
    private final ListProperty<String> users;
    private final StringProperty id;
    private final StringProperty newMessagesFlag;
    private final List<Message> messages;

    private final static String NO_NEW_MESSAGE_FLAG = " ";
    private final static String NEW_MESSAGE_FLAG = "*";

    /**
     * Конструктор комнаты
     *
     * @param creator_id   - идентификатор пользователя-создателя комнаты
     * @param name         - имя комнаты
     * @param time_created - UNIX-время создания комнаты
     * @param users        - список идентификаторов создателей
     * @param id           - идентификатор комнаты
     */
    public Room(String creator_id, String name, int time_created, List<String> users, String id) {
        this.creator_id = new SimpleStringProperty(creator_id);
        this.name = new SimpleStringProperty(name);
        this.time_created = new SimpleIntegerProperty(time_created);
        ObservableList<String> userData = FXCollections.observableArrayList();
        userData.addAll(users);
        this.users = new SimpleListProperty<>(userData);
        this.id = new SimpleStringProperty(id);
        this.messages = new ArrayList<>();
        this.newMessagesFlag = new SimpleStringProperty(NO_NEW_MESSAGE_FLAG);
    }

    /**
     * Выставление флага прочитанных сообщений для комнаты
     * @param flag булевое значение
     */
    public void setNewMessageFlag(boolean flag){
        if (flag)
            newMessagesFlag.setValue(NEW_MESSAGE_FLAG);
        else
            newMessagesFlag.setValue(NO_NEW_MESSAGE_FLAG);
    }

    public StringProperty newMessagesFlagString() {
        return newMessagesFlag;
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    public String getId() {
        return id.get();
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public ObservableList<String> getUsers() {
        return users.get();
    }

}
