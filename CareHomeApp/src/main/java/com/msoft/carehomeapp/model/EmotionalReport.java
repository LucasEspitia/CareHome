/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.msoft.carehomeapp.model;

import java.time.LocalDateTime;

/**
 *
 * @author lucas
 */
public class EmotionalReport {
    
    //Attributes
    private EmotionalState currentState;
    private Room currentRoom;
    private LocalDateTime currentDate;
    
    //Constructor
    public EmotionalReport(EmotionalState currentState, Room currentRoom){
        this.currentState = currentState;
        this.currentRoom = currentRoom;
        this.currentDate = LocalDateTime.now();
    }
    
    
    
    
}
