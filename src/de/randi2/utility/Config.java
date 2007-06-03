package de.randi2.utility;

import java.util.Properties;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.meterware.httpunit.HttpUnitUtils;

import de.randi2.datenbank.Datenbank;

import java.io.FileOutputStream;


/**
 * Alle Daten aus den Konfigurationsdateien in conf/debug und conf/release
 * werden ueber diese Klasse angesprochen. Konfigurationen die die Datenbank
 * betreffen finden sich in conf/release/proxool_cfg.xml
 * 
 * @author Andreas Freudling [afreudling@stud.hs-heilbronn.de]
 * @version $Id: Config.java 2457 2007-05-08 11:21:46Z btheel $
 * 
 */
public final class Config {
    /**
     * Varible fuer den Singleton
     */
    private static Config singleton = null;

    /**
     * Properties Objekt fuer die Debug-Konfigurationsdatei
     */
    private Properties debugConf = null;

    /**
     * Properties Objekt fuer die Release-Konfigurationsdatei
     */
    private Properties releaseConf = null;

    /**
     * Properties Objekt fuer die Systemsperrung-Konfigurationsdatei
     */
    private Properties systemsperrungConf = null;

    /**
     * Dateiname der Release-Konfigurationsdatei
     */
    private static String releaseDateiname = "";

    /**
     * Dateiname der Debug-Konfigurationsdatei
     */
    private static String debugDateiname = "";

    /**
     * 
     * Dateiname der Konfigurationsdatei für die Systemsperrung
     */
    private static String systemsperrungDateiname = "";

