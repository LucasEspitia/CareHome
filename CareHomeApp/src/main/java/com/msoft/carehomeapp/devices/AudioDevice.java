/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.msoft.carehomeapp.devices;

/**
 *
 * @author lucas
 */
public class AudioDevice implements SmartDevice {
    //------------ Attributes -------------
    private String deviceID;
    private String deviceBrand;
    
    private String currentSong = null;
    
    //----------- Constructors ------------
    public AudioDevice(){}
    public AudioDevice(String deviceID, String deviceBrand){
        this.deviceID = deviceID;
        this.deviceBrand = deviceBrand;
    }
    //----------- Getters ----------------
    public String getCurrentSong() {
        return currentSong;
    }
    public String getDeviceID(){
        return deviceID;
    }
    public String getDeviceBrand(){
        return deviceBrand;
    }
    
    //------------ Methods ----------------
    @Override
    public boolean testConnection(){
        System.out.println("[AudioDevice] Testing connection... OK");
        return true;
    }
    
    public void play(String songTitle) {
        this.currentSong = songTitle;
        System.out.println("[SpeakerDevice] Playing: " + songTitle);
    }

}
