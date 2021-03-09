package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import sample.Main;
import sample.api.MyAPI;
import sample.models.Message;
import sample.models.Room;
import sample.models.User;
import sample.utils.MyLogger;

public class CreateNewRoomController{
    private Main mainApp;
    private Stage dialogStage;

    private ObservableList<User> AllUsersData = FXCollections.observableArrayList();
    private ObservableList<User> ChatUsersData = FXCollections.observableArrayList();

    private MyAPI APISession;
    @FXML
    private TableView<User> AllUsersTable;
    @FXML
    private TableColumn<User, String> AllUsersColumn;

    @FXML
    private TableView<User> ChatUsersTable;
    @FXML
    private TableColumn<User, String> ChatUsersColumn;

    //TODO: Инициализация двух таблиц
    //В табилце слева все возможные
    public void initialize(Main mainApp, Stage dialogStage) {
        this.mainApp = mainApp;
        this.dialogStage = dialogStage;

        //Инициализация таблиц
        AllUsersTable.setItems(AllUsersData);
        ChatUsersTable.setItems(ChatUsersData);

        //API, через которое взаимодействуем с миром
        APISession = mainApp.getAPISession();

        //Обработчик добавления пользователя в список участников
        AllUsersTable.getSelectionModel().selectedItemProperty().addListener(
                ((observableValue, oldValue, newValue) -> addUserToChat(newValue))
        );
        //Обработчик удаления пользователя из списка участников
        ChatUsersTable.getSelectionModel().selectedItemProperty().addListener(
                ((observableValue, oldValue, newValue) -> removeUserFromChat(newValue))
        );

        //Ресайз, зависящий от данных
        AllUsersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ChatUsersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //Отображение имен пользователей
        AllUsersColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        ChatUsersColumn.setCellValueFactory(cellData -> cellData.getValue().getUserNameProperty());
    }

    @FXML
    private void CancelButtonClicked(){
        dialogStage.close();
    }

    @FXML
    public void CreateRoomButtonClicker() {
        MyLogger.logger.info("*Кликнули на создание комнаты*");
    }

    /**
     * Добавление пользователя в чат
     * @param user
     */
    private void addUserToChat(User user){

    }

    /**
     * Удаление пользователя из чата
     * @param user
     */
    private void removeUserFromChat(User user){

    }
}
