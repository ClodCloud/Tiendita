package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TienditaDatabaseManager {
    private static TienditaDatabaseManager tienditaDatabaseManager = null;
    private Connection connection;
    private String nombreBaseDatos;
    private String usuario;
    private String password;
    private String protocolo;
    private String driver;

    private TienditaDatabaseManager(String nombreBaseDatos, String usuario, String password, String protocolo, String driver) {
        super();
        this.nombreBaseDatos = nombreBaseDatos;
        this.usuario = usuario;
        this.password = password;
        this.protocolo = protocolo;
        this.driver = driver;
    }

    public String hacerConexion() {
        try {
            if (connection == null || connection.isClosed()) {
                System.out.println("Intentando hacer conexion"); //
                Class.forName(driver); //
                connection = DriverManager.getConnection(protocolo + nombreBaseDatos, usuario, password); //
                System.out.println("Conexion establecida"); //
            }
            return "exito";
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e); //
            return e.toString(); //
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static TienditaDatabaseManager newInstance(String nombre, String usuario, String password, String protocolo,
                                                      String driver) {
        return tienditaDatabaseManager = tienditaDatabaseManager == null
                ? new TienditaDatabaseManager(nombre, usuario, password, protocolo, driver)
                : tienditaDatabaseManager;
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}