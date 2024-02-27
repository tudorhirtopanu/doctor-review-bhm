package uk.ac.brunel.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import uk.ac.brunel.models.Doctor;

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
