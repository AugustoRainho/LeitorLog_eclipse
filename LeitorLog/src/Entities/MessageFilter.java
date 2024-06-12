package Entities;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class MessageFilter {
    public static List<String> filterStatefieldMessages(List<String> messages) {
        List<String> filteredMessages = new ArrayList<>();
        Pattern pattern = Pattern.compile(Pattern.quote("statefield"), Pattern.CASE_INSENSITIVE);
        
        for (int i = 0; i < messages.size(); i++) {
            if (pattern.matcher(messages.get(i)).find()) {
                filteredMessages.add(messages.get(i));
                for (int j = 1; j <= 3; j++) {
                    if (i + j < messages.size()) {
                        filteredMessages.add(messages.get(i + j));
                    } else {
                        break;
                    }
                }
                
                filteredMessages.add("**************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************"); // Add separator

            }
        }
        return filteredMessages;
    }
}

