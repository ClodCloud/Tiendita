package com.example.model;

import com.example.interfaces.Keyable;
import javafx.beans.property.*;

import java.time.LocalDate;

public class Product implements Keyable {
    //todas son final para indicar que siempre van a ser de su property, además usamos que sea de property para bindearlos a sus listas y que se actualizen
    private final StringProperty codigoBarra;
    private final StringProperty nombre;
    private final StringProperty marca;
    private final StringProperty tipo;
    private final StringProperty contenido;
    private final StringProperty unidadMedida;
    private final IntegerProperty stockMinimo;
    private final IntegerProperty stockMaximo;
    private final StringProperty presentacion;
    private final StringProperty descripcion;
    private final BooleanProperty activo;
    private final StringProperty rutaImagen;
    private final ObjectProperty<LocalDate> fechaAlta;
    private final BooleanProperty perecedero;
    private final StringProperty pasillo;

    //constructor para inicializar a la hora de cargar todos los productos
    public Product(String codigoBarra, String nombre, String marca, String tipo, String contenido,
                   String unidadMedida, int stockMinimo, int stockMaximo, String presentacion,
                   String descripcion, boolean activo, String rutaImagen, LocalDate fechaAlta,
                   boolean perecedero, String pasillo) {
        this.codigoBarra = new SimpleStringProperty(this, "codigoBarra", codigoBarra);
        this.nombre = new SimpleStringProperty(this, "nombre", nombre);
        this.marca = new SimpleStringProperty(this, "marca", marca);
        this.tipo = new SimpleStringProperty(this, "tipo", tipo);
        this.contenido = new SimpleStringProperty(this, "contenido", contenido);
        this.unidadMedida = new SimpleStringProperty(this, "unidadMedida", unidadMedida);
        this.stockMinimo = new SimpleIntegerProperty(this, "stockMinimo", stockMinimo);
        this.stockMaximo = new SimpleIntegerProperty(this, "stockMaximo", stockMaximo);
        this.presentacion = new SimpleStringProperty(this, "presentacion", presentacion);
        this.descripcion = new SimpleStringProperty(this, "descripcion", descripcion);
        this.activo = new SimpleBooleanProperty(this, "activo", activo);
        this.rutaImagen = new SimpleStringProperty(this, "rutaImagen", rutaImagen);
        this.fechaAlta = new SimpleObjectProperty<>(this, "fechaAlta", fechaAlta);
        this.perecedero = new SimpleBooleanProperty(this, "perecedero", perecedero);

        this.pasillo = new SimpleStringProperty(this, "pasillo", pasillo);
    }

    //constructor para cada que se modifique un producto agregarlo
    public Product(String codigoBarra) {
        this.codigoBarra = new SimpleStringProperty(this, "codigoBarra", codigoBarra);
        this.nombre = new SimpleStringProperty(this, "nombre", "");
        this.marca = new SimpleStringProperty(this, "marca", "");
        this.tipo = new SimpleStringProperty(this, "tipo", "");
        this.contenido = new SimpleStringProperty(this, "contenido", "");
        this.unidadMedida = new SimpleStringProperty(this, "unidadMedida", "");
        this.stockMinimo = new SimpleIntegerProperty(this, "stockMinimo", 0);
        this.stockMaximo = new SimpleIntegerProperty(this, "stockMaximo", 0);
        this.presentacion = new SimpleStringProperty(this, "presentacion", "");
        this.descripcion = new SimpleStringProperty(this, "descripcion", "");
        this.activo = new SimpleBooleanProperty(this, "activo", false);
        this.rutaImagen = new SimpleStringProperty(this, "rutaImagen", "");
        this.fechaAlta = new SimpleObjectProperty<>(this, "fechaAlta", null);
        this.perecedero = new SimpleBooleanProperty(this, "perecedero", false);
        this.pasillo = new SimpleStringProperty(this, "pasillo", "");
    }



    @Override
    public String getKey() {
        return codigoBarra.get();
    }

    public StringProperty codigoBarraProperty() { return codigoBarra; }
    public StringProperty nombreProperty() { return nombre; }
    public StringProperty marcaProperty() { return marca; }
    public StringProperty tipoProperty() { return tipo; }
    public StringProperty contenidoProperty() { return contenido; }
    public StringProperty unidadMedidaProperty() { return unidadMedida; }
    public IntegerProperty stockMinimoProperty() { return stockMinimo; }
    public IntegerProperty stockMaximoProperty() { return stockMaximo; }
    public StringProperty presentacionProperty() { return presentacion; }
    public StringProperty descripcionProperty() { return descripcion; }
    public BooleanProperty activoProperty() { return activo; }
    public StringProperty rutaImagenProperty() { return rutaImagen; }
    public ObjectProperty<LocalDate> fechaAltaProperty() { return fechaAlta; }
    public BooleanProperty perecederoProperty() { return perecedero; }
    public StringProperty pasilloProperty() { return pasillo;}

    public String getCodigoBarra() { return codigoBarra.get(); }
    public String getNombre() { return nombre.get(); }
    public String getMarca() { return marca.get(); }
    public String getTipo() { return tipo.get(); }
    public String getContenido() { return contenido.get(); }
    public String getUnidadMedida() { return unidadMedida.get(); }
    public int getStockMinimo() { return stockMinimo.get(); }
    public int getStockMaximo() { return stockMaximo.get(); }
    public String getPresentacion() { return presentacion.get(); }
    public String getDescripcion() { return descripcion.get(); }
    public boolean isActivo() { return activo.get(); }
    public String getRutaImagen() { return rutaImagen.get(); }
    public LocalDate getFechaAlta() { return fechaAlta.get(); }
    public boolean isPerecedero() { return perecedero.get(); }
    public String getPasillo() { return pasillo.get(); }

    public void setCodigoBarra(String codigoBarra) { this.codigoBarra.set(codigoBarra); }
    public void setNombre(String nombre) { this.nombre.set(nombre); }
    public void setMarca(String marca) { this.marca.set(marca); }
    public void setTipo(String tipo) { this.tipo.set(tipo); }
    public void setContenido(String contenido) { this.contenido.set(contenido); }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida.set(unidadMedida); }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo.set(stockMinimo); }
    public void setStockMaximo(int stockMaximo) { this.stockMaximo.set(stockMaximo); }
    public void setPresentacion(String presentacion) { this.presentacion.set(presentacion); }
    public void setDescripcion(String descripcion) { this.descripcion.set(descripcion); }
    public void setActivo(boolean activo) { this.activo.set(activo); }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen.set(rutaImagen); }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta.set(fechaAlta); }
    public void setPerecedero(boolean perecedero) { this.perecedero.set(perecedero); }
    public void setPasillo(String pasillo) { this.pasillo.set(pasillo); }


}
