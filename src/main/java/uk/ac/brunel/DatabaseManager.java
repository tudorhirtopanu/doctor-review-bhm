package uk.ac.brunel;
import java.sql.*;
import java.util.ArrayList;

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
	
}
