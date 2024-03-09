package uk.ac.brunel.models;

/**
 * Represents a doctor with relevant details.
 */
public class Doctor {
	
	private int id;
	private String name;
	private String specialization;
	private double reviewRating;
	private int totalReviews;
	
	/**
	 * Constructor to initialise a Doctor object with the provided details.
	 *
	 * @param id The unique identifier for the doctor.
	 * @param name The name of the doctor.
	 * @param specialisation The specialisation of the doctor.
	 * @param reviewRating The average review rating of the doctor.
	 * @param totalReviews The total number of reviews for the doctor.
	 */
	public Doctor(int id, String name, String specialization, double reviewRating, int totalReviews) {
		
		this.id = id;
		this.name = name;
		this.specialization = specialization;
		this.reviewRating = reviewRating;
		this.totalReviews = totalReviews;
		
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSpecialization() {
		return specialization;
	}
	
	public double getReviewRating() {
		return reviewRating;
	}
	
	public int getTotalReviews() {
		return totalReviews;
	}
	
	
}
