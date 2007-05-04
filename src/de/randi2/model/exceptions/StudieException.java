 package de.randi2.model.exceptions;
/**
 * Diese Klasse beinhaltet die Fehler, die innerhalb der Klasse Studie auftreten koennen.
 * @author Susanne Friedrich [sufriedr@stud.hs-heilbronn.de]
 * @author Nadine Zwink [nzwink@stud.hs-heilbronn.de]
 * @version $Id$
 *
 */
public class StudieException extends Randi2Exception {
	
	/**
	 * Diese Konstante wird uebergeben, wenn das Datum nicht in der Zukunft liegt.
	 */
	public static final String DATUM_FEHLER="Datum liegt nicht in der Zukunft.";
	/**
	 * Diese Konstante wird uebergeben, wenn der Studienname nicht eingegeben wurde.
	 */
	public static final String STUDIENNAME_FEHLT = "Bitte Studienname eingeben.";
	/**
	 * Diese Konstante wird uebergeben, wenn der Studienname zu kurz oder zu lang ist.
	 */
	public static final String STUDIENNAME_UNGUELTIG = "Studienname zu kurz oder zu lang";
	/**
	 * Diese Konstante wird uebergeben,wenn die Studienarme nicht eingetragen wurden.
	 */
	public static final String STUDIENARM_FEHLT = "Studiennarme nicht eingetragen.";
	/**
	 * Diese Konstante wird uebergeben, wenn der Studienarm ungueltig.
	 */
	public static final String STUDIENARM_UNGUELTIG = "Studienarm ungültig.";
	/**
	 * Diese Konstante wird uebergeben, wenn der Status nicht angegeben wurde.
	 */
	public static final String STATUSFEHLER ="Statusfehler.";
	/**
	 * Diese Konstante wird uebergeben, wenn der Status falsch gesetzt wurde.
	 */
	public static final String STATUS_UNGUELTIG = "Status falsch gesetzt.";
	
	
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
