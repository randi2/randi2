package de.randi2.model.exceptions;

/**
 * 
 * @author Andreas Freudling [afreudling@stud.hs-heilbronn.de]
 * @version $Id$
 *
 */
public class AktivierungException extends BenutzerException{
	
	/**
	 * 
	 * @param fehlermeldung
	 */
	public AktivierungException(String fehlermeldung)
	{
		super(fehlermeldung);
	}

}
