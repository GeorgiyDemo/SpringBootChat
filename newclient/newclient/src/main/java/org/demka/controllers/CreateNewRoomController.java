package org.demka.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.demka.App;
import org.demka.api.MyAPI;
import org.demka.exceptions.EmptyAPIResponseException;
import org.demka.exceptions.FalseServerFlagException;
import org.demka.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CreateNewRoomController extends SuperPartController {

    private static final Logger logger = LoggerFactory.getLogger(CreateNewRoomController.class);
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
    @FXML
    private Button CreateRoomButton;
    @FXML
    private TextField ChatName;
    private boolean CustomChatName = false;

    @Override
    public void initialize(App mainApp, Stage dialogStage) {
        this.mainApp = mainApp;
        this.dialogStage = dialogStage;

        //Инициализация таблиц
        AllUsersTable.setItems(AllUsersData);
        ChatUsersTable.setItems(ChatUsersData);

        //API, через которое взаимодействуем с миром
        APISession = mainApp.getAPISession();

        //Ресайз, зависящий от данных
        AllUsersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ChatUsersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //Отображение имен пользователей
        AllUsersColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        ChatUsersColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());

        AllUsersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ChatUsersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Добавляем пользователей системы
        try {
            AllUsersData.addAll(APISession.getUsers(null));
        } catch (FalseServerFlagException | EmptyAPIResponseException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void CancelButtonClicked() {
        dialogStage.close();
    }

    @FXML
    public void CreateRoomButtonClicked() {
        String localeChatName;
        StringBuilder usersIds = new StringBuilder();
        //Если не было задано название конфы, то генерируем ее из имен
        if (ChatName.getText().equals("")) {

            StringBuilder ChatNameBuilder = new StringBuilder();
            ChatNameBuilder.append("Чат с ");
            for (User user : ChatUsersData) {
                ChatNameBuilder.append(user.getName()).append(", ");
            }
            //Удаляем последнюю запятую
            ChatNameBuilder.setLength(ChatNameBuilder.length() - 2);
            //Если длина слишком большая - сокращаем
            if (ChatNameBuilder.length() > 100) {
                ChatNameBuilder.setLength(100);
                ChatNameBuilder.append("..");
            }
            localeChatName = ChatNameBuilder.toString();

        } else {
            localeChatName = ChatName.getText();
        }

        //Добавляем id всех пользователей
        for (User user : ChatUsersData) {
            usersIds.append(user.getId());
            usersIds.append(",");
        }
        //Удаляем последнюю запятую
        usersIds.setLength(usersIds.length() - 1);

        //Создаем комнату
        try {
            APISession.createRoom(localeChatName, usersIds.toString());
        } catch (FalseServerFlagException | EmptyAPIResponseException e) {
            e.printStackTrace();
        }

        dialogStage.close();
    }

    /**
     * Добавление пользователя в чат
     */
    @FXML
    private void addUserToChat() {

        ObservableList<User> selectedItems = AllUsersTable.getSelectionModel().getSelectedItems();
        //Буферный список, который не изменяется
        List<User> selectedUsers = new ArrayList<User>();
        selectedUsers.addAll(selectedItems);

        for (int i = 0; i < selectedUsers.size(); i++) {
            User user = selectedUsers.get(i);
            logger.info("addUserToChat - добавили пользователя " + user.getName());
            AllUsersData.remove(user);
            ChatUsersData.add(user);
        }

        CreateRoomButton.setDisable(ChatUsersData.size() <= 0);

    }

    /**
     * Удаление пользователя из чата
     */
    @FXML
    private void removeUserFromChat() {

        ObservableList<User> selectedItems = ChatUsersTable.getSelectionModel().getSelectedItems();
        //Буферный список, который не изменяется
        List<User> selectedUsers = new ArrayList<User>();
        selectedUsers.addAll(selectedItems);

        for (int i = 0; i < selectedUsers.size(); i++) {
            User user = selectedUsers.get(i);
            logger.info("addUserToChat - удалили пользователя " + user.getName());
            ChatUsersData.remove(user);
            AllUsersData.add(user);
        }

        CreateRoomButton.setDisable(ChatUsersData.size() <= 0);
    }
}
