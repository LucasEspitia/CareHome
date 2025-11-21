package com.msoft.carehomeapp.data;

import com.msoft.carehomeapp.model.*;
import java.util.List;

/**
 *
 * @author lucas
 */
public interface IRecordsDAO {
    
    void save(EmotionalReport report);
    
    List<EmotionalReport> getAll();
    
    List<EmotionalReport> getLastN(int n);
    
    List<EmotionalReport> getPaged(int offset, int limit);
    
    List<EmotionalReport> filter(ReportFilter filter);
    
    List<EmotionalReport> filterPaged(ReportFilter filter, int offset, int limit);
    
}
