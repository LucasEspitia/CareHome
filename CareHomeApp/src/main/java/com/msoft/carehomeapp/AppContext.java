package com.msoft.carehomeapp;

import com.msoft.carehomeapp.business.managers.EmotionManager;
import com.msoft.carehomeapp.business.managers.PreferencesManager;
import com.msoft.carehomeapp.business.managers.RecordsManager;
import com.msoft.carehomeapp.business.services.NotificationService;

/**
 *
 * @author lucas
 */
public class AppContext {

    private static EmotionManager emotionManager;
    private static PreferencesManager preferencesManager;
    private static RecordsManager recordsManager;
    private static NotificationService notificationService;

    public static void init(
        EmotionManager em,
        PreferencesManager pm,
        RecordsManager rm, 
        NotificationService ns){
        
        if(emotionManager != null) 
            throw new IllegalStateException("AppContext already initialized");
        
        emotionManager = em;
        preferencesManager = pm;
        recordsManager = rm;   
        notificationService = ns;
    }
    
    public static EmotionManager getEmotionManager() {
        return emotionManager;
    }

    public static PreferencesManager getPreferencesManager() {
        return preferencesManager;
    }

    public static RecordsManager getRecordsManager() {
        return recordsManager;
    }
    
    public static NotificationService getNotificationService() {
       return notificationService;
    }
}
