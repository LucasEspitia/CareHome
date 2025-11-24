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

        String message = "Reminder!\nTake a 5-minute break";

        Platform.runLater(() -> {
            switch (config.getType()) {
                case POPUP -> AlertUtils.info("Wellness Reminder", message);
                case SOUND -> System.out.println("[SOUND (not implemented)]");
                case VIBRATION -> System.out.println("[VIBRATION (not implemented)]");
                case VISUAL_ONLY -> System.out.println("[VISUAL]: (not implemented)");
            }
        });
    }
    }
