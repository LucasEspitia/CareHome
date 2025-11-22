package com.msoft.carehomeapp.business.managers;

import com.msoft.carehomeapp.business.services.LightningService;
import com.msoft.carehomeapp.business.services.MusicService;
import com.msoft.carehomeapp.model.*;

public class DeviceManager {

    private final LightningService lightingService;
    private final MusicService musicService;
    private final DeviceTestManager deviceTestManager;

    public DeviceManager(
            LightningService lightingService,
            MusicService musicService,
            DeviceTestManager testManager
    ) {
        this.lightingService = lightingService;
        this.musicService = musicService;
        this.deviceTestManager = testManager;
    }

    public String applyLighting(Room room, Emotion.EmotionType type, Preferences prefs) {
        if (!deviceTestManager.testLight(room)) {
            return "[ERROR] Light test failed for room " + room.getName();
        }
        return lightingService.applyLightingForRoom(room, type, prefs);
    }

    public String applyMusic(Room room, Song song) {
        if(!deviceTestManager.testAudio(room)){
            return "[ERROR] Audio test failed for room" + room.getName();
        }
        return musicService.playSelectedSong(room, song);
    }
}
