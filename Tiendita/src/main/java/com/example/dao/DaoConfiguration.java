package com.example.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DaoConfiguration {
    public static String DATA_BASE_NAME;
    public static String USER;
    public static String PASSWORD;
    public static String PROTOCOL;
    public static String DRIVER;
    static {

        Properties properties = new Properties();

        try (FileInputStream fis = new FileInputStream("config/config.properties")) {
            properties.load(fis);
            DATA_BASE_NAME = properties.getProperty("dataBaseName");
            USER = properties.getProperty("user");
            PASSWORD = properties.getProperty("password");
            PROTOCOL = properties.getProperty("protocol");
            DRIVER = properties.getProperty("driver");
            System.out.println(DATA_BASE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
