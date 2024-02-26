package uk.ac.brunel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.util.*;


public class Controller implements Initializable {

    @FXML
    private Button helloButton;

    @FXML
    private ListView<Doctor> listView;
    
    @FXML
    private void handleButtonClick(ActionEvent event) {
        System.out.println("Hello, World!");
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	// Create list of items
    	List<Doctor> items = new ArrayList<>();
        items.add(new Doctor(0, "Dr Mia Carte", "Vascular Surgery", 4.9, 45));
        
        listView.getItems().addAll(items);
        
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
    
}
