/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.msoft.carehomeapp.devices;

/**
 *
 * @author lucas
 */
public class LightDevice implements SmartDevice {
    
    //-------------- Attributes ----------------
    private String deviceID;
    private String deviceBrand;
    private String currentColor = "white";
    
    //----------- Constructors ----------------
    public LightDevice(){}
    public LightDevice(String deviceID, String deviceBrand){
        this.deviceID = deviceID;
        this.deviceBrand = deviceBrand;
    }
    //----------- Getters ----------------
    public String getCurrentColor() {
        return currentColor;
    }
    
    public String getDeviceID(){
        return deviceID;
    }
    public String getDeviceBrand(){
        return deviceBrand;
    }    
    
    //----------- Setters ----------------
    public void setColor(String color) {
        this.currentColor = color;
        System.out.println("[LightDevice] Light color set to: " + color);
    } 
    
    //----------- Methods ----------------
    @Override
    public boolean testConnection(){
        System.out.println("[LightDevice] Testing connection... OK");
        return true;
    }
   
}
