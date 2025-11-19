package com.msoft.carehomeapp.business.services;

import com.msoft.carehomeapp.data.devices.implementation.LightDevice;
import com.msoft.carehomeapp.model.*;

/**
 *
 * @author lucas
 */
public class LightningService {
    
    public String applyLightingForRoom(Room room, Emotion.EmotionType type, Preferences prefs) {
        LightDevice light = room.getLight();
        
        //UC01 - Includes UC07: Test Light Connection
        //UC07-2a: Device does not respond
        if(light == null){
            return "[LightingService] No light device in " + room.getName() +
                   ". Skipping lighting adjustment.";
        }
        
        if(!light.testConnection()){
            return "[LightingService] Light connection failed in " + room.getName() +
                   ". Skipping lighting adjustment.";           
        }
        //Get color according the preferences of the user
        String color = prefs.getPreferredLighting(type);
        
        //Check for Debugging
        if (color == null) {
            System.out.println("[LightingService] No lighting preference found for" + type);
        }
        
        //All ok -> Adjust light
        light.setColor(color);
        return null; //No message. Lights are adjusted.
    }
}
