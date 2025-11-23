package com.msoft.carehomeapp.model;

import java.util.*;

public class Preferences {

    // Now each EmotionType maps to a LIST of 3 Songs
    private final Map<Emotion.EmotionType, List<Song>> preferredMusic;

    private final Map<Emotion.EmotionType, String> preferredLighting;

    private boolean notificationsEnabled;

    public Preferences() {
        preferredMusic = new HashMap<>();
        preferredLighting = new HashMap<>();
        notificationsEnabled = true;
    }
    //--- getters -----
    public List<Song> getPreferredMusic(Emotion.EmotionType type) {
        return preferredMusic.getOrDefault(type, new ArrayList<>());
    }
    
    public String getPreferredLighting(Emotion.EmotionType type) {
        return preferredLighting.get(type);
    }
    
    public Map<Emotion.EmotionType, String> getPreferredLightingMap() {
        return preferredLighting;
    }
    // Needed for JSON serialization
    public Map<Emotion.EmotionType, List<Song>> getPreferredMusicMap() {
        return preferredMusic;
    }
    
    //---- setters ----
     public void setPreferredMusic(Emotion.EmotionType type, List<Song> songs) {
        preferredMusic.put(type, songs);
    }
     
    
    public void setPreferredLighting(Emotion.EmotionType type, String lighting) {
        preferredLighting.put(type, lighting);
    }
    
    public void setNotificationsEnabled(boolean enabled) {
        this.notificationsEnabled = enabled;
    }
    
    //---- methods ----
  

    public void addPreferredSong(Emotion.EmotionType type, Song song) {
        preferredMusic.computeIfAbsent(type, k -> new ArrayList<>()).add(song);
    }
    
    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }


}
