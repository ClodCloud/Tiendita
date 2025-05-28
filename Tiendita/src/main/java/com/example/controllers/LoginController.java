package com.example.controllers;

import com.example.model.AppModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.utility.Utility.showError;

public class  LoginController implements Initializable{
    @FXML
    private PasswordField passField;

    @FXML
    private TextField userField;
    @FXML
    private Button enterButton;


    public void onLogin()  {
        AppModel.getInstance().evaluateClientCredentials(userField.getText(), passField.getText());
        if(AppModel.getInstance().isUserLoginSuccessFlag()) {
            Stage stage1 = (Stage) enterButton.getScene().getWindow();
            AppModel.getInstance().getViewFactory().closeStage(stage1);
            AppModel.getInstance().getViewFactory().showClientWindow(userField.getText());
        }else{
            userField.clear();
            passField.clear();
            userField.requestFocus();
            showError("Usuario o contraseña incorrectos");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userField.setOnAction(e -> passField.requestFocus());
        passField.setOnAction(e -> enterButton.fire());
        enterButton.setOnAction(e -> onLogin());
    }
}