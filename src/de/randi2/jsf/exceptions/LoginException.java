package de.randi2.jsf.exceptions;

public class LoginException extends Exception{
	
	private static final long serialVersionUID = 6485662397573250500L;
	
	public static String LOGIN_PASS_INCORRECT = "Wrong Login/Password!";
	
	public LoginException(String message){
		super(message);
	}

}
