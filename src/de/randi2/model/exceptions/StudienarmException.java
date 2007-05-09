package de.randi2.model.exceptions;

/**
 * 
 * @author Lukasz Plotnicki [lplotni@stud.hs-heilbronn.de]
 * @version $Id: StudienarmException.java 2418 2007-05-04 14:37:12Z jthoenes $
 */
public class StudienarmException extends BenutzerException {

	/**
	 * 
	 * @param fehlermeldung
	 */
	public StudienarmException(String fehlermeldung) {
		super(fehlermeldung);
	}
}
