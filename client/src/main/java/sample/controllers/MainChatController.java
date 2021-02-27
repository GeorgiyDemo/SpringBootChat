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
    private ObservableList<Room> RoomData = FXCollections.observableArrayList();

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
        personTable.setItems(RoomData);

        System.out.println("запускили конструктор");
        APISession = mainApp.getAPISession();
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        System.out.println("initialize отработал");
        //RoomData.addAll(APISession.getUserRooms());
        
        APISession.getUserRooms();
        //userChatRooms
    }
}
