package uk.ac.brunel.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import uk.ac.brunel.managers.DatabaseManager;
import uk.ac.brunel.models.Doctor;
import uk.ac.brunel.managers.DoctorManager;

import java.net.URL;
import java.util.*;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.*;



public class HomeController implements Initializable {
	
	private DatabaseManager dbm = new DatabaseManager();

	private DoctorManager doctorManager = new DoctorManager();
	
	// FXML variables
	@FXML private TextField textField;
	
    @FXML private Button helloButton;

    @FXML private ListView<Doctor> listView;
    
    @FXML private RadioButton sortRatingHighestBtn;
    
    @FXML private RadioButton sortRatingLowestBtn;
    
    @FXML private ToggleGroup ratingToggle;
    
    // variables
    ArrayList<Doctor> items = new ArrayList<>();
    
    // methods
    
    @FXML private void handleButtonClick(ActionEvent event) {
    	
    	String text = textField.getText();
    	
    	System.out.println(text);
    	
    	ArrayList<Doctor> filteredDoctors = doctorManager.filterDoctorByName(items, text);
    	
    	listView.getItems().clear();
    	
    	listView.getItems().addAll(filteredDoctors);
    	
    	/*
    	listView.getItems().clear();
    	
    	List<Doctor> selectedDoctors = Arrays.asList(
                new Doctor(0, "Dr Mia Carte", "Vascular Surgery", 4.9, 45),
                new Doctor(1, "Dr Oliver Johnson", "Vascular Surgery", 3.0, 45),
                new Doctor(2, "Dr Eli Ward", "Neurolog", 4.5, 49),
                new Doctor(3, "Dr John Doe", "Cardiology", 4.5, 20),
                new Doctor(4, "Dr Jane Smith", "Orthopedics", 4.2, 15),
                new Doctor(5, "Dr Wayne Hope", "Orthopedics", 4.2, 15)
        );
    	
    	listView.getItems().addAll(selectedDoctors);
    	*/
        
    }
    
    private void setupListView() {
    	
    	try {
  		  items.addAll(dbm.getAllDoctors(dbm.conn()));
    	}
    	catch(Exception e) {
  		  e.printStackTrace();
    	}
      
      listView.getItems().addAll(items);
      
      listView.setOnMouseClicked(event -> {
          Doctor selectedDoctor = listView.getSelectionModel().getSelectedItem();
          if (selectedDoctor != null) {
              navigateToDestination(selectedDoctor);
          }
      });
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    	setupListView();
        
        // Set cell factory
        listView.setCellFactory(param -> new ListCell<>() {
        	
        	// Create HBox for each cell
        	private final HBox hbox = new HBox();
        	Text name = new Text();
    		Text specialty = new Text();
    		Text reviewNo = new Text();
    		Text rating =  new Text();
        	
    		// Instance initialiser block
        	{
        		// Create text nodes to display properties of doctor        		
        		hbox.getChildren().addAll(name,specialty,reviewNo ,rating);
        		
        	}
        	
        	@Override
            protected void updateItem(Doctor item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                	
                    // Update the content of the HBox based on the Doctor object
                    name.setText(item.getName());
                    specialty.setText(item.getSpecialization());
                    reviewNo.setText(Integer.toString(item.getTotalReviews()));
                    rating.setText(Double.toString(item.getReviewRating()));
                    
                    name.setWrappingWidth(175);
                    specialty.setWrappingWidth(220);
                    reviewNo.setWrappingWidth(75);
                    rating.setWrappingWidth(75);
                    
                    //name.setFont(Font.font(null, FontWeight.SEMI_BOLD, 14));

                    setGraphic(hbox);
                }
            }
        	
        });

    
    }
    
    private void navigateToDestination(Doctor doctor) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uk/ac/brunel/views/DoctorReview.fxml"));
            Parent root = loader.load();
            DoctorReviewController destinationController = loader.getController();
            
            destinationController.initData(doctor);
            
            StackPane rootPane = (StackPane) helloButton.getScene().getRoot();
            rootPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
