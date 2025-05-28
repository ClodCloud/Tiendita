package com.example.model;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class SalesItem {
    private final Product product;
    private final IntegerProperty quantity;
    private final DoubleProperty priceAtSale;
    private final IntegerProperty currentStock;
    private final DoubleProperty subtotal;

    public SalesItem(Product product, int initialQuantity) {
        this.product = product;
        this.quantity = new SimpleIntegerProperty(initialQuantity);
        this.currentStock = new SimpleIntegerProperty(getCurrentStock());

        Inventory inventory = AppModel.getInstance().getInventoryForProduct(product.getCodigoBarra()); //
        this.priceAtSale = new SimpleDoubleProperty(inventory != null ? inventory.getPrecioActual() : 0.0); //

        this.subtotal = new SimpleDoubleProperty();
        this.subtotal.bind(Bindings.multiply(this.quantity, this.priceAtSale));
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        if (quantity > 0) { // Basic validation
            this.quantity.set(quantity);
        }
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public void incrementQuantity(int amount) {
        this.setQuantity(this.getQuantity() + amount);
    }

    public double getPriceAtSale() {
        return priceAtSale.get();
    }

    public DoubleProperty priceAtSaleProperty() {
        return priceAtSale;
    }

    public double getSubtotal() {
        return subtotal.get();
    }

    public DoubleProperty subtotalProperty() {
        return subtotal;
    }

    public String getCodigoBarra() {
        return product.getCodigoBarra();
    }

    public String getNombre() {
        return product.getNombre();
    }

    public int getCurrentStock() {
        Inventory inventory = AppModel.getInstance().getInventoryForProduct(product.getCodigoBarra()); //
        return inventory != null ? inventory.getExistencias() : 0;
    }
}