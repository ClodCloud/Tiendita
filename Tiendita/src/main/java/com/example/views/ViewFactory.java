package com.example.views;

import com.example.controllers.ClientController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {

    private final StringProperty clientSelectedMenuItem;
    private AnchorPane operationsView;
    private AnchorPane sellingsView;
    private AnchorPane productsView;

    public ViewFactory() {
        clientSelectedMenuItem = new SimpleStringProperty("");
    }

    public StringProperty getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public AnchorPane getOperationsView() {
        if (operationsView == null) {
            try{
                operationsView = new FXMLLoader(getClass().getResource("/fxml/operations.fxml")).load();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return operationsView;
    }

    public AnchorPane getSalesView() {
        if (sellingsView == null) {
            try{
                sellingsView = new FXMLLoader(getClass().getResource("/fxml/sales.fxml")).load();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sellingsView;
    }

    public AnchorPane getProductsView() {
        if (productsView == null) {
            try{
                productsView = new FXMLLoader(getClass().getResource("/fxml/Products.fxml")).load();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return productsView;
    }

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loginScreen.fxml"));
        createStage(loader);

    }

    public void showClientWindow(String name) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);
        createFullScreenStage(loader);
    }

    private void createFullScreenStage(FXMLLoader loader) {
        Scene sceneFull = null;
        try{
            sceneFull = new Scene(loader.load());
        }catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setScene(sceneFull);
        stage.setTitle("Tiendita");
        stage.setMaximized(true);
        stage.show();


    }

    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try{
            scene = new Scene(loader.load());
        }catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Tiendita");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
