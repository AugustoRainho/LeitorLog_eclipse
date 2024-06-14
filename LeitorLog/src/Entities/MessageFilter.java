package Entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageFilter {

    public static Map<String, List<String>> filterMessages(List<String> messages, List<String> keywords) {
        List<String> singleLineMessages = new ArrayList<>();
        List<String> threeLineMessages = new ArrayList<>();

        for (int i = 0; i < messages.size(); i++) {
            for (String keyword : keywords) {
                if (messages.get(i).toLowerCase().contains(keyword.toLowerCase())) {
                    singleLineMessages.add(messages.get(i));
                    StringBuilder threeLines = new StringBuilder(messages.get(i));
                    for (int j = 1; j <= 3; j++) {
                        if (i + j < messages.size()) {
                            threeLines.append("\n").append(messages.get(i + j));
                        } else {
                            break;
                        }
                    }
                    threeLines.append("\n").append("**************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************");
                    threeLineMessages.add(threeLines.toString());
                    break; // Exit the inner loop and continue with the next message
                }
            }
        }

        Map<String, List<String>> result = new HashMap<>();
        result.put("singleLineMessages", singleLineMessages);
        result.put("threeLineMessages", threeLineMessages);
        
        
		return result;

    
    }
}
