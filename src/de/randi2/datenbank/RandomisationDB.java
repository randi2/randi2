package de.randi2.datenbank;

import java.util.HashMap;

import de.randi2.model.exceptions.RandomisationsException;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.utility.NullKonstanten;

/**
 * Die Klasse Block bietet einen Persitenzmechanismus fuer die Block-basierten
 * Randomisationen an.
 * 
 * @author Johannes Thoenes [jthoenes@stud.hs-heilbronn.de]
 * @version $Id: Block.java 2445 2007-05-07 09:13:25Z jthoenes $
 */
public final class RandomisationDB extends Filter {

	/**
	 * Niemand soll diese Klasse instanzieren koennen.
	 * 
	 */
	private RandomisationDB() {
	}

	/**
	 * Speichert einen Block in der Datenbank.
	 * 
	 * @param block
	 *            Der zu speichernde Block.
	 * @param s
	 *            Die Studie zu der der Block gespeichert werden soll.
	 * @throws RandomisationsException
	 *             Falls noch Werte zu dieser Studie in der Datenbank
	 *             existieren.
	 * @see RandomisationsException#WERTE_EXISTIEREN
	 */
	public static synchronized void speichernBlock(int[] block, StudieBean s)
			throws RandomisationsException {

	}

	/**
	 * Gibt den naechsten Wert fuer die Randomisation zu dieser Studie zurueck.
	 * 
	 * @return Den naechsten Wert oder {@link NullKonstanten#NULL_INT} falls
	 *         keine Werte mehr zu dieser Studie existieren.
	 */
	public static synchronized int getNext() {
		return NullKonstanten.NULL_INT;
	}

	/**
	 * Gibt die Anzahl der Patienten zu allen Studienarmen der Studie zurueck,
	 * welche die selben Strata-Kombinationen wie die uebergebenen hat.
	 * 
	 * @param studienID
	 *            Die Id der Studie deren Studienarme untersucht werden sollen.
	 * @param strataKombination
	 *            Die Strata-Kombinationen zu denen die Anzahl der Patienten
	 *            ermittelt werden soll.
	 * @return Eine HashMap, die angibt wieviele Patienten zu einem Studienarm
	 *         zugeordnet sind. Die erste Stelle der HashMap ist mit der Id des
	 *         Studienarms als {@link Long} belegt. Die zweite Stelle der
	 *         HashMap ist mit die Anzahl der zugeordneten Patienten als
	 *         {@link Integer}.
	 */
	public static HashMap<Long, Integer> getAnzahlPatientenZuStudienarmen(
			long studienID, String strataKombination) {
		return null;
	}

	/**
	 * Gibt die Anzahl der Patienten zu allen Studienarmen der Studie zurueck.
	 * 
	 * @param studienID
	 *            Die Id der Studie deren Studienarme untersucht werden sollen.
	 * @return Eine HashMap, die angibt wieviele Patienten zu einem Studienarm
	 *         zugeordnet sind. Die erste Stelle der HashMap ist mit der Id des
	 *         Studienarms als {@link Long} belegt. Die zweite Stelle der
	 *         HashMap ist mit die Anzahl der zugeordneten Patienten als
	 *         {@link Integer}.
	 */
	public static HashMap<Long, Integer> getAnzahlPatientenZuStudienarmen(
			long studienID) {
		return null;
	}

}
