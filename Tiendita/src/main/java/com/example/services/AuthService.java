package com.example.services;

import com.example.dao.DatabaseHandler;
import com.example.dao.TienditaDatabaseManager;
import com.example.model.User;
import com.example.utility.Utility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.dao.DaoConfiguration.*;

public class AuthService {

    private final TienditaDatabaseManager databaseManager;

    public AuthService(TienditaDatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public User authenticateUser(String username, String password) {
        Connection connection = databaseManager.getConnection();
        DatabaseHandler databaseHandler = new DatabaseHandler(connection);
        User authenticatedUser = null;

        try(ResultSet resultSet = databaseHandler.getUserData(username, password)){
            if (resultSet != null && resultSet.next()){
                String retrievedUsername = resultSet.getString("username");
                String retrievedPassword = resultSet.getString("password");
                authenticatedUser = new User(retrievedUsername, retrievedPassword);
            }
        }catch (SQLException e) {
            Utility.showError("Error de base de datos durante la autenticación: " + e.getMessage());
        } finally {
//            databaseManager.close();
        }
        return authenticatedUser;
    }


}
