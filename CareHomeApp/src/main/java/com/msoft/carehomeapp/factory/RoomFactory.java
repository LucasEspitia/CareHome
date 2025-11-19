package com.msoft.carehomeapp.factory;

import com.msoft.carehomeapp.devices.AudioDevice;
import com.msoft.carehomeapp.devices.LightDevice;
import com.msoft.carehomeapp.model.Room;

/**
 *
 * @author lucas
 */
public class RoomFactory {
    
    public static void createRooms(){
        createBathroom();
        createBedroom();
        createKitchen();
        createLivingRoom();
    }
    
    public static Room createLivingRoom() {
        return new Room("Living Room", new AudioDevice(), new LightDevice());
    }

    public static Room createBedroom() {
        return new Room("Bedroom", new AudioDevice(), new LightDevice());
    }

    public static Room createKitchen() {
        return new Room("Kitchen", null, new LightDevice());
    }

    public static Room createBathroom() {
        return new Room("Bathroom", null, null);
    }
}
