package com.example.model;

import com.example.dao.TienditaDatabaseManager;
import com.example.services.AuthService;
import com.example.services.InventoryService;
import com.example.services.ProductService;
import com.example.views.ViewFactory;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.dao.DaoConfiguration.*;

public class AppModel {

    private static AppModel appModel = null;
    private final ViewFactory viewFactory;
    private final AuthService authService;
    private final ProductService productService;
    private final InventoryService inventoryService;
    //Contenedor donde están todos los productos, solo existe este, asi que se va a acceder desde este cada que se ocupe
    private Contenedor<Product> productContainer;
    private Contenedor<Inventory> inventoryContainer;

    private final TienditaDatabaseManager databaseManager;

    /* Sección de datos de usuario

     */



    private boolean userLoginSuccessFlag;
    private User currentUser;

    private AppModel() {
        this.databaseManager = TienditaDatabaseManager.newInstance(DATA_BASE_NAME, USER, PASSWORD, PROTOCOL, DRIVER);
        this.viewFactory = new ViewFactory();
        this.authService = new AuthService(this.databaseManager);
        this.productService = new ProductService(this.databaseManager);
        this.inventoryService = new InventoryService(this.databaseManager);
        this.productContainer = new Contenedor<>();
        this.inventoryContainer = new Contenedor<>();
        this.userLoginSuccessFlag = false;
        this.currentUser = new User("", "");
        //Aqui se cargan los productos nadamás iniciar la aplicación
        initializeDatabaseAndData();
    }

    private void initializeDatabaseAndData() {
        // Ensure connection is established before loading data
        if (!databaseManager.hacerConexion().equals("exito")) {
            System.err.println("No se pudo establecer la conexion");
        }
        loadProducts();
        loadInventories();
    }
    public static synchronized AppModel getInstance() {
        if (appModel == null) {
            appModel = new AppModel();
        }
        return appModel;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }


    /*
    Sección de metodos de usuario
     */

    public boolean isUserLoginSuccessFlag() {
        return userLoginSuccessFlag;
    }

    public void setUserLoginSuccessFlag(boolean flag) {
        this.userLoginSuccessFlag = flag;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void evaluateClientCredentials(String username, String password) {
        User authenticatedUser = authService.authenticateUser(username, password);
        if(authenticatedUser != null){
            this.currentUser.usernameProperty().set(authenticatedUser.usernameProperty().get());
            this.currentUser.passwordProperty().set(authenticatedUser.passwordProperty().get());
            this.userLoginSuccessFlag = true;
        } else {
            this.userLoginSuccessFlag = false;
        }
    }

    /*
    Sección de metodos de productos
     */

    public Contenedor<Product> getProductContainer() {
        return productContainer;
    }

    private void loadProducts() {
        this.productContainer = productService.getProductList();
    }

    public void saveProductToDatabase(Product product) {
        productContainer.add(product);
        productService.saveProductDatabase(product);
    }

    public boolean deleteProductFromDatabase(Product product) {
        if(!productService.deleteProduct(product.getCodigoBarra())){
            return false;
        }
        if(!productContainer.remove(product)){
            return false;
        }
        return true;
    }

    public boolean updateProductFromDatabase(Product oldProduct, Product newProduct) {
        if(!productService.updateProduct(oldProduct.getCodigoBarra(), newProduct)){
            return false;
        }
        if(!productContainer.update(oldProduct, newProduct)){
            return false;
        }
        return true;
    }

    /*
    Sección de metodos de inventario y precios
     */

    public Contenedor<Inventory> getInventoryContainer() {
        return inventoryContainer;
    }

    public void addInventoryRecord(Inventory inventory) {
        if (inventoryService.saveOrUpdateInventory(inventory)) {
            Inventory existingInventory = inventoryContainer.getAll().stream()
                    .filter(inv -> inv.getKey().equals(inventory.getKey()))
                    .findFirst()
                    .orElse(null);
            if (existingInventory != null) {
                inventoryContainer.update(existingInventory, inventory);
                System.out.println("El inventario ya existe");
            } else {
                System.out.println("guardando inventario");
                inventoryContainer.add(inventory);
            }
        }
    }

    public Inventory getInventoryForProduct(String codigoBarra) {
        Inventory inventory = inventoryContainer.getAll().stream()
                .filter(inv -> inv.getKey().equals(codigoBarra))
                .findFirst()
                .orElse(null);
        if (inventory == null) {
            inventory = inventoryService.getProductInventory(codigoBarra);
            if (inventory != null) {
                inventoryContainer.add(inventory);
            }
        }
        return inventory;
    }

    public void addPriceHistoryRecord(PriceHistory priceHistory) {
        inventoryService.addPriceHistory(priceHistory);
    }

    public Contenedor<PriceHistory> getPriceHistoryForProduct(String codigoBarra) {
        return inventoryService.getProductPriceHistory(codigoBarra);
    }

    private void loadInventories() {
        this.inventoryContainer = inventoryService.getAllInventories();
    }

    public void refreshInventories() {
        this.inventoryContainer = inventoryService.getAllInventories();
    }


}