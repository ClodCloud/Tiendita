package com.example.controllers;

import com.example.model.AppModel;
import com.example.model.Inventory;
import com.example.model.PriceHistory;
import com.example.model.Product;
import com.example.utility.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AddProductFormController implements Initializable {

    @FXML
    private CheckBox activBox;

    @FXML
    private DatePicker altaDate;

    @FXML
    private TextField codBar;

    @FXML
    private TextField contLbl;
    @FXML
    private TextField priceField;

    @FXML
    private TextField stockField;

    @FXML
    private TextField descLbl;

    @FXML
    private TextField marLbl;

    @FXML
    private TextField maxField;

    @FXML
    private TextField minField;

    @FXML
    private TextField nomLbl;

    @FXML
    private ChoiceBox<String> pasilloBox;

    @FXML
    private CheckBox perecederoCheck;

    @FXML
    private ComboBox<String> presenBox;

    @FXML
    private Spinner<?> stockSpin;

    @FXML
    private ComboBox<String> tipoBox;

    @FXML
    private TextField unMedLbl;

    @FXML
    private Button guardarBtn;

    @FXML
    void guardarAction(ActionEvent event) {
        Product product = new Product(codBar.getText());
        product.setNombre(nomLbl.getText());
        product.setMarca(marLbl.getText());
        product.setTipo(tipoBox.getValue());
        product.setContenido(contLbl.getText());
        product.setUnidadMedida(unMedLbl.getText());
        double initialPrice;
        int initialStock;
        try {
            product.setStockMaximo(Integer.parseInt(maxField.getText()));
            product.setStockMinimo(Integer.parseInt(minField.getText()));
            initialPrice = Double.parseDouble(priceField.getText());
            initialStock = Integer.parseInt(stockField.getText());
        } catch (NumberFormatException e) {
            Utility.showError("Stock Mínimo, Máximo, Precio y Existencias deben ser números válidos.");
            return;
        }
        product.setPresentacion(presenBox.getValue());
        product.setDescripcion(descLbl.getText());
        product.setActivo(activBox.isSelected());
        product.setRutaImagen(pasilloBox.getValue());
        product.setFechaAlta(altaDate.getValue());
        product.setPerecedero(perecederoCheck.isSelected());
        product.setPasillo(pasilloBox.getValue());
        AppModel.getInstance().saveProductToDatabase(product);

        Inventory initialInventory = new Inventory(codBar.getText(), initialStock, initialPrice, LocalDateTime.now());
        AppModel.getInstance().addInventoryRecord(initialInventory);

        PriceHistory initialPriceHistory = new PriceHistory(codBar.getText(), initialPrice, LocalDateTime.now());
        AppModel.getInstance().addPriceHistoryRecord(initialPriceHistory);
        Utility.showSuccess("Producto y su inventario inicial creados correctamente.");
        AppModel.getInstance().refreshInventories();

        Stage stage = (Stage) guardarBtn.getScene().getWindow();
        stage.close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        presenBox.getItems().addAll("Botella", "Caja", "Bolsa", "Lata");
        tipoBox.getItems().addAll("Bebidas", "Comestibles", "Electrónicos", "Otros");
        pasilloBox.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9");
        altaDate.setValue(LocalDate.now());
    }
}
