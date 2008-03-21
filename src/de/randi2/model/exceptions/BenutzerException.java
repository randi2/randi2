package de.randi2.model.exceptions;

import org.apache.log4j.Logger;

/**
 * Die BenutzerException ist eine Exception in der Benutzerausnahmen gekapselt
 * werden koennen.
 * 
 * @author Johannes Thoenes [jthoenes@stud.hs-heilbronn.de]
 * @version $Id: BenutzerException.java 2418 2007-05-04 14:37:12Z jthoenes $
 * 
 */
public abstract class BenutzerException extends Exception {

	/**
	 * Konstruktor der eine Nachricht eintraegt.
	 * 
	 * @param msg
	 *            Nachricht.
	 */
	public BenutzerException(String msg) {
		super(msg);
		Logger.getLogger(this.getClass()).debug("Benutzerfehler: " + msg);
	}

}
