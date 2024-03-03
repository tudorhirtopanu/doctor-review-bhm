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
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.*;
import javafx.geometry.Pos;


// TODO: add option to clear text 
// TODO: instead of flags maybe use is value == null

public class HomeController implements Initializable {
	
	private DatabaseManager dbm = new DatabaseManager();

	private DoctorManager doctorManager = new DoctorManager();
	
	// FXML variables
	@FXML private TextField textField;
	
    @FXML private Button helloButton;
    @FXML private Button clearFiltersBtn;

    @FXML private ListView<Doctor> listView;
    @FXML private ListView<Doctor> topDoctorsList;
    
    @FXML private RadioButton sortRatingHighestBtn;
    
    @FXML private RadioButton sortRatingLowestBtn;
    
    @FXML private ToggleGroup ratingToggle;
    
    @FXML private ComboBox<String> specialtyFilter;
    @FXML private ComboBox<Integer> ratingFilter;
    
    // variables
    ArrayList<Doctor> allDoctors = new ArrayList<>(); // full list of doctors
    ArrayList<Doctor> doctorItems = new ArrayList<>(); // list of doctors being searched
    ArrayList<Doctor> filteredDoctorItems = new ArrayList<>(); // filtered list of doctors
    
    ArrayList<String> specialisations = new ArrayList<>();
    
    Doctor[] topDoctors = new Doctor[7];
    
