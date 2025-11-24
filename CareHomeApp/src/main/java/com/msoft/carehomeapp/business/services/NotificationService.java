package com.msoft.carehomeapp.business.services;

import com.msoft.carehomeapp.model.EmotionalReport;
import com.msoft.carehomeapp.model.NotificationScheduleConfig;
import static com.msoft.carehomeapp.model.NotificationScheduleConfig.NotificationType.POPUP;
import static com.msoft.carehomeapp.model.NotificationScheduleConfig.NotificationType.SOUND;
import static com.msoft.carehomeapp.model.NotificationScheduleConfig.NotificationType.VIBRATION;
import static com.msoft.carehomeapp.model.NotificationScheduleConfig.NotificationType.VISUAL_ONLY;
import com.msoft.carehomeapp.ui.utils.AlertUtils;
import javafx.application.Platform;

/**
 *
 * @author lucas
 */
public class NotificationService {

    public boolean shouldTriggerUC02(EmotionalReport report) {

        boolean isPositive = report.getEmotionalState().getEmotion().isPositive();
        boolean hasActivity = report.getActivity() != null &&
                              report.getActivity().getText() != null &&
                              !report.getActivity().getText().isBlank();

        return isPositive && hasActivity;
    }
    
    public void send(NotificationScheduleConfig config) {


        Platform.runLater(() -> {
            switch (config.getType()) {
                case POPUP -> showPopUp();
                case SOUND -> sendSound();
                case VIBRATION -> sendVibration();
                case VISUAL_ONLY ->sendOnlyVisual();
            }
        });
    }
    
    private void showPopUp(){
        String message = "Reminder!\nTake a 5-minute break";
        AlertUtils.info("Wellness Reminder", message);
    }
    
    private void sendSound(){
        System.out.println("[SOUND (not implemented)]");
    }
    private void sendVibration(){
        System.out.println("[VIBRATION (not implemented)]");
    }
    private void sendOnlyVisual(){
         System.out.println("[VISUAL]: (not implemented)");
    }
}
