package com.msoft.carehomeapp.business.services;

import com.msoft.carehomeapp.model.ActivitySuggestion;
import com.msoft.carehomeapp.model.Emotion;

import java.util.*;

//ToDo add more Sugestion -> Selects randomly 3.
/**
 *
 * @author lucas
 */
public class ActivitySuggestionService {
    
    private final Map<Emotion.EmotionType, List<String>> suggestions = new HashMap<>();
    
       public ActivitySuggestionService() {
            suggestions.put(Emotion.EmotionType.POSITIVE, List.of(
               "Exercise",
               "Read a motivational book",
               "Work on a personal project",
               "Go for a walk",
               "Listen to upbeat music"
            ));

            suggestions.put(Emotion.EmotionType.NEUTRAL, List.of(
                "Organize your space",
                "Have some tea",
                "Listen to soft music",
                "Plan your day",
                "Take a short break"
            ));

            suggestions.put(Emotion.EmotionType.NEGATIVE, List.of(
                "Breathing exercises",
                "Go outside for some fresh air",
                "Talk to someone you trust",
                "Do some gentle stretches",
                "Write down how you feel"
            ));  
    }
    public ActivitySuggestion suggest(Emotion.EmotionType type) {
        List<String> options = suggestions.getOrDefault(type, List.of());
        if (options.isEmpty()) {
            return new ActivitySuggestion("No suggestions are available for this state.");
        }
        Random rnd = new Random();
        return new ActivitySuggestion(options.get(rnd.nextInt(options.size())));
    }  
    
}
