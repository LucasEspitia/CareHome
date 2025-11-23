package com.msoft.carehomeapp.ui.controllers.viewRecords;


//import com.msoft.carehomeapp.model.ExportFormat;
import com.msoft.carehomeapp.AppContext;
import com.msoft.carehomeapp.business.managers.RecordsManager;
import com.msoft.carehomeapp.model.EmotionalReport;
import com.msoft.carehomeapp.ui.SceneSwitcher;
import com.msoft.carehomeapp.ui.utils.AlertUtils;
import com.msoft.carehomeapp.utils.ExportUtility;
import java.io.File;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

/**
 *
 * @author lucas
 */
public class ExportRecordsController {
    @FXML private ComboBox<String> formatCombo;  
    @FXML private CheckBox includeSuggestionsCheck;
    @FXML private CheckBox includeEnvSettingsCheck;
    @FXML private CheckBox includeIntensityChartCheck;
    @FXML private Button exportBtn;
    @FXML private Button cancelBtn;
    
    private final RecordsManager recordsManager = AppContext.getRecordsManager();
    private List<EmotionalReport> list;
    
    public void initialize(){
        formatCombo.getItems().addAll("PDF", "CSV");
        includeSuggestionsCheck.setSelected(true);
        exportBtn.setOnAction(e -> confirmExport());
        cancelBtn.setOnAction(e -> backToList());

    }
    
    private void confirmExport() {
        if (formatCombo.getValue() == null) {
            AlertUtils.warning("No Format Selected", 
                    "Please select a file format before proceeding.");
            return;
        }

        list = resolveRecordsToExport();

        if (list.isEmpty()) {
            AlertUtils.error("No Records Fetched", "Please Try Again.");
            return;
        }

        exportingOptions();
        backToList();
    }
    
    private List<EmotionalReport> resolveRecordsToExport() {
        if (RecordsSession.getCurrentFilter() == null) {
            return recordsManager.getLastN(50);
        }
        return recordsManager.filter(RecordsSession.getCurrentFilter());
    }

    
    private void exportingOptions(){
        String selectedFormat = formatCombo.getValue();
        boolean includeSuggestions = includeSuggestionsCheck.isSelected();
        boolean includeEnv = includeEnvSettingsCheck.isSelected();
        boolean includeChart = includeIntensityChartCheck.isSelected();
        
        boolean ok = switch (selectedFormat) {
                case "CSV" -> exportCSV(includeSuggestions, includeEnv, includeChart);
                case "PDF" -> exportPDF(includeSuggestions, includeEnv, includeChart);
                default -> false;
        };
        if (!ok) {
            AlertUtils.error("Error", "Failed to export the file.");
                return;
            }   

        AlertUtils.confirm("Success", "The file has been exported successfully!");
    }
    private boolean exportCSV(boolean includeSuggestions, boolean includeEnv, boolean includeChart) {
        File file = ExportUtility.chooseExportLocation(exportBtn.getScene().getWindow(), "csv");
        return ExportUtility.generateCSV(file, list, includeSuggestions, includeEnv, includeChart);
    }

    private boolean exportPDF(boolean includeSuggestions, boolean includeEnv, boolean includeChart) {
        File file = ExportUtility.chooseExportLocation(exportBtn.getScene().getWindow(), "pdf");
        return ExportUtility.generatePDF(file, list, includeSuggestions, includeEnv, includeChart);
    }

    private void backToList(){
        SceneSwitcher.switchScene(formatCombo, "/viewRecords/RecordsListView.fxml");
    }
}