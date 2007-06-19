package de.randi2.datenbank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

import sun.nio.cs.ext.DoubleByteEncoder;

import com.meterware.httpunit.HttpUnitUtils;

import de.randi2.datenbank.exceptions.DatenbankExceptions;


/**
 * Klasse bietet Methoden zum Aufbau der Verbindung mit der Datenbank
 * @author Frederik Reifschneider [Reifschneider@stud.uni-heidelberg.de]
 * @version $Id$
 *
 */
public class ConnectionFactory {
	
	
	private static ConnectionFactory aConFac = null;
	
	/**
	 * Konstruktor
	 */
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
		Logger.getLogger(this.getClass()).info("ConnectionFactory initialisiert!");
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
	 * @throws DatenbankExceptions 
	 * @throws SQLException Falls ein Fehler beim Verbindungsaufbau auftritt.
	 */
	public Connection getConnection() throws DatenbankExceptions {
		Connection con=null;
			try {
				con = DriverManager.getConnection("proxool.randi2");
			} catch (SQLException e) {
				throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
			}
				
		return con;
	}

	/**
	 * Trennt Verbindung zur Datenbank.
	 * 
	 * @param con das Connection Objekt.
	 * @throws DatenbankExceptions 
	 * @throws SQLException Falls ein Fehler bei der Verbindungstrennung auftritt.
	 */
	public void closeConnection(Connection con) throws DatenbankExceptions {
		try {
			if (con != null && !con.isClosed()) {
				con.close();
				con=null;
			}
		} catch (SQLException e) {
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}		
	}
	
	
	
	
}
