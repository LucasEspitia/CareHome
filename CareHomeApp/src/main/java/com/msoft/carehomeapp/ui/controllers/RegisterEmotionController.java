package com.msoft.carehomeapp.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.msoft.carehomeapp.model.Emotion;
import com.msoft.carehomeapp.ui.RegisterSession;
import com.msoft.carehomeapp.ui.SceneSwitcher;
import com.msoft.carehomeapp.ui.utils.AlertUtils;

/**
 *
 * @author lucas
 */
public class RegisterEmotionController {
    @FXML private ComboBox<Emotion.EmotionName> comboEmotion;
    @FXML private Slider sliderIntensity;
    @FXML private Button btnNext;
    @FXML private Label labelIntensityValue;

    @FXML
    public void initialize() {        
        comboEmotion.getItems().addAll(Emotion.EmotionName.values());
        
        sliderIntensity.valueProperty().addListener((obs, oldV, newV) -> {
        int value = newV.intValue();
            labelIntensityValue.setText(String.valueOf(value));
        });

        btnNext.setOnAction(e -> {
            if (comboEmotion.getValue() == null){
                AlertUtils.warning("Missing Selection", "Please select an emotion.");
                return;
            } 

            RegisterSession.emotionName = comboEmotion.getValue();
            RegisterSession.intensity = (int) sliderIntensity.getValue();
            
            SceneSwitcher.switchScene(e, "SelectRoomView.fxml");
        });
    }
    
}
