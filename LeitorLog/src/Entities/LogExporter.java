package Entities;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LogExporter {

    // Method to save all filtered messages to a log file
    public static void saveFilteredMessagesToLogFile(List<String> filteredMessages) {
        saveMessagesToLogFile(filteredMessages, "Filtered_Messages.log");
    }

    // Method to save incorrect messages (outside 5-minute interval) to a log file
    public static void saveIncorrectMessagesToLogFile(List<String> incorrectMessages) {
        saveMessagesToLogFile(incorrectMessages, "Incorrect_Messages.log");
    }

    // Generic method to save messages to a log file
    private static void saveMessagesToLogFile(List<String> messages, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String message : messages) {
                writer.write(message);
                writer.newLine();
            }
            System.out.println("Log file " + fileName + " created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error creating log file " + fileName + ": " + e.getMessage());
        }
    }
}
