package de.randi2.utility;

import java.util.Properties;
import org.apache.log4j.Logger;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.randi2.controller.DispatcherServlet;

/**
 * Alle Daten aus den Konfigurationsdateien in conf/debug und conf/release werden ueber diese Klasse angesprochen.
 * Konfigurationen die die Datenbank betreffen finden sich in conf/release/proxool_cfg.xml
 * @author Andreas Freudling afreudling@stud.hs-heilbronn.de
 * @version $Id$
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
	private String releaseDateiname = "";

	/**
	 * Dateiname der Debug-Konfigurationsdatei
	 */
	private String debugDateiname = "";

	/**
	 * 
	 * Dateiname der Konfigurationsdatei für die Systemsperrung
	 */
	private String systemsperrungDateiname = "";

	/**
	 * Privater Konstruktor für den Singleton
	 * Liest Konfigurationsdateien
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
			systemsperrungConf
					.load(Config.class.getResourceAsStream("/"+systemsperrungDateiname));
			Logger.getLogger(this.getClass()).info(
					"Systemsperrung-Konfiguration geladen. "
							+ systemsperrungDateiname);

			debugConf.load(Config.class.getResourceAsStream("/"+debugDateiname));
			Logger.getLogger(this.getClass()).info(
					"Debug-Konfiguration geladen: " + debugDateiname);
			// ReleaseConf wird gefuellt
			releaseConf.load(Config.class.getResourceAsStream("/"+releaseDateiname));
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
	 * @param feld Name der Property
	 * @return Daten des gewuenschten Properties
	 */
	public static synchronized String getProperty(Felder feld) {
		if (singleton == null) {
			singleton = new Config();
		}
		return singleton.releaseConf.getProperty(feld + "");
	}

	/**
	 * Methode speichert die Sperrung des Systems persistent in einer Konfigurationsdatei
	 * @param fehlermeldung Fehlermeldung die gespeichert wird, wenn das System gesperrt werden soll
	 * @return true falls Sperrung erfolgreich, sonst false
	 */
	public synchronized boolean sperreSystem(String fehlermeldung) {
		if (singleton == null) {
			singleton = new Config();
		}
		singleton.systemsperrungConf.setProperty(
				Config.Felder.SYSTEMSPERRUNG_SYSTEMSPERRUNG.name(), "true");
		singleton.systemsperrungConf.setProperty(
				Config.Felder.SYSTEMSPERRUNG_FEHLERMELDUNG.name(),
				fehlermeldung);
		try {
			systemsperrungConf.store(new FileOutputStream(
					systemsperrungDateiname),
					"Persistente Speicherung der Systemsperrung");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TODO @Benny muss das jetzt weg oder nicht
		DispatcherServlet aDispatcherServlet = new DispatcherServlet();
		aDispatcherServlet.setSystemGesperrt(true); //angepasst an Aendungen in Servlet 
		aDispatcherServlet.setMeldungSystemGesperrt(fehlermeldung);
		return false;
	}

	/**
	 * Methode speichert die Entsperrung des Systems persistent ind einer Konfigurationsdatei.
	 * @return true, falls Speicherung erfolgreich war, sonst false
	 */
	public synchronized boolean entsperreSystem() {
		if (singleton == null) {
			singleton = new Config();
		}
		singleton.systemsperrungConf.setProperty(
				Config.Felder.SYSTEMSPERRUNG_SYSTEMSPERRUNG.name(), "false");
		singleton.systemsperrungConf.setProperty(
				Config.Felder.SYSTEMSPERRUNG_FEHLERMELDUNG.name(), "");
		try {
			systemsperrungConf.store(new FileOutputStream(
					systemsperrungDateiname),
					"Persistente Speicherung der Systemsperrung");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DispatcherServlet aDispatcherServlet = new DispatcherServlet();
		aDispatcherServlet.setSystemGesperrt(false);//angepasst an Aendungen in Servlet 
		aDispatcherServlet.setMeldungSystemGesperrt("");
		return false;
	}

	/**
	 * Alle Konfigurationsfelder aus den Konfigurationsdateien muesen hier hinterlegt werden.
	 * Dabei ist folgende Namenskonvention zu beachten: [Konfigurationstyp]_[Konfigurationsuntersektion]_[Exakte_Konfiguration].
	 * Der Name der Enum muss exakt gleich dem Namen in der Konfigurationsdatei sein.
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
		 *Absender-Name des Mailaccounts
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
	 * @param args Parameter bei Programmstart
	 */
	public static void main(String[] args) {
		for (Config.Felder e : Config.Felder.values()) {
			System.out.println(Config.getProperty(e));
		}
	}
}
