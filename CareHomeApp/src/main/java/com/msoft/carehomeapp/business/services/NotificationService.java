package com.msoft.carehomeapp.business.services;

import com.msoft.carehomeapp.model.EmotionalReport;

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
