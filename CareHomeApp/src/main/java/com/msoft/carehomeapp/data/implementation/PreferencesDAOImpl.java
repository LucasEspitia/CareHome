package com.msoft.carehomeapp.data.implementation;

import com.google.gson.*;
import com.msoft.carehomeapp.data.IPreferencesDAO;
import com.msoft.carehomeapp.model.Emotion;
import com.msoft.carehomeapp.model.Preferences;
import com.msoft.carehomeapp.model.Song;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 *
 * @author lucas
 */
public class PreferencesDAOImpl implements IPreferencesDAO {
    // ---------------- BACK4APP CONF -------------
    private static final String APP_ID = "NXSruR1F9YMCGrIX6pfkQudf7copKvjBh48U6nt9";
    private static final String REST_KEY = "Jb1wtKHG4n3Qao2qpUzla7MRxWKjmRvzRHLjxMMk";
    private static final String BASE_URL = "https://parseapi.back4app.com/classes/Preferences";
    private final Gson gson = new GsonBuilder().create();
    
    private String preferencesObjectId = null;
    
    //------------ Singletone------------
    //Instance
    private static PreferencesDAOImpl instance;
    //Constructor
    private PreferencesDAOImpl() {}
    //Getting only the single instance
    public static synchronized PreferencesDAOImpl getInstance() {
        if (instance == null) {
            instance = new PreferencesDAOImpl();
        }
        return instance;
    }   
    

    @Override
      public Preferences loadPreferences() {
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("X-Parse-Application-Id", APP_ID);
            conn.setRequestProperty("X-Parse-REST-API-Key", REST_KEY);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
            );

            JsonObject response = gson.fromJson(reader, JsonObject.class);
            JsonArray results = response.getAsJsonArray("results");

            if (results.size() == 0) {
                return new Preferences(); // No preferences stored yet
            }

            JsonObject obj = results.get(0).getAsJsonObject();
            preferencesObjectId = obj.get("objectId").getAsString();

            Preferences prefs = new Preferences();

            // ===== MUSIC (Map<EmotionType, List<Song>>) =====
            if (obj.has("preferredMusic")) {
                JsonObject musicObj = obj.getAsJsonObject("preferredMusic");

                for (String key : musicObj.keySet()) {
                    Emotion.EmotionType type = Emotion.EmotionType.valueOf(key);

                    JsonArray songsArray = musicObj.getAsJsonArray(key);
                    List<Song> songs = new ArrayList<>();

                    for (JsonElement element : songsArray) {
                        Song song = gson.fromJson(element.getAsJsonObject(), Song.class);
                        songs.add(song);
                    }

                    prefs.setPreferredMusic(type, songs);
                }
            }

            // ===== LIGHTING (Map<EmotionType, String>) =====
            if (obj.has("preferredLighting")) {
                JsonObject lightsObj = obj.getAsJsonObject("preferredLighting");

                for (String key : lightsObj.keySet()) {
                    Emotion.EmotionType type = Emotion.EmotionType.valueOf(key);
                    prefs.setPreferredLighting(type, lightsObj.get(key).getAsString());
                }
            }

            // ===== NOTIFICATIONS =====
            if (obj.has("notificationsEnabled")) {
                prefs.setNotificationsEnabled(obj.get("notificationsEnabled").getAsBoolean());
            }

            return prefs;

        } catch (IOException e) {
            System.err.println("Error loading Preferences: " + e);
        }

        return new Preferences();
    }
      
    @Override
    public void saveOrUpdate(Preferences prefs) {
        try {
            if (preferencesObjectId == null) {
                findPreferencesObjectId();
            }

            JsonObject json = new JsonObject();
            json.add("preferredMusic", gson.toJsonTree(prefs.getPreferredMusicMap()));
            json.add("preferredLighting", gson.toJsonTree(prefs.getPreferredLightingMap()));
            json.addProperty("notificationsEnabled", prefs.isNotificationsEnabled());

            if (preferencesObjectId == null) {
                // ================= CREATE NEW =================
                URL url = new URL(BASE_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                conn.setRequestProperty("X-Parse-Application-Id", APP_ID);
                conn.setRequestProperty("X-Parse-REST-API-Key", REST_KEY);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(json.toString().getBytes(StandardCharsets.UTF_8));
                }

                conn.getInputStream(); // execute

            } else {
                // ================= UPDATE =================
                URL url = new URL(BASE_URL + "/" + preferencesObjectId);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("PUT");
                conn.setRequestProperty("X-Parse-Application-Id", APP_ID);
                conn.setRequestProperty("X-Parse-REST-API-Key", REST_KEY);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(json.toString().getBytes(StandardCharsets.UTF_8));
                }

                conn.getInputStream(); // execute
            }

        } catch (IOException e) {
            System.err.println("Error saving or updating: " + e);
        }    
    }

    @Override
    public void isNotificationEnabled() {
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("X-Parse-Application-Id", APP_ID);
            conn.setRequestProperty("X-Parse-REST-API-Key", REST_KEY);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
            );

            JsonObject response = gson.fromJson(reader, JsonObject.class);
            JsonArray results = response.getAsJsonArray("results");

            if (results.size() > 0) {
                preferencesObjectId = results.get(0)
                        .getAsJsonObject()
                        .get("notificationsEnabled")
                        .getAsString();
            }

        } catch (IOException e) {
           System.err.println("Error getting notificationsEnabled" + e);
        }
    }
    
    private void findPreferencesObjectId() {
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("X-Parse-Application-Id", APP_ID);
            conn.setRequestProperty("X-Parse-REST-API-Key", REST_KEY);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
            );

            JsonObject response = gson.fromJson(reader, JsonObject.class);
            JsonArray results = response.getAsJsonArray("results");

            if (results.size() > 0) {
                preferencesObjectId = results.get(0)
                        .getAsJsonObject()
                        .get("objectId")
                        .getAsString();
            }

        } catch (IOException e) {
            System.err.println("Error getting object ID: s" + e);
        }
    }
}
