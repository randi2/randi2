package de.randi2.model.exceptions;

/**
 * <p>
 * Diese Klasse kappselt die Fehler, die innerhalb der Klasse Benutzerkonto
 * auftreten koennen.
 * </p>
 * 
 * @author Lukasz Plotnicki [lplotni@stud.hs-heilbronn.de]
 * @version $Id: BenutzerkontoException.java 2426 2007-05-06 17:34:32Z twillert $
 * 
 */
public class BenutzerkontoException extends BenutzerException {

	/*
	 * Die einzelnen Konstanten werde sich auch in Laufe der Arbeit ergeben.
	 */

	/**
	 * Diese Konstante wird uebergeben, wenn der Benutzername fehlt.
	 */
	public static final String BENUTZERNAME_FEHLT = "Bitte Benutzername eingeben.";

	/**
	 * Diese Konstante wird uebergeben, wenn das Passwort fehlt.
	 */
	public static final String PASSWORT_FEHLT = "Bitte Passwort eingeben.";

	/**
	 * Fehlermeldung, wenn das uebergebene Passwort zu kurz ist.
	 */
	public static final String PASSWORT_ZU_KURZ = "Das eingegebene Passwort ist zu kurz (Min. 6 Zeichen)!";

	/**
	 * Fehlermeldung, wenn das uebergebene PersonObjekt noch nicht in der DB gepeichert wurde
	 */
	public static final String FK_PERSON_NICHT_GESPEICHERT = "Das &uuml;bergebene PersonObjekt wurde noch nicht gespeichert!";

	/**
	 * Fehlermeldung, wenn das uebergebene Zentrumbean noch in der Datenbank
	 * nicht gespeichert wurde.
	 */
	public static final String ZENTRUM_NICHT_GESPEICHERT = "Das &uuml;bergebene Zentrum wurde noch nicht gespeichert!";

	/**
	 * Fehlermeldung, wenn null an die setZentrum() Methode uebergeben wurde.
	 */
	public static final String ZENTRUM_NULL = "Es wurde kein Zentrum &uuml;bergeben!";

	/**
	 * Fehlermeldung, wenn null an die setBenutzer() Methode uebergeben wurde.
	 */
	public static final String BENUTZER_NULL = "Es wurde kein Benutzer &uuml;bergeben!";

	/**
	 * Fehlermeldung, wenn die Rolle nicht uebergeben wurde.
	 */
	public static final String ROLLE_FEHLT = "Die Rolle des Benutzers wurde nicht &uuml;bergeben!";

	/**
	 * Fehlermeldung, wenn das uebergebene Passwort den Anspruechen nicht
	 * genuegt.
	 */
	public static final String PASSWORT_FALSCH = "Das Passwort entspricht nicht den Vorgaben!\n(Mind. ein Sonderzeichen und eine Ziffer)";

	/**
	 * Fehlermeldung, wenn das uebergebene Datum in der Zukunft liegt.
	 */
	public static final String DATUM_IN_DER_ZUKUNFT = "Das &uuml;bergebene Datum liegt in der Zukunft!";

	/**
	 * Diese Konstante wird uebergeben, wenn der Benutzername zu kurz ist.
	 */
	public static final String BENUTZERNAME_ZU_KURZ = "Der eingegebene Benutzername ist zu kurz (min. 6 Zeichen)";

	/**
	 * Diese Konstante wird uebergeben, wenn der Benutzername ungueltige Zeichen
	 * enthaelt.
	 */
	public static final String BENUTZERNAME_ENTHAELT_UNGUELTIGE_ZEICHEN = "Den eingegebene Benutzername enthaelt ungueltige Zeichen";

	/**
	 * Diese Konstante wird uebergeben, wenn der Benutzername zu lang ist.
	 */
	public static final String BENUTZERNAME_ZU_LANG = "Der eingegebene Benutzername ist zu lang (max. 50 Zeichen)";

	/**
	 * Ein Konstruktor dieser Klasse
	 * 
	 * @param arg0
	 *            eine Konstante aus dieser Klasse als Message
	 */
	public BenutzerkontoException(String arg0) {
		super(arg0);
	}

}
