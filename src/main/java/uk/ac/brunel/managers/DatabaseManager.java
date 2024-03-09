package uk.ac.brunel.managers;
import java.sql.*;

import java.text.DecimalFormat;
import java.util.ArrayList;

import uk.ac.brunel.models.*;

public class DatabaseManager {
	
	private DateManager dateManager = new DateManager();

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
	
	public ArrayList<Review> getReviews(int doctorId) throws SQLException {
		
		ArrayList<Review> allReviews = new ArrayList<Review>();
		
		ResultSet rs = this.conn().createStatement().executeQuery("SELECT * FROM reviews WHERE doctor_id = "+doctorId);
		
		while(rs.next()) {
			int reviewID = rs.getInt("review_id");
			int doctorID = rs.getInt("doctor_id");
			String reviewerName = rs.getString("reviewer_name");
			String reviewTitle = rs.getString("review_title");
			String reviewText = rs.getString("review_text");
			String reviewDate = rs.getString("review_date");
			int reviewId = rs.getInt("review_rating");
			
			Review review = new Review(reviewID, doctorID, reviewerName, reviewTitle, reviewText, reviewDate, reviewId);
			allReviews.add(review);
			System.out.println(review.getReviewTitle());
		}
		
		return allReviews;
		
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
	
	// Submit review
	public void submitReview(int doctorId, String reviewerName, String reviewTitle, String reviewText, int reviewRating) throws SQLException {
		
		 try (Connection conn = this.conn()) {
	            String insertQuery = "INSERT INTO reviews (doctor_id, reviewer_name, review_title, review_date, review_text, review_rating) VALUES ("
	                    + doctorId + ", '" + reviewerName + "', '" + reviewTitle + "', '" + dateManager.getCurrentDateFormatted() + "', '" + reviewText + "', " + reviewRating + ")";

	            try (Statement statement = conn.createStatement()) {
	                statement.executeUpdate(insertQuery);
	            }

	        }
		
	}
	
	// Return random doctors for user session
	public Doctor[] getUserSessionDoctors(int numToReturn) throws SQLException {
		
        ArrayList<Doctor> doctorsList = new ArrayList<>();

        try (Connection conn = this.conn()) {
            String selectQuery = "SELECT * FROM doctors_info ORDER BY RANDOM() LIMIT " + numToReturn;

            try (Statement statement = conn.createStatement();
                 ResultSet rs = statement.executeQuery(selectQuery)) {

                while (rs.next()) {
                    // Retrieve data from the result set and create Doctor objects
                    int id = rs.getInt("id");
                    String name = rs.getString("doctor_name"); 
                    String specialization = rs.getString("specialization"); 
                    double reviewRating = rs.getDouble("review_rating");
        			int totalReviews = rs.getInt("total_reviews");
                    Doctor doctor = new Doctor(id, name, specialization, reviewRating, totalReviews);
                    doctorsList.add(doctor);
                }
            }
        }

        // Convert List<Doctor> to array
        Doctor[] doctorsArray = new Doctor[doctorsList.size()];
        doctorsList.toArray(doctorsArray);
        
        return doctorsArray;
    }
	
	public void updateDoctor(Doctor doctor, int rating) throws SQLException  {
		
		double newRating = ((doctor.getReviewRating()*doctor.getTotalReviews())+rating) / (doctor.getTotalReviews() + 1);
		DecimalFormat decimalFormat = new DecimalFormat("#.#");
		double roundedRating = Double.parseDouble(decimalFormat.format(newRating));
		
		try (Connection conn = this.conn()) {
			 String updateQuery = "UPDATE doctors_info SET review_rating = " + roundedRating + ", total_reviews = total_reviews + 1 WHERE id = " + doctor.getID();
			 try (Statement statement = conn.createStatement()) {
	                statement.executeUpdate(updateQuery);
	                System.out.println("New Rating for "+ doctor.getName() +" is "+ roundedRating);
	         }
		}
		
	}
	
}
