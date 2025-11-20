package com.msoft.carehomeapp.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.msoft.carehomeapp.ui.RegisterSession;
import com.msoft.carehomeapp.ui.SceneSwitcher;
import com.msoft.carehomeapp.AppContext;
import com.msoft.carehomeapp.business.managers.RecordsManager;
import com.msoft.carehomeapp.model.*;

public class ActivitySelectionController {

    @FXML private ListView<ActivitySuggestion> listActivities;
    @FXML private Button btnConfirm;

    private final RecordsManager recordsManager = AppContext.getRecordsManager();

    @FXML
    public void initialize() {

        var response = RegisterSession.response;

        listActivities.getItems().addAll(response.getActivities());

        btnConfirm.setOnAction(e -> {

            ActivitySuggestion chosen =
                    listActivities.getSelectionModel().getSelectedItem();

            EmotionalReport draft = response.getDraftReport();
            draft.setActivity(chosen);

            recordsManager.saveReport(draft);

            RegisterSession.reset();

            SceneSwitcher.switchScene(btnConfirm, "HomeView.fxml");
        });
    }
}
