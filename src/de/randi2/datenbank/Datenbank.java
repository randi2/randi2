package de.randi2.datenbank;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.exceptions.BenutzerException;
import de.randi2.model.fachklassen.beans.AktivierungBean;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.PatientBean;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.model.fachklassen.beans.StudienarmBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;
import de.randi2.model.fachklassen.beans.PersonBean.Titel;
import de.randi2.utility.NullKonstanten;
import de.randi2.utility.SystemException;

/**
 * <p>
 * Datenbankklasse
 * </p>
 * 
 * @version $Id$
 * @author Frederik Reifschneider [Reifschneider@stud.uni-heidelberg.de]
 */
public class Datenbank implements DatenbankSchnittstelle {
	// TODO es muss bei allen schreib zugriffen geloggt werden!!
	// TODO Exception Handling.
	// TODO Es gibt für sql TEXT kein Types Feld!!

	/**
	 * Logging Objekt
	 */
	private Logger log = null;

	/**
	 * Enum Klasse welche die Tabellen der Datenbank auflistet
	 * 
	 * @author Frederik Reifschneider [Reifschneider@stud.uni-heidelberg.de]
	 */
	private enum Tabellen {
		/**
		 * Die Repräsentation der Zentrumstabelle aus der Datenbank.
		 */
		ZENTRUM("Zentrum"),
		/**
		 * Die Repräsentation der Personentabelle aus der Datenbank.
		 */
		PERSON("Person"),
		/**
		 * Die Repräsentation der Benutzerkontotabelle aus der Datenbank.
		 */
		BENUTZERKONTO("Benutzerkonto"),
		/**
		 * Die Repräsentation der Aktivierungstabelle aus der Datenbank.
		 */
		AKTIVIERUNG("Aktivierung"),
		/**
		 * Die Repräsentation der Studienarmtabelle aus der Datenbank.
		 */
		STUDIENARM("Studiearm"),
		/**
		 * Die Repräsentation der Studientabelle aus der Datenbank.
		 */
		STUDIE("Studie"),
		/**
		 * Die Repräsentation der Randomisationstabelle aus der Datenbank.
		 */
		RANDOMISATION("Randomisation"),
		/**
		 * Die Repräsentation der Patiententabelle aus der Datenbank.
		 */
		PATIENT("Patient"),
		/**
		 * Die Repräsentation der Studie_has-Zentrum-Tablle aus der Datenbank
		 * (n:m-Relation).
		 */
		STUDIE_ZENTRUM("Studie_has_Zentrum"),
		/**
		 * Die Repräsentation der Strata_Typen-Tabelle aus der Datenbank.
		 */
		STRATA_TYPEN("Strata_Typen"),
		/**
		 * Die Repräsentation der Strata_Auspraegungs-Tabelle aus der Datenbank.
		 */
		STRATA_AUSPRAEGUNG("Strata_Auspraegung"),
		/**
		 * Die Repräsentation der Strata_Werte_has_Zentrum-Tabelle aus der
		 * Datenbank (n:m-Relation).
		 */
		STRATA_PATIENT("Strata_Werte_has_Patient");

		/**
		 * Der Name der Tabellen.
		 */
		private String name = "";

		/**
		 * Konstruktor von Tabellen.
		 * 
		 * @param name
		 *            der Name der Tabellen.
		 */
		private Tabellen(String name) {
			this.name = name;
		}

		/**
		 * toString-Methode dieser Enum.
		 * 
		 * @return Liefert den Name der Tabelle.
		 */
		public String toString() {
			return this.name;
		}
	}

	/**
	 * Enum Klasse welche die Felder der Tabelle Zentrum repraesntiert.
	 * 
	 * @author Frederik Reifschneider [Reifschneider@stud.uni-heidelberg.de]
	 * 
	 */
	private enum FelderZentrum {
		ID("zentrumsId"), ANSPRECHPARTNERID("ansprechpartnerId"), INSTITUTION(
				"institution"), ABTEILUNGSNAME("abteilungsname"), ORT("ort"), PLZ(
				"plz"), STRASSE("strasse"), HAUSNUMMER("hausnummer"), PASSWORT(
				"passwort"), AKTIVIERT("aktiviert");

		private String name = "";

		private FelderZentrum(String name) {
			this.name = name;
		}

		public String toString() {
			return this.name;
		}
	}

	/**
	 * Enum Klasse welche die Felder der Tabelle Person repraesentiert.
	 * 
	 * @author Frederik Reifschneider [Reifschneider@stud.uni-heidelberg.de]
	 * 
	 */
	private enum FelderPerson {

		ID("personenId"), NACHNAME("nachname"), VORNAME("vorname"), TITEL(
				"titel"), GESCHLECHT("geschlecht"), TELEFONNUMMER(
				"telefonnummer"), HANDYNUMMER("handynummer"), FAX("fax"), EMAIL(
				"email"), STELLVERTRETER("stellvertreterId");

		private String name = "";

		private FelderPerson(String name) {
			this.name = name;
		}

		public String toString() {
			return this.name;
		}
	}

	/**
	 * Enum Klasse welche die Felder der Tabelle Benutzerkonto repraesentiert.
	 * 
	 * @author Kai Marco Krupka [kai.krupka@urz.uni-heidelberg.de]
	 * 
	 */
	private enum FelderBenutzerkonto {
		ID("benutzerkontenId"), 
		PERSON("Person_personenId"), 
		LOGINNAME("loginname"), 
		PASSWORT("passwort"),
		PERSONID("Person_personenID"),
		ROLLEACCOUNT("rolle"), 
		ERSTERLOGIN("erster_login"), 
		LETZTERLOGIN("letzter_login"), 
		GESPERRT("gesperrt");

		private String name = "";

		private FelderBenutzerkonto(String name) {
			this.name = name;
		}

		public String toString() {
			return this.name;
		}
	}

	/**
	 * Enum Klasse welche die Felder der Tabelle Aktivierung repraesentiert.
	 * 
	 * @author Kai Marco Krupka [kai.krupka@urz.uni-heidelberg.de]
	 * 
	 */
	private enum FelderAktivierung {
		Id("aktivierungsId"), BENUTZER("Benutzerkonto_benutzerkontenId"), LINK(
				"aktivierungslink"), VERSANDDATUM("versanddatum");

		private String name = "";

		private FelderAktivierung(String name) {
			this.name = name;
		}

		public String toString() {
			return this.name;
		}
	}

	/**
	 * Enum Klasse welche die Felder der Tabelle Studie repraesentiert.
	 * 
	 * @author Kai Marco Krupka [kai.krupka@urz.uni-heidelberg.de]
	 * 
	 */
	private enum FelderStudie {
		ID("studienId"), BENUTZER("Benutzerkonto_benutzerkontenId"), NAME(
				"name"), BESCHREIBUNG("beschreibung"), STARTDATUM("startdatum"), ENDDATUM(
				"enddatum"), PROTOKOLL("studienprotokoll"), RANDOMISATIONSART(
				"randomisationsart"), STATUS("status_studie");

		private String name = "";

		private FelderStudie(String name) {
			this.name = name;
		}

		public String toString() {
			return this.name;
		}
	}

	/**
	 * Enum Klasse welche die Felder der Tabelle Studienarm repraesentiert.
	 * 
	 * @author Kai Marco Krupka [kai.krupka@urz.uni-heidelberg.de]
	 * 
	 */
	private enum FelderStudienarm {
		ID("studienarmId"), STUDIE("Studie_studienId"), STATUS(
				"status_aktivitaet"), BEZEICHNUNG("bezeichnung"), BESCHREIBUNG(
				"beschreibung");

		private String name = "";

		private FelderStudienarm(String name) {
			this.name = name;
		}

		public String toString() {
			return this.name;
		}
	}

	/**
	 * Enum Klasse welche die Felder der Tabelle Patient repraesentiert.
	 * 
	 * @author Kai Marco Krupka [kai.krupka@urz.uni-heidelberg.de]
	 * 
	 */
	private enum FelderPatient {
		ID("patientenId"), BENUTZER("Benutzerkonto_benutzerkontenId"), STUDIENARM(
				"Studienarm_studienarmId"), INITIALEN("initialen"), GEBURTSDATUM(
				"geburtsdatum"), GESCHLECHT("geschlecht"), AUFKLAERUNGSDATUM(
				"aufklaerungsdatum"), KOERPEROBERFLAECHE("koerperoberflaeche"), PERFORMANCESTATUS(
				"performancestatus");

		private String name = "";

