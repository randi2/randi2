package de.randi2.utility;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Die Klasse Randi2LogLayout implementiert das Log-Layout fuer das fachliche
 * Log von Randi2.
 * 
 * @author Johannes Thoenes [jthoenes@stud.hs-heilbronn.de]
 * @version $Id: LogLayout.java 1804 2007-04-04 16:58:20Z jthoenes $
 */
public class LogLayout extends Layout {

	/**
	 * Oberklasse fuer das fachliche Log.
	 */
	private static final String OBERKLASSE = "Randi2.";

	/**
	 * Klasse fuer die Ein- und Auslogvorgaenge.
	 */
	public static final String LOGIN_LOGOUT = OBERKLASSE + "LoginLogout";

	/**
	 * Klasse fuer die Datenaenderung.
	 */
	public static final String DATENAENDERUNG = OBERKLASSE + "Datenaenderung";

    /**
     * Klasse fuer die Administration
     */
    public static final String ADMINISTRATION = OBERKLASSE + "Administration";
    
	/**
	 * Klasse fuer Rechteverletzugen.
	 */
	public static final String RECHTEVERLETZUNG = OBERKLASSE
			+ "Rechteverletzung";

	/**
	 * Standartgroesse des String-Buffers fuer das Log.
	 */
	private static final int DEFAULT_SIZE = 256;

	/**
	 * Maximale Groesse des String-Buffers fuer das Log.
	 */
	private static final int UPPER_LIMIT = 2048;

	/**
	 * String-Buffer fuer die Log-Eintraege.
	 */
	private StringBuffer buf = new StringBuffer(DEFAULT_SIZE);

	/**
	 * Aktiviert Optionen. In dieser Implementierung keine Auswirkungen.
	 * 
	 * @see org.apache.log4j.Layout#activateOptions()
	 */
	@Override
	public void activateOptions() {
	}

	/**
	 * Formatiert das LoggingEvent in einen String um, der letzendlich geloggt
	 * werden kann.
	 * 
	 * @param event
	 *            Das aufgetretene Event, dass geloggt werfen soll.
	 * @return Der formatierte Log-Eintrags String.
	 * @see org.apache.log4j.Layout#format(LoggingEvent)
	 */
	@Override
	public String format(LoggingEvent event) {
		if (event.getMessage() instanceof LogAktion) {

			LogAktion aktion = (LogAktion) event.getMessage();

			// Der String Buffer ist von dem Log4J-XMLLayout geklaut ;-)
			if (buf.capacity() > UPPER_LIMIT) {
				buf = new StringBuffer(DEFAULT_SIZE);
			} else {
				buf.setLength(0);
			}

			// Bauen des Log-Eintrag
			buf.append("<randi2log:aktion>\r\n");

			String zeitString = DateFormat.getDateTimeInstance(
					DateFormat.MEDIUM, DateFormat.MEDIUM).format(
					new Date(event.timeStamp));
			buf.append("\t<randi2log:zeit>" + zeitString
					+ "</randi2log:zeit>\r\n");

			buf.append("\t<randi2log:art>" + event.getLoggerName()
					+ "</randi2log:art>\r\n");

			buf.append("\t<randi2log:stufe>" + event.getLevel()
					+ "</randi2log:stufe>\r\n");

			buf.append("\t<randi2log:nachricht>" + aktion.getNachricht()
					+ "</randi2log:nachricht>\r\n");

			if (aktion.getGeanderteDaten() != null) {
				buf.append("\t<randi2log:daten objekt=\""
						+ aktion.getGeanderteDaten().getTyp() + "\" id=\""
						+ aktion.getGeanderteDaten().getId() + "\">\r\n");

				HashMap<String, String> daten = aktion.getGeanderteDaten()
						.getDaten();
				Iterator<String> itTypen = daten.keySet().iterator();

				while (itTypen.hasNext()) {
					String typ = itTypen.next();
					String wert = daten.get(typ);
					buf.append("\t\t<randi2log:eintrag typ=\"" + typ + "\">"
							+ wert + "</randi2log:eintrag>\r\n");
				}

				buf.append("</randi2log:daten>\r\n");
			}
			buf.append("\t<randi2log:benutzer>" + aktion.getBenutzernamen()
					+ "</randi2log:benutzer>\r\n");

			// buf.append("\t<randi2log:>" + +"</randi2log:>\r\n");

			buf.append("</randi2log:aktion>\r\n\n");

			return buf.toString();

		} else {
			Logger
					.getLogger(this.getClass())
					.warn(
							"Versuch ein Objekt vom Typ "
									+ event.getMessage().getClass()
									+ " ins fachliche Log zu schreiben. Log nicht geschrieben.");
			return "";
		}
	}

	/**
	 * Gibt an, ob das Log-Layout Exceptions ignoeriert.
	 * 
	 * @return Immer <code>false</code>.
	 * @see org.apache.log4j.Layout#ignoresThrowable()
	 */
	@Override
	public boolean ignoresThrowable() {
		return false;
	}

}
