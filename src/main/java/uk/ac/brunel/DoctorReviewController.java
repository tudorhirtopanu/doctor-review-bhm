package uk.ac.brunel;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DoctorReviewController {

	
	@FXML
    private Label nameLabel;

    @FXML
    private Label ratingLabel;
	
	public void initData(Doctor doctor) {
        // Initialize the destination view with data from the selected doctor
        nameLabel.setText(doctor.getName());
        //ratingLabel.setText(String.valueOf(doctor.getReviewRating()));
    }
	
}
