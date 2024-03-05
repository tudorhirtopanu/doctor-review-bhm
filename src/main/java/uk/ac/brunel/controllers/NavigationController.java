package uk.ac.brunel.controllers;

import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class NavigationController {
	
	private StackPane rootPane;
	
	public NavigationController(StackPane rootPane) {
		this.rootPane = rootPane;
	}
	
	public void navigate(String fxmlPath) {
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            rootPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
}
