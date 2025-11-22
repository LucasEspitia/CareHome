/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.msoft.carehomeapp.model;

/**
 *
 * @author lucas
 */

public class Emotion {
    private EmotionName displayName;   
    private EmotionType type;
    
    public static enum EmotionName{
        HAPPY, EXCITED,  INSPIRED, MOTIVATED,
        SAD, DEPRESSED, ANXIOUS, FEAR, INSECURE,
        RELAX, CALM, PEACEFUL
    };
    
   
    public static enum EmotionType {
        POSITIVE,
        NEGATIVE,
        NEUTRAL;
        
    };
    
    public Emotion(EmotionName displayName, EmotionType type){
        this.displayName = displayName;
        this.type = type;
    }
  
    public EmotionName getName() { 
        return displayName; 
    }
    public EmotionType getType() {
        return type;
    }
    public boolean isPositive(){
        return this.type == EmotionType.POSITIVE;
    }
   
   
}
