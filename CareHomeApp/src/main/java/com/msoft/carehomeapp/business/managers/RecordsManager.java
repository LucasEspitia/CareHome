/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.msoft.carehomeapp.business.managers;

import com.msoft.carehomeapp.data.IRecordsDAO;
import com.msoft.carehomeapp.model.EmotionalReport;
import com.msoft.carehomeapp.model.ReportFilter;
import java.util.List;

/**
 *
 * @author lucas
 */
public class RecordsManager {
    private final IRecordsDAO recordsDAO;

    public RecordsManager(IRecordsDAO dao) {
        this.recordsDAO = dao;
    }
    
    // ----------- Methods -------------
    public void saveReport(EmotionalReport report) {
        recordsDAO.save(report);
    }

    public List<EmotionalReport> getAll() {
        return recordsDAO.getAll();
    }

    public List<EmotionalReport> getLastN(int n) {
        return recordsDAO.getLastN(n);
    }
    
    public List<EmotionalReport> getPaged(int offset, int limit) {
        return recordsDAO.getPaged(offset, limit);
    }

    public List<EmotionalReport> filter(ReportFilter filter) {
        return recordsDAO.filter(filter);
    }
    
    public List<EmotionalReport> filterPaged(ReportFilter filter, int offset, int limit) {
        return recordsDAO.filterPaged(filter, offset, limit);
    }   
}