		private FelderPatient(String name) {
			this.name = name;
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
			JAXPConfigurator.configure("./src/conf/release/proxool_cfg.xml",
					false);
		} catch (ProxoolException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Dokumentation siehe Schnittstellenbeschreibung
	 * 
	 * @see de.randi2.datenbank.DatenbankSchnittstelle#loeschenObjekt(de.randi2.datenbank.Filter)
	 */
	public <T extends Filter> void loeschenObjekt(T zuLoeschendesObjekt)
			throws DatenbankFehlerException {
		if (zuLoeschendesObjekt == null) {
			throw new DatenbankFehlerException(
					DatenbankFehlerException.ARGUMENT_IST_NULL);
		} else {
			// ZentrumBean löschen
			if (zuLoeschendesObjekt instanceof ZentrumBean) {
				ZentrumBean zentrum = (ZentrumBean) zuLoeschendesObjekt;
				this.loeschenZentrum(zentrum);
			}
			// PersonBean löschen
			if (zuLoeschendesObjekt instanceof PersonBean) {
				PersonBean person = (PersonBean) zuLoeschendesObjekt;
				this.loeschenPerson(person);
			}
			// Benutzerkonto löschen
			if (zuLoeschendesObjekt instanceof BenutzerkontoBean) {
				BenutzerkontoBean benutzer = (BenutzerkontoBean) zuLoeschendesObjekt;
				this.loeschenBenutzerkonto(benutzer);
			}
			// Aktivierung löschen
			if (zuLoeschendesObjekt instanceof AktivierungBean) {
				AktivierungBean aktivierung = (AktivierungBean) zuLoeschendesObjekt;
				this.loeschenAktivierung(aktivierung);
			}
			// Studie löschen
			if (zuLoeschendesObjekt instanceof StudieBean) {
				StudieBean studie = (StudieBean) zuLoeschendesObjekt;
				this.loeschenStudie(studie);
			}
			// Studienarm löschen
			if (zuLoeschendesObjekt instanceof StudienarmBean) {
				StudienarmBean studienarm = (StudienarmBean) zuLoeschendesObjekt;
				this.loeschenStudienarm(studienarm);
			}
			// Patient löschen
			if (zuLoeschendesObjekt instanceof PatientBean) {
				PatientBean patient = (PatientBean) zuLoeschendesObjekt;
				this.loeschenPatient(patient);
			}
		}
	}

	/**
	 * Loescht das übergebene Zentrumsobjekt aus der Datenbank.
	 * 
	 * @param zentrum
	 *            zu löschendes ZentrumBean.
	 * @throws DatenbankFehlerException
	 *             wirft Datenbankfehler bei Verbindungs- oder Loeschfehlern.
	 */
	private void loeschenZentrum(ZentrumBean zentrum)
			throws DatenbankFehlerException {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		try {
			con = getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		sql = "DELETE FROM " + Tabellen.ZENTRUM + " WHERE " + FelderZentrum.ID
				+ "=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, zentrum.getId());
			pstmt.executeUpdate(sql);
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.LOESCHEN_ERR);
		}
		try {
			this.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
	}

	/**
	 * Loescht das übergebene Personenobjekt aus der Datenbank.
	 * 
	 * @param person
	 *            zu löschendes PersonBean.
	 * @throws DatenbankFehlerException
	 *             wirft Datenbankfehler bei Verbindungs- oder Loeschfehlern.
	 */
	private void loeschenPerson(PersonBean person)
			throws DatenbankFehlerException {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		try {
			con = getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		sql = "DELETE FROM " + Tabellen.PERSON + " WHERE " + FelderPerson.ID
				+ "=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, person.getId());
			pstmt.executeUpdate(sql);
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.LOESCHEN_ERR);
		}
		try {
			this.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
	}

	/**
	 * Loescht das übergebene Benutzerkontenobjekt aus der Datenbank.
	 * 
	 * @param benutzer
	 *            zu löschendes BenutzerkontoBean.
	 * @throws DatenbankFehlerException
	 *             wirft Datenbankfehler bei Verbindungs- oder Loeschfehlern.
	 */
	private void loeschenBenutzerkonto(BenutzerkontoBean benutzer)
			throws DatenbankFehlerException {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		try {
			con = getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		sql = "DELETE FROM " + Tabellen.BENUTZERKONTO + " WHERE "
				+ FelderBenutzerkonto.ID + "=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, benutzer.getId());
			pstmt.executeUpdate(sql);
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.LOESCHEN_ERR);
		}
		try {
			this.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
	}

	/**
	 * Loescht das übergebene Aktivierungsobjekt aus der Datenbank.
	 * 
	 * @param aktivierung
	 *            zu löschendes AktivierungBean.
	 * @throws DatenbankFehlerException
	 *             wirft Datenbankfehler bei Verbindungs- oder Loeschfehlern.
	 */
	private void loeschenAktivierung(AktivierungBean aktivierung)
			throws DatenbankFehlerException {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		try {
			con = getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		sql = "DELETE FROM " + Tabellen.AKTIVIERUNG + " WHERE "
				+ FelderAktivierung.Id + "=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, aktivierung.getAktivierungsId());
			pstmt.executeUpdate(sql);
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.LOESCHEN_ERR);
		}
		try {
			this.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
	}

	/**
	 * Loescht das übergebene Studienobjekt aus der Datenbank.
	 * 
	 * @param studie
	 *            zu löschendes StudieBean.
	 * @throws DatenbankFehlerException
	 *             wirft Datenbankfehler bei Verbindungs- oder Loeschfehlern.
	 */
	private void loeschenStudie(StudieBean studie)
			throws DatenbankFehlerException {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		try {
			con = getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		sql = "DELETE FROM " + Tabellen.STUDIE + " WHERE " + FelderStudie.ID
				+ "=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, studie.getId());
			pstmt.executeUpdate(sql);
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.LOESCHEN_ERR);
		}
		try {
			this.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
	}

	/**
	 * Loescht das übergebene Studienarmobjekt aus der Datenbank.
	 * 
	 * @param studienarm
	 *            zu löschendes StudienarmBean.
	 * @throws DatenbankFehlerException
	 *             wirft Datenbankfehler bei Verbindungs- oder Loeschfehlern.
	 */
	private void loeschenStudienarm(StudienarmBean studienarm)
			throws DatenbankFehlerException {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		try {
			con = getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		sql = "DELETE FROM " + Tabellen.STUDIENARM + " WHERE "
				+ FelderStudienarm.ID + "=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, studienarm.getId());
			pstmt.executeUpdate(sql);
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.LOESCHEN_ERR);
		}
		try {
			this.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
	}

	/**
	 * Loescht das übergebene Patientenobjekt aus der Datenbank.
	 * 
	 * @param patient
	 *            zu löschendes PatientBean.
	 * @throws DatenbankFehlerException
	 *             wirft Datenbankfehler bei Verbindungs- oder Loeschfehlern.
	 */
	private void loeschenPatient(PatientBean patient)
			throws DatenbankFehlerException {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		try {
			con = getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		sql = "DELETE FROM " + Tabellen.PATIENT + " WHERE " + FelderPatient.ID
				+ "=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, patient.getId());
			pstmt.executeUpdate(sql);
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.LOESCHEN_ERR);
		}
		try {
			this.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
	}

	/**
	 * Dokumentation siehe Schnittstellenbeschreibung
	 * 
	 * @see de.randi2.datenbank.DatenbankSchnittstelle#schreibenObjekt(de.randi2.datenbank.Filter)
	 */
	@SuppressWarnings("unchecked")
	public <T extends Filter> T schreibenObjekt(T zuSchreibendesObjekt)
			throws DatenbankFehlerException {
		if (zuSchreibendesObjekt == null) {
			throw new DatenbankFehlerException(
					DatenbankFehlerException.ARGUMENT_IST_NULL);
		} else {
			// ZentrumBean schreiben
			if (zuSchreibendesObjekt instanceof ZentrumBean) {
				ZentrumBean zentrum = (ZentrumBean) zuSchreibendesObjekt;
				return (T) schreibenZentrum(zentrum);
			}
			// PersonBean schreiben
			else if (zuSchreibendesObjekt instanceof PersonBean) {
				PersonBean person = (PersonBean) zuSchreibendesObjekt;
				return (T) schreibenPerson(person);
			}
			// BenutzerKontoBean schreiben
			else if (zuSchreibendesObjekt instanceof BenutzerkontoBean) {
				BenutzerkontoBean benutzerKonto = (BenutzerkontoBean) zuSchreibendesObjekt;
				return (T) this.schreibenBenutzerKonto(benutzerKonto);
			}
			// AktivierungsBean
			else if (zuSchreibendesObjekt instanceof AktivierungBean) {
				AktivierungBean aktivierung = (AktivierungBean) zuSchreibendesObjekt;
				return (T) this.schreibenAktivierung(aktivierung);
			}
			// StudieBean
			else if (zuSchreibendesObjekt instanceof StudieBean) {
				StudieBean studie = (StudieBean) zuSchreibendesObjekt;
				return (T) this.schreibenStudie(studie);
			}
			if (zuSchreibendesObjekt instanceof StudienarmBean) {
				StudienarmBean studienarm = (StudienarmBean) zuSchreibendesObjekt;
				return (T) this.schreibenStudienarm(studienarm);
			}
			if (zuSchreibendesObjekt instanceof PatientBean) {
				PatientBean patient = (PatientBean) zuSchreibendesObjekt;
				return (T) this.schreibenPatient(patient);
			}
		}
		return null;
	}

	/**
	 * Schreibt Personbean in die Datenbank
	 * 
	 * @param person
	 *            zu schreibendes PersonBean
	 * @return PersonBean mit vergebener Id oder null falls nur ein Update
	 *         durchgefuehrt wurde
	 * @throws DatenbankFehlerException
	 *             wirft Datenbankfehler bei Verbindungs- oder Schreibfehlern.
	 */
	private PersonBean schreibenPerson(PersonBean person)
			throws DatenbankFehlerException {
		Connection con = null;
		try {
			con = getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		String sql;
		PreparedStatement pstmt;
		ResultSet rs;
		// neue Person da Id der Nullkonstante entspricht
		if (person.getId() == NullKonstanten.NULL_LONG) {
			long id = Long.MIN_VALUE;
			sql = "INSERT INTO " + Tabellen.PERSON + "(" + FelderPerson.ID
					+ "," + FelderPerson.NACHNAME + "," + FelderPerson.VORNAME
					+ "," + FelderPerson.GESCHLECHT + "," + FelderPerson.TITEL
					+ "," + FelderPerson.EMAIL + "," + FelderPerson.FAX + ","
					+ FelderPerson.TELEFONNUMMER + ","
					+ FelderPerson.STELLVERTRETER + ")"
					+ "VALUES (NULL,?,?,?,?,?,?,?,?);";
			try {
				pstmt = con.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, person.getNachname());
				pstmt.setString(2, person.getVorname());
				pstmt.setString(3, Character.toString(person.getGeschlecht()));
				pstmt.setString(4, person.getTitel().toString());
				pstmt.setString(5, person.getEmail());
				pstmt.setString(6, person.getFax());
				pstmt.setString(7, person.getTelefonnummer());
				if (person.getStellvertreterId() == NullKonstanten.NULL_LONG) {
					pstmt.setNull(8, Types.NULL);
				} else {
					pstmt.setLong(8, person.getStellvertreterId());
				}

				pstmt.executeUpdate();
				rs = pstmt.getGeneratedKeys();
				rs.next();
				id = rs.getLong(1);
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatenbankFehlerException(
						DatenbankFehlerException.SCHREIBEN_ERR);
			}
			person.setId(id);
			return person;
		}
		// vorhandenes Zentrum wird aktualisiert
		else {
			sql = "UPDATE " + Tabellen.PERSON + " SET " + FelderPerson.NACHNAME
					+ "=?," + FelderPerson.VORNAME + "=?,"
					+ FelderPerson.GESCHLECHT + "=?," + FelderPerson.TITEL
					+ "=?," + FelderPerson.EMAIL + "=?," + FelderPerson.FAX
					+ "=?," + FelderPerson.TELEFONNUMMER + "=?,"
					+ FelderPerson.STELLVERTRETER + "=?," + " WHERE "
					+ FelderPerson.ID + "=?";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, person.getNachname());
				pstmt.setString(2, person.getVorname());
				pstmt.setString(3, Character.toString(person.getGeschlecht()));
				pstmt.setString(4, person.getTitel().toString());
				pstmt.setString(5, person.getEmail());
				pstmt.setString(6, person.getFax());
				pstmt.setString(7, person.getTelefonnummer());
				pstmt.setLong(8, person.getStellvertreterId());
				pstmt.setLong(9, person.getId());
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatenbankFehlerException(
						DatenbankFehlerException.SCHREIBEN_ERR);
			}
		}
		try {
			closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		return null;
	}

