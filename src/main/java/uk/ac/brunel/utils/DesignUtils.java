package uk.ac.brunel.utils;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DesignUtils {

	// Set a drop shadow to rectangle element
	public static void setDropShadow(Rectangle rect) {
		String hexColor = "#E7E7EB"; 
	     Color color = Color.web(hexColor);
		 
		 DropShadow dropShadow = new DropShadow();
		 
		 // Set the properties of the drop shadow
	     dropShadow.setColor(color); 
	     dropShadow.setRadius(20); 
	     dropShadow.setOffsetX(5); 
	     dropShadow.setOffsetY(5); 

	     // Apply the DropShadow effect to the Rectangle
	     rect.setEffect(dropShadow);
	}
	
}
