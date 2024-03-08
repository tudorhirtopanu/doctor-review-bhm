package uk.ac.brunel.controllers;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.*;

public class ReviewCardController implements Initializable  {

	@FXML Label reviewNameLabel;
	@FXML Label reviewDate;
	@FXML Label reviewTitle;
	
	@FXML TextArea reviewTextArea;
	
	@FXML ImageView reviewStarRating;
	
	// Function to set values to the review instance
	public void setValues(String reviewerName, String date, String revTitle , String reviewText, int rating) {
		
		// Set the name
		reviewNameLabel.setText(reviewerName);
		
		// Set the date
		reviewDate.setText(date);
		
		// Set the review title
		reviewTitle.setText(revTitle);
		
		// Set the review text
		reviewTextArea.setText(reviewText);
		
		// Set the image for the review rating
		Image image = new Image(getClass().getResourceAsStream("/rating"+rating+".png"));
        reviewStarRating.setImage(image);
	}
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		reviewTextArea.setEditable(false);
		reviewTextArea.setWrapText(true);
		reviewTextArea.setFocusTraversable(false);
		
	}
	
}
