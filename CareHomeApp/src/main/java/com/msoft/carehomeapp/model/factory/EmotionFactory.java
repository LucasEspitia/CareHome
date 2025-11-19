package com.msoft.carehomeapp.model.factory;

import com.msoft.carehomeapp.model.Emotion;
import java.util.*;
/**
 * 
 * @author lucas
 */

public class EmotionFactory {

    private static final Map<Emotion.EmotionName, Emotion> EMOTIONS = new HashMap<>();

    static {

        // POSITIVE
        put(Emotion.EmotionName.HAPPY, Emotion.EmotionType.POSITIVE);
        put(Emotion.EmotionName.EXCITED, Emotion.EmotionType.POSITIVE);
        put(Emotion.EmotionName.INSPIRED, Emotion.EmotionType.POSITIVE);
        put(Emotion.EmotionName.MOTIVATED, Emotion.EmotionType.POSITIVE);

        // NEUTRAL
        put(Emotion.EmotionName.CALM, Emotion.EmotionType.NEUTRAL);
        put(Emotion.EmotionName.PEACEFUL, Emotion.EmotionType.NEUTRAL);
        put(Emotion.EmotionName.RELAX, Emotion.EmotionType.NEUTRAL);

        // NEGATIVE
        put(Emotion.EmotionName.SAD, Emotion.EmotionType.NEGATIVE);
        put(Emotion.EmotionName.DEPRESSED, Emotion.EmotionType.NEGATIVE);
        put(Emotion.EmotionName.ANXIOUS, Emotion.EmotionType.NEGATIVE);
        put(Emotion.EmotionName.FEAR, Emotion.EmotionType.NEGATIVE);
        put(Emotion.EmotionName.INSECURE, Emotion.EmotionType.NEGATIVE);
    }

    private static void put(Emotion.EmotionName name, Emotion.EmotionType type) {
        EMOTIONS.put(name, new Emotion(name, type));
    }

    public static Emotion get(Emotion.EmotionName name) {
        return EMOTIONS.get(name);
    }

    public static List<Emotion> getAll() {
        return new ArrayList<>(EMOTIONS.values());
    }
}
