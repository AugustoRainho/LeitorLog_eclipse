package APP;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import Entities.MessageFilter;
import Entities.StateFieldParse;
import Entities.TimeIntervalCheck;

public class MessageFilterFrame extends JFrame {
    private JTextArea textArea;
    private JButton openButton;
    private JButton filterButton;
    private JButton checkButton;
    private JLabel infoLabel;
    private JLabel EneidaLabel;
    private JLabel ioLabel;
    private JComboBox<String> selectMess;
    private JComboBox<String> selectTim;
    private String SelectMessage = "Select an Option";
    private String SelectTime = "Select Time";

    public MessageFilterFrame() {
        // Set custom background color for JFileChooser
        UIManager.put("FileChooser.background", new Color(173, 216, 230)); // Light blue color
        UIManager.put("FileChooser.foreground", Color.BLACK);
        UIManager.put("FileChooser.titlePanel.background", new Color(100, 149, 237)); // Cornflower blue color
        UIManager.put("FileChooser.titlePanel.foreground", Color.BLUE);
        UIManager.put("Panel.background", new Color(173, 216, 230)); // Light blue color

        setTitle("Message Filter App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set background color of the frame
        getContentPane().setBackground(new Color(245, 245, 245)); // Light gray color

        // Create a panel to hold the labels
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(2, 1));
        labelPanel.setBackground(new Color(245, 245, 245)); // Match the frame background

        // Add a label with some text
        infoLabel = new JLabel("Sellect one Log File to check messages");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoLabel.setOpaque(true);
        infoLabel.setBackground(new Color(173, 216, 230)); // Light blue color

        // Create a panel to hold the newLabel and additionalLabel side by side
        JPanel subLabelPanel = new JPanel();
        subLabelPanel.setLayout(new FlowLayout());
        subLabelPanel.setBackground(new Color(245, 245, 245)); // Match the frame background

        // Add the new label
        EneidaLabel = new JLabel("Eneida");
        EneidaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        EneidaLabel.setFont(new Font("Arial", Font.BOLD, 30));
        EneidaLabel.setOpaque(false);
        EneidaLabel.setBackground(new Color(173, 216, 230)); // Light blue color

        // Add the additional label
        ioLabel = new JLabel(".io");
        ioLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ioLabel.setFont(new Font("Arial", Font.BOLD, 30));
        ioLabel.setForeground(new Color(245, 220, 0));
        ioLabel.setOpaque(false);
        ioLabel.setBackground(Color.YELLOW); // Yellow color

        // Add both labels to the sub-panel
        subLabelPanel.add(EneidaLabel);
        subLabelPanel.add(ioLabel);

        // Add the main label and sub-label panel to the main label panel
        labelPanel.add(infoLabel);
        labelPanel.add(subLabelPanel);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        openButton = new JButton("1. Open File");
        openButton.addActionListener(new OpenButtonListener());

        filterButton = new JButton("2. Filter Message");
        filterButton.addActionListener(new FilterButtonListener());
        filterButton.setEnabled(false); // Disable filter button initially
        
       //JComboBox para selecionar mensagens e tempo
        String[] selectData = {SelectMessage, "State Field", "Raw Data", "State Alarm"};
        String[] selectComm = {SelectTime, "1 min", "3 min", "5 min", "10 min", "15 min", "30 min", "60 min", "120 min", "240 min"};      

        selectMess = new JComboBox<>(selectData);
        selectTim = new JComboBox<>(selectComm);
        // Optionally set a default selection
        selectMess.setSelectedIndex(0); // Selects the first item by default
        selectTim.setSelectedIndex(0); // Selects the first item by default
        // Add a popup menu listener to hide the initial prompt when showing the options
        
        selectMess.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                if (selectMess.getItemAt(0).equals(SelectMessage)) {
                	selectMess.removeItemAt(0);
                }
            }
            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                if (selectMess.getSelectedIndex() == -1 || selectMess.getSelectedItem().equals(SelectMessage)) {
                	selectMess.insertItemAt(SelectMessage, 0);
                	selectMess.setSelectedIndex(0);
                }
            }
            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                if (selectMess.getSelectedIndex() == -1 || selectMess.getSelectedItem().equals(SelectMessage)) {
                	selectMess.insertItemAt(SelectMessage, 0);
                	selectMess.setSelectedIndex(0);
                }
            }
        });
        selectTim.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                if (selectTim.getItemAt(0).equals(SelectTime)) {
                	selectTim.removeItemAt(0);
                }
            }
            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                if (selectTim.getSelectedIndex() == -1 || selectTim.getSelectedItem().equals(SelectTime)) {
                	selectTim.insertItemAt(SelectTime, 0);
                	selectTim.setSelectedIndex(0);
                }
            }
            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                if (selectTim.getSelectedIndex() == -1 || selectTim.getSelectedItem().equals(SelectTime)) {
                	selectTim.insertItemAt(SelectTime, 0);
                	selectTim.setSelectedIndex(0);
                }
            }
        });
        
        checkButton = new JButton("3. Check Interval Messages");
        checkButton.addActionListener(new CheckButtonListener());
        checkButton.setEnabled(false); // Disable filter button initially
        //Mostrar botoes
        JPanel panel = new JPanel();
        panel.setBackground(new Color(173, 216, 230)); // Set background color for panel
        panel.add(openButton);
        panel.add(selectMess);
        panel.add(selectTim);
        panel.add(filterButton);
        panel.add(checkButton);
        selectMess.setEnabled(false);
        selectTim.setEnabled(false);

        setLayout(new BorderLayout());
        add(labelPanel, BorderLayout.NORTH); // Add the label panel to the top
        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);      
        
        setVisible(true);
    }

    private class OpenButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Set custom background color and title for JFileChooser
            UIManager.put("FileChooser.background", new Color(230, 216, 230)); // Light blue color
            UIManager.put("FileChooser.foreground", Color.BLACK);
            UIManager.put("FileChooser.titlePanel.background", new Color(100, 149, 237)); // Cornflower blue color
            UIManager.put("FileChooser.titlePanel.foreground", Color.BLUE);
            UIManager.put("Panel.background", new Color(173, 216, 230)); // Light blue color
        	openButton.setEnabled(false);
            JFileChooser fileChooser = new JFileChooser() {
                @Override
                protected JDialog createDialog(Component parent) throws HeadlessException {
                    JDialog dialog = super.createDialog(parent);
                    dialog.setTitle("Custom File Chooser");
                    return dialog;
                }
            };
            
            int result = fileChooser.showOpenDialog(MessageFilterFrame.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                loadFile(selectedFile);
            }
            selectMess.setEnabled(true);
            selectTim.setEnabled(true);
            filterButton.setEnabled(true); // enable filter button initially
        }
    }

    private void loadFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            textArea.setText("");
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class FilterButtonListener implements ActionListener {
    	
        public void actionPerformed(ActionEvent e) {
            if (selectMess.getSelectedIndex() == 0) {
                // selectMess.removeItemAt(0);

                filterButton.setEnabled(false); // Disable filter button after click
                selectMess.setEnabled(true);
                selectTim.setEnabled(true);

                String[] messagesArray = textArea.getText().split("\n");
                List<String> messages = new ArrayList<>(Arrays.asList(messagesArray));
                Map<String, List<String>> filteredMessagesMap = MessageFilter.filterMessages(messages, Arrays.asList("statefield"));

                List<String> singleLineMessages = filteredMessagesMap.get("singleLineMessages");
                List<String> threeLineMessages = filteredMessagesMap.get("threeLineMessages");

                // Save filtered messages to MessageHolder or StateFieldParse
                StateFieldParse.setFilteredMessages(threeLineMessages); // Assuming you want to save three-line messages

                // Clear the text area and display filtered messages
                textArea.setText("");
                for (String message : threeLineMessages) {
                    textArea.append(message + "\n");

                    // Extract and print the date and time
                    String dateTime = StateFieldParse.extractDateTime(message);
                    if (dateTime != null) {
                        // Print only the first line
                        String firstLine = dateTime.split("\n")[0];
                        System.out.println(firstLine);
                    }
                 //   System.out.println(message);
	            }
	            selectMess.setEnabled(false);
	            selectTim.setEnabled(false);
	        	checkButton.setEnabled(true);
            }
            if (selectMess.getSelectedIndex() == 1) {
                // selectMess.removeItemAt(0);

                filterButton.setEnabled(false); // Disable filter button after click
                selectMess.setEnabled(true);
                selectTim.setEnabled(true);

                String[] messagesArray = textArea.getText().split("\n");
                List<String> messages = new ArrayList<>(Arrays.asList(messagesArray));
                Map<String, List<String>> filteredMessagesMap = MessageFilter.filterMessages(messages, Arrays.asList("fullrawdata"));

                List<String> singleLineMessages = filteredMessagesMap.get("singleLineMessages");
                List<String> threeLineMessages = filteredMessagesMap.get("threeLineMessages");

                // Save filtered messages to MessageHolder or StateFieldParse
                StateFieldParse.setFilteredMessages(threeLineMessages); // Assuming you want to save three-line messages

                // Clear the text area and display filtered messages
                textArea.setText("");
                for (String message : threeLineMessages) {
                    textArea.append(message + "\n");

                    // Extract and print the date and time
                    String dateTime = StateFieldParse.extractDateTime(message);
                    if (dateTime != null) {
                        // Print only the first line
                        String firstLine = dateTime.split("\n")[0];
                        System.out.println(firstLine);
                    }
                 //   System.out.println(message);
	            }
	            selectMess.setEnabled(false);
	            selectTim.setEnabled(false);
	        	checkButton.setEnabled(true);
            }
            if (selectMess.getSelectedIndex() == 2) {
                // selectMess.removeItemAt(0);

                filterButton.setEnabled(false); // Disable filter button after click
                selectMess.setEnabled(true);
                selectTim.setEnabled(true);

                String[] messagesArray = textArea.getText().split("\n");
                List<String> messages = new ArrayList<>(Arrays.asList(messagesArray));
                Map<String, List<String>> filteredMessagesMap = MessageFilter.filterMessages(messages, Arrays.asList("state/alarmstatus"));

                List<String> singleLineMessages = filteredMessagesMap.get("singleLineMessages");
                List<String> threeLineMessages = filteredMessagesMap.get("threeLineMessages");

                // Save filtered messages to MessageHolder or StateFieldParse
                StateFieldParse.setFilteredMessages(threeLineMessages); // Assuming you want to save three-line messages

                // Clear the text area and display filtered messages
                textArea.setText("");
                for (String message : threeLineMessages) {
                    textArea.append(message + "\n");

                    // Extract and print the date and time
                    String dateTime = StateFieldParse.extractDateTime(message);
                    if (dateTime != null) {
                        // Print only the first line
                        String firstLine = dateTime.split("\n")[0];
                        System.out.println(firstLine);
                    }
                 //   System.out.println(message);
	            }
	            selectMess.setEnabled(false);
	            selectTim.setEnabled(false);
	        	checkButton.setEnabled(true);
            }
        }
    }
    	
    private class CheckButtonListener implements ActionListener {

    	public void actionPerformed(ActionEvent e) {
            checkButton.setEnabled(false);

            String selectedInterval = (String) selectTim.getSelectedItem();
            if ("SelectTime".equals(selectedInterval)) {
                System.out.println("Please select a valid interval.");
                return;
            }
            int intervalMinutes = Integer.parseInt(selectedInterval.split(" ")[0]);
            List<String> filteredMessages = StateFieldParse.getFilteredMessages();
            if (filteredMessages != null && !filteredMessages.isEmpty()) {
                for (String message : filteredMessages) {
                    String dateTime = StateFieldParse.extractDateTime(message);

                    if (dateTime != null) {
                        String[] lines = dateTime.split("\n");
                        if (lines.length > 0) {
                            String firstLine = lines[0].trim(); // Get the first line and trim whitespace
                            if (firstLine.length() >= 19) {
                                String hour = firstLine.substring(11, 19); // Extract the HH:mm:ss part

                                if (TimeIntervalCheck.isIntervalMatch(hour, intervalMinutes)) {
                                    System.out.println("Time " + hour + " matches the " + intervalMinutes + "-minute interval.");
                                } else {
                                    System.out.println("Time " + hour + " does NOT match the " + intervalMinutes + "-minute interval.");
                                    TimeIntervalCheck.highlightIncorrectTime(textArea, firstLine);

                                }
                            } else {
                                System.out.println("Invalid date-time format in message: " + firstLine);
                            }
                        }
                    }
                }
            } else {
                System.out.println("No filtered messages to check.");
            }
        }
    }
    	


}
