package de.randi2.utility;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Die Klasse Log4jInit initialisiert die Log4j Log-Files beim Starten des
 * Apache Tomcats.
 * 
 * @author Johannes Thoenes [jthoenes@stud.hs-heilbronn.de]
 * @version $Id: Log4jInit.java 1828 2007-04-06 18:31:47Z jthoenes $
 * 
 */
public class Log4jInit extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Ermittelt den Pfad der Konfigurationsdatei und initialisiert die Log4j
	 * Log-Files. Diese Methode ist für den Serverbetrieb im Netz.
	 */
	public void init() {
		// System.getProperties().list(System.out);
		String prefix = getServletContext().getRealPath("/");
		String dateiName = getInitParameter("log4j-init-file");
		if (dateiName != null) {
			PropertyConfigurator.configureAndWatch(prefix + dateiName);
			Logger.getLogger(this.getClass()).debug(
					"Log4J System konfigueriert.");
		}
	}

	/**
	 * Ermittelt den Pfad der Konfigurationsdatei und initialisiert die Log4j
	 * Log-Files. Diese Methode ist für den Debug- und Testmodus (lokal).
	 */
	public static void initDebug() {
		// System.getProperties().list(System.out);
		String dateiPfad = "WebContent/WEB-INF/log4j.lcf";

		PropertyConfigurator.configureAndWatch(dateiPfad);
		Logger.getLogger(Log4jInit.class).debug("Log4J System konfigueriert.");

	}
}
