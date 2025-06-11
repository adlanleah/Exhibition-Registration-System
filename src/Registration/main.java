package Registration;

import java.awt.HeadlessException;
import javax.swing.JOptionPane;
        public class main {
        public static void main(String[] args) {
             System.out.println(System.getProperty("java.class.path"));
            try {
                // Explicitly load the UCanAccess driver
                Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

                // Testing the database connection
                if (DatabaseManager.testConnection()) {
                    System.out.println("Database connection successful!");
                } else {
                    System.err.println("Database connection failed!");
                    JOptionPane.showMessageDialog(null, "Database connection failed. Check the database path and driver.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit if the connection fails
                }

                // Initializing the database and creating a table if it doesn't exist
                DatabaseManager.initializeDatabase();

                // Now creating and displaying the GUI
                RegistrationForm form = new RegistrationForm();
                form.setVisible(true);
                form.toFront();

            } catch (ClassNotFoundException e) {
                System.err.println("UCanAccess driver not found!");
                JOptionPane.showMessageDialog(null, "UCanAccess driver not found.  Make sure the driver JARs are in the classpath.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (HeadlessException e) {
                System.err.println("An unexpected error occurred: " + e.getMessage());
                JOptionPane.showMessageDialog(null, "An unexpected error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
