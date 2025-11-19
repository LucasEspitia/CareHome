package com.msoft.carehomeapp.dao;

import com.msoft.carehomeapp.data.IPreferencesDAO;
import com.msoft.carehomeapp.data.implementation.PreferencesDAOImpl;
import com.msoft.carehomeapp.model.Emotion;
import com.msoft.carehomeapp.model.Preferences;
import com.msoft.carehomeapp.model.Song;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PreferencesDAOTest {

    private final IPreferencesDAO dao = PreferencesDAOImpl.getInstance();

    @Test
    public void testSaveAndLoadPreferences() {

        System.out.println("=== TESTING PREFERENCES DAO ===");

        // ====== BUILDING TEST PREFERENCES ======
        Preferences prefs = new Preferences();

        // POSITIVE SONGS
        prefs.setPreferredMusic(
                Emotion.EmotionType.POSITIVE,
                List.of(
                        new Song("Happy Tune 1", "Artist A", "Album A", "Pop"),
                        new Song("Happy Tune 2", "Artist B", "Album B", "Pop"),
                        new Song("Happy Tune 3", "Artist C", "Album C", "Pop")
                )
        );
        prefs.setPreferredLighting(Emotion.EmotionType.POSITIVE, "warm");

        // NEUTRAL SONGS
        prefs.setPreferredMusic(
                Emotion.EmotionType.NEUTRAL,
                List.of(
                        new Song("Chill Track 1", "Chill Artist", "Chill Album", "LoFi"),
                        new Song("Chill Track 2", "Chill Artist", "Chill Album", "LoFi"),
                        new Song("Chill Track 3", "Chill Artist", "Chill Album", "LoFi")
                )
        );
        prefs.setPreferredLighting(Emotion.EmotionType.NEUTRAL, "white");

        // NEGATIVE SONGS
        prefs.setPreferredMusic(
                Emotion.EmotionType.NEGATIVE,
                List.of(
                        new Song("Calm Track 1", "Zen Artist", "Zen Album", "Ambient"),
                        new Song("Calm Track 2", "Zen Artist", "Zen Album", "Ambient"),
                        new Song("Calm Track 3", "Zen Artist", "Zen Album", "Ambient")
                )
        );
        prefs.setPreferredLighting(Emotion.EmotionType.NEGATIVE, "blue");


        // ====== SAVE TO BACK4APP ======
        System.out.println("Saving preferences...");
        dao.saveOrUpdate(prefs);

        // ====== LOAD AGAIN ======
        System.out.println("Loading preferences...");
        Preferences loaded = dao.loadPreferences();

        Assertions.assertNotNull(loaded);

        // ===== CHECK SONG LISTS =====

        Assertions.assertEquals(3, loaded.getPreferredMusic(Emotion.EmotionType.POSITIVE).size());
        Assertions.assertEquals(3, loaded.getPreferredMusic(Emotion.EmotionType.NEUTRAL).size());
        Assertions.assertEquals(3, loaded.getPreferredMusic(Emotion.EmotionType.NEGATIVE).size());

        Assertions.assertEquals("warm", loaded.getPreferredLighting(Emotion.EmotionType.POSITIVE));
        Assertions.assertEquals("white", loaded.getPreferredLighting(Emotion.EmotionType.NEUTRAL));
        Assertions.assertEquals("blue", loaded.getPreferredLighting(Emotion.EmotionType.NEGATIVE));

        // Additional check for title matching 1 song
        Song s = loaded.getPreferredMusic(Emotion.EmotionType.POSITIVE).get(0);
        Assertions.assertEquals("Happy Tune 1", s.getTitle());

        System.out.println("=== TEST PASSED SUCCESSFULLY ===");
    }
}
