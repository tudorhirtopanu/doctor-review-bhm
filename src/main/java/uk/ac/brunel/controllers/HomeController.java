package uk.ac.brunel.controllers;

// JavaFX imports
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.scene.text.Font;

//Java Standard Library Imports
import java.io.IOException;
import java.net.URL;
import java.util.*;

//Application specific imports
import uk.ac.brunel.managers.DatabaseManager;
import uk.ac.brunel.models.Doctor;
import uk.ac.brunel.managers.DoctorManager;
import uk.ac.brunel.managers.DoctorManager.SortOrder;
import uk.ac.brunel.models.UserSession;

// TODO: add option to clear text 
// TODO: instead of flags maybe use is value == null
// TODO: allow users to switch action between writing review and viewing review

public class HomeController implements Initializable {
	
	private UserSession userSession = UserSession.getInstance("John Doe");
	private DatabaseManager dbm = new DatabaseManager();
	private DoctorManager doctorManager = new DoctorManager();
	
	// FXML variables
	@FXML private TextField textField;
    @FXML private Button searchButton;
    @FXML private Button clearFiltersBtn;
    @FXML private ImageView brunelLogo;
    @FXML private Image logoImage = new Image(getClass().getResourceAsStream("/brunel_logo.png"));
    @FXML private ListView<Doctor> listView;
    @FXML private ListView<Doctor> topDoctorsList;
    @FXML private ListView<Doctor> recentlySeenDoctorsList;
    
    @FXML private RadioButton sortRatingHighestBtn;
    @FXML private RadioButton sortRatingLowestBtn;
    @FXML private ToggleGroup ratingToggle;    
    
    @FXML private RadioButton selectionModeWrite;
    @FXML private RadioButton selectionModeView;
    @FXML private ToggleGroup selectionMode;
    
    @FXML private ComboBox<String> specialtyFilter;
    @FXML private ComboBox<Integer> ratingFilter;
    @FXML private Label noSearchResultsLabel;
    
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
    	
    	// Get the search text
    	String text = textField.getText();
    	
    	// Filter doctors by name
    	doctorItems = doctorManager.filterDoctorByName(allDoctors, text);
    	
    	if(doctorItems.size() == 0) {
        	noSearchResultsLabel.setVisible(true);
        } else {
        	noSearchResultsLabel.setVisible(false);
        }
    	
