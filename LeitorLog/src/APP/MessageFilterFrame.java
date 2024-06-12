package APP;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Entities.MessageFilter;

public class MessageFilterFrame extends JFrame {
    private JTextArea textArea;
    private JButton openButton;
    private JButton filterButton;
    private JButton checkButton;

    public MessageFilterFrame() {
        setTitle("Message Filter App");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        openButton = new JButton("1. Open File");
        openButton.addActionListener(new OpenButtonListener());
        
        filterButton = new JButton("2. Filter StateField Messages");
        filterButton.addActionListener(new FilterButtonListener());
        
        checkButton = new JButton("3. Check StateField Communication");
        checkButton.addActionListener(new CheckButtonListener());

        JPanel panel = new JPanel();
        panel.add(openButton);
        panel.add(filterButton);
        panel.add(checkButton);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private class OpenButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	openButton.setEnabled(false);
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(MessageFilterFrame.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                loadFile(selectedFile);
            }
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
        	filterButton.setEnabled(false);
            String[] messages = textArea.getText().split("\n");
            List<String> filteredMessages = MessageFilter.filterStatefieldMessages(List.of(messages));

            textArea.setText("");
            for (String message : filteredMessages) {
                textArea.append(message + "\n");
            }
        }
        
    }
    private class CheckButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	checkButton.setEnabled(false);
   
        	
        	
        	
        	
        }
    }
}
