package de.randi2.model.fachklassen;

import java.util.Collection;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;

import com.meterware.httpunit.javascript.JavaScript.Link;

import de.randi2.controller.Nachrichtendienst;
import de.randi2.datenbank.LogDemo;
import de.randi2.model.exceptions.NachrichtException;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.utility.SystemException;

/**
 * Kapselt eine Nachricht und implementiert die benoetigten Methoden.
 * 
 * @author BTheel [BTheel@stud.hs-heilbronn.de]
 * @version $Id$
 */
public class Nachricht {

    /**
     * Schlaegt der Versand fehl, wird eine {@link SystemException} mit dieser
     * Mitteilung geworfen
     */
    public static final String NACHRICHTENVERSAND_FEHLGESCHLAGEN = "Es ist ein Fehler beim Nachrichtenversand aufgetreten. Bitte verst&auml;ndigen Sie den Systemadministrator.";

    
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
        //falls Benutzer und Passwort, setzte die Authentication
        if (Nachrichtendienst.getUser()!=null && Nachrichtendienst.getUser().trim()!="" &&
        	Nachrichtendienst.getPwd()!=null && Nachrichtendienst.getPwd().trim()!="")  
        	aMail.setAuthentication(Nachrichtendienst.getUser(), Nachrichtendienst.getPwd());
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
     * @throws NachrichtException
     *             Siehe {@link #setAbsender(PersonBean)},{@link #setBetreff(String)},
     *             {@link #setText(String)}, {@link #addEmpfaenger(Collection)},
     *             {@link #addEmpfaenger(PersonBean)}
     */
    public Nachricht(PersonBean absender, PersonBean empfaenger,
            String betreff, String text) throws NachrichtException {
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
     * @throws NachrichtException
     *             Siehe {@link #setAbsender(PersonBean)},{@link #setBetreff(String)},
     *             {@link #setText(String)}, {@link #addEmpfaenger(Collection)},
     *             {@link #addEmpfaenger(PersonBean)}
     */
    public Nachricht(PersonBean absender, Collection<PersonBean> empfaenger,
            String betreff, String text) throws NachrichtException {
        initMail();
        this.setAbsender(absender);
        this.setBetreff(betreff);
        this.addEmpfaenger(empfaenger);
        this.setText(text);
    }

    /**
     * Versendet die angelegte E-Mail<br>
     * Es
     * 
     * @throws NachrichtException
     *             {@link NachrichtException#ABSENDER_NULL},
     *             {@link NachrichtException#LEERER_BETREFF},
     *             {@link NachrichtException#EMPFEANGER_NULL}
     * @throws SystemException
     *             Das Versenden der Mail ist Fehlgeschlagen {@link #NACHRICHTENVERSAND_FEHLGESCHLAGEN}
     *             
     * 
     */
    public final void senden() throws NachrichtException, SystemException {
        if (aMail.getFromAddress() == null) {
            throw new NachrichtException(NachrichtException.ABSENDER_NULL);
        } else if (aMail.getSubject() == null) {
            throw new NachrichtException(NachrichtException.LEERER_BETREFF);
        }
        // Inhalt sollte vorhangen sein, jedoch kann dieser nicht wieder der
        // Mail gewonnen werden, daher keine Pruefung
        try {
            aMail.send();
        } catch (EmailException e) {
            if (e.getMessage().equals("At least one receiver address required")) {
                throw new NachrichtException(NachrichtException.EMPFEANGER_NULL);
            }
            Logger.getLogger(this.getClass()).debug("Mailversand fehlgeschlagen",e);
            e.printStackTrace();
            throw new SystemException(NACHRICHTENVERSAND_FEHLGESCHLAGEN);
        }
    }

    /**
     * Entnimmt dem uebergebenen PersonBean die E-Mail-Adresse und die
     * Namensangaben und setzt diese als Absender in die Mail.<br>
     * Als Name des Absenders wird aus dem Vornamen und dem Nachnamen
     * zusammengesetzt.
     * 
     * @param absender
     *            PersonBean des Absenders
     * @throws NachrichtException
     *             {@link NachrichtException#ABSENDER_NULL},
     *             {@link NachrichtException#PERSONBEAN_IST_FILTER},
     *             {@link NachrichtException#UNGUELTIGE_ADRESSE}
     */
    public final void setAbsender(PersonBean absender)
            throws NachrichtException {
        if (absender == null) {
            throw new NachrichtException(NachrichtException.ABSENDER_NULL);
        }
        if (absender.isFilter()) {
            throw new NachrichtException(
                    NachrichtException.PERSONBEAN_IST_FILTER);
        }
        try {
            aMail.setFrom(absender.getEmail(), absender.getVorname() + " "
                    + absender.getNachname());
        } catch (EmailException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Setzt den Betreff einer Mail
     * 
     * @param betreff
     *            Betreff der Mail
     * @throws EmailException
     *             Wenn (betreff == null) || (betreff.length() == 0)
     */
    public final void setBetreff(String betreff) throws NachrichtException {
        if (betreff == null || betreff.length() == 0) {
            throw new NachrichtException(NachrichtException.LEERER_BETREFF);
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
     * @throws NachrichtException
     *             {@link NachrichtException#ABSENDER_NULL},
     *             {@link NachrichtException#PERSONBEAN_IST_FILTER},
     *             {@link NachrichtException#UNGUELTIGE_ADRESSE}
     * 
     */
    public final void addEmpfaenger(PersonBean empfaenger)
            throws NachrichtException {
        if (empfaenger == null) {
            throw new NachrichtException(NachrichtException.EMPFEANGER_NULL);
        }
        if (empfaenger.isFilter()) {
            throw new NachrichtException(
                    NachrichtException.PERSONBEAN_IST_FILTER);
        }
        try {
            aMail.addTo(empfaenger.getEmail(), empfaenger.getVorname() + " "
                    + empfaenger.getNachname());
        } catch (EmailException e) {
            // Die EMailadresse wird von dem JavaMail Framework als ungueltig
            // agbewiesen.
            // una problema grande!
            throw new NachrichtException(NachrichtException.UNGUELTIGE_ADRESSE);
        }

    }

    /**
     * Fuegt der Mail eine Liste von Empfaengern hinzu.<br>
     * Fuer jedes ListenItem wird die Methode {@link #addEmpfaenger(PersonBean)}
     * aufgerufen.
     * 
     * @param empfaenger
     *            Liste der Empfaenger
     * @throws NachrichtException
     *             {@link NachrichtException#EMPFEANGER_NULL}, vgl.
     *             {@link Nachricht#addEmpfaenger(PersonBean)}
     */
    public final void addEmpfaenger(Collection<PersonBean> empfaenger)
            throws NachrichtException {
        if (empfaenger == null) {
            throw new NachrichtException(NachrichtException.EMPFEANGER_NULL);
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
     *             {@link NachrichtException#LEERER_TEXT}
     */
    public final void setText(String text) throws NachrichtException {
        try {
            aMail.setMsg(text);
        } catch (EmailException e) {
            throw new NachrichtException(NachrichtException.LEERER_TEXT);
        }
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
     * @throws NachrichtException @
     *             {@link NachrichtException#BOUNCE_IST_NULL}
     */
    public final void setBounce(PersonBean bounceopfer)
            throws NachrichtException {
        if (bounceopfer == null) {
            throw new NachrichtException(NachrichtException.BOUNCE_IST_NULL);
        }
        aMail.setBounceAddress(bounceopfer.getEmail());
    }

}
