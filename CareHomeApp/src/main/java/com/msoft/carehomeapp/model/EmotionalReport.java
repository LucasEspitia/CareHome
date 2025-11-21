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
    private ActivitySuggestion activity;
    private LocalDateTime stampDate;
    
    // --------------- Constructor ------------
    public EmotionalReport(EmotionalState currentState, Room currentRoom, ActivitySuggestion activity){
        this.state = currentState;
        this.room = currentRoom;
        this.activity = activity;
        this.stampDate = LocalDateTime.now();
    }
    public EmotionalReport(EmotionalState state, Room room, ActivitySuggestion activity, LocalDateTime date){
        this.state = state;
        this.room = room;
        this.activity = activity;
        this.stampDate = date;
    }
    
    // ------------- Getters -----------------
    public EmotionalState getEmotionalState(){
        return this.state;
    }
    
    public Room getRoom(){
        return this.room;
    }
    
    public LocalDateTime getDate(){
        return this.stampDate;
    }
    
    public ActivitySuggestion getActivity(){
        return activity;
    }
    
    // ------------- Setters ----------------
    public void setActivity(ActivitySuggestion activity){
        this.activity = activity;
    }
  @Override
    public String toString() {
        return "Report{" +
                "date=" + stampDate +
                ", emotion=" + state.getEmotion().getName() +
                ", intensity=" + state.getIntensity() +
                ", room=" + room.getName() +
                ", activity=" + activity.getText() +
                '}';
    }
}
