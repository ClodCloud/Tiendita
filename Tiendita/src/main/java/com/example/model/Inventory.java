package com.example.model;

import com.example.interfaces.Keyable;
import javafx.beans.property.*;

import java.time.LocalDateTime;

public class Inventory implements Keyable {
    private final StringProperty codigoBarrasProducto;
    private final IntegerProperty existencias;
    private final DoubleProperty precioActual;
    private final ObjectProperty<LocalDateTime> ultimaActualizacion;

    public Inventory(String codigoBarraProducto, int existencias, double precioActual, LocalDateTime ultimaActualizacion) {
        this.codigoBarrasProducto = new SimpleStringProperty(this, "codigoBarraProducto", codigoBarraProducto);
        this.existencias = new SimpleIntegerProperty(this, "existencias", existencias);
        this.precioActual = new SimpleDoubleProperty(this, "precioActual", precioActual);
        this.ultimaActualizacion = new SimpleObjectProperty<>(this, "ultimaActualizacion", ultimaActualizacion);
    }

    public StringProperty codigoBarraProductoProperty() { return codigoBarrasProducto; }
    public IntegerProperty existenciasProperty() { return existencias; }
    public DoubleProperty precioActualProperty() { return precioActual; }
    public ObjectProperty<LocalDateTime> ultimaActualizacionProperty() { return ultimaActualizacion; }

    public String getCodigoBarraProducto() { return codigoBarrasProducto.get(); }
    public int getExistencias() { return existencias.get(); }
    public double getPrecioActual() { return precioActual.get(); }
    public LocalDateTime getUltimaActualizacion() { return ultimaActualizacion.get(); }

    public void setExistencias(int existencias) { this.existencias.set(existencias); }
    public void setPrecioActual(double precioActual) { this.precioActual.set(precioActual); }
    public void setUltimaActualizacion(LocalDateTime ultimaActualizacion) { this.ultimaActualizacion.set(ultimaActualizacion); }

    @Override
    public String getKey() {
        return codigoBarrasProducto.get();
    }

}
