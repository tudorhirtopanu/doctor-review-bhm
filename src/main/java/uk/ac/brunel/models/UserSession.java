package uk.ac.brunel.models;

import java.sql.SQLException;

import uk.ac.brunel.managers.DatabaseManager;

public class UserSession {

    private static UserSession instance;
    private static DatabaseManager dbManager = new DatabaseManager();

    private String name;
    private Doctor[] doctorsSeen;

    
    private UserSession(String name, Doctor[] doctors) {
        this.name = name;
        this.doctorsSeen = doctors;
    }

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
}
