package com.msoft.carehomeapp.business.managers;

import com.msoft.carehomeapp.data.IPreferencesDAO;
import com.msoft.carehomeapp.model.factory.PreferencesFactory;
import com.msoft.carehomeapp.model.Emotion;
import com.msoft.carehomeapp.model.Preferences;
import com.msoft.carehomeapp.model.Song;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lucas
 */
public class PreferencesManager {
    // ------------ DAO inyected --------------
    private final IPreferencesDAO preferencesDAO;
    // ---------- Constructor ------------------
    public PreferencesManager(IPreferencesDAO dao) {
        this.preferencesDAO = dao;
    }
    /*
    * A method that ensures there are always 3 options for the user to 
    * choose from.    
    */
    public Preferences getPreferences(){
        Preferences loaded = preferencesDAO.loadPreferences();
        Preferences defaults = PreferencesFactory.createDefaultPreferences();
        
        boolean hasSavedPrefs = loaded != null;

        Preferences merged = new Preferences();
        
        for(Emotion.EmotionType type: Emotion.EmotionType.values()){
            // -------- MUSIC MERGE -------
            //Loading Songs from the DAO and Factory
            List<Song> userSongs =
                    hasSavedPrefs ? loaded.getPreferredMusic(type) : null;
            
            List<Song> defaultSongs = defaults.getPreferredMusic(type);
            
            List<Song> finalSongs = new ArrayList<>();
            
            //DAO songs is full? -> Nothing to do.
            if(userSongs != null){
                finalSongs.addAll(userSongs);
            }
            // Add till 3 songs at least per type of emotion
            for (Song s: defaultSongs){
                if(finalSongs.size() >= 3) break;
                finalSongs.add(s);
            }
            // Merge
            merged.setPreferredMusic(type, finalSongs);
            
            //------- LIGHT MERGE -------
            String userLight = 
                    hasSavedPrefs ? loaded.getPreferredLighting(type) : null;
            
            if(userLight != null){
                merged.setPreferredLighting(type, userLight);
            } else {
                merged.setPreferredLighting(type, defaults.getPreferredLighting(type));
            }
        }
            
        // ------ Notifications --------
        if (hasSavedPrefs) {
            merged.setNotificationsEnabled(loaded.isNotificationsEnabled());
        } else {
            merged.setNotificationsEnabled(defaults.isNotificationsEnabled());
        }
        return merged;
    }
    
    public void updateOrSavePreferences(Preferences newPrefs){
        preferencesDAO.saveOrUpdate(newPrefs);
    }
    
}