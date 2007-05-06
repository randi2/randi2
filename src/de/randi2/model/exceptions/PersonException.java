package de.randi2.model.exceptions;

/**
 * <p>
 * Diese Klasse kapselt die Fehler, die innerhalb der Klasse Person auftreten.
 * koennen.
 * </p>
 * 
 * @author Thomas Willert [twillert@stud.hs-heilbronn.de]
 * @version $Id: PersonException.java 1287 2007-02-05 14:42:00Z twillert $
 * 
 */
public class PersonException extends BenutzerException {

	/**
	 * Diese Konstante wird uebergeben, wenn das Person Objekt in der Datenbank
	 * nicht gefunden werden konnte und somit auch durch die get() Methode nicht
	 * zurueckgeliefert werden konnte. (Der entsprechende Datenbank-Fehler kann
	 * in der log Datei gefunden werden.)
	 */
	public static final String PERSON_NICHT_GEFUNDEN = "Die gew&uuml;nschte Person konnte nicht geladen werden.";

	/**
	 * Diese Konstante wird uebergeben, wenn waehrend des Such-Vorgangs ein
	 * Fehler in der DB aufgetreten ist. Die entsprechende DB-Fehlermeldung ist
	 * in der log-Datei zu finden.
	 */
	public static final String DB_FEHLER = "W&auml;hrend des Such-Vorgangs ist ein Fehler aufgetreten.";

	/**
	 * Diese Konstante wird uebergeben, wenn der Nachname fehlt.
	 */
	public static final String NACHNAME_FEHLT = "Bitte Nachname eingeben.";

	// TODO in der meldung stehen dann spater noch die echten konventionen

	/**
	 * Diese Konstante wird uebergeben, wenn der der Nachname nicht den
	 * Konventionen.
	 */
	public static final String NACHNAME_UNGUELTIG = "Nachname muss 2 bis 50 Zeichen lang sein.";

	/**
	 * Diese Konstante wird uebergeben, wenn der Vorname fehlt.
	 */
	public static final String VORNAME_FEHLT = "Bitte Vorname eingeben.";

	/**
	 * Diese Konstante wird uebergeben, wenn der Vorname nicht den Konventionen
	 * entspricht.
	 */
	public static final String VORNAME_UNGUELTIG = "Vorname muss 2 bis 50 Zeichen lang sein.";

	/**
	 * Diese Konstante wird uebergeben, wenn das Geschlecht fehlt.
	 */
	public static final String GESCHLECHT_FEHLT = "Bitte Geschlecht eingeben.";

	/**
	 * Diese Konstante wird uebergeben, wenn die E-Mail-Adresse fehlt.
	 */
	public static final String EMAIL_FEHLT = "Bitte E-Mail-Adresse eingeben.";

	/**
	 * Diese Konstante wird uebergeben, wenn die E-Mail-Adresse ungueltig ist.
	 */
	public static final String EMAIL_UNGUELTIG = "Email muss 6 bis 255 Zeichen lang und valide sein.";

	/**
	 * Diese Konstante wird uebergeben, wenn die Telefonnumer fehlt.
	 */
	public static final String TELEFONNUMMER_FEHLT = "Bitte Telefonnummer eingeben.";

	/**
	 * Diese Konstante wird uebergeben, wenn die Telefonnummer nicht den
	 * Konventionen entspricht.
	 */
	public static final String TELEFONNUMMER_UNGUELTIG = "Telefonnummer muss 6 bis 26 Zeichen lang und valide sein.";

	/**
	 * Diese Konstante wird uebergeben, wenn die Faxnummer nicht den
	 * Konventionen entspricht.
	 */
	public static final String FAX_UNGUELTIG = "Fax muss 6 bis 26 Zeichen lang und valide sein.";

	/**
	 * Diese Konstante wird uebergeben, wenn das Geschlecht nicht den
	 * Konventionen.
	 */
	public static final String GESCHLECHT_UNGUELTIG = "Geschlecht muss m oder w sein.";

	/**
	 * Diese Konstante wird uebergeben, wenn die Handynummer nicht den
	 * Konventionen entspricht.
	 */
	public static final String HANDY_UNGUELTIG = "Handynummer muss 7 bis 26 Zeichen lang und valide sein.";

	/**
	 * Diese Konstante wird uebergeben, wenn der der Titel nicht den
	 * Konventionen entsprciht.
	 */
	public static final String TITEL_UNGUELTIG = "Titel muss Prof. / Dr. / Prof. Dr. / (leer) sein";

	/**
	 * Diese Konstante wird uebergeben, wenn ein unbekannter Fehler aufgetreten
	 * ist.
	 */
	public static final String FEHLER = "Ein unbekannter Fehler ist aufgetreten. Bitte erneut versuchen.";

	/**
	 * Ein Konstruktor dieser Klasse
	 * 
	 * @param arg0
	 *            eine Konstante aus dieser Klasse als Message
	 */
	public PersonException(String arg0) {
		super(arg0);
	}

}
