package com.example;

import com.example.dao.TienditaDatabaseManager;
import com.example.model.AppModel;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.dao.DaoConfiguration.*;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        TienditaDatabaseManager.newInstance(DATA_BASE_NAME, USER, PASSWORD, PROTOCOL, DRIVER).hacerConexion();
        AppModel.getInstance().getViewFactory().showLoginWindow();
    }
    @Override
    public void stop() throws Exception {
        super.stop();
        TienditaDatabaseManager.newInstance(DATA_BASE_NAME, USER, PASSWORD, PROTOCOL, DRIVER).close();
        System.out.println("Conexión a la base de datos cerrada.");
    }
    public static void main(String[] args) {
        launch();
    }
}