package uk.ac.brunel.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import uk.ac.brunel.managers.DatabaseManager;
import uk.ac.brunel.models.Doctor;
import uk.ac.brunel.managers.DoctorManager;
import uk.ac.brunel.managers.DoctorManager.SortOrder;

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


// TODO: add option to clear text 

public class HomeController implements Initializable {
	
	private DatabaseManager dbm = new DatabaseManager();

	private DoctorManager doctorManager = new DoctorManager();
	
	// FXML variables
	@FXML private TextField textField;
	
    @FXML private Button helloButton;
    @FXML private Button clearFiltersBtn;

    @FXML private ListView<Doctor> listView;
    
    @FXML private RadioButton sortRatingHighestBtn;
    
    @FXML private RadioButton sortRatingLowestBtn;
    
    @FXML private ToggleGroup ratingToggle;
    
    @FXML private ComboBox<String> specialtyFilter;
    
    // variables
    ArrayList<Doctor> allDoctors = new ArrayList<>(); // full list of doctors
    ArrayList<Doctor> doctorItems = new ArrayList<>(); // list of doctors being searched
    ArrayList<Doctor> filteredDoctorItems = new ArrayList<>(); // filtered list of doctors
    
    ArrayList<String> specialisations = new ArrayList<>();
    
    // flags
    private boolean filteringSearchItems = false;
    private boolean isSpecialisationSelected = false;
    
    // methods
    
    @FXML private void searchDoctors(ActionEvent event) {
    	
    	String text = textField.getText();
    	
    	//System.out.println(text);
    	
    	doctorItems = doctorManager.filterDoctorByName(allDoctors, text);
    	
    	if(sortRatingHighestBtn.isSelected()) {
    		doctorItems = doctorManager.sortDoctorsByRating(doctorItems, SortOrder.DESCENDING);
    	}
    	else if (sortRatingLowestBtn.isSelected()) {
    		doctorItems = doctorManager.sortDoctorsByRating(doctorItems, SortOrder.ASCENDING);
    	}
    	
    	// if a specialisation is selected then filter by that when searching
    	if(isSpecialisationSelected) {
    		filteredDoctorItems = doctorManager.filterDoctorsBySpecialisation(doctorItems, specialtyFilter.getValue());
    		listView.getItems().setAll(filteredDoctorItems);
    	} 
    	
    	// otherwise just set a new list of doctors
    	else {
        	listView.getItems().setAll(doctorItems);
    	}

    }
    
    // Sort the array of doctors based on which button has been clicked
    @FXML private void handleSortButtonClicked(ActionEvent event) {
    	
    	if(sortRatingHighestBtn.isSelected()) {
    		
    		if(!filteringSearchItems) {
    			doctorItems = doctorManager.sortDoctorsByRating(doctorItems, SortOrder.DESCENDING);
        		
        		listView.getItems().setAll(doctorItems);
    		} else {
    			filteredDoctorItems = doctorManager.sortDoctorsByRating(filteredDoctorItems, SortOrder.DESCENDING);
        		
        		listView.getItems().setAll(filteredDoctorItems);
    		}

    	} else if (sortRatingLowestBtn.isSelected()) {
    		
    		if(!filteringSearchItems) {
    			doctorItems = doctorManager.sortDoctorsByRating(doctorItems, SortOrder.ASCENDING);
        		
        		listView.getItems().setAll(doctorItems);
    		} else {
    			filteredDoctorItems = doctorManager.sortDoctorsByRating(filteredDoctorItems, SortOrder.ASCENDING);
        		
        		listView.getItems().setAll(filteredDoctorItems);
    		}
    		
    	}
    	
    }
    
    @FXML private void handleComboBoxEvent(ActionEvent event) {
    	
    	String specialisation = specialtyFilter.getValue();
    	
    	filteredDoctorItems = doctorManager.filterDoctorsBySpecialisation(doctorItems, specialisation);
    	
    	listView.getItems().setAll(filteredDoctorItems); 
    	
    	filteringSearchItems = true;
    	isSpecialisationSelected = true;
    	
    }
    
    @FXML private void resetFilters(ActionEvent event) {
    	
    	
    	System.out.println(listView.getItems().size());
    	// Set Combo Box to null
    	specialtyFilter.setValue("");
    	specialtyFilter.setValue(null);
    	
    	
    	// Set array to unfiltered doctor items
    	listView.getItems().setAll(doctorItems);
    	
    	filteringSearchItems = false;
    	isSpecialisationSelected = false;
    	
    	// reset toggles to remove visual indication of radio button toggle
    	ratingToggle.getToggles().forEach(toggle -> {
    	    if (toggle.isSelected()) {
    	        toggle.setSelected(false);
    	    }
    	});
    	//textField.setText("");
    }
    
    /*
     * These functions set up the components on initialisation
     */
    
    // Set up the list view
    private void setupListView() {
    	
    	try {
  		  doctorItems.addAll(dbm.getAllDoctors(dbm.conn()));
  		  allDoctors.addAll(dbm.getAllDoctors(dbm.conn()));
    	}
    	catch(Exception e) {
  		  e.printStackTrace();
    	}
      
      listView.getItems().addAll(doctorItems);
      
      listView.setOnMouseClicked(event -> {
          Doctor selectedDoctor = listView.getSelectionModel().getSelectedItem();
          if (selectedDoctor != null) {
              navigateToDestination(selectedDoctor);
          }
      });
    }
    
    // Create a custom cell factory to display the doctor information
    private void setupCellFactory() {
    	
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

                    setGraphic(hbox);
                }
            }
    		
    	});
    	
    }
    
    // Add specialisations to combo box
    private void  setupComboBox() {

    	specialtyFilter.setPromptText("Specialty");
    	
    	// Create button cell to overcome bug where prompt text wouldnt reset when value is null
    	specialtyFilter.setButtonCell(new ListCell<String>() {
    	    @Override
    	    protected void updateItem(String item, boolean empty) {
    	        super.updateItem(item, empty);
    	        if (empty || item == null) {
    	            setText(specialtyFilter.getPromptText()); // Display prompt text
    	        } else {
    	            setText(item);
    	        }
    	    }
    	});
    	
    	try {
    		specialisations = dbm.getSpecialisations(dbm.conn());
    	}
    	catch(Exception e) {
    		  e.printStackTrace();
      	}
    	
    	specialtyFilter.getItems().addAll(specialisations);
    	
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
    
    // Initialiser for the controller
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    	setupListView();
    	setupCellFactory();
    	setupComboBox();
    
    }
    
}
