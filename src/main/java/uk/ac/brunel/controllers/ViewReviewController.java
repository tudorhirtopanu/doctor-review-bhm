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
        dummyReviews.add(new Review(1, 2, "Name 1", "Title 1", "Great service and approved very fast and tablets sent the next day. This was way better than trying and failing to get through to my doctors surgery, by time I'd have got an appointment my ailment would have gone and I needed tablets immediately. Yes, you pay a little more but the convenience far outweighs that. Yes, you pay a little more but the", "Today",1));
        dummyReviews.add(new Review(2, 2, "Name 2", "Title 2", "Text 2", "Today", 2));
        dummyReviews.add(new Review(3, 2, "Name 3", "Title 3", "Text 3", "Today", 1));
        dummyReviews.add(new Review(4, 2, "Name 4", "Title 4", "Text 4", "Today", 2));
        dummyReviews.add(new Review(5, 2, "Name 5", "Title 5", "Text 5", "Today", 4));
        
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
