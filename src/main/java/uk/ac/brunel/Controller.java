package uk.ac.brunel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class Controller {

    @FXML
    private Button helloButton;

    @FXML
    private void handleButtonClick(ActionEvent event) {
        System.out.println("Hello, World!");
    }
    
}
