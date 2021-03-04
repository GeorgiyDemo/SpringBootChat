package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sample.Main;
import sample.api.API;
import sample.models.Message;
import sample.models.Room;
import sample.api.LongPollRunnable;
import sample.api.MyAPI;
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

    @FXML
    private Button sendMessageButton;
    @FXML
    private TextField newMessageText;
    /***
     *  Обработчик нажатия на button отпраавки сообщения
     */
    @FXML
    private void sendMessageButtonClicked(){
        String messageText = newMessageText.getText();
        if (messageText.equals("")){
            MyLogger.logger.info("Поле отправки сообщения пустое");
        }
        else {
            MyLogger.logger.info("*Отправили сообщение*");
        }


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

        //Слушатель изменения текста
        newMessageText.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean flag = newValue.equals("");
            sendMessageButton.setDisable(flag);
        });

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

        //Запускаем отдельный поток, который будет:
        // Добавлять новую комнату в RoomData, если пришло обновление по комнате, id которой нет в RoomData
        // Добавлять в определенный элемент room.addMessage() новое сообщение, которое прилетело через лонгпул
        LongPollRunnable runnable = new LongPollRunnable(RoomData, MessageData, APISession);
        Thread thread = new Thread(runnable, "LongPoll Thread");
        thread.start();

        MyLogger.logger.info("MainChatController - стартанули LongPollRunnable");

    }

    /**
     * Обработчик нажатия на комнату-чат
     * @param room
     */
    private void showChatRoomDetails(Room room){
        //Выставляем id текущей комнаты
        MessageData.clear();
        if(room != null) {
            MyAPI.setCurrentRoomId(room.getId());
            MessageData.addAll(room.getMessages());
            MessageTable.setItems(MessageData);
        }

    }
}
