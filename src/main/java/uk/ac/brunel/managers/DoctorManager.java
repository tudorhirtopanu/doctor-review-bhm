package uk.ac.brunel.managers;

import java.util.ArrayList;
import uk.ac.brunel.models.Doctor;

public class DoctorManager {

	/*
		NOTE: This function assumes that doctor names follow the format "Dr. FirstName LastName".
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
