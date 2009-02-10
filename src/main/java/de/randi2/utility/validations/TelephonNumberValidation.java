package de.randi2.utility.validations;

import org.hibernate.validator.Validator;

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
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < digits.length; i++) {
			buf.append(digits[i].trim());
		}
		number = buf.toString();
		if (number.charAt(0) == '+') {
			number = number.substring(1, number.length());
		}	
		if(!number.matches("^\\d+$")){
			return false;
		}
		
		return true;
	}
}
