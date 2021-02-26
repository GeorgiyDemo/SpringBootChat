package sample.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ChatRoom {

    private final StringProperty name;

    public ChatRoom(String name){
        this.name = new SimpleStringProperty(name);
    }

    public StringProperty getNameProperty() {
        return name;
    }
}
