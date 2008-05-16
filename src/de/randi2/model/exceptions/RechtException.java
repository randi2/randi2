package de.randi2.model.exceptions;

import de.randi2.model.fachklassen.Rolle;
import de.randi2.utility.SystemException;


/**
 * Die RechtException wird bei fehlerhaften Methodenaufrufen der Klasse {@link Right} geworfen. 
 * @author BTheel [BTheel@stud.hs-heilbronn.de]
 */
@SuppressWarnings("serial")
public class RechtException extends SystemException {

    /**
     * Das uebergebene Argument war <code>null</code>
     */
    public static final String NULL_ARGUMENT = "Ungueltiges Argument: 'null'";
    /**
     * Das uebergebene Argument entspricht keinem Rollennamen ({@link Rolle.Rollen} ) 
     */
    public static final String UNGUELTIGES_ARGUMENT="Ungueltiges Argument";
    /**
     * Erstellt eine neue RechtException mit der uebergebenen Mitteilung
     * @param msg Mitteilung/ Begruendung der Exception
     */
    public RechtException(String msg){
        super(msg);
    }
}
