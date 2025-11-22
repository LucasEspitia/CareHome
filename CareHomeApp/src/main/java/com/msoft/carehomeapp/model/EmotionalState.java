/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.msoft.carehomeapp.model;

/**
 *
 * @author lucas
 */

public class EmotionalState {
    
    // --------------- Attributes ------------
    private Emotion emotion;
    private int intensity;
    
    // --------------- Constructor ------------
    public EmotionalState(Emotion emotion, int intensity){
        this.emotion = emotion;
        this.intensity  = intensity;
    }
    // --------------- Getters---- ------------
    public Emotion getEmotion(){
        return this.emotion;
    }
    public int getIntensity(){
        return this.intensity;
    }
    
}
