package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.Main;
import sample.models.ChatRoom;

public class MainChatController extends SuperController {
    private ObservableList<ChatRoom> personData = FXCollections.observableArrayList();


    @FXML
    private TableView<ChatRoom> personTable;


    @FXML
    private TableColumn<ChatRoom, String> firstNameColumn;

    @FXML
    private void initialize(){
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        System.out.println("init отработал");
        for (int i = 0; i < 5; i++) {
            ChatRoom bufChat = new ChatRoom("MEOW"+i);
            personData.add(bufChat);
        }

        //firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
    }

    @Override
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        personTable.setItems(personData);
    }


}
