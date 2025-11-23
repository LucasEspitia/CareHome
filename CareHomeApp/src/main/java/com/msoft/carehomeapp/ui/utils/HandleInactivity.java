package com.msoft.carehomeapp.ui.utils;

import com.msoft.carehomeapp.business.managers.EmotionManager;
import com.msoft.carehomeapp.ui.SceneSwitcher;
import com.msoft.carehomeapp.ui.controllers.registerEmotion.RegisterSession;
import javafx.scene.Node;


/** 
 *
 * @author lucas
 */
public class HandleInactivity {
    
    /**
     *
     * @param manager
     * @param msg
     * @param rootNode
     */
    public static void saveMinimalAndExitWithRoom(EmotionManager manager, String msg,  Node rootNode) {
        manager.logMinimalReport(
                RegisterSession.emotionName,
                RegisterSession.intensity,
                RegisterSession.room
        );

        AlertUtils.infoNonBlocking("Session expired", msg);
        SceneSwitcher.switchScene(rootNode, "HomeView.fxml");
    }
    
    public static void saveMinimalAndExitNoRoom(
            EmotionManager manager,
            String msg,
            Node anyUiElement
    ) {
        manager.logMinimalReport(
                RegisterSession.emotionName,
                RegisterSession.intensity
        );

        AlertUtils.infoNonBlocking("Session expired", msg);
        SceneSwitcher.switchScene(anyUiElement, "HomeView.fxml");
    }
    
}
