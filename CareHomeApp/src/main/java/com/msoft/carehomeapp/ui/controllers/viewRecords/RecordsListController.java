/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.msoft.carehomeapp.ui.controllers.viewRecords;

import com.msoft.carehomeapp.AppContext;
import com.msoft.carehomeapp.business.managers.RecordsManager;
import com.msoft.carehomeapp.model.*;
import com.msoft.carehomeapp.ui.SceneSwitcher;
import com.msoft.carehomeapp.ui.utils.AlertUtils;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author lucas
 */
public class RecordsListController {
    @FXML private ComboBox<Emotion.EmotionName> comboEmotion;
    @FXML private ComboBox<Emotion.EmotionType> comboType;
    @FXML private ComboBox<String> comboRoom;

    @FXML private DatePicker dateFrom;
    @FXML private DatePicker dateTo;

    @FXML private TextField fieldIntensityMin;
    @FXML private TextField fieldIntensityMax;

    @FXML private Button btnApplyFilters;
    @FXML private Button btnClearFilters;
    @FXML private Button btnExport;
    @FXML private Button btnBack;

    @FXML private TableView<EmotionalReport> tableRecords;
    @FXML private TableColumn<EmotionalReport, String> colDate;
    @FXML private TableColumn<EmotionalReport, String> colEmotion;
    @FXML private TableColumn<EmotionalReport, String> colType;
    @FXML private TableColumn<EmotionalReport, Integer> colIntensity;
    @FXML private TableColumn<EmotionalReport, String> colRoom;
    @FXML private TableColumn<EmotionalReport, String> colActivity;
    @FXML private TableColumn<EmotionalReport, String> colSong;

    private final RecordsManager recordsManager = AppContext.getRecordsManager();

    private int offset = 0;
    private final int PAGE_SIZE = 10;    
    @FXML 
    public void initalize(){
        //Get initial data
        
        //Fill filters
    }
}
