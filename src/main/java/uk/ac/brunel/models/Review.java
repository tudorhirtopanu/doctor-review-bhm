package uk.ac.brunel.models;

public class Review {
	
	private int reviewId;
	private int doctorId;
	private String reviewerName;
	private String reviewTitle;
	private String reviewText;
	private String date;
	private int reviewRating;
	
	public Review(int rID, int dID, String reviewerName, String reviewTitle, String reviewText, String date, int reviewRating) {
		reviewId = rID;
		doctorId = dID;
		this.reviewerName = reviewerName;
		this.reviewTitle = reviewTitle;
		this.reviewText = reviewText;
		this.date = date;
		this.reviewRating = reviewRating;
	}
	
	public String getReviewerName() {
		return reviewerName;
	}
	
	public String getReviewTitle() {
		return reviewTitle;
	}
	
	public String getReviewText() {
		return reviewText;
	}
	
	public String date() {
		return date;
	}
	
	public int getReviewRating() {
		return reviewRating;
	}

}
