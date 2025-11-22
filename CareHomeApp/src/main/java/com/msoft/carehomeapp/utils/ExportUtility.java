package com.msoft.carehomeapp.utils;

import com.msoft.carehomeapp.model.EmotionalReport;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;


public class ExportUtility {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    // ==========================================================
    // FILECHOOSER → retorna un File seleccionado por el usuario
    // ==========================================================
    public static File chooseExportLocation(Window owner, String extension) {

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save Exported Report");

        // Filtro de extensión
        FileChooser.ExtensionFilter filter =
                new FileChooser.ExtensionFilter(
                        extension.toUpperCase() + " File (*." + extension + ")",
                        "*." + extension
                );
        chooser.getExtensionFilters().add(filter);

        // Nombre sugerido
        chooser.setInitialFileName("carehome_export_" + System.currentTimeMillis() + "." + extension);

        return chooser.showSaveDialog(owner);
    }


    // ==========================================================
    // CSV EXPORT
    // ==========================================================
    public static boolean generateCSV(File file,
                                      List<EmotionalReport> list,
                                      boolean includeSuggestions,
                                      boolean includeEnv,
                                      boolean includeChart) {

        if (file == null) return false; // usuario canceló

        try {

            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);

            pw.print("Date,Time,Emotion,Intensity,Room");
            if (includeSuggestions) pw.print(",Activity");
            if (includeEnv) {
                pw.print(",LightPreferences");
                pw.print(",SongPreferences");
            }
            if (includeChart) pw.print(",IntensityTrend");
            pw.println();

            for (EmotionalReport r : list) {
                String date = r.getDate().format(dateFormatter);
                String time = r.getDate().format(timeFormatter);

                pw.print(
                        date + "," +
                        time + "," +
                        r.getEmotionalState().getEmotion().getName() + "," +
                        r.getEmotionalState().getIntensity() + "," +
                        safe(r.getRoom().toString()) + "," 
                );
                //Activity including Suggestion   
                if (includeSuggestions){
                    pw.print("," + safe(r.getActivity().toString()));
                }
                // Environment settings
                if (includeEnv) {
                    pw.print(",Not Implemented"); 
                    pw.print(",Not Implemented"); 
                }

                // Intensity Trend
                if (includeChart){
                    pw.print(",Not Implemented");
                }
                pw.println();
                }
            pw.close();
            fw.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // ==========================================================
    // PDF "simple"
    // ==========================================================
    public static boolean generatePDF(File file,
                                  List<EmotionalReport> list,
                                  boolean includeSuggestions,
                                  boolean includeEnv,
                                  boolean includeChart) {
        if (file == null) return false;

        try {
            
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            int sum = 0;
            
            if (includeChart) {
                for (EmotionalReport r : list) {
                    int intensity = r.getEmotionalState().getIntensity();
                    min = Math.min(min, intensity);
                    max = Math.max(max, intensity);
                    sum += intensity;
                }
            }
            
            double avg = includeChart ? (double) sum/list.size() : 0;
             
            Document document = new Document(); 
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            document.add(new Paragraph("CARE HOME – Emotional Report Export\n\n"));
            document.add(new Paragraph("Total Records: " + list.size() + "\n\n"));

            for (EmotionalReport r : list) {
                String date = r.getDate().format(dateFormatter);
                String time = r.getDate().format(timeFormatter);

                document.add(new Paragraph("Date: " + date));
                document.add(new Paragraph("Time: " + time));
                document.add(new Paragraph("Emotion: " + r.getEmotionalState().getEmotion().getName()));
                document.add(new Paragraph("Intensity: " + r.getEmotionalState().getIntensity()));
                document.add(new Paragraph("Room: " + safe(r.getRoom().toString())));
                if (includeSuggestions)
                    document.add(new Paragraph("Activity: " + safe(r.getActivity().toString())));

                if (includeEnv) {
                    document.add(new Paragraph("Light Preferences: Not Implemented"));
                    document.add(new Paragraph("Song Preferences: Not Implemented"));
                }

                if (includeChart){

                   document.add(new Paragraph("Intensity Trend:"));
                   document.add(new Paragraph("   Min: " + min));
                   document.add(new Paragraph("   Max: " + max));
                   document.add(new Paragraph("   Average: " + String.format("%.2f", avg)));                    
                }
                document.add(new Paragraph("--------------------------------------------\n"));
            }
            document.close();
            return true;

        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


    // ==========================================================
    // Safe helper
    // ==========================================================
    private static String safe(String s) {
        return (s == null) ? "" : s.replace(",", " ");
    }
}
