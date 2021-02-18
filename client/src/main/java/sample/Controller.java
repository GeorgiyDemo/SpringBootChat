package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.awt.*;

public class Controller {

    @FXML
    private Label AppName;
    @FXML
    private JFXTextField LoginTextField;
    @FXML
    private JFXTextField PasswordTextField;
    @FXML

    public void mainButtonClick() {
        AppName.setText("ЛОЛ ЛОГИН ЕСТЬ");
        Alert alert = new Alert(Alert.AlertType.WARNING);
        //alert.initOwner(root.getPrimaryStage );
    }

}
