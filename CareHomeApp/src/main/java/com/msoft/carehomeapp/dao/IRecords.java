package com.msoft.carehomeapp.dao;

import com.google.gson.JsonObject;
import com.msoft.carehomeapp.model.*;
import java.util.List;

/**
 *
 * @author lucas
 */
public interface IRecords {
    
    void save(EmotionalReport report);
    
    List<EmotionalReport> getAll();
    
    List<EmotionalReport> filter(ReportFilter filter);
    
    List<EmotionalReport> fetchReports(JsonObject where);
       
}
