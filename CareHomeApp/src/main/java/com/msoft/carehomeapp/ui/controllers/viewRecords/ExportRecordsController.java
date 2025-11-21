package com.msoft.carehomeapp.ui.controllers.viewRecords;


//import com.msoft.carehomeapp.model.ExportFormat;
import com.msoft.carehomeapp.AppContext;
import com.msoft.carehomeapp.business.managers.RecordsManager;
import com.msoft.carehomeapp.model.EmotionalReport;
import com.msoft.carehomeapp.ui.SceneSwitcher;
import com.msoft.carehomeapp.ui.utils.AlertUtils;
import com.msoft.carehomeapp.utils.ExportUtility;
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
        
        exportBtn.setOnAction(e -> confirmExport());
        cancelBtn.setOnAction(e -> close());

    }
    
    private void confirmExport(){
        if(formatCombo.getValue() == null ){
            AlertUtils.warning("No Format Selected", 
                    "Please select a file format before proceeding.");
            return;
        }
        //Get values
        list = recordsManager.getAll();
        
        if (list.isEmpty()){
            AlertUtils.error("No Records Fetched", "Please Try Again.");
            return;
        }
        
        exportingOptions();
        AlertUtils.confirm("Export Completed", "The file has been exported successfully!");
        close();
    }
    
    private void exportingOptions(){
        String selectedFormat = formatCombo.getValue();
        
        switch(selectedFormat){
            case "CSV":
                ExportUtility.generateCSV(list);
                break;
            case "PDF": 
                ExportUtility.generatePDF(list);
                break;
            default:
                System.out.println("In development");  
        }
    }
    
    private void close(){
        SceneSwitcher.switchScene(formatCombo, "/viewRecords/RecordsListView.fxml");
    }
}