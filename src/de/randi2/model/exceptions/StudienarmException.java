package de.randi2.model.exceptions;

/**
 * 
 * @author Lukasz Plotnicki [lplotni@stud.hs-heilbronn.de]
 * @version $Id$
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
