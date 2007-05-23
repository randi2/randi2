package de.randi2.datenbank.exceptions;

import java.sql.SQLException;

import de.randi2.utility.SystemException;

/**
 * Diese Exception zeigt einen generellen Fehler beim Datenbankzugriff auf.
 * 
 * @version $Id: DatenbankFehlerException.java 1205 2007-02-03 13:50:44Z
 *          jthoenes $
 * @author Daniel Haehn [dhaehn@stud.hs-heilbronn.de]
 */
@SuppressWarnings("serial")
public class DatenbankFehlerException extends SystemException {
	
	/**
	 * Fehlermeldung, wenn eine negative oder =0 Id an die setId() Methode uebergeben wurde.
	 */
	public static final String ID_FALSCH = "Es wurde keine positive Id uebergeben!";
	
	/**
	 * Konstante falls Argument null.
	 */
	public static final String ARGUMENT_IST_NULL = "Das uebergebene Argument ist null";

	/**
	 * Konstante falls der Filter beim Objekt nicht gesetzt.
	 */
	public static final String SUCHOBJEKT_IST_KEIN_FILTER = "Beim Uebergebenen Objekt wurde der Filter nicht gesetzt";

	/**
	 * Allgemeine Fehlerkonstante wenn beim suchen eines Objektes ein Fehler
	 * auftritt
	 */
	public static final String SUCHEN_ERR = "Beim Suchen trat ein Fehler auf.";

	/**
	 * Allgemeine Fehlerkonstante wenn beim schreiben eines Objektes ein Fehler
	 * auftritt
	 */
	public static final String SCHREIBEN_ERR = "Beim Schreiben trat ein Fehler auf.";
	
	/**
	 * Allgemeine Fehlerkonstante fuer Fehler die beim Loeschen auftretten.
	 */
	public static final String LOESCHEN_ERR="Beim Loeschen trat ein Fehler auf.";
	
	/**
	 * Konstante falls beim Suchen nach einer spezifischen ID diese nicht in der Datenbank
	 * vorhanden ist.
	 */
	public static final String ID_NICHT_VORHANDEN="Es existiert kein Objekt mit dieser ID.";
	
	
	/**
	 * Allgemeine Konstante falls ein Datensatz unkorrekte Daten enthaelt.
	 */
	public static final String UNGUELTIGE_DATEN="Geladener Datensatz ist nicht valide.";
	
	/**
	 * Konstante, falls beim Verbinden oder Trennen ein Fehler auftritt.
	 */
	public static final String CONNECTION_ERR="Es sind Probleme mit der Verbindung zur Datenbank aufgetreten.";

	/**
	 * Konstruktor.
	 * 
	 * @param msg
	 *            Eine Nachricht aus der Liste der Konstanten.
	 */
	public DatenbankFehlerException(String msg) {
		super(msg);
	}
	
	/**
	 * Konstruktor.
	 * 
	 * @param msg
	 *            Eine Nachricht aus der Liste der Konstanten.
	 */
	public DatenbankFehlerException(SQLException e, String sql) {
		// TODO SQLException auswerten
	}

}
