package com.example.services;

import com.example.dao.DatabaseHandler;
import com.example.dao.TienditaDatabaseManager;
import com.example.model.Contenedor;
import com.example.model.Inventory;
import com.example.model.PriceHistory;
import com.example.utility.Utility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static com.example.dao.DaoConfiguration.*;

public class InventoryService {

    private final TienditaDatabaseManager databaseManager;

    public InventoryService(TienditaDatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public Contenedor<Inventory> getAllInventories() {
        Contenedor<Inventory> inventoryContainer = new Contenedor<>();
        if (!databaseManager.hacerConexion().equals("exito")) {
            Utility.showError("No se pudo establecer conexión a la base de datos para obtener todos los inventarios.");
            return inventoryContainer;
        }
        Connection connection = databaseManager.getConnection();
        DatabaseHandler databaseHandler = new DatabaseHandler(connection);
        try (ResultSet resultSet = databaseHandler.getAllInventoryData()) { // You'll need to add this method to DatabaseHandler
            while (resultSet != null && resultSet.next()) {
                String codigoBarra = resultSet.getString("codigoBarra");
                int existencias = resultSet.getInt("existencias");
                double precioActual = resultSet.getDouble("precioActual");
                LocalDateTime ultimaActualizacion = resultSet.getTimestamp("ultimaActualizacion").toLocalDateTime();
                Inventory inventory = new Inventory(codigoBarra, existencias, precioActual, ultimaActualizacion);
                inventoryContainer.add(inventory);
            }
        } catch (SQLException e) {
            Utility.showError("Error de base de datos al obtener todos los inventarios: " + e.getMessage());
        }
        return inventoryContainer;
    }

    /*

    No funciona getProductInventory no se por que

     */
    public Inventory getProductInventory(String codigoBarra) {
        Connection connection = databaseManager.getConnection();
        DatabaseHandler databaseHandler = new DatabaseHandler(connection);
        Inventory inventory = null;
        ResultSet resultSet = null;
        try {
            resultSet = databaseHandler.getInventoryData(codigoBarra);
            if (resultSet != null && resultSet.next()) {
                int existencias = resultSet.getInt("existencias");
                double precioActual = resultSet.getDouble("precioActual");
                LocalDateTime ultimaActualizacion = resultSet.getTimestamp("ultimaActualizacion").toLocalDateTime();
                inventory = new Inventory(codigoBarra, existencias, precioActual, ultimaActualizacion);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.err.println("Error closing ResultSet: " + e.getMessage());
                }
            }
        }
        return inventory;
    }

    public boolean saveOrUpdateInventory(Inventory inventory) {
        Connection connection = databaseManager.getConnection();
        DatabaseHandler databaseHandler = new DatabaseHandler(connection);
        try {
            Inventory existingInventory = getProductInventory(inventory.getCodigoBarraProducto());
            int rowsAffected;
            if (existingInventory != null) {
                // Update existing record
                rowsAffected = databaseHandler.updateInventoryData(inventory);
            } else {
                // Save new record
                rowsAffected = databaseHandler.saveInventoryData(inventory);
            }

            if (rowsAffected > 0) {
                Utility.showSuccess("Inventario del producto guardado/actualizado correctamente.");
                return true;
            } else {
                Utility.showError("No se pudo guardar/actualizar el inventario del producto.");
                return false;
            }
        } catch (SQLException e) {
            Utility.showError("Error de base de datos al guardar/actualizar inventario: " + e.getMessage());
            return false;
        }
    }

    public boolean addPriceHistory(PriceHistory priceHistory) {
        Connection connection = databaseManager.getConnection();
        DatabaseHandler databaseHandler = new DatabaseHandler(connection);
        try {
            int rowsAffected = databaseHandler.savePriceHistoryData(priceHistory);
            if (rowsAffected > 0) {
                Utility.showSuccess("Historial de precios actualizado correctamente.");
                return true;
            } else {
                Utility.showError("No se pudo registrar el historial de precios.");
                return false;
            }
        } catch (SQLException e) {
            Utility.showError("Error de base de datos al guardar historial de precios: " + e.getMessage());
            return false;
        }
    }

    public Contenedor<PriceHistory> getProductPriceHistory(String codigoBarra) {
        Contenedor<PriceHistory> priceHistoryContainer = new Contenedor<>();
        Connection connection = databaseManager.getConnection();
        DatabaseHandler databaseHandler = new DatabaseHandler(connection);
        try (ResultSet resultSet = databaseHandler.getPriceHistoryData(codigoBarra)) {
            while (resultSet != null && resultSet.next()) {
                double precio = resultSet.getDouble("precio");
                LocalDateTime fechaCambio = resultSet.getTimestamp("fechaCambio").toLocalDateTime();
                PriceHistory priceHistory = new PriceHistory(codigoBarra, precio, fechaCambio);
                priceHistoryContainer.add(priceHistory);
            }
        } catch (SQLException e) {
            Utility.showError("Error de base de datos al obtener historial de precios: " + e.getMessage());
        }
        return priceHistoryContainer;
    }
}