    // flags
    private boolean filteringSearchItems = false;
    private boolean isSpecialisationSelected = false;
    private boolean isRatingSelected = false;
    
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
    	if(isSpecialisationSelected && !isRatingSelected) {
    		filteredDoctorItems = doctorManager.filterDoctorsBySpecialisation(doctorItems, specialtyFilter.getValue());
    		listView.getItems().setAll(filteredDoctorItems);
    	} 
    	// if a rating is selected then filter by that when searching
    	else if(isRatingSelected && !isSpecialisationSelected) {
    		filteredDoctorItems = doctorManager.filterDoctorsByRating(doctorItems, ratingFilter.getValue());
    		listView.getItems().setAll(filteredDoctorItems);
    	}
    	// both are selected so filter by both
    	else if(isRatingSelected && isSpecialisationSelected) {
    		filteredDoctorItems = doctorManager.filterDoctorsByRating(doctorItems, ratingFilter.getValue());
    		filteredDoctorItems = doctorManager.filterDoctorsBySpecialisation(filteredDoctorItems, specialtyFilter.getValue());
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
    
    @FXML private void handleFilters(ActionEvent event) {
    	
    	// if the selected combo box is specialty filter
    	if (event.getSource() == specialtyFilter) {
    		
    		// check if rating filter has a value as well
    		if (ratingFilter.getValue() != null) { 
        		
    			// if it does, get the values for both
        		int minRating = ratingFilter.getValue();
        		String specialisation = specialtyFilter.getValue();
        		
        		// filter the doctors based on rating
        		filteredDoctorItems = doctorManager.filterDoctorsByRating(doctorItems, minRating);
        		
        		// filter that array based on specialisation
        		filteredDoctorItems = doctorManager.filterDoctorsBySpecialisation(filteredDoctorItems, specialisation);
        		
        		// set flags to true
        		filteringSearchItems = true;
    	    	isSpecialisationSelected = true;
    	    	isRatingSelected = true;
    	    	
    	    	// set that as the list
    	    	listView.getItems().setAll(filteredDoctorItems); 
        	} else {
        		
        		// rating filter has no value so get value only for specialisation
        		String specialisation = specialtyFilter.getValue();
        		
        		// filter doctor items by specialisation
        		filteredDoctorItems = doctorManager.filterDoctorsBySpecialisation(doctorItems, specialisation);
        		
        		// set flags to true
        		filteringSearchItems = true;
    	    	isSpecialisationSelected = true;
    	    	
    	    	// set as list items
    	    	listView.getItems().setAll(filteredDoctorItems); 
        		
        	}
    		
    	// Check if selected combo box is rating filter
    	} else if (event.getSource() == ratingFilter) {
    		
    		// If specialty filter has a value as well
    		if (specialtyFilter.getValue() != null) { 

    			// get values for both combo boxes
    			int minRating = ratingFilter.getValue();
        		String specialisation = specialtyFilter.getValue();
        		
        		// sort first by specialisation
        		filteredDoctorItems = doctorManager.filterDoctorsBySpecialisation(doctorItems, specialisation);
        		
        		// sort that array by rating
        		filteredDoctorItems = doctorManager.filterDoctorsByRating(filteredDoctorItems, minRating);
        		
        		// set flags to true
        		filteringSearchItems = true;
    	    	isSpecialisationSelected = true;
    	    	isRatingSelected = true;
    	    	
    	    	// set as list items
    	    	listView.getItems().setAll(filteredDoctorItems); 
        	} else {
        		
        		if (ratingFilter.getValue() != null) {
	        		// specialisation has no value so get rating only
	        		int minRating = ratingFilter.getValue();
	        		
	        		// filter by that rating
	        		filteredDoctorItems = doctorManager.filterDoctorsByRating(doctorItems, minRating);
	        		
	        		// set flags to true
	        		filteringSearchItems = true;
	        		isRatingSelected = true;
	    	    	
	        		// set as list items
	    	    	listView.getItems().setAll(filteredDoctorItems); 
        		}
        		
        	}
    		
    	}
    	
    }
    
    @FXML private void resetFilters(ActionEvent event) {
    	
    	
    	System.out.println(listView.getItems().size());
    	// Set Combo Box to null
    	specialtyFilter.setValue("");
    	specialtyFilter.setValue(null);
    	
    	ratingFilter.setValue(0);
    	ratingFilter.setValue(null);
    	
    	
    	// Set array to unfiltered doctor items
    	listView.getItems().setAll(doctorItems);
    	
    	filteringSearchItems = false;
    	isSpecialisationSelected = false;
    	isRatingSelected = false;
    	
    	// reset toggles to remove visual indication of radio button toggle

    	ratingToggle.getToggles().forEach(toggle -> {
    	    if (toggle.isSelected()) {
    	        toggle.setSelected(false);
    	    }
    	});

    }
    
    /*
     * These functions set up the components on initialisation
     */
    
    // Set up the list view
    private void setupListView() {
    	
    	try {
    		
    		// Load all doctors
  		  doctorItems.addAll(dbm.getAllDoctors(dbm.conn()));
  		  allDoctors.addAll(dbm.getAllDoctors(dbm.conn()));
  		  
  		  topDoctors = doctorManager.returnTopDoctors(dbm.getAllDoctors(dbm.conn()), 7);
  		  
  		  System.out.println(topDoctors.length);
    	}
    	catch(Exception e) {
  		  e.printStackTrace();
    	}
      
      listView.getItems().addAll(doctorItems);
      topDoctorsList.getItems().addAll(doctorManager.returnTopDoctors(allDoctors, 7));
      
      listView.setOnMouseClicked(event -> {
          Doctor selectedDoctor = listView.getSelectionModel().getSelectedItem();
          if (selectedDoctor != null) {
              navigateToWriteReview(selectedDoctor);
          }
      });
      
      topDoctorsList.setOnMouseClicked(event -> {
          Doctor selectedDoctor = topDoctorsList.getSelectionModel().getSelectedItem();
          if (selectedDoctor != null) {
        	  navigateToViewReviews(selectedDoctor);
          }
      });
      
      
    }
    
    // Create a custom cell factory to display the doctor information
    private void setupCellFactory() {
    	
    	// Cell factory for all doctors list
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
    
    private void setupTopDoctorsCellFactory() {
    	
    	topDoctorsList.setCellFactory(param -> new ListCell<>() {
    		
    		HBox hbox = new HBox();
    		Label starLabel = new Label("★");
    		Text name = new Text();
    		Text specialty = new Text();
    		
    		{
    			VBox vbox = new VBox();
    			vbox.getChildren().addAll(name, specialty);
    			hbox.getChildren().addAll(starLabel, vbox);
    		}
    		
    		@Override
    		protected void updateItem(Doctor item, boolean empty) {
    			super.updateItem(item, empty);
    			
    			 if (empty || item == null) {
    	                setGraphic(null);
    	         } else {
    	        	 
    	        	 name.setText(item.getName());
    	             specialty.setText(item.getSpecialization());
    	             
    	             hbox.setSpacing(10);
                     hbox.setAlignment(Pos.CENTER_LEFT);
                     setGraphic(hbox);
    	        	 
    	         }
    		}
    		
    	});
    	
    }
    
    
    
    // Add specialisations to combo box
    private void  setupComboBox() {

    	specialtyFilter.setPromptText("Specialty");
    	ratingFilter.setPromptText("Rating");
    	
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
    	
    	ratingFilter.setButtonCell(new ListCell<Integer>() {
    	    @Override
    	    protected void updateItem(Integer item, boolean empty) {
    	        super.updateItem(item, empty);
    	        if (empty || item == null) {
    	            setText(ratingFilter.getPromptText()); // Display prompt text
    	        } else {
    	            setText(String.valueOf(item));
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
    	ratingFilter.getItems().addAll(0, 1, 2, 3, 4);
    	
    }
    
    private void navigateToWriteReview(Doctor doctor) {
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
    
    private void navigateToViewReviews(Doctor doctor) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uk/ac/brunel/views/ReviewList.fxml"));
            Parent root = loader.load();
            ViewReviewController destinationController = loader.getController();
            
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
    	setupTopDoctorsCellFactory();
    	setupCellFactory();
    	setupComboBox();
    
    }
    
}
