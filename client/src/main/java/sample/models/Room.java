package sample.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Room {

    private final StringProperty creator_id;
    private final StringProperty name;
    private final IntegerProperty time_created;
    private final ListProperty<String> users;
    private final StringProperty id;

    private List<Message> messages;

    public Room(String creator_id, String name, int time_created, List<String> users, String id){
        this.creator_id = new SimpleStringProperty(creator_id);
        this.name = new SimpleStringProperty(name);
        this.time_created = new SimpleIntegerProperty(time_created);
        ObservableList<String> userData = FXCollections.observableArrayList();
        userData.addAll(users);
        this.users = new SimpleListProperty<String>(userData);
        this.id = new SimpleStringProperty(id);
        this.messages = new ArrayList<Message>();
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public String getId() {
        return id.get();
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }
}
