package de.randi2.model.fachklassen;

import java.util.Collection;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import de.randi2.controller.Nachrichtendienst;
import de.randi2.model.fachklassen.beans.PersonBean;

/**
 * Kapselt eine Nachricht und implementiert die benoetigten Methoden.
 * 
 * @author BTheel [BTheel@stud.hs-heilbronn.de]
 * @version $Id$
 */
public class Nachricht {

    /**
     * SimpleMail Object
     */
    private SimpleEmail aMail;

    /**
     * DebugModus der Mail<br>
     * Default ist <code>false</code>
     */
    private boolean debug = false;

    /**
     * Instanziert eine neue SimpleMail und setzt den Ausgangsserver, den
     * Benutzer und das Passwort. Muss in jedem Konstruktor aufgerufen werden.
     */
    protected final void initMail() {
        aMail = new SimpleEmail();
        aMail.setHostName(Nachrichtendienst.getServer());
        aMail.setAuthentication(Nachrichtendienst.getUser(), Nachrichtendienst
                .getPwd());
        aMail.setDebug(debug);
    }

    /**
     * Einfacher Konstruktor, initialisiert die Mail mittels eines Aufrufes der
     * Methode {@link #initMail()}
     */
    public Nachricht() {
        initMail();
    }

    /**
     * Erstellt eine Mail aus den uebergebenen Parametern.<br>
     * Initialisiert die Mail mittels eines Aufrufes der Methode
     * {@link #initMail()}
     * 
     * @param absender
     *            Absender in Form (s)eines {@link PersonBean}
     * @param empfaenger
     *            Empfaenger in Form (s)eines {@link PersonBean}
     * @param betreff
     *            Betreff der Mail
     * @param text
     *            Nachrichtentext der Mail
     * @throws EmailException
     *             Siehe {@link #setAbsender(PersonBean)},{@link #setBetreff(String)},
     *             {@link #setText(String)}, {@link #addEmpfaenger(Collection)},
     *             {@link #addEmpfaenger(PersonBean)}
     */
    public Nachricht(PersonBean absender, PersonBean empfaenger,
            String betreff, String text) throws EmailException {
        initMail();
        this.setAbsender(absender);
        this.setBetreff(betreff);
        this.addEmpfaenger(empfaenger);
        this.setText(text);
    }

    /**
     * Erstellt eine Mail aus den uebergebenen Parametern.<br>
     * Initialisiert die Mail mittels eines Aufrufes der Methode
     * {@link #initMail()}
     * 
     * @param absender
     *            Absender in Form (s)eines {@link PersonBean}
     * @param empfaenger
     *            Empfaenger in Form einer typisieren Collection
     *            {@link PersonBean}
     * @param betreff
     *            Betreff der Mail
     * @param text
     *            Nachrichtentext der Mail
     * @throws EmailException
     *             Siehe {@link #setAbsender(PersonBean)},{@link #setBetreff(String)},
     *             {@link #setText(String)}, {@link #addEmpfaenger(Collection)},
     *             {@link #addEmpfaenger(PersonBean)}
     */
    public Nachricht(PersonBean absender, Collection<PersonBean> empfaenger,
            String betreff, String text) throws EmailException {
        initMail();
        this.setAbsender(absender);
        this.setBetreff(betreff);
        this.addEmpfaenger(empfaenger);
        this.setText(text);
    }

    /**
     * Versendet die angelegte E-Mail
     * 
     * @throws EmailException
     *             Das Versenden der Mail ist fehlgeschlagen
     * 
     */
    public final void senden() throws EmailException {
        aMail.send();
    }

    /**
     * Entnimmt dem uebergebenen PersonBean die E-Mail-Adresse und die
     * Namensangaben und setzt diese als Absender in die Mail.<br>
     * Als Name des Absenders wird aus dem Vornamen und dem Nachnamen
     * zusammengesetzt.
     * 
     * @param absender
     *            PersonBean des Absenders
     * @throws EmailException
     *             die E-Mailadresse ist ungueltig oder der Absender ist
     *             <code>null</code>
     */
    public final void setAbsender(PersonBean absender) throws EmailException {
        if (absender == null) {
            throw new EmailException("Absender ist null");
        }
        aMail.setFrom(absender.getEmail(), absender.getVorname() + " "
                + absender.getNachname());
    }

    /**
     * Setzt den Betreff einer Mail
     * 
     * @param betreff
     *            Betreff der Mail
     * @throws EmailException
     *             Wenn (betreff == null) || (betreff.length() == 0)
     */
    public final void setBetreff(String betreff) throws EmailException {
        if (betreff == null || betreff.length() == 0) {
            throw new EmailException("Betreff darf nicht leer sein");
        }
        aMail.setSubject(betreff);
    }

    /**
     * Fuegt einen neuen Empfaenger hinzu.<br>
     * Es wird die E-Mail-Adresse aus dem Bean gewonnen. Als Name des Empfaenger
     * wird aus dem Vornamen und dem Nachnamen zusammengesetzt
     * 
     * @param empfaenger
     *            {@link PersonBean} des Empfaenger
     * @throws EmailException
     *             Die E-Mail-Adresse ist ungueltig oder Empfaenger ist null
     */
    public final void addEmpfaenger(PersonBean empfaenger)
            throws EmailException {
        if (empfaenger == null) {
            throw new EmailException("Empfaenger ist null");
        }
        aMail.addTo(empfaenger.getEmail(), empfaenger.getVorname() + " "
                + empfaenger.getNachname());
        System.out.println("Adde " + empfaenger.getEmail());
    }

    /**
     * Fuegt der Mail eine Liste von Empfaengern hinzu.<br>
     * Fuer jedes ListenItem wird die Methode {@link #addEmpfaenger(PersonBean)}
     * aufgerufen.
     * 
     * @param empfaenger
     *            Liste der Empfaenger
     * @throws EmailException
     *             Die E-Mail-Adresse (mindestens) eines Empfaengers ist
     *             ungueltig oder die Collecion ist <code>null</code>
     */
    public final void addEmpfaenger(Collection<PersonBean> empfaenger)
            throws EmailException {
        if (empfaenger == null) {
            throw new EmailException("Empfaenger ist null");
        }
        for (PersonBean bean : empfaenger) {
            addEmpfaenger(bean);

        }
    }

    /**
     * Setzt den Nachrichtentext der E-Mail
     * 
     * @param text
     *            Text der Mitteilung
     * @throws EmailException
     *             Wenn (text == null) || (text.length() == 0)
     */
    public final void setText(String text) throws EmailException {
        aMail.setMsg(text);
    }

    /**
     * Setzt den Debugmodus der Mail (Default == <code>false</code>)
     * 
     * @param debug
     *            Debug-Modus (de-)aktivieren
     */
    public final void setDebug(boolean debug) {
        aMail.setDebug(debug);
        this.debug = debug;
    }

    /**
     * Setzt die BounceAdresse der Mail.
     * 
     * @param bounceopfer
     *            Person, die die Bouncemails erhalten soll
     * @throws EmailException
     *             Der Empfaenger ist <code>null</code>
     */
    public final void setBounce(PersonBean bounceopfer) throws EmailException {
        if (bounceopfer == null) {
            throw new EmailException("Empfaenger der Bouncemails ist null");
        }
        aMail.setBounceAddress(bounceopfer.getEmail());
    }

}
