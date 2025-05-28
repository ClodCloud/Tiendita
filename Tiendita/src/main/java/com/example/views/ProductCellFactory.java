package com.example.views;

import com.example.model.Product;
import com.example.controllers.ProductCellController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class ProductCellFactory extends ListCell<Product> {

    protected void updateItem(Product product, boolean empty) {
        super.updateItem(product, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductCell.fxml"));
            ProductCellController controller = new ProductCellController(product);
            fxmlLoader.setController(controller);
            setText(null);
            try{
                setGraphic(fxmlLoader.load());
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
