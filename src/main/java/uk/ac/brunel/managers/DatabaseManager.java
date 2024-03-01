package uk.ac.brunel.managers;
import java.sql.*;
import java.util.ArrayList;

import uk.ac.brunel.models.Doctor;

public class DatabaseManager {

	String url = "jdbc:sqlite:databases/doctors.db";
	
	public Connection conn() throws SQLException {
		Connection conn =  DriverManager.getConnection(url);
		return conn;
	}
	
	// Fetch all doctors from database and return arraylist of doctor objects  
	public ArrayList<Doctor> getAllDoctors(Connection conn) throws SQLException {
		
		ArrayList<Doctor> allDoctors = new ArrayList<Doctor>();
		
		ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM doctors_info");
		while(rs.next()) {
			
			int id = rs.getInt("id");
			String doctorName = rs.getString("doctor_name");
			String specialization = rs.getString("specialization");
			double reviewRating = rs.getDouble("review_rating");
			int totalReviews = rs.getInt("total_reviews");
			
			Doctor doctor = new Doctor(id,doctorName, specialization, reviewRating, totalReviews);
			allDoctors.add(doctor);
		}
		
		return allDoctors;
		
	}
	
	// Fetch distinct specialisations from database, in alphabetical order
	public ArrayList<String> getSpecialisations(Connection conn) throws SQLException {
		
		ArrayList<String> specialisations = new ArrayList<String>();
		
		ResultSet rs = conn.createStatement().executeQuery("SELECT DISTINCT specialization FROM doctors_info ORDER BY specialization ASC");
		while(rs.next()) {
			String specialization = rs.getString("specialization");
			specialisations.add(specialization);
		}
		
		return specialisations;
	}
	
}
