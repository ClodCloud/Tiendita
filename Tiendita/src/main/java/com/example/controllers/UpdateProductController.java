package com.example.controllers;

import com.example.model.AppModel;
import com.example.model.Product;
import com.example.utility.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateProductController implements Initializable {
    private Product oldProduct;

    @FXML
    private CheckBox activBox;

    @FXML
    private DatePicker altaDate;

    @FXML
    private TextField codBar;

    @FXML
    private TextField contLbl;

    @FXML
    private TextField descLbl;

    @FXML
    private Button guardarBtn;

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
    private TextField stockField;

    @FXML
    private ComboBox<String> tipoBox;

    @FXML
    private TextField unMedLbl;

    @FXML
    void guardarAction(ActionEvent event) {
        if (Utility.showConfirmation("Se guardarán todos los cambios realizados. ¿Deseas continuar?")) {
            Product product = new Product(codBar.getText());
            product.setNombre(nomLbl.getText());
            product.setMarca(marLbl.getText());
            product.setTipo(tipoBox.getValue());
            product.setContenido(contLbl.getText());
            product.setUnidadMedida(unMedLbl.getText());
            try {
                product.setStockMaximo(Integer.parseInt(maxField.getText()));
                product.setStockMinimo(Integer.parseInt(minField.getText()));
            } catch (NumberFormatException e) {
                Utility.showError("Stock Mínimo y Máximo deben ser números válidos.");
                return;
            }
            product.setPresentacion(presenBox.getValue());
            product.setDescripcion(descLbl.getText());
            product.setActivo(activBox.isSelected());
            product.setRutaImagen(pasilloBox.getValue());
            product.setFechaAlta(altaDate.getValue());
            product.setPerecedero(perecederoCheck.isSelected());
            product.setPasillo(pasilloBox.getValue());
            AppModel.getInstance().updateProductFromDatabase(oldProduct, product);
            Stage stage = (Stage) guardarBtn.getScene().getWindow();
            stage.close();
        }
    }

    public void setOldProduct(Product product) {
        this.oldProduct = product;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        codBar.setText(oldProduct.getCodigoBarra());
        nomLbl.setText(oldProduct.getNombre());
        marLbl.setText(oldProduct.getMarca());
        tipoBox.setValue(oldProduct.getTipo());
        contLbl.setText(oldProduct.getContenido());
        unMedLbl.setText(oldProduct.getUnidadMedida());
        minField.setText("" + oldProduct.getStockMinimo());
        maxField.setText("" + oldProduct.getStockMaximo());
        presenBox.setValue(oldProduct.getPresentacion());
        descLbl.setText(oldProduct.getDescripcion());
        activBox.setSelected(oldProduct.isActivo());
        altaDate.setValue(oldProduct.getFechaAlta());
        perecederoCheck.setSelected(oldProduct.isPerecedero());
        pasilloBox.setValue(oldProduct.getPasillo());
        presenBox.getItems().addAll("Botella", "Caja", "Bolsa", "Lata");
        tipoBox.getItems().addAll("Bebidas", "Comestibles", "Electrónicos", "Otros");
        pasilloBox.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9");
    }
}
