package com.example.controllers;

import com.example.model.AppModel;
import com.example.model.Inventory;
import com.example.model.Product;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchProductController implements Initializable {

    @FXML
    private Button aceeptBtn;

    @FXML
    private TableColumn<Product, String> codBarCol;

    @FXML
    private TextField codBarSearch;

    @FXML
    private TableColumn<Product, Integer> existCol;

    @FXML
    private TableColumn<Product, String> nomCol;

    @FXML
    private TableColumn<Product, Double> priceCol;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, String> ubiCol;

    private Product selectedProduct;
    private ObservableList<Product> allProducts;
    private FilteredList<Product> filteredProducts;

    @FXML
    void acceptAction(ActionEvent event) {
        Stage stage = (Stage) aceeptBtn.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        codBarCol.setCellValueFactory(new PropertyValueFactory<>("codigoBarra"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        ubiCol.setCellValueFactory(new PropertyValueFactory<>("pasillo"));
        existCol.setCellValueFactory(cellData -> {
            Product product = cellData.getValue();
            Inventory inventory = AppModel.getInstance().getInventoryForProduct(product.getCodigoBarra());
            Integer stock = 0; // Default value
            if (inventory != null) {
                stock = inventory.getExistencias();
            }
            return new SimpleIntegerProperty(stock).asObject();
        });

        priceCol.setCellValueFactory(cellData -> {
            Product product = cellData.getValue();
            Inventory inventory = AppModel.getInstance().getInventoryForProduct(product.getCodigoBarra());
            Double price = 0.0; // Default value
            if (inventory != null) {
                price = inventory.getPrecioActual();
            }
            return new SimpleDoubleProperty(price).asObject();
        });

        allProducts = AppModel.getInstance().getProductContainer().getAll();
        filteredProducts = new FilteredList<>(allProducts, p -> true);
        productTable.setItems(filteredProducts);

        codBarSearch.setOnAction(event -> performSearch());

        productTable.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                selectedProduct = productTable.getSelectionModel().getSelectedItem();
                if (selectedProduct != null) {
                    Stage stage = (Stage) aceeptBtn.getScene().getWindow();
                    stage.close();
                }
            }
        });

        productTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedProduct = newSelection;
            }
        });

    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    private void performSearch() {
        String searchText = codBarSearch.getText().toLowerCase();
        filteredProducts.setPredicate(product -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            if (product.getNombre().toLowerCase().contains(searchText)) {
                return true;
            } else if (product.getCodigoBarra().toLowerCase().contains(searchText)) {
                return true;
            }
            return false;
        });
    }

}
