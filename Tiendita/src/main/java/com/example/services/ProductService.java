package com.example.services;

import com.example.dao.DatabaseHandler;
import com.example.dao.TienditaDatabaseManager;
import com.example.model.Contenedor;
import com.example.model.Product;
import com.example.utility.Utility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static com.example.dao.DaoConfiguration.*;

public class ProductService {

    private final TienditaDatabaseManager databaseManager;

    public ProductService(TienditaDatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public Contenedor<Product> getProductList(){
        Contenedor<Product> productContainer = new Contenedor<>();
        Connection connection = databaseManager.getConnection();
        DatabaseHandler databaseHandler = new DatabaseHandler(connection);

        try(ResultSet resultSet = databaseHandler.getAllProductsData()){
            while (resultSet != null && resultSet.next()){
                String codigoBarra = resultSet.getString("codigoBarra");
                String nombre = resultSet.getString("nombre");
                String marca = resultSet.getString("marca");
                String tipo = resultSet.getString("tipo");
                String contenido = resultSet.getString("contenido");
                String unidadMedida = resultSet.getString("unidadMedida");
                int stockMinimo = resultSet.getInt("stockMinimo");
                int stockMaximo = resultSet.getInt("stockMaximo");
                String presentacion = resultSet.getString("presentacion");
                String descripcion = resultSet.getString("descripcion");
                boolean activo = resultSet.getBoolean("activo");
                String rutaImagen = resultSet.getString("rutaImagen");
                LocalDate fechaAlta = resultSet.getDate("fechaAlta").toLocalDate();
                boolean perecedero = resultSet.getBoolean("perecedero");
                String pasillo = resultSet.getString("ubicacionTienda");
                Product producto = new Product(codigoBarra,nombre,marca,tipo,contenido,
                        unidadMedida,stockMinimo,stockMaximo,presentacion,descripcion,activo,rutaImagen,fechaAlta,
                        perecedero,pasillo);
                productContainer.add(producto);
            }
        }catch (SQLException e) {
            Utility.showError("Error de base de datos durante la autenticación: " + e.getMessage());
        } finally {
//            databaseManager.close();
        }
        return productContainer;
    }

    public boolean saveProductDatabase(Product product) {
        Connection connection = databaseManager.getConnection();
        DatabaseHandler databaseHandler = new DatabaseHandler(connection);
        try {
            int rowsAffected = databaseHandler.saveProductData(product);
            if (rowsAffected > 0) {
                Utility.showSuccess("Producto creado correctamente." + product.getCodigoBarra());
                return true;
            } else {
                Utility.showError("No se pudo guardar el producto en la base de datos.");
                return false;
            }
        } catch (SQLException e) {
            Utility.showError("Error de base de datos al guardar producto: " + e.getMessage());
            return false;
        } finally {
//            databaseManager.close();
        }
    }

    public boolean deleteProduct(String barCode) {
        Connection connection = databaseManager.getConnection();
        DatabaseHandler databaseHandler = new DatabaseHandler(connection);
        try {
            int rowsAffected = databaseHandler.deleteProductData(barCode);
            if (rowsAffected > 0) {
                return true;
            } else {
                Utility.showError("No se pudo guardar el producto en la base de datos.");
                return false;
            }
        } catch (SQLException e) {
            Utility.showError("Error de base de datos al guardar producto: " + e.getMessage());
            return false;
        } finally {
//            databaseManager.close();
        }
    }

    public boolean updateProduct(String oldCodBar, Product newProduct) {
        Connection connection = databaseManager.getConnection();
        DatabaseHandler databaseHandler = new DatabaseHandler(connection);
        try {
            int rowsAffected = databaseHandler.updateProductData(oldCodBar, newProduct);
            if (rowsAffected > 0) {
                return true;
            } else {
                Utility.showError("No se pudo guardar el producto en la base de datos.");
                return false;
            }
        } catch (SQLException e) {
            Utility.showError("Error de base de datos al modificar el producto: " + e.getMessage());
            return false;
        } finally {
//            databaseManager.close();
        }
    }


}
