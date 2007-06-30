package de.randi2.utility;

import java.util.Vector;

/**
 * Wrapped eine Tabelle die nachher im CSV oder Excel-Format ausgegeben werden
 * kann.
 * 
 * @author Johannes Thoenes [jthoenes@stud.hs-heilbronn.de]
 * @version $Id$
 * 
 */
public class Tabelle {

	/**
	 * Die Anzahl der Spalten
	 */
	private int anzSpalten = 0;

	/**
	 * Die Kopfzeile
	 */
	private String kopfzeile[];

	/**
	 * Der Inhalt der Tabelle zeilenweise.
	 */
	private Vector<String[]> inhalt;

	/**
	 * Spaltentrenner
	 */
	private static final String SPALTENTRENNER = ",";

	/**
	 * Zeilentrenner
	 */
	private static final String ZEILENTRENNER = "\n";

	/**
	 * Konstruktor.
	 * 
	 * @param anzSpalten
	 *            Die Anzahl der Spalten
	 */
	public Tabelle(int anzSpalten) {
		this.anzSpalten = anzSpalten;
		kopfzeile = null;
		this.inhalt = new Vector<String[]>();
	}

	/**
	 * Konstruktor.
	 * 
	 * @param kopfzeile
	 *            Die Kopfzeile
	 */
	public Tabelle(String[] kopfzeile) {
		this.kopfzeile = kopfzeile;
		this.anzSpalten = kopfzeile.length;
		this.inhalt = new Vector<String[]>();
	}

	/**
	 * Fuegt der Tabelle eine Zeile an.
	 * 
	 * @param zeile
	 *            Die Zeile
	 */
	public void addZeile(String[] zeile) {
		this.inhalt.add(zeile);
	}

	/**
	 * Gibt einen CSV-String zurueck·
	 * 
	 * @return Der CSV-String.
	 */
	public String getCSVString() {
		StringBuffer csv = new StringBuffer();
		if (this.kopfzeile != null) {
			for (int i = 0; i < kopfzeile.length; i++) {
				if (i < kopfzeile.length - 1) {
					csv.append(kopfzeile[i] + SPALTENTRENNER);
				} else {
					csv.append(kopfzeile[i] + ZEILENTRENNER);
				}
			}

		}

		for (String zeile[] : this.inhalt) {
			for (int i = 0; i < zeile.length; i++) {
				if (i < zeile.length - 1) {
					csv.append(zeile[i] + SPALTENTRENNER);
				} else {
					csv.append(zeile[i] + ZEILENTRENNER);
				}
			}
		}

		return csv.toString();
	}

}
