package com.msoft.carehomeapp.dao;

import com.msoft.carehomeapp.dao.implementation.*;
import com.msoft.carehomeapp.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*Testing connection with the API*/
class SaveRecordsDAO {

    @Test
    void testSaveAndGetAll() {

        RecordsDAO dao = new RecordsDAO();

        // ===== CREATE TEST OBJECT =====
        Emotion emotion = new Emotion(
                Emotion.EmotionName.HAPPY,
                Emotion.EmotionType.POSITIVE
        );

        EmotionalState state = new EmotionalState(emotion, 7);
        Room room = new Room("TestRoom");

        EmotionalReport report = new EmotionalReport(state, room);

        // ===== TEST SAVE =====
        assertDoesNotThrow(() -> dao.save(report), "save() should not throw");

        // ===== TEST GET ALL =====
        List<EmotionalReport> list = dao.getAll();

        assertNotNull(list, "getAll() must not return null");
        assertTrue(list.size() > 0, "should return at least 1 record");

        // Console debug
        System.out.println("Total records: " + list.size());
        for (EmotionalReport r : list) {
            System.out.println(
                r.getEmotionalState().getEmotion().getName() +
                " | " + r.getRoom().getName() +
                " | " + r.getDate()
            );
        }
    }
}