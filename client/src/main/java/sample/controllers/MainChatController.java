package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.Main;
import sample.models.Room;
import sample.utils.MyAPI;
import sample.utils.MyLogger;

public class MainChatController extends SuperController {
    private ObservableList<Room> personData = FXCollections.observableArrayList();

    private MyAPI APISession;
    @FXML
    private TableView<Room> personTable;

    @FXML
    private TableColumn<Room, String> firstNameColumn;

    /***
     *  Обработчик нажатия на button отпраавки сообщения
     */
    @FXML
    private void sendMessageButtonClicked(){
        MyLogger.logger.info("Нажали на button отправки сообщения");

    }


    @Override
    public void initialize(Main mainApp) {
        System.out.println("запустили setMainApp");
        this.mainApp = mainApp;
        personTable.setItems(personData);

        System.out.println("запускили конструктор");
        APISession = mainApp.getAPISession();
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        System.out.println("initialize отработал");
        for (int i = 0; i < 5; i++) {
            Room bufChat = new Room("MEOW"+i);
            personData.add(bufChat);
        }
        APISession.getUserRooms();
        //userChatRooms
    }
}
