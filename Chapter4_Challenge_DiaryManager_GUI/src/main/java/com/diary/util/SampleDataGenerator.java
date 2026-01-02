package com.diary.util;

import com.diary.model.DiaryEntry;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SampleDataGenerator {

    public static void generateSampleData(FileManager fileManager) {
        if (!fileManager.loadAllEntries().isEmpty()) {
            return;
        }

        try {
            LocalDateTime today = LocalDateTime.now();

            // Entry 1: Morning Calm (8:00 AM)
            DiaryEntry entry1 = new DiaryEntry("Morning Reflections", 
                "<p>Started the day with a warm cup of coffee on the porch. The air was crisp, and the birds were singing. " +
                "It felt incredibly <b>calm</b> and grounding. I took a few deep breaths and set my intentions for the day.</p>" +
                "<p><i>\"Peace comes from within. Do not seek it without.\"</i></p>");
            entry1.setCreatedDate(today.with(LocalTime.of(8, 0)));
            fileManager.saveEntry(entry1);

            // Entry 2: Productive Afternoon (2:00 PM)
            DiaryEntry entry2 = new DiaryEntry("A Productive Afternoon", 
                "<p>Finally tackled that big project I've been putting off! It feels so good to be <b>accomplished</b>. " +
                "I broke it down into smaller tasks and powered through them one by one.</p>" +
                "<ul><li>Completed the draft</li><li>Sent emails</li><li>Organized files</li></ul>" +
                "<p>Time for a well-deserved break.</p>");
            entry2.setCreatedDate(today.with(LocalTime.of(14, 30)));
            fileManager.saveEntry(entry2);

            // Entry 3: Gratitude Journal (8:00 PM)
            DiaryEntry entry3 = new DiaryEntry("Gratitude Journal", 
                "<p><b>Three things I am grateful for today:</b></p>" +
                "<ol>" +
                "<li>The support of my friends.</li>" +
                "<li>A delicious home-cooked meal.</li>" +
                "<li>The beautiful sunset I caught on my walk.</li>" +
                "</ol>" +
                "<p>❤️ Feeling blessed.</p>");
            entry3.setCreatedDate(today.with(LocalTime.of(20, 0)));
            fileManager.saveEntry(entry3);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
