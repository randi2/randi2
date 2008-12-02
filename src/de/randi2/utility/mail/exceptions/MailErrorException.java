package de.randi2.utility.mail.exceptions;

/**
 * Generic exception for
 * 
 * @author Daniel Haehn <dh@randi2.de>
 *
 */
public class MailErrorException extends Exception {

	private String message;
	
	public MailErrorException(String message) {
		
		this.message = message;
		
	}
	
	public String toString() {
		
		return message;
		
	}
	
}
