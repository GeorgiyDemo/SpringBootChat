package sample.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Message {

    private StringProperty userId;
    private StringProperty userName;
    private StringProperty text;
    private StringProperty roomId;
    private IntegerProperty timeCreated;
    private StringProperty id;

    public Message(String userId, String userName, String text, String roomId, int timeCreated, String id){
        this.userId = new SimpleStringProperty(userId);
        this.userName = new SimpleStringProperty(userName);
        this.text = new SimpleStringProperty(text);
        this.roomId = new SimpleStringProperty(roomId);
        this.timeCreated = new SimpleIntegerProperty(timeCreated);
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

    public int getTimeCreated() {
        return timeCreated.get();
    }

    public String getRoomId() {
        return roomId.get();
    }

    public String getId() {
        return id.get();
    }
}
