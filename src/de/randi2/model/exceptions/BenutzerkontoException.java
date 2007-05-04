package de.randi2.model.exceptions;

/**
 * <p>
 * Diese Klasse kappselt die Fehler, die innerhalb der Klasse Benutzerkonto
 * auftreten koennen.
 * </p>
 * 
 * @author Lukasz Plotnicki [lplotni@stud.hs-heilbronn.de]
 * @version $Id$
 * 
 */
public class BenutzerkontoException extends Randi2Exception {

	/*
	 * Die einzelnen Konstanten werde sich auch in Laufe der Arbeit ergeben.
	 */

	/**
	 * Diese Konstante wird uebergeben, wenn ein Fehler bei dem "Benutzer
	 * anlegen" Prozess aufgetreten ist.
	 */
	public static final String ANLEGEN_FEHLER = "Der Benutzer konnte nicht angelegt werden";

	/**
	 * Diese Konstante wird uebergeben, wenn der gewollte Benutzer nicht
	 * vorhanden ist.
	 */
	public static final String BENUTZER_NICHT_VORHANDEN = "Der gesuchte Benutzer ist nicht vorhanden";

	/**
	 * Diese Konstante wird uebergeben, wenn der Benutzername fehlt. 
	 */
	public static final String BENUTZERNAME_FEHLT = "Bitte Benutzername eingeben.";

	/**
	 * Diese Konstante wird uebergeben, wenn das Passwort fehlt.
	 */
	public static final String PASSWORT_FEHLT = "Bitte Passwort eingeben.";

	/**
	 * Diese Konstante wird uebergeben, wenn der Benutzername oder das Passwort falsch ist.
	 */
	public static final String LOGIN_FEHLER = "Benutzername oder Passwort ist falsch.";

	/**
	 * Diese Konstante wird uebergeben, wenn keine Fehlerursache bekannt ist.
	 */
	public static final String FEHLER = "Erstmal fuer alles,bei dem mir nix anderes Sinnvolles einfaellt!!!";

	/**
	 * Diese Konstante wird uebergeben, wenn der Benutzername zu kurz ist.
	 */
    public static final String BENUTZERNAME_ZU_KURZ = "Der eingegebene Benutzername ist zu kurz (min. 6 Zeichen)";

    /**
	 * Diese Konstante wird uebergeben, wenn der Benutzername ungueltige Zeichen enthaelt.
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
