package com.msoft.carehomeapp.business.services;

import com.msoft.carehomeapp.data.devices.implementation.AudioDevice;
import com.msoft.carehomeapp.model.*;

/**
 *
 * @author lucas
 */
public class MusicService {
    
    public String playSelectedSong(Room room, Song selectedSong){
        
        //UC01 - 8: User selected 'No Music'
        if(selectedSong == null){
            return "[MusicService] User skipped music playback.";
        }
        
        AudioDevice audio = room.getAudio();
        
        //UC01 - 8a: Music playback fails
        //No connection
        if(audio == null){
            return "[MusicService] No speaker in " + room.getName() 
                    +  ". Music playback unavailable; adjusting lighting only."; 
        }
        //Fail test
        if(!audio.testConnection()){
            return "[MusicService] Speaker connection failed in " + room.getName()
                    + ". Music playback unavailable; adjusting lighting only.";
        }
        
        // All ok -> playback
        audio.play(selectedSong.getTitle());
        return null; // No message. Audio playing.
    }
    
}
