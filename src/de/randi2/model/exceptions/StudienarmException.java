package de.randi2.model.exceptions;

/**
 * Diese Klasse beinhaltet die Fehler, die innerhalb der Klasse Studienarm
 * auftreten koennen.
 * 
 * 
 * @author Daniel Haehn [dhaehn@stud.hs-heilbronn.de]
 * @version $Id: StudienarmException.java 2418 2007-05-04 14:37:12Z jthoenes $
 */
public class StudienarmException extends BenutzerException {

	/**
	 * Diese Konstante wird uebergeben, wenn die Patienten nicht gefunden
	 * wurden.
	 */
	public static final String PATIENTEN_NICHT_GEFUNDEN = "Die Patienten wurden nicht gefunden.";

	/**
	 * Diese Konstante wird uebergeben, wenn die Studie nicht gefunden
	 * wurde.
	 */
	public static final String STUDIE_NICHT_GEFUNDEN = "Die Studie wurde nicht gefunden.";
	
	/**
	 * Diese Konstante wird uebergeben, wenn der Status falsch gesetzt wurde.
	 */
	public static final String STATUS_UNGUELTIG = "Status falsch gesetzt.";

	
	/**
	 * 
	 * @param fehlermeldung
	 */
	public StudienarmException(String fehlermeldung) {
		super(fehlermeldung);
	}
}
