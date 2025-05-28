package com.example.controllers;

import com.example.model.AppModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class SideMenuController implements Initializable {
    @FXML
    private Label userLabel;
    @FXML
    private Button onOperations;
    @FXML
    private Button onReports;
//    public void init(String userName){
//        userLabel.setText(userName);
//    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListeners();
        userLabel.textProperty().bind(AppModel.getInstance().getCurrentUser().usernameProperty()  );
    }

    private void addListeners() {
        onOperations.setOnAction(event -> onOperations());
        onReports.setOnAction(event -> onReports());
    }

    private void onOperations() {
        AppModel.getInstance().getViewFactory().getClientSelectedMenuItem().set("Operations");
    }

    private void onReports() {

        AppModel.getInstance().getViewFactory().getClientSelectedMenuItem().set("Sales");
    }


}
