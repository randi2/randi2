package de.randi2.model.exceptions;

/**
 * Kapselt die Exceptions zu StrataBean, StrataAuspraegung und der Fachklasse
 * Strata.
 * 
 * @author Johannes Thoenes [jthoenes@stud.hs-heilbronn.de]
 * @version $Id: StrataException.java 2418 2007-05-04 14:37:12Z jthoenes $
 */
public class StrataException extends BenutzerException {

	/**
	 * Falls die Liste der Auspreagungen leer ist.
	 */
	public static final String STRATA_AUSPRAEGUNGEN_LEER = "Es muss mindestens eine Auspraegung zu einem Strata existieren.";

	/**
	 * Falls der Name leer ist.
	 */
	public static final String STRATA_NAME_LEER = "Ein Strata muss einen Namen haben";

	/**
	 * Falls der Auspraegungsname leer ist.
	 */
	public static final String STRATA_AUSPRAEGUNG_NAME_LEER = "Eine Strata Auspraegung muss einen Namen haben";
	
	/**
	 * Fehlermeldung falls das Strata noch nicht in der DB gespeichert wurde.
	 */
	public static final String STRATA_BEAN_NICHT_GESPEICHERT= "Das &uuml;bergebene Srata wurde noch nicht gespeichert!";
	
	/**
	 * Fehlermeldung, wenn null an die setStudie() Methode uebergeben wurde.
	 */
	public static final String STUDIE_NULL="&Uuml;bergebene Studie ist null!";

	/**
	 * Konstruktor.
	 * 
	 * @param fehlermeldung
	 *            Eine der verfuegbaren Konstanten.
	 */
	public StrataException(String fehlermeldung) {
		super(fehlermeldung);
	}

}
