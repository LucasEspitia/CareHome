package com.msoft.carehomeapp.ui.controllers.registerEmotion;

import com.msoft.carehomeapp.AppContext;
import com.msoft.carehomeapp.business.managers.EmotionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import com.msoft.carehomeapp.model.Room;
import com.msoft.carehomeapp.model.factory.RoomFactory;
import com.msoft.carehomeapp.ui.SceneSwitcher;
import com.msoft.carehomeapp.ui.utils.AlertUtils;

/**
 *
 * @author lucas
 */
public class SelectRoomController {

    @FXML private ComboBox<Room> comboRoom;
    @FXML private Button btnNext;
    
    private final EmotionManager emotionManager = AppContext.getEmotionManager();
    private final PauseTransition inactivityTimer = new PauseTransition(Duration.seconds(60));
    
    @FXML
    public void initialize() {
        
        comboRoom.getItems().addAll(RoomFactory.getListRooms());
        
                // ---- START 1-MINUTE TIMER ----
        inactivityTimer.setOnFinished(e -> handleTimeout());
        inactivityTimer.play();

        btnNext.setOnAction(e -> {
            Room selected = comboRoom.getValue();
            if (selected == null) {
                AlertUtils.warning("Missing Selection", "Please select a room.");
                return;
            }
            
            inactivityTimer.stop();

            RegisterSession.room = selected;        
            
            SceneSwitcher.switchScene(e, "/registerEmotion/MusicSelectionView.fxml");
        });
    }
    private void handleTimeout() {        
        //Save Minimum log
        emotionManager.logMinimalReport(
                RegisterSession.emotionName, 
                RegisterSession.intensity
        );
        
        AlertUtils.infoNonBlocking("Session expired", 
                "No room was selected in 60 seconds.\nA minimal report was saved."
        );
        
        SceneSwitcher.switchScene(comboRoom, "HomeView.fxml");
    }
}