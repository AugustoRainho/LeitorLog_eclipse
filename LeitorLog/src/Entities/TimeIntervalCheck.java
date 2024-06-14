package Entities;

import java.awt.Color;
import java.time.LocalTime;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class TimeIntervalCheck {


    public static boolean isIntervalMatch(String time, int intervalMinutes) {
        // Parse the time string into LocalTime
        LocalTime localTime = LocalTime.parse(time);

        // Calculate minutes from the start of the day
        int minutesOfDay = localTime.toSecondOfDay() / 60;

        // Check if the minutes since midnight is divisible by the interval
        return minutesOfDay % intervalMinutes == 0;
    }
    public static void highlightIncorrectTime(JTextArea textArea, String line) {
        // Get the current text from the JTextArea
        String text = textArea.getText();
        int start = text.indexOf(line);
        int end = start + line.length();

        // Create a custom highlighter (change color or background)
        Highlighter highlighter = textArea.getHighlighter();
        DefaultHighlighter.DefaultHighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);

        try {
            // Highlight the incorrect time in red
            highlighter.addHighlight(start, end, painter);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }
}

