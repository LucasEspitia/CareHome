package com.msoft.carehomeapp.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.msoft.carehomeapp.AppContext;
import com.msoft.carehomeapp.business.managers.EmotionManager;
import com.msoft.carehomeapp.business.managers.PreferencesManager;
import com.msoft.carehomeapp.model.*;
import com.msoft.carehomeapp.ui.RegisterSession;
import com.msoft.carehomeapp.ui.SceneSwitcher;

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

        btnContinue.setOnAction(e -> {

            if (!checkNoMusic.isSelected())
                RegisterSession.selectedSong = listSongs.getSelectionModel().getSelectedItem();

            RegisterSession.response = emotionManager.processEmotion(
                    RegisterSession.emotionName,
                    RegisterSession.intensity,
                    RegisterSession.room,
                    RegisterSession.selectedSong
            );

            SceneSwitcher.switchScene(btnContinue, "ActivitySelectionView.fxml");
        });
    }
}
