package de.randi2.utility;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Die Klasse Log4jInit initialisiert die Log4j Log-Files beim Starten des
 * Apache Tomcats.
 * 
 * @author Johannes Thoenes <jthoenes@stud.hs-heilbronn.de>
 * @version $Id$
 * 
 */
public class Log4jInit extends HttpServlet {

	/**
	 * Ermittelt den Pfad der Konfigurationsdatei und initialisiert die Log4j
	 * Log-Files.
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
}
