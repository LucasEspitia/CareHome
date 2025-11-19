package com.msoft.carehomeapp.model.factory;

import com.msoft.carehomeapp.model.Emotion;
import com.msoft.carehomeapp.model.Song;
import java.util.List;

/**
 *
 * @author lucas
 */
public class SongFactory {
    public static List<Song> getDefaultSongsFor(Emotion.EmotionType type) {
        return switch (type) {

            case POSITIVE -> List.of(
                new Song("Carnaval", "Joe Arroyo y La verdad", "El rey del Carnaval", "Salsa"),
                new Song("Uplift", "Positive Vibes", "Inspiration", "Dance"),
                new Song("Shining Day", "Radiant Band", "Sky High", "Indie Pop")
            );

            case NEUTRAL -> List.of(
                new Song("Chill Breeze", "Lofi Masters", "Calm Nights", "LoFi"),
                new Song("Soft Focus", "AmbientLab", "Work Mode", "Ambient"),
                new Song("Neutral Vision", "FlowState", "Deep Mind", "Electronic")
            );

            case NEGATIVE -> List.of(
                new Song("Calm Waters", "Zen Artist", "Meditation Vol.1", "Ambient"),
                new Song("Deep Breaths", "Relax Sound", "Healing Collection", "New Age"),
                new Song("Slow Drift", "Soft Spirits", "Inner Peace", "Chill")
            );
        };
    }
}
   
