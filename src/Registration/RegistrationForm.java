/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ADLAN
 */
 package Registration;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationForm extends JFrame {

    private JTextField txtRegistrationID;
    private JTextField txtStudentName;
    private JTextField txtFaculty;
    private JTextField txtProjectTitle;
    private JTextField txtContactNumber;
    private JTextField txtEmailAddress;
    private JTextField txtImagePath;
    private final JLabel lblImageDisplay;
    private final JButton btnBrowse;
    private String selectedImagePath;

    public RegistrationForm() {
        setTitle("VU Exhibition Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(8, 2, 5, 5)); //adding rows and columns
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); //adding padding

        // Labels and Text Fields
        formPanel.add(new JLabel("Registration ID:"));
        txtRegistrationID = new JTextField();
        txtRegistrationID.setEditable(false); 
        formPanel.add(txtRegistrationID);

        formPanel.add(new JLabel("Student Name:"));
        txtStudentName = new JTextField();
        formPanel.add(txtStudentName);

        formPanel.add(new JLabel("Faculty:"));
        txtFaculty = new JTextField();
        formPanel.add(txtFaculty);

        formPanel.add(new JLabel("Project Title:"));
        txtProjectTitle = new JTextField();
        formPanel.add(txtProjectTitle);

        formPanel.add(new JLabel("Contact Number:"));
        txtContactNumber = new JTextField();
        formPanel.add(txtContactNumber);

        formPanel.add(new JLabel("Email Address:"));
        txtEmailAddress = new JTextField();
        formPanel.add(txtEmailAddress);

        formPanel.add(new JLabel("Project Image:"));
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
        txtImagePath = new JTextField(20);
        txtImagePath.setEditable(false);
        imagePanel.add(txtImagePath);

        btnBrowse = new JButton("Browse");
        imagePanel.add(btnBrowse);
        formPanel.add(imagePanel);

        // Image Display Label
        lblImageDisplay = new JLabel();
        lblImageDisplay.setHorizontalAlignment(SwingConstants.CENTER); 
        lblImageDisplay.setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
        add(lblImageDisplay, BorderLayout.CENTER); 

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnRegister = new JButton("Register");
        JButton btnSearch = new JButton("Search");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear");
        JButton btnExit = new JButton("Exit");

        buttonPanel.add(btnRegister);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnExit);

        // Add panels to the frame
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        btnBrowse.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif");
            fileChooser.setFileFilter(filter);
            int returnVal = fileChooser.showOpenDialog(RegistrationForm.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedImagePath = selectedFile.getAbsolutePath();
                txtImagePath.setText(selectedImagePath);
                displayImage(selectedImagePath);
            }
        });

        btnRegister.addActionListener((ActionEvent e) -> {
            if (validateInput()) {
                Participant participant = new Participant();
                participant.setStudentName(txtStudentName.getText());
                participant.setFaculty(txtFaculty.getText());
                participant.setProjectTitle(txtProjectTitle.getText());
                participant.setContactNumber(txtContactNumber.getText());
                participant.setEmailAddress(txtEmailAddress.getText());
                participant.setProjectImagePath(txtImagePath.getText());
                
                DatabaseManager.insertParticipant(participant);
                clearForm();
            }
        });

        btnSearch.addActionListener((ActionEvent e) -> {
            try {
                int registrationID = Integer.parseInt(JOptionPane.showInputDialog(RegistrationForm.this, "Enter Registration ID to search:"));
                Participant participant = DatabaseManager.searchParticipant(registrationID);
                
                if (participant != null) {
                    txtRegistrationID.setText(String.valueOf(participant.getRegistrationID()));
                    txtStudentName.setText(participant.getStudentName());
                    txtFaculty.setText(participant.getFaculty());
                    txtProjectTitle.setText(participant.getProjectTitle());
                    txtContactNumber.setText(participant.getContactNumber());
                    txtEmailAddress.setText(participant.getEmailAddress());
                    txtImagePath.setText(participant.getProjectImagePath());
                    displayImage(participant.getProjectImagePath());
                } else {
                    JOptionPane.showMessageDialog(RegistrationForm.this, "Participant not found.");
                    clearForm();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(RegistrationForm.this, "Invalid Registration ID. Please enter a number.");
            }
        });

        btnUpdate.addActionListener((ActionEvent e) -> {
            if (validateInput()) {
                try {
                    int registrationID = Integer.parseInt(txtRegistrationID.getText());
                    Participant participant = new Participant();
                    participant.setRegistrationID(registrationID);
                    participant.setStudentName(txtStudentName.getText());
                    participant.setFaculty(txtFaculty.getText());
                    participant.setProjectTitle(txtProjectTitle.getText());
                    participant.setContactNumber(txtContactNumber.getText());
                    participant.setEmailAddress(txtEmailAddress.getText());
                    participant.setProjectImagePath(txtImagePath.getText());
                    
                    DatabaseManager.updateParticipant(participant);
                    clearForm();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(RegistrationForm.this, "Invalid Registration ID. Please search for a participant first.");
                }
            }
        });

        btnDelete.addActionListener((ActionEvent e) -> {
            try {
                int registrationID = Integer.parseInt(txtRegistrationID.getText());
                int choice = JOptionPane.showConfirmDialog(RegistrationForm.this, "Are you sure you want to delete this participant?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    DatabaseManager.deleteParticipant(registrationID);
                    clearForm();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(RegistrationForm.this, "Invalid Registration ID. Please search for a participant first.");
            }
        });

        btnClear.addActionListener((ActionEvent e) -> {
            clearForm();
        });

        btnExit.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        // Make the form visible and centered
        setLocationRelativeTo(null); 
        setVisible(true);
    }

    private void displayImage(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    Image image = ImageIO.read(imageFile);
                    Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    ImageIcon imageIcon = new ImageIcon(scaledImage);
                    lblImageDisplay.setIcon(imageIcon);
                } else {
                    lblImageDisplay.setIcon(null);
                    JOptionPane.showMessageDialog(RegistrationForm.this, "Image file not found: " + imagePath, "Error", JOptionPane.ERROR_MESSAGE);
                    txtImagePath.setText(""); 
                }
            } catch (IOException e) {
                System.err.println("Error displaying image: " + e.getMessage());
                JOptionPane.showMessageDialog(RegistrationForm.this, "Error displaying image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                lblImageDisplay.setIcon(null); 
                txtImagePath.setText(""); 
            }
        } else {
            lblImageDisplay.setIcon(null); 
        }
    }

    private boolean validateInput() {
        if (txtStudentName.getText().isEmpty() || txtFaculty.getText().isEmpty() || txtProjectTitle.getText().isEmpty() || txtContactNumber.getText().isEmpty() || txtEmailAddress.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate email format
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(txtEmailAddress.getText());
        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void clearForm() {
        txtRegistrationID.setText("");
        txtStudentName.setText("");
        txtFaculty.setText("");
        txtProjectTitle.setText("");
        txtContactNumber.setText("");
        txtEmailAddress.setText("");
        txtImagePath.setText("");
        lblImageDisplay.setIcon(null);
        selectedImagePath = null;
    }
}

