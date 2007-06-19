package de.randi2.datenbank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

import com.meterware.httpunit.HttpUnitUtils;

import de.randi2.datenbank.exceptions.DatenbankExceptions;


/**
 * Klasse bietet Methoden zum Aufbau der Verbindung mit der Datenbank
 * @author Frederik Reifschneider [Reifschneider@stud.uni-heidelberg.de]
 * @version $Id$
 *
 */
public class ConnectionFactory {
	
	private Connection con = null;
	
	private static ConnectionFactory aConFac = null;
	
	private ConnectionFactory() {
		String pfad;
		try {
			pfad = Datenbank.class.getResource("/conf/release/proxool_cfg.xml")
					.getPath();
			if (System.getProperty("os.name").indexOf("Win") != -1) {
				Logger.getLogger(this.getClass()).debug("Betriebssystem: Windows");
				pfad=HttpUnitUtils.decode(Datenbank.class.getResource(
						"/conf/release/proxool_cfg.xml").getPath());
			}
			JAXPConfigurator.configure(pfad, false);
		} catch (ProxoolException e) {			
			new DatenbankExceptions(DatenbankExceptions.PROXOOL_CONF_ERR);
		}
	}
	
	/**
	 * Liefert ein Objekt der Klasse {@link ConnectionFactory}
	 * @return
	 */
	public static ConnectionFactory getInstanz() {
		if(aConFac==null) {
			aConFac = new ConnectionFactory();
		}
		return aConFac;
	}
	
	/**
	 * Baut Verbindung zur Datenbank auf
	 * 
	 * @return Connectionobjekt welches Zugriff auf die Datenbank ermoeglicht.
	 * @throws SQLException Falls ein Fehler beim Verbindungsaufbau auftritt.
	 */
	public Connection getConnection() throws SQLException {
		if(con==null) {
			this.con = DriverManager.getConnection("proxool.randi2");
		}		
		return con;
	}

	/**
	 * Trennt Verbindung zur Datenbank.
	 * 
	 * @param con das Connection Objekt.
	 * @throws SQLException Falls ein Fehler bei der Verbindungstrennung auftritt.
	 */
	public void closeConnection() throws SQLException {
		if (con != null && !con.isClosed()) {
			con.close();
			con=null;
		}		
	}
	
	
	
	
}
