/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.msoft.carehomeapp.model;

import com.msoft.carehomeapp.data.devices.implementation.AudioDevice;
import com.msoft.carehomeapp.data.devices.implementation.LightDevice;

/**
 *
 * @author lucas
 */
public class Room {
    // --------- Attributes ---------
    private String name;
    private AudioDevice audio;
    private LightDevice light;
    
    // -------- Constructors --------
    public Room(String name){
        this.name = name;
    }
    
    public Room(String name, AudioDevice audio, LightDevice light){
        this.name = name;
        this.audio = audio;
        this.light = light;
    }
    
    // ---------- Getters ------------
    public String getName(){
        return name;
    }     

    public AudioDevice getAudio() { 
        return audio;
    }
    
    public LightDevice getLight(){
        return light;
    }
    
    // ---------- Setters ------------
    public void setAudio(AudioDevice audio){
        this.audio = audio;
    }
    
    public void setLight(LightDevice light){
        this.light = light;
    }
}
