package com.msoft.carehomeapp.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.msoft.carehomeapp.model.Emotion;
import com.msoft.carehomeapp.ui.RegisterSession;
import com.msoft.carehomeapp.ui.SceneSwitcher;

/**
 *
 * @author lucas
 */
public class RegisterEmotionController {
    @FXML private ComboBox<Emotion.EmotionName> comboEmotion;
    @FXML private Slider sliderIntensity;
    @FXML private Button btnNext;

    @FXML
    public void initialize() {

        comboEmotion.getItems().addAll(Emotion.EmotionName.values());

        btnNext.setOnAction(e -> {
            if (comboEmotion.getValue() == null) return;

            RegisterSession.emotionName = comboEmotion.getValue();
            RegisterSession.intensity = (int) sliderIntensity.getValue();

            SceneSwitcher.switchScene(btnNext, "SelectRoomView.fxml");
        });
    }
    
}
