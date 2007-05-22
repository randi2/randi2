package de.randi2.model.exceptions;

/**
 * <p>
 * Die Klasse ZentrumException kapselt die Fehler die im Zusammenhang mit der
 * Validierung von Zentrumsobjekten entstehen koennen.
 * </p>
 * 
 * @author Lukasz Plotnicki [lplotni@stud.hs-heilbronn.de]
 * @version $Id: ZentrumException.java 2418 2007-05-04 14:37:12Z jthoenes $
 * 
 */
public class ZentrumException extends BenutzerException {

	/**
	 * Diese Konstante wird uebergeben, wenn die Abteilung falsch ist.
	 */
	public static final String ABTEILUNG_FALSCH = "Die eingegebene Abteilung ist falsch!";

	/**
	 * Diese Konstante wird uebergeben, wenn die Abteilung fehlt.
	 */
	public static final String ABTEILUNG_NULL = "Die Abteilung wurde nicht eingegeben!";

	/**
	 * Diese Konstante wird uebergeben, wenn die Hausnummer falsch ist.
	 */
	public static final String HAUSNR_FALSCH = "Die eingegebene Hausnummer ist falsch!";

	/**
	 * Diese Konstante wird uebergeben, wenn die Hausnummer fehlt.
	 */
	public static final String HAUSNR_NULL = "Die Hausnummer wurde nicht eingegeben!";

	/**
	 * Diese Konstante wird uebergeben, wenn die Institution falsch ist.
	 */
	public static final String INSTITUTION_FALSCH = "Die eingegebene Institution ist falsch!";

	/**
	 * Diese Konstante wird uebergeben, wenn die Institution fehlt.
	 */
	public static final String INSTITUTION_NULL = "Die Institution wurde nicht eingegeben!";

	/**
	 * Diese Konstante wird uebergeben, wenn der Ort falsch ist.
	 */
	public static final String ORT_FALSCH = "Der eingegebene Ort ist falsch!";

	/**
	 * Diese Konstante wird uebergeben, wenn der Ort fehlt.
	 */
	public static final String ORT_NULL = "Der Ort wurde nicht eingegeben!";

	/**
	 * Diese Konstante wird uebergeben, wenn die PLZ falsch ist.
	 */
	public static final String PLZ_FALSCH = "Die eingegenene Postleitzahl ist falsch!";

	/**
	 * Diese Konstante wird uebergeben, wenn die PLZ fehlt.
	 */
	public static final String PLZ_NULL = "Die Postleitzahl wurde nicht eingegeben!";

	/**
	 * Diese Konstante wird uebergeben, wenn die Strasse falsch ist.
	 */
	public static final String STRASSE_FALSCH = "Die eingegebene Strasse falsch!";

	/**
	 * Diese Konstante wird uebergeben, wenn die Strasse fehlt.
	 */
	public static final String STRASSE_NULL = "Die Stra�e wurde nicht eingegeben!";

	/**
	 * Diese Konstante wird uebergeben, wenn das Passwort falsch ist.
	 */
	public static final String PASSWORT_FALSCH = "Das eingegbene Passwort ist falsch!";

	/**
	 * Diese Konstante wird uebergeben, wenn das Passwort fehlt.
	 */
	public static final String PASSWORT_NULL = "Das Passwort wurde nicht eingegeben!";

	/**
	 * Diese Konstante wird uebergeben, wenn das PersonBean Objekt, das an die
	 * setAnsprechpartner() Methode uebergeben wurde, noch nicht in der DB
	 * gespeichert wurde.
	 */
	public static final String ANSPRECHPARTNER_NICHT_GESPEICHERT = "Der &uuml;bergebene Ansprechpartner muss vorher gespeichert werden!";

	/**
	 * Diese Konstante wird uebergeben, wenn an die setAnsprechpartner() Methode
	 * NULL uebergeben wurde.
	 */
	public static final String ANSPRECHPARTNER_NULL = "Kein Ansprechpartner wurde &uuml;bergeben!";

	/**
	 * Die Konstante wird uebergeben, wenn bei der Suche eines Zentrums zu einer
	 * ID das Zentrum nicht gefunden wurde.
	 */
	public static final String ZENTRUM_NICHT_GEFUNDEN = "Zu der angegebenen ID wurde kein Zentrum gefunden.";

	/**
	 * Konstruktor dieser Klasse an den ein Message String übergeben werden
	 * muss. (Als Message ist eine entsprechende Konstante aus dieser Klasse zu
	 * benutzen)
	 * 
	 * @param message
	 *            Konstante aus dieser Klassen (STRING)
	 */
	public ZentrumException(String message) {
		super(message);
	}

}
