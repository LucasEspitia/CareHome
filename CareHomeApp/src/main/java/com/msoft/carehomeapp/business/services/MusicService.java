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
            return "[SKIP] User skipped music playback.";
        }
        
        AudioDevice audio = room.getAudio();
        
        // All ok -> playback
        audio.play(selectedSong.getTitle());
        System.out.println("[SpeakerDevice] Playing: " 
                + selectedSong.getTitle() + 
                " - " + selectedSong.getArtist()); 
        
        return null; //All ok -> No message for Alert
        }
    
}
