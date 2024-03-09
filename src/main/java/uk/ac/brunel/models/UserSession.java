package uk.ac.brunel.models;

import java.sql.SQLException;

import uk.ac.brunel.managers.DatabaseManager;

/**
 * Represents a user session in the application.
 */

public class UserSession {

	// Singleton instance of UserSession
    private static UserSession instance;
    
    // Database manager instance for fetching data
    private static DatabaseManager dbManager = new DatabaseManager();

    private String name;
    private Doctor[] doctorsSeen;
    private int[] doctorIDList;

    /**
     * Private constructor to initialise a UserSession with the provided name and array of doctors.
     *
     * @param name The name of the user associated with the session.
     * @param doctors The array of doctors seen by the user.
     */
    private UserSession(String name, Doctor[] doctors) {
        this.name = name;
        this.doctorsSeen = doctors;
        doctorIDList = new int[doctors.length];
        for(int i = 0; i < doctors.length; i++) {
        	
        	doctorIDList[i] = doctors[i].getID();
        	
        }
    }

    /**
     * Returns the singleton instance of UserSession with the provided user name.
     *
     * @param name The name of the user associated with the session.
     * @return The singleton instance of UserSession.
     */
    public static synchronized UserSession getInstance(String name) {
    	
    	// Check if the instance is null, meaning it hasn't been created yet
        if (instance == null) {
            
        	// If the instance is null, fetch doctors from the database
            Doctor[] doctorsFromDatabase = null;
            System.out.println("Instance is being created");
            try {
            	// Attempt to retrieve doctors from the database
                doctorsFromDatabase = dbManager.getUserSessionDoctors(5);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Create a new instance of UserSession with the provided name and fetched doctors
            instance = new UserSession(name, doctorsFromDatabase);
        }
        System.out.println("Instance returned");
        
        // Return the existing instance (or the newly created one)
        return instance;
    }

    public String getName() {
        return name;
    }
    
    public void getFirstDoctorName() {
    	System.out.println(doctorsSeen[0].getName());
    }
    
    public Doctor[] getRecentDoctors() {
    	return doctorsSeen;
    }
    
    public int[] getDoctorIDList() {
    	return doctorIDList;
    }
}
