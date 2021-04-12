package org.demka.models;

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
    private final StringProperty timeCreated;
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
        this.timeCreated = new SimpleStringProperty(UNIXTimeUtil.convert(timeCreated));
        this.id = new SimpleStringProperty(id);
    }

    public String getText() {
        return text.get();
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty getUserNameProperty() {
        return userName;
    }

    public StringProperty getTextProperty() {
        return text;
    }

    public StringProperty getTimeCreatedProperty() {
        return timeCreated;
    }

    public String getTimeCreated() {
        return timeCreated.get();
    }

    public String getRoomId() {
        return roomId.get();
    }

    public String getId() {
        return id.get();
    }
}