    /**
     * Privater Konstruktor für den Singleton Liest Konfigurationsdateien
     */
    private Config() {
        releaseDateiname = "conf/release/release.conf";
        debugDateiname = "conf/debug/debug.conf";
        systemsperrungDateiname = "conf/release/systemsperrung.conf";

        systemsperrungConf = new Properties();
        debugConf = new Properties(systemsperrungConf);
        // release mit Oberproperty
        releaseConf = new Properties(debugConf); // 
        try {
            // DebugConf wird gefuellt
            systemsperrungConf.load(Config.class.getResourceAsStream("/"
                    + systemsperrungDateiname));
            Logger.getLogger(this.getClass()).info(
                    "Systemsperrung-Konfiguration geladen. "
                            + systemsperrungDateiname);

            debugConf.load(Config.class.getResourceAsStream("/"
                    + debugDateiname));
            Logger.getLogger(this.getClass()).info(
                    "Debug-Konfiguration geladen: " + debugDateiname);
            // ReleaseConf wird gefuellt
            releaseConf.load(Config.class.getResourceAsStream("/"
                    + releaseDateiname));
            Logger.getLogger(this.getClass()).info(
                    "Release-Konfiguraion geladen: " + releaseDateiname);

        } catch (Exception e) {
            Logger.getLogger(this.getClass()).fatal(
                    "Fehlerhafte Konfigurationsdateien:", e);
            // e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Um Property-Informationen egal welcher Konfigurationsdatei auszulesen.
     * 
     * @param feld
     *            Name der Property
     * @return Daten des gewuenschten Properties
     */
    public static synchronized String getProperty(Felder feld) {
        if (singleton == null) {
            singleton = new Config();
        }
        return singleton.releaseConf.getProperty(feld + "");
    }

    /**
     * Speichert den Systemstatus in der entsprechenden Config-Datei
     * 
     * @param isSystemGesperrt
     *            neuer Systemstatus [gesperrt=true|gesperrt=false]
     * @return <code>true</code>, wenn der Wert in der Config gespeichert
     *         werden konnte, anderenfalls <code>false</code>
     */
    public static synchronized boolean setSystemGesperrt(
            boolean isSystemGesperrt) {
        if (singleton == null) {
            singleton = new Config();
        }
        singleton.systemsperrungConf.setProperty(
                Config.Felder.SYSTEMSPERRUNG_SYSTEMSPERRUNG.name(), Boolean
                        .toString(isSystemGesperrt));
        try {
            singleton.systemsperrungConf.store(new FileOutputStream(Config.class.getResource("/"+
                    systemsperrungDateiname).getPath()),
                    "Persistente Speicherung der Systemsperrung");
        } catch (Exception e) {
            Logger.getLogger(Config.class).error(
                    "Fehler bei Speicherung der Systemsperrung", e);
            return false;
        }
        return true;
    }

    /**
     * Setzt die Mitteilung zur Systemsperrung neu in dem Config-File <br>
     * Die Methode erstetzt HTML-Entities in der Mitteilung automatisch.
     * 
     * @param mitteilung
     *            Neue Mitteilung
     * @return <code>true</code>, sofern die Mitteilung gespeichert werden
     *         konnte, anderenfalls false
     */
    public static synchronized boolean setMitteilungSystemsperrung(
            String mitteilung) {
        if (singleton == null) {
            singleton = new Config();
        }
        
        mitteilung = StringEscapeUtils.escapeHtml(mitteilung);
        
        singleton.systemsperrungConf.setProperty(
                Config.Felder.SYSTEMSPERRUNG_FEHLERMELDUNG.name(), mitteilung);
        try {
            singleton.systemsperrungConf.store(new FileOutputStream(Config.class.getResource("/"+
                    systemsperrungDateiname).getPath()),
                    "Persistente Speicherung der Systemsperrung");
        } catch (Exception e) {
            Logger.getLogger(Config.class).error(
                    "Fehler bei Speicherung der Systemsperrung", e);
            return false;
        }
        return true;
    }

    /**
     * Alle Konfigurationsfelder aus den Konfigurationsdateien muesen hier
     * hinterlegt werden. Dabei ist folgende Namenskonvention zu beachten:
     * [Konfigurationstyp]_[Konfigurationsuntersektion]_[Exakte_Konfiguration].
     * Der Name der Enum muss exakt gleich dem Namen in der Konfigurationsdatei
     * sein.
     */
    public enum Felder {
        /**
         * Hostname des Selenium Servers
         */
        DEBUG_SELENIUM_SERVER_HOST,
        /**
         * Port des Selenium Servers
         */
        DEBUG_SELENIUM_SERVER_PORT,
        /**
         * Pfad zur Exe-Datei des Browsers
         */
        DEBUG_SELENIUM_FIREFOX_LOCATION,
        /**
         * Startseite fuer den Selenium Server
         */
        DEBUG_SELENIUM_START_URL,

        /**
         * Hostname des Mail Servers
         */
        RELEASE_MAIL_SERVER,

        /**
         * Benutzername fuer den Mailaccount
         */
        RELEASE_MAIL_ACCOUNT,

        /**
         * Passwort fuer den Mailaccount
         */
        RELEASE_MAIL_PASSWORD,

        /**
         * Absenderadresse fuer den Mailaccount
         */
        RELEASE_MAIL_RANDI2MAILADRESSE,

        /**
         * Absender-Name des Mailaccounts
         */
        RELEASE_MAIL_RANDI2NAME,
        /**
         * Email-Adresse bei denen die Emails "bouncen"
         */
        RELEASE_MAIL_RANDI2BOUNCE,
        /**
         * Debug gibt einen Log auf der Console aus
         */
        RELEASE_MAIL_DEBUG,
        
        /**
         * RequestAttribut fuer die Aktivierung	
         */
        RELEASE_AKTIVIERUNG_ATTRIBUT,
        
        /**
         * Link PRAEFIX fuer den Aktivierungslink,ohne Nummer
         */
        RELEASE_AKTIVIERUNG_LINK,
        
        /**
         * Gueltigkeitsdauer des Aktivierungsllinks in Stunden
         */
        RELEASE_AKTIVIERUNG_GUELTIGKEIT,

        /**
         * Speicherung ob System gesperrt ist oder nicht.
         */
        SYSTEMSPERRUNG_SYSTEMSPERRUNG,

        /**
         * Fehlermeldung die fuer die Systemsperrung gespeichert wird.
         */
        SYSTEMSPERRUNG_FEHLERMELDUNG,
    }

    /**
     * Main-Methode zum Testen
     * 
     * @param args
     *            Parameter bei Programmstart
     */
    public static void main(String[] args) {
        for (Config.Felder e : Config.Felder.values()) {
            System.out.println(Config.getProperty(e));
        }
    }
}
