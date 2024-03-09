package uk.ac.brunel.controllers;

// Java Standard Library Imports
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// SQL imports
import java.sql.SQLException;

// JavaFX imports
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Alert;
import javafx.scene.image.*;
import javafx.scene.Parent;

// Application specific imports
import uk.ac.brunel.utils.TextFieldUtils;
import uk.ac.brunel.utils.TextFieldUtils.CharLimit;
import uk.ac.brunel.managers.DatabaseManager;
import uk.ac.brunel.models.Doctor;
import uk.ac.brunel.utils.DesignUtils;


public class DoctorReviewController implements Initializable {

	private DatabaseManager dbManager = new DatabaseManager();
	
	// UI Elements
	@FXML private Rectangle reviewBackgroundRect;
	@FXML private ImageView brunelLogo;
    @FXML private Image logoImage = new Image(getClass().getResourceAsStream("/brunel_logo.png"));
    @FXML private Image xImage = new Image(getClass().getResourceAsStream("/X.png"));
    @FXML private Button writeRevBackButton;
    @FXML private Button submitButton;
    @FXML private Label submitLabel;
    @FXML private Label revRatingLabel;
    
    // Form fields
    @FXML private Button starBtn1;
    @FXML private Button starBtn2;
    @FXML private Button starBtn3;
    @FXML private Button starBtn4;
    @FXML private Button starBtn5;
    
    @FXML private TextField name;
    @FXML private TextField reviewTitle;
    @FXML private TextArea reviewText;
    @FXML private CheckBox anonymCheckbox;
    
    // Variable containing the currently selected doctor
    private Doctor doctor;
    
    // Review rating
    private int rating = 0;
    
    // Function to submit review or provide alert if invalid form is submitted
    @FXML private void submitReview() {
    	
    	// If user tries to submit an empty form
    	if(areFieldsEmpty()) {
    		
    		// Display an alert
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setTitle("Error");
    		alert.setHeaderText("Invalid Form Submission");
    		alert.setContentText("Fields have been left empty/filled incorrectly. Please review and try again");

    		alert.showAndWait();
    		
    	} else {
    		
    		// Submit the form
    		try {
    			String userName = name.isDisable() ? "Anonymous" : name.getText();
    			
        		dbManager.submitReview(doctor.getID(), userName, reviewTitle.getText(), reviewText.getText(), rating);
        		dbManager.updateDoctor(doctor, rating);
        		navigateToHome();
        	} catch (SQLException e) {
        	    // Handle the SQLException
        	    e.printStackTrace(); 
        	}
    	}    	
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
    
    // Returns a boolean indicating whether or not fields are empty
    private boolean areFieldsEmpty() {
    	
    	// Get the text from the fields
    	String nameText = name.getText().trim();
    	String reviewTitleText = reviewTitle.getText().trim();
    	String reviewBodyText = reviewText.getText().trim();
    	
    	// If name text field is disabled then dont check if it is empty
    	if (name.isDisable()) {
    		if(reviewTitleText.isEmpty() || reviewBodyText.isEmpty() || rating == 0) {
        		return true;
        	} else {
        		return false;
        	}
    	} else {
    		
    		// Check if any fields are empty
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
    
    // Determine the rating & label based on the button that triggered the event
    private int getRating(ActionEvent event) {
        if (event.getSource() == starBtn1) {
        	rating = 1;
        	revRatingLabel.setText("Very Poor");
        	return 1;
        }
        if (event.getSource() == starBtn2) {
        	rating = 2;
        	revRatingLabel.setText("Poor");
        	return 2;
        }
        if (event.getSource() == starBtn3) {
        	rating = 3;
        	revRatingLabel.setText("Average");
        	return 3;
        }
        if (event.getSource() == starBtn4) {
        	rating = 4;
        	revRatingLabel.setText("Good");
        	return 4;
        }
        if (event.getSource() == starBtn5) {
        	rating = 5;
        	revRatingLabel.setText("Excellent");
        	return 5;
        }
        
        return 0; 
    }

    // Method to initialize buttons
    public void initialiseButtons() {
    	
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
    
    // Navigate to home view
    @FXML private void navigateToHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uk/ac/brunel/views/Home.fxml"));
            Parent root = loader.load();
            StackPane rootPane = (StackPane) writeRevBackButton.getScene().getRoot();
            rootPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Recieves data from the home view
 	public void initData(Doctor doctor_p) {
 		
 		doctor = doctor_p;
 		submitLabel.setText(doctor_p.getName() + " and other users will be able to see this review.");
 		
        ImageView imageView = new ImageView(xImage);
        
        writeRevBackButton.setGraphic(imageView);
        
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
     }
 	
 	//public void setRectangleShadow
	
	 @Override
	 public void initialize(URL location, ResourceBundle resources) {
		 
		 // Limit the characters that can be entered for each page
		 TextFieldUtils.limitCharacters(reviewText, CharLimit.TEXT);
		 TextFieldUtils.limitTextFieldCharacters(name, CharLimit.NAME);
		 TextFieldUtils.limitTextFieldCharacters(reviewTitle, CharLimit.TITLE);
		 
		 initialiseButtons();
		 
		 // Disable/enable the name text field based on if the checkbox is selected
		 anonymCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
	            name.setDisable(newValue);
	      });
		 
		 brunelLogo.setImage(logoImage);
		 
		 // Set drop shadow on rectangle
		 DesignUtils.setDropShadow(reviewBackgroundRect);
	     
	     // Allow text to wrap on multiple lines
	     reviewText.setWrapText(true);
		 
		 
	 }

	
}
