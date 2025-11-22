package com.msoft.carehomeapp.ui.controllers.notifications;

import com.msoft.carehomeapp.business.managers.WellnessNotificationScheduler;
import com.msoft.carehomeapp.model.NotificationScheduleConfig;
import com.msoft.carehomeapp.ui.controllers.SceneSwitcher;
import com.msoft.carehomeapp.ui.utils.AlertUtils;
import com.msoft.carehomeapp.ui.utils.PromptRestore;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 *
 * @author lucas
 */
public class NotificationSettingsController {
    
    @FXML private ComboBox<String> frequencyCombo;
    @FXML private ComboBox<Integer> repeatCombo;
    @FXML private ComboBox<NotificationScheduleConfig.NotificationType> typeCombo;
    
    @FXML private Button clearFrequencyBtn;
    @FXML private Button clearRepeatBtn;
    @FXML private Button clearTypeBtn;
    @FXML private Button saveBtn;
    
    private NotificationScheduleConfig finalConfig;

            
    public void initialize(){
        frequencyCombo.getItems().addAll("10 min", "15 min", "20 min");
        repeatCombo.getItems().addAll(1, 2, 3, 4, 5);
        typeCombo.getItems().addAll(
            NotificationScheduleConfig.NotificationType.values()
        );
        //Save Button confirm inputs
        saveBtn.setOnAction(e -> {
            validateInput();
        });
        
        //Style hoover
        enableStyleHover(); 
        
        //Keep the prompt
        PromptRestore.enablePromptRestore(frequencyCombo);
        PromptRestore.enablePromptRestore(repeatCombo);
        PromptRestore.enablePromptRestore(typeCombo);
        
        //Clear each field
        enableResetValues();
         
    };

    public NotificationScheduleConfig getFinalConfig() {
        return finalConfig;
    }
    
    private void validateInput(){        
        NotificationScheduleConfig.NotificationType 
                typeSelected = typeCombo.getValue();
        
        if(typeSelected == null){
            AlertUtils.warning("No Notification Type Selected", 
                    "Please select at least the notification type before continue.");
            return;
        }
        
        //Creating with default parameters
        NotificationScheduleConfig confNoti=
                new NotificationScheduleConfig(typeSelected);

        
        String freq = frequencyCombo.getValue();  
        if(freq != null){
            System.out.print("Changing Default Value for frequency.\n");
            int minutes = Integer.parseInt(freq.replace(" min", ""));
            confNoti.setFrequency(minutes);
        }
        
        
        Integer repeat = repeatCombo.getValue();
        if(repeat != null){
            System.out.print("Changing Default Value for repeat.\n");
            confNoti.setRepeat(repeat);
        }
        
        // Save in controller
        this.finalConfig = confNoti;

        // Close modal
        saveBtn.getScene().getWindow().hide();
    }
    
    private void enableResetValues() {
        clearFrequencyBtn.setOnAction(e -> frequencyCombo.setValue(null));
        clearRepeatBtn.setOnAction(e -> repeatCombo.setValue(null));
        clearTypeBtn.setOnAction(e -> typeCombo.setValue(null));
    }

    
    private void enableStyleHover(){
        clearFrequencyBtn.setOnMouseEntered(e ->
        clearFrequencyBtn.setStyle("-fx-background-color: #bbb; -fx-background-radius: 20;"));
        clearFrequencyBtn.setOnMouseExited(e ->
        clearFrequencyBtn.setStyle("-fx-background-color: #ddd; -fx-background-radius: 20;"));

        clearRepeatBtn.setOnMouseEntered(e ->
        clearRepeatBtn.setStyle("-fx-background-color: #bbb; -fx-background-radius: 20;"));
        clearRepeatBtn.setOnMouseExited(e ->
        clearRepeatBtn.setStyle("-fx-background-color: #ddd; -fx-background-radius: 20;"));

        clearTypeBtn.setOnMouseEntered(e ->
        clearTypeBtn.setStyle("-fx-background-color: #bbb; -fx-background-radius: 20;"));
        clearTypeBtn.setOnMouseExited(e ->
        clearTypeBtn.setStyle("-fx-background-color: #ddd; -fx-background-radius: 20;"));

    }
    
}



