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
    
    // --------------- Attributes -------------
    private EmotionalState state;
    private Room room;
    private LocalDateTime stampDate;
    
    // --------------- Constructor ------------
    public EmotionalReport(EmotionalState currentState, Room currentRoom){
        this.state = currentState;
        this.room = currentRoom;
        this.stampDate = LocalDateTime.now();
    }
    public EmotionalReport(EmotionalState state, Room room, LocalDateTime date){
        this.state = state;
        this.room = room;
        this.stampDate = date;
    }
    
    // ------------- Getters -----------------
    public EmotionalState getEmotionalState(){return this.state;}
    public Room getRoom(){return this.room;}
    public LocalDateTime getDate(){return this.stampDate;}
    
    
}