    	// Sort doctors by rating if selected
    	if(sortRatingHighestBtn.isSelected()) {
    		doctorItems = doctorManager.sortDoctorsByRating(doctorItems, SortOrder.DESCENDING);
    	}
    	else if (sortRatingLowestBtn.isSelected()) {
    		doctorItems = doctorManager.sortDoctorsByRating(doctorItems, SortOrder.ASCENDING);
    	}
    	// Filter doctors by specialisation if selected
    	if(isSpecialisationSelected && !isRatingSelected) {
    		filteredDoctorItems = doctorManager.filterDoctorsBySpecialisation(doctorItems, specialtyFilter.getValue());
    		listView.getItems().setAll(filteredDoctorItems);
    	} 
    	// Filter doctors by rating if selected
    	else if(isRatingSelected && !isSpecialisationSelected) {
    		filteredDoctorItems = doctorManager.filterDoctorsByRating(doctorItems, ratingFilter.getValue());
    		listView.getItems().setAll(filteredDoctorItems);
    	}
    	// Filter by rating and specialisation if selected
    	else if(isRatingSelected && isSpecialisationSelected) {
    		filteredDoctorItems = doctorManager.filterDoctorsByRating(doctorItems, ratingFilter.getValue());
    		filteredDoctorItems = doctorManager.filterDoctorsBySpecialisation(filteredDoctorItems, specialtyFilter.getValue());
    		listView.getItems().setAll(filteredDoctorItems);
    		System.out.println("5");
    	}
    	// Set a new list of doctors
    	else {
        	listView.getItems().setAll(doctorItems);
    	}

    }
    
    // Sort the array of doctors based on which button has been clicked
    @FXML private void handleSortButtonClicked(ActionEvent event) {
    	
    	// Check if sort by highest button is selected
    	if(sortRatingHighestBtn.isSelected()) {
    		
    		// Sort doctors by rating in descending order
    		if(!filteringSearchItems) {
    			doctorItems = doctorManager.sortDoctorsByRating(doctorItems, SortOrder.DESCENDING);
        		
        		listView.getItems().setAll(doctorItems);
    		} else {
    			filteredDoctorItems = doctorManager.sortDoctorsByRating(filteredDoctorItems, SortOrder.DESCENDING);
        		
        		listView.getItems().setAll(filteredDoctorItems);
    		}

    	} 
    	// Check if sort by lowest rating is selected
    	else if (sortRatingLowestBtn.isSelected()) {
    		
    		// Sort doctors in ascending order
    		if(!filteringSearchItems) {
    			doctorItems = doctorManager.sortDoctorsByRating(doctorItems, SortOrder.ASCENDING);
        		
        		listView.getItems().setAll(doctorItems);
    		} else {
    			filteredDoctorItems = doctorManager.sortDoctorsByRating(filteredDoctorItems, SortOrder.ASCENDING);
        		
        		listView.getItems().setAll(filteredDoctorItems);
    		}
    		
    	}
    	
    }
    
    private void applySpecialisationFilter() {
    	
    	// Get the selected specialisation from the filter
    	String specialisation = specialtyFilter.getValue();
    	
    	// Filter doctors by specialisation
    	filteredDoctorItems = doctorManager.filterDoctorsBySpecialisation(doctorItems, specialisation);

    	// Apply rating filter if it's selected
        if (ratingFilter.getValue() != null) {
            int minRating = ratingFilter.getValue();
            filteredDoctorItems = doctorManager.filterDoctorsByRating(filteredDoctorItems, minRating);
        }
        
        // Update the list view with filtered results
        updateListView();
    	
    }
    
    private void applyRatingFilter() {
    	
    	// Get the selected minimum rating from the filter
        int minRating = ratingFilter.getValue();
        
        // Filter doctors by rating
        filteredDoctorItems = doctorManager.filterDoctorsByRating(doctorItems, minRating);

        // Apply specialisation filter if it's selected
        if (specialtyFilter.getValue() != null) {
            String specialisation = specialtyFilter.getValue();
            filteredDoctorItems = doctorManager.filterDoctorsBySpecialisation(filteredDoctorItems, specialisation);
        }

        // Update the list view with filtered results
        updateListView();
    }
    
    private void updateListView() {
    	
    	// Set the list view items with filtered results
        listView.getItems().setAll(filteredDoctorItems);
        
        // If there are no items then set the label visibility to true
        if(filteredDoctorItems.size() == 0) {
        	noSearchResultsLabel.setVisible(true);
        } else {
        	noSearchResultsLabel.setVisible(false);
        }
        
        // Update flags 
        filteringSearchItems = true;
        isSpecialisationSelected = specialtyFilter.getValue() != null;
        isRatingSelected = ratingFilter.getValue() != null;
        
        // sort the filtered doctors by rating
        sortFilteredDoctorsByRating();
    }
    
    @FXML private void handleFilters(ActionEvent event) {
    	
    	// Apply filters based on the source of the event
        if (event.getSource() == specialtyFilter) {
            applySpecialisationFilter();
        } else if (event.getSource() == ratingFilter) {
            applyRatingFilter();
        }
    }
    
    private void sortFilteredDoctorsByRating() {
    	
    	// Sort filtered doctors by rating based on selected sorting button
    	if(sortRatingHighestBtn.isSelected()) {
    		filteredDoctorItems = doctorManager.sortDoctorsByRating(filteredDoctorItems, SortOrder.DESCENDING);
    	} else if(sortRatingLowestBtn.isSelected()) {
    		filteredDoctorItems = doctorManager.sortDoctorsByRating(filteredDoctorItems, SortOrder.ASCENDING);
    	}
    }
    
    // Reset filters and values
    @FXML private void resetFilters(ActionEvent event) {

    	specialtyFilter.setValue("");
    	specialtyFilter.setValue(null);
    	
    	if(ratingFilter.getValue() != null) {
    		ratingFilter.setValue(-1);
    	}
    	
    	// Set array to unfiltered doctor items
    	listView.getItems().setAll(doctorItems);
    	
    	if(doctorItems.size() != 0) {
    		// set label visibility to false again
        	noSearchResultsLabel.setVisible(false);
    	}
    	
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

    ////////////////////////// Functions to set up components on initialisation ////////////////////////////////////
    
    // Set up the list view
    private void setupListView() {
    	try {
    		
    		// Load all doctors
  		  doctorItems.addAll(dbm.getAllDoctors(dbm.conn()));
  		  allDoctors.addAll(dbm.getAllDoctors(dbm.conn()));
  		  
  		  topDoctors = doctorManager.returnTopDoctors(dbm.getAllDoctors(dbm.conn()), 7);
  		  
    	}
    	catch(Exception e) {
  		  e.printStackTrace();
  		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("An Error Has Occured");
		alert.setContentText("Sorry, we're unable to load the database at the moment. Please try again later or contact support for assistance.");

		alert.showAndWait();
    	}
      
      listView.getItems().addAll(doctorItems);
      topDoctorsList.getItems().addAll(doctorManager.returnTopDoctors(allDoctors, 7));
      recentlySeenDoctorsList.getItems().addAll(userSession.getRecentDoctors());
      
      listView.setOnMouseClicked(event -> {
          Doctor selectedDoctor = listView.getSelectionModel().getSelectedItem();
          if (selectedDoctor != null) {
        	  
        	  // If selected mode is Write Reviews
        	  if(selectionModeWrite.isSelected() == true) {
        		  
        		  boolean hasSeenDoctor = false;
            	  int[] doctorIDList = userSession.getDoctorIDList();
            	  // Check if id matches recently seen doctor id

            	  for(int i = 0; i<userSession.getDoctorIDList().length; i++) {
            		  
            		  if(doctorIDList[i] == selectedDoctor.getID()) {
            			  hasSeenDoctor = true;
            			  break;
            		  }
            	  }
            	  
            	  if (hasSeenDoctor) {
            		  navigateToWriteReview(selectedDoctor);
            	  } else {
            		  
            		// If user has not seen doctor then show alert instead
            		Alert alert = new Alert(AlertType.ERROR);
              		alert.setTitle("Error");
              		alert.setHeaderText("Can't Review Doctor");
              		alert.setContentText("You cannot select this doctor to review as you have not visited them.");
              		alert.showAndWait();
            	  } 
            	  
        	  } else if (selectionModeView.isSelected() == true) {
        		  navigateToViewReviews(selectedDoctor);
        	  }
        	  
        	  
          }
      });
      
      recentlySeenDoctorsList.setOnMouseClicked(event -> {
          Doctor selectedDoctor = recentlySeenDoctorsList.getSelectionModel().getSelectedItem();
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
                	
                	// if cell is empty, do not highlight or have hand cursor
                	setStyle("-fx-background-color: transparent; -fx-cursor: default;");
                    setGraphic(null);
                    
                } else {
                	
                    // Update the content of the HBox based on the Doctor object
                    name.setText(item.getName());
                    specialty.setText(item.getSpecialization());
                    reviewNo.setText(Integer.toString(item.getTotalReviews()));
                    rating.setText(Double.toString(item.getReviewRating()));
                    
                    name.setWrappingWidth(200);
                    specialty.setWrappingWidth(260);
                    reviewNo.setWrappingWidth(100);
                    rating.setWrappingWidth(100);
                    
                    listView.setStyle("-fx-background-color: transparent;");
                    setStyle("");
                    setGraphic(hbox);
                }
            }
    	});
    	
    }
    
    private void setupRecentDoctorsFactory() {
    	
    	// Cell factory for all doctors list
    	recentlySeenDoctorsList.setCellFactory(param -> new ListCell<>() {
    		
    		// Create HBox for each cell
        	private final HBox hbox = new HBox();
        	Text name = new Text();

    		// Instance initialiser block
        	{
        		// Create text nodes to display properties of doctor        		
        		hbox.getChildren().addAll(name);
        		
        	}
        	
        	@Override
            protected void updateItem(Doctor item, boolean empty) {
                super.updateItem(item, empty);
                
                if (empty || item == null) {
                	
                	// if cell is empty, do not highlight or have hand cursor
                	setStyle("-fx-background-color: transparent; -fx-cursor: default;");
                    setGraphic(null);
                    
                } else {
                	
                    // Update the content of the HBox based on the Doctor object
                    name.setText(item.getName());
                    name.setWrappingWidth(150);
                    
                    recentlySeenDoctorsList.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                    setStyle("");
                    setGraphic(hbox);
                }
            }
    	});
    }
    
    private void setupTopDoctorsCellFactory() {
    	
    	topDoctorsList.setCellFactory(param -> new ListCell<>() {
    	    
    	    HBox hbox = new HBox();
    	    Label starLabel = new Label();
    	    
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
    	            // Set the text and style class for the starLabel
    	        	Image image = new Image("/star_fill.png");
    	            ImageView starImageView = new ImageView(image);
    	            
    	            starImageView.setFitWidth(20);
    	            starImageView.setFitHeight(20);
    	            
    	            starLabel.setGraphic(starImageView);
    	            
    	            name.setText(item.getName());
    	            specialty.setText(item.getSpecialization());
    	            
    	            name.setFont(Font.font("Arial", 12));
    	            specialty.setFont(Font.font("Arial", 10));
    	            specialty.setOpacity(0.6);
    	            
    	            topDoctorsList.setStyle("-fx-background-color: transparent;");
    	            
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
    	        if (empty || item == null || item == -1) {
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
    
    // Functions to navigate to views
    
    private void navigateToWriteReview(Doctor doctor) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uk/ac/brunel/views/DoctorReview.fxml"));
            Parent root = loader.load();
            DoctorReviewController destinationController = loader.getController();
            
            destinationController.initData(doctor);
            
            StackPane rootPane = (StackPane) searchButton.getScene().getRoot();
            rootPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void navigateToViewReviews(Doctor doctor) {
        try {
        	
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uk/ac/brunel/views/ReviewList.fxml"));
            
            ViewReviewController destinationController = new ViewReviewController(doctor);
            
            loader.setController(destinationController);
            Parent root = loader.load();

            destinationController.initData(doctor);
            
            StackPane rootPane = (StackPane) searchButton.getScene().getRoot();
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
    	setupRecentDoctorsFactory();
    	setupComboBox();
    	
    	// Set logo image 
    	brunelLogo.setImage(logoImage);
    	
    	// Make label visibility false when view loads
    	noSearchResultsLabel.setVisible(false);
    	
    	// By default set selectionModeWrite to true
    	selectionModeWrite.setSelected(true);
    
    }
    
}
