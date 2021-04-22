package org.demka.models;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.demka.utils.UNIXTimeUtil;

/**
 * Модель сообщения
 */
public class Message {

    private final StringProperty userId;
    private final StringProperty userName;
    private final StringProperty text;
    private final StringProperty roomId;
    private final StringProperty timeCreatedString;
    private final LongProperty timeCreatedLong;
    private final StringProperty id;

    /**
     * Конструктор сообщения
     *
     * @param userId      - идентификатор пользователя, отправившего сообщение
     * @param userName    - имя пользователя, отправившего сообщение
     * @param text        - текст сообщения
     * @param roomId      - идентификатор комнаты, куда отправляется сообщение
     * @param timeCreated - UNIX-время создания сообщения
     * @param id          - идентификатор сообщения
     */
    public Message(String userId, String userName, String text, String roomId, long timeCreated, String id) {
        this.userId = new SimpleStringProperty(userId);
        this.userName = new SimpleStringProperty(userName);
        this.text = new SimpleStringProperty(text);
        this.roomId = new SimpleStringProperty(roomId);

        this.timeCreatedLong = new SimpleLongProperty(timeCreated);
        this.timeCreatedString = new SimpleStringProperty(UNIXTimeUtil.convert(timeCreated));
        this.id = new SimpleStringProperty(id);
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        return text.get();
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName.get();
    }

    /**
     * Gets user name property.
     *
     * @return the user name property
     */
    public StringProperty getUserNameProperty() {
        return userName;
    }

    /**
     * Gets text property.
     *
     * @return the text property
     */
    public StringProperty getTextProperty() {
        return text;
    }

    /**
     * Gets time created property.
     *
     * @return the time created property
     */
    public StringProperty getTimeCreatedProperty() {
        return timeCreatedString;
    }

    /**
     * Gets time created string.
     *
     * @return the time created string
     */
    public String getTimeCreatedString() {
        return timeCreatedString.get();
    }

    /**
     * Gets time created long.
     *
     * @return the time created long
     */
    public long getTimeCreatedLong() {
        return timeCreatedLong.get();
    }

    /**
     * Gets room id.
     *
     * @return the room id
     */
    public String getRoomId() {
        return roomId.get();
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id.get();
    }

}
