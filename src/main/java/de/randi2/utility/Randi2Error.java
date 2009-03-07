package de.randi2.utility;

public class Randi2Error extends Error{
	
	private static final long serialVersionUID = -2207931640308250401L;

	public Randi2Error(String msg){
		super(msg);
	}
	
	public Randi2Error(Throwable cause){
		initCause(cause);
	}
}
