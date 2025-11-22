package com.msoft.carehomeapp.business.managers;

import com.msoft.carehomeapp.data.devices.implementation.AudioDevice;
import com.msoft.carehomeapp.model.Room;
import com.msoft.carehomeapp.data.devices.implementation.LightDevice;

/**
 * UC-07: Testing external light device.
 * Only tests availability/connection. No UI, no actions.
 */
public class DeviceTestManager {

    /**
     * Returns true if the room has a light device and it responds.
     * @param room
     * @return 
     */
    public boolean testLight(Room room) {
        if (room == null) return false;

        LightDevice light = room.getLight();
        if (light == null) return false;

        try {
            return light.testConnection();
        } catch (Exception e) {
            return false;
        }
    }
    public boolean testAudio(Room room){
        if (room == null) return false;
        
        AudioDevice audio = room.getAudio();
        if(audio == null) return false;
            
        try{
            return audio.testConnection();
        } catch(Exception e){
            return false;
        }
    }
}
