package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.Main;
import sample.models.Message;
import sample.models.Room;
import sample.utils.LongPollRunnable;
import sample.utils.MyAPI;
import sample.utils.MyLogger;

import java.util.List;

public class MainChatController extends SuperController {
    private ObservableList<Room> RoomData = FXCollections.observableArrayList();
    private ObservableList<Message> MessageData = FXCollections.observableArrayList();

    private MyAPI APISession;
    @FXML
    private TableView<Room> RoomTable;
    @FXML
    private TableColumn<Room, String> RoomColumn;


    @FXML
    private TableView<Message> MessageTable;
    @FXML
    private TableColumn<Message, String> MessageUserColumn;
    @FXML
    private TableColumn<Message, String> MessageTextColumn;

    /***
     *  Обработчик нажатия на button отпраавки сообщения
     */
    @FXML
    private void sendMessageButtonClicked(){
        MyLogger.logger.info("Нажали на button отправки сообщения");

    }


    /**
     * Метод инициализации (вызывается с Main)
     * @param mainApp
     */
    @Override
    public void initialize(Main mainApp) {
        this.mainApp = mainApp;
        RoomTable.setItems(RoomData);

        //API, через которое взаимодействуем с миром
        APISession = mainApp.getAPISession();

        //Обработчик нажатия на комнату
        RoomTable.getSelectionModel().selectedItemProperty().addListener(
                ((observableValue, oldValue, newValue) -> showChatRoomDetails(newValue))
        );


        //Отображение имени комнаты в таблице
        RoomColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        //Отображение сообщений в таблице
        MessageUserColumn.setCellValueFactory(cellData -> cellData.getValue().getUserFromProperty());
        MessageTextColumn.setCellValueFactory(cellData -> cellData.getValue().getTextProperty());

        //Получаем комнаты и запускаем цикл по каждой из них
        List<Room> roomList = APISession.getUserRooms();
        for (Room currentRoom : roomList) {

            String currentRoomId = currentRoom.getId();
            //Получаем сообщения для каждой из комнат
            for (Message message : APISession.getRoomMessagesHistory(currentRoomId)) {
                //Добавляем сообщеньку для комнаты
                currentRoom.addMessage(message);
            }

        }
        RoomData.addAll(roomList);
        MyLogger.logger.info("MainChatController - инициализировали все комнаты");

        //TODO: Инициализируем longpoll
        LongPollRunnable runnable = new LongPollRunnable(RoomData, APISession);
        Thread thread = new Thread(runnable, "LongPoll Thread");
        thread.start();

        //Запускаем отдельный поток, который будет:
        // Добавлять новую комнату в RoomData, если пришло обновление по комнате, id которой нет в RoomData
        // Добавлять в определенный элемент room.addMessage() новое сообщение, которое прилетело через лонгпул

    }

    /**
     * Обработчик нажатия на комнату-чат
     * @param room
     */
    private void showChatRoomDetails(Room room){
        MessageData.clear();
        if(room != null) {
            MessageData.addAll(room.getMessages());
            MessageTable.setItems(MessageData);
        }

    }
}
