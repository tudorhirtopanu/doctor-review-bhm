package uk.ac.brunel.models;

public class Review {
	
	private int reviewId;
	private int doctorId;
	private String reviewerName;
	private String reviewTitle;
	private String reviewText;
	private String date;
	
	public Review(int rID, int dID, String reviewerName, String reviewTitle, String reviewText, String date) {
		reviewId = rID;
		doctorId = dID;
		this.reviewerName = reviewerName;
		this.reviewTitle = reviewTitle;
		this.reviewText = reviewText;
		this.date = date;
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

}
