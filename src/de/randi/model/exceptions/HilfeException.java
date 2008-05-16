package de.randi2.model.exceptions;

/**
 * <p>
 * Diese Klasse kappselt die Fehler, die innerhalb der Util-Klasse Hilfe
 * auftreten koennen.
 * </p>
 * 
 * @author Daniel Haehn [dhaehn@stud.hs-heilbronn.de]
 * @version $Id$
 * 
 */
public class HilfeException extends BenutzerException {

	/**
	 * Diese Konstante wird uebergeben, wenn die Unterseite fehlt.
	 */
	public static final String UNTERSEITE_FEHLT = "Zu diesem Thema existiert keine Hilfe!";

	/**
	 * Ein Konstruktor dieser Klasse
	 * 
	 * @param arg0
	 *            eine Konstante aus dieser Klasse als Message
	 */
	public HilfeException(String arg0) {
		super(arg0);
	}

}
