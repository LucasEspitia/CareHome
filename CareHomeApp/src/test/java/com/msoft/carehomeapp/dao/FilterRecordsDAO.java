/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.msoft.carehomeapp.dao;

import com.msoft.carehomeapp.model.*;
import com.msoft.carehomeapp.dao.implementation.RecordsDAOImpl;
import com.msoft.carehomeapp.model.ReportFilter;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author lucas
 */
public class FilterRecordsDAO {
        RecordsDAOImpl dao = RecordsDAOImpl.getInstance();

    @Test
    void testFilterFunctionality() {

        // ==================================================
        // STEP 1: Add multiple records (for filtering tests)
        // ==================================================
        System.out.println("=== INSERTING TEST RECORDS ===");

        EmotionalReport r1 = new EmotionalReport(
                new EmotionalState(
                        new Emotion(Emotion.EmotionName.HAPPY, Emotion.EmotionType.POSITIVE),
                        8
                ),
                new Room("Kitchen")
        );

        EmotionalReport r2 = new EmotionalReport(
                new EmotionalState(
                        new Emotion(Emotion.EmotionName.SAD, Emotion.EmotionType.NEGATIVE),
                        3
                ),
                new Room("Bedroom")
        );

        EmotionalReport r3 = new EmotionalReport(
                new EmotionalState(
                        new Emotion(Emotion.EmotionName.HAPPY, Emotion.EmotionType.POSITIVE),
                        4
                ),
                new Room("Living Room")
        );

        EmotionalReport r4 = new EmotionalReport(
                new EmotionalState(
                        new Emotion(Emotion.EmotionName.CALM, Emotion.EmotionType.NEUTRAL),
                        6
                ),
                new Room("Kitchen")
        );

        dao.save(r1);
        dao.save(r2);
        dao.save(r3);
        dao.save(r4);

        // ==================================================
        // STEP 2: FILTER BY EMOTION (HAPPY)
        // ==================================================
        System.out.println("\n=== FILTER: Emotion = HAPPY ===");

        ReportFilter f1 = new ReportFilter();
        f1.setEmotionName(Emotion.EmotionName.HAPPY);

        List<EmotionalReport> happyList = dao.filter(f1);

        assertNotNull(happyList);
        assertTrue(happyList.size() >= 2);

        happyList.forEach(r ->
                System.out.println("Happy: " + r.getEmotionalState().getIntensity()
                        + " | Room=" + r.getRoom().getName())
        );

        // ==================================================
        // STEP 3: FILTER BY ROOM (Kitchen)
        // ==================================================
        System.out.println("\n=== FILTER: Room = Kitchen ===");

        ReportFilter f2 = new ReportFilter();
        f2.setRoom("Kitchen");

        List<EmotionalReport> kitchenList = dao.filter(f2);

        assertNotNull(kitchenList);
        assertTrue(kitchenList.size() >= 2);

        kitchenList.forEach(r ->
                System.out.println("Kitchen → " + r.getEmotionalState().getEmotion().getName()
                        + " | intensity=" + r.getEmotionalState().getIntensity())
        );

        // ==================================================
        // STEP 4: FILTER BY EMOTION + ROOM
        // ==================================================
        System.out.println("\n=== FILTER: Emotion = HAPPY + Room = Kitchen ===");

        ReportFilter f3 = new ReportFilter();
        f3.setEmotionName(Emotion.EmotionName.HAPPY);
        f3.setRoom("Kitchen");

        List<EmotionalReport> combinedList = dao.filter(f3);

        assertNotNull(combinedList);

        combinedList.forEach(r ->
                System.out.println("HAPPY in Kitchen: intensity=" + 
                    r.getEmotionalState().getIntensity())
        );

        // ==================================================
        // STEP 5: FILTER BY DATE RANGE (last 1 hour)
        // ==================================================
        System.out.println("\n=== FILTER: Last hour ===");

        ReportFilter f4 = new ReportFilter();
        f4.setFromDate(LocalDateTime.now().minusHours(1));

        List<EmotionalReport> recent = dao.filter(f4);

        assertNotNull(recent);
        assertTrue(recent.size() >= 1);

        recent.forEach(r ->
                System.out.println("Recent: " + r.getDate() + " | " +
                        r.getEmotionalState().getEmotion().getName())
        );
    }
}
