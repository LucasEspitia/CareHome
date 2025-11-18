/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
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
    
    //List<EmotionalReport>fi
    
}
