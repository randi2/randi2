package de.randi2.utility;

import de.randi2.model.fachklassen.beans.BenutzerkontoBean;

/**
 * Die Klasse LogAktion kapselt die Daten die zu einer zu loggenden Aktion
 * gehoeren und stellt getMethoden dazu zur Verfuegung.
 * 
 * @author Johannes Th&ouml;nes [jthoenes@stud.hs-heilbronn.de]
 * @version $Id: LogAktion.java 1804 2007-04-04 16:58:20Z jthoenes $
 */
public class LogAktion {
	/**
	 * Nachricht die als Log-Text erscheinen soll.
	 */
	private String nachricht = null;

	/**
	 * Das Benutzerkonto des Benutzers der die Aktion ausgefuehrt hat.
	 */
	private BenutzerkontoBean benutzer = null;

	/**
	 * Das Objekt in dem die in dieser Aktion geanderten Daten enthalten sind.
	 */
	private LogGeanderteDaten geanderteDaten = null;

	/**
	 * Erstellt eine Log Aktion. Dieser Konstruktor ist zu verwenden, wenn keine
	 * Daten geaendert wurden.
	 * 
	 * @param nachricht
	 *            Nachricht die als Log-Text erscheinen soll. Darf nicht leer
	 *            sein.
	 * @param benutzer
	 *            Das Benutzerkonto des Benutzers der die Aktion ausgefuehrt
	 *            hat. Darf nicht leer sein.
	 */
	public LogAktion(String nachricht, BenutzerkontoBean benutzer) {
		super();
		if (nachricht == null || nachricht.equals("")) {
			throw new IllegalArgumentException(
					"Nachricht darf nicht leer sein.");
		}
		this.nachricht = nachricht;

		if (benutzer == null) {
			throw new IllegalArgumentException("Benutzer darf nicht leer sein.");
		}
		this.benutzer = benutzer;
	}

	/**
	 * Erstellt eine Log Aktion. Dieser Konstruktor ist zu verwenden, wenn Daten
	 * geaendert wurden.
	 * 
	 * @param nachricht
	 *            Nachricht die als Log-Text erscheinen soll. Darf nicht leer
	 *            sein.
	 * @param benutzer
	 *            Das Benutzerkonto des Benutzers der die Aktion ausgefuehrt
	 *            hat. Darf nicht leer sein.
	 * @param geanderteDaten
	 *            Das Objekt in dem die in dieser Aktion geanderten Daten
	 *            enthalten sind. Darf nicht leer sein.
	 */
	public LogAktion(String nachricht, BenutzerkontoBean benutzer,
			LogGeanderteDaten geanderteDaten) {
		this(nachricht, benutzer);
		if (geanderteDaten == null) {
			throw new IllegalArgumentException(
					"Die geanderten Daten duerfen nicht leer sein.");
		}
		this.geanderteDaten = geanderteDaten;
	}

	/**
	 * Gibt den Benutzernamen des Benutzers zurueck, der die Aktion ausgefuehrt
	 * hat.
	 * 
	 * @return Der Benutzername.
	 */
	public String getBenutzernamen() {
		return benutzer.getBenutzername();
	}

	/**
	 * Gibt die Daten zurück, die der Benutzer bei dieser Aktion veraendert hat.
	 * 
	 * @return Die geanderten Daten.
	 */
	public LogGeanderteDaten getGeanderteDaten() {
		return geanderteDaten;
	}

	/**
	 * Gibt eine Nachricht zurueck, die im Log-Text erscheinen soll.
	 * 
	 * @return Die Nachricht.
	 */
	public String getNachricht() {
		return nachricht;
	}

}
