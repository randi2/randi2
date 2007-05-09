package de.randi2.utility;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;

import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.utility.Config;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Vector;

/**
 * Stellt die Moglichkeit des E-Mail-Versandes bereit. <br>
 * Im wesendlichen werden zwei Methoden zur Verf&uuml;gung gestellt:
 * <ul>
 * <li>{@link #sendenNachricht(PersonBean, String, String, PersonBean[])}:
 * Versendet eine Mail im Namen des Absenders</li>
 * <li>{@link #sendenSystemnachricht(String, String, PersonBean[])}: Versendet
 * eine Mail im Namen des Systems, es werden die Daten RANDIs als Absender
 * verwendet</li>
 * </ul>
 * Der Nachrichtendienst benoetigt folgende Libs:
 * <ul>
 * <li>Java-Mail (<a
 * href="http://java.sun.com/products/javamail/">http://java.sun.com/products/javamail/</a>)</li>
 * <li>Jakarta Commons Mail (<a
 * href="http://jakarta.apache.org/commons/email/index.html">http://jakarta.apache.org/commons/email/index.html</a>)</li>
 * <li>Java Activation Framework (<a
 * href="http://java.sun.com/products/javabeans/jaf/">http://java.sun.com/products/javabeans/jaf/</a>)</li>
 * </ul>
 * Die Konfiguration ist ueber die '<code>Nachrichtendienst.properties</code>'
 * moeglich. Aufbau der Conf-Datei:<br>
 * <code>## Server<br>
 * mailserver:<br>
 * account:<br>
 * password:<br>
 * ## Absenderdaten des Systems<br>
 * randi2Mailadresse:<br>
 * randi2Name:<br>
 * randi2Bounce:<br>
 * ## Misc<br>
 * debug:<br>
 * </code>
 * 
 * @author Benjamin Theel [BTheel@stud.hs-heilbronn.de]
 * @version $Id: Nachrichtendienst.java 1894 2007-04-13 17:51:20Z afreudli $
 */
public final class Nachrichtendienst {
/* TODO Anwendungslog noch einbinden
 * FRAGE Bounceadresse notwendig?
 */


    /**
     * Instanz des Nachrichtendiensts (realisiert als Singlton)
     */
    private static Nachrichtendienst instance = null;

