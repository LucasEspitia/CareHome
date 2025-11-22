package com.msoft.carehomeapp.controllers.registerEmotion;

import com.msoft.carehomeapp.business.managers.EmotionManager;
import com.msoft.carehomeapp.model.*;

/**
 *
 * @author lucas
 */


public class RegisterSession { 
    public static Emotion.EmotionName emotionName; 
    public static int intensity; 
    public static Room room; 
    public static Song selectedSong; 
    public static EmotionManager.Response response; 
    
    public static void reset() { 
        emotionName = null;
        intensity = 0; 
        room = null; 
        selectedSong = null; 
        response = null; } 
}