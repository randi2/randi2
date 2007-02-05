package de.randi2.model.exceptions;

/**
 * <p>
 * Diese Klasse kapselt die Fehler, die innerhalb der Klasse Person
 * auftreten koennen.
 * </p>
 * 
 * @author Thomas Willert <twillert@stud.hs-heilbronn.de>
 * @version $Id: PersonException.java 1287 2007-02-05 14:42:00Z twillert $
 * 
 */
public class PersonException extends Exception {

	/*
	 * Die einzelnen Konstanten werde sich auch im Laufe der Arbeit ergeben.
	 */

	/**
	 * Diese Konstante wird uebergeben, wenn der Nachname fehlt.
	 */
	public static final String NACHNAME_FEHLT = "Bitte Nachname eingeben.";
	//in der meldung stehen dann später noch die echten konventionen
	public static final String NACHNAME_UNGUELTIG="Nachname entspricht nicht den Konventionen";

	/**
	 * Diese Konstante wird uebergeben, wenn der Vorname fehlt.
	 */
	public static final String VORNAME_FEHLT = "Bitte Vorname eingeben.";
	public static final String VORNAME_UNGUELTIG="Vorname entspricht nicht den Konventionen.";
	public static final String GESCHLECHT_FEHLT="Bitte Geschlecht eingeben.";
	public static final String EMAIL_FEHLT="Bitte E-Mail-Adresse eingeben.";
	public static final String EMAIL_UNGUELTIG="Email ist ungültig.";
	public static final String TELEFONNUMMER_FEHLT="Bitte Telefonnummer eingeben.";
	public static final String TELEFONNUMMER_UNGUELTIG="Telefonnummer entspricht nicht den Konventionen.";
	public static final String FAX_UNGUELTIG="Fax enstspricht nicht den Konventionen.";
	public static final String GESCHLECHT_UNGUELTIG="Geschlecht entspricht nicht den Konventionen.";
	public static final String HANDY_UNGUELTIG="Handynummer entspricht nicht den Konventionen.";
	public static final String TITEL_UNGUELTIG="Titel entspricht nicht den Konventionen.";
	public static final String FEHLER="Erstmal fuer alles, bei dem mir nix anderes Sinnvolles einfaellt!!!";
	/**
	 * Ein Konstruktor dieser Klasse
	 * 
	 * @param String
	 *            eine Konstante aus dieser Klasse als Message
	 */
	public PersonException(String arg0) {
		super(arg0);
	}

}