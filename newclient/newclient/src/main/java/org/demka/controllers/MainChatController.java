package org.demka.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.demka.App;
import org.demka.api.MyAPI;
import org.demka.exceptions.EmptyAPIResponseException;
import org.demka.exceptions.FalseServerFlagException;
import org.demka.models.Message;
import org.demka.models.Room;
import org.demka.runnable.CheckInternetRunnable;
import org.demka.runnable.LongPollRunnable;
import org.demka.runnable.RunnableManager;
import org.demka.utils.MyMenuActionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Контроллер-обработчик главного окна чата
 */
public class MainChatController extends SuperFullController {
    private static final Logger logger = LoggerFactory.getLogger(MainChatController.class);
    private final ObservableList<Room> roomData = FXCollections.observableArrayList();
    private final ObservableList<Message> messageData = FXCollections.observableArrayList();
    private MyAPI myAPI;
    @FXML
    private TableView<Room> roomTable;
    @FXML
    private TableColumn<Room, String> roomColumn;

    @FXML
    private TableView<Message> messageTable;
    @FXML
    private TableColumn<Message, String> messageTimeColumn;
    @FXML
    private TableColumn<Message, String> messageUserColumn;
    @FXML
    private TableColumn<Message, String> messageTextColumn;


    @FXML
    private Menu aboutMenuItem;
    @FXML
    private Menu exitMenuItem;
    @FXML
    private MenuBar mainMenuBar;

    @FXML
    private JFXButton chatUsersButton;
    @FXML
    private JFXButton sendMessageButton;
    @FXML
    private TextField newMessageText;

    /***
     *  Обработчик нажатия на кнопку сообщения
     */
    @FXML
    private void sendMessageButtonClicked() {
        String messageText = newMessageText.getText();
        if (messageText.equals("")) {
            logger.info("Поле отправки сообщения пустое");
        } else {
            //Отправляем сообщеньку
            try {
                myAPI.writeMessage(messageText);
            } catch (FalseServerFlagException | EmptyAPIResponseException e) {
                e.printStackTrace();
            }

            newMessageText.setText("");
            messageTable.scrollTo(messageData.get(messageData.size() - 1));
        }
    }

    /**
     * Обработчик нажатия на кнопку создания новой комнаты
     */
    @FXML
    private void createRoomButtonClicked() {
        app.NewRoom();
    }


    /**
     * Метод инициализации (вызывается с Main)
     *
     * @param app
     */
    @Override
    public void initialize(App app) {
        this.app = app;
        roomTable.setItems(roomData);

        sendMessageButton.setOpacity(0);
        newMessageText.setDisable(true);
        chatUsersButton.setDisable(true);
        newMessageText.setOpacity(0);
        chatUsersButton.setOpacity(0);

        //API, через которое взаимодействуем с миром
        myAPI = app.getMyAPI();

        //Обработчик нажатия на комнату
        roomTable.getSelectionModel().selectedItemProperty().addListener(
                ((observableValue, oldValue, newValue) -> showChatRoomDetails(newValue))
        );
        roomTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //Отображение имени комнаты в таблице
        roomColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        //Отображение сообщений в таблице
        messageTimeColumn.setCellValueFactory(cellData -> cellData.getValue().getTimeCreatedProperty());
        messageUserColumn.setCellValueFactory(cellData -> cellData.getValue().getUserNameProperty());
        messageTextColumn.setCellValueFactory(cellData -> cellData.getValue().getTextProperty());

        //Текст для таблицы сообщений, когда комната не выбрана
        Label placeholder = new Label();
        placeholder.setText("Выберите диалог слева или <создайте новый>");
        placeholder.setStyle("-fx-text-fill: white");
        messageTable.setPlaceholder(placeholder);
        messageTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //Слушатель изменения текста
        newMessageText.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean flag = newValue.equals("");
            sendMessageButton.setDisable(flag);
        });

        //Получаем комнаты и запускаем цикл по каждой из них
        List<Room> roomList = null;
        try {
            roomList = myAPI.getUserRooms();
        } catch (FalseServerFlagException | EmptyAPIResponseException e) {
            e.printStackTrace();
        }
        //Ну мало ли
        assert roomList != null;
        for (Room currentRoom : roomList) {

            String currentRoomId = currentRoom.getId();
            //Получаем сообщения для каждой из комнат
            try {
                for (Message message : myAPI.getRoomMessagesHistory(currentRoomId)) {
                    //Добавляем сообщеньку для комнаты
                    currentRoom.addMessage(message);
                }
            } catch (FalseServerFlagException | EmptyAPIResponseException e) {
                e.printStackTrace();
            }

        }
        roomData.addAll(roomList);
        logger.info("MainChatController - инициализировали все комнаты");

        //Запускаем отдельный поток, который будет:
        // Добавлять новую комнату в RoomData, если пришло обновление по комнате, id которой нет в RoomData
        // Добавлять в определенный элемент room.addMessage() новое сообщение, которое прилетело через лонгпул
        LongPollRunnable runnable1 = new LongPollRunnable(roomData, messageData, myAPI, app);
        Thread thread1 = new Thread(runnable1, "LongPoll thread");
        RunnableManager.threadsList.add(thread1);
        thread1.start();
        logger.info("MainChatController - стартанули LongPollRunnable с id " + thread1.getId());

        //Поток, который проверяет доступонсть интернета
        CheckInternetRunnable runnable2 = new CheckInternetRunnable(app);
        Thread thread2 = new Thread(runnable2, "Check internet connection thread");
        RunnableManager.threadsList.add(thread2);
        thread2.start();
        logger.info("MainChatController - стартанули CheckInternetRunnable с id " + thread2.getId());

        //Биндим действия к AboutMenuItem и ExitMenuItem
        MyMenuActionUtil.onAction(aboutMenuItem);
        MyMenuActionUtil.onAction(exitMenuItem);
    }

    /**
     * Нажатие на пункт "О программе"
     */
    @FXML
    private void aboutMenuItemClicked() {
        logger.info("Нажатие на button 'О программе'");
        app.AboutMe();
    }

    /**
     * Нажатие на выход из аккаунта пользователя
     */
    @FXML
    private void exitMenuItemClicked() {
        //Останавливаем все потоки (лонгпул и проверка интернета)
        RunnableManager.interruptAll();
        //Удаляем ключ авторизации
        app.getAuthUtil().writeKey("");
        mainMenuBar.setDisable(true);
        logger.info("Осуществлен выход из профиля");
        app.myStart(app.getPrimaryStage());
    }

    /**
     * Обработчик нажатия на комнату-чат
     *
     * @param room - объект комнаты
     */
    private void showChatRoomDetails(Room room) {

        //Отображаем отправку сообщений
        newMessageText.setDisable(false);
        chatUsersButton.setDisable(false);
        chatUsersButton.setOpacity(1);
        sendMessageButton.setOpacity(1);
        newMessageText.setOpacity(1);

        //Выставляем id текущей комнаты
        messageData.clear();
        if (room != null) {
            myAPI.setCurrentRoomId(room.getId());
            messageData.addAll(room.getMessages());
            messageTable.setItems(messageData);
        }

        //Отчистка поля отправки сообщения
        newMessageText.clear();
        //Пролистываем до конца
        messageTable.scrollTo(messageData.get(messageData.size() - 1));
    }

    /**
     * Показ пользователей комнаты
     */
    @FXML
    private void chatUsersButtonClicked() {
        app.showCurrentRoomUsers();
        logger.info("Нажатие на button показа пользователей комнаты");
    }
}
