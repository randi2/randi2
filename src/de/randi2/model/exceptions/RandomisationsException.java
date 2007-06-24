package de.randi2.model.exceptions;

import de.randi2.utility.SystemException;

/**
 * Die Klasse kapselt die Fehler die waehrend der Randomisation auftreten. Es
 * sollte darauf geachtet werden, dass bei normalen Programmablauf diese Fehler
 * niemals auftreten. Insofern sollte ein Auftreten der Exception auf einen
 * Programmierfehler hinweisen.
 * 
 * @author Johannes Thoenes [johannes.thoenes@urz.uni-heidelberg.de]
 * @version $Id: RandomisationsException.java 2442 2007-05-07 08:45:21Z jthoenes $
 */
public class RandomisationsException extends SystemException {

	/**
	 * Fehler falls die gewaehlte Blockgroesse kein Vielfaches der Anzahl der
	 * Studienarme ist.
	 */
	public static final String BLOCKGROESSE_KEIN_VIELFACHES_DER_ARMEANZAHL = "Die &uuml;bergebene Blockgr&ouml;sse ist kein Vielfaches der Anzahl der Studienarme.";

	/**
	 * Fehler falls die uebergebenen Studie null ist.
	 */
	public static final String STUDIE_NULL = "Die &uuml;bergebene Studie darf nicht null sein.";

	/**
	 * Fehler falls aktueller Studienarm nicht verwendet werden kann.
	 */
	public static final String ARM_NICHT_VERWENDBAR = "Dieser Studienarm kann nicht verwendet werden.";
	
	/**
	 * Fehler falls versuch wird, einen neuen Block in der Tabelle zu speichern, obwohl noch Werte in der Datenbank vorhanden sind.
	 */
	public static final String NOCH_RANDOMISATIONS_WERTE_VORHANDEN = "Neuer Block kann nicht angelegt werden. Noch werte in der Blocktabelle vorhanden.";

	/**
	 * Fehler bei Auswahl eines ungueltigen Randomisationsalgorithmus
	 */
	public static final String ALGORITHMUS_UNGUELTIG = "Ungueltiger Randomisationsalgorithmus!";
	
	
	/**
	 * Ein Konstruktor dieser Klasse
	 * 
	 * @param arg0
	 *            eine Konstante aus dieser Klasse als Message
	 */
	public RandomisationsException(String arg0) {
		super(arg0);
	}
}
