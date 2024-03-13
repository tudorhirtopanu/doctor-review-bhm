package uk.ac.brunel.utils;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TextFieldUtils {

	public enum CharLimit {
	    NAME(25),
	    TITLE(25),
	    TEXT(350);

	    private final int charCount;

	    // Constructor
	    private CharLimit(int charCount) {
	        this.charCount = charCount;
	    }

	    // Method to return display name
	    public int getCharCount() {
	        return charCount;
	    }
	}
	
	public static void limitCharacters(TextArea textArea, CharLimit limit) {
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > limit.getCharCount()) {
                textArea.setText(oldValue); 
            }
            
            String filteredValue = newValue.replaceAll("[^a-zA-Z0-9,.?! ]", ""); 
            if (!filteredValue.equals(newValue)) {
                textArea.setText(filteredValue);
            }

        });
    }
	
	public static void limitTextFieldCharacters(TextField textField, CharLimit limit) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > limit.getCharCount()) {
                textField.setText(oldValue); 
            }

            String filteredValue = newValue.replaceAll("[^a-zA-Z0-9,.?! ]", ""); 
            if (!filteredValue.equals(newValue)) {
                textField.setText(filteredValue);
            }
        });
    }
	
}
