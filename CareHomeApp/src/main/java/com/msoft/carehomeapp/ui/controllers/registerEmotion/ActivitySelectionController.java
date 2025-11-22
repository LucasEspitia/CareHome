package com.msoft.carehomeapp.ui.controllers.registerEmotion;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.msoft.carehomeapp.ui.controllers.SceneSwitcher;
import com.msoft.carehomeapp.AppContext;
import com.msoft.carehomeapp.business.managers.EmotionManager;
import com.msoft.carehomeapp.business.managers.RecordsManager;
import com.msoft.carehomeapp.business.managers.WellnessNotificationScheduler;
import com.msoft.carehomeapp.business.services.NotificationService;
import com.msoft.carehomeapp.model.*;
import com.msoft.carehomeapp.ui.utils.AlertUtils;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class ActivitySelectionController {

    @FXML private ListView<ActivitySuggestion> listActivities;
    @FXML private Button btnConfirm;
    
    private final EmotionManager emotionManager = AppContext.getEmotionManager();    
    private final NotificationService notificationService = new NotificationService();

    private final PauseTransition inactivityTimer = new PauseTransition(Duration.seconds(60));
    


    private final RecordsManager recordsManager = AppContext.getRecordsManager();

    @FXML
    public void initialize() {

        var response = RegisterSession.response;

        listActivities.getItems().addAll(response.getActivities());
        
          // ---- START 1-MINUTE TIMER ----
        inactivityTimer.setOnFinished(e -> handleTimeout());
        inactivityTimer.play();

        btnConfirm.setOnAction(e -> {

            ActivitySuggestion chosen =
                    listActivities.getSelectionModel().getSelectedItem();
            
                    
            //Timer stop
            inactivityTimer.stop();
            EmotionalReport draft = response.getDraftReport();
            draft.setActivity(chosen);

            recordsManager.saveReport(draft);     
            
            
            AlertUtils.confirm("Save Succesfully", 
                    "The emotional state has been succesfully saved :)"
            );
            //Try the notification scheduler
           
            if (notificationService.shouldTriggerUC02(draft)) {
                NotificationScheduleConfig config = 
                    SceneSwitcher.openModal("notifications/NotificationSettingsView.fxml", "Notifications");

                if (config != null)
                    new WellnessNotificationScheduler().scheduleNotifications(config);
            }     
            
            RegisterSession.reset();
            SceneSwitcher.switchScene(e, "HomeView.fxml");
        });
    }
    
    public void handleTimeout(){
        emotionManager.logMinimalReport(
                RegisterSession.emotionName, 
                RegisterSession.intensity,
                RegisterSession.room
        );
        
        AlertUtils.infoNonBlocking("Session expired", 
                "No Activity was selected in 60 seconds.\nA minimal report was saved."
        );
        
        SceneSwitcher.switchScene(btnConfirm, "HomeView.fxml");
    }
}
