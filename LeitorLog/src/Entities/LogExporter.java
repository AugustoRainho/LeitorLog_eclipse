package Entities;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;



public class LogExporter {

    public static void saveMessagesToLogFile(List<String> messages, boolean includeSeparator) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("StateField_With_Errors.log"))) {
            for (String message : messages) {
                if (includeSeparator) {
                    writer.write("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    writer.newLine();
                }
                writer.write(message);
                writer.newLine();
            }
            JOptionPane.showMessageDialog(null, "Log file created successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error creating log file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void saveMessagesToLogFile(List<String> filteredMessagesLog) {
        // Method stub (not used in this context)
    }
}