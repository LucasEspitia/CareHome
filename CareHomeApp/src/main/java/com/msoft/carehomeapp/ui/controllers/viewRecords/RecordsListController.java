package com.msoft.carehomeapp.ui.controllers.viewRecords;

import com.msoft.carehomeapp.AppContext;
import com.msoft.carehomeapp.business.managers.RecordsManager;
import com.msoft.carehomeapp.model.Emotion;
import com.msoft.carehomeapp.model.EmotionalReport;
import com.msoft.carehomeapp.model.ReportFilter;
import com.msoft.carehomeapp.model.factory.RoomFactory;
import com.msoft.carehomeapp.ui.SceneSwitcher;
import com.msoft.carehomeapp.ui.utils.AlertUtils;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class RecordsListController {

    @FXML private TableView<EmotionalReport> tableRecords;
    @FXML private TableColumn<EmotionalReport, String> colDate;
    @FXML private TableColumn<EmotionalReport, String> colTime;
    @FXML private TableColumn<EmotionalReport, String> colEmotion;
    @FXML private TableColumn<EmotionalReport, String> colType;
    @FXML private TableColumn<EmotionalReport, Integer> colIntensity;
    @FXML private TableColumn<EmotionalReport, String> colRoom;
    @FXML private TableColumn<EmotionalReport, String> colActivity;
    
    @FXML private ComboBox comboEmotion;
    @FXML private ComboBox comboType;
    @FXML private ComboBox comboRoom;
    @FXML private ComboBox comboIntensityMin;
    @FXML private ComboBox comboIntensityMax;
    
    @FXML private DatePicker dateFrom;
    @FXML private DatePicker dateTo;
    
    @FXML private Button btnApplyFilters;
    @FXML private Button btnClearFilters;
    @FXML private Button btnExport;
    @FXML private Button btnBack;

    private final RecordsManager recordsManager = AppContext.getRecordsManager();


    private int currentOffset = 0;
    private final int batchSize = 10;
    private boolean isLoading = false;
    private boolean noMoreData = false;
  
    @FXML
    public void initialize() {
        
        //Init value for comboBoxes
        setupFilterValues();

    
        // -------- LOAD FIRST 10 RECORDS --------
        loadMoreData();

        // -------- AUTOLOAD ON SCROLL --------
       
    }  
    // TABLE SETUP
    private void setupTableColumns(){
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        colDate.setCellValueFactory(c ->
            new SimpleStringProperty(
                  c.getValue().getDate().format(dateFormatter)
            )
        );

        colTime.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getDate().format(timeFormatter)
            )
        );

        colEmotion.setCellValueFactory(c ->
            new SimpleStringProperty(
                c.getValue().getEmotionalState().getEmotion().getName().name()
            )
        );

        colType.setCellValueFactory(c ->
            new SimpleStringProperty(
                c.getValue().getEmotionalState().getEmotion().getType().name()
            )
        );

        colIntensity.setCellValueFactory(c ->
            new SimpleObjectProperty<>(
                c.getValue().getEmotionalState().getIntensity()
            )
        );

        colRoom.setCellValueFactory(c ->
            new SimpleStringProperty(
                c.getValue().getRoom().getName()
            )
        );

        colActivity.setCellValueFactory(c ->
            new SimpleStringProperty(
                c.getValue().getActivity().getText()
            )
        );
             
        
        
    }
    
    //SETUP ALL FILTER VAUES
    private void setupFilterValues() {
        comboEmotion.getItems().addAll(Emotion.EmotionName.values());
        comboType.getItems().addAll(Emotion.EmotionType.values());
        comboRoom.getItems().addAll(
                RoomFactory.getListRooms()
        );
        
        for (int i = 1; i <= 10; i++) {
            comboIntensityMin.getItems().add(i);
            comboIntensityMax.getItems().add(i);
        }
       
    }
   
    // SETUP ALL BUTTONS
    private void setupButtons(){
        btnBack.setOnAction(e ->
            SceneSwitcher.switchScene(btnBack, "HomeView.fxml"));
        
        btnClearFilters.setOnAction(e -> {
            comboEmotion.setValue(null);
            comboType.setValue(null);
            comboRoom.setValue(null);
            comboIntensityMin.setValue(null);
            comboIntensityMax.setValue(null);
            dateFrom.setValue(null);
            dateTo.setValue(null);

            resetAndReload();
        });
        btnApplyFilters.setOnAction(e -> applyFilters());

        btnExport.setOnAction(e -> {
            AlertUtils.info("Export", "Export feature not implemented yet (optional).");
        });
    }
    
    // LOAD MORE RECORDS
    private void loadMoreData() {
        if (isLoading || noMoreData) return;

        isLoading = true;

        List<EmotionalReport> list = recordsManager.getPaged(currentOffset, batchSize);
        
        System.out.println("Lista devuelta" + list);
        
        if (list.isEmpty()) {
            noMoreData = true;

            if (currentOffset == 0)
                AlertUtils.info("No Data", "No emotional records found yet.");

            return;
        }

        tableRecords.getItems().addAll(list);

        currentOffset += batchSize;
        isLoading = false;
    }
    
    // RESET ALL VALUESs
    private void resetAndReload() {
        currentOffset = 0;
        noMoreData = false;
        tableRecords.getItems().clear();
        loadMoreData();
    }
    
    // AUTOLOAD WHEN SCROLLING
  
    private void enableAutoLoad() {

        tableRecords.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            if (newSkin == null) return;

            ScrollBar bar = (ScrollBar) tableRecords.lookup(".scroll-bar:vertical");

            if (bar != null) {
                bar.valueProperty().addListener((o, oldVal, newVal) -> {
                    if (newVal.doubleValue() > 0.90) {
                        loadMoreData();
                    }
                });
            }
        });
    }
   
    // FILTER LOGIC 
   private void applyFilters() {
        ReportFilter filter = new ReportFilter();

        if (comboEmotion.getValue() != null)
            filter.setEmotionName(comboEmotion.getValue());

        if (comboType.getValue() != null)
            filter.setEmotionType(comboType.getValue());

        if (comboRoom.getValue() != null)
            filter.setRoom(comboRoom.getValue());

        if (comboIntensityMin.getValue() != null)
            filter.setMinIntensity(comboIntensityMin.getValue());

        if (comboIntensityMax.getValue() != null)
            filter.setMaxIntensity(comboIntensityMax.getValue());

        if (dateFrom.getValue() != null)
            filter.setFromDate(dateFrom.getValue().atStartOfDay());

        if (dateTo.getValue() != null)
            filter.setToDate(dateTo.getValue().atTime(23,59,59));

        List<EmotionalReport> filtered = recordsManager.filter(filter);

        if (filtered.isEmpty()) {
            AlertUtils.warning("No Results", "No data found for selected filters.");
            return;
        }

        tableRecords.getItems().setAll(filtered);
        noMoreData = true; // avoid autoload overwriting filter
    }
}