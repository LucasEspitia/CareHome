package com.msoft.carehomeapp.model;

import java.time.LocalDateTime;

/**
 *
 * @author lucas
 */
public class ReportFilter {
    private Emotion.EmotionName emotionName;
    private Emotion.EmotionType emotionType;
    private String room;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Integer minIntensity;
    private Integer maxIntensity;
    
    public ReportFilter(){};
    
    //-------------------Getters-------------------
    public Emotion.EmotionName getEmotionName(){ 
        return this.emotionName;
    }
    public Emotion.EmotionType getEmotionType(){
        return this.emotionType;
    }
    public String getRoom(){
        return this.room;
    }
    public LocalDateTime getFromDate(){
        return this.fromDate;
    }
    public LocalDateTime getToDate(){
        return this.toDate;
    }
    public Integer getMinIntensity(){
        return this.minIntensity;
    }
    public Integer getMaxIntensity(){
        return this.maxIntensity;
    }
    
    //--------------------Setters------------------------
    public void setEmotionName(Emotion.EmotionName name){
        this.emotionName = name;
    }
    public void setEmotionType(Emotion.EmotionType type){
        this.emotionType = type;
    }   
    public void setRoom(String room){
        this.room = room;
    }
    public void setFromDate(LocalDateTime date){
        this.fromDate = date;
    }
    public void setToDate(LocalDateTime date){
        this.toDate = date;
    }
    public void setMinIntensity(int intensity){
        this.minIntensity = intensity;
    }
     public void setMaxIntensity(int intensity){
        this.maxIntensity = intensity;
    }
    
}