	/**
	 * Speichert bzw. aktualisiert das uebergebene Zentrum in der Datenbank.
	 * 
	 * @param zentrum
	 *            zu speicherndes Zentrum
	 * @return das Zentrum mit der vergebenen eindeutigen Id bzw. das
	 *         aktualisierte Zentrum
	 * @throws DatenbankFehlerException
	 *             wirft Datenbankfehler bei Verbindungs- oder Schreibfehlern.
	 */
	private ZentrumBean schreibenZentrum(ZentrumBean zentrum)
			throws DatenbankFehlerException {
		Connection con = null;
		try {
			con = getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		String sql;
		PreparedStatement pstmt;
		ResultSet rs;
		// neues Zentrum da Id der Nullkonstante entspricht
		if (zentrum.getId() == NullKonstanten.NULL_LONG) {
			long id = Long.MIN_VALUE;
			sql = "INSERT INTO " + Tabellen.ZENTRUM + "(" + FelderZentrum.ID
					+ "," + FelderZentrum.INSTITUTION + ","
					+ FelderZentrum.ABTEILUNGSNAME + ","
					+ FelderZentrum.ANSPRECHPARTNERID + ","
					+ FelderZentrum.STRASSE + "," + FelderZentrum.HAUSNUMMER
					+ "," + FelderZentrum.PLZ + "," + FelderZentrum.ORT + ","
					+ FelderZentrum.PASSWORT + "," + FelderZentrum.AKTIVIERT
					+ ")" + "VALUES (NULL,?,?,?,?,?,?,?,?,?);";
			try {
				pstmt = con.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, zentrum.getInstitution());
				pstmt.setString(2, zentrum.getAbteilung());
				pstmt.setLong(3, zentrum.getAnsprechpartnerId());
				pstmt.setString(4, zentrum.getStrasse());
				pstmt.setString(5, zentrum.getHausnr());
				pstmt.setString(6, zentrum.getPlz());
				pstmt.setString(7, zentrum.getOrt());
				pstmt.setString(8, zentrum.getPasswort());
				pstmt.setBoolean(9, zentrum.getIstAktiviert());
				pstmt.executeUpdate();
				rs = pstmt.getGeneratedKeys();
				rs.next();
				id = rs.getLong(1);
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatenbankFehlerException(
						DatenbankFehlerException.SCHREIBEN_ERR);
			}
			zentrum.setId(id);
			return zentrum;
		}
		// vorhandenes Zentrum wird aktualisiert
		else {
			sql = "UPDATE " + Tabellen.ZENTRUM + " SET "
					+ FelderZentrum.INSTITUTION + "=?,"
					+ FelderZentrum.ABTEILUNGSNAME + "=?,"
					+ FelderZentrum.ANSPRECHPARTNERID + "=?,"
					+ FelderZentrum.STRASSE + "=?," + FelderZentrum.HAUSNUMMER
					+ "=?," + FelderZentrum.PLZ + "=?," + FelderZentrum.ORT
					+ "=?," + FelderZentrum.PASSWORT + "=?,"
					+ FelderZentrum.AKTIVIERT + "=?" + " WHERE "
					+ FelderPerson.ID + "=?";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, zentrum.getInstitution());
				pstmt.setString(2, zentrum.getAbteilung());
				pstmt.setLong(3, zentrum.getAnsprechpartnerId());
				pstmt.setString(4, zentrum.getStrasse());
				pstmt.setString(5, zentrum.getHausnr());
				pstmt.setString(6, zentrum.getPlz());
				pstmt.setString(7, zentrum.getOrt());
				pstmt.setString(8, zentrum.getPasswort());
				pstmt.setLong(9, zentrum.getId());
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatenbankFehlerException(
						DatenbankFehlerException.SCHREIBEN_ERR);
			}
		}
		try {
			closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		return null;
	}

