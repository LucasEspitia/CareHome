package com.msoft.carehomeapp.business.services;

import com.msoft.carehomeapp.model.EmotionalReport;
import com.msoft.carehomeapp.ui.SceneSwitcher;

/**
 *
 * @author lucas
 */
public class NotificationService {
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
      SceneSwitcher.switchScene("notifications/NotificationSettings.fxml",
              null
      );
  }
}
