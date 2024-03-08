package uk.ac.brunel.controllers;

import java.io.IOException;

import uk.ac.brunel.models.Review;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import uk.ac.brunel.models.Doctor;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ScrollPane;
import javafx.geometry.Insets;


public class ViewReviewController implements Initializable  {
	
	@FXML private ImageView brunelLogo;
    @FXML private Image logoImage = new Image(getClass().getResourceAsStream("/brunel_logo.png"));
    
    @FXML private Button backButton;
    @FXML private Label doctorName;
    @FXML private GridPane reviewGrid;
    @FXML private ScrollPane scrollPane;
    
    private ArrayList<Review> reviews;

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
    }
	
	private ArrayList<Review> createDummyReviews() {
        // Dummy method to create some sample reviews
        ArrayList<Review> dummyReviews = new ArrayList<>();
        dummyReviews.add(new Review(1, 2, "Name 1", "Title 1", "Text 1", "Today",5));
        dummyReviews.add(new Review(2, 2, "Name 2", "Title 2", "Text 2", "Today", 5));
        dummyReviews.add(new Review(3, 2, "Name 3", "Title 3", "Text 3", "Today", 1));
        dummyReviews.add(new Review(4, 2, "Name 4", "Title 4", "Text 4", "Today", 2));
        dummyReviews.add(new Review(5, 2, "Name 5", "Title 5", "Text 5", "Today", 4));
        // Add more dummy reviews as needed
        return dummyReviews;
    }
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
    	
		reviews = createDummyReviews();
    	// Set logo image 
    	brunelLogo.setImage(logoImage);
    	
    	// Add reviews to the GridPane
        int columnIndex = 0;
        int rowIndex = 0;
        
        reviewGrid.setPadding(new Insets(10)); // Padding around the whole GridPane
        reviewGrid.setHgap(10); // Horizontal spacing between cells
        reviewGrid.setVgap(20);
        
        for (Review review : reviews) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/uk/ac/brunel/views/ReviewCard.fxml"));
                AnchorPane reviewCard = loader.load();
                ReviewCardController controller = loader.getController();
                //controller.setTitle(review.getTitle()); // Set title for the review card
                //controller.setContent(review.getContent()); // Set content for the review card

                //reviewCard.setPadding(new Insets(10));
                
                reviewGrid.add(reviewCard, columnIndex, rowIndex);

                // Increment columnIndex and rowIndex
                columnIndex++;
                if (columnIndex == 4) {
                    columnIndex = 0;
                    rowIndex++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(reviewGrid);
    
    }
	
}
