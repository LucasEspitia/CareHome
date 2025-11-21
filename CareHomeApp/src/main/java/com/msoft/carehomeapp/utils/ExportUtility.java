package com.msoft.carehomeapp.utils;

import com.msoft.carehomeapp.model.EmotionalReport;

import java.io.FileWriter;
import java.util.List;

/**
 *
 * @author lucas
 */
public class ExportUtility {

    public static void generateCSV(List<EmotionalReport> list) {
        try {
            FileWriter csv = new FileWriter("records_export.csv");

            csv.write("Date,Emotion,Intensity,Room\n");

            for (EmotionalReport r : list) {
                csv.write(r.getDate()+ "," +
                          r.getEmotionalState()+ "," +
                          r.getRoom()+ "," +
                          r.getActivity()+ "\n");
            }

            csv.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generatePDF(List<EmotionalReport> list) {
        // Por ahora placeholder
        System.out.println("PDF generation pending...");
    }
}