package de.randi2.datenbank;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import de.randi2.utility.Config;
import de.randi2.datenbank.exceptions.DatenbankFehlerException;

/**
 * <p>Datenbankklasse</p>
 * @version $Id$
 * @author Frederik Reifschneider [Reifschneider@stud.uni-heidelberg.de]
 */
public class Datenbank implements DatenbankSchnittstelle{
	
	/**
	 * Konstruktor der Datenbankklasse.
	 * Laedt den SQL Treiber
	 */
	public Datenbank() {
		try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
        	System.out.println(e.getLocalizedMessage());
        } catch (IllegalAccessException e) {
        	System.out.println(e.getLocalizedMessage());
        } catch (ClassNotFoundException e) {
        	System.out.println(e.getLocalizedMessage());
        }
	}

	public <T extends Filter> void loeschenObjekt(T zuLoeschendesObjekt) throws DatenbankFehlerException {
		// TODO Auto-generated method stub
		
	}

	public <T extends Filter> T schreibenObjekt(T zuSchreibendesObjekt) throws DatenbankFehlerException {
		// TODO Auto-generated method stub
		return null;
	}

	public <T extends Filter> Vector<T> suchenObjekt(T zuSuchendesObjekt) throws DatenbankFehlerException {
		// TODO Auto-generated method stub
		return null;
	}

	public <T extends Filter> T suchenObjektID(long id, T nullObjekt) throws DatenbankFehlerException {
		// TODO Auto-generated method stub
		return null;
	}

	public <T extends Filter> T suchenObjektKomplett(long id, T nullObjekt) throws DatenbankFehlerException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
     * Baut Verbindung zur Datenbank auf
     * @return
     * 			Connectionobjekt welches Zugriff auf die Datenbankermoeglicht.
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {
    	Connection con = DriverManager.getConnection("jdbc:mysql://"+Config.getProperty(Config.Felder.RELEASE_DB_HOST)+":"+Config.getProperty(Config.Felder.RELEASE_DB_PORT)+"/neuroguard",Config.getProperty(Config.Felder.RELEASE_DB_NUTZERNAME),Config.getProperty(Config.Felder.RELEASE_DB_PASSWORT));    	
    	return con;
    }
    
    /**
     * Trennt Verbindung zur Datenbank.
     * @throws SQLException 
     * @throws DBExceptions
     */
    private void closeConnection(Connection con) throws SQLException {
        	if(con!=null && !con.isClosed()) {
        		con.close();
        	}            
    }	

}
