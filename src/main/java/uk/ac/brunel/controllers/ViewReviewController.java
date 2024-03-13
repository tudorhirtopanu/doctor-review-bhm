package uk.ac.brunel.controllers;

// JavaFX imports
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ScrollPane;
import javafx.geometry.Insets;

//Java Standard Library Imports
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

// Application specific imports
import uk.ac.brunel.models.Doctor;
import uk.ac.brunel.models.Review;
import uk.ac.brunel.managers.*;

// SQL imports
import java.sql.*;

public class ViewReviewController implements Initializable  {
	
	private DatabaseManager dbManager = new DatabaseManager();
	
	// FXML fields
	@FXML private ImageView brunelLogo;
    @FXML private Image logoImage = new Image(getClass().getResourceAsStream("/brunel_logo.png"));
    
    @FXML private ImageView backButtonImg;
    @FXML private Image backArrow = new Image(getClass().getResourceAsStream("/backArrow.png"));
    
    @FXML private Button backButton;
    @FXML private Label doctorName;
    @FXML private Label doctorSpecialisationLabel;
    @FXML private GridPane reviewGrid;
    @FXML private ScrollPane scrollPane;
    
    // fields
    private ArrayList<Review> reviews;
    private Doctor doctor;
    
    // Constructor
    public ViewReviewController(Doctor doctor) {
        this.doctor = doctor;
    }

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
    
	public void initData(Doctor doctor) {
		doctorName.setText(doctor.getName());
		doctorSpecialisationLabel.setText(doctor.getSpecialization());
    }
	
	// Create dummy reviews alongside actual reviews
	private ArrayList<Review> createDummyReviews() {
		
		// Array of dummy reviews
        ArrayList<Review> dummyReviews = new ArrayList<>();
        dummyReviews.add(new Review(1, 2, "Emily Smith", "Life Saver!", doctor.getName() + "'s expertise changed my life! Highly recommended for their compassionate care and accurate diagnosis", "12 January 2024",5));
        dummyReviews.add(new Review(2, 2, "David Johnson", "Exceptional Doctor", doctor.getName()+" is an exceptional doctor! Their dedication to their patients and depth of knowledge are unmatched", "20 January 2024", 5));
        dummyReviews.add(new Review(3, 2, "Lisa Chen", "Caring and Knowledgeable", "They are not only caring but also highly knowledgeable. Took time to listen and provided excellent care", "1 Febuary 2024", 5));
        dummyReviews.add(new Review(4, 2, "Michael Brown", "Needs Improvement", "Lack of communication and poor manners. Would not recommend to anyone.", "3 March 2024", 1));
        dummyReviews.add(new Review(5, 2, "Anonymous", "Average Experience", "The experience with "+ doctor.getName() + " was okay. Nothing exceptional, but nothing particularly negative either.", "7 March 2024", 3));
        
        try {
        	// add on actual reviews to dummy reviews
    		dummyReviews.addAll(dbManager.getReviews(doctor.getID()));
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	}
        
        return dummyReviews;
    }
	
	private void setUpReview() {
		
		reviews = createDummyReviews();
		
		// Add reviews to the GridPane
        int columnIndex = 0;
        int rowIndex = 0;
        
        // Padding and spacing for grid pane
        reviewGrid.setPadding(new Insets(10));
        reviewGrid.setHgap(10);
        reviewGrid.setVgap(20);
        
        // Use enhanced for loop / for each loop
        for (Review review : reviews) {
            try {
            	// Load the FXML file for the review card
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/uk/ac/brunel/views/ReviewCard.fxml"));
                AnchorPane reviewCard = loader.load();
                ReviewCardController controller = loader.getController();

                // Set values for the review card using data from the review object
                controller.setValues(review.getReviewerName(), review.getDate(), review.getReviewTitle(), review.getReviewText(), review.getReviewRating());
                
                // Add review card to grid pane 
                reviewGrid.add(reviewCard, columnIndex, rowIndex);

                // Increment columnIndex and rowIndex, and reset columnIndex if it reaches 4
                columnIndex++;
                if (columnIndex == 4) {
                    columnIndex = 0;
                    rowIndex++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        // Configure the ScrollPane to fit its content and disable horizontal scrolling
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(reviewGrid);
		
	}
	
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	// Set logo image 
    	brunelLogo.setImage(logoImage);
    	
    	backButtonImg.setImage(backArrow);
    	
    	backButtonImg.setFitWidth(25);
    	backButtonImg.setFitHeight(11);
    	
    	setUpReview();
    
    }
	
}
