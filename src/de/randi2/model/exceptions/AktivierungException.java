package de.randi2.model.exceptions;

/**
  * <p>
 * Diese Klasse kapselt die Fehler, die innerhalb der Klasse Aktivierung auftreten koennen.
 * </p>
 * @author Andreas Freudling [afreudling@stud.hs-heilbronn.de]
 * @version $Id$
 *
 */
public class AktivierungException extends BenutzerException{
	
	/**
	 * Erstellt eine Exception und haengt eine Fehlermeldung an.
	 * 
	 * @param fehlermeldung Fehlermeldung die an die Exception angehaengt wird.
	 */
	public AktivierungException(String fehlermeldung){
		super(fehlermeldung);
	}

}
