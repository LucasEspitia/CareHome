package com.msoft.carehomeapp;

import com.msoft.carehomeapp.business.managers.EmotionManager;
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

    public static void init(
        EmotionManager em,
        PreferencesManager pm,
        RecordsManager rm){
        emotionManager = em;
        preferencesManager = pm;
        recordsManager = rm;   
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
}
