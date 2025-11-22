package com.msoft.carehomeapp.model;

/**
 *
 * @author lucas
 */
public class NotificationScheduleConfig {
    
    public enum NotificationType {
        POPUP, SOUND, VIBRATION, VISUAL_ONLY;
         @Override
        public String toString() {
            String normal = name().toLowerCase().replace("_", " ");
            return Character.toUpperCase(normal.charAt(0)) + normal.substring(1);
        }
    }
    //-------- Attributes ----------
    private int frequencyMinutes;
    private int repeat;
    private NotificationType type;
    
    //-------- Constructor ---------
    //Default Notification Config Constructor
    public NotificationScheduleConfig(NotificationType type) {
        this.frequencyMinutes = 20;
        this.repeat = 3;
        this.type = type;
    }
    //-------- Getters -------------
    public int getFrequencyMinutes(){
        return frequencyMinutes;
    }
    public int getRepeat(){
        return repeat;
    }
    public NotificationType getType(){
        return type;
    }
    //---------- Setters -------------
    public void setRepeat(int repeat){
        this.repeat = repeat;
    }
    public void setFrequency(int freq){
        this.frequencyMinutes = freq;
    }
    
}
