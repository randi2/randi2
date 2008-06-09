package de.randi2.jsf;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Randi2 {
	
	public static String SUCCESS = "success";
	
	public static String ERROR = "error";
	
	public static void showMessage(Exception e){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getLocalizedMessage(),e.toString()));
	}
	
	public static void showMessage(String message){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,message,null));
	}

}
