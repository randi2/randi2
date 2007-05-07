package de.randi2.datenbank;

import de.randi2.model.exceptions.RandomisationsException;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.utility.NullKonstanten;

/**
 * Die Klasse Block bietet einen Persitenzmechanismus fuer die Block-basierten
 * Randomisationen an.
 * 
 * @author Johannes Thoenes [jthoenes@stud.hs-heilbronn.de]
 * @version $Id$
 */
public final class Block {

	/**
	 * Niemand soll diese Klasse instanzieren koennen.
	 * 
	 */
	private Block() {
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

}
