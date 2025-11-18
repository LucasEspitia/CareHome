/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.msoft.carehomeapp.model;
import java.time.Duration;

/**
 *
 * @author lucas
 */
public class NotificationPreference {
 
    //------------------Atributes---------------------------
    //Interval of Notifications
    private Duration interval;
    //Repeat notifications
    private int repeatPerActiviy;

    //Constructor with user preferences
    public NotificationPreference(Duration interval, int repeatPerActivity){
        this.interval = interval;
        this.repeatPerActiviy = repeatPerActivity;
    }
    //-----------------Constructors-------------------------
    //Constructor when user set no Notifications
    public NotificationPreference(){
        this.repeatPerActiviy = 0;
    }
    
    //------------------Getters-----------------------------
    public Duration getInterval() {
        return interval;
    }

    public int getRepeatPerActivity() {
        return repeatPerActiviy;
    }

    public boolean isEnabled() {
        return repeatPerActiviy != 0;
    }
    
}
