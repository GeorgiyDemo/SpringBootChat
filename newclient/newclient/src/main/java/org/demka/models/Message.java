package org.demka.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.demka.utils.UNIXTime2String;

import java.util.List;

public class Message {

    private StringProperty userId;
    private StringProperty userName;
    private StringProperty text;
    private StringProperty roomId;
    private StringProperty timeCreated;
    private StringProperty id;

    public Message(String userId, String userName, String text, String roomId, long timeCreated, String id){
        this.userId = new SimpleStringProperty(userId);
        this.userName = new SimpleStringProperty(userName);
        this.text = new SimpleStringProperty(text);
        this.roomId = new SimpleStringProperty(roomId);
        this.timeCreated = new SimpleStringProperty(UNIXTime2String.convert(timeCreated));
        this.id = new SimpleStringProperty(id);
    }

    public String getText() {
        return text.get();
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty getUserNameProperty(){
        return userName;
    }

    public StringProperty getTextProperty(){
        return text;
    }

    public StringProperty getTimeCreatedProperty(){
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
