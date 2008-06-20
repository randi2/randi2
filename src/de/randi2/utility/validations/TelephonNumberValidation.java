package de.randi2.utility.validations;

import org.hibernate.validator.Validator;

import sun.security.jca.GetInstance.Instance;

public class TelephonNumberValidation implements Validator<TelephonNumber>{

	@Override
	public void initialize(TelephonNumber arg0) {
		
	}

	@Override
	public boolean isValid(Object telephonnumber) {
		String number =  (String) telephonnumber;
		if (number == null || number.trim().equals("")) {
			return true;
		}
		
		
		number = number.trim();

		String[] digits = number.split("[-/() \t.]");
		number = "";
		
		for (int i = 0; i < digits.length; i++) {
			number += digits[i].trim();
		}
		if (number.charAt(0) == '+') {
			number = number.substring(1, number.length());
		}	
		if(!number.matches("^\\d+$")){
			return false;
		}
		
		return true;
	}
}
