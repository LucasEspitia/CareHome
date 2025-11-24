package com.msoft.carehomeapp.business.services;

import com.msoft.carehomeapp.model.NotificationScheduleConfig;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WellnessNotificationScheduler {

    private ScheduledExecutorService executor;
    private final NotificationService notificationService;
    
    public WellnessNotificationScheduler(NotificationService ns) {
        this.notificationService = ns;
    }
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
                notificationService.send(config);
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
