package com.msoft.carehomeapp.business.services;

import com.msoft.carehomeapp.data.devices.implementation.LightDevice;
import com.msoft.carehomeapp.model.*;

/**
 *
 * @author lucas
 */
/**
 * Executes lighting actions on devices.
 * Does NOT test devices. No UC-07 here.
 */
public class LightningService {

    public String applyLightingForRoom(Room room, Emotion.EmotionType type, Preferences prefs) {
        if (room == null || prefs == null || type == null) {
            return "[LightingService] Invalid input. Skipping lighting adjustment.";
        }

        LightDevice light = room.getLight();
        if (light == null) {
            return "[LightingService] No light device in " + room.getName() +
                   ". Skipping lighting adjustment.";
        }

        String color = prefs.getPreferredLighting(type);

        if (color == null) {
            System.out.println("[LightingService] No lighting preference found for: " + type);
            color = "white"; // fallback simple
        }

        light.setColor(color);
        System.out.println("[LightingService] Adjusting lights to " + color +
                           " in " + room.getName());

        return null; 
    }
}
