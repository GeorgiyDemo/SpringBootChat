package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.Main;
import sample.models.Message;
import sample.models.Room;
import sample.utils.MyAPI;
import sample.utils.MyLogger;

import java.util.List;

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
        this.mainApp = mainApp;
        personTable.setItems(RoomData);

        APISession = mainApp.getAPISession();
        //Отображение имени комнаты в таблице
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());

        //Получаем комнаты и запускаем цикл по каждой из них
        List<Room> roomList = APISession.getUserRooms();
        for (Room currentRoom : roomList) {

            String currentRoomId = currentRoom.getId();
            //Получаем сообщения для каждой из комнат
            for (Message message : APISession.getRoomMessagesHistory(currentRoomId)) {
                currentRoom.addMessage(message);
            }

        }
        RoomData.addAll(roomList);

        MyLogger.logger.info("MainChatController - конструктор отработал");

    }
}
