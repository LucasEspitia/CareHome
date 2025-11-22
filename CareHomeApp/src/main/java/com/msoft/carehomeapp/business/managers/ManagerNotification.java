package com.msoft.carehomeapp.business.managers;

import com.msoft.carehomeapp.ui.utils.AlertUtils;
import javafx.application.Platform;
/**
 * 
 * Handles all forms of notifications (UI popups, sound, vibration, visual only)
 * Any thread can safely call these methods.
 * 
 * @author lucas
 */

public class ManagerNotification {
    /**
     * Show a JavaFX popup message (must run on FX thread).
     * @param title
     * @param message
     */
    public static void showInfo(String title, String message) {
        Platform.runLater(() -> {
            AlertUtils.info(title, message);
        });
    }
    /**
     * Sound notification (placeholder).Path could be a WAV or MP3 file later.
     * @param soundInfo
     */
    public static void playSound(String soundInfo) {
        // Future implementation: AudioClip or MediaPlayer
        System.out.println("[SOUND] " + soundInfo);
    }
    /**
     * Vibration placeholder (useful for Android version or hardware integration).
     * @param vibrationMsg
     */
    public static void playVibration(String vibrationMsg) {
        // Future implementation: Vibration
        System.out.println("[VIBRATION] " + vibrationMsg);
    }

    /**
     * Visual-only notification (UI banner in the future)
     * @param visualMsg
     */
    public static void showOnlyVisual(String visualMsg) {
        System.out.println("[VISUAL ONLY] " + visualMsg);
    }
}
