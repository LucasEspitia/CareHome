package com.msoft.carehomeapp.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.msoft.carehomeapp.AppContext;
import com.msoft.carehomeapp.business.managers.EmotionManager;
import com.msoft.carehomeapp.business.managers.PreferencesManager;
import com.msoft.carehomeapp.model.*;
import com.msoft.carehomeapp.ui.RegisterSession;
import com.msoft.carehomeapp.ui.SceneSwitcher;
import com.msoft.carehomeapp.ui.utils.AlertUtils;

import java.util.List;

public class MusicSelectionController {

    @FXML private ListView<Song> listSongs;
    @FXML private CheckBox checkNoMusic;
    @FXML private Button btnContinue;

    private final EmotionManager emotionManager = AppContext.getEmotionManager();
    private final PreferencesManager prefsManager = AppContext.getPreferencesManager();

    @FXML
    public void initialize() {

        Preferences prefs = prefsManager.getPreferences();

        Emotion.EmotionType type =
                EmotionManager.determineType(RegisterSession.emotionName);

        List<Song> songs = prefs.getPreferredMusic(type);
        listSongs.getItems().addAll(songs);

        btnContinue.setOnAction((var e) -> {
            
           // if(!checkNoMusic.isSelected())

            if (!checkNoMusic.isSelected())
                RegisterSession.selectedSong = listSongs.getSelectionModel().getSelectedItem();

            RegisterSession.response = emotionManager.processEmotion(
                    RegisterSession.emotionName,
                    RegisterSession.intensity,
                    RegisterSession.room,
                    RegisterSession.selectedSong
            );
            
            if(RegisterSession.response.getMusicMessage() != null){
                AlertUtils.error("Music playback unavailable", """
                                                               Please check your connections
                                                               Adjusting Lights only.""");
            }
            
            if(RegisterSession.response.getLightsMessage() != null){
                AlertUtils.error(
                        "Lighning control unavailable", 
                        "Please check your connections."
                );
            }
            //Testing
            System.out.println(RegisterSession.response.getMusicMessage());
            System.out.println(RegisterSession.response.getLightsMessage());
            
            SceneSwitcher.switchScene(e, "ActivitySelectionView.fxml");
        });
    }
}