package de.randi2.model.exceptions;

/**
 * 
 * @author Lukasz Plotnicki [lplotni@stud.hs-heilbronn.de]
 * @version $Id: PatientException.java 2418 2007-05-04 14:37:12Z jthoenes $
 */
public class PatientException extends BenutzerException {
	
	/**
	 * 
	 * @param fehlermeldung
	 */
	public PatientException (String fehlermeldung) {
		super(fehlermeldung);
	}

}
