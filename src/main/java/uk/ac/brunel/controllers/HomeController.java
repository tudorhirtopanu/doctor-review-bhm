package uk.ac.brunel.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import uk.ac.brunel.managers.DatabaseManager;
import uk.ac.brunel.models.Doctor;

import java.net.URL;
import java.util.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.layout.StackPane;



public class HomeController implements Initializable {
	
	DatabaseManager dbm = new DatabaseManager();

	
	// FXML variables
    @FXML
    private Button helloButton;

    @FXML
    private ListView<Doctor> listView;
    
    // variables
    ArrayList<Doctor> items = new ArrayList<>();
    
    // methods
    
    @FXML
    private void handleButtonClick(ActionEvent event) {
        System.out.println("Hello, World!");
        
        try {
            dbm.conn(); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
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
        
        // Set cell factory to display name property
        listView.setCellFactory(new Callback<ListView<Doctor>, ListCell<Doctor>>() {
        	
            @Override
            public ListCell<Doctor> call(ListView<Doctor> param) {
                return new ListCell<Doctor>() {
                	
                    @Override
                    protected void updateItem(Doctor item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            setText(item.getName());
                        } else {
                            setText(null);
                        }
                    }
                    
                };
                
            }
            
        });
    	
        
        
    }
    
    private void navigateToDestination(Doctor doctor) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uk/ac/brunel/views/DoctorReview.fxml"));
            Parent root = loader.load();
            DoctorReviewController destinationController = loader.getController();
            
            // Pass data to the destination controller if needed
            destinationController.initData(doctor);
            
            StackPane rootPane = (StackPane) helloButton.getScene().getRoot();
            rootPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
