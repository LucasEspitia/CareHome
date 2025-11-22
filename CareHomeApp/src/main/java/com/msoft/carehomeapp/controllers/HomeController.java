package com.msoft.carehomeapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
/**
 *
 * @author lucas
 */
public class HomeController {
    @FXML private Button btnRegisterMood;
    @FXML private Button btnViewRecords;
    @FXML private Button btnPreferences;
    //ToDo -> add devices -> but UC not implemented 
    
    @FXML
    public void initialize(){
        
        btnRegisterMood.setOnAction(e ->
                SceneSwitcher.switchScene(e, "/registerEmotion/RegisterEmotionView.fxml")
        );
        btnViewRecords.setOnAction(e ->
                SceneSwitcher.switchScene(e, "/viewRecords/RecordsListView.fxml"));

        btnPreferences.setOnAction(e ->
                SceneSwitcher.switchScene(e, "/setPreferences/PreferencesView.fxml"));

        
    }
    
}
