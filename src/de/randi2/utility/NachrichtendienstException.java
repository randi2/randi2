package de.randi2.utility;

/**
 * <p>
 * Diese Klasse kappselt die Fehler, die innerhalb der Klasse Nachrichtendienst
 * auftreten koennen.
 * </p>
 * 
 * @author Benjamin Theel [BTheel@stud.hs-heilbronn.de]
 * @version $Id$
 */
@SuppressWarnings("serial")
public class NachrichtendienstException extends Exception {

    /**
     * Das Laden der Konfigurationsdatei ist fehlgeschlagen. Als Grund ist die
     * Ausgangsexception angehaengt
     */
    public static final String CONF_LADEN_FEHLGESCHLAGEN = "Die angegebene Konfigurationsdatei konnte nicht gelesen werden";

    /**
     * Absender der Nachricht ist <code>null</code>
     */
    public static final String ABSENDER_UNDEFINIERT = "Der Absender der Nachricht ist nicht definiert";

    /**
     * Die Meng der Empfaenger ist leer oder <code>null</code>
     */
    public static final String EMPFAENGER_UNDEFINIERT = "Kein(e) Empfaenger definiert";

    /**
     * Das Versenden einer Nachricht ist fehlgeschlagen. Die Ausgangsexception
     * ist angehaengt.
     */
    public static final String VERSAND_FEHLGESCHLAGEN = "Das Versenden einer Nachricht ist fehlgeschlagen";

    /**
     * Die E-Mail-Adresse des Absenders ist ungueltig
     * 
     */
    public static final String UNGUELTIGE_EMAILADRESSE = "Die E-Mail-Adresse des Absenders ist ungueltig!";
    /*
     * Die Exception sollte NIE auftauchen, ansonsten sind die Checks des
     * PersonenBeans Muell!!! - BTheel
     */

    /**
     * Die Exceptiopn bedeutet, das in dem Text Bestandteile
     * enthalten sind, die {@link javax.mail.internet.MimeBodyPart} nicht
     * vertraegt
     */
    public static final String FEHLERHAFTER_NACHRICHTENTEXT = "Der Nachrichtentext enthaelt ungueltige Bestanteile";

    // TODO Bitte hier eine Auswahl von wirklich verwendeten 
    // Konstruktoren treffen und korrigieren.
    
    public NachrichtendienstException() {
        super();
    }

    public NachrichtendienstException(String message) {
        super(message);
    }

    public NachrichtendienstException(Throwable cause) {
        super(cause);
    }

    public NachrichtendienstException(String message, Throwable cause) {
        super(message, cause);
    }

}
