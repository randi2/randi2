package de.randi2.model.exceptions;

/**
 * 
 * @author Lukasz Plotnicki [lplotni@stud.hs-heilbronn.de]
 * @version $Id$
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
