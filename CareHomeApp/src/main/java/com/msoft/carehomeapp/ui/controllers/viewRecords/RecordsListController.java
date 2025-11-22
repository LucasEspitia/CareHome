package com.msoft.carehomeapp.ui.controllers.viewRecords;

import com.msoft.carehomeapp.AppContext;
import com.msoft.carehomeapp.business.managers.EmotionManager;
import com.msoft.carehomeapp.business.managers.RecordsManager;
import com.msoft.carehomeapp.model.Emotion;
import com.msoft.carehomeapp.model.EmotionalReport;
import com.msoft.carehomeapp.model.ReportFilter;
import com.msoft.carehomeapp.model.Room;
import com.msoft.carehomeapp.model.factory.RoomFactory;
import com.msoft.carehomeapp.ui.controllers.SceneSwitcher;
import com.msoft.carehomeapp.ui.utils.AlertUtils;
import java.time.LocalDate;
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
    @FXML private ComboBox<String> comboRoom;
    @FXML private ComboBox<Integer> comboIntensityMin;
    @FXML private ComboBox<Integer> comboIntensityMax;
    
    @FXML private DatePicker dateFrom;
    @FXML private DatePicker dateTo;
    
    @FXML private Button btnApplyFilters;
    @FXML private Button btnClearFilters;
    @FXML private Button btnExport;
    @FXML private Button btnBack;

    private final RecordsManager recordsManager = AppContext.getRecordsManager();
    private final EmotionManager emotionManager = AppContext.getEmotionManager();

    private int currentOffset = 0;
    private final int batchSize = 10;
    private boolean isLoading = false;
    private boolean noMoreData = false;
  
    @FXML
    public void initialize() {
        
        disableFutureDates(dateFrom);
        disableFutureDates(dateTo);
        
        setupTableColumns();
        setupFilterValues();
        setupButtons();
        enableValidation();
        
        loadMoreData();
        enableAutoLoad();
        
    
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
        comboEmotion.getItems().add("All");
        comboEmotion.getItems().addAll(Emotion.EmotionName.values());
        
        comboType.getItems().add("All");
        comboType.getItems().addAll(Emotion.EmotionType.values());
        
        comboRoom.getItems().add("All");
        comboRoom.getItems().addAll(
                RoomFactory.getListRooms().stream()
                .map(Room::getName)
                .toList()
        );
        
            for (int i = 1; i <= 10; i++) {
            comboIntensityMin.getItems().add(i);
            comboIntensityMax.getItems().add(i);
        }

        comboIntensityMin.setPromptText("Any");
        comboIntensityMax.setPromptText("Any");
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
            SceneSwitcher.switchScene(e, "/viewRecords/ExportRecordsView.fxml");
            
        });
    }
    
    // LOAD MORE RECORDS
    private void loadMoreData() {
        
        //If export is cancelled.
        if(RecordsSession.getCurrentFilter() != null){
            List<EmotionalReport> results = recordsManager.filter(RecordsSession.getCurrentFilter());
            tableRecords.getItems().addAll(results);
            
            return;
        }
        
        if (isLoading || noMoreData) return;

        isLoading = true;

        List<EmotionalReport> list = recordsManager.getPaged(currentOffset, batchSize);
                
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
        RecordsSession.setCurrentFilter(null);
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

        // ---- EMOTION NAME ----
        Object selectedEmotion = comboEmotion.getValue();
        if (selectedEmotion instanceof Emotion.EmotionName en) {
            filter.setEmotionName(en);
        }

        // ---- EMOTION TYPE ----
        Object selectedType = comboType.getValue();
        if (selectedType instanceof Emotion.EmotionType et) {
            filter.setEmotionType(et);
        }

        // ---- ROOM ----
        String room = (String) comboRoom.getValue();
        if (room != null && !room.equals("All")) {
            filter.setRoom(room);
        }

        // ---- INTENSITY MIN ----
        Object minObj = comboIntensityMin.getValue();
        if (minObj instanceof Integer min) {
            filter.setMinIntensity(min);
        }

        // ---- INTENSITY MAX ----
        Object maxObj = comboIntensityMax.getValue();
        if (maxObj instanceof Integer max) {
            filter.setMaxIntensity(max);
        }

        // ---- DATE SINCE ----
        if (dateFrom.getValue() != null) {
            filter.setFromDate(dateFrom.getValue().atStartOfDay());
        }

        // ---- DATE UNTIL ----
        if (dateTo.getValue() != null) {
            filter.setToDate(dateTo.getValue().atTime(23, 59, 59));
        }

        // -------- CALL DAO --------
        List<EmotionalReport> results = recordsManager.filter(filter);

        // -------- UPDATE TABLE --------
        tableRecords.getItems().clear();

        if (results.isEmpty()) {
            AlertUtils.info("No Data", "No records found for the selected filters.");
            return;
        }
        
        RecordsSession.setCurrentFilter(filter);
        tableRecords.getItems().addAll(results);
    }
    
    /// VALIDATION FILTERS
    // --- control flags ---
    private boolean isUpdatingEmotion = false;
    private boolean isUpdatingDate = false;
    private boolean isUpdatingIntensity = false;


    private void enableIntensityValidation() {

        comboIntensityMin.valueProperty().addListener((obs, oldVal, min) -> {
            if (isUpdatingIntensity) return;
            isUpdatingIntensity = true;

            try {
                Integer currentMax = comboIntensityMax.getValue();

                comboIntensityMax.getItems().clear();
                if (min != null) {
                    for (int i = min; i <= 10; i++) comboIntensityMax.getItems().add(i);
                } else {
                    for (int i = 1; i <= 10; i++) comboIntensityMax.getItems().add(i);
                }

                if (currentMax != null &&
                    (min == null || currentMax.compareTo(min) >= 0)) {

                    comboIntensityMax.setValue(currentMax);
                } else {
                    comboIntensityMax.setValue(null);
                }

            } finally {
                isUpdatingIntensity = false;
            }
        });


        comboIntensityMax.valueProperty().addListener((obs, oldVal, max) -> {
            if (isUpdatingIntensity) return;
            isUpdatingIntensity = true;

            try {
                Integer currentMin = comboIntensityMin.getValue();

                comboIntensityMin.getItems().clear();
                if (max != null) {
                    for (int i = 1; i <= max; i++) comboIntensityMin.getItems().add(i);
                } else {
                    for (int i = 1; i <= 10; i++) comboIntensityMin.getItems().add(i);
                }

                if (currentMin != null &&
                    (max == null || currentMin.compareTo(max) <= 0)) {

                    comboIntensityMin.setValue(currentMin);
                } else {
                    comboIntensityMin.setValue(null);
                }

            } finally {
                isUpdatingIntensity = false;
            }
        });
    }

    private void enableEmotionTypeValidation() {

        comboEmotion.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (isUpdatingEmotion) return;
            isUpdatingEmotion = true;

            try {
                // Emotion selected → restrict Type
                if (newVal instanceof Emotion.EmotionName en) {

                    Emotion.EmotionType inferred = emotionManager.determineType(en);

                    comboType.getItems().setAll("All", inferred);
                    comboType.setValue(inferred);

                } else {
                    comboType.getItems().setAll("All");
                    comboType.getItems().addAll(Emotion.EmotionType.values());
                    comboType.setValue("All");
                }
            } finally {
                isUpdatingEmotion = false;
            }
        });

        comboType.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (isUpdatingEmotion) return;
            isUpdatingEmotion = true;

            try {
                // Type selected → restrict Emotion
                if (newVal instanceof Emotion.EmotionType et) {

                    comboEmotion.getItems().setAll("All");

                    for (Emotion.EmotionName n : Emotion.EmotionName.values()) {
                        if (emotionManager.determineType(n) == et)
                            comboEmotion.getItems().add(n);
                    }

                    comboEmotion.setValue("All");

                } else {
                    comboEmotion.getItems().setAll("All");
                    comboEmotion.getItems().addAll(Emotion.EmotionName.values());
                    comboEmotion.setValue("All");
                }
            } finally {
                isUpdatingEmotion = false;
            }
        });
    }
    
    private void enableDatesValidation() {

        dateFrom.valueProperty().addListener((obs, old, from) -> {
            if (isUpdatingDate) return;
            isUpdatingDate = true;

            try {
                if (from != null) {
                    if (dateTo.getValue() != null && dateTo.getValue().isBefore(from)) {
                        dateTo.setValue(null);
                    }

                    dateTo.setDayCellFactory(picker -> new DateCell() {
                        @Override
                        public void updateItem(LocalDate d, boolean empty) {
                            super.updateItem(d, empty);
                            setDisable(empty || d.isBefore(from));
                        }
                    });
                }
            } finally {
                isUpdatingDate = false;
            }
        });

        dateTo.valueProperty().addListener((obs, old, to) -> {
            if (isUpdatingDate) return;
            isUpdatingDate = true;

            try {
                if (to != null) {
                    if (dateFrom.getValue() != null && dateFrom.getValue().isAfter(to)) {
                        dateFrom.setValue(null);
                    }

                    dateFrom.setDayCellFactory(picker -> new DateCell() {
                        @Override
                        public void updateItem(LocalDate d, boolean empty) {
                            super.updateItem(d, empty);
                            setDisable(empty || d.isAfter(to));
                        }
                    });
                }
            } finally {
                isUpdatingDate = false;
            }
        });
    }
       
    private void enableValidation(){
        enableDatesValidation();
        enableEmotionTypeValidation();
        enableIntensityValidation();
    }
    
    // DISABLE DATES FOR FUTURE DAYS
    private void disableFutureDates(DatePicker picker) {
        picker.setDayCellFactory(p -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (empty) return;

                // ❌ Deshabilitar fechas futuras
                if (date.isAfter(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #F0F0F0;");
                }
            }
        });
    }
}