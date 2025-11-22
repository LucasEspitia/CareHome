package com.msoft.carehomeapp.business.managers;

import com.msoft.carehomeapp.model.NotificationScheduleConfig;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;

public class WellnessNotificationScheduler {

    private ScheduledExecutorService executor;

    public void scheduleNotifications(NotificationScheduleConfig config) {

        if (config == null) return;
        if (config.getRepeat() <= 0) return;
        if (config.getFrequencyMinutes() <= 0) return;

        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
        }

        executor = Executors.newSingleThreadScheduledExecutor();
        final int[] sentCount = {0};

        executor.scheduleAtFixedRate(() -> {

            if (sentCount[0] >= config.getRepeat()) {
                executor.shutdownNow();
                return;
            }

            try {
                String message = "Reminder!\nTake a 5-minute break";

                Platform.runLater(() -> {
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
                });

                sentCount[0]++;

            } catch (Exception ex) {
                System.err.println("Notification failed, retrying next cycle...");
            }

        },
        config.getFrequencyMinutes(),   // initial delay
        config.getFrequencyMinutes(),   // interval
        TimeUnit.SECONDS);              
    }

    // Optional -> In future we could set if user wants to cancel this notification.
    public void cancel() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
        }
    }
}
