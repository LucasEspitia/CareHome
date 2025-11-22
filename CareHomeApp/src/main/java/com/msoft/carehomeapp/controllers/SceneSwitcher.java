package com.msoft.carehomeapp.controllers;

import com.msoft.carehomeapp.controllers.notifications.NotificationSettingsController;
import com.msoft.carehomeapp.model.NotificationScheduleConfig;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.stage.Modality;

/**
 *
 * @author lucas
 */
public class SceneSwitcher {
    
    private static Stage mainStage;
    
        public static void setMainStage(Stage stage) {
        mainStage = stage;
    }
    
    public static void swichTo(String fxml){
        try {
            FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource("/com/msoft/carehomeapp/ui/" + fxml));
            Parent root = loader.load();

            if (mainStage == null) {
                throw new IllegalStateException("Main stage not set. Call SceneSwitcher.setMainStage() first.");
            }

            mainStage.setScene(new Scene(root));
            mainStage.show();

        } catch (IOException e) {
            System.err.println("Error switching to scene: " + e);
            e.printStackTrace();
        }
    }
     
    public static void switchScene(ActionEvent event, String fxml) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        loadScene(stage, fxml);
    }

    public static void switchScene(Node node, String fxml) {
        Stage stage = (Stage) node.getScene().getWindow();
        loadScene(stage, fxml);
    }
    
    public static NotificationScheduleConfig openModal(String fxml, String title) {
    try {
        FXMLLoader loader = new FXMLLoader(
                SceneSwitcher.class.getResource("/com/msoft/carehomeapp/ui/" + fxml)
        );

        Parent root = loader.load();

        // controller genérico
        Object controller = loader.getController();

        Stage stage = new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainStage);
        stage.setScene(new Scene(root));
        
        stage.showAndWait(); 

        
        if (controller instanceof NotificationSettingsController ctrl) {
            return ctrl.getFinalConfig();
        }

        return null;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
    
 
    private static void loadScene(Stage stage, String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    SceneSwitcher.class.getResource("/com/msoft/carehomeapp/ui/" + fxml)
            );
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.err.println("Error switching scene to: " + fxml);
            e.printStackTrace();
        }
    }
    
}