/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.msoft.carehomeapp.ui.controllers.setPreferences;

import com.msoft.carehomeapp.AppContext;
import com.msoft.carehomeapp.business.managers.PreferencesManager;
import com.msoft.carehomeapp.model.Emotion;
import com.msoft.carehomeapp.model.Preferences;
import com.msoft.carehomeapp.model.Song;
import com.msoft.carehomeapp.ui.SceneSwitcher;
import com.msoft.carehomeapp.ui.utils.AlertUtils;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 *
 * @author lucas
 */
public class PreferencesController {
   @FXML private ComboBox<Emotion.EmotionType> comboEmotion;
   @FXML private TextField fieldSong1Title;
   @FXML private TextField fieldSong1Artist;
   @FXML private TextField fieldSong2Title;
   @FXML private TextField fieldSong2Artist;
   @FXML private TextField fieldSong3Title;
   @FXML private TextField fieldSong3Artist;
   
   @FXML private ComboBox<String> comboLights;
    
   @FXML private Button btnSave;
   @FXML private Button btnBack; 
    private PreferencesManager preferencesManager= AppContext.getPreferencesManager();
    private Preferences currentPreferences;
    
    @FXML
    public void initialize(){
        //AlertUtils.info("UNDER CONSTRUCTION", "STOP!! THIS IS A DANGER ZONE. NO COMING BACK");
        comboEmotion.getItems().addAll(Emotion.EmotionType.values());

        comboLights.getItems().setAll(
        "Warm",
        "Cool",
        "Soft",
        "Bright",
        "Colorful",
        "Dim",
        "Neutral"
        );
        comboLights.setValue("Warm");
        
        currentPreferences = preferencesManager.getPreferencesAPI();
        if(currentPreferences == null){
            currentPreferences = new Preferences();
        }
        
        btnSave.setOnAction(e -> onSaveClicked());
        btnBack.setOnAction(e ->  SceneSwitcher.switchScene(btnBack, "HomeView.fxml"));
    }
    
    private void onSaveClicked(){
        Emotion.EmotionType emotion = comboEmotion.getValue();
        if (emotion == null) {
            AlertUtils.error("Validation Error", "You must select an emotion.");
            return;
        }
        List<Song> songs = buildSongsList();
        if (songs.isEmpty()) {
            AlertUtils.error("Validation Error", "Please provide at least one valid song.");
            return;
        }
        String lightingPref = comboLights.getValue();
        if (lightingPref == null && !lightingPref.isBlank()) {
        AlertUtils.error("Validation Error", "Please choose a lights preference.");
            return;
        }
        currentPreferences.setPreferredMusic(emotion, songs);
        currentPreferences.setPreferredLighting(emotion, lightingPref);
        
         preferencesManager.updateOrSavePreferences(currentPreferences);

        AlertUtils.info("Success", "Preferences updated for emotion: " + emotion);
        
        SceneSwitcher.switchScene(btnSave, "HomeView.fxml");
    }
    private List<Song> buildSongsList() {
        List<Song> list = new ArrayList<>();

        addIfValid(list, fieldSong1Title.getText(), fieldSong1Artist.getText());
        addIfValid(list, fieldSong2Title.getText(), fieldSong2Artist.getText());
        addIfValid(list, fieldSong3Title.getText(), fieldSong3Artist.getText());

        return list;
    }

    private void addIfValid(List<Song> songs, String title, String artist) {
        Song s = new Song(title, artist);
        if (s.isValid()) {
            songs.add(s);
        }
    }

}
