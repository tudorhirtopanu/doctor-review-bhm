package uk.ac.brunel.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import uk.ac.brunel.models.Doctor;

import javafx.scene.image.*;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.geometry.Pos;

public class DoctorReviewController implements Initializable {

    @FXML private Button backButton;
    
    @FXML private Button starBtn1;
    @FXML private Button starBtn2;
    @FXML private Button starBtn3;
    @FXML private Button starBtn4;
    @FXML private Button starBtn5;
    
    private boolean starOneActive = false;
    private boolean starTwoActive = false;
    private boolean starThreeActive = false;
    private boolean starFourActive = false;
    private boolean starFiveActive = false;
    
    @FXML private void navigateToHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uk/ac/brunel/views/Home.fxml"));
            Parent root = loader.load();
            //ViewReviewController destinationController = loader.getController();
            StackPane rootPane = (StackPane) backButton.getScene().getRoot();
            rootPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
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
        String imageUrl = filled ? "star_fill.png" : "empty_star.png";
        
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
        if (event.getSource() == starBtn1) return 1;
        if (event.getSource() == starBtn2) return 2;
        if (event.getSource() == starBtn3) return 3;
        if (event.getSource() == starBtn4) return 4;
        if (event.getSource() == starBtn5) return 5;
        
        return 0; 
    }
    
    /*
    @FXML public void setRatings(ActionEvent event) {
    	
    	if(event.getSource() == starBtn1) {
    		ImageView star1 = createStarImageView("star_fill.png");
    		ImageView star2 = createStarImageView("empty_star.png");
            ImageView star3 = createStarImageView("empty_star.png");
            ImageView star4 = createStarImageView("empty_star.png");
            ImageView star5 = createStarImageView("empty_star.png");
    		
    		 setButtonProperties(starBtn1, star1);
    	     setButtonProperties(starBtn2, star2);
    	     setButtonProperties(starBtn3, star3);
    	     setButtonProperties(starBtn4, star4);
    	     setButtonProperties(starBtn5, star5);
    	}
    	
    	if(event.getSource() == starBtn2) {
    		ImageView star1 = createStarImageView("star_fill.png");
    		ImageView star2 = createStarImageView("star_fill.png");
    		ImageView star3 = createStarImageView("empty_star.png");
    		ImageView star4 = createStarImageView("empty_star.png");
    		ImageView star5 = createStarImageView("empty_star.png");
    		
    		 setButtonProperties(starBtn1, star1);
    	     setButtonProperties(starBtn2, star2);
    	     setButtonProperties(starBtn3, star3);
    	     setButtonProperties(starBtn4, star4);
    	     setButtonProperties(starBtn5, star5);
    	}
    	
    	if(event.getSource() == starBtn3) {
    		ImageView star1 = createStarImageView("star_fill.png");
    		ImageView star2 = createStarImageView("star_fill.png");
    		ImageView star3 = createStarImageView("star_fill.png");
    		ImageView star4 = createStarImageView("empty_star.png");
    		ImageView star5 = createStarImageView("empty_star.png");
    		
    		 setButtonProperties(starBtn1, star1);
    	     setButtonProperties(starBtn2, star2);
    	     setButtonProperties(starBtn3, star3);
    	     setButtonProperties(starBtn4, star4);
    	     setButtonProperties(starBtn5, star5);
    	}
    	
    	if(event.getSource() == starBtn4) {
    		ImageView star1 = createStarImageView("star_fill.png");
    		ImageView star2 = createStarImageView("star_fill.png");
    		ImageView star3 = createStarImageView("star_fill.png");
    		ImageView star4 = createStarImageView("star_fill.png");
    		ImageView star5 = createStarImageView("empty_star.png");
    		
    		 setButtonProperties(starBtn1, star1);
    	     setButtonProperties(starBtn2, star2);
    	     setButtonProperties(starBtn3, star3);
    	     setButtonProperties(starBtn4, star4);
    	     setButtonProperties(starBtn5, star5);
    	}
    	
    	if(event.getSource() == starBtn5) {
    		ImageView star1 = createStarImageView("star_fill.png");
    		ImageView star2 = createStarImageView("star_fill.png");
    		ImageView star3 = createStarImageView("star_fill.png");
    		ImageView star4 = createStarImageView("star_fill.png");
    		ImageView star5 = createStarImageView("star_fill.png");
    		
    		 setButtonProperties(starBtn1, star1);
    	     setButtonProperties(starBtn2, star2);
    	     setButtonProperties(starBtn3, star3);
    	     setButtonProperties(starBtn4, star4);
    	     setButtonProperties(starBtn5, star5);
    	}
    	
    }
    */
	
	public void initData(Doctor doctor) {
        // Initialize the destination view with data from the selected doctor
        
        //ratingLabel.setText(String.valueOf(doctor.getReviewRating()));
    }

    // Method to initialize buttons
    public void initializeButtons() {
    	
        // Create ImageView instances
        ImageView star1 = createStarImageView("empty_star.png");
        ImageView star2 = createStarImageView("empty_star.png");
        ImageView star3 = createStarImageView("empty_star.png");
        ImageView star4 = createStarImageView("empty_star.png");
        ImageView star5 = createStarImageView("empty_star.png");

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

		 
	 }

	
}
