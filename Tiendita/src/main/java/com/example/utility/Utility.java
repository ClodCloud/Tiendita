package com.example.utility;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Utility {
    public static void showError(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("Ocurrió un error");
        alert.setContentText(mensaje);
        alert.initStyle(StageStyle.UTILITY);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                Utility.class.getResource("/css/Error.css").toExternalForm()
        );
        dialogPane.getStyleClass().add("dialog-pane");

        alert.showAndWait();
    }

    public static void showSuccess(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Correcto!");
        alert.setHeaderText("Listo");
        alert.setContentText(mensaje);
        alert.initStyle(StageStyle.UTILITY);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                Utility.class.getResource("/css/Error.css").toExternalForm()
        );
        dialogPane.getStyleClass().add("dialog-pane");

        alert.showAndWait();

    }

    public static boolean showConfirmation(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmacion!");
        alert.setHeaderText(mensaje);
        alert.initStyle(StageStyle.UTILITY);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Utility.class.getResource("/css/Error.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");

        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

}
