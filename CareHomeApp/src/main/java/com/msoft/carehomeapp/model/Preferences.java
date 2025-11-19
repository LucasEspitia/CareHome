package com.msoft.carehomeapp.model;

import java.util.*;

public class Preferences {

    // Now each EmotionType maps to a LIST of 3 Songs
    private Map<Emotion.EmotionType, List<Song>> preferredMusic = new HashMap<>();

    private Map<Emotion.EmotionType, String> preferredLighting = new HashMap<>();

    private boolean notificationsEnabled = true;

    public Preferences() {}

    // MUSIC (3 songs per emotion)
    public List<Song> getPreferredMusic(Emotion.EmotionType type) {
        return preferredMusic.getOrDefault(type, new ArrayList<>());
    }

    public void setPreferredMusic(Emotion.EmotionType type, List<Song> songs) {
        preferredMusic.put(type, songs);
    }

    public void addPreferredSong(Emotion.EmotionType type, Song song) {
        preferredMusic.computeIfAbsent(type, k -> new ArrayList<>()).add(song);
    }

    // LIGHTING
    public String getPreferredLighting(Emotion.EmotionType type) {
        return preferredLighting.get(type);
    }

    public void setPreferredLighting(Emotion.EmotionType type, String lighting) {
        preferredLighting.put(type, lighting);
    }

    // NOTIFICATIONS
    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(boolean enabled) {
        this.notificationsEnabled = enabled;
    }

    // Needed for JSON serialization
    public Map<Emotion.EmotionType, List<Song>> getPreferredMusicMap() {
        return preferredMusic;
    }

    public Map<Emotion.EmotionType, String> getPreferredLightingMap() {
        return preferredLighting;
    }
}
