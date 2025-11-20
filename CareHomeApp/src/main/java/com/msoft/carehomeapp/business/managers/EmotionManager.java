package com.msoft.carehomeapp.business.managers;

import com.msoft.carehomeapp.data.IRecordsDAO;
import com.msoft.carehomeapp.business.services.*;
import com.msoft.carehomeapp.model.factory.ActivitySuggestionFactory;
import com.msoft.carehomeapp.model.*;
import java.util.List;

/**
 *
 * @author lucas
 */
public class EmotionManager {
    
    private final IRecordsDAO recordsDAO;
    private final MusicService musicService;
    private final LightningService lightingService;
    private final PreferencesManager preferencesManager;

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
            IRecordsDAO recordsDAO,
            MusicService musicService,
            LightningService lightningService,
            PreferencesManager preferencesManager
    ){
       this.recordsDAO = recordsDAO ;
       this.musicService = musicService;
       this.lightingService = lightningService;
       this.preferencesManager = preferencesManager;
    }
    
    public Response processEmotion(
            Emotion.EmotionName name,
            int intensity,
            Room room,
            Song selectedSong){
        
        // Create emotion & state
        Emotion emotion = new Emotion(name, determineType(name));
        EmotionalState state = new EmotionalState(emotion, intensity);
        
        // Get preferences through the manager
        Preferences prefs = preferencesManager.getPreferences();
        
        // Play Music
        String musicMsg = musicService.playSelectedSong(room, selectedSong);
        
        // Change Lights 
        String lightsMsg = lightingService.applyLightingForRoom(
                room, 
                emotion.getType(),
                prefs
        );

        
        // Get Activitys 
        List<ActivitySuggestion> activities = 
                ActivitySuggestionFactory.getSuggestionsFor(emotion.getType());
        
        // Draft report 
        EmotionalReport draft = new EmotionalReport(state, room, null);
        
        return new Response(musicMsg, lightsMsg, activities, draft);
    }
    
     public static Emotion.EmotionType determineType(Emotion.EmotionName n) {
        switch (n) {
            case HAPPY, EXCITED,  INSPIRED, MOTIVATED:
                return Emotion.EmotionType.POSITIVE;
            case SAD, DEPRESSED, ANXIOUS, FEAR, INSECURE:
                return Emotion.EmotionType.NEGATIVE;
            default:
                return Emotion.EmotionType.NEUTRAL;
        }
    }
    
       
}
