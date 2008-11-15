package de.randi2.model.exceptions;

import org.hibernate.validator.InvalidValue;

public class ValidationException extends RuntimeException {

	private InvalidValue[] invalids;
	private String[] messages;

	public ValidationException(InvalidValue[] _invalids) {
		this.invalids = _invalids;
	}
	
	public String[] getMessages(){
		if(messages == null){
			messages = new String[invalids.length];
			for(int i = 0; i< messages.length; i++){
				messages[i] = invalids[i].getMessage();
			}
		}
		return messages;
	}
	
	public InvalidValue[] getInvalids(){
		return this.invalids;
	}

}
