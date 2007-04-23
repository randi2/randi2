package de.randi2.datenbank;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;
import de.randi2.utility.Config;
import de.randi2.utility.NullKonstanten;

/**
 * <p>Datenbankklasse</p>
 * @version $Id$
 * @author Frederik Reifschneider [Reifschneider@stud.uni-heidelberg.de]
 */
public class Datenbank implements DatenbankSchnittstelle{
	//TODO Logger Klasse implementieren
	
	/**
	 * Enum Klasse welche die Tabellen der Datenbank auflistet
	 * @author Frederik Reifschneider [Reifschneider@stud.uni-heidelberg.de]
	 *
	 */
	private enum Tabellen{
	
		ZENTRUM ("Zentrum"),
		PERSON ("Person");
		
		private String name = "";
		private Tabellen(String name) {
			this.name=name;
		}
		
		public String toString() {
			return this.name;
		}
	}
	
	/**
	 * Enum Klasse welche die Felder der Tabelle Zentrum repraesntiert
	 * @author Frederik Reifschneider [Reifschneider@stud.uni-heidelberg.de]
	 *
	 */
	private enum FelderZentrum {
		ID ("zentrumsID"),
		ANSPRECHPARTNERID ("Person_personenID"),
		INSTITUTION ("institution"),
		ABTEILUNGSNAME("abteilungsname"),
		ORT ("ort"),
		PLZ ("plz"),
		STRASSE ("strasse"),
		HAUSNUMMER ("hausnummer"),
		PASSWORT ("passwort"),
		AKTIVIERT ("aktiviert");
		
		private String name = "";
		private FelderZentrum(String name) {
			this.name=name;
		}
		
		public String toString() {
			return this.name;
		}
	}
	
	/**
	 * Enum Klasse welche die Felder der Tabelle Person repraesntiert
	 * @author Frederik Reifschneider [Reifschneider@stud.uni-heidelberg.de]
	 *
	 */
	private enum FelderPerson {
		
		ID ("personeneID"),
		NACHNAME ("nachname"),
		VORNAME ("vorname"),
		TITEL ("titel"),
		GESCHLECHT ("geschlecht"),
		TELEFONNUMMER ("telefonnummer"),
		HANDYNUMMER ("handynummer"),
		FAX ("fax"),
		EMAIL ("email"),
		STELLVERTRETER ("personenID");
		
		private String name = "";
		private FelderPerson(String name) {
			this.name=name;
		}
		
		public String toString() {
			return this.name;
		}
	}
	
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

	/**
	 * @see de.randi2.datenbank.DatenbankSchnittstelle#loeschenObjekt(de.randi2.datenbank.Filter)
	 */
	public <T extends Filter> void loeschenObjekt(T zuLoeschendesObjekt) throws DatenbankFehlerException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see de.randi2.datenbank.DatenbankSchnittstelle#schreibenObjekt(de.randi2.datenbank.Filter)
	 */
	public <T extends Filter> T schreibenObjekt(T zuSchreibendesObjekt) throws DatenbankFehlerException {
		//ZentrumBean schreiben
		if (zuSchreibendesObjekt instanceof ZentrumBean) {
			ZentrumBean zentrum = (ZentrumBean) zuSchreibendesObjekt;	
			return (T) schreibenZentrum(zentrum);
		}
		//PersonBean schreiben
		else if (zuSchreibendesObjekt instanceof PersonBean) {
			PersonBean person = (PersonBean) zuSchreibendesObjekt;
			return (T) schreibenPerson(person);
		}
		
		return null;
	}
	
	
	private PersonBean schreibenPerson(PersonBean person) {
		Connection con=null;
		try {
			con = getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql; 
		PreparedStatement pstmt;
		ResultSet rs;
		//neues Zentrum
		if(person.getId()== NullKonstanten.NULL_LONG) {
			long id = Long.MIN_VALUE;
			sql = "INSERT INTO "+Tabellen.PERSON+"("+
			FelderPerson.ID+","+
			FelderPerson.NACHNAME+","+
			FelderPerson.VORNAME+","+
			FelderPerson.GESCHLECHT+","+
			FelderPerson.TITEL+","+
			FelderPerson.EMAIL+","+
			FelderPerson.FAX+","+
			FelderPerson.TELEFONNUMMER+","+
			FelderPerson.STELLVERTRETER+")"+
			"VALUES (NULL,?,?,?,?,?,?,?,?);";
			try {
				pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, person.getNachname());
				pstmt.setString(2, person.getVorname());
				char tmp =  person.getGeschlecht();
				pstmt.setString(3, ""+person.getGeschlecht()); //TODO workaround
				pstmt.setString(4, person.getTitel().toString());
				pstmt.setString(5, person.getEmail());
				pstmt.setString(6, person.getFax());
				pstmt.setString(7, person.getTelefonnummer());
				pstmt.setString(8, person.getTelefonnummer());
				//pstmt.setBoolean(9, person.get);
				rs = pstmt.getGeneratedKeys();
				rs.next();
				id = rs.getLong(FelderZentrum.ID.toString());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			person.setId(id);
			return person;						
		}
		//vorhandenes Zentrum wird aktualisiert
		else {
			//TODO ausimplementieren
		}		
		try {
			closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	
	}

	/**
	 * Speichert bzw. aktualisiert das uebergebene Zentrum in der Datenbank
	 * @param zentrum
	 * 			zu speicherndes Zentrum
	 * @return
	 * 			das Zentrum mit der vergebenen eindeutigen ID bzw. das aktualisierte Zentrum
	 */
	private ZentrumBean schreibenZentrum(ZentrumBean zentrum) {
		Connection con=null;
		try {
			con = getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql; 
		PreparedStatement pstmt;
		ResultSet rs;
		//neues Zentrum
		if(zentrum.getId()== NullKonstanten.NULL_LONG) {
			long id = Long.MIN_VALUE;
			sql = "INSERT INTO "+Tabellen.ZENTRUM+"("+
			FelderZentrum.ID+","+
			FelderZentrum.ABTEILUNGSNAME+","+			
			FelderZentrum.ANSPRECHPARTNERID+","+
			FelderZentrum.HAUSNUMMER+","+
			FelderZentrum.INSTITUTION+","+
			FelderZentrum.ORT+","+
			FelderZentrum.PASSWORT+","+
			FelderZentrum.PLZ+","+
			FelderZentrum.STRASSE+","+
			FelderZentrum.AKTIVIERT+")"+
			"VALUES (NULL,?,?,?,?,?,?,?,?,?);";
			try {
				pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, zentrum.getAbteilung());
				pstmt.setLong(2, zentrum.getAnsprechpartnerId());
				pstmt.setString(3, zentrum.getHausnr());
				pstmt.setString(4, zentrum.getInstitution());
				pstmt.setString(5, zentrum.getOrt());
				pstmt.setString(6, zentrum.getPasswort());
				pstmt.setString(7, zentrum.getPlz());
				pstmt.setString(8, zentrum.getStrasse());
				pstmt.setBoolean(9, zentrum.getIstAktiviert());
				rs = pstmt.getGeneratedKeys();
				rs.next();
				id = rs.getLong(FelderZentrum.ID.toString());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			zentrum.setId(id);
			return zentrum;						
		}
		//vorhandenes Zentrum wird aktualisiert
		else {
			//TODO ausimplementieren
		}		
		try {
			closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;		
	}

	/**
	 * @see de.randi2.datenbank.DatenbankSchnittstelle#suchenObjekt(de.randi2.datenbank.Filter)
	 */
	public <T extends Filter> Vector<T> suchenObjekt(T zuSuchendesObjekt) throws DatenbankFehlerException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see de.randi2.datenbank.DatenbankSchnittstelle#suchenObjektID(long, de.randi2.datenbank.Filter)
	 */
	public <T extends Filter> T suchenObjektID(long id, T nullObjekt) throws DatenbankFehlerException {
		if (nullObjekt instanceof PersonBean) {
			PersonBean person = suchenPersonID(id);
			
			
		}
		return null;
	}
	
	/**
	 * Sucht in der Datenbank nach der Person mit der uebergebenen ID
	 * @param id
	 * 			zu suchende ID
	 * @return
	 * 			Person mit zutreffender ID
	 */
	private PersonBean suchenPersonID(long id) {
		Connection con=null;
		PreparedStatement pstmt;
		ResultSet rs = null;
		PersonBean tmpPerson;
		try {
			con = getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql;
		sql = "SELECT * FROM "+Tabellen.PERSON+" WHERE "+FelderPerson.ID+" = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			rs.next();
			//TODO fertig stellen
			//tmpPerson = new PersonBean(rs.getLong(FelderPerson.ID.toString()),);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try {
			closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	public <T extends Filter> T suchenObjektKomplett(long id, T nullObjekt) throws DatenbankFehlerException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see de.randi2.datenbank.DatenbankSchnittstelle#suchenMitgliederObjekte(de.randi2.datenbank.Filter, de.randi2.datenbank.Filter)
	 */
	public <T extends Filter, U extends Filter> Vector<T> suchenMitgliederObjekte(U vater, T kind) throws DatenbankFehlerException {
		return null;
	}
	
	public static void main(String[] args) {
		Datenbank db = new Datenbank();
		Connection con = null;
		try {
			con = db.getConnection();
		} catch (SQLException e) {		
			e.printStackTrace();
		}
		if (con==null) {
			System.out.println("keine Verbindung vorhanden");
		}
		else System.out.println("Verbindung steht");
		String query = "SELECT * FROM patient";
		Statement stmt;
		ResultSet rs=null;
		try {
			stmt = con.createStatement();
			rs =  stmt.executeQuery(query);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		try {
			db.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	/**
     * Baut Verbindung zur Datenbank auf
     * @return
     * 			Connectionobjekt welches Zugriff auf die Datenbankermoeglicht.
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {
    	Connection con = DriverManager.getConnection("jdbc:mysql://"+Config.getProperty(Config.Felder.RELEASE_DB_HOST)+":"+Config.getProperty(Config.Felder.RELEASE_DB_PORT)+"/randi2",Config.getProperty(Config.Felder.RELEASE_DB_NUTZERNAME),Config.getProperty(Config.Felder.RELEASE_DB_PASSWORT));    	
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
