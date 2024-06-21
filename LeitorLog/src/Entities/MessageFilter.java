package Entities;

import java.util.ArrayList;
import java.util.List;

public class MessageFilter {

    public static List<String> filterMessages(List<String> messages) {
        List<String> filteredMessages = new ArrayList<>();

        List<String> keywords = List.of("statefield", "state/field", "config_ts", "{\"general\":{\"value\":{\"isCommissioned\"");

        for (String message : messages) {
            boolean containsAnyKeyword = false;
            for (String keyword : keywords) {
                if (message.toLowerCase().contains(keyword.toLowerCase())) {
                    containsAnyKeyword = true;
                    break;
                }
            }
            if (containsAnyKeyword) {
                filteredMessages.add(message);
            }
        }

        // Enviar as mensagens filtradas para StateFieldParse
        StateFieldParse.setFilteredMessages(filteredMessages);

        return filteredMessages;
    }
}










/*package Entities;

import java.util.ArrayList;
import java.util.List;

public class MessageFilter {

    public static List<String> filterMessages(List<String> messages) {
        List<String> filteredMessages = new ArrayList<>();

        List<String> keywords = List.of("statefield", "state/field", "config_ts", "{\"general\":{\"value\":{\"isCommissioned\"");

        for (String message : messages) {
            boolean containsAnyKeyword = false;
            for (String keyword : keywords) {
                if (message.toLowerCase().contains(keyword.toLowerCase())) {
                    containsAnyKeyword = true;
                    break;
                }
            }
            if (containsAnyKeyword) {
                if ((message.toLowerCase().contains("state/field") || message.toLowerCase().contains("statefield") ) && message.toLowerCase().contains("url")) {
                    filteredMessages.add("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                }
                filteredMessages.add(message);
            }
        }

        return filteredMessages;
    }
    

}*/