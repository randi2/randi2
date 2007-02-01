package de.randi2.model.exceptions;

/**
 * <p>
 * Diese Klasse kappselt die Fehler, die innerhalb der Klasse Benutzerkonto
 * auftreten koennen.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@stud.hs-heilbronn.de>
 * @version $Id$
 * 
 */
public class BenutzerkontoException extends Exception {

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
	 * Ein Konstruktor dieser Klasse
	 * 
	 * @param String
	 *            eine Konstante aus dieser Klasse als Message
	 */
	public BenutzerkontoException(String arg0) {
		super(arg0);
	}

}
