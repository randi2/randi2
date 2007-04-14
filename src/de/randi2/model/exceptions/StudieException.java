package de.randi2.model.exceptions;
/**
 * 
 * @author Susanne Friedrich [sufriedr@stud.hs-heilbronn.de]
 * @author Nadine Zwink [nzwink@stud.hs-heilbronn.de]
 * @version $Id: StudieException.java 1828 2007-04-06 18:31:47Z jthoenes $
 *
 */
public class StudieException extends Exception {
	//TODO Kommentare
	/**
	 * Diese Konstante wird uebergeben, wenn das Datum nicht in der Zukunft liegt.
	 */
	public static final String DATUM_FEHLER="Datum liegt nicht in der Zukunft.";
	public static final String STUDIENNAME_FEHLT = "Bitte Studienname eingeben.";
	public static final String STUDIENNAME_UNGUELTIG = "Studienname ungültig";
	public static final String STUDIENARM_FEHLT = "Studiennarme nicht eingetragen.";
	public static final String STUDIENARM_UNGUELTIG = "Studienarm ungültig.";
	
	
	/**
	 * Ein Konstruktor dieser Klasse
	 * 
	 * @param arg0
	 *            eine Konstante aus dieser Klasse als Message
	 */
	public StudieException(String arg0) {
		super(arg0);
	}



}
