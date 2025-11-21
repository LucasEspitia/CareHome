package com.msoft.carehomeapp.ui.controllers.viewRecords;

import com.msoft.carehomeapp.model.ReportFilter;

/**
 *
 * @author lucas
 */
public class RecordsSession {
    private static ReportFilter currentFilter;
    
    public static void setCurrentFilter(ReportFilter report){
        currentFilter = report;
    }
    public static ReportFilter getCurrentFilter(){
        return currentFilter;
    }
   
}