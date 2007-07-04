package de.randi2.model.exceptions;

/**
 * Fehler, die beim Nachrichtenversand auftreten und an den Benutzer
 * weitergeleitet werden.
 * 
 * @author Benjamin Theel [BTheel@stud.hs-heilbronn.de]
 * @version $Id$
 */
@SuppressWarnings("serial")
public class NachrichtException extends BenutzerException {

    /**
     * Der uebergebene Parameter war <code>null</code>
     */
    public static final String BOUNCE_IST_NULL = "Empfaenger der Bouncemails ist null";

    /**
     * Wenn (text == null) || (text.length() == 0)
     */
    public static final String LEERER_TEXT = "Leeren Nachrichtentext &uuml;bergeben";

    /**
     * Uebergebenes {@link PersonBean} ist <code>null</code>
     */
    public static final String EMPFAENGER_NULL = "Empf&auml;nger ist null";

    /**
     * PersonBean ist Filter, somit nicht zwingend vollstaendig, bzw. mit
     * korrekten Werten gefuellt. Als Empfaenger abgewiesen
     */
    public static final String PERSONBEAN_IST_FILTER = "Empf&auml;nger ist Filter";

    /**
     * DIe EMailAdresse des Empfaengers ist ungueltig.
     */
    public static final String UNGUELTIGE_ADRESSE = "Die Adresse des Empf&auml;ngers ist ung&uuml;ltig";

    /**
     * Wenn (betreff == null) || (betreff.length() == 0)
     */
    public static final String LEERER_BETREFF = "Betreff darf nicht leer sein";

    /**
     * Absender ist null
     */
    public static final String ABSENDER_NULL = "Absender ist null";


    public NachrichtException(final String msg) {
        super(msg);
    }
}
