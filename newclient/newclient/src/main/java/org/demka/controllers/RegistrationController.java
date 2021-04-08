package org.demka.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import org.demka.App;

public class RegistrationController extends SuperFullController {

    /**
     * Метод инициализации (вызывается с Main)
     * @param mainApp
     */

    @FXML
    private JFXButton BackButton;
    @FXML
    private JFXButton RegButton;

    @FXML
    private JFXTextField UserNameTextField;
    @FXML
    private JFXTextField EmailTextField;
    @FXML
    private JFXTextField PasswordTextField;

    @Override
    public void initialize(App mainApp) {
        this.mainApp = mainApp;
    }
}
