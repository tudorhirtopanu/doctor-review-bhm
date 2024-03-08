package uk.ac.brunel.managers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateManager {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMMM yyyy");

    public static String getCurrentDateFormatted() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.format(FORMATTER);
    }
	
}
