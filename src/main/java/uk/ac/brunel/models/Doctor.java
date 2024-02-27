package uk.ac.brunel.models;

public class Doctor {
	
	private int id;
	private String name;
	private String specialization;
	private double reviewRating;
	private int totalReviews;
	
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
