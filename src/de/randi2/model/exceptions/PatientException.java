package de.randi2.model.exceptions;

/**
 * <p>
 * Die Exception Klasse fuer die PatienBean/Patient Exceptions
 * </p>
 * 
 * @author Lukasz Plotnicki [lplotni@stud.hs-heilbronn.de]
 * @version $Id: PatientException.java 2418 2007-05-04 14:37:12Z jthoenes $
 */
public class PatientException extends BenutzerException {

	/**
	 * Fehlermeldung, wenn das eingegebene Datum in der Zukunft liegt.
	 */
	public static final String DATUM_IN_DER_ZUKUNFT = BenutzerkontoException.DATUM_IN_DER_ZUKUNFT;

	/**
	 * Fehlermeldung, wenn das Aufklaerungsdatum/Geburtsdatum gleich null ist.
	 */
	public static final String DATUM_NULL = "Das Datum ist nicht vorhanden!";

	/**
	 * Fehlermeldung, wenn das uebergebene Geschlecht falsch war.
	 */
	public static final String GESCHLECHT_FALSCH = "Das &uuml;bergebene Geschlecht ist falsch (Entweder m oder w)!";

	/**
	 * Fehlermeldung, wenn das Geschlecht = null ist.
	 */
	public static final String GESCHLECHT_NULL = "Das Geschlecht wurde nicht gesetzt!";

	/**
	 * Fehlermeldung, wenn der eingegebene Performance-Status nicht korrekt ist.
	 */
	public static final String PERFORMANCE_STATUS_FALSCH = "Der &uuml;bergebene Performance-Status ist Falsch!\n(Korrekte Werte: 0,1,2,3,4)";

	/**
	 * Fehlermeldung, wenn PerformanceStatus = null.
	 */
	public static final String PERFORMANCE_NULL = "Kein Performance-Status vorhanden!";

	/**
	 * Fehlermeldung, wenn das gewuenschte Studienarm nicht vorhanden ist.
	 */
	public static final String STUDIENARM_NICHT_VORHANDEN = "Das gewuenschte Studienarm ist nicht vorhanden!";

	/**
	 * Fehlermeldung, wenn null an die setStudienarm() Methode uebergeben wurde.
	 */
	public static final String STUDIENARM_NULL = "Es wurde kein Studienarm &uuml;bergeben!";

	/**
	 * Fehlermeldung, wenn beim Zugriff auf die DB ein Fehler aufgetreten ist.
	 */
	public static final String DB_FEHLER = "Ein Fehler in der Datenbank ist aufgetreten!";

	/**
	 * Fehlermeldung, wenn das StudienarmBean, was uebergeben wurde (o. seine
	 * Id) nocht nicht in der DB gespeichert wurde.
	 */
	public static final String STUDIENARM_NOCH_NICHT_GESPEICHERT = "Der Studienarm wurde noch nicht gespeichert!";

	/**
	 * Fehlermeldung, wenn ein negativer Wert uebergeben wurde!
	 */
	public static final String KOERPEROBERFLAECHE_NEGATIV = "Die &uuml;bergebene K&ouml;rperoberfl&auml;che ist negativ!";

	/**
	 * Fehlermeldung, wenn die Koerperoberflache = null ist.
	 */
	public static final String KOERPEROBERFLACHE_NULL = "Die K&ouml;rperoberfl&auml;che wurde nicht eingegeben!";

	/**
	 * Fehlermeldung, wenn null als Initiale des Patienten uebergeben wurde.
	 */
	public static final String INITIALEN_NULL = "Die Initiale des Patienten wurde nicht &uuml;bergeben!";

	/**
	 * Fehlermeldung, wenn der String mit Initialen leer ist.
	 */
	public static final String INITIALEN_LEER = "Die Initiale sind leer!";

	/**
	 * Fehlermeldung, wenn kein Benutzerkonto zu dem Patient-Objekt zugeordnet
	 * wurde.
	 */
	public static final String BENUTZERKONTO_NULL = "Kein Benutzerkonto vorhanden!";

	/**
	 * Fehlermeldung, wenn eine Falsche Id uebergeben wurde - wahrscheinlich
	 * wurde das BenutzerkontoBean noch nicht persistent im System gespeichert.
	 */
	public static final String BENUTZERKONTOID_FALSCH = "Eine falsche Id wurde &uuml;bergeben!";

	/**
	 * Konstruktor dieser Klasse an den die Fehlermassage uebergeben werden
	 * muss.
	 * 
	 * @param fehlermeldung
	 *            Fehlermeldung
	 */
	public PatientException(String fehlermeldung) {
		super(fehlermeldung);
	}

}