	/**
	 * Speichert bzw. aktualisiert das übergebene Benutzerkonto.
	 * 
	 * @param benutzerKonto
	 *            welches gespeichert (ohne Id) oder aktualisiert (mit Id)
	 *            werden soll.
	 * @return das gespeicherte Objekt MIT Id, bzw. <code>null</code> falls
	 *         ein Update durchgeführt wurde.
	 * @throws DatenbankFehlerException
	 *             wirft Datenbankfehler bei Verbindungs- oder Schreibfehlern.
	 */
	private BenutzerkontoBean schreibenBenutzerKonto(
			BenutzerkontoBean benutzerKonto) throws DatenbankFehlerException {
		Connection con = null;
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = this.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		// Neues Benutzerkonto
		if (benutzerKonto.getId() == NullKonstanten.NULL_LONG) {
			int i = 1;
			long id = Long.MIN_VALUE;
			sql = "INSERT INTO " + Tabellen.BENUTZERKONTO + " ("
					+ FelderBenutzerkonto.ID + ", "
					+ FelderBenutzerkonto.PERSON + ", "
					+ FelderBenutzerkonto.LOGINNAME + ", "
					+ FelderBenutzerkonto.PASSWORT + ", "
					+ FelderBenutzerkonto.ROLLEACCOUNT + ", "
					+ FelderBenutzerkonto.ERSTERLOGIN + ", "
					+ FelderBenutzerkonto.LETZTERLOGIN + ", "
					+ FelderBenutzerkonto.GESPERRT + ")"
					+ " VALUES (NULL,?,?,?,?,?,?,?)";
			try {
				pstmt = con.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				pstmt.setLong(i++, benutzerKonto.getBenutzerId());
				pstmt.setString(i++, benutzerKonto.getBenutzername());
				pstmt.setString(i++, benutzerKonto.getPasswort());
				pstmt.setString(i++, benutzerKonto.getRolle().getName());
				if (benutzerKonto.getErsterLogin() != null) {
					pstmt.setDate(i++, new Date(benutzerKonto.getErsterLogin()
							.getTimeInMillis()));
				} else {
					pstmt.setNull(i++, Types.DATE);
				}
				if (benutzerKonto.getLetzterLogin() != null) {
					pstmt.setDate(i++, new Date(benutzerKonto.getLetzterLogin()
							.getTimeInMillis()));
				} else {
					pstmt.setNull(i++, Types.DATE);
				}
				pstmt.setBoolean(i++, benutzerKonto.isGesperrt());
				pstmt.executeUpdate();
				rs = pstmt.getGeneratedKeys();
				rs.next();
				id = rs.getLong(1);
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatenbankFehlerException(
						DatenbankFehlerException.SCHREIBEN_ERR);
			}
			benutzerKonto.setId(id);
			return benutzerKonto;

		} else {
			int j = 1;
			sql = "UPDATE " + Tabellen.BENUTZERKONTO + " SET "
					+ FelderBenutzerkonto.PERSON + "= ?, "
					+ FelderBenutzerkonto.LOGINNAME + "= ?, "
					+ FelderBenutzerkonto.PASSWORT + "= ?, "
					+ FelderBenutzerkonto.ROLLEACCOUNT + "= ?, "
					+ FelderBenutzerkonto.ERSTERLOGIN + "= ?, "
					+ FelderBenutzerkonto.LETZTERLOGIN + "= ?, "
					+ FelderBenutzerkonto.GESPERRT + "= ? " + "WHERE "
					+ FelderBenutzerkonto.ID + "= ? ";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setLong(j++, benutzerKonto.getBenutzerId());
				pstmt.setString(j++, benutzerKonto.getBenutzername());
				pstmt.setString(j++, benutzerKonto.getPasswort());
				pstmt.setString(j++, benutzerKonto.getRolle().getName());
				if (benutzerKonto.getErsterLogin() != null) {
					pstmt.setDate(j++, new Date(benutzerKonto.getErsterLogin()
							.getTimeInMillis()));
				} else {
					pstmt.setNull(j++, Types.DATE);
				}
				if (benutzerKonto.getLetzterLogin() != null) {
					pstmt.setDate(j++, new Date(benutzerKonto.getLetzterLogin()
							.getTimeInMillis()));
				} else {
					pstmt.setNull(j++, Types.DATE);
				}
				pstmt.setBoolean(j++, benutzerKonto.isGesperrt());
				pstmt.setLong(j++, benutzerKonto.getId());
				pstmt.executeUpdate();
				pstmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatenbankFehlerException(
						DatenbankFehlerException.SCHREIBEN_ERR);
			}
		}
		try {
			this.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		return null;
	}

	/**
	 * Speichert bzw. aktualisiert die übergebenen Aktivierungsdaten.
	 * 
	 * @param aktivierung
	 *            welche gespeichert (ohne Id) oder aktualisiert (mit Id) werden
	 *            soll.
	 * @return das gespeicherte Objekt MIT Id, bzw. <code>null</code> falls
	 *         ein Update durchgeführt wurde.
	 * @throws DatenbankFehlerException
	 *             wirft Datenbankfehler bei Verbindungs- oder Schreibfehlern.
	 */
	private AktivierungBean schreibenAktivierung(AktivierungBean aktivierung)
			throws DatenbankFehlerException {
		Connection con = null;
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = this.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		if (aktivierung.getAktivierungsId() == NullKonstanten.NULL_LONG) {
			int i = 1;
			long id = Long.MIN_VALUE;
			sql = "INSERT INTO " + Tabellen.AKTIVIERUNG + " ("
					+ FelderAktivierung.Id + ", " + FelderAktivierung.BENUTZER
					+ ", " + FelderAktivierung.LINK + ", "
					+ FelderAktivierung.VERSANDDATUM + ") "
					+ " VALUES (NULL,?,?,?)";
			try {
				pstmt = con.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				pstmt.setLong(i++, aktivierung.getBenutzerkontoId());
				pstmt.setString(i++, aktivierung.getAktivierungsLink());
				pstmt.setDate(i++, new Date(aktivierung.getVersanddatum()
						.getTimeInMillis()));
				pstmt.executeUpdate();
				rs = pstmt.getGeneratedKeys();
				rs.next();
				id = rs.getLong(1);
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatenbankFehlerException(
						DatenbankFehlerException.SCHREIBEN_ERR);
			}
			aktivierung.setAktivierungsId(id);
			return aktivierung;
		} else {
			int j = 1;
			sql = "UPDATE " + Tabellen.AKTIVIERUNG + " SET "
					+ FelderAktivierung.BENUTZER + "=? , "
					+ FelderAktivierung.LINK + "=? , "
					+ FelderAktivierung.VERSANDDATUM + "=?  " + "WHERE "
					+ FelderAktivierung.Id + "=?";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setLong(j++, aktivierung.getBenutzerkontoId());
				pstmt.setString(j++, aktivierung.getAktivierungsLink());
				pstmt.setDate(j++, new Date(aktivierung.getVersanddatum()
						.getTimeInMillis()));
				pstmt.setLong(j++, aktivierung.getAktivierungsId());
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatenbankFehlerException(
						DatenbankFehlerException.SCHREIBEN_ERR);
			}
		}

		try {
			this.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}

