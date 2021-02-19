package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class Controller {

    @FXML
    private Label AppName;
    @FXML
    private JFXTextField LoginTextField;
    @FXML
    private JFXTextField PasswordTextField;
    @FXML

    public void mainButtonClick() throws IOException {
        //alert.initOwner(root.getPrimaryStage );

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("ЧАТ");
        stage.show();
    }

}
