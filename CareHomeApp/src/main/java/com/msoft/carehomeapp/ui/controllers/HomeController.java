package com.msoft.carehomeapp.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.msoft.carehomeapp.ui.SceneSwitcher;
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
                SceneSwitcher.switchScene(btnRegisterMood, "RegisterEmotionView.fxml")
        );
        btnViewRecords.setOnAction(e ->
                SceneSwitcher.switchScene(btnViewRecords, "RecordsListView.fxml"));

        btnPreferences.setOnAction(e ->
                SceneSwitcher.switchScene(btnPreferences, "PreferencesView.fxml"));

        
    }
    
}
