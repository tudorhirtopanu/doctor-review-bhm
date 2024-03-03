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
	
	// Filter doctors based on specialisation
	public ArrayList<Doctor> filterDoctorsBySpecialisation(ArrayList<Doctor> doctors, String specialisation) {
		
		ArrayList<Doctor> filteredList = new ArrayList<Doctor>();
		
		for(int i = 0; i< doctors.size(); i++) {
			
			Doctor doctor = doctors.get(i);
			
			if(doctor.getSpecialization().equals(specialisation)) {
				filteredList.add(doctor);
			}
			
		}

		return filteredList;
	}
	
	public ArrayList<Doctor> filterDoctorsByRating(ArrayList<Doctor> doctors, double minReviewRating) {
		
		// Store doctors in array list as the final length of list is unknown
		ArrayList<Doctor> filteredList = new ArrayList<Doctor>();
		
		for(int i = 0; i< doctors.size(); i++) {
			
			Doctor doctor = doctors.get(i);
			
			if(doctor.getReviewRating()>= minReviewRating) {
				filteredList.add(doctor);
			}
			
		}
		
		return filteredList;
	}
	
	public Doctor[] returnTopDoctors(ArrayList<Doctor> doctors, int numToReturn) {
		
		// Handle invalid numToReturn
		if (numToReturn <= 0 || numToReturn > doctors.size()) {
	        return new Doctor[0];
	    }
		
		ArrayList<Doctor> sortedDoctors = sortDoctorsByRating(doctors, SortOrder.DESCENDING);
		
		// Create a new array to store the top doctors
	    Doctor[] topDoctors = new Doctor[numToReturn];
	    
	    // Copy the top doctors from sortedDoctors to topDoctors
	    for (int i = 0; i < numToReturn; i++) {
	        topDoctors[i] = sortedDoctors.get(i);
	    }
		
		return topDoctors;
		
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
						if(splitString[1].startsWith(splitInput[1]) && splitString[2].startsWith(splitInput[2])) {
							filteredDoctors.add(doctor);
						}
					}
				}
				// first word isn't Dr. 
				else {
					// return doctors whos first name starts with first input word and whose second name match second input word
					if(splitString[1].startsWith(splitInput[0]) && splitString[2].startsWith(splitInput[1])) {
						filteredDoctors.add(doctor);
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
