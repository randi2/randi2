package de.randi2.services;

public class ServiceException extends Exception {

	private static final long serialVersionUID = -8396210192890379230L;

	public ServiceException() {
		super();
	}
	
	public ServiceException(String msg){
		super(msg);
	}
}
