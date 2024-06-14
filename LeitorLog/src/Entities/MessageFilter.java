package Entities;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class MessageFilter {
	
	public static List<String> filterMessages(List<String> messages, List<String> keywords) {
        List<String> filteredMessages = new ArrayList<>();
        
        for (int i = 0; i < messages.size(); i++) {
            for (String keyword : keywords) {
                if (messages.get(i).toLowerCase().contains(keyword.toLowerCase())) {
                    filteredMessages.add(messages.get(i));
                    for (int j = 1; j <= 3; j++) {
                        if (i + j < messages.size()) {
                            filteredMessages.add(messages.get(i + j));
                        } else {
                            break;
                        }
                    }
                    filteredMessages.add("**************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************"); // Add separator
                    break; // Exit the inner loop and continue with the next message
                }
            }
        }

        // Remove the last separator if present
        if (!filteredMessages.isEmpty() && "**********".equals(filteredMessages.get(filteredMessages.size() - 1))) {
            filteredMessages.remove(filteredMessages.size() - 1);
        }
        return filteredMessages;
    }
}

