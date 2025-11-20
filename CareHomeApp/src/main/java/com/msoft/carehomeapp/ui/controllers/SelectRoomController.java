package com.msoft.carehomeapp.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.msoft.carehomeapp.model.Room;
import com.msoft.carehomeapp.model.factory.RoomFactory;
import com.msoft.carehomeapp.ui.RegisterSession;
import com.msoft.carehomeapp.ui.SceneSwitcher;
import com.msoft.carehomeapp.ui.utils.AlertUtils;
/**
 *
 * @author lucas
 */
public class SelectRoomController {

    @FXML private ComboBox<Room> comboRoom;
    @FXML private Button btnNext;
    
    @FXML
    public void initialize() {
        
        comboRoom.getItems().addAll(RoomFactory.getListRooms());

        btnNext.setOnAction(e -> {
            Room selected = comboRoom.getValue();
            if (selected == null) {
                AlertUtils.warning("Missing Selection", "Please select a room.");
                return;
            }

            RegisterSession.room = selected;        
            
            SceneSwitcher.switchScene(e, "MusicSelectionView.fxml");
        });
    }
}