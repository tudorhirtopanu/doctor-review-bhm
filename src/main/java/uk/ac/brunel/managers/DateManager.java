package uk.ac.brunel.managers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *  Class for managing dates and date formatting.
 */

public class DateManager {

	// Date formatter pattern for formatting dates
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMMM yyyy");

	// Retrieves the current date formatted according to the specified date formatter pattern.
    public static String getCurrentDateFormatted() {
    	
    	// Get the current date
        LocalDate currentDate = LocalDate.now();
        
        // Format the current date using the specified formatter
        return currentDate.format(FORMATTER);
    }
	
}
