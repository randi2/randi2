package de.randi2.model.exceptions;

import de.randi2.utility.SystemException;

/**
 * Die Klasse kapselt die Fehler die waehrend der Randomisation auftreten. Es
 * sollte darauf geachtet werden, dass bei normalen Programmablauf diese Fehler
 * niemals auftreten. Insofern sollte ein Auftreten der Exception auf einen
 * Programmierfehler hinweisen.
 * 
 * @author Johannes Thoenes [johannes.thoenes@urz.uni-heidelberg.de]
 * @version $Id$
 */
public class RandomisationsException extends SystemException {

	/**
	 * Fehler falls der zu randomisierende Patient andere Strata als die Studie
	 * hat.
	 */
	public static final String FALSCHE_STRATA = "Die Strata des Patienten entsprechen nicht den Strata, die f&uuml;r ";

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
	 * Ein Konstruktor dieser Klasse
	 * 
	 * @param arg0
	 *            eine Konstante aus dieser Klasse als Message
	 */
	public RandomisationsException(String arg0) {
		super(arg0);
	}
}
