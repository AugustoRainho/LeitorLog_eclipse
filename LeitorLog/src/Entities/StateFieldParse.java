package Entities;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StateFieldParse {
	DateTimeFormatter SFHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

	public StateFieldParse() {
		 
	}
	
    public static String extractDateTime(String message) {
        // Define the regular expression pattern to match the date and time
        String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);

        // Find and return the first match
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
    
    private static List<String> filteredMessages = new ArrayList<>();

    public static void setFilteredMessages(List<String> messages) {
        filteredMessages = new ArrayList<>(messages);
        
        String dateTime = extractDateTime(filteredMessages.toString());
        
       // System.out.println(dateTime);  // example output: 2024-05-16 13:30:11

    }

    public static List<String> getFilteredMessages() {
        return new ArrayList<>(filteredMessages);
    }

    

}