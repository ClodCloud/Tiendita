package com.example.controllers;

import com.example.model.Product;
import com.example.model.SalesItem;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SalesDashboardController implements Initializable {

    @FXML
    private TableColumn<SalesItem, Integer> cantidadCol;

    @FXML
    private TableColumn<SalesItem, String> codBarCol;

    @FXML
    private TableColumn<SalesItem, String> descripCol;

    @FXML
    private TableColumn<SalesItem, Integer> existenciasCol;

    @FXML
    private TableColumn<SalesItem, Double> precioCol;

    @FXML
    private TableView<SalesItem> salesTable;

    @FXML
    private Button searchBtn;

    @FXML
    private Label totalLabel;

    @FXML
    private TableColumn<SalesItem, Double> subtotalCol;

    private ObservableList<SalesItem> currentSaleItems;

    @FXML
    private AnchorPane rootPane;

    @FXML
    void searchAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/searchProductView.fxml")); //
            Scene scene = new Scene(fxmlLoader.load());
            SearchProductController searchController = fxmlLoader.getController(); // Get controller instance

            Stage searchStage = new Stage();
            searchStage.setTitle("Buscar Producto");
            searchStage.setScene(scene);
            searchStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows
            searchStage.initOwner(((Node)event.getSource()).getScene().getWindow()); // Set owner

            searchStage.showAndWait(); // Wait for the search window to close

            Product selectedProduct = searchController.getSelectedProduct(); //

            if (selectedProduct != null) {
                // Check if product already in cart
                Optional<SalesItem> existingItemOpt = currentSaleItems.stream()
                        .filter(item -> item.getProduct().getKey().equals(selectedProduct.getKey())) //
                        .findFirst();

                if (existingItemOpt.isPresent()) {
                    // Product exists, increment quantity
                    SalesItem existingItem = existingItemOpt.get();
                    // Here you might want to ask for quantity or just increment by 1
                    // For now, let's assume we ask or have a default way to handle quantity increase
                    existingItem.incrementQuantity(1); // Assuming default increment of 1
                    salesTable.refresh(); // Refresh table to show updated quantity and subtotal
                } else {
                    // Product not in cart, add as new SalesItem
                    // For now, default quantity is 1. You can add a dialog to ask for quantity.
                    SalesItem newItem = new SalesItem(selectedProduct, 1);
                    currentSaleItems.add(newItem);

                }
                updateTotal();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Consider showing an error dialog to the user via Utility.showError()
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentSaleItems = FXCollections.observableArrayList();
        salesTable.setItems(currentSaleItems);

        codBarCol.setCellValueFactory(new PropertyValueFactory<>("codigoBarra"));
        descripCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        cantidadCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        existenciasCol.setCellValueFactory(new PropertyValueFactory<>("currentStock"));
        precioCol.setCellValueFactory(new PropertyValueFactory<>("priceAtSale"));
        subtotalCol.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        updateTotal();
        Platform.runLater(() -> {
            setListeners();
        });
    }



    private void updateTotal() {
        double total = 0.0;
        for (SalesItem item : currentSaleItems) {
            total += item.getSubtotal(); //
        }
        totalLabel.setText("$ " + String.format("%.2f", total));
    }

    private void setListeners(){
        Scene scene = searchBtn.getScene();
        scene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.F2){
                System.out.println("F2");
                searchBtn.fire();
            }
        });
    }


}
