package com.example.dao;

import com.example.model.Inventory;
import com.example.model.PriceHistory;
import com.example.model.Product;

import java.sql.*;

public class DatabaseHandler {
    private Connection connection;

    public DatabaseHandler(Connection connection) {
        this.connection = connection;
    }

    public ResultSet getUserData(String user, String password) throws SQLException {
        ResultSet result = null;
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = null;

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);
            result = preparedStatement.executeQuery();
        }catch (SQLException e){
            throw e;
        }
        return result;
    }

    /*
    ESTA ES LA SECCION PARA LOS PRODUCTOS
     */

    public ResultSet getAllProductsData() throws SQLException {

        ResultSet result = null;
        String query = "SELECT * FROM productos";
        PreparedStatement preparedStatement = null;

        try{
            preparedStatement = connection.prepareStatement(query);
            result = preparedStatement.executeQuery();
        }catch (SQLException e){
            throw e;
        }
        return result;
    }

    public int saveProductData(Product product) throws SQLException {
        String query = "INSERT INTO productos (codigoBarra, nombre, marca, tipo, contenido, unidadMedida, " +
                "stockMinimo, stockMaximo, presentacion, descripcion, activo, rutaImagen, fechaAlta, " +
                "perecedero, ubicacionTienda) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, product.getCodigoBarra());
            preparedStatement.setString(2, product.getNombre());
            preparedStatement.setString(3, product.getMarca());
            preparedStatement.setString(4, product.getTipo());
            preparedStatement.setString(5, product.getContenido());
            preparedStatement.setString(6, product.getUnidadMedida());
            preparedStatement.setInt(7, product.getStockMinimo());
            preparedStatement.setInt(8, product.getStockMaximo());
            preparedStatement.setString(9, product.getPresentacion());
            preparedStatement.setString(10, product.getDescripcion());
            preparedStatement.setBoolean(11, product.isActivo());
            preparedStatement.setString(12, product.getRutaImagen());
            preparedStatement.setDate(13, Date.valueOf(product.getFechaAlta()));
            preparedStatement.setBoolean(14, product.isPerecedero());
            preparedStatement.setString(15, product.getPasillo());
            return preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.err.println("Error al guardar el producto: " + e.getMessage());
            throw e;
        }

    }

    public int deleteProductData(String codigoBarra) throws SQLException {
        String query = "DELETE FROM productos WHERE codigoBarra = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, codigoBarra);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar el producto: " + e.getMessage());
            throw e;
        }
    }

    public int updateProductData(String oldCodBar, Product newProduct) throws SQLException {
        String query = "UPDATE productos SET nombre = ?, marca = ?, tipo = ?, contenido = ?, " +
                "unidadMedida = ?, stockMinimo = ?, stockMaximo = ?, presentacion = ?, " +
                "descripcion = ?, activo = ?, rutaImagen = ?, fechaAlta = ?, " +
                "perecedero = ?, ubicacionTienda = ?, codigoBarra = ? WHERE codigoBarra = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, newProduct.getNombre());
            preparedStatement.setString(2, newProduct.getMarca());
            preparedStatement.setString(3, newProduct.getTipo());
            preparedStatement.setString(4, newProduct.getContenido());
            preparedStatement.setString(5, newProduct.getUnidadMedida());
            preparedStatement.setInt(6, newProduct.getStockMinimo());
            preparedStatement.setInt(7, newProduct.getStockMaximo());
            preparedStatement.setString(8, newProduct.getPresentacion());
            preparedStatement.setString(9, newProduct.getDescripcion());
            preparedStatement.setBoolean(10, newProduct.isActivo());
            preparedStatement.setString(11, newProduct.getRutaImagen());
            preparedStatement.setDate(12, Date.valueOf(newProduct.getFechaAlta()));
            preparedStatement.setBoolean(13, newProduct.isPerecedero());
            preparedStatement.setString(14, newProduct.getPasillo());
            preparedStatement.setString(15, newProduct.getCodigoBarra());
            preparedStatement.setString(16, oldCodBar);
            return preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.err.println("Error al eliminar el producto: " + e.getMessage());
            throw e;
        }
    }
    /*
    Esta es la seccion para el inventario
     */
    public ResultSet getAllInventoryData() throws SQLException {
        ResultSet result = null;
        String query = "SELECT * FROM inventario";
        PreparedStatement preparedStatement = null;

        try{
            preparedStatement = connection.prepareStatement(query);
            result = preparedStatement.executeQuery();
        }catch (SQLException e){
            throw e;
        }
        return result;
    }


    public ResultSet getInventoryData(String codigoBarra) throws SQLException {
        ResultSet result = null;
        String query = "SELECT * FROM inventario WHERE codigoBarra = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, codigoBarra);
            result = preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error al obtener datos de inventario: " + e.getMessage());
            throw e;
        }
        return result;
    }

    public int saveInventoryData(Inventory inventory) throws SQLException {
        String query = "INSERT INTO inventario (codigoBarra, existencias, precioActual, ultimaActualizacion) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, inventory.getCodigoBarraProducto());
            preparedStatement.setInt(2, inventory.getExistencias());
            preparedStatement.setDouble(3, inventory.getPrecioActual());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(inventory.getUltimaActualizacion()));
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar datos de inventario: " + e.getMessage());
            throw e;
        }
    }

    public int updateInventoryData(Inventory inventory) throws SQLException {
        String query = "UPDATE inventario SET existencias = ?, precioActual = ?, ultimaActualizacion = ? WHERE codigoBarra = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, inventory.getExistencias());
            preparedStatement.setDouble(2, inventory.getPrecioActual());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(inventory.getUltimaActualizacion()));
            preparedStatement.setString(4, inventory.getCodigoBarraProducto());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar datos de inventario: " + e.getMessage());
            throw e;
        }
    }

    /*
    SECCION PARA HISTORIAL DE PRECIOS
     */

    public int savePriceHistoryData(PriceHistory priceHistory) throws SQLException {
        String query = "INSERT INTO historial_precios (codigoBarra, precio, fechaCambio) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, priceHistory.getCodigoBarraProducto());
            preparedStatement.setDouble(2, priceHistory.getPrecio());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(priceHistory.getFechaCambio()));
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar historial de precios: " + e.getMessage());
            throw e;
        }
    }

    public ResultSet getPriceHistoryData(String codigoBarra) throws SQLException {
        ResultSet result = null;
        String query = "SELECT * FROM historial_precios WHERE codigoBarra = ? ORDER BY fechaCambio DESC";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, codigoBarra);
            result = preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error al obtener historial de precios: " + e.getMessage());
            throw e;
        }
        return result;
    }




}
