module com.example.controllers {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires jdk.jshell;

    opens com.example.controllers to javafx.fxml;
    exports com.example.controllers;
    exports com.example;
    opens com.example to javafx.fxml;
    opens com.example.model to javafx.base;
}