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
        if (n <= 0) return List.of();
        return recordsDAO.getLastN(n);
    }
    
    public List<EmotionalReport> getPaged(int offset, int limit) {
        if (offset < 0 || limit <= 0) return List.of();
        return recordsDAO.getPaged(offset, limit);
    }

    public List<EmotionalReport> filter(ReportFilter filter) {
        return recordsDAO.filter(filter);
    }
    
    public List<EmotionalReport> filterPaged(ReportFilter filter, int offset, int limit) {
        if (offset < 0 || limit <= 0) return List.of();
        return recordsDAO.filterPaged(filter, offset, limit);
    }   
}
