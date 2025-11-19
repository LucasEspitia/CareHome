package com.msoft.carehomeapp.factory;

import com.msoft.carehomeapp.model.ActivitySuggestion;
import com.msoft.carehomeapp.model.Emotion;
import java.util.*;

/**
 *
 * @author lucas
 */
public class ActivitySuggestionFactory {
    private static final Map<Emotion.EmotionType, List<ActivitySuggestion>> SUGGESTIONS = new HashMap<>();
    
    static {
        SUGGESTIONS.put(Emotion.EmotionType.POSITIVE, List.of(
            new ActivitySuggestion("Light exercise or a walk"),
            new ActivitySuggestion("Dance"),
            new ActivitySuggestion("Write in a gratitude journal"),
            new ActivitySuggestion("Call a friend"),
            new ActivitySuggestion("Work on a hobby")
        ));
        
        SUGGESTIONS.put(Emotion.EmotionType.NEUTRAL, List.of(
            new ActivitySuggestion("Read a book"),
            new ActivitySuggestion("Organize the house"),
            new ActivitySuggestion("Drink a hot tea"),
            new ActivitySuggestion("Call with someone close")
        ));
        
        SUGGESTIONS.put(Emotion.EmotionType.NEGATIVE, List.of(
            new ActivitySuggestion("Breathing exercise"),
            new ActivitySuggestion("Gentle stretches"),
            new ActivitySuggestion("Take a break from your phone"),
            new ActivitySuggestion("Write how you feel"),
            new ActivitySuggestion("Call an emergency person")
        ));
    }
    
    public static List<ActivitySuggestion> getSuggestionsFor(Emotion.EmotionType type) {
        return SUGGESTIONS.get(type);
    }
}
