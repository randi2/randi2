package de.randi2.model.exceptions;

/**
 * 
 * @author Lukasz Plotnicki [lplotni@stud.hs-heilbronn.de]
 * @version $Id: StrataException.java 2418 2007-05-04 14:37:12Z jthoenes $
 */
public class StrataException extends BenutzerException {
	
	/**
	 * 
	 * @param fehlermeldung
	 */
	public StrataException(String fehlermeldung) {
		super(fehlermeldung);
	}

}
