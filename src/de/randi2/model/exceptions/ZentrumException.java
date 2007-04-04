package de.randi2.model.exceptions;

/**
 * Die Klasse ZentrumException kapselt die Fehler die im Zusammenhang mit der
 * Validierung von Zentrumsobjekten entstehen koennen.
 * 
 * @author Lukasz Plotnicki <lplotni@stud.hs-heilbronn.de>
 * @version $Id$
 * 
 */
public class ZentrumException extends Exception {

	/**
	 * Diese Konstante wird uebergeben, wenn die Abteilung falsch ist.
	 */
	public static final String ABTEILUNG_FALSCH = "Abteilung falsch!";

	/**
	 * Diese Konstante wird uebergeben, wenn die Abteilung fehlt.
	 */
	public static final String ABTEILUNG_NULL = "Abteilung = null!";

	/**
	 * Diese Konstante wird uebergeben, wenn die Hausnummer falsch ist.
	 */
	public static final String HAUSNR_FALSCH = "Hausnummer falsch!";

	/**
	 * Diese Konstante wird uebergeben, wenn die Hausnummer fehlt.
	 */
	public static final String HAUSNR_NULL = "Hausnummer = null!";

	/**
	 * Diese Konstante wird uebergeben, wenn die Institution falsch ist.
	 */
	public static final String INSTITUTION_FALSCH = "Institution falsch!";

	/**
	 * Diese Konstante wird uebergeben, wenn die Institution fehlt.
	 */
	public static final String INSTITUTION_NULL = "Institution = null!";

	/**
	 * Diese Konstante wird uebergeben, wenn der Ort falsch ist.
	 */
	public static final String ORT_FALSCH = "Ort falsch!";

	/**
	 * Diese Konstante wird uebergeben, wenn der Ort fehlt.
	 */
	public static final String ORT_NULL = "Ort = null!";

	/**
	 * Diese Konstante wird uebergeben, wenn die PLZ falsch ist.
	 */
	public static final String PLZ_FALSCH = "PLZ falsch!";

	/**
	 * Diese Konstante wird uebergeben, wenn die PLZ fehlt.
	 */
	public static final String PLZ_NULL = "PLZ = null!";

	/**
	 * Diese Konstante wird uebergeben, wenn die Strasse falsch ist.
	 */
	public static final String STRASSE_FALSCH = "Strasse falsch!";

	/**
	 * Diese Konstante wird uebergeben, wenn die Strasse fehlt.
	 */
	public static final String STRASSE_NULL = "Strasse = null!";

	/**
	 * Diese Konstante wird uebergeben, wenn das Passwort falsch ist.
	 */
	public static final String PASSWORT_FALSCH = "Passwort falsch!";

	/**
	 * Diese Konstante wird uebergeben, wenn das Passwort fehlt.
	 */
	public static final String PASSWORT_NULL = "Passwort = null!";

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
