package com.msoft.carehomeapp.model.factory;

import com.msoft.carehomeapp.model.Emotion;
import com.msoft.carehomeapp.model.Preferences;
import com.msoft.carehomeapp.model.Song;
import java.util.List;

/**
 *
 * @author lucas
 */
public class PreferencesFactory {
      public static Preferences createDefaultPreferences() {
        Preferences prefs = new Preferences();
      // ------------------------- MUSIC --------------------------------
        for (Emotion.EmotionType type : Emotion.EmotionType.values()) {
            List<Song> songs = SongFactory.getDefaultSongsFor(type);
            prefs.setPreferredMusic(type, songs);
        }

        // -------------------- LIGHTING ---------------------------------
        prefs.setPreferredLighting(Emotion.EmotionType.POSITIVE, "warm-yellow");
        prefs.setPreferredLighting(Emotion.EmotionType.NEUTRAL, "soft-white");
        prefs.setPreferredLighting(Emotion.EmotionType.NEGATIVE, "cool-blue");

        // Notifications enabled by default
        prefs.setNotificationsEnabled(true);

        return prefs;   
    }
}
