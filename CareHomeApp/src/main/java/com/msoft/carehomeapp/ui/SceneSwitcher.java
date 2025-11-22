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
    
    public static <T> void switchScene(String fxml,  
                                 java.util.function.Consumer<T> controllerInit) {
    try {
        FXMLLoader loader = new FXMLLoader(
                SceneSwitcher.class.getResource("/com/msoft/carehomeapp/ui/" + fxml)
        );

        Parent root = loader.load();
        T controller = loader.getController();

        if (controllerInit != null)
            controllerInit.accept(controller);

        Stage popup = new Stage();
        popup.setScene(new Scene(root)); 
        popup.setResizable(false);
        popup.initOwner(mainStage); 
        popup.show();

    } catch (Exception e) {
        System.err.println("Error opening stage: " + fxml);
        e.printStackTrace();
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