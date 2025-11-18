package com.msoft.carehomeapp.model;

/**
 *
 * @author lucas
 */
public class ActivitySuggestion {
    private String text;
    public ActivitySuggestion(String text){
        this.text = text;
    }
    public String getText(){
        return text;
    }
    @Override
    public String toString(){
        return text;
    }
}
