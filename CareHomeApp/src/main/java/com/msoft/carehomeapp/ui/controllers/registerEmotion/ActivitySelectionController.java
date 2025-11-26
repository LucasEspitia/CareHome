package com.msoft.carehomeapp.ui.controllers.registerEmotion;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.msoft.carehomeapp.ui.SceneSwitcher;
import com.msoft.carehomeapp.AppContext;
import com.msoft.carehomeapp.business.managers.EmotionManager;
import com.msoft.carehomeapp.business.managers.NotificationsManager;
import com.msoft.carehomeapp.business.managers.RecordsManager;
import com.msoft.carehomeapp.business.services.WellnessNotificationScheduler;
import com.msoft.carehomeapp.model.*;
import com.msoft.carehomeapp.ui.utils.AlertUtils;
import com.msoft.carehomeapp.ui.utils.HandleInactivity;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class ActivitySelectionController {

    @FXML private ListView<ActivitySuggestion> listActivities;
    @FXML private Button btnConfirm;
    
    private final EmotionManager emotionManager = AppContext.getEmotionManager();    
    private final NotificationsManager notificationManager = AppContext.getNotificationManager();
    private final RecordsManager recordsManager = AppContext.getRecordsManager();

    
    private final PauseTransition inactivityTimer = new PauseTransition(Duration.seconds(60));
    



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
            
            if(chosen == null){
                AlertUtils.warning("No activity selected", "Please choose an activity before continue.");
                return;
            }
                    
            //Timer stop
            inactivityTimer.stop();
            EmotionalReport draft = response.getDraftReport();
            draft.setActivity(chosen);

            recordsManager.saveReport(draft);     
            
            
            AlertUtils.confirm("Save Succesfully", 
                    "The emotional state has been succesfully saved :)"
            );
            //Try the notification scheduler
           
            if (notificationManager.tryTrigerSendNotifications(draft)) {
                NotificationScheduleConfig config = 
                    SceneSwitcher.openModal("notifications/NotificationSettingsView.fxml", "Notifications");

                if (config != null)
                    notificationManager.scheduleNotifications(config);
            }

            
            RegisterSession.reset();
            SceneSwitcher.switchScene(e, "HomeView.fxml");
        });
    }
    
    public void handleTimeout() {
        HandleInactivity.saveMinimalAndExitWithRoom(
            emotionManager,
            "No Activity was selected in 60 seconds.\nA minimal report was saved.",
            btnConfirm
        );
    }
}
