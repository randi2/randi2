package de.randi2.model.exceptions;

/**
 * 
 * @author Lukasz Plotnicki [lplotni@stud.hs-heilbronn.de]
 * @version $Id: StrataException.java 2418 2007-05-04 14:37:12Z jthoenes $
 */
public class StrataException extends BenutzerException {

	public static final String STRATA_AUSPRAEGUNGEN_LEER = "Es muss mindestens eine Auspraegung zu einem Strata existieren.";

	public static final String STRATA_NAME_LEER = "Ein Strata muss einen Namen haben";

	/**
	 * 
	 * @param fehlermeldung
	 */
	public StrataException(String fehlermeldung) {
		super(fehlermeldung);
	}

}
