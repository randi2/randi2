package de.randi2.model.exceptions;

/**
  * <p>
 * Diese Klasse kapselt die Fehler, die innerhalb der Klasse Aktivierung auftreten koennen.
 * </p>
 * @author Andreas Freudling [afreudling@stud.hs-heilbronn.de]
 * @version $Id: AktivierungException.java 2448 2007-05-07 13:45:09Z afreudli $
 *
 */
public class AktivierungException extends BenutzerException{
	
    
    	/**
    	 * Fehlermeldung, wenn ein Aktivierungsbean erstellt wird ohne ein Benutzerkonto anzugeben.
    	 */
    	public static final String BENUTZERKONTO_NICHT_GESETZT="Ein Aktivierungslink muss immer einem Benutzer zugeordnet sein.";
    	
    	/**
    	 * Fehlermeldung,wenn der Aktivierungslink die falsche Laenge hat.
    	 */
    	public static final String AKTIVIERUNGSLINK_FALSCHE_LAENGE="Der Aktivierungslink hat die falsche Laenge";
	/**
	 * Erstellt eine Exception und haengt eine Fehlermeldung an.
	 * 
	 * @param fehlermeldung Fehlermeldung die an die Exception angehaengt wird.
	 */
	public AktivierungException(String fehlermeldung){
		super(fehlermeldung);
	}

}
