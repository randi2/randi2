package de.randi2.datenbank.exceptions;

/**
 * Diese Exception zeigt einen generellen Fehler beim Datenbankzugriff auf.
 * 
 * @version $Id: DatenbankFehlerException.java 1205 2007-02-03 13:50:44Z
 *          jthoenes $
 * @author Daniel Haehn <dhaehn@stud.hs-heilbronn.de>
 */
@SuppressWarnings("serial")
public class DatenbankFehlerException extends Exception {

    public static final String ARGUMENT_IST_NULL = "Das uebergebene Argument ist null";
    public static final String SUCHOBJEKT_IST_KEIN_FILTER = "Beim uebergebenen Objekt wurde der Filter nicht gesetzt";

    public DatenbankFehlerException(){
        super();
    }
    
    public DatenbankFehlerException(String msg) {
        super(msg);
    }

}
