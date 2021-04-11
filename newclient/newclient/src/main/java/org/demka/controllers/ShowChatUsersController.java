package org.demka.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.demka.App;
import org.demka.api.MyAPI;
import org.demka.exceptions.EmptyAPIResponseException;
import org.demka.exceptions.FalseServerFlagException;
import org.demka.exceptions.RoomNotFoundException;
import org.demka.models.Room;
import org.demka.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ShowChatUsersController extends SuperPartController {

    private static final Logger logger = LoggerFactory.getLogger(ShowChatUsersController.class);
    @FXML
    private TableView<User> ChatUserTable;
    @FXML
    private TableColumn<User, String> ChatUserNameColumn;
    private ObservableList<User> ChatUserData = FXCollections.observableArrayList();
    private MyAPI APISession;

    /**
     * Метод инициализации (вызывается с Main)
     *
     * @param mainApp
     * @param dialogStage
     */
    @Override
    public void initialize(App mainApp, Stage dialogStage) {
        this.mainApp = mainApp;
        this.dialogStage = dialogStage;
        APISession = mainApp.getAPISession();

        ChatUserTable.setItems(ChatUserData);
        ChatUserNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        String currentRoomId = APISession.getCurrentRoomId();
        try {
            Room currentRoom = APISession.getRoomInfo(currentRoomId);
            dialogStage.setTitle("Информация о диалоге " + currentRoom.getName());
            List<User> roomUsersList = APISession.getUsersByRoom(currentRoomId);
            ChatUserData.addAll(roomUsersList);
            ChatUserNameColumn.setText("Участники (" + roomUsersList.size() + " всего)");
        } catch (FalseServerFlagException | EmptyAPIResponseException | RoomNotFoundException e) {
            e.printStackTrace();
            dialogStage.close();
        }
    }

    @FXML
    private void backButtonClicked() {
        logger.info("Выход из из подменю информации о пользователях чата");
        dialogStage.close();
    }

}
