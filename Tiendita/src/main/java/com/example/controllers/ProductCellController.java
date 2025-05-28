package com.example.controllers;

import com.example.model.Product;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductCellController implements Initializable {
    public Label producName;

    private final Product product;

    public ProductCellController(Product product) {
        this.product = product;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
