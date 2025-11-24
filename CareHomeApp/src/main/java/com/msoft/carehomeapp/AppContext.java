package com.msoft.carehomeapp;

import com.msoft.carehomeapp.business.managers.DeviceManager;
import com.msoft.carehomeapp.business.managers.EmotionManager;
import com.msoft.carehomeapp.business.managers.NotificationsManager;
import com.msoft.carehomeapp.business.managers.PreferencesManager;
import com.msoft.carehomeapp.business.managers.RecordsManager;

/**
 *
 * @author lucas
 */
public class AppContext {

    private static EmotionManager emotionManager;
    private static PreferencesManager preferencesManager;
    private static RecordsManager recordsManager;
    private static NotificationsManager notificationsManager;
    private static DeviceManager deviceManager;

    public static void init(
        EmotionManager em,
        PreferencesManager pm,
        RecordsManager rm, 
        NotificationsManager ns,
        DeviceManager dm
    ){
        
        if(emotionManager != null) 
            throw new IllegalStateException("AppContext already initialized");
        
        emotionManager = em;
        preferencesManager = pm;
        recordsManager = rm;   
        notificationsManager = ns;
        deviceManager = dm;
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
    public static DeviceManager getDeviceManager(){
        return deviceManager;
    }
    
    public static NotificationsManager getNotificationManager() {
       return notificationsManager;
    }
  
}
