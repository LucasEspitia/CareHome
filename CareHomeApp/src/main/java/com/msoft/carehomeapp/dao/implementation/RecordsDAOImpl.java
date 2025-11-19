package com.msoft.carehomeapp.dao.implementation;

import com.msoft.carehomeapp.dao.IRecordsDAO;
import com.msoft.carehomeapp.model.*;

import java.util.List;
import java.io.*;
import java.net.*;

import com.google.gson.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 *
 * @author lucas
 */
public class RecordsDAOImpl implements IRecordsDAO {
    // ---------------- BACK4APP CONF -------------   
    private static final String APP_ID = "NXSruR1F9YMCGrIX6pfkQudf7copKvjBh48U6nt9";
    private static final String API_KEY = "Jb1wtKHG4n3Qao2qpUzla7MRxWKjmRvzRHLjxMMk";
    private static final String BASE_URL = "https://parseapi.back4app.com/classes/Records";
    
    private final Gson gson = new Gson();
    
    //-----------------SingleTone----------------------
    //Instance
    private static RecordsDAOImpl instance;
    //Constructor
    private RecordsDAOImpl() {}
    //Getting only the single instance
    public static synchronized RecordsDAOImpl getInstance() {
        if (instance == null) {
            instance = new RecordsDAOImpl();
        }
        return instance;
    }
   
    @Override
    public void save(EmotionalReport report) {
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            System.out.println("POST URL = " + BASE_URL);


            conn.setRequestMethod("POST");
            conn.setRequestProperty("X-Parse-Application-Id", APP_ID);
            conn.setRequestProperty("X-Parse-REST-API-Key", API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JsonObject json = new JsonObject();
            json.addProperty("emotionName", report.getEmotionalState().getEmotion().getName().name());
            json.addProperty("emotionType", report.getEmotionalState().getEmotion().getType().name());
            json.addProperty("intensity", report.getEmotionalState().getIntensity());
            json.addProperty("room", report.getRoom().getName());
            json.addProperty("activity", report.getActivity().getText());

            
            JsonObject dateObj = new JsonObject();
            dateObj.addProperty("__type", "Date");
            dateObj.addProperty("iso", report.getDate().toString());
            json.add("date", dateObj);

            try(OutputStream os = conn.getOutputStream()){
                os.write(json.toString().getBytes());
            }

            int code = conn.getResponseCode();
            System.out.println("Back4App response: " + code);

            conn.disconnect();
        } catch (IOException e){
            System.err.println("Error saving reports: " + e);        }
    }

    @Override
    public List<EmotionalReport> getAll() {
       return fetchReports(null);
    }
    
    @Override
    public List<EmotionalReport> filter(ReportFilter filter) {

        JsonObject where = new JsonObject();

        // --- FILTER EMOTION NAME ---
        if (filter.getEmotionName() != null) {
            where.addProperty("emotionName", filter.getEmotionName().name());
        }

        // --- FILTER EMOTION TYPE ---
        if (filter.getEmotionType() != null) {
            where.addProperty("emotionType", filter.getEmotionType().name());
        }

        // --- FILTER ROOM ---
        if (filter.getRoom() != null) {
            where.addProperty("room", filter.getRoom());
        }

        // --- FILTER DATE RANGE ---
        if (filter.getFromDate() != null || filter.getToDate() != null) {

            JsonObject dateFilter = new JsonObject();

            // Lower bound
            if (filter.getFromDate() != null) {
                JsonObject gteDate = new JsonObject();
                gteDate.addProperty("__type", "Date");
                gteDate.addProperty("iso", filter.getFromDate().toString());
                dateFilter.add("$gte", gteDate);
            }

            // Upper bound
            if (filter.getToDate() != null) {
                JsonObject lteDate = new JsonObject();
                lteDate.addProperty("__type", "Date");
                lteDate.addProperty("iso", filter.getToDate().toString());
                dateFilter.add("$lte", lteDate);
            }

            where.add("date", dateFilter);
        }

        // --- FILTER INTENSITY ---
        if (filter.getMinIntensity() != null || filter.getMaxIntensity() != null) {

            JsonObject intensityFilter = new JsonObject();

            if (filter.getMinIntensity() != null) {
                intensityFilter.addProperty("$gte", filter.getMinIntensity());
            }

            if (filter.getMaxIntensity() != null) {
                intensityFilter.addProperty("$lte", filter.getMaxIntensity());
            }

            where.add("intensity", intensityFilter);
        }

        return fetchReports(where);
    }


    @Override
    public List<EmotionalReport> fetchReports(JsonObject where) {

        List<EmotionalReport> list = new ArrayList<>();

        try {
            String urlString = BASE_URL;

            // Apply filters if exists
            if (where != null) {
                String whereEncoded = URLEncoder.encode(where.toString(), StandardCharsets.UTF_8);
                urlString += "?where=" + whereEncoded;
            }

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-Parse-Application-Id", APP_ID);
            conn.setRequestProperty("X-Parse-REST-API-Key", API_KEY);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {

                JsonObject response = gson.fromJson(reader, JsonObject.class);

                JsonArray results = response.getAsJsonArray("results");

                for (JsonElement element : results) {
                    JsonObject obj = element.getAsJsonObject();

                    // ------ EMOTION ------
                    Emotion.EmotionName name =
                            Emotion.EmotionName.valueOf(obj.get("emotionName").getAsString());
                    Emotion.EmotionType type =
                            Emotion.EmotionType.valueOf(obj.get("emotionType").getAsString());
                    Emotion emotion = new Emotion(name, type);

                    // ------ STATE ------
                    int intensity = obj.get("intensity").getAsInt();
                    EmotionalState state = new EmotionalState(emotion, intensity);

                    // ------ ROOM ------
                    Room room = new Room(obj.get("room").getAsString());
                    
                    // ------ Activity --------
                    ActivitySuggestion activity = new ActivitySuggestion(obj.get("activity").getAsString());
                    // ------ DATE ------
                    JsonObject dateObj = obj.getAsJsonObject("date"); // <-- correct
                    String iso = dateObj.get("iso").getAsString();
                    LocalDateTime date = LocalDateTime.parse(iso.substring(0, iso.length() - 1)); 
                    // Remove final 'Z' to parse to LocalDateTime

                    // ------ REPORT ------
                    EmotionalReport report = new EmotionalReport(state, room, activity, date);

                    list.add(report);
                }
            }

            conn.disconnect();

        } catch (IOException e) {
            System.err.println("Error fetching reports: " + e);
        }

        return list;
    }

}
