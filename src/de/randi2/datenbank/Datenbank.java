package de.randi2.datenbank;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import org.apache.log4j.Logger;

import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;
import de.randi2.model.fachklassen.beans.PersonBean.Titel;
import de.randi2.utility.Config;
import de.randi2.utility.NullKonstanten;

import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

/**
 * <p>Datenbankklasse</p>
 * @version $Id$
 * @author Frederik Reifschneider [Reifschneider@stud.uni-heidelberg.de]
 */
public class Datenbank implements DatenbankSchnittstelle{
	//TODO es muss bei allen schreib zugriffen geloggt werden!!
	//TODO Exception Handling.
	
	/**
	 * Logging Objekt
	 */
	private Logger log = null;
	
	/**
	 * Enum Klasse welche die Tabellen der Datenbank auflistet
	 * @author Frederik Reifschneider [Reifschneider@stud.uni-heidelberg.de]
	 */
	private enum Tabellen{	
		ZENTRUM ("Zentrum"),
		PERSON ("Person"),
		BENUTZERKONTO("Benutzerkonto"),
		AKTIVIERUNG("Aktivierung"),
		STUDIENARM("Studiearm"),
		STUDIE("Studie"),
		STATISTIK("Statistik"),
		RANDOMISATION("Randomisation"),
		PATIENT("Patient"),
		STUDIE_ZENTRUM("Studie_has_Zentrum");
		
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
	 * Enum Klasse welche die Felder der Tabelle Person repraesentiert
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
	 */
	public Datenbank() {
        log = Logger.getLogger("Randi2.Datenaenderung");
        try {
			JAXPConfigurator.configure("./src/conf/release/proxool_cfg.xml", false);
		} catch (ProxoolException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Dokumentation siehe Schnittstellenbeschreibung
	 * @see de.randi2.datenbank.DatenbankSchnittstelle#loeschenObjekt(de.randi2.datenbank.Filter)
	 */
	public <T extends Filter> void loeschenObjekt(T zuLoeschendesObjekt) throws DatenbankFehlerException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Dokumentation siehe Schnittstellenbeschreibung
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
	
	
	/**
	 * Schreibt Personbean in die Datenbank
	 * @param person
	 * 			zu schreibendes PersonBean
	 * @return
	 * 			PersonBean mit vergebener ID oder null falls nur ein Update durchgefuehrt wurde
	 */
	private PersonBean schreibenPerson(PersonBean person) {
		//TODO Logging
		Connection con=null; 
		try {
			con = getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql; 
		PreparedStatement pstmt;
		ResultSet rs;
		//neue Person da ID der Nullkonstante entspricht
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
				pstmt.setString(3, Character.toString(person.getGeschlecht()));
				pstmt.setString(4, person.getTitel().toString());
				pstmt.setString(5, person.getEmail());
				pstmt.setString(6, person.getFax());
				pstmt.setString(7, person.getTelefonnummer());
				pstmt.setLong(8, person.getStellvertreterID());
				rs = pstmt.getGeneratedKeys();
				rs.next();
				id = rs.getLong(FelderZentrum.ID.toString());
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			person.setId(id);
			return person;						
		}
		//vorhandenes Zentrum wird aktualisiert
		else {
			sql = "UPDATE "+Tabellen.PERSON+
			" SET "+FelderPerson.NACHNAME+"=?,"+
			FelderPerson.VORNAME+"=?,"+
			FelderPerson.GESCHLECHT+"=?,"+
			FelderPerson.TITEL+"=?,"+
			FelderPerson.EMAIL+"=?,"+
			FelderPerson.FAX+"=?,"+
			FelderPerson.TELEFONNUMMER+"=?,"+
			FelderPerson.STELLVERTRETER+"=?,"+
			" WHERE "+FelderPerson.ID+"=?";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, person.getNachname());
				pstmt.setString(2, person.getVorname());
				pstmt.setString(3,Character.toString(person.getGeschlecht())); 
				pstmt.setString(4, person.getTitel().toString());
				pstmt.setString(5, person.getEmail());
				pstmt.setString(6, person.getFax());
				pstmt.setString(7, person.getTelefonnummer());
				pstmt.setLong(9, person.getStellvertreterID());
				pstmt.setLong(9, person.getId());	
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		try {
			closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	
	}

	/**
	 * Speichert bzw. aktualisiert das uebergebene Zentrum in der Datenbank.
	 * @param zentrum
	 * 			zu speicherndes Zentrum
	 * @return
	 * 			das Zentrum mit der vergebenen eindeutigen ID bzw. das aktualisierte Zentrum
	 */
	private ZentrumBean schreibenZentrum(ZentrumBean zentrum) {
		//TODO Logging
		Connection con=null;
		try {
			con = getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql; 
		PreparedStatement pstmt;
		ResultSet rs;
		//neues Zentrum da ID der Nullkonstante entspricht
		if(zentrum.getId()== NullKonstanten.NULL_LONG) {
			long id = Long.MIN_VALUE;
			sql = "INSERT INTO "+Tabellen.ZENTRUM+"("+
			FelderZentrum.ID+","+
			FelderZentrum.INSTITUTION+","+
			FelderZentrum.ABTEILUNGSNAME+","+			
			FelderZentrum.ANSPRECHPARTNERID+","+			
			FelderZentrum.STRASSE+","+
			FelderZentrum.HAUSNUMMER+","+	
			FelderZentrum.PLZ+","+
			FelderZentrum.ORT+","+
			FelderZentrum.PASSWORT+","+				
			FelderZentrum.AKTIVIERT+")"+
			"VALUES (NULL,?,?,?,?,?,?,?,?,?);";
			try {
				pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(2, zentrum.getAbteilung());
				pstmt.setLong(3, zentrum.getAnsprechpartnerId());
				pstmt.setString(5, zentrum.getHausnr());
				pstmt.setString(1, zentrum.getInstitution());
				pstmt.setString(7, zentrum.getOrt());
				pstmt.setString(8, zentrum.getPasswort());
				pstmt.setString(6, zentrum.getPlz());
				pstmt.setString(4, zentrum.getStrasse());
				pstmt.setBoolean(9, zentrum.getIstAktiviert());
				rs = pstmt.getGeneratedKeys();
				rs.next();
				id = rs.getLong(FelderZentrum.ID.toString());
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			zentrum.setId(id);
			return zentrum;						
		}
		//vorhandenes Zentrum wird aktualisiert
		else {
			sql = "UPDATE "+Tabellen.ZENTRUM+			
			" SET "+FelderZentrum.INSTITUTION+"=?,"+
			FelderZentrum.ABTEILUNGSNAME+"=?,"+			
			FelderZentrum.ANSPRECHPARTNERID+"=?,"+			
			FelderZentrum.STRASSE+"=?,"+
			FelderZentrum.HAUSNUMMER+"=?,"+	
			FelderZentrum.PLZ+"=?,"+
			FelderZentrum.ORT+","+
			FelderZentrum.PASSWORT+"=?,"+				
			FelderZentrum.AKTIVIERT+"=?"+
			" WHERE "+FelderPerson.ID+"=?";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, zentrum.getInstitution());
				pstmt.setString(2, zentrum.getAbteilung());
				pstmt.setLong(3,zentrum.getAnsprechpartnerId());
				pstmt.setString(4, zentrum.getStrasse());
				pstmt.setString(5, zentrum.getHausnr());
				pstmt.setString(6, zentrum.getPlz());
				pstmt.setString(7, zentrum.getOrt());
				pstmt.setString(9, zentrum.getPasswort());
				pstmt.setLong(9, zentrum.getId());	
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		try {
			closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;		
	}

	/**
	 * Dokumentation siehe Schnittstellenbeschreibung
	 * @see de.randi2.datenbank.DatenbankSchnittstelle#suchenObjekt(de.randi2.datenbank.Filter)
	 */
	public <T extends Filter> Vector<T> suchenObjekt(T zuSuchendesObjekt) throws DatenbankFehlerException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Dokumentation siehe Schnittstellenbeschreibung
	 * @see de.randi2.datenbank.DatenbankSchnittstelle#suchenObjektID(long, de.randi2.datenbank.Filter)
	 */
	public <T extends Filter> T suchenObjektID(long id, T nullObjekt) throws DatenbankFehlerException {
		if (nullObjekt instanceof PersonBean) {
			PersonBean person = suchenPersonID(id);
			return (T) person;
		}
		return null;
	}
	
	/**
	 * Sucht in der Datenbank nach der Person mit der uebergebenen ID
	 * @param id
	 * 			zu suchende ID
	 * @return
	 * 			Person mit zutreffender ID, null falls keine Person mit entsprechender ID gefunden wurde
	 * @throws DatenbankFehlerException 
	 */
	private PersonBean suchenPersonID(long id) throws DatenbankFehlerException {
		Connection con=null;
		PreparedStatement pstmt;
		ResultSet rs = null;
		PersonBean tmpPerson=null;
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
			if(rs.next()) {
				char[] tmp = rs.getString(FelderPerson.GESCHLECHT.toString()).toCharArray();
				try {
					tmpPerson = new PersonBean(rs.getLong(FelderPerson.ID.toString()), rs.getString(FelderPerson.NACHNAME.toString()),rs.getString(FelderPerson.VORNAME.toString())
							, Titel.parseTitel(rs.getString(FelderPerson.TITEL.toString())) , tmp[0],rs.getString(FelderPerson.EMAIL.toString()),
							rs.getString(FelderPerson.TELEFONNUMMER.toString()),rs.getString(FelderPerson.HANDYNUMMER.toString()),rs.getString(FelderPerson.FAX.toString()));
				} catch (PersonException e) {					
					e.printStackTrace();
					throw new DatenbankFehlerException(DatenbankFehlerException.UNGUELTIGE_DATEN);
					//sollte hier lieber die Person Exception weitergeleitet werden? wie sieht es mit logging aus?
				}
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		try {
			closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tmpPerson;
		
	}
	
	/**
	 * Dokumentation siehe Schnittstellenbeschreibung
	 * @see de.randi2.datenbank.DatenbankSchnittstelle#suchenMitgliederObjekte(de.randi2.datenbank.Filter, de.randi2.datenbank.Filter)
	 */
	public <T extends Filter, U extends Filter> Vector<T> suchenMitgliederObjekte(U vater, T kind) throws DatenbankFehlerException {
		if (vater instanceof ZentrumBean && kind instanceof PersonBean) {
			ZentrumBean zentrum = (ZentrumBean) vater;
			PersonBean ansprechpartner = suchenAnsprechpartner(zentrum.getAnsprechpartnerId());
			Vector<T> personVec = new Vector();
			personVec.add((T)ansprechpartner);
			return personVec;
		}
		return null;
	}
	
	
	/**
	 * Methode sucht den Ansprechpartner eines Zentrums
	 * @param id
	 * 			Ansprechpartner des Ansprechpartners, welche im ZentrumBean der aufrufenden Methode gespeichert st
	 * @return
	 * 			Ansprechpartner
	 * @throws DatenbankFehlerException
	 * 			Falls ein DB Fehler auftritt
	 */
	private PersonBean suchenAnsprechpartner(long id) throws DatenbankFehlerException {
		return suchenObjektID(id, new PersonBean()); 
	}
	
	//TODO main methode spaeter rausschmeissen
	/**
	 * Nur Testfunktionalitaet
	 * @param args
	 */
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
		else {
			System.out.println("Verbindung aufgebaut");
		}
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
		System.out.println("ENUM TEST ");
		System.out.println("FelderPerson.Nachname: "+FelderPerson.NACHNAME);
		System.out.println("FelderPerson.Nachname.toString(): "+FelderPerson.NACHNAME.toString());
		
	}
	
	/**
     * Baut Verbindung zur Datenbank auf
     * @return
     * 			Connectionobjekt welches Zugriff auf die Datenbank ermoeglicht.
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {
    	Connection con = DriverManager.getConnection("proxool.randi2");    	
    	return con;
    }
    //"jdbc:mysql://"+Config.getProperty(Config.Felder.RELEASE_DB_HOST)+":"+Config.getProperty(Config.Felder.RELEASE_DB_PORT)+"/randi2",Config.getProperty(Config.Felder.RELEASE_DB_NUTZERNAME),Config.getProperty(Config.Felder.RELEASE_DB_PASSWORT
    
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
