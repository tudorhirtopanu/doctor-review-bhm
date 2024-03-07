package uk.ac.brunel.controllers;

import java.io.IOException;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import uk.ac.brunel.managers.DatabaseManager;
import uk.ac.brunel.models.Doctor;

import javafx.scene.image.*;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.geometry.Pos;

import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Tooltip;

// TODO: Make class to get current date

public class DoctorReviewController implements Initializable {

	private DatabaseManager dbManager = new DatabaseManager();
	
	@FXML private Rectangle reviewBackgroundRect;
	
	@FXML private ImageView brunelLogo;
    @FXML private Image logoImage = new Image(getClass().getResourceAsStream("/brunel_logo.png"));
    
    @FXML private Image xImage = new Image(getClass().getResourceAsStream("/X.png"));
	
    @FXML private Button writeRevBackButton;
    
    @FXML private Button submitButton;
    
    @FXML private Label submitLabel;
    
    @FXML private CheckBox anonymCheckbox;
    
    @FXML private Button starBtn1;
    @FXML private Button starBtn2;
    @FXML private Button starBtn3;
    @FXML private Button starBtn4;
    @FXML private Button starBtn5;
    
    // Form fields
    @FXML private TextField name;
    @FXML private TextField reviewTitle;
    @FXML private TextArea reviewText;
    
    Doctor doctor;
    
    private int rating = 0;
    
    @FXML 
    private void initialiseView() {
        
        // Create an ImageView with the image
        ImageView imageView = new ImageView(xImage);
        
        // Set the graphic of the backButton to the ImageView
        writeRevBackButton.setGraphic(imageView);
        
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
    }
    
    @FXML private void navigateToHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uk/ac/brunel/views/Home.fxml"));
            Parent root = loader.load();
            //ViewReviewController destinationController = loader.getController();
            StackPane rootPane = (StackPane) writeRevBackButton.getScene().getRoot();
            rootPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML private void checkboxActions() {
    	/*
    	if(anonymCheckbox.isSelected()) {
    		name.setDisable(true);
    	} else {
    		name.setDisable(true);
    	}
    	*/
    	/*
    	anonymCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            name.setDisable(newValue); // Toggle TextField's disable state
        });
        */

    }
    
    @FXML private void submitReview() {
    	
    	if(areFieldsEmpty()) {
    		System.out.println("At least one field is empty");
    		
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error");
    		alert.setHeaderText("Invalid Form Submission");
    		alert.setContentText("Fields have been left empty/filled incorrectly. Please review and try again");

    		// Show the alert
    		alert.showAndWait();
    		
    	} else {
    		try {
    			
    			String userName = name.isDisable() ? "Anonymous" : name.getText();
    			
    			// TODO: Get current date
        		dbManager.submitReview(doctor.getID(), userName, reviewTitle.getText(), reviewText.getText(), "Today", rating);
        		navigateToHome();
        	} catch (SQLException e) {
        	    // Handle the SQLException
        	    e.printStackTrace(); 
        	}
    	}
    	
    	
    }
    
    private boolean areFieldsEmpty() {
    	
    	String nameText = name.getText().trim();
    	String reviewTitleText = reviewTitle.getText().trim();
    	String reviewBodyText = reviewText.getText().trim();
    	
    	if (name.isDisable()) {
    		if(reviewTitleText.isEmpty() || reviewBodyText.isEmpty() || rating == 0) {
        		return true;
        	} else {
        		return false;
        	}
    	} else {
    		if(nameText.isEmpty() || reviewTitleText.isEmpty() || reviewBodyText.isEmpty() || rating == 0) {
        		return true;
        	} else {
        		return false;
        	}
    	}
    	
    	
    }
    
    // Method to create and configure a new ImageView with the star image
    private ImageView createStarImageView(String imagePath) {
    	
        // Load the image
        Image starImage = new Image(getClass().getResourceAsStream(imagePath));

        // Create a new ImageView with the loaded image
        ImageView starImageView = new ImageView(starImage);

        // Set the fit width and height
        starImageView.setFitWidth(30);
        starImageView.setFitHeight(30);

        return starImageView;
    }

