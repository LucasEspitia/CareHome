package com.msoft.carehomeapp.model.factory;

import com.msoft.carehomeapp.data.devices.implementation.AudioDevice;
import com.msoft.carehomeapp.data.devices.implementation.LightDevice;
import com.msoft.carehomeapp.model.Room;
import java.util.ArrayList;

import java.util.List;
/**
 *
 * @author lucas
 */
public class RoomFactory {
    
    private static List<Room> listRooms;
    
    public static void createRooms(){
        listRooms = new ArrayList<Room>();
        listRooms.add(createBathroom());
        listRooms.add(createBedroom());
        listRooms.add(createKitchen());
        listRooms.add(createLivingRoom());
    }
    
    public static List<Room> getListRooms() {
        if (listRooms == null) {
            createRooms();
        }
        return listRooms;
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
