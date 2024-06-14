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

import javax.swing.JButton;
import javax.swing.JCheckBox;
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

import Entities.MessageFilter;

public class MessageFilterFrame extends JFrame {
    private JTextArea textArea;
    private JButton openButton;
    private JButton filterButton;
    private JButton checkButton;
    private JLabel infoLabel;
    private JLabel EneidaLabel;
    private JLabel ioLabel;
    private JComboBox<String> sellectMess;
    private JComboBox<String> sellectTime;



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
        String[] sellectData = {"Sellect Message", "State Field", "Raw Data", "State Alarm"};
        String[] sellectComm = {"Sellect Time", "1 min", "3 min", "5 min", "10 min", "15 min", "30 min", "60 min", "120 min", "240 min"};
        

        sellectMess = new JComboBox<>(sellectData);
        sellectTime = new JComboBox<>(sellectComm);
        
        
     
        // Optionally set a default selection
    
        sellectMess.setSelectedIndex(0); // Selects the first item by default
        sellectTime.setSelectedIndex(0); // Selects the first item by default


        
        checkButton = new JButton("3. Check StateField Messages");
        checkButton.addActionListener(new CheckButtonListener());
        checkButton.setEnabled(false); // Disable filter button initially

        
        //Mostrar botoes
        JPanel panel = new JPanel();
        panel.setBackground(new Color(173, 216, 230)); // Set background color for panel
        panel.add(openButton);
        panel.add(sellectMess);
        panel.add(sellectTime);
        panel.add(filterButton);
        panel.add(checkButton);
        sellectMess.setEnabled(false);
        sellectTime.setEnabled(false);

       
        

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
            sellectMess.setEnabled(true);
            sellectTime.setEnabled(true);
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
 
              
            filterButton.setEnabled(false); // Disable filter button after click
            sellectMess.setEnabled(true);
            sellectTime.setEnabled(true);

            String[] messagesArray = textArea.getText().split("\n");
            List<String> messages = new ArrayList<>(Arrays.asList(messagesArray));
            List<String> filteredMessages = MessageFilter.filterMessages(messages, Arrays.asList("statefield"));

            textArea.setText("");
            for (String message : filteredMessages) {
                textArea.append(message + "\n");
            }
            sellectMess.setEnabled(false);
            sellectTime.setEnabled(false);
        	checkButton.setEnabled(true);
        	

        }
 
    }
    	
    private class CheckButtonListener implements ActionListener {
        
    	public void actionPerformed(ActionEvent e) {
   
            checkButton.setEnabled(false);

            String[] options = {"Resp : (200)", "Comm_Interval", "Outra Opção", "Mais uma Opção"}; // Exemplo de opções
            List<String> selectedKeywords = new ArrayList<>();

            // Mostra uma caixa de diálogo para selecionar múltiplas opções
            JPanel panel = new JPanel(new GridLayout(options.length, 1));
            JCheckBox[] checkBoxes = new JCheckBox[options.length];
            for (int i = 0; i < options.length; i++) {
                checkBoxes[i] = new JCheckBox(options[i]);
                panel.add(checkBoxes[i]);
                //System.out.println("tou aqui");

            }

            // Exibe a caixa de diálogo
            int result = JOptionPane.showConfirmDialog(null, panel, "Filtrar StateField por:", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                for (JCheckBox checkBox : checkBoxes) {
                    if (checkBox.isSelected()) {
                        selectedKeywords.add(checkBox.getText());
                        //System.out.println("tou aqui");

                    }
                }

                if (!selectedKeywords.isEmpty()) {
                    // Aqui você pode usar as palavras-chave selecionadas para alguma ação
                    System.out.println("Palavras-chave selecionadas: " + selectedKeywords);
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhuma palavra-chave selecionada.", "Informação", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    	  	
    }
}
