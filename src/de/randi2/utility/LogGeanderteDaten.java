package de.randi2.utility;

import java.util.HashMap;

/**
 * Die Klasse LogGeanderteDaten kapselt die geanderten Daten eines
 * Datenbankobjekts und stelt get-Methoden zur Verfuegung.
 * 
 * @author Johannes Th&ouml;nes <jthoenes@stud.hs-heilbronn.de>
 * @version $Id$
 * 
 */
public class LogGeanderteDaten {

	/**
	 * Die Id des Datenobjekts.
	 */
	private long id = Long.MAX_VALUE;

	/**
	 * Der Typ des Datenobjekts.
	 */
	private String typ = null;

	/**
	 * Die geanderten Daten.
	 */
	private HashMap<String, String> daten = null;

	/**
	 * Konstruktor fuer ein geandertes Datenobjekt.
	 * 
	 * @param id
	 *            Die id des Datenobjekts in der Datenbank. Muss groesser als 0
	 *            sein.
	 * @param typ
	 *            Der Typ des Datenobjekts. Darf nicht leer sein.
	 * @param daten
	 *            Die geanderten Daten. Darf nicht leer sein.
	 */
	public LogGeanderteDaten(long id, String typ, HashMap<String, String> daten) {
		super();

		if (id <= 0) {
			throw new IllegalArgumentException("Id darf nicht 0 sein");
		}
		this.id = id;
		if (typ == null || typ.equals("")) {
			throw new IllegalArgumentException("Typ darf nicht leer sein");
		}
		this.typ = typ;

		if (daten == null || daten.isEmpty()) {
			throw new IllegalArgumentException(
					"Die geanderten Daten duerfen nicht leer sein");
		}
		this.daten = daten;
	}

	/**
	 * Gibt die geanderten Daten zurueck.
	 * 
	 * @return Die geanderten Daten.
	 */
	public HashMap<String, String> getDaten() {
		return daten;
	}

	/**
	 * Gibt die Id des geanderten Objekts zurueck.
	 * 
	 * @return Die Id.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Der Typ des geanderten Objekts.
	 * 
	 * @return Der Typ.
	 */
	public String getTyp() {
		return typ;
	}

}
