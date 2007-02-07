package de.randi2.model.exceptions;

/**
 * @author Lukasz Plotnicki <lplotni@stud.hs-heilbronn.de>
 * @version $Id$
 * 
 */
public class ZentrumException extends Exception {

	public final static String ABTEILUNG_FALSCH = "Abteilung falsch!";

	public final static String ABTEILUNG_NULL = "Abteilung = null!";

	public final static String HAUSNR_FALSCH = "Hausnummer falsch!";

	public final static String HAUSNR_NULL = "Hausnummer = null!";

	public final static String INSTITUTION_FALSCH = "Institution falsch!";

	public final static String INSTITUTION_NULL = "Institution = null!";

	public final static String ORT_FALSCH = "Ort falsch!";

	public final static String ORT_NULL = "Ort = null!";

	public final static String PLZ_FALSCH = "PLZ falsch!";

	public final static String PLZ_NULL = "PLZ = null!";

	public final static String STRASSE_FALSCH = "Strasse falsch!";

	public final static String STRASSE_NULL = "Strasse = null!";

	public final static String PASSWORT_FALSCH = "Passwort falsch!";

	public final static String PASSWORT_NULL = "Passwort = null!";

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
