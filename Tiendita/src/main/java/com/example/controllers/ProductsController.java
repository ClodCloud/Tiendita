package com.example.controllers;

import com.example.model.AppModel;
import com.example.model.Inventory;
import com.example.model.Product;
import com.example.utility.Utility;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProductsController implements Initializable {
//    private final StringProperty codigoBarra;
//    private final StringProperty nombre;
//    private final StringProperty marca;
//    private final StringProperty tipo;
//    private final StringProperty contenido;
//    private final StringProperty unidadMedida;
//    private final IntegerProperty stockMinimo;
//    private final IntegerProperty stockMaximo;
//    private final StringProperty presentacion;
//    private final StringProperty descripcion;
//    private final BooleanProperty activo;
//    private final StringProperty rutaImagen;
//    private final ObjectProperty<LocalDate> fechaAlta;
//    private final BooleanProperty perecedero;
//    private final IntegerProperty pasillo;
//    private final IntegerProperty estante;

    @FXML
    private Button updateBtn;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Boolean> activCol;

    @FXML
    private Button addProdBtn;

    @FXML
    private TableColumn<Product, String> codBarCol;

    @FXML
    private TableColumn<Product, String> contCol;

    @FXML
    private TableColumn<Product, String> descCol;

    @FXML
    private TableColumn<Product, LocalDate> fechaCol;

    @FXML
    private TableColumn<Product, String> marcCol;

    @FXML
    private TableColumn<Product, String> nomCol;

    @FXML
    private TableColumn<Product, String> pasCol;

    @FXML
    private TableColumn<Product, Boolean> pereceCol;

    @FXML
    private TableColumn<Product, String> presenCol;

    @FXML
    private TableColumn<Product, Integer> stockMaxCol;

    @FXML
    private TableColumn<Product, Integer> stockMinCol;

    @FXML
    private TableColumn<Product, String> tipoCol;

    @FXML
    private TableColumn<Product, String> uniMedCol;

    @FXML
    private Button deleteBtn;

    @FXML
    private TableColumn<Product, Integer> currentStockCol;
    @FXML
    private TableColumn<Product, Double> currentPriceCol;


    @FXML
    void addAction(ActionEvent event) {

        //Por mejorar, para que use el viewfactory
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/addProductForm.fxml"));
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load());
        }catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Tiendita");
        stage.show();
    }

    @FXML
    void deleteAction(ActionEvent event) {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Utility.showError("No se ha seleccionado ningun producto");
        }else{
            if(Utility.showConfirmation("Desea eliminar el elemento?")){
                AppModel.getInstance().deleteProductFromDatabase(selectedProduct);
                productTable.getSelectionModel().clearSelection();
                Utility.showSuccess("Producto eliminado con exito");
            }
        }
    }

    @FXML
    void updateAction(ActionEvent event) {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Utility.showError("No se ha seleccionado ningun producto");
        }else{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/updateProductForm.fxml"));
            UpdateProductController controller = new UpdateProductController();
            fxmlLoader.setController(controller);
            controller.setOldProduct(selectedProduct);
            Scene scene = null;
            try{
                scene = new Scene(fxmlLoader.load());
            }catch (Exception e) {
                e.printStackTrace();
            }

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Modificar producto");
            stage.show();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        codBarCol.setCellValueFactory(new PropertyValueFactory<>("key"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        marcCol.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tipoCol.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        contCol.setCellValueFactory(new PropertyValueFactory<>("contenido"));
        uniMedCol.setCellValueFactory(new PropertyValueFactory<>("unidadMedida"));
        stockMinCol.setCellValueFactory(new PropertyValueFactory<>("stockMinimo"));
        stockMaxCol.setCellValueFactory(new PropertyValueFactory<>("stockMaximo"));
        presenCol.setCellValueFactory(new PropertyValueFactory<>("presentacion"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        activCol.setCellValueFactory(new PropertyValueFactory<>("activo"));
        fechaCol.setCellValueFactory(new PropertyValueFactory<>("fechaAlta"));
        pereceCol.setCellValueFactory(new PropertyValueFactory<>("perecedero"));
        pasCol.setCellValueFactory(new PropertyValueFactory<>("pasillo"));

        currentStockCol.setCellValueFactory(cellData -> {
            Product product = cellData.getValue();
            Inventory inventory = AppModel.getInstance().getInventoryForProduct(product.getCodigoBarra());
            Integer stock = 0; // Default value
            if (inventory != null) {
                stock = inventory.getExistencias();
            }
            return new SimpleIntegerProperty(stock).asObject();
        });

        currentPriceCol.setCellValueFactory(cellData -> {
            Product product = cellData.getValue();
            Inventory inventory = AppModel.getInstance().getInventoryForProduct(product.getCodigoBarra());
            Double price = 0.0; // Default value
            if (inventory != null) {
                price = inventory.getPrecioActual();
            }
            return new SimpleDoubleProperty(price).asObject();
        });
        productTable.setItems(AppModel.getInstance().getProductContainer().getAll());
    }
}
