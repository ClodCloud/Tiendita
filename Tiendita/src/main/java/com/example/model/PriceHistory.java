package com.example.model;

import com.example.interfaces.Keyable;
import javafx.beans.property.*;

import java.time.LocalDateTime;

public class PriceHistory implements Keyable {
    private final StringProperty codigoBarraProducto;
    private final DoubleProperty precio;
    private final ObjectProperty<LocalDateTime> fechaCambio;

    public PriceHistory(String codigoBarraProducto, double precio, LocalDateTime fechaCambio) {
        this.codigoBarraProducto = new SimpleStringProperty(this, "codigoBarraProducto", codigoBarraProducto);
        this.precio = new SimpleDoubleProperty(this, "precio", precio);
        this.fechaCambio = new SimpleObjectProperty<>(this, "fechaCambio", fechaCambio);
    }

    public StringProperty codigoBarraProductoProperty() { return codigoBarraProducto; }
    public DoubleProperty precioProperty() { return precio; }
    public ObjectProperty<LocalDateTime> fechaCambioProperty() { return fechaCambio; }

    public String getCodigoBarraProducto() { return codigoBarraProducto.get(); }
    public double getPrecio() { return precio.get(); }
    public LocalDateTime getFechaCambio() { return fechaCambio.get(); }

    @Override
    public String getKey() {
        return codigoBarraProducto.get() + "_" + fechaCambio.get().toString();
    }

}
