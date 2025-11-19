package com.msoft.carehomeapp.data;

import com.google.gson.JsonObject;
import com.msoft.carehomeapp.model.*;
import java.util.List;

/**
 *
 * @author lucas
 */
public interface IRecordsDAO {
    
    void save(EmotionalReport report);
    
    List<EmotionalReport> getAll();
    
    List<EmotionalReport> filter(ReportFilter filter);
    
    List<EmotionalReport> fetchReports(JsonObject where);
       
}
