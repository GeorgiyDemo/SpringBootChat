package sample.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Message {

    private StringProperty userFrom;
    private StringProperty text;
    private StringProperty roomId;
    private IntegerProperty timeCreated;
    private StringProperty id;

    public Message(String userFrom, String text, String roomId, int timeCreated, String id){
        this.userFrom = new SimpleStringProperty(userFrom);
        this.text = new SimpleStringProperty(text);
        this.roomId = new SimpleStringProperty(roomId);
        this.timeCreated = new SimpleIntegerProperty(timeCreated);
        this.id = new SimpleStringProperty(id);
    }

    public String getText() {
        return text.get();
    }

    public String getUserFrom() {
        return userFrom.get();
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
