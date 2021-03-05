package sample.controllers;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import sample.Main;

public class CreateNewRoomController{
    private Main mainApp;
    private Stage dialogStage;

    //TODO: Инициализация двух таблиц
    //В табилце слева все возможные
    public void initialize(Main mainApp, Stage dialogStage) {
        this.mainApp = mainApp;
        this.dialogStage = dialogStage;
    }

    @FXML
    private void CancelButtonClicked(){
        dialogStage.close();
    }
}
