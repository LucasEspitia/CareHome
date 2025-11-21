package com.msoft.carehomeapp.data.implementation;

import com.msoft.carehomeapp.data.IRecordsDAO;
import com.msoft.carehomeapp.model.*;

import java.util.List;
import java.io.*;
import java.net.*;

import com.google.gson.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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
        
    // ---------- LAYER 1 → URL BUILDER ----------------
    private String buildUrl(Map<String, String> params) throws Exception {

        StringBuilder url = new StringBuilder(BASE_URL);

        if (!params.isEmpty()) {
            url.append("?");
            boolean first = true;
            for (var entry : params.entrySet()) {
                if (!first) url.append("&");
                first = false;
                url.append(entry.getKey())
                   .append("=")
                   .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            }
        }

        return url.toString();
    }
   
    //----------- LAYER 2 → HTTP GET -------------------
    private JsonObject httpGet(String urlString) throws Exception {

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("X-Parse-Application-Id", APP_ID);
        conn.setRequestProperty("X-Parse-REST-API-Key", API_KEY);

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(conn.getInputStream()));

        JsonObject response = gson.fromJson(reader, JsonObject.class);

        conn.disconnect();
        return response;
    }

    // ---------- LAYER 3 → JSON PARSER -----------------
    private List<EmotionalReport> parseReports(JsonObject response) {

        List<EmotionalReport> list = new ArrayList<>();

        if (response == null || !response.has("results"))
            return list;

        JsonArray results = response.getAsJsonArray("results");

        for (JsonElement element : results) {
            JsonObject obj = element.getAsJsonObject();

            // ----- Emotion -----
            Emotion.EmotionName name =
                    Emotion.EmotionName.valueOf(obj.get("emotionName").getAsString());

            Emotion.EmotionType type =
                    Emotion.EmotionType.valueOf(obj.get("emotionType").getAsString());

            Emotion emotion = new Emotion(name, type);

            // ----- State -----
            int intensity = obj.get("intensity").getAsInt();
            EmotionalState state = new EmotionalState(emotion, intensity);

            // ----- Room -----
            Room room = new Room(obj.get("room").getAsString());

            // ----- Activity -----
            ActivitySuggestion activity =
                    new ActivitySuggestion(obj.get("activity").getAsString());

            // ----- Date -----
            String iso = obj.getAsJsonObject("date").get("iso").getAsString();
            LocalDateTime date = LocalDateTime.parse(iso.substring(0, iso.length() - 1));

            // ----- Report -----
            EmotionalReport report = new EmotionalReport(state, room, activity, date);

            list.add(report);
        }

        return list;
    }
    
     // Where JSON builder (used by filter & filterPaged)
    private JsonObject buildWhere(ReportFilter filter) {

        JsonObject where = new JsonObject();

        // Emotion Name
        if (filter.getEmotionName() != null)
            where.addProperty("emotionName", filter.getEmotionName().name());

        // Emotion Type
        if (filter.getEmotionType() != null)
            where.addProperty("emotionType", filter.getEmotionType().name());

        // Room
        if (filter.getRoom() != null)
            where.addProperty("room", filter.getRoom());

        // Date Range
        if (filter.getFromDate() != null || filter.getToDate() != null) {

            JsonObject dateFilter = new JsonObject();

            if (filter.getFromDate() != null) {
                JsonObject gte = new JsonObject();
                gte.addProperty("__type", "Date");
                gte.addProperty("iso", filter.getFromDate().toString());
                dateFilter.add("$gte", gte);
            }

            if (filter.getToDate() != null) {
                JsonObject lte = new JsonObject();
                lte.addProperty("__type", "Date");
                lte.addProperty("iso", filter.getToDate().toString());
                dateFilter.add("$lte", lte);
            }

            where.add("date", dateFilter);
        }

        // Intensity
        if (filter.getMinIntensity() != null || filter.getMaxIntensity() != null) {
            JsonObject intFilter = new JsonObject();

            if (filter.getMinIntensity() != null)
                intFilter.addProperty("$gte", filter.getMinIntensity());

            if (filter.getMaxIntensity() != null)
                intFilter.addProperty("$lte", filter.getMaxIntensity());

            where.add("intensity", intFilter);
        }

        return where;
    }
    
    @Override
    public void save(EmotionalReport report) {
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("X-Parse-Application-Id", APP_ID);
            conn.setRequestProperty("X-Parse-REST-API-Key", API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // ---- BUILD JSON BODY ----
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

            // ---- SEND ----
            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.toString().getBytes(StandardCharsets.UTF_8));
            }

            int code = conn.getResponseCode();
            System.out.println("Back4App response: " + code);

            conn.disconnect();

        } catch (IOException e) {
            System.err.println("Error saving report: " + e);
        }
    }
  
    @Override
    public List<EmotionalReport> getAll() {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("order", "-date");

            String url = buildUrl(params);
            JsonObject response = httpGet(url);

            return parseReports(response);

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public List<EmotionalReport> getLastN(int n) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("limit", String.valueOf(n));
            params.put("order", "-date");

            String url = buildUrl(params);
            JsonObject response = httpGet(url);

            return parseReports(response);

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public List<EmotionalReport> getPaged(int offset, int limit) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("limit", String.valueOf(limit));
            params.put("skip", String.valueOf(offset));
            params.put("order", "-date");

            String url = buildUrl(params);
            JsonObject response = httpGet(url);

            return parseReports(response);

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public List<EmotionalReport> filter(ReportFilter filter) {
        try {
            JsonObject where = buildWhere(filter);

            Map<String, String> params = new HashMap<>();
            params.put("where", where.toString());
            params.put("order", "-date");

            String url = buildUrl(params);
            JsonObject response = httpGet(url);

            return parseReports(response);

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public List<EmotionalReport> filterPaged(ReportFilter filter, int offset, int limit) {
        try {
            JsonObject where = buildWhere(filter);

            Map<String, String> params = new HashMap<>();
            params.put("where", where.toString());
            params.put("limit", String.valueOf(limit));
            params.put("skip", String.valueOf(offset));
            params.put("order", "-date");

            String url = buildUrl(params);
            JsonObject response = httpGet(url);

            return parseReports(response);

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    
}
