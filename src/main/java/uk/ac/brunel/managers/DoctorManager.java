package uk.ac.brunel.managers;

import java.util.*;

import uk.ac.brunel.models.Doctor;

public class DoctorManager {

	public enum SortOrder {
		ASCENDING,
		DESCENDING,
	}
	
	// Sort doctors in order by rating, order depending on the enum case
	public ArrayList<Doctor> sortDoctorsByRating(ArrayList<Doctor> doctors, SortOrder sortOrder ) {
		
		// Define a comparator based on the review rating of the doctor objects
		Comparator<Doctor> comparator = Comparator.comparingDouble(doctor -> doctor.getReviewRating());
		
		// If the enum case is DESCENDING then reverse comparator
		if (sortOrder == SortOrder.DESCENDING) {
			comparator = comparator.reversed();
		}
		
		// Sort arraylist using comparator
		doctors.sort(comparator);
		
		return doctors;
		
	}
	
	/*
	 * NOTE: This function assumes that doctor names follow the format "Dr. FirstName LastName".
	 * 
	 * This function returns doctors based on partial input
	 * This is customised so if a user searches "Mi", it returns doctors whose first and/or last names start with "Mi", not just contain those letters
	 * 
	 */
	public ArrayList<Doctor> filterDoctorByName(ArrayList<Doctor> doctors, String inputText) /*throws CustomExceptions*/ {
		
		ArrayList<Doctor> filteredDoctors = new ArrayList<Doctor>();
		
		// use "\\s+" to account for one or more whitespaces
		String[] splitInput = inputText.trim().toLowerCase().split("\\s+");
	
		
		for(int i = 0; i<doctors.size(); i++) {
			
			Doctor doctor = doctors.get(i);
			
			String[] splitString = doctor.getName().toLowerCase().split(" ");
			
			if (splitInput.length > splitString.length) {
				System.out.println("invalid string");
				//throw new CustomExceptions("Invalid string: User input is longer than the doctor's name.");
			}
			
			// If the user has typed more than one word
			if(splitInput.length > 1) {
				
				// If the first word is Dr
				if (splitInput[0].equals("dr") || splitInput[0].equals("dr.")) {
					
					// filter starting from the next word
					if(splitInput.length-1 == 1) {
						
						// If there is only one word after doctor, check first name and surname
						for(int j = 1; j<splitString.length; j++) {
							if(splitString[j].startsWith(splitInput[1])) {
								filteredDoctors.add(doctor);
								break;
							}
						}
					} else {
						
						for(int j = 1; j < splitInput.length; j++) {
							
							if(splitString[j].startsWith(splitInput[j])) {
								filteredDoctors.add(doctor);
								break;
							}
						}
					}
				}
				// first word isn't Dr. 
				else {
					// filter from the start
					for(int j = 0; j < splitInput.length; j++) {
						if(splitString[j+1].startsWith(splitInput[j])) {
							filteredDoctors.add(doctor);
							break;
						}					
					}
				}	
			} 
			// If user has only typed one word
			else {
				for(int j = 0; j<splitString.length; j++) {
					if (splitString[j].startsWith(inputText.toLowerCase())){
						filteredDoctors.add(doctor);
						break;
					}
					
				}
			}
		}
		return filteredDoctors;
	}
	
}
