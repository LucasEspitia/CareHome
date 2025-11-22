package com.msoft.carehomeapp.business.managers;

import com.msoft.carehomeapp.model.NotificationScheduleConfig;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author lucas
 */
public class WellnessNotificationScheduler {
    
    private ScheduledExecutorService executor;
    
     /**
     * Schedules wellness notifications based on user preferences.
     * @param config
     */
    public void scheduleNotifications(NotificationScheduleConfig config) {
        // If a previous scheduler exists → shut it down
        if(executor != null && !executor.isShutdown()){
            executor.shutdownNow();
        }
        
        executor = Executors.newSingleThreadScheduledExecutor();

        final int[] sentCount = {0};
        
       executor.scheduleAtFixedRate(() -> {

            // If already sent all notifications → stop
            if (sentCount[0] >= config.getRepeat()) {
                executor.shutdownNow();
                return;
            }

            try {
                // Prepare notification message --> ToDo: Add more messages.
                String message = "Reminder! \n Take a 5-minute break";

                switch (config.getType()) {
                    case POPUP ->
                        ManagerNotification.showInfo("Wellness Reminder", message);

                    case SOUND ->
                        ManagerNotification.playSound("notification.wav (not implemented)"); 

                    case VIBRATION ->
                        ManagerNotification.playVibration("Vibration (not implemented)");

                    case VISUAL_ONLY ->
                        ManagerNotification.showOnlyVisual("Visual-only alert → " + message);
                }

                sentCount[0]++;

            } catch (Exception ex) {
                System.err.println("Notification failed, retrying next cycle...");
            }

        }, 
        config.getFrequencyMinutes(), 
        config.getFrequencyMinutes(),
        TimeUnit.SECONDS);  
    }
    
}
