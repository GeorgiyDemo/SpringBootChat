package org.demka.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.demka.App;
import org.demka.api.CheckInternetRunnable;
import org.demka.exceptions.EmptyAPIResponseException;
import org.demka.exceptions.FalseServerFlagException;
import org.demka.models.Message;
import org.demka.models.Room;
import org.demka.api.LongPollRunnable;
import org.demka.api.MyAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MainChatController extends SuperFullController {
    private ObservableList<Room> RoomData = FXCollections.observableArrayList();
    private ObservableList<Message> MessageData = FXCollections.observableArrayList();
    private static final Logger logger = LoggerFactory.getLogger(MainChatController.class);

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
            logger.info("Поле отправки сообщения пустое");
        }
        else {
            //Отправляем сообщеньку
            try {
                APISession.writeMessage(messageText);
            } catch (FalseServerFlagException | EmptyAPIResponseException e) {
                e.printStackTrace();
            }

            newMessageText.setText("");
            MessageTable.scrollTo(MessageData.get(MessageData.size()-1));
        }
    }

    @FXML
    private void createRoomButtonClicked(){
         mainApp.NewRoom();
    }


    /**
     * Метод инициализации (вызывается с Main)
     * @param mainApp
     */
    @Override
    public void initialize(App mainApp) {
        this.mainApp = mainApp;
        RoomTable.setItems(RoomData);

        newMessageText.setDisable(true);
        sendMessageButton.setOpacity(0);
        newMessageText.setOpacity(0);

        //API, через которое взаимодействуем с миром
        APISession = mainApp.getAPISession();

        //Обработчик нажатия на комнату
        RoomTable.getSelectionModel().selectedItemProperty().addListener(
                ((observableValue, oldValue, newValue) -> showChatRoomDetails(newValue))
        );
        RoomTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //Отображение имени комнаты в таблице
        RoomColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        //Отображение сообщений в таблице
        MessageUserColumn.setCellValueFactory(cellData -> cellData.getValue().getUserNameProperty());
        MessageTextColumn.setCellValueFactory(cellData -> cellData.getValue().getTextProperty());

        //Текст для таблицы сообщений, когда комната не выбрана
        Label placeholder = new Label();
        placeholder.setText("Выберите диалог слева или <создайте новый>");
        placeholder.setStyle("-fx-text-fill: white");
        MessageTable.setPlaceholder(placeholder);
        MessageTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //Слушатель изменения текста
        newMessageText.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean flag = newValue.equals("");
            sendMessageButton.setDisable(flag);
        });

        //Получаем комнаты и запускаем цикл по каждой из них
        List<Room> roomList = null;
        try {
            roomList = APISession.getUserRooms();
        } catch (FalseServerFlagException | EmptyAPIResponseException e) {
            e.printStackTrace();
        }
        for (Room currentRoom : roomList) {

            String currentRoomId = currentRoom.getId();
            //Получаем сообщения для каждой из комнат
            try {
                for (Message message : APISession.getRoomMessagesHistory(currentRoomId)) {
                    //Добавляем сообщеньку для комнаты
                    currentRoom.addMessage(message);
                }
            } catch (FalseServerFlagException | EmptyAPIResponseException e) {
                e.printStackTrace();
            }

        }
        RoomData.addAll(roomList);
        logger.info("MainChatController - инициализировали все комнаты");

        //Запускаем отдельный поток, который будет:
        // Добавлять новую комнату в RoomData, если пришло обновление по комнате, id которой нет в RoomData
        // Добавлять в определенный элемент room.addMessage() новое сообщение, которое прилетело через лонгпул
        LongPollRunnable runnable1 = new LongPollRunnable(RoomData, MessageData, APISession,mainApp);
        Thread thread1 = new Thread(runnable1, "LongPoll thread");
        thread1.start();
        logger.info("MainChatController - стартанули LongPollRunnable");

        //Поток, который проверяет доступонсть интернета
        CheckInternetRunnable runnable2 = new CheckInternetRunnable();
        Thread thread2 = new Thread(runnable2, "Check internet connection thread");
        thread2.start();
        logger.info("MainChatController - стартанули CheckInternetRunnable");

    }

    /**
     * Обработчик нажатия на комнату-чат
     * @param room
     */
    private void showChatRoomDetails(Room room){

        //Отображаем отправку сообщений
        newMessageText.setDisable(false);
        sendMessageButton.setOpacity(1);
        newMessageText.setOpacity(1);

        //Выставляем id текущей комнаты
        MessageData.clear();
        if(room != null) {
            APISession.setCurrentRoomId(room.getId());
            MessageData.addAll(room.getMessages());
            MessageTable.setItems(MessageData);
        }
        MessageTable.scrollTo(MessageData.get(MessageData.size()-1));
    }
}
