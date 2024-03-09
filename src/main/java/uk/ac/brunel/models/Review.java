package uk.ac.brunel.models;

/**
 * Represents a review for a doctor.
 */
public class Review {
	
	private int reviewId;
	private int doctorId;
	private String reviewerName;
	private String reviewTitle;
	private String reviewText;
	private String date;
	private int reviewRating;
	
	
	/**
	 * Constructor to initialize a Review object with the provided details.
	 *
	 * @param rID The review ID.
	 * @param dID The ID of the doctor who this review is for.
	 * @param reviewerName The name of the reviewer.
	 * @param reviewTitle The title of the review.
	 * @param reviewText The text content of the review.
	 * @param date The date when the review was made.
	 * @param reviewRating The rating given in the review.
	 */
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
	
	public String getDate() {
		return date;
	}
	
	public int getReviewRating() {
		return reviewRating;
	}

}
