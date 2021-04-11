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
    private final ObservableList<User> allUsersData = FXCollections.observableArrayList();
    private final ObservableList<User> chatUsersData = FXCollections.observableArrayList();
    private MyAPI myAPI;
    @FXML
    private TableView<User> allUsersTable;
    @FXML
    private TableColumn<User, String> allUsersColumn;
    @FXML
    private TableView<User> chatUsersTable;
    @FXML
    private TableColumn<User, String> chatUsersColumn;
    @FXML
    private Button createRoomButton;
    @FXML
    private TextField chatName;

    @Override
    public void initialize(App app, Stage dialogStage) {
        this.app = app;
        this.dialogStage = dialogStage;

        //Инициализация таблиц
        allUsersTable.setItems(allUsersData);
        chatUsersTable.setItems(chatUsersData);

        //API, через которое взаимодействуем с миром
        myAPI = app.getMyAPI();

        //Ресайз, зависящий от данных
        allUsersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        chatUsersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //Отображение имен пользователей
        allUsersColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        chatUsersColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());

        allUsersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        chatUsersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Добавляем пользователей системы
        try {
            allUsersData.addAll(myAPI.getUsers(null));
        } catch (FalseServerFlagException | EmptyAPIResponseException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelButtonClicked() {
        dialogStage.close();
    }

    @FXML
    public void createRoomButtonClicked() {
        String localeChatName;
        StringBuilder usersIds = new StringBuilder();
        //Если не было задано название конфы, то генерируем ее из имен
        if (chatName.getText().equals("")) {

            StringBuilder ChatNameBuilder = new StringBuilder();
            ChatNameBuilder.append("Чат с ");
            for (User user : chatUsersData) {
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
            localeChatName = chatName.getText();
        }

        //Добавляем id всех пользователей
        for (User user : chatUsersData) {
            usersIds.append(user.getId());
            usersIds.append(",");
        }
        //Удаляем последнюю запятую
        usersIds.setLength(usersIds.length() - 1);

        //Создаем комнату
        try {
            myAPI.createRoom(localeChatName, usersIds.toString());
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

        ObservableList<User> selectedItems = allUsersTable.getSelectionModel().getSelectedItems();
        //Буферный список, который не изменяется
        List<User> selectedUsers = new ArrayList<User>();
        selectedUsers.addAll(selectedItems);

        for (int i = 0; i < selectedUsers.size(); i++) {
            User user = selectedUsers.get(i);
            logger.info("addUserToChat - добавили пользователя " + user.getName());
            allUsersData.remove(user);
            chatUsersData.add(user);
        }

        createRoomButton.setDisable(chatUsersData.size() <= 0);

    }

    /**
     * Удаление пользователя из чата
     */
    @FXML
    private void removeUserFromChat() {

        ObservableList<User> selectedItems = chatUsersTable.getSelectionModel().getSelectedItems();
        //Буферный список, который не изменяется
        List<User> selectedUsers = new ArrayList<User>();
        selectedUsers.addAll(selectedItems);

        for (int i = 0; i < selectedUsers.size(); i++) {
            User user = selectedUsers.get(i);
            logger.info("addUserToChat - удалили пользователя " + user.getName());
            chatUsersData.remove(user);
            allUsersData.add(user);
        }

        createRoomButton.setDisable(chatUsersData.size() <= 0);
    }
}
