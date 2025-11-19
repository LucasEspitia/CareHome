package com.msoft.carehomeapp.factory;

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
        return new Room("Living Room");
    }

    public static Room createBedroom() {
        return new Room("Bedroom");
    }

    public static Room createKitchen() {
        return new Room("Kitchen");
    }

    public static Room createBathroom() {
        return new Room("Bathroom");
    }
}
