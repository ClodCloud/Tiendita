package com.example.controllers;

import com.example.model.AppModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class OperationsController implements Initializable {

    @FXML
    private AnchorPane onSellings;
    @FXML
    private AnchorPane onProducts;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListeners();
    }

    private void addListeners() {
        onSellings.setOnMouseClicked(event -> onSellings());
        onProducts.setOnMouseClicked(event -> onProducts());
    }

    private void onProducts() {
        System.out.println("onProducts");
        AppModel.getInstance().getViewFactory().getClientSelectedMenuItem().set("Products");
    }

    private void onSellings() {
        AppModel.getInstance().getViewFactory().getClientSelectedMenuItem().set("Sales");
    }
}
