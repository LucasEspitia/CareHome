package com.msoft.carehomeapp.ui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

/**
 *
 * @author lucas
 */
public class SceneSwitcher {
    
    private static Stage mainStage;
     
    public static void switchScene(ActionEvent event, String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource("/com/msoft/carehomeapp/ui/" + fxml));
            Parent root = loader.load();
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
           
        } catch (IOException e) {
            System.err.println("Error switching scene: " + e);
            e.printStackTrace();
        }
    }
    
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
}