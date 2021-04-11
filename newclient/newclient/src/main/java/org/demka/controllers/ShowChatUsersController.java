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
    private final ObservableList<User> chatUserData = FXCollections.observableArrayList();
    @FXML
    private TableView<User> chatUserTable;
    @FXML
    private TableColumn<User, String> chatUserNameColumn;
    private MyAPI myAPI;

    /**
     * Метод инициализации (вызывается с Main)
     *
     * @param app
     * @param dialogStage
     */
    @Override
    public void initialize(App app, Stage dialogStage) {
        this.app = app;
        this.dialogStage = dialogStage;
        myAPI = app.getMyAPI();

        chatUserTable.setItems(chatUserData);
        chatUserNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        String currentRoomId = myAPI.getCurrentRoomId();
        try {
            Room currentRoom = myAPI.getRoomInfo(currentRoomId);
            dialogStage.setTitle("Информация о диалоге " + currentRoom.getName());
            List<User> roomUsersList = myAPI.getUsersByRoom(currentRoomId);
            chatUserData.addAll(roomUsersList);
            chatUserNameColumn.setText("Участники (" + roomUsersList.size() + " всего)");
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
