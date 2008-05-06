package com.myicetest.models.exceptions;

public class UserException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public enum Messages{
		 
		NOT_FOUND("User not found!"),
		
		WRONG_LOGIN("Login/Pass wrong!");

		
		private String msg = "";

		private Messages(String msg) {
			this.msg = msg;
		}

		public String toString() {
			return this.msg;
		}
	};
	
	public UserException(Messages msg){
		super(msg.toString());
	}
}
