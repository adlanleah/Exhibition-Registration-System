/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ADLAN
 */
package Registration;

public class Participant {
    private int RegistrationID;
    private String studentName;
    private String faculty;
    private String projectTitle;
    private String contactNumber;
    private String emailAddress;
    private String projectImagePath;
    

    public Participant() {}
    
    
    public Participant(int registrationID, String studentName, String faculty, 
                      String projectTitle, String contactNumber, String emailAddress, 
                      String projectImagePath) {
        this.RegistrationID = registrationID;
        this.studentName = studentName;
        this.faculty = faculty;
        this.projectTitle = projectTitle;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.projectImagePath = projectImagePath;
    }
    
    // Getters
    public int getRegistrationID() { return RegistrationID; }
    public String getStudentName() { return studentName; }
    public String getFaculty() { return faculty; }
    public String getProjectTitle() { return projectTitle; }
    public String getContactNumber() { return contactNumber; }
    public String getEmailAddress() { return emailAddress; }
    public String getProjectImagePath() { return projectImagePath; }
    
    // Setters
    public void setRegistrationID(int registrationID) { this.RegistrationID = registrationID; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public void setFaculty(String faculty) { this.faculty = faculty; }
    public void setProjectTitle(String projectTitle) { this.projectTitle = projectTitle; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
    public void setProjectImagePath(String projectImagePath) { this.projectImagePath = projectImagePath; }
    
    @Override
    public String toString() {
        return "Participant{" +
                "registrationID=" + RegistrationID +
                ", studentName='" + studentName + '\'' +
                ", faculty='" + faculty + '\'' +
                ", projectTitle='" + projectTitle + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", projectImagePath='" + projectImagePath + '\'' +
                '}';
    }
}
