
package Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StateFieldParse {

    private static List<String> filteredMessages = new ArrayList<>();

    public StateFieldParse() {
        // Constructor
    }

    public static String extractTime(String message) {
        // Regular expression pattern to match time formats
        String regex1 = "(\\d{2}:\\d{2}:\\d{2})"; // Matches HH:mm:ss

        // Compile the pattern
        Pattern pattern1 = Pattern.compile(regex1);
        Matcher matcher1 = pattern1.matcher(message);

        // Find and return the first match
        if (matcher1.find()) {
            return matcher1.group(1); // Return the first capturing group (HH:mm:ss)
        }

        return null; // Return null if no match found
    }

    public static void setFilteredMessages(List<String> messages) {
        filteredMessages = new ArrayList<>(messages);
    }

    public static List<String> getFilteredMessages() {
        List<String> times = new ArrayList<>();
        for (String message : filteredMessages) {
            String time = extractTime(message);
            if (time != null) {
                times.add(time);
            }
        }
        return times;
    }
}































/*package Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StateFieldParse {

    private static List<String> filteredMessages = new ArrayList<>();

    public StateFieldParse() {
        // Construtor vazio
    }

    // Extrai a hora de diferentes formatos de mensagens
    public static String extractTime(String message) {
        String regex1 = "\\d{4}-\\d{2}-\\d{2} (\\d{2}:\\d{2}:\\d{2})"; // Formato 1
        String regex2 = "(\\d{2}:\\d{2}:\\d{2}):\\d{3} ->"; // Formato 2
        String regex3 = "(\\d{2}:\\d{2}:\\d{2})\\.\\d{3},"; // Formato 3

        Pattern pattern1 = Pattern.compile(regex1);
        Matcher matcher1 = pattern1.matcher(message);
        if (matcher1.find()) {
            return matcher1.group(1);
        }

        Pattern pattern2 = Pattern.compile(regex2);
        Matcher matcher2 = pattern2.matcher(message);
        if (matcher2.find()) {
            return matcher2.group(1);
        }

        Pattern pattern3 = Pattern.compile(regex3);
        Matcher matcher3 = pattern3.matcher(message);
        if (matcher3.find()) {
            return matcher3.group(1);
        }

        return null;
    }

    public static void setFilteredMessages(List<String> messages) {
        filteredMessages = new ArrayList<>(messages);
    }

    public static List<String> getFilteredMessages() {
        List<String> times = new ArrayList<>();
        for (String message : filteredMessages) {
            String time = extractTime(message);
            if (time != null) {
                times.add(time);
            }
        }
        return times;
    }
}*/
