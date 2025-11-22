package com.msoft.carehomeapp.business.services;

import com.msoft.carehomeapp.business.managers.WellnessNotificationScheduler;
import com.msoft.carehomeapp.model.EmotionalReport;
import com.msoft.carehomeapp.ui.controllers.SceneSwitcher;
import com.msoft.carehomeapp.model.NotificationScheduleConfig;
import com.msoft.carehomeapp.ui.utils.AlertUtils;

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
}
