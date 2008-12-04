package de.randi2.utility.validations;

import org.hibernate.validator.Validator;

public class PasswordValidator implements Validator<Password>{

	private int min;
	private int max;
	private int hash_length;
	
	@Override
	public void initialize(Password parameters) {
		min = parameters.min();
		max = parameters.max();
		hash_length = parameters.hash_length();
		
	}

	@Override
	public boolean isValid(Object password) {
		if(password==null)	return false;
		
		String pass = (String) password;
		if(pass.length()<= max && pass.length()>= min){
			String pLetter = ".*[A-Za-z].*";
			String pNumber = ".*[0-9].*";
			String pSpecialSign = ".*[-\\[\\]\\^,.#+;:_'*!\"§$%&/()=?|<>\\\\].*";
			return pass.matches(pLetter) && pass.matches(pNumber)
					&& pass.matches(pSpecialSign);			
		}else if(pass.length()==hash_length) return true;
		
		
		return false;
	}



}