    /**
     * Instanz des SystemBeans (realisiert als Singlton) Das SystemBean fasst
     * die Absenderdaten des Randi2-Systems und wird verwendet, wenn Nachrichten
     * von System versendet werden.
     */
    private PersonBean systemBean = null;
    
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());
    
    /**
     * Const des Nachrichtendienstes<br>
     * Laed die Properties, Instanziert den Dienst
     * 
     * @throws NachrichtendienstException
     *             {@link NachrichtendienstException#CONF_LADEN_FEHLGESCHLAGEN}
     */
    private Nachrichtendienst() throws NachrichtendienstException {
        try {
            // SystemBean/-Account anlegen
            systemBean = new PersonBean();
            systemBean.setEmail(Config.getProperty(Config.Felder.RELEASE_MAIL_RANDI2MAILADRESSE));
            systemBean.setNachname(Config.getProperty(Config.Felder.RELEASE_MAIL_RANDI2NAME));
        } catch (Exception e) {
            throw new NachrichtendienstException(
                    NachrichtendienstException.CONF_LADEN_FEHLGESCHLAGEN, e);
        }
    }
    
    /**
     * @return Die Instanz des Nachrichtendienstes
     * @throws NachrichtendienstException
     *             {@link NachrichtendienstException#CONF_LADEN_FEHLGESCHLAGEN}
     */
    public static Nachrichtendienst getInstance() throws NachrichtendienstException {
        if (instance == null) {
            instance = new Nachrichtendienst();
        }
        return instance;
    }

    

    /**
     * Versendet eine Nachricht an eine Menge von Empf&auml;ngern.<br>
     * Die Daten des AbsenderBeans werden als Absender in den Mail-Header
     * eingetragen <br>
     * Als Name des Empf&auml;ngers, bzw. des Absenders werden eine
     * Konkatenation aus '$Titel $Vorname $Nachname' aus dem entsprechendem
     * PersonBean verwendet.
     * 
     * @param absender
     *            Person(Bean), in dessen Name die Mail versendet wird
     * @param betreff
     *            Betreff der Nachricht
     * @param nachrichtentext
     *            Text der nachricht
     * @param empfaenger
     *            Empf&auml;nger der Mail. (Als {@link Vector})
     * @throws NachrichtendienstException
     *             {@link NachrichtendienstException#ABSENDER_UNDEFINIERT}
     *             {@link NachrichtendienstException#EMPFAENGER_UNDEFINIERT}
     */
    public void sendenNachricht(PersonBean absender, String betreff,
            String nachrichtentext, Vector<PersonBean> empfaenger)
            throws NachrichtendienstException {
        sendenNachricht(absender, betreff, nachrichtentext,
                (PersonBean[]) empfaenger.toArray(new PersonBean[empfaenger
                        .size()]));
    }

    /**
     * Versendet eine Nachricht an eine Menge von Empf&auml;ngern.<br>
     * Die Daten des AbsenderBeans werden als Absender in den Mail-Header
     * eingetragen <br>
     * Als Name des Empf&auml;ngers, bzw. des Absenders werden eine
     * Konkatenation aus '$Titel $Vorname $Nachname' aus dem entsprechendem
     * PersonBean verwendet.
     * 
     * @param absender
     *            Person(Bean), in dessen Name die Mail versendet wird
     * @param betreff
     *            Betreff der Nachricht
     * @param nachrichtentext
     *            Text der nachricht
     * @param empfaenger
     *            Empf&auml;nger der Mail. K&ouml;nnen als Einzeleintrag,
     *            Kommaseperierte Liste oder als {@link PersonBean}[] angegeben
     *            werden. (VarArgs)
     * @throws NachrichtendienstException
     *             {@link NachrichtendienstException#ABSENDER_UNDEFINIERT}
     *             {@link NachrichtendienstException#EMPFAENGER_UNDEFINIERT}
     * 
     */
    public void sendenNachricht(PersonBean absender, String betreff,
            String nachrichtentext, PersonBean... empfaenger)
            throws NachrichtendienstException {
        /*
         * FRAGE Sind die Checks erforderlich 
         * if ((nachrichtentext.length() == 0) ||
         * nachrichtentext.equals("")) { // Der boese Wolf} else if
         * ((subjekt.length() == 0) || subjekt.equalsIgnoreCase("")) { //
         * Rotkaeppchen} else
         */if ((empfaenger == null) || empfaenger.length == 0) {
            throw new NachrichtendienstException(
                    NachrichtendienstException.EMPFAENGER_UNDEFINIERT);
        } else if (absender == null) {
            throw new NachrichtendienstException(
                    NachrichtendienstException.ABSENDER_UNDEFINIERT);
        }

        SimpleEmail email = new SimpleEmail();

        email.setDebug(Boolean
                .valueOf(Config.getProperty(Config.Felder.RELEASE_MAIL_DEBUG)));

        email.setHostName(Config.getProperty(Config.Felder.RELEASE_MAIL_SERVER)); // Server
        // setzten
        email.setAuthentication(Config.getProperty(Config.Felder.RELEASE_MAIL_ACCOUNT), Config
                .getProperty(Config.Felder.RELEASE_MAIL_PASSWORD)); // Accountdaten setzten

         // Generieren/Setzten der AbsenderAdresse
            StringBuffer absenderName = new StringBuffer();

            if (absender.getTitel() != null) {
                absenderName.append(absender.getTitel() + " ");
            }
            if (absender.getVorname() != null) {
                absenderName.append(absender.getVorname() + " ");
            }
            if (absender.getNachname() != null) {
                absenderName.append(absender.getNachname() + " ");
            }

            try {
                email.setFrom(absender.getEmail(), absenderName.toString());
            } catch (EmailException e1) {
                // Wenns hier kracht, ist Holland in Not, da dann unsere E-Mail-Checks fehlerhaft sind - BTheel 
                throw new NachrichtendienstException(NachrichtendienstException.UNGUELTIGE_EMAILADRESSE);
            }

       

        StringBuffer empfaenderName;

        for (PersonBean aEmpfaenger : empfaenger) {
            // FRAGE Jede Mail fuer sich versenden oder via (B)CC? -Btheel
            empfaenderName = new StringBuffer();
            if (aEmpfaenger.getTitel() != null) {
                empfaenderName.append(aEmpfaenger.getTitel() + " ");
            }
            if (aEmpfaenger.getVorname() != null) {
                empfaenderName.append(aEmpfaenger.getVorname() + " ");
            }
            if (aEmpfaenger.getNachname() != null) {
                empfaenderName.append(aEmpfaenger.getNachname() + " ");
            }
            
            try {
                email.addTo(aEmpfaenger.getEmail(), empfaenderName.toString());
            } catch (EmailException e1) {
                // Wenns hier kracht, ist Holland in Not, da dann unsere E-Mail-Checks fehlerhaft sind -BTheel 
                throw new NachrichtendienstException(NachrichtendienstException.UNGUELTIGE_EMAILADRESSE);
            }

            email.setSubject(betreff);
            try{
                email.setMsg(nachrichtentext);
            }catch(Exception eMsg){
                throw new NachrichtendienstException(NachrichtendienstException.FEHLERHAFTER_NACHRICHTENTEXT);
            }

            try { // E-Mail fuellen
                email.send();
                logger.info("Nachricht an " + aEmpfaenger.getEmail()+" erfolgt");
            } catch (Exception e) {
                throw new NachrichtendienstException(NachrichtendienstException.VERSAND_FEHLGESCHLAGEN,e);
            }
        }

    }

    /**
     * Versendet eine Systemnachricht an eine Menge von Empf&auml;ngern. <br>
     * Als Absender wird Randi2 eingetragen (siehe
     * <code>Nachrichtendienst.properties</code>, {@link #systemBean}) <br>
     * Als Name des Empf&auml;, bzw. des Absenders werden eine Konkatenation aus
     * '$Titel $Vorname $Nachname' aus dem entsprechendem PersonBean verwendet.
     * 
     * @param betreff
     *            Betreff der Nachricht
     * @param nachrichtentext
     *            Text der Nachricht
     * @param empfaenger
     *            Empf&auml;nger der Mail. (Als {@link Vector})
     * @throws NachrichtendienstException
     *             {@link NachrichtendienstException#ABSENDER_UNDEFINIERT}
     *             {@link NachrichtendienstException#EMPFAENGER_UNDEFINIERT}
     */
    public void sendenSystemnachricht(String betreff, String nachrichtentext,
            Vector<PersonBean> empfaenger) throws NachrichtendienstException {
        sendenNachricht(systemBean, betreff, nachrichtentext,
                (PersonBean[]) empfaenger.toArray(new PersonBean[empfaenger
                        .size()]));
    }

    /**
     * Versendet eine Systemnachricht an eine Menge von Empf&auml;ngern. <br>
     * Als Absender wird Randi2 eingetragen (siehe
     * <code>Nachrichtendienst.properties</code>, {@link #systemBean}) <br>
     * Als Name des Empf&auml;, bzw. des Absenders werden eine Konkatenation aus
     * '$Titel $Vorname $Nachname' aus dem entsprechendem PersonBean verwendet.
     * 
     * @param betreff
     *            Betreff der Nachricht
     * @param nachrichtentext
     *            Text der Nachricht
     * @param empfaenger
     *            Empf&auml;nger der Mail. K&ouml;nnen als Einzeleintrag,
     *            Kommaseperierte Liste oder als {@link PersonBean}[] angegeben
     *            werden. (VarArgs)
     * @throws NachrichtendienstException
     *             {@link NachrichtendienstException#ABSENDER_UNDEFINIERT}
     *             {@link NachrichtendienstException#EMPFAENGER_UNDEFINIERT} 
     */
    public void sendenSystemnachricht(String betreff, String nachrichtentext,
            PersonBean... empfaenger) throws NachrichtendienstException {
        sendenNachricht(systemBean, betreff, nachrichtentext, empfaenger);

    }

}
