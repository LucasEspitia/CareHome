package com.msoft.carehomeapp.ui.controllers.registerEmotion;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.msoft.carehomeapp.AppContext;
import com.msoft.carehomeapp.business.managers.EmotionManager;
import com.msoft.carehomeapp.business.managers.PreferencesManager;
import com.msoft.carehomeapp.model.*;
import com.msoft.carehomeapp.ui.SceneSwitcher;
import com.msoft.carehomeapp.ui.utils.AlertUtils;

import java.util.List;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class MusicSelectionController {

    @FXML private ListView<Song> listSongs;
    @FXML private CheckBox checkNoMusic;
    @FXML private Button btnContinue;

    private final EmotionManager emotionManager = AppContext.getEmotionManager();
    private final PreferencesManager prefsManager = AppContext.getPreferencesManager();
    private final PauseTransition inactivityTimer = new PauseTransition(Duration.seconds(60));


    @FXML
    public void initialize() {

        Preferences prefs = prefsManager.getPreferences();

        Emotion.EmotionType type =
                emotionManager.determineType(RegisterSession.emotionName);

        List<Song> songs = prefs.getPreferredMusic(type);
        listSongs.getItems().addAll(songs);
        
                        // ---- START 1-MINUTE TIMER ----
        inactivityTimer.setOnFinished(e -> handleTimeout());
        inactivityTimer.play();

        btnContinue.setOnAction((var e) -> {
            if (!checkNoMusic.isSelected())
                RegisterSession.selectedSong = listSongs.getSelectionModel().getSelectedItem();
            
            //Timer stop
            inactivityTimer.stop();
            
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
            //System.out.println(RegisterSession.response.getMusicMessage());
            //System.out.println(RegisterSession.response.getLightsMessage());
            
            SceneSwitcher.switchScene(e, "/registerEmotion/ActivitySelectionView.fxml");
        });
    }
    public void handleTimeout(){
        emotionManager.logMinimalReport(
                RegisterSession.emotionName, 
                RegisterSession.intensity,
                RegisterSession.room
        );
        
        AlertUtils.infoNonBlocking("Session expired", 
                "No Song was selected in 60 seconds.\nA minimal report was saved."
        );
        
        SceneSwitcher.switchScene(checkNoMusic, "HomeView.fxml");
    }
}
