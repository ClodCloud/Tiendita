package com.example.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

    private final StringProperty username;
    private final StringProperty password;

    public User(String username, String password) {
        this.username = new SimpleStringProperty(this, "Nombre", username);
        this.password = new SimpleStringProperty(this, "Password", password);
    }

    public StringProperty usernameProperty() { return username; }
    public StringProperty passwordProperty() { return password; }
}
