package com.msoft.carehomeapp.ui.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
 /*
 * @author lucas
 */
public class AlertUtils {
    public static void info(String title, String message) {
        show(Alert.AlertType.INFORMATION, title, message);
    }
    public static void infoNonBlocking(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show(); 
    }
    public static void error(String title, String message) {
        show(Alert.AlertType.ERROR, title, message);
    }

    public static void warning(String title, String message) {
        show(Alert.AlertType.WARNING, title, message);
    }

    public static boolean confirm(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(title);
        alert.setContentText(message);

        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    private static void show(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
