package de.randi2.utility;

public class Randi2Error extends Error{
	public Randi2Error(Throwable cause){
		initCause(cause);
	}
}
