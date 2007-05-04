package de.randi2.model.exceptions;

import org.apache.log4j.Logger;

/**
 * Die Randi2Exception ist eine Exception in der Systemfehler gekapselt werden
 * koennen. Fehler die von ihr gekapselt werden, werden automatisch geloggt und
 * dem Benutzer wird eine verstaendliche Warnung ausgegeben, die auf der
 * Oberflaeche angezeigt werden kann.
 * 
 * @author Johannes Thoenes [jthoenes@stud.hs-heilbronn.de]
 * @version $Id$
 * 
 */
public abstract class Randi2Exception extends Exception {

	/**
	 * Die nicht technische Fehlermeldung die dem Benutzer angezeigt werden
	 * soll.
	 */
	private static final String FEHLERMELDUNG = "Es ist ein nicht behebbarer Fehler im System aufgetreten. Bitte verst&auml;ndigen Sie den Systemadministrator.";

	/**
	 * Loggt den Error in die Log-Datei.
	 * 
	 */
	private void reportError() {
		Logger log = Logger.getLogger(this.getClass());
		log.error("Nicht behebbarer Systemfehler.", this);
	}

	/**
	 * Konstruktor mit der Angabe eine spezialisierten Fehlermeldung.
	 * 
	 * @param fehlermeldung
	 *            Nicht-technische Fehlermeldung die dem Benutzer angezeigt
	 *            werden soll.
	 */
	public Randi2Exception(String fehlermeldung) {
		super(fehlermeldung);
		this.reportError();
	}

	/**
	 * Konstruktor falls dem Benutzer die Standartfehlermeldung angezeigt werden
	 * soll.
	 * 
	 */
	public Randi2Exception() {
		super(FEHLERMELDUNG);
		this.reportError();
	}

}
