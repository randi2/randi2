package de.randi2.utility;

import java.util.Properties;
import org.apache.log4j.Logger;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.randi2.controller.DispatcherServlet;

/**
 * @author Andreas Freudling afreudling@stud.hs-heilbronn.de
 * @version $Id$
 * 
 */
public class Config {
	private static Config singleton = null;

	private Properties debugConf = null;

	private Properties releaseConf = null;

	private Properties systemsperrungConf = null;

	private String releaseDateiname = "";

	private String debugDateiname = "";

	private String systemsperrungDateiname = "";

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
	 * @param feld
	 * @return
	 */
	public static synchronized String getProperty(Felder feld) {
		if (singleton == null) {
			singleton = new Config();
		}
		return singleton.releaseConf.getProperty(feld + "");
	}

	/**
	 * @param fehlermeldung
	 * @return
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
		DispatcherServlet aDispatcherServlet = new DispatcherServlet();
		aDispatcherServlet.setIstSystemGesperrt(true);
		aDispatcherServlet.setSystemsperrungFehlermeldung(fehlermeldung);
		return false;
	}

	/**
	 * @return
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
		aDispatcherServlet.setIstSystemGesperrt(false);
		aDispatcherServlet.setSystemsperrungFehlermeldung("");
		return false;
	}

	/**
	 * 
	 * 
	 */
	public enum Felder {
		/**
		 * 
		 */
		DEBUG_SELENIUM_SERVER_HOST,
		/**
		 * 
		 */
		DEBUG_SELENIUM_SERVER_PORT,
		/**
		 * 
		 */
		DEBUG_SELENIUM_FIREFOX_LOCATION,
		/**
		 * 
		 */
		DEBUG_SELENIUM_START_URL,
		/**
		 * 
		 */
		RELEASE_MAIL_SERVER,
		/**
		 * 
		 */
		RELEASE_MAIL_ACCOUNT,
		/**
		 * 
		 */
		RELEASE_MAIL_PASSWORD,
		/**
		 * 
		 */
		RELEASE_MAIL_RANDI2MAILADRESSE,
		/**
		 * 
		 */
		RELEASE_MAIL_RANDI2NAME,
		/**
		 * 
		 */
		RELEASE_MAIL_RANDI2BOUNCE,
		/**
		 * 
		 */
		RELEASE_MAIL_DEBUG,

		/**
		 * 
		 */
		SYSTEMSPERRUNG_SYSTEMSPERRUNG,

		/**
		 * 
		 */
		SYSTEMSPERRUNG_FEHLERMELDUNG,
		
		/**
		 * 
		 */
		RELEASE_DB_HOST,
		/**
		 * 
		 */
		RELEASE_DB_PORT,
		/**
		 * 
		 */
		RELEASE_DB_NUTZERNAME,
		/**
		 * 
		 */
		RELEASE_DB_PASSWORT
	}

	public static void main(String[] args) {
		for (Config.Felder e : Config.Felder.values()) {
			System.out.println(Config.getProperty(e));
		}
	}
}