    // Method to set the graphic and alignment for a button
    private void setButtonProperties(Button button, ImageView imageView) {
        button.setGraphic(imageView);
        button.setAlignment(Pos.CENTER);
    }
    
    // Set the stars to either filled or unfilled
    @FXML public void setRatings(ActionEvent event) {
    	
    	// Create array containing the buttons
        Button[] starButtons = {starBtn1, starBtn2, starBtn3, starBtn4, starBtn5};
        
        for (int i = 0; i < starButtons.length; i++) {
        	
        	// Determine if the star should be filled based on its position and the selected rating
        	boolean filled = i < getRating(event);
        	
        	// Create an ImageView for the star
            ImageView star = createStarImageView(filled);
            
            // set the properties for each star
            setButtonProperties(starButtons[i], star);
        }
        
    }
    
    // Returns image view of star
    private ImageView createStarImageView(boolean filled) {
    	
    	// Determine the image URL based on whether the star should be filled or unfilled
        String imageUrl = filled ? "/star_fill.png" : "/empty_star.png";
        
        // Load the star image from the resources
        Image starImage = new Image(getClass().getResourceAsStream(imageUrl));
        
     // Create an ImageView for the star image
        ImageView starImageView = new ImageView(starImage);
        
        // Set size
        starImageView.setFitWidth(30);
        starImageView.setFitHeight(30);
        
        return starImageView;
    }
    
    //determine the rating based on the button that triggered the event
    private int getRating(ActionEvent event) {
        if (event.getSource() == starBtn1) {
        	rating = 1;
        	return 1;
        }
        if (event.getSource() == starBtn2) {
        	rating = 2;
        	return 2;
        }
        if (event.getSource() == starBtn3) {
        	rating = 3;
        	return 3;
        }
        if (event.getSource() == starBtn4) {
        	rating = 4;
        	return 4;
        }
        if (event.getSource() == starBtn5) {
        	rating = 5;
        	return 5;
        }
        
        return 0; 
    }

	
	public void initData(Doctor doctor_p) {
        // Initialize the destination view with data from the selected doctor
        
        //ratingLabel.setText(String.valueOf(doctor.getReviewRating()));
		doctor = doctor_p;
		submitLabel.setText(doctor_p.getName() + " and other users will be able to see this review.");
    }

    // Method to initialize buttons
    public void initializeButtons() {
    	
        // Create ImageView instances
        ImageView star1 = createStarImageView("/empty_star.png");
        ImageView star2 = createStarImageView("/empty_star.png");
        ImageView star3 = createStarImageView("/empty_star.png");
        ImageView star4 = createStarImageView("/empty_star.png");
        ImageView star5 = createStarImageView("/empty_star.png");

        // Set properties for each button
        setButtonProperties(starBtn1, star1);
        setButtonProperties(starBtn2, star2);
        setButtonProperties(starBtn3, star3);
        setButtonProperties(starBtn4, star4);
        setButtonProperties(starBtn5, star5);
    }
    
    
	
	 @Override
	 public void initialize(URL location, ResourceBundle resources) {
		 
		 initializeButtons();
		 anonymCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
	            name.setDisable(newValue); // Toggle TextField's disable state
	        });
		 
		 brunelLogo.setImage(logoImage);
		 
		 initialiseView();
		 
		 	String hexColor = "#E7E7EB"; // Hexadecimal color value
	        Color color = Color.web(hexColor);
		 
		 	DropShadow dropShadow = new DropShadow();
	        dropShadow.setColor(color); // Set the color of the drop shadow
	        dropShadow.setRadius(20); // Set the radius of the drop shadow
	        dropShadow.setOffsetX(5); // Set the horizontal offset of the drop shadow
	        dropShadow.setOffsetY(5); // Set the vertical offset of the drop shadow

	        // Apply the DropShadow effect to the Rectangle
	        reviewBackgroundRect.setEffect(dropShadow);
		 
		 
	 }

	
}
