package com.msoft.carehomeapp.business.services;

import com.msoft.carehomeapp.business.managers.WellnessNotificationScheduler;
import com.msoft.carehomeapp.model.EmotionalReport;
import com.msoft.carehomeapp.controllers.SceneSwitcher;
import com.msoft.carehomeapp.model.NotificationScheduleConfig;
import com.msoft.carehomeapp.ui.utils.AlertUtils;

/**
 *
 * @author lucas
 */
public class NotificationService {
    private final WellnessNotificationScheduler scheduler = 
            new WellnessNotificationScheduler();
     /**
     * Entry point for UC-02.Checks if the conditions are met. Called from UC-01 Register Emotion.
     * @param report
     */
    public void tryStartUC02(EmotionalReport report) {

        boolean isPositive =
                report.getEmotionalState().getEmotion().isPositive(); 

        boolean hasActivity =
                report.getActivity() != null &&
                report.getActivity().getText() != null &&
                !report.getActivity().getText().isBlank(); 

        // If UC-02 does not apply → exit
        if (!isPositive || !hasActivity) return;

        // Open the Notification Settings
        NotificationScheduleConfig config =
               SceneSwitcher.openModal("notifications/NotificationSettingsView.fxml",
                        "Notification Settings");
        
        if(config == null) return;
        
        scheduler.scheduleNotifications(config);
        
        AlertUtils.confirm("Schedule Succesfully",
            "Notifications scheduled: first reminder in " +
                    config.getFrequencyMinutes() +
                    " minutes."
        );     
  }
}
