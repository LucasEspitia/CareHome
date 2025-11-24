package com.msoft.carehomeapp.business.managers;

import com.msoft.carehomeapp.business.services.WellnessNotificationScheduler;
import com.msoft.carehomeapp.business.services.NotificationService;
import com.msoft.carehomeapp.model.EmotionalReport;
import com.msoft.carehomeapp.model.NotificationScheduleConfig;
/**
 * 
 * Handles all forms of notifications (UI popups, sound, vibration, visual only)
 * Any thread can safely call these methods.
 * 
 * @author lucas
 */

public class NotificationsManager {

    private final NotificationService notiService;
    private final WellnessNotificationScheduler scheduler;

    public NotificationsManager(NotificationService ns) {
        this.notiService = ns;
        this.scheduler = new WellnessNotificationScheduler(ns);
    }

    public boolean tryTrigerSendNotifications(EmotionalReport report){
        return notiService.shouldTriggerUC02(report);
    }
    
    public void scheduleNotifications(NotificationScheduleConfig config) {
        scheduler.scheduleNotifications(config);
    }
}

