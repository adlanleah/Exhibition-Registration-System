/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ADLAN
 */
package Registration;

import java.sql.*;
import javax.swing.JOptionPane;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:ucanaccess://C:\\Users\\NOLAN\\Documents\\NetBeansProjects\\Registration\\VUE_Exhibition.accdb";

    // No need for the static block anymore, we'll load the driver explicitly in MainClass

    // Test database connection
    public static boolean testConnection() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            return conn != null;
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }

    // Initialize database and create table if it not exists
    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            // Checking whether the table exists first
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "participants", null);

            if (!tables.next()) {
                // Table doesn't exist, create it
                String createTableSQL =
                    "CREATE TABLE participants (" +
                    "registration_id COUNTER PRIMARY KEY, " + 
                    "student_name TEXT(100) NOT NULL, " +
                    "faculty TEXT(100) NOT NULL, " +
                    "project_title TEXT(200) NOT NULL, " +
                    "contact_number TEXT(15) NOT NULL, " +
                    "email_address TEXT(100) NOT NULL, " +
                    "project_image_path TEXT(255)" +
                    ")";

                stmt.execute(createTableSQL);
                System.out.println("Participants table created successfully.");
            }

        } catch (SQLException e) {
            System.err.println("Database initialization error: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                "Database initialization failed. Please check the database path and permissions.",
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Insert new participant
    public static void insertParticipant(Participant participant) {
        String insertSQL =
            "INSERT INTO participants (StudentName, Faculty, ProjectTitle, " +
            "ContactNumber, EmailAddress, ProjectImagePath) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, participant.getStudentName());
            pstmt.setString(2, participant.getFaculty());
            pstmt.setString(3, participant.getProjectTitle());
            pstmt.setString(4, participant.getContactNumber());
            pstmt.setString(5, participant.getEmailAddress());
            pstmt.setString(6, participant.getProjectImagePath());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Participant registered successfully!");
            }

        } catch (SQLException e) {
            System.err.println("Error inserting participant: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error registering participant: " + e.getMessage(),
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Search participant by ID
    public static Participant searchParticipant(int RegistrationID) {
        String searchSQL = "SELECT * FROM participants WHERE RegistrationID = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(searchSQL)) {

            pstmt.setInt(1, RegistrationID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Participant participant = new Participant();
                participant.setRegistrationID(rs.getInt("RegistrationID"));
                participant.setStudentName(rs.getString("StudentName"));
                participant.setFaculty(rs.getString("Faculty"));
                participant.setProjectTitle(rs.getString("ProjectTitle"));
                participant.setContactNumber(rs.getString("ContactNumber"));
                participant.setEmailAddress(rs.getString("EmailAddress"));
                participant.setProjectImagePath(rs.getString("ProjectImagePath"));
                return participant;
            }

        } catch (SQLException e) {
            System.err.println("Error searching participant: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error searching participant: " + e.getMessage(),
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    // Update participant
    public static void updateParticipant(Participant participant) {
        String updateSQL =
            "UPDATE participants SET StudentName = ?, Faculty = ?, ProjectTitle = ?, " +
            "ContactNumber = ?, EmailAddress = ?, ProjectImagePath = ? " +
            "WHERE RegistrationID = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

            pstmt.setString(1, participant.getStudentName());
            pstmt.setString(2, participant.getFaculty());
            pstmt.setString(3, participant.getProjectTitle());
            pstmt.setString(4, participant.getContactNumber());
            pstmt.setString(5, participant.getEmailAddress());
            pstmt.setString(6, participant.getProjectImagePath());
            pstmt.setInt(7, participant.getRegistrationID());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Participant updated successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No participant found with the given ID.");
            }

        } catch (SQLException e) {
            System.err.println("Error updating participant: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error updating participant: " + e.getMessage(),
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Delete participant
    public static void deleteParticipant(int RegistrationID) {
        String deleteSQL = "DELETE FROM participants WHERE RegistrationID = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {

            pstmt.setInt(1, RegistrationID);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Participant deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No participant found with the given ID.");
            }

        } catch (SQLException e) {
            System.err.println("Error deleting participant: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error deleting participant: " + e.getMessage(),
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
