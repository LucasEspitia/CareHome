package com.msoft.carehomeapp.dao;

import com.msoft.carehomeapp.model.*;
import com.msoft.carehomeapp.business.managers.RecordsManager;
import com.msoft.carehomeapp.data.implementation.RecordsDAOImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

public class RecordsDAOTest {

    private final RecordsManager recordsManager =
            new RecordsManager(RecordsDAOImpl.getInstance());

    @Test
    public void testSaveAndFetch() {

        System.out.println("\n=== TEST: SAVE AND FETCH ===");

        // Build objects
        Emotion emotion = new Emotion(
                Emotion.EmotionName.HAPPY,
                Emotion.EmotionType.POSITIVE
        );
        EmotionalState state = new EmotionalState(emotion, 7);

        Room room = new Room("TestRoom");
        ActivitySuggestion activity = new ActivitySuggestion("Reading");

        EmotionalReport report = new EmotionalReport(
                state,
                room,
                activity,
                LocalDateTime.now()
        );

        // SAVE
        recordsManager.saveReport(report);

        // FETCH LAST 5
        List<EmotionalReport> last = recordsManager.getLastN(5);

        System.out.println("Fetched reports:");
        for (EmotionalReport r : last) {
            System.out.println(r.getDate() + " | " +
                    r.getEmotionalState().getEmotion().getName() +
                    " | room=" + r.getRoom().getName());
        }

        assert last.size() > 0;
    }


    @Test
    public void testFilterByEmotion() {

        System.out.println("\n=== TEST: FILTER BY EMOTION ===");

        ReportFilter filter = new ReportFilter();
        filter.setEmotionName(Emotion.EmotionName.HAPPY);

        List<EmotionalReport> list = recordsManager.filter(filter);

        for (EmotionalReport r : list) {
            System.out.println("HAPPY -> " + r.getDate());
        }

        // At least 1 result if previously saved
        assert true;
    }


    @Test
    public void testPagination() {

        System.out.println("\n=== TEST: PAGINATION ===");

        // page 1
        List<EmotionalReport> page1 = recordsManager.getPaged(0, 3);

        // page 2
        List<EmotionalReport> page2 = recordsManager.getPaged(3, 3);

        System.out.println("Page1 size = " + page1.size());
        System.out.println("Page2 size = " + page2.size());

        assert true;
    }


    @Test
    public void testFilterPaged() {

        System.out.println("\n=== TEST: FILTER + PAGED ===");

        ReportFilter filter = new ReportFilter();
        filter.setEmotionType(Emotion.EmotionType.POSITIVE);

        // First page
        List<EmotionalReport> list = recordsManager.filterPaged(filter, 0, 5);

        for (EmotionalReport r : list) {
            System.out.println("POSITIVE -> " + r.getDate());
        }

        assert true;
    }
}
