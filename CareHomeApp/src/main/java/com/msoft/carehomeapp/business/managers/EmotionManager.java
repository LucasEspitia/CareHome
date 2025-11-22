package com.msoft.carehomeapp.business.managers;

import com.msoft.carehomeapp.business.services.*;
import com.msoft.carehomeapp.model.factory.ActivitySuggestionFactory;
import com.msoft.carehomeapp.model.*;
import com.msoft.carehomeapp.model.Emotion.EmotionName;
import com.msoft.carehomeapp.model.Emotion.EmotionType;
import com.msoft.carehomeapp.model.factory.RoomFactory;
import java.util.List;

/**
 *
 * @author lucas
 */
public class EmotionManager {
    
    //Inyection 
    //Managers
    private final PreferencesManager prefsManager;
    private final RecordsManager recordsManager;
    private final DeviceManager deviceManager;
    

    // INNER STATIC RESPONSE CLASS
    public static class Response {
        private final String musicMessage;
        private final String lightsMessage;
        private final List<ActivitySuggestion> activities;
        private final EmotionalReport reportDraft;

        public Response(String m, String l, List<ActivitySuggestion> acts, EmotionalReport draft) {
            this.musicMessage = m;
            this.lightsMessage = l;
            this.activities = acts;
            this.reportDraft = draft;
        }

        public String getMusicMessage() { return musicMessage; }
        public String getLightsMessage() { return lightsMessage; }
        public List<ActivitySuggestion> getActivities() { return activities; }
        public EmotionalReport getDraftReport() { return reportDraft; }
    }
   
    
    public EmotionManager(
        RecordsManager recordsManager,
        PreferencesManager prefsManager,
        DeviceManager deviceManager
    ) {
        this.recordsManager = recordsManager;
        this.prefsManager = prefsManager;
        this.deviceManager = deviceManager;
    }
    
    public Response processEmotion(
            Emotion.EmotionName name,
            int intensity,
            Room room,
            Song selectedSong){
        
        // Create emotion & state
        EmotionalState state = buildState(name, intensity);
        
        // Get preferences through the manager
        Preferences prefs = prefsManager.getPreferences();       
        
        // Play Music
        String musicMsg = deviceManager.applyMusic(room, selectedSong);   
        
        // Adjsust Lights
        String lightsMsg = deviceManager.applyLighting(room, state.getEmotion().getType(), prefs);
        
        
        // Get Activitys 
        List<ActivitySuggestion> activities = 
                ActivitySuggestionFactory.getSuggestionsFor(state.getEmotion().getType());
        
        // Draft report 
        EmotionalReport draft = new EmotionalReport(state, room, null);
        
        return new Response(musicMsg, lightsMsg, activities, draft);
    }
    
    public Emotion.EmotionType determineType(Emotion.EmotionName n) {
        switch (n) {
            case HAPPY, EXCITED,  INSPIRED, MOTIVATED:
                return Emotion.EmotionType.POSITIVE;
            case SAD, DEPRESSED, ANXIOUS, FEAR, INSECURE:
                return Emotion.EmotionType.NEGATIVE;
            default:
                return Emotion.EmotionType.NEUTRAL;
        }
    }
    
    public void logMinimalReport(Emotion.EmotionName emotionName, int intensity){
        
        Emotion.EmotionType type = determineType(emotionName);
        
        EmotionalState state = buildState(emotionName, intensity);
        
        Room unknownRoom = RoomFactory.createUnknownRoom();
        
        ActivitySuggestion unknownActivity = ActivitySuggestionFactory.createUnknownActivity();
        
        EmotionalReport report = new EmotionalReport(state,
                unknownRoom, 
                unknownActivity
        );
        //Save the report
        recordsManager.saveReport(report);
        
        };
    public void logMinimalReport(Emotion.EmotionName emotionName, int intensity, Room room){
        
        Emotion.EmotionType type = determineType(emotionName);
        
        Emotion emotion = new Emotion(emotionName, type);
        EmotionalState state = new EmotionalState(emotion, intensity);
        
        ActivitySuggestion unknownActivity = ActivitySuggestionFactory.createUnknownActivity();
        
        EmotionalReport report = new EmotionalReport(state,
                room, 
                unknownActivity
        );
        //Save the report 
        recordsManager.saveReport(report);
    }
    
    private EmotionalState buildState(EmotionName name, int intensity) {
        EmotionType type = determineType(name);
        Emotion emotion = new Emotion(name, type);
        return new EmotionalState(emotion, intensity);
    }    
}  
