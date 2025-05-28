package com.example.controllers;

import com.example.model.AppModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    @FXML
    public BorderPane client_parent;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AppModel.getInstance().getViewFactory().getClientSelectedMenuItem().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Sales" -> client_parent.setCenter(AppModel.getInstance().getViewFactory().getSalesView());
                case "Products" -> client_parent.setCenter(AppModel.getInstance().getViewFactory().getProductsView());
                default -> client_parent.setCenter(AppModel.getInstance().getViewFactory().getOperationsView());
            }
        });
    }
}
