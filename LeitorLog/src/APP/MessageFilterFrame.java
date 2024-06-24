package APP;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import Entities.LogExporter;
import Entities.MessageFilter;
import Entities.StateFieldParse;

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
   
    private List<String> filteredMessagesLog;
    
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
        infoLabel = new JLabel("Select one Log File to check messages");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoLabel.setOpaque(true);
        infoLabel.setBackground(new Color(173, 216, 230)); // Light blue color

        // Create a panel to hold the newLabel and additionalLabel side by side
        JPanel subLabelPanel = new JPanel();
        subLabelPanel.setLayout(new FlowLayout());

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

        // Initialize JComboBox components before adding ActionListener
        String[] selectData = {SelectMessage, "State Field"};
        String[] selectComm = {SelectTime, "5 min"};

        selectMess = new JComboBox<>(selectData);
        selectTim = new JComboBox<>(selectComm);

        checkButton = new JButton("3. Check Interval Messages");
        checkButton.addActionListener(new CheckButtonListener(checkButton, textArea));
        checkButton.setEnabled(false); // Disable check button initially

        filterButton.addActionListener(new FilterButtonListener(textArea, selectMess, selectTim, checkButton));
        filterButton.setEnabled(false); // Disable filter button initially

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

        // Add buttons and combo boxes to the panel
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

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose a log file");

            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                readFile(selectedFile);
                filterButton.setEnabled(true); // Enable filter button after opening a file
                selectMess.setEnabled(true); // Enable the selectMess combo box after filtering messages
                selectTim.setEnabled(true); // Enable the selectTim combo box after filtering messages
                checkButton.setEnabled(false); // Enable check button after filtering messages
            }
        }

        private void readFile(File file) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                textArea.read(br, null);
                textArea.requestFocus();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private class FilterButtonListener implements ActionListener {
        private JTextArea textArea;
        private JComboBox<String> selectMess;
        private JComboBox<String> selectTim;
        private JButton checkButton;

        public FilterButtonListener(JTextArea textArea, JComboBox<String> selectMess, JComboBox<String> selectTim, JButton checkButton) {
            this.textArea = textArea;
            this.selectMess = selectMess;
            this.selectTim = selectTim;
            this.checkButton = checkButton;
        }

        public void actionPerformed(ActionEvent e) {
            String text = textArea.getText();
            String[] lines = text.split("\\n");
            List<String> messages = new ArrayList<>();
            for (String line : lines) {
                messages.add(line);
            }

            List<String> filteredMessages = MessageFilter.filterMessages(messages);
            
        

            filteredMessagesLog = MessageFilter.filterMessages(messages);
            
            

            textArea.setText(""); // Clear the text area before displaying filtered messages
            for (String message : filteredMessages) {
                textArea.append(message + "\n");
            }
            filterButton.setEnabled(false);
            selectMess.setEnabled(false); // Enable the selectMess combo box after filtering messages
            selectTim.setEnabled(false); // Enable the selectTim combo box after filtering messages
            checkButton.setEnabled(true); // Enable check button after filtering messages
        }
    }

    
    /*private class FilterButtonListener implements ActionListener {
    	
        public void actionPerformed(ActionEvent e) {
            if (selectMess.getSelectedIndex() == 0) {
                // selectMess.removeItemAt(0);

                filterButton.setEnabled(false); // Disable filter button after click
                selectMess.setEnabled(true);
                selectTim.setEnabled(true);

                String[] messagesArray = textArea.getText().split("\n");
                List<String> messages = new ArrayList<>(Arrays.asList(messagesArray));  
                List<String> filteredMessages = MessageFilter.filterMessages(messages, Arrays.asList("state", "field", "{\"config_ts\":"));

                //List<String> keywords = Arrays.asList("state/field", "{\"config_ts\":");
                //List<String> keywords = Arrays.asList("statefield", "{\"general\":{\"value\":{\"isCommissioned\"");
                Map<String, List<String>> filteredMessagesMap = MessageFilter.filterMessages(messages, filteredMessages);
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
	            }
	            selectMess.setEnabled(false);
	            selectTim.setEnabled(false);
	        	checkButton.setEnabled(true);
            }
            // ATIVAR PARA ADICIONAR RAWDATA E STATE ALARM
          /*  if (selectMess.getSelectedIndex() == 1) {
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
    } */

    public class CheckButtonListener implements ActionListener {
        private JButton checkButton;
        private JTextArea textPane;

        public CheckButtonListener(JButton checkButton, JTextArea textArea) {
            this.checkButton = checkButton;
            this.textPane = textArea;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            checkButton.setEnabled(false);
            StringBuilder incorrectTimes = new StringBuilder();
            List<String> filteredMessages = StateFieldParse.getFilteredMessages();

            if (filteredMessages == null || filteredMessages.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No filtered messages to check.", "Info", JOptionPane.INFORMATION_MESSAGE);
                checkButton.setEnabled(true);
                return;
            }

            // Clear text pane and prepare to append messages
            textPane.setText("");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime previousTime = null;
            boolean firstMessage = true;

            for (String message : filteredMessages) {
                String timeString = StateFieldParse.extractTime(message);

                if (timeString != null) {
                    LocalTime messageTime = LocalTime.parse(timeString, timeFormatter);

                    if (!firstMessage) {
                        long minutesDifference = ChronoUnit.MINUTES.between(previousTime, messageTime);

                        if (minutesDifference > 5) {
                            appendColoredText(textPane, message, Color.RED);
                            incorrectTimes.append(message).append("\n");
                        } else {
                            appendColoredText(textPane, message, Color.BLACK);
                        }
                    } else {
                        appendColoredText(textPane, message, Color.BLACK);
                        firstMessage = false;
                    }
                    previousTime = messageTime;
                } else {
                    textPane.append(message + "\n");
                }
            }

            // Save all filtered messages to a log file
      

            List<String> filteredMessagesLOG = MessageFilter.filterMessages(filteredMessagesLog);
            
        
            LogExporter.saveFilteredMessagesToLogFile(filteredMessagesLOG);

            // Save incorrect messages to a log file
            if (incorrectTimes.length() > 0) {
                List<String> incorrectMessagesList = new ArrayList<>();
                for (String line : incorrectTimes.toString().split("\n")) {
                    incorrectMessagesList.add(line);
                }
                LogExporter.saveIncorrectMessagesToLogFile(incorrectMessagesList);

                // Show a popup with messages outside the 5-minute interval, if any
                JTextArea resultTextArea = new JTextArea("Messages outside the 5-minute interval:\n\n" + incorrectTimes.toString());
                resultTextArea.setEditable(false);
                resultTextArea.setLineWrap(true);
                resultTextArea.setWrapStyleWord(true);

                JScrollPane scrollPane = new JScrollPane(resultTextArea);
                scrollPane.setPreferredSize(new Dimension(400, 200));

                Object[] options = {"Close", "Copy Messages to Log File"};
                int result = JOptionPane.showOptionDialog(null, scrollPane, "Log File Creation Result", JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);

                if (result == 1) {
                    JOptionPane.showMessageDialog(null, "Messages copied to log file 'Incorrect_Messages.log'.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "All messages are within the 5-minute interval.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }

            checkButton.setEnabled(true);
        }

        private void appendColoredText(JTextArea textArea, String text, Color color) {
            Document doc = textArea.getDocument();
            try {
                if (doc instanceof PlainDocument) {
                    textArea.getDocument().insertString(doc.getLength(), text + "\n", null);
                }
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
            textArea.append(text + "\n");
            textArea.setForeground(color);
        }
    }
    
    
    
    
    
    /*
    public class CheckButtonListener implements ActionListener {
        private JButton checkButton;
        private JTextArea textPane;

        public CheckButtonListener(JButton checkButton, JTextArea textArea) {
            this.checkButton = checkButton;
            this.textPane = textArea;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            checkButton.setEnabled(false);
            StringBuilder incorrectTimes = new StringBuilder();

            List<String> filteredMessages = StateFieldParse.getFilteredMessages();
            if (filteredMessages == null || filteredMessages.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No filtered messages to check.", "Info", JOptionPane.INFORMATION_MESSAGE);
                checkButton.setEnabled(true);
                return;
            }

            // Clear text pane and prepare to append messages
            textPane.setText("");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            LocalTime previousTime = null;
            boolean firstMessage = true;

            for (String message : filteredMessages) {
                // Check if the message contains a URL
                boolean containsURL = message.contains("statefield") || message.contains("state/field");

                if (containsURL) {
                    // Append separator before URL messages
                    appendSeparator(textPane);
                
                }
                String timeString = StateFieldParse.extractTime(message);
                if (timeString != null) {
                    LocalTime messageTime = LocalTime.parse(timeString, timeFormatter);

                    if (!firstMessage) {
                        long minutesDifference = ChronoUnit.MINUTES.between(previousTime, messageTime);
                        
                        int minutos = messageTime.getMinute();
                        if (minutos == 0 || minutos == 5 && minutesDifference == 5) {
                            System.out.println("Os minutos são 0 ou 5.");
                            appendColoredText(textPane, message, Color.BLACK);
                        } else {
                            System.out.println("Os minutos não são 0 ou 5.");
                            appendColoredText(textPane, message, Color.RED);
                            
                            incorrectTimes.append(messageTime).append("\n");
                            
                            
                        }
                        
                        
                        
                        
                  /*      if (minutesDifference > 5 ||  minutesDifference < 5) {
                        //if (minutesDifference % 5 != 0) {

                            appendColoredText(textPane, message, Color.RED);
                            incorrectTimes.append(timeString).append("\n");
                           // System.out.println(minutesDifference);
                           // System.out.println(timeString);

                            
                        } else{
                            appendColoredText(textPane, message, Color.BLACK);
                        }
                    } else {
                        appendColoredText(textPane, message, Color.BLACK);
                        firstMessage = false;
                    }

                    // Update previousTime to current message's time
                    previousTime = messageTime;
                } else {
                    textPane.append(message + "\n"); // If time format is invalid, still append the whole message
                }
            }

            // Show a popup with times outside the 5 minutes interval
            JTextArea resultTextArea = new JTextArea("Horas fora do intervalo de 5 minutos:\n\n" + incorrectTimes.toString());
            resultTextArea.setEditable(false);
            resultTextArea.setLineWrap(true);
            resultTextArea.setWrapStyleWord(true);

            JScrollPane scrollPane = new JScrollPane(resultTextArea);
            scrollPane.setPreferredSize(new Dimension(400, 200));

            // Custom buttons for JOptionPane
            Object[] options = {"Fechar", "Copiar Horas para Arquivo de Log"};
            int result = JOptionPane.showOptionDialog(null, scrollPane, "Log File Creation Result", JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
    
            
            
        
            if (result == 1) {
                // Handle "Copiar Horas para Arquivo de Log" action here

                // Debug print to check contents of filteredMessagesLog
                System.out.println("Filtered Messages for Log: " + filteredMessagesLog);

                LogExporter.saveMessagesToLogFile(filteredMessagesLog, true); // Include separator in log file
            }
            checkButton.setEnabled(true);
        }

        private void appendColoredText(JTextArea textArea, String text, Color color) {
            Document doc = textArea.getDocument();
            try {
                if (doc instanceof PlainDocument) {
                    textArea.getDocument().insertString(doc.getLength(), text + "\n", null);
                }
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
            textArea.append(text + "\n");
            textArea.setForeground(color);
        }

        private void appendSeparator(JTextArea textArea) {
            textArea.append("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        }
    }*/

 

    
    
    
    
    
    /*
    private class CheckButtonListener implements ActionListener {
        @Override

    	public void actionPerformed(ActionEvent e) {
            checkButton.setEnabled(false);
            StringBuilder hours = new StringBuilder();

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

                                    hours.append(dateTime).append("\n");
                                }

                            } else {
                                System.out.println("Invalid date-time format in message: " + firstLine);

                            }

                        }
                    }
                }                
                String allHours = hours.toString();
                JTextArea textArea = new JTextArea("Mensagem fora do tempo às:\n" + allHours);
                textArea.setEditable(false);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);

                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new java.awt.Dimension(400, 200));

             // Custom buttons
                Object[] options = {"Close", "Copy Messages to Log File"};
                int result = JOptionPane.showOptionDialog(null, scrollPane, "Resultado:", JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                if (result == 1) {
                    // Handle "Botao X" action here
                    System.out.println("Botao X pressed");
                    LogExporter.saveMessagesToLogFile(filteredMessages);
                }                
            } else {
                System.out.println("No filtered messages to check.");
            }
        }
    }
    	
*/

}