		return null;
	}

	/**
	 * Speichert bzw. aktualisiert die übergebenen Studiendaten.
	 * 
	 * @param studie
	 *            welche gespeichert (ohne Id) oder aktualisiert (mit Id) werden
	 *            soll.
	 * @return das gespeicherte Objekt MIT Id, bzw <code>null</code> falls ein
	 *         Update durchgeführt wurde.
	 * @throws DatenbankFehlerException
	 *             wirft Datenbankfehler bei Verbindungs- oder Schreibfehlern.
	 */
	private StudieBean schreibenStudie(StudieBean studie)
			throws DatenbankFehlerException {
		Connection con = null;
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = this.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		if (studie.getId() == NullKonstanten.NULL_LONG) {
			int i = 1;
			long id = Long.MIN_VALUE;
			try {
				sql = "INSERT INTO " + Tabellen.STUDIE + " (" + FelderStudie.ID
						+ ", " + FelderStudie.BENUTZER + ", "
						+ FelderStudie.NAME + ", " + FelderStudie.BESCHREIBUNG
						+ ", " + FelderStudie.STARTDATUM + ", "
						+ FelderStudie.ENDDATUM + ", " + FelderStudie.PROTOKOLL
						+ ", " + FelderStudie.RANDOMISATIONSART + ", "
						+ FelderStudie.STATUS + ") "
						+ "VALUES (NULL,?,?,?,?,?,?,?,?)";
				pstmt = con.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				pstmt.setLong(i++, studie.getBenutzerkontoId());
				pstmt.setString(i++, studie.getName());
				if (studie.getBeschreibung() != "") {
					pstmt.setString(i++, studie.getBeschreibung());
				} else {
					pstmt.setNull(i++, Types.NULL);
				}
				pstmt.setDate(i++, new Date(studie.getStartDatum()
						.getTimeInMillis()));
				pstmt.setDate(i++, new Date(studie.getEndDatum()
						.getTimeInMillis()));
				pstmt.setString(i++, studie.getStudienprotokollpfad());
				pstmt.setString(i++, studie.getRandomisationseigenschaften()
						.toString());
				pstmt.setString(i++, studie.getStatus().toString());
				pstmt.executeUpdate();
				rs = pstmt.getGeneratedKeys();
				rs.next();
				id = rs.getLong(1);
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatenbankFehlerException(
						DatenbankFehlerException.SCHREIBEN_ERR);
			}
			studie.setId(id);
			return studie;
		} else {
			int j = 1;
			sql = "UPDATE " + Tabellen.STUDIE + " SET " + FelderStudie.BENUTZER
					+ "=?, " + FelderStudie.NAME + "=?, "
					+ FelderStudie.BESCHREIBUNG + "=?, "
					+ FelderStudie.STARTDATUM + "=?, " + FelderStudie.ENDDATUM
					+ "=?, " + FelderStudie.PROTOKOLL + "=?, "
					+ FelderStudie.RANDOMISATIONSART + "=?, "
					+ FelderStudie.STATUS + "=? " + "WHERE " + FelderStudie.ID
					+ "=?";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setLong(j++, studie.getBenutzerkontoId());
				pstmt.setString(j++, studie.getName());
				if (studie.getBeschreibung() != "") {
					pstmt.setString(j++, studie.getBeschreibung());
				} else {
					pstmt.setNull(j++, Types.NULL);
				}
				pstmt.setDate(j++, new Date(studie.getStartDatum()
						.getTimeInMillis()));
				pstmt.setDate(j++, new Date(studie.getEndDatum()
						.getTimeInMillis()));
				pstmt.setString(j++, studie.getStudienprotokollpfad());
				pstmt.setString(j++, studie.getRandomisationseigenschaften()
						.toString());
				pstmt.setString(j++, studie.getStatus().toString());
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatenbankFehlerException(
						DatenbankFehlerException.SCHREIBEN_ERR);
			}
		}
		try {
			this.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		return null;
	}

	/**
	 * Speichert bzw. aktualisiert die übergebenen Studienarmdaten.
	 * 
	 * @param studienarm
	 *            welcher gespeichert (ohne Id) oder aktualisiert (mit Id)
	 *            werden soll.
	 * @return das gespeicherte Objekt MIT Id, bzw. <code>null</code> falls
	 *         ein Update durchgeführt wurde.
	 * @throws DatenbankFehlerException
	 *             wirft Datenbankfehler bei Verbindungs- oder Schreibfehlern.
	 */
	private StudienarmBean schreibenStudienarm(StudienarmBean studienarm)
			throws DatenbankFehlerException {
		Connection con = null;
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = this.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		if (studienarm.getId() == NullKonstanten.NULL_LONG) {
			int i = 1;
			long id = Long.MIN_VALUE;
			try {
				sql = "INSERT INTO " + Tabellen.STUDIENARM + " ("
						+ FelderStudienarm.ID + ", " + FelderStudienarm.STUDIE
						+ ", " + FelderStudienarm.STATUS + ", "
						+ FelderStudienarm.BEZEICHNUNG + ", "
						+ FelderStudienarm.BESCHREIBUNG + ") "
						+ "VALUES (NULL,?,?,?,?,?,?,?,?)";
				pstmt = con.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				pstmt.setLong(i++, studienarm.getStudie().getId());
				pstmt.setString(i++, studienarm.getAStatus().toString());
				pstmt.setString(i++, studienarm.getBezeichnung());
				if (studienarm.getBeschreibung() != "") {
					pstmt.setString(i++, studienarm.getBeschreibung());
				} else {
					pstmt.setNull(i++, Types.NULL);
				}
				pstmt.executeUpdate();
				rs = pstmt.getGeneratedKeys();
				rs.next();
				id = rs.getLong(1);
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatenbankFehlerException(
						DatenbankFehlerException.SCHREIBEN_ERR);
			}
			studienarm.setId(id);
			return studienarm;
		} else {
			int j = 1;
			sql = "UPDATE " + Tabellen.STUDIENARM + " SET "
					+ FelderStudienarm.STUDIE + "=?, "
					+ FelderStudienarm.STATUS + "=?, "
					+ FelderStudienarm.BEZEICHNUNG + "=?, "
					+ FelderStudienarm.BESCHREIBUNG + "=?, " + "WHERE "
					+ FelderStudienarm.ID + "=?";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setLong(j++, studienarm.getStudie().getId());
				pstmt.setString(j++, studienarm.getAStatus().toString());
				pstmt.setString(j++, studienarm.getBezeichnung());
				if (studienarm.getBeschreibung() != "") {
					pstmt.setString(j++, studienarm.getBeschreibung());
				} else {
					pstmt.setNull(j++, Types.NULL);
				}
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatenbankFehlerException(
						DatenbankFehlerException.SCHREIBEN_ERR);
			}
		}
		try {
			this.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		return null;
	}

	/**
	 * Speichert bzw. aktualisiert die übergebenen Patientendaten.
	 * 
	 * @param patient
	 *            welche(r) gespeichert (ohne Id) oder aktualisiert (mit Id)
	 *            werden soll.
	 * @return das gespeicherte Objekt MIT Id, bzw. <code>null</code> falls
	 *         ein Update durchgeführt wurde.
	 * @throws DatenbankFehlerException
	 *             wirft Datenbankfehler bei Verbindungs- oder Schreibfehlern.
	 */
	private PatientBean schreibenPatient(PatientBean patient)
			throws DatenbankFehlerException {
		Connection con = null;
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = this.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		if (patient.getId() == NullKonstanten.NULL_LONG) {
			int i = 1;
			long id = Long.MIN_VALUE;
			try {
				sql = "INSERT INTO " + Tabellen.PATIENT + " ("
						+ FelderPatient.ID + ", " + FelderPatient.BENUTZER
						+ ", " + FelderPatient.STUDIENARM + ", "
						+ FelderPatient.INITIALEN + ", "
						+ FelderPatient.GEBURTSDATUM + ", "
						+ FelderPatient.GESCHLECHT + ", "
						+ FelderPatient.AUFKLAERUNGSDATUM + ", "
						+ FelderPatient.KOERPEROBERFLAECHE + ", "
						+ FelderPatient.PERFORMANCESTATUS + ") "
						+ "VALUES (NULL,?,?,?,?,?,?,?,?)";
				pstmt = con.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				pstmt.setLong(i++, patient.getBenutzerkontoId());
				pstmt.setLong(i++, patient.getStudienarm().getId());
				pstmt.setString(i++, patient.getInitialen());
				pstmt.setDate(i++, new Date(patient.getGeburtsdatum()
						.getTimeInMillis()));
				pstmt.setString(i++, Character.toString(patient.getGeschlecht()));
				pstmt.setDate(i++, new Date(patient.getDatumAufklaerung()
						.getTimeInMillis()));
				pstmt.setFloat(i++, patient.getKoerperoberflaeche());
				pstmt.setInt(i++, patient.getPerformanceStatus());
				pstmt.executeUpdate();
				rs = pstmt.getGeneratedKeys();
				rs.next();
				id = rs.getLong(1);
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatenbankFehlerException(
						DatenbankFehlerException.SCHREIBEN_ERR);
			}
			patient.setId(id);
			return patient;
		} else {
			int j = 1;
			sql = "UPDATE " + Tabellen.PATIENT + " SET "
					+ FelderPatient.BENUTZER + "=?, "
					+ FelderPatient.STUDIENARM + "=?, "
					+ FelderPatient.INITIALEN + "=?, "
					+ FelderPatient.GEBURTSDATUM + "=?, "
					+ FelderPatient.GESCHLECHT + "=?, "
					+ FelderPatient.AUFKLAERUNGSDATUM + "=?, "
					+ FelderPatient.KOERPEROBERFLAECHE + "=?, "
					+ FelderPatient.PERFORMANCESTATUS + "=?, " + "WHERE "
					+ FelderPatient.ID + "=?";
			try {
				pstmt.setLong(j++, patient.getBenutzerkontoId());
				pstmt.setLong(j++, patient.getStudienarm().getId());
				pstmt.setString(j++, patient.getInitialen());
				pstmt.setDate(j++, new Date(patient.getGeburtsdatum()
						.getTimeInMillis()));
				pstmt.setString(j++, Character
						.toString(patient.getGeschlecht()));
				pstmt.setDate(j++, new Date(patient.getDatumAufklaerung()
						.getTimeInMillis()));
				pstmt.setFloat(j++, patient.getKoerperoberflaeche());
				pstmt.setInt(j++, patient.getPerformanceStatus());
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatenbankFehlerException(
						DatenbankFehlerException.SCHREIBEN_ERR);
			}
		}
		try {
			this.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		return null;
	}

	/**
	 * Dokumentation siehe Schnittstellenbeschreibung
	 * 
	 * @see de.randi2.datenbank.DatenbankSchnittstelle#suchenObjekt(de.randi2.datenbank.Filter)
	 */
	public <T extends Filter> Vector<T> suchenObjekt(T zuSuchendesObjekt)
			throws DatenbankFehlerException {
		//pruefe ob Argument ungleich null ist
		if(zuSuchendesObjekt == null) {
			throw new DatenbankFehlerException(DatenbankFehlerException.ARGUMENT_IST_NULL);
		}//pruefe ob Filter auf true gesetzt ist
		if(!zuSuchendesObjekt.isFilter()) {
			throw new DatenbankFehlerException(DatenbankFehlerException.SUCHOBJEKT_IST_KEIN_FILTER);
		}
		
		if (zuSuchendesObjekt instanceof PersonBean) {
			return (Vector<T>) suchenPerson((PersonBean) zuSuchendesObjekt);
		}
		
		if (zuSuchendesObjekt instanceof BenutzerkontoBean) {
			return (Vector<T>) suchenBenutzerkonto((BenutzerkontoBean) zuSuchendesObjekt);
		}
		
		return null;
	}
	
	
	/**
	 * Sucht alle Personen aus der Tabelle {@link Tabellen#PERSON} welche den Kritieren den Filters entsprechen
	 * @param person
	 * 			PersonBean Objekt welches als Filter dient. Es wird nach allen Attribute ungleich 
	 * 			den Nullkonstanten in der Datenbank gesucht
	 * @return
	 * 			Vector mit gefundenen Personen
	 * @throws DatenbankFehlerException 
	 * 			siehe {@link DatenbankSchnittstelle#suchenObjekt(Filter)}
	 * 			
	 */
	private Vector<PersonBean> suchenPerson(PersonBean person) throws DatenbankFehlerException {
		Connection con;
		try {
			con = getConnection();
		} catch (SQLException e) {
			throw new DatenbankFehlerException(DatenbankFehlerException.CONNECTION_ERR);
		}
		PreparedStatement pstmt;
		ResultSet rs;
		PersonBean tmpPerson;
		Vector<PersonBean> personen = new Vector<PersonBean>();
		//erstellen der SQL Abfrage
		String sql ="SELECT * FROM "+Tabellen.PERSON.toString()+" WHERE ";
		if(person.getNachname()!=null) { 
			sql +=FelderPerson.NACHNAME.toString()+" LIKE ?% AND ";
		}
		else {//falls Nachname nicht gesetzt ist, fuehrt das TRUE OR dazu das die Bedingung in dem Fall immer wahr ist
			sql+= "(TRUE OR " + FelderPerson.NACHNAME.toString()+" LIKE ?% AND ";
		}
		if(person.getVorname()!=null) {
			sql +=FelderPerson.VORNAME.toString()+" LIKE ?% AND ";
		}
		else {
			sql+= "(TRUE OR " + FelderPerson.VORNAME.toString()+" LIKE ?%) AND ";
		}
		if(person.getTitel()!=null) {
			sql +=FelderPerson.TITEL.toString()+" LIKE ?% AND ";
		}
		else {
			sql+= "(TRUE OR " + FelderPerson.TITEL.toString()+" LIKE ?%) AND ";
		}
		if(person.getGeschlecht()!=NullKonstanten.NULL_CHAR) {
			sql +=FelderPerson.GESCHLECHT.toString()+" LIKE ?% AND ";
		}
		else {
			sql+= "(TRUE OR " + FelderPerson.GESCHLECHT.toString()+" LIKE ?%) AND ";
		}
		if(person.getTelefonnummer()!=null) {
			sql +=FelderPerson.TELEFONNUMMER.toString()+" LIKE ?% AND ";
		}
		else {
			sql+= "(TRUE OR " + FelderPerson.TELEFONNUMMER.toString()+" LIKE ?%) AND ";
		}
		if(person.getHandynummer()!=null) {
			sql +=FelderPerson.HANDYNUMMER.toString()+" LIKE ?% AND ";
		}
		else {
			sql+= "(TRUE OR " + FelderPerson.HANDYNUMMER.toString()+" LIKE ?%) AND ";
		}
		if(person.getFax()!=null) {
			sql +=FelderPerson.FAX.toString()+" LIKE ?% AND ";
		}
		else {
			sql+= "(TRUE OR " + FelderPerson.FAX.toString()+" LIKE ?%) AND ";
		}
		if(person.getEmail()!=null) {
			sql +=FelderPerson.EMAIL.toString()+"LIKE ?% AND ";
		}
		else {
			sql+= "(TRUE OR " + FelderPerson.EMAIL.toString()+" LIKE ?%) AND ";
		}//wird nach der StellvertreterId ueberhaupt gesucht?
		if(person.getStellvertreterId()!=NullKonstanten.NULL_LONG) {
			sql +=FelderPerson.STELLVERTRETER.toString()+" LIKE ?%";
		}
		else {
			sql+= "(TRUE OR " + FelderPerson.STELLVERTRETER.toString()+"LIKE ?%)";
		}
		try {
			//Prepared Statement erzeugen
			pstmt = con.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, person.getNachname());
			pstmt.setString(index++, person.getVorname());
			pstmt.setString(index++, person.getTitel().toString());
			pstmt.setString(index++, Character.toString(person.getGeschlecht()));
			pstmt.setString(index++, person.getTelefonnummer());
			pstmt.setString(index++, person.getHandynummer());
			pstmt.setString(index++, person.getFax());
			pstmt.setString(index++, person.getEmail());
			pstmt.setLong(index++, person.getStellvertreterId());
			rs = pstmt.executeQuery();
			//durchlaufe ResultSet
			while(rs.next()) {
				//erstelle PersonBeans
				tmpPerson = new PersonBean(rs.getLong(FelderPerson.ID.toString()), 
						rs.getLong(FelderPerson.STELLVERTRETER.toString()),
						rs.getString(FelderPerson.NACHNAME.toString()), 
						rs.getString(FelderPerson.VORNAME.toString()), 
						Titel.parseTitel(rs.getString(FelderPerson.TITEL.toString())),
						rs.getString(FelderPerson.GESCHLECHT.toString()).charAt(0),
						rs.getString(FelderPerson.EMAIL.toString()), 
						rs.getString(FelderPerson.TELEFONNUMMER.toString()), 
						rs.getString(FelderPerson.HANDYNUMMER.toString()), 
						rs.getString(FelderPerson.FAX.toString()));
				//fuege Person dem Vector hinzu
				personen.add(tmpPerson);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (BenutzerException f) {
			throw new DatenbankFehlerException(DatenbankFehlerException.UNGUELTIGE_DATEN);
		}
		try {
			closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(DatenbankFehlerException.CONNECTION_ERR);
		}
		return personen;
	}
	
	/**
	 * Sucht alle Personen aus der {@link Tabellen#BENUTZERKONTO} die den im Filter Bean gesetzten Kriterien entsprechen
	 * @param bk
	 * 			BenutzerkontoBean Objekt welches als Filter dient. Es wird nach allen Attribute ungleich 
	 * 			den Nullkonstanten in der Datenbank gesucht
	 * @return
	 * 			Vector mit gefundenen Benutzerkonten
	 * @throws DatenbankFehlerException 
	 */
	private Vector<BenutzerkontoBean> suchenBenutzerkonto(BenutzerkontoBean bk) throws DatenbankFehlerException {
		Connection con;
		try {
			con = getConnection();
		} catch (SQLException e) {
			throw new DatenbankFehlerException(DatenbankFehlerException.CONNECTION_ERR);
		}
		PreparedStatement pstmt;
		ResultSet rs;
		BenutzerkontoBean tmpBenutzerkonto;
		Vector<BenutzerkontoBean> konten = new Vector<BenutzerkontoBean>();
		//erstellen der SQL Abfrage
		String sql ="SELECT * FROM "+Tabellen.BENUTZERKONTO.toString()+" WHERE ";
		
		if(bk.getBenutzername()==null) {
			sql += "(TRUE OR "+FelderBenutzerkonto.LOGINNAME+" LIKE ?% ) AND ";
		}
		else {
			sql += FelderBenutzerkonto.LOGINNAME+" LIKE ?%  AND ";
		}
		if(bk.getErsterLogin()==null) {
			sql += "(TRUE OR "+FelderBenutzerkonto.ERSTERLOGIN+" LIKE ?% ) AND ";
		}
		else {
			sql += FelderBenutzerkonto.ERSTERLOGIN+" LIKE ?%  AND ";
		}
		if(bk.getLetzterLogin()==null) {
			sql += "(TRUE OR "+FelderBenutzerkonto.LETZTERLOGIN+" LIKE ?% ) AND ";
		}
		else {
			sql += FelderBenutzerkonto.LETZTERLOGIN+" LIKE ?%  AND ";
		}
		sql += FelderBenutzerkonto.ROLLEACCOUNT+" = ? AND";
		sql += FelderBenutzerkonto.GESPERRT+" = ?";
		
		try {
			//Prepared Statement erzeugen
			pstmt = con.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, bk.getBenutzername());
			pstmt.setDate(index++, new Date(bk.getErsterLogin().getTimeInMillis()));
			pstmt.setDate(index++, new Date(bk.getLetzterLogin().getTimeInMillis()));
			pstmt.setString(index++, bk.getRolle().toString());
			pstmt.setBoolean(index++, bk.isGesperrt());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				GregorianCalendar ersterLogin= new GregorianCalendar();
				GregorianCalendar letzterLogin= new GregorianCalendar();
				ersterLogin.setTime(rs.getDate(FelderBenutzerkonto.ERSTERLOGIN.toString()));
				letzterLogin.setTime(rs.getDate(FelderBenutzerkonto.LETZTERLOGIN.toString()));
				
				tmpBenutzerkonto = new BenutzerkontoBean(rs.getLong(FelderBenutzerkonto.ID.toString()),
						rs.getString(FelderBenutzerkonto.PASSWORT.toString()),
						rs.getString(FelderBenutzerkonto.ROLLEACCOUNT.toString()), 
						//TODO Benni bastelt eine Methode die eine Rolle anhand eines uebergebenen Strings liefert
						rs.getLong(FelderBenutzerkonto.PERSONID.toString()),
						rs.getBoolean(FelderBenutzerkonto.GESPERRT.toString()),
						ersterLogin, 
						letzterLogin);
				konten.add(tmpBenutzerkonto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (SystemException f) {
			throw new SystemException(DatenbankFehlerException.UNGUELTIGE_DATEN);
		}
		try {
			closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(DatenbankFehlerException.CONNECTION_ERR);
		}
		return konten;
	}

	/**
	 * Dokumentation siehe Schnittstellenbeschreibung
	 * 
	 * @see de.randi2.datenbank.DatenbankSchnittstelle#suchenObjektId(long,
	 *      de.randi2.datenbank.Filter)
	 */
	@SuppressWarnings("unchecked")
	public <T extends Filter> T suchenObjektId(long id, T nullObjekt)
			throws DatenbankFehlerException {
		if (nullObjekt instanceof PersonBean) {
			PersonBean person = this.suchenPersonId(id);
			return (T) person;
		}
		if (nullObjekt instanceof ZentrumBean) {
			ZentrumBean zentrum = this.suchenZentrumId(id);
			return (T) zentrum;
		}
		if (nullObjekt instanceof BenutzerkontoBean) {
			BenutzerkontoBean benutzerkonto = this.suchenBenutzerkontoId(id);
			return (T) benutzerkonto;
		}
		if (nullObjekt instanceof AktivierungBean) {
			AktivierungBean aktivierung = this.suchenAktivierungId(id);
			return (T) aktivierung;
		}
		if (nullObjekt instanceof PatientBean) {
			PatientBean patient = this.suchenPatientId(id);
			return (T) patient;
		}
		if (nullObjekt instanceof StudieBean) {
			StudieBean studie = this.suchenStudieId(id);
			return (T) studie;
		}
		if (nullObjekt instanceof StudienarmBean) {
			StudienarmBean studienarm = this.suchenStudienarmId(id);
			return (T) studienarm;
		}
		
		
		return null;
	}

	/**
	 * Sucht in der Datenbank nach der Person mit der uebergebenen Id.
	 * 
	 * @param id
	 *            zu suchende Id.
	 * @return Person mit zutreffender Id, null falls keine Person mit
	 *         entsprechender Id gefunden wurde.
	 * @throws DatenbankFehlerException
	 *             Falls bei der Suche ein Fehler auftrat.
	 */
	private PersonBean suchenPersonId(long id) throws DatenbankFehlerException {
		Connection con = null;
		PreparedStatement pstmt;
		ResultSet rs = null;
		PersonBean tmpPerson = null;
		try {
			con = getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		String sql;
		sql = "SELECT * FROM " + Tabellen.PERSON + " WHERE " + FelderPerson.ID
				+ " = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				char[] tmp = rs.getString(FelderPerson.GESCHLECHT.toString())
						.toCharArray();
				try {
					tmpPerson = new PersonBean(rs.getLong(FelderPerson.ID.toString()), 
							rs.getLong(FelderPerson.STELLVERTRETER.toString()),
							rs.getString(FelderPerson.NACHNAME.toString()), 
							rs.getString(FelderPerson.VORNAME.toString()), 
							Titel.parseTitel(rs.getString(FelderPerson.TITEL.toString())), 
							tmp[0], 
							rs.getString(FelderPerson.EMAIL.toString()), 
							rs.getString(FelderPerson.TELEFONNUMMER.toString()), 
							rs.getString(FelderPerson.HANDYNUMMER.toString()), 
							rs.getString(FelderPerson.FAX.toString()));
				} catch (PersonException e) {
					e.printStackTrace();
					throw new DatenbankFehlerException(
							DatenbankFehlerException.UNGUELTIGE_DATEN);
					// sollte hier lieber die Person Exception weitergeleitet
					// werden? wie sieht es mit logging aus?
				}
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.SUCHEN_ERR);
		}
		try {
			closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		return tmpPerson;

	}

	/**
	 * Sucht in der Datenbank nach dem zur Id zugehörigen Zentrum.
	 * 
	 * @param id
	 *            zu suchende Id.
	 * @return Zentrum mit zugehöriger Id, null falls kein Zentrum mit
	 *         entsprechender Id gefunden wurde.
	 * @throws DatenbankFehlerException
	 *             falls bei der Suche ein Fehler auftrat.
	 */
	private ZentrumBean suchenZentrumId(long id)
			throws DatenbankFehlerException {
		Connection con = null;
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ZentrumBean zentrum = null;

		try {
			con = this.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}

		sql = "SELECT * FROM " + Tabellen.ZENTRUM + " WHERE "
				+ FelderZentrum.ID + " =?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				zentrum = new ZentrumBean(rs.getLong(FelderZentrum.ID
						.toString()), rs.getString(FelderZentrum.INSTITUTION
						.toString()), rs.getString(FelderZentrum.ABTEILUNGSNAME
						.toString()), rs
						.getString(FelderZentrum.ORT.toString()), rs
						.getString(FelderZentrum.PLZ.toString()), rs
						.getString(FelderZentrum.STRASSE.toString()), rs
						.getString(FelderZentrum.HAUSNUMMER.toString()), rs
						.getLong(FelderZentrum.ANSPRECHPARTNERID.toString()),
						rs.getString(FelderZentrum.PASSWORT.toString()), rs
								.getBoolean(FelderZentrum.AKTIVIERT.toString()));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.SUCHEN_ERR);
		} catch (BenutzerException e) {
			throw new DatenbankFehlerException(DatenbankFehlerException.UNGUELTIGE_DATEN);
		}

		try {
			this.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		return zentrum;
	}

	/**
	 * Sucht in der Datenbank nach dem zur Id zugehörigen Benutzerkonto.
	 * 
	 * @param id
	 *            zu suchende Id.
	 * @return Benutzerkonto mit zugehöriger Id, null falls kein Benutzerkonto
	 *         mit entsprechender Id gefunden wurde.
	 * @throws DatenbankFehlerException
	 *             falls bei der Suche ein Fehler auftrat.
	 */
	private BenutzerkontoBean suchenBenutzerkontoId(long id)
			throws DatenbankFehlerException {
		Connection con = null;
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BenutzerkontoBean benutzerkonto = null;

		try {
			con = this.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}

		sql = "SELECT * FROM " + Tabellen.BENUTZERKONTO + " WHERE "
				+ FelderBenutzerkonto.ID + " =?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			
//			 benutzerkonto = new BenutzerkontoBean
//			 (rs.getLong(FelderBenutzerkonto.ID.toString()),
//			 rs.getString(FelderBenutzerkonto.LOGINNAME.toString()),
//			 rs.getString(FelderBenutzerkonto.PASSWORT.toString()),
//			 rs.getString(FelderBenutzerkonto.ROLLEACCOUNT.toString()),
//			 rs.getLong(FelderBenutzerkonto.ID.toString()),
//			 rs.getBoolean(FelderBenutzerkonto.GESPERRT.toString()),
//			 rs.getLong(FelderBenutzerkonto.ID.toString());

			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.SUCHEN_ERR);
		}

		try {
			this.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		return benutzerkonto;
	}

	/**
	 * Sucht in der Datenbank nach dem zur Id zugehörigen
	 * Aktivierungseigenschaften.
	 * 
	 * @param id
	 *            zu suchende Id.
	 * @return Aktivierung mit zugehöriger Id, null falls keine Aktivierung mit
	 *         entsprechender Id gefunden wurde.
	 * @throws DatenbankFehlerException
	 *             falls bei der Suche ein Fehler auftrat.
	 */
	private AktivierungBean suchenAktivierungId(long id)
			throws DatenbankFehlerException {
		Connection con = null;
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GregorianCalendar cal = new GregorianCalendar();
		AktivierungBean aktivierung = null;

		try {
			con = this.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}

		sql = "SELECT * FROM " + Tabellen.AKTIVIERUNG + " WHERE "
				+ FelderAktivierung.Id + " =?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cal.setTime(rs.getDate(FelderAktivierung.VERSANDDATUM
						.toString()));
				aktivierung = new AktivierungBean(rs
						.getLong(FelderAktivierung.Id.toString()), cal, rs
						.getLong(FelderAktivierung.BENUTZER.toString()), rs
						.getString(FelderAktivierung.LINK.toString()));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.SUCHEN_ERR);
		} catch(BenutzerException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(DatenbankFehlerException.UNGUELTIGE_DATEN);
		}

		try {
			this.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		return aktivierung;
	}

	/**
	 * Diese Methode sucht in der Datenbank nach der zur Id gehoerigen Studie und
	 * gibt sie als Bean zurueck.
	 * 
	 * @param id
	 * 			zu suchende Id.
	 * @return StudienBean, das zur uebergebenen Id gehoert. Null, falls keine Studie
	 * 			mit entsprechender Id gefunden wurde. 
	 * @throws DatenbankFehlerException wenn bei der Suche ein Fehler auftrat.
	 */
	private StudieBean suchenStudieId(long id) throws DatenbankFehlerException {
		
		Connection con = null;
		PreparedStatement pstmt;
		ResultSet rs = null;
		StudieBean tmpStudie = null;
		GregorianCalendar startDatum = new GregorianCalendar();
		GregorianCalendar endDatum = new GregorianCalendar();
		
		try {
			con = getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		String sql;
		sql = "SELECT * FROM " + Tabellen.STUDIE + " WHERE " + FelderStudie.ID
				+ " = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				
				try {
					startDatum.setTime(rs.getDate(FelderStudie.STARTDATUM.toString()));
					endDatum.setTime(rs.getDate(FelderStudie.ENDDATUM.toString()));
					
					// FIXME kein Feld fuer Parameter ZentrumId in der enum.
					
//					tmpStudie = new StudieBean(	rs.getLong(FelderStudie.ID.toString()), 
//												rs.getString(FelderStudie.BESCHREIBUNG.toString()), 
//												startDatum, endDatum,
//												rs.getString(FelderStudie.PROTOKOLL.toString()),
//												rs.getLong(FelderStudie.RANDOMISATIONSART.toString()),
//												rs.getLong(FelderStudie.ZENTRUM.toString()));
													
				} catch (BenutzerException e) {		
					e.printStackTrace();
					throw new DatenbankFehlerException(
							DatenbankFehlerException.UNGUELTIGE_DATEN);
				}
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.SUCHEN_ERR);
		}
		try {
			closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		return tmpStudie;
	}

	/**
	 * Diese Methode sucht in der Datenbank nach dem zur Id gehoerigen Studienarm und
	 * gibt ihn als Bean zurueck.
	 * 
	 * @param id
	 * 			zu suchende Id.
	 * @return StudienarmBean, das zur uebergebenen Id gehoert. Null, falls kein 
	 * 			Studienarm mit entsprechender Id gefunden wurde. 
	 * @throws DatenbankFehlerException wenn bei der Suche ein Fehler auftrat.
	 */
	private StudienarmBean suchenStudienarmId(long id)
			throws DatenbankFehlerException {
		
		Connection con = null;
		PreparedStatement pstmt;
		ResultSet rs = null;
		StudienarmBean tmpStudienarm = null;
		
		try {
			con = getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		String sql;
		sql = "SELECT * FROM " + Tabellen.PATIENT + " WHERE " + FelderStudienarm.ID
				+ " = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				try {
					// FIXME Bin nicht sicher, ob das nicht mit Proxy realisiert werden muesste. 
					// frage heute noch lplotni. tnoack 04.05.07
					tmpStudienarm = new StudienarmBean(rs.getLong(FelderStudienarm.ID.toString()), 
													//StudieBean, 
													//Status,
													rs.getString(FelderStudienarm.BEZEICHNUNG.toString()),
													rs.getString(FelderStudienarm.BESCHREIBUNG.toString()),
													//Vector<PatientBean> patienten
					);
													
				} catch (BenutzerException e) {		
					e.printStackTrace();
					throw new DatenbankFehlerException(
							DatenbankFehlerException.UNGUELTIGE_DATEN);
				}
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.SUCHEN_ERR);
		}
		try {
			closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		return tmpStudienarm;

	}

	/**
	 * Diese Methode sucht in der Datenbank nach dem zur Id gehoerigen Patient und
	 * gibt ihn als Bean zurueck.
	 * 
	 * @param id
	 * 			zu suchende Id.
	 * @return PatientBean, das zur uebergebenen Id gehoert. Null, falls kein Patient
	 * 			mit entsprechender Id gefunden wurde. 
	 * @throws DatenbankFehlerException wenn bei der Suche ein Fehler auftrat.
	 */
	private PatientBean suchenPatientId(long id)
			throws DatenbankFehlerException {
		
		Connection con = null;
		PreparedStatement pstmt;
		ResultSet rs = null;
		PatientBean tmpPatient = null;
		GregorianCalendar geburtsdatum = new GregorianCalendar();
		GregorianCalendar aufklaerungsdatum = new GregorianCalendar();
		
		try {
			con = getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		String sql;
		sql = "SELECT * FROM " + Tabellen.PATIENT + " WHERE " + FelderPatient.ID
				+ " = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				char[] geschlecht = rs.getString(FelderPatient.GESCHLECHT.toString())
						.toCharArray();
				try {
					geburtsdatum.setTime(rs.getDate(FelderPatient.GEBURTSDATUM.toString()));
					aufklaerungsdatum.setTime(rs.getDate(FelderPatient.AUFKLAERUNGSDATUM.toString()));
					
					tmpPatient = new PatientBean(	rs.getLong(FelderPatient.ID.toString()), 
													rs.getString(FelderPatient.INITIALEN.toString()), 
													geschlecht[0],geburtsdatum, 
													rs.getInt(FelderPatient.PERFORMANCESTATUS.toString()),
													aufklaerungsdatum,
													rs.getInt(FelderPatient.KOERPEROBERFLAECHE.toString()),
													rs.getInt(FelderPatient.STUDIENARM.toString()),
													rs.getLong(FelderPatient.BENUTZER.toString()));
													
				} catch (BenutzerException e) {		
					e.printStackTrace();
					throw new DatenbankFehlerException(
							DatenbankFehlerException.UNGUELTIGE_DATEN);
				}
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.SUCHEN_ERR);
		}
		try {
			closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankFehlerException(
					DatenbankFehlerException.CONNECTION_ERR);
		}
		return tmpPatient;

	}

	/**
	 * Dokumentation siehe Schnittstellenbeschreibung
	 * 
	 * @see de.randi2.datenbank.DatenbankSchnittstelle#suchenMitgliederObjekte(de.randi2.datenbank.Filter,
	 *      de.randi2.datenbank.Filter)
	 */
	public <T extends Filter, U extends Filter> Vector<T> suchenMitgliederObjekte(
			U vater, T kind) throws DatenbankFehlerException {
		if (vater instanceof ZentrumBean && kind instanceof PersonBean) {
			ZentrumBean zentrum = (ZentrumBean) vater;
			PersonBean ansprechpartner = suchenAnsprechpartner(zentrum
					.getAnsprechpartnerId());
			Vector<T> personVec = new Vector<T>();
			personVec.add((T) ansprechpartner);
			return personVec;
		}
		return null;
	}

	/**
	 * Methode sucht den Ansprechpartner eines Zentrums
	 * 
	 * @param id
	 *            Ansprechpartner des Ansprechpartners, welche im ZentrumBean
	 *            der aufrufenden Methode gespeichert st
	 * @return Ansprechpartner
	 * @throws DatenbankFehlerException
	 *             Falls ein DB Fehler auftritt
	 */
	private PersonBean suchenAnsprechpartner(long id)
			throws DatenbankFehlerException {
		return suchenObjektId(id, new PersonBean());
	}


	/**
	 * Baut Verbindung zur Datenbank auf
	 * 
	 * @return Connectionobjekt welches Zugriff auf die Datenbank ermoeglicht.
	 * @throws SQLException
	 */
	private Connection getConnection() throws SQLException {
		Connection con = DriverManager.getConnection("proxool.randi2");
		return con;
	}

	// "jdbc:mysql://"+Config.getProperty(Config.Felder.RELEASE_DB_HOST)+":"+Config.getProperty(Config.Felder.RELEASE_DB_PORT)+"/randi2",Config.getProperty(Config.Felder.RELEASE_DB_NUTZERNAME),Config.getProperty(Config.Felder.RELEASE_DB_PASSWORT

	/**
	 * Trennt Verbindung zur Datenbank.
	 * 
	 * @throws SQLException
	 * @throws DBExceptions
	 */
	private void closeConnection(Connection con) throws SQLException {
		if (con != null && !con.isClosed()) {
			con.close();
		}
	}

}
