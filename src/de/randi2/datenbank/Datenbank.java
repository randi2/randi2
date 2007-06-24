package de.randi2.datenbank;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.log4j.Logger;

import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.AktivierungException;
import de.randi2.model.exceptions.BenutzerException;
import de.randi2.model.exceptions.BenutzerkontoException;
import de.randi2.model.exceptions.PatientException;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.exceptions.RandomisationsException;
import de.randi2.model.exceptions.RechtException;
import de.randi2.model.exceptions.StudieException;
import de.randi2.model.exceptions.StudienarmException;
import de.randi2.model.exceptions.ZentrumException;
import de.randi2.model.fachklassen.Rolle;
import de.randi2.model.fachklassen.Studie.Status;
import de.randi2.model.fachklassen.beans.AktivierungBean;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.PatientBean;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.model.fachklassen.beans.StrataBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.model.fachklassen.beans.StudienarmBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;
import de.randi2.model.fachklassen.beans.PersonBean.Titel;
import de.randi2.randomisation.Randomisation.Algorithmen;
import de.randi2.utility.LogAktion;
import de.randi2.utility.LogGeanderteDaten;
import de.randi2.utility.LogLayout;
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
		STUDIENARM("Studienarm"),
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
		STRATA_AUSPRAEGUNG("Strata_Auspraegung");

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
		/**
		 * Id des Zentrums.
		 */
		ID("zentrumsID"),
		/**
		 * Id des Ansprechpartners.
		 */
		ANSPRECHPARTNERID("Person_personenID"),
		/**
		 * Der Institutsname.
		 */
		INSTITUTION("institution"),
		/**
		 * Der Abteilungsname des Zentrums
		 */
		ABTEILUNGSNAME("abteilungsname"),
		/**
		 * Der Ort.
		 */
		ORT("ort"),
		/**
		 * Die zugehörige Poszleitzahl.
		 */
		PLZ("plz"),
		/**
		 * Die Strasse.
		 */
		STRASSE("strasse"),
		/**
		 * Die Hausnummer.
		 */
		HAUSNUMMER("hausnummer"),
		/**
		 * Das Passwort.
		 */
		PASSWORT("passwort"),
		/**
		 * Der Status ueber die Aktivitaet.
		 */
		AKTIVIERT("aktiviert");

		/**
		 * Name eines Feldes.
		 */
		private String name = "";

		/**
		 * Konstruktor.
		 * 
		 * @param name
		 *            Name eines Feldes.
		 */
		private FelderZentrum(String name) {
			this.name = name;
		}

		/**
		 * to String Methode.
		 * 
		 * @return liefert den Namen eines Feldes.
		 */
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

		/**
		 * Die Id der Person.
		 */
		ID("personenID"),
		/**
		 * Der Nachname der Person.
		 */
		NACHNAME("nachname"),
		/**
		 * Der Vorname der Person.
		 */
		VORNAME("vorname"),
		/**
		 * Der Titel der Person.
		 */
		TITEL("titel"),
		/**
		 * Das Geschlecht der Person.
		 */
		GESCHLECHT("geschlecht"),
		/**
		 * Die Telefonnummer der Person.
		 */
		TELEFONNUMMER("telefonnummer"),
		/**
		 * Die Handynummer der Person.
		 */
		HANDYNUMMER("handynummer"),
		/**
		 * Die Faxnummer der Person.
		 */
		FAX("fax"),
		/**
		 * Die eMail der Person.
		 */
		EMAIL("email"),
		/**
		 * Die Id der Person (vom Stellvertreter).
		 */
		STELLVERTRETER("Person_personenID");

		/**
		 * Name eines Feldes.
		 */
		private String name = "";

		/**
		 * Konstruktor.
		 * 
		 * @param name
		 *            Name eines Feldes.
		 */
		private FelderPerson(String name) {
			this.name = name;
		}

		/**
		 * to String Methode.
		 * 
		 * @return liefert den Namen eines Feldes.
		 */
		public String toString() {
			return this.name;
		}
	}

	/**
	 * Enum Klasse welche die Felder der Tabelle Benutzerkonto repraesentiert.
	 * 
	 * @author Kai Marco Krupka [kkrupka@stud.hs-heilbronn.de]
	 * 
	 */
	private enum FelderBenutzerkonto {
		/**
		 * Die Id des Benutzerkontos
		 */
		ID("benutzerkontenID"),
		/**
		 * Der Loginname für das Benutzerkonto.
		 */
		LOGINNAME("loginname"),
		/**
		 * Das Passwort für den Login.
		 */
		PASSWORT("passwort"),
		/**
		 * Die Id der zugehörigen Person.
		 */
		PERSONID("Person_personenID"),
		/**
		 * Die Id des zugehörigen Zentrums.
		 */
		ZENTRUMID("Zentrum_zentrumsID"),
		/**
		 * Die Rolle des Benutzers.
		 */
		ROLLEACCOUNT("rolle"),
		/**
		 * Das Datum des ersten Logins.
		 */
		ERSTERLOGIN("erster_login"),
		/**
		 * Das Datum des letzten Login.
		 */
		LETZTERLOGIN("letzter_login"),
		/**
		 * Der Sperrungsstatus des Benutzers.
		 */
		GESPERRT("gesperrt");

		/**
		 * Name eines Feldes.
		 */
		private String name = "";

		/**
		 * Konstruktor.
		 * 
		 * @param name
		 *            Name eines Feldes.
		 */
		private FelderBenutzerkonto(String name) {
			this.name = name;
		}

		/**
		 * to String Methode.
		 * 
		 * @return liefert den Namen eines Feldes.
		 */
		public String toString() {
			return this.name;
		}
	}

	/**
	 * Enum Klasse welche die Felder der Tabelle Aktivierung repraesentiert.
	 * 
	 * @author Kai Marco Krupka [kkrupka@stud.hs-heilbronn.de]
	 * 
	 */
	private enum FelderAktivierung {
		/**
		 * Die Id der Aktivierung.
		 */
		Id("aktivierungsID"),
		/**
		 * Die Id des zugehörigen Benutzerkontos.
		 */
		BENUTZER("Benutzerkonto_benutzerkontenID"),
		/**
		 * Der Aktivierungslink für die Aktivierung des Kontos.
		 */
		LINK("aktivierungslink"),
		/**
		 * Das Versanddatum des Aktivierungslink.
		 */
		VERSANDDATUM("versanddatum");

		/**
		 * Name eines Feldes.
		 */
		private String name = "";

		/**
		 * Konstruktor.
		 * 
		 * @param name
		 *            Name eines Feldes.
		 */
		private FelderAktivierung(String name) {
			this.name = name;
		}

		/**
		 * to String Methode.
		 * 
		 * @return liefert den Namen eines Feldes.
		 */
		public String toString() {
			return this.name;
		}
	}

	/**
	 * Enum Klasse welche die Felder der Tabelle Studie repraesentiert.
	 * 
	 * @author Kai Marco Krupka [kkrupka@stud.hs-heilbronn.de]
	 * 
	 */
	private enum FelderStudie {
		/**
		 * Die Id der Studie.
		 */
		ID("studienID"),
		/**
		 * Die Id der zugehörigen Studie.
		 */
		BENUTZER("Benutzerkonto_benutzerkontenID"),
		/**
		 * Der Name der Studie.
		 */
		NAME("name"),
		/**
		 * Die Beschreibung der Studie.
		 */
		BESCHREIBUNG("beschreibung"),
		/**
		 * Randomisationsalgorithmus
		 */
		RANDOMISATIONSALGORITHMUS("randomisationsalgorithmus"),
		/**
		 * Das Startdatum der Studie.
		 */
		STARTDATUM("startdatum"),
		/**
		 * Das Enddatum der Studie.
		 */
		ENDDATUM("enddatum"),
		/**
		 * Der Pfad des Studienprotokolls.
		 */
		PROTOKOLL("studienprotokoll"),
		/**
		 * Die Art der Randomisation.
		 */
		RANDOMISATIONSART("randomisationArt"),
		/**
		 * Der Status der Studie.
		 */
		STATUS("status_Studie");

		/**
		 * Name eines Feldes.
		 */
		private String name = "";

		/**
		 * Konstruktor.
		 * 
		 * @param name
		 *            Name eines Feldes.
		 */
		private FelderStudie(String name) {
			this.name = name;
		}

		/**
		 * to String Methode.
		 * 
		 * @return liefert den Namen eines Feldes.
		 */
		public String toString() {
			return this.name;
		}
	}

	/**
	 * Enum Klasse welche die Felder der Tabelle Studienarm repraesentiert.
	 * 
	 * @author Kai Marco Krupka [kkrupka@stud.hs-heilbronn.de]
	 * 
	 */
	private enum FelderStudienarm {
		/**
		 * Die Id des Studienarms.
		 */
		ID("studienarmID"),
		/**
		 * Die Id der Studie.
		 */
		STUDIE("Studie_studienID"),
		/**
		 * Der Status des Studienarms.
		 */
		STATUS("status_aktivitaet"),
		/**
		 * Die Bezeichnung des Studienarms.
		 */
		BEZEICHNUNG("bezeichnung"),
		/**
		 * Die Beschreibung des Studienarms.
		 */
		BESCHREIBUNG("beschreibung");

		/**
		 * Name eines Feldes.
		 */
		private String name = "";

		/**
		 * Konstruktor.
		 * 
		 * @param name
		 *            Name eines Feldes.
		 */
		private FelderStudienarm(String name) {
			this.name = name;
		}

		/**
		 * to String Methode.
		 * 
		 * @return liefert den Namen eines Feldes.
		 */
		public String toString() {
			return this.name;
		}
	}

	/**
	 * Enum Klasse welche die Felder der Tabelle Patient repraesentiert.
	 * 
	 * @author Kai Marco Krupka [kkrupka@stud.hs-heilbronn.de]
	 * 
	 */
	private enum FelderPatient {
		/**
		 * Die Id des Patienten.
		 */
		ID("patientenID"),
		/**
		 * Die Id des zugehörigen Benutzers, welcher den Patient aufgenommen
		 * hat.
		 */
		BENUTZER("Benutzerkonto_benutzerkontenID"),
		/**
		 * Die Id des zugehörigen Studienarms.
		 */
		STUDIENARM("Studienarm_studienarmID"),
		/**
		 * Die Initialen des Patienten.
		 */
		INITIALEN("initialen"),
		/**
		 * Das Geburtsdatum des Patienten.
		 */
		GEBURTSDATUM("geburtsdatum"),
		/**
		 * das Geschlecht des Patienten.
		 */
		GESCHLECHT("geschlecht"),
		/**
		 * Das Datum der Aufklärung.
		 */
		AUFKLAERUNGSDATUM("aufklaerungsdatum"),
		/**
		 * Die Koerperoberflaeche des Patienten.
		 */
		KOERPEROBERFLAECHE("koerperoberflaeche"),
		/**
		 * Der Performancestatus.
		 */
		PERFORMANCESTATUS("performancestatus"),
		/**
		 * Serialisierter String mit der fuer diesen Patienten spezifischen
		 * Stratakombination
		 */
		STRATA_GRUPPE("strata_gruppe");

		/**
		 * Name eines Feldes.
		 */
		private String name = "";

		/**
		 * Konstruktor.
		 * 
		 * @param name
		 *            Name eines Feldes.
		 */
		private FelderPatient(String name) {
			this.name = name;
		}

		/**
		 * to String Methode.
		 * 
		 * @return liefert den Namen eines Feldes.
		 */
		public String toString() {
			return this.name;
		}
	}

	/**
	 * Felder der Tabelle Block.
	 * 
	 * @author Frederik Reifschneider [Reifschneider@stud.uni-heidelberg.de]
	 */
	private enum FelderBlock {
		/**
		 * Die Id der Blockrandomisation.
		 */
		ID("blockID"),
		/**
		 * Die Id der Studie.
		 */
		STUDIEID("Studie_studienID"),
		/**
		 * Der Blockwert der Randomisation.
		 */
		BLOCKWERT("blockwert");

		/**
		 * Name eines Feldes
		 */
		private String name = "";

		/**
		 * Konstruktor.
		 * 
		 * @param name
		 *            Name eines Feldes.
		 */
		private FelderBlock(String name) {
			this.name = name;
		}

		/**
		 * liefert den Namen des Feldes
		 * 
		 * @return String mit Namen des Feldes
		 * @see java.lang.Enum#toString()
		 */
		public String toString() {
			return this.name;
		}
	}

	/**
	 * Felder der Tabelle StrataTypen.
	 * 
	 * @author Kai Marco Krupka [kkrupka@stud.hs-heilbronn.de]
	 */
	private enum FelderStrataTypen {
		/**
		 * Die Id des StrataTyps.
		 */
		Id("strata_TypenID"),
		/**
		 * Die Id der zugehörigen Studie.
		 */
		STUDIEID("Studie_studienID"),
		/**
		 * Der Name des Stratatypen.
		 */
		NAME("name"),
		/**
		 * Die Beschreibung des Stratatypen.
		 */
		BESCHREIBUNG("beschreibung");

		/**
		 * Name eines Feldes
		 */
		private String name = "";

		/**
		 * Konstruktor.
		 * 
		 * @param name
		 *            Name eines Feldes.
		 */
		private FelderStrataTypen(String name) {
			this.name = name;
		}

		/**
		 * liefert den Namen des Feldes
		 * 
		 * @return String mit Namen des Feldes
		 * @see java.lang.Enum#toString()
		 */
		public String toString() {
			return this.name;
		}
	}

	/**
	 * Felder der Tabelle StrataAuspraegung.
	 * 
	 * @author Kai Marco Krupka [kkrupka@stud.hs-heilbronn.de]
	 */
	private enum FelderStrataAuspraegung {
		/**
		 * Die Id der Strataauspraegung.
		 */
		Id("strata_WerteID"),
		/**
		 * Die Id des zugehörigen Stratatypen.
		 */
		STRATAID("Strata_Typen_strata_TypenID"),
		/**
		 * Der Wert der Strataauspraegung.
		 */
		WERT("wert");

		/**
		 * Name eines Feldes
		 */
		private String name = "";

		/**
		 * Konstruktor.
		 * 
		 * @param name
		 *            Name eines Feldes.
		 */
		private FelderStrataAuspraegung(String name) {
			this.name = name;
		}

		/**
		 * liefert den Namen des Feldes
		 * 
		 * @return String mit Namen des Feldes
		 * @see java.lang.Enum#toString()
		 */
		public String toString() {
			return this.name;
		}
	}

	/**
	 * Felder der Tabelle StudieHasZentrum.
	 * 
	 * @author Kai Marco Krupka [kkrupka@stud.hs-heilbronn.de]
	 */
	private enum FelderStudieHasZentrum {
		/**
		 * Die Id der Studie.
		 */
		STUDIENID("Studie_studienID"),
		/**
		 * Die Id des Zentrums.
		 */
		ZENTRUMID("Zentrum_zentrumsID");

		/**
		 * Name eines Feldes
		 */
		private String name = "";

		/**
		 * Konstruktor.
		 * 
		 * @param name
		 *            Name eines Feldes.
		 */
		private FelderStudieHasZentrum(String name) {
			this.name = name;
		}

		/**
		 * liefert den Namen des Feldes
		 * 
		 * @return String mit Namen des Feldes
		 * @see java.lang.Enum#toString()
		 */
		public String toString() {
			return this.name;
		}
	}

	/**
	 * Konstruktor der Datenbankklasse.
	 */
	public Datenbank() {
		log = Logger.getLogger(LogLayout.DATENAENDERUNG);
		log.info("Datenbank initialisiert");
	}

	/**
	 * Dokumentation siehe Schnittstellenbeschreibung
	 * 
	 * @see de.randi2.datenbank.DatenbankSchnittstelle#loeschenObjekt(de.randi2.datenbank.Filter)
	 */
	public <T extends Filter> void loeschenObjekt(T zuLoeschendesObjekt)
			throws DatenbankExceptions {
		if (zuLoeschendesObjekt == null) {
			throw new DatenbankExceptions(DatenbankExceptions.ARGUMENT_IST_NULL);
		} else {
			// PersonBean loeschen
			if (zuLoeschendesObjekt instanceof PersonBean) {
				PersonBean person = (PersonBean) zuLoeschendesObjekt;
				this.loeschenPerson(person);
			}
			// Aktivierung loeschen
			else if (zuLoeschendesObjekt instanceof AktivierungBean) {
				AktivierungBean aktivierung = (AktivierungBean) zuLoeschendesObjekt;
				this.loeschenAktivierung(aktivierung);
			}
			// Studie loeschen
			else if (zuLoeschendesObjekt instanceof StudieBean) {
				StudieBean studie = (StudieBean) zuLoeschendesObjekt;
				this.loeschenStudie(studie);
			}
		}
	}

	/**
	 * Loescht das übergebene Personenobjekt aus der Datenbank.
	 * 
	 * @param person
	 *            zu löschendes PersonBean.
	 * @throws DatenbankExceptions
	 *             wirft Datenbankfehler bei Verbindungs- oder Loeschfehlern.
	 */
	private void loeschenPerson(PersonBean person) throws DatenbankExceptions {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		sql = "DELETE FROM " + Tabellen.PERSON + " WHERE " + FelderPerson.ID
				+ "=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, person.getId());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.LOESCHEN_ERR);
		} finally {
			try {
				ConnectionFactory.getInstanz().closeConnection(con);
			} catch (DatenbankExceptions e) {
				e.printStackTrace();
				throw new DatenbankExceptions(
						DatenbankExceptions.CONNECTION_ERR);
			}
		}
		this.loggenDaten(person, LogKonstanten.LOESCHE_DATENSATZ);
	}

	/**
	 * Loescht das übergebene Aktivierungsobjekt aus der Datenbank.
	 * 
	 * @param aktivierung
	 *            zu löschendes AktivierungBean.
	 * @throws DatenbankExceptions
	 *             wirft Datenbankfehler bei Verbindungs- oder Loeschfehlern.
	 */
	private void loeschenAktivierung(AktivierungBean aktivierung)
			throws DatenbankExceptions {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		sql = "DELETE FROM " + Tabellen.AKTIVIERUNG + " WHERE "
				+ FelderAktivierung.Id + "= ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, aktivierung.getId());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.LOESCHEN_ERR);
		} finally {
			try {
				ConnectionFactory.getInstanz().closeConnection(con);
			} catch (DatenbankExceptions e) {
				e.printStackTrace();
				throw new DatenbankExceptions(
						DatenbankExceptions.CONNECTION_ERR);
			}
		}
		this.loggenDaten(aktivierung, LogKonstanten.LOESCHE_DATENSATZ);
	}

	/**
	 * Loescht das übergebene Studienobjekt aus der Datenbank.
	 * 
	 * @param studie
	 *            zu löschendes StudieBean.
	 * @throws DatenbankExceptions
	 *             wirft Datenbankfehler bei Verbindungs- oder Loeschfehlern.
	 */
	private void loeschenStudie(StudieBean studie) throws DatenbankExceptions {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		sql = "DELETE FROM " + Tabellen.STUDIE + " WHERE " + FelderStudie.ID
				+ "= ? AND " + Tabellen.STUDIE + "." + FelderStudie.STATUS
				+ " = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, studie.getId());
			pstmt.setString(2, Status.INVORBEREITUNG.toString());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.LOESCHEN_ERR);
		} finally {
			try {
				ConnectionFactory.getInstanz().closeConnection(con);
			} catch (DatenbankExceptions e) {
				e.printStackTrace();
				throw new DatenbankExceptions(
						DatenbankExceptions.CONNECTION_ERR);
			}
		}
		this.loggenDaten(studie, LogKonstanten.LOESCHE_DATENSATZ);
	}

	/**
	 * Dokumentation siehe Schnittstellenbeschreibung
	 * 
	 * @see de.randi2.datenbank.DatenbankSchnittstelle#schreibenObjekt(de.randi2.datenbank.Filter)
	 */
	@SuppressWarnings("unchecked")
	public <T extends Filter> T schreibenObjekt(T zuSchreibendesObjekt)
			throws DatenbankExceptions {
		if (zuSchreibendesObjekt == null) {
			throw new DatenbankExceptions(DatenbankExceptions.ARGUMENT_IST_NULL);
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
			// StudienarmBean
			else if (zuSchreibendesObjekt instanceof StudienarmBean) {
				StudienarmBean studienarm = (StudienarmBean) zuSchreibendesObjekt;
				return (T) this.schreibenStudienarm(studienarm);
			}
			// PatientenBean
			else if (zuSchreibendesObjekt instanceof PatientBean) {
				PatientBean patient = (PatientBean) zuSchreibendesObjekt;
				return (T) this.schreibenPatient(patient);
			}
			if (zuSchreibendesObjekt instanceof StrataBean) {
				StrataBean strata = (StrataBean) zuSchreibendesObjekt;
				return (T) schreibenStrata(strata);
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
	 * @throws DatenbankExceptions
	 *             wirft Datenbankfehler bei Verbindungs- oder Schreibfehlern.
	 */
	private PersonBean schreibenPerson(PersonBean person)
			throws DatenbankExceptions {
		Connection con = null;
		String sql = "";
		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		// JDBC
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
					+ FelderPerson.HANDYNUMMER + ","
					+ FelderPerson.STELLVERTRETER + ")"
					+ "VALUES (NULL,?,?,?,?,?,?,?,?,?)";
			try {
				// Erstellung des Statements
				pstmt = con.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, person.getNachname());
				pstmt.setString(2, person.getVorname());
				pstmt.setString(3, Character.toString(person.getGeschlecht()));
				pstmt.setString(4, person.getTitel().toString());
				pstmt.setString(5, person.getEmail());
				pstmt.setString(6, person.getFax());
				pstmt.setString(7, person.getTelefonnummer());
				pstmt.setString(8, person.getHandynummer());
				if (person.getStellvertreterId() == NullKonstanten.DUMMY_ID) {
					pstmt.setNull(9, Types.NULL);
				} else {
					pstmt.setLong(9, person.getStellvertreterId());
				}

				pstmt.executeUpdate();
				rs = pstmt.getGeneratedKeys();
				rs.next();
				id = rs.getLong(1);
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatenbankExceptions(DatenbankExceptions.SCHREIBEN_ERR);
			} finally {
				try {
					ConnectionFactory.getInstanz().closeConnection(con);
				} catch (DatenbankExceptions e) {
					e.printStackTrace();
					throw new DatenbankExceptions(
							DatenbankExceptions.CONNECTION_ERR);
				}
			}
			person.setId(id);
			// loggen eines neuen Datensatzes
			loggenDaten(person, LogKonstanten.NEUER_DATENSATZ);
			return person;
		}
		// vorhandene Person wird aktualisiert
		else {
			sql = "UPDATE " + Tabellen.PERSON + " SET " + FelderPerson.NACHNAME
					+ "=?," + FelderPerson.VORNAME + "=?,"
					+ FelderPerson.GESCHLECHT + "=?," + FelderPerson.TITEL
					+ "=?," + FelderPerson.EMAIL + "=?," + FelderPerson.FAX
					+ "=?," + FelderPerson.TELEFONNUMMER + "=?,"
					+ FelderPerson.HANDYNUMMER + "=?,"
					+ FelderPerson.STELLVERTRETER + "=?" + " WHERE "
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
				pstmt.setString(8, person.getHandynummer());
				if (person.getStellvertreterId() == NullKonstanten.DUMMY_ID) {
					pstmt.setNull(9, Types.NULL);
				} else {
					pstmt.setLong(9, person.getStellvertreterId());
				}
				pstmt.setLong(10, person.getId());
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				throw new DatenbankExceptions(e, sql,
						DatenbankExceptions.SCHREIBEN_ERR);
			} finally {
				try {
					ConnectionFactory.getInstanz().closeConnection(con);
				} catch (DatenbankExceptions e) {
					throw new DatenbankExceptions(
							DatenbankExceptions.CONNECTION_ERR);
				}
			}
			// loggen eines geaenderten Datensatzes
			loggenDaten(person, LogKonstanten.AKTUALISIERE_DATENSATZ);
		}
		return person;
	}

	/**
	 * Speichert bzw. aktualisiert das uebergebene Zentrum in der Datenbank.
	 * 
	 * @param zentrum
	 *            zu speicherndes Zentrum
	 * @return das Zentrum mit der vergebenen eindeutigen Id bzw. das
	 *         aktualisierte Zentrum
	 * @throws DatenbankExceptions
	 *             wirft Datenbankfehler bei Verbindungs- oder Schreibfehlern.
	 */
	private ZentrumBean schreibenZentrum(ZentrumBean zentrum)
			throws DatenbankExceptions {
		Connection con = null;
		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
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
				throw new DatenbankExceptions(DatenbankExceptions.SCHREIBEN_ERR);
			}
			zentrum.setId(id);
			loggenDaten(zentrum, LogKonstanten.NEUER_DATENSATZ);
			return zentrum;
		}
		// vorhandenes Zentrum wird aktualisiert
		else {
			// sql Query
			sql = "UPDATE " + Tabellen.ZENTRUM + " SET "
					+ FelderZentrum.INSTITUTION + "=?,"
					+ FelderZentrum.ABTEILUNGSNAME + "=?,"
					+ FelderZentrum.ANSPRECHPARTNERID + "=?,"
					+ FelderZentrum.STRASSE + "=?," + FelderZentrum.HAUSNUMMER
					+ "=?," + FelderZentrum.PLZ + "=?," + FelderZentrum.ORT
					+ "=?," + FelderZentrum.PASSWORT + "=?,"
					+ FelderZentrum.AKTIVIERT + "=?" + " WHERE "
					+ FelderZentrum.ID + "=?";
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
				pstmt.setBoolean(9, zentrum.getIstAktiviert());
				pstmt.setLong(10, zentrum.getId());
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatenbankExceptions(DatenbankExceptions.SCHREIBEN_ERR);
			}
		}
		try {
			ConnectionFactory.getInstanz().closeConnection(con);
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		// loggen des geaenderten Datensatzes
		loggenDaten(zentrum, LogKonstanten.AKTUALISIERE_DATENSATZ);
		return zentrum;
	}

	/**
	 * Speichert bzw. aktualisiert das übergebene Benutzerkonto.
	 * 
	 * @param benutzerKonto
	 *            welches gespeichert (ohne Id) oder aktualisiert (mit Id)
	 *            werden soll.
	 * @return das gespeicherte Objekt MIT Id, bzw. <code>null</code> falls
	 *         ein Update durchgeführt wurde.
	 * @throws DatenbankExceptions
	 *             wirft Datenbankfehler bei Verbindungs- oder Schreibfehlern.
	 */
	private BenutzerkontoBean schreibenBenutzerKonto(
			BenutzerkontoBean benutzerKonto) throws DatenbankExceptions {
		// JDBC
		Connection con = null;
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		// Neues Benutzerkonto
		if (benutzerKonto.getId() == NullKonstanten.NULL_LONG) {
			int i = 1;
			long id = Long.MIN_VALUE;
			sql = "INSERT INTO " + Tabellen.BENUTZERKONTO + " ("
					+ FelderBenutzerkonto.ID + ", "
					+ FelderBenutzerkonto.PERSONID + ", "
					+ FelderBenutzerkonto.LOGINNAME + ", "
					+ FelderBenutzerkonto.PASSWORT + ", "
					+ FelderBenutzerkonto.ZENTRUMID + ", "
					+ FelderBenutzerkonto.ROLLEACCOUNT + ", "
					+ FelderBenutzerkonto.ERSTERLOGIN + ", "
					+ FelderBenutzerkonto.LETZTERLOGIN + ", "
					+ FelderBenutzerkonto.GESPERRT + ")"
					+ " VALUES (NULL,?,?,?,?,?,?,?,?)";
			try {

				// JDBC Statement erzeugen
				pstmt = con.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				pstmt.setLong(i++, benutzerKonto.getBenutzerId());
				pstmt.setString(i++, benutzerKonto.getBenutzername());
				pstmt.setString(i++, benutzerKonto.getPasswort());
				pstmt.setLong(i++, benutzerKonto.getZentrumId());
				pstmt.setString(i++, benutzerKonto.getRolle().getName());
				if (benutzerKonto.getErsterLogin() == null) {
					pstmt.setNull(i++, Types.DATE);
				} else {
					pstmt.setTimestamp(i++, new Timestamp(benutzerKonto
							.getErsterLogin().getTimeInMillis()));
				}
				if (benutzerKonto.getLetzterLogin() == null) {
					pstmt.setNull(i++, Types.DATE);
				} else {
					pstmt.setTimestamp(i++, new Timestamp(benutzerKonto
							.getLetzterLogin().getTimeInMillis()));
				}
				pstmt.setBoolean(i++, benutzerKonto.isGesperrt());
				pstmt.executeUpdate();
				rs = pstmt.getGeneratedKeys();
				rs.next();
				id = rs.getLong(1);
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				if (e.getErrorCode() == 1062) {
					throw new DatenbankExceptions(
							DatenbankExceptions.TESTER_EXISTIERT_ERR);
				}
				throw new DatenbankExceptions(e, sql,
						DatenbankExceptions.SCHREIBEN_ERR);
			}
			benutzerKonto.setId(id);
			loggenDaten(benutzerKonto, LogKonstanten.NEUER_DATENSATZ);
			return benutzerKonto;

		} else {
			int j = 1;
			sql = "UPDATE " + Tabellen.BENUTZERKONTO + " SET "
					+ FelderBenutzerkonto.PERSONID + "= ?, "
					+ FelderBenutzerkonto.LOGINNAME + "= ?, "
					+ FelderBenutzerkonto.PASSWORT + "= ?, "
					+ FelderBenutzerkonto.ZENTRUMID + "= ?, "
					+ FelderBenutzerkonto.ROLLEACCOUNT + "= ?, "
					+ FelderBenutzerkonto.ERSTERLOGIN + "= ?, "
					+ FelderBenutzerkonto.LETZTERLOGIN + "= ?, "
					+ FelderBenutzerkonto.GESPERRT + "= ? " + "WHERE "
					+ FelderBenutzerkonto.ID + "= ? ";
			try {
				// JDBC Statement erzeugen
				pstmt = con.prepareStatement(sql);
				pstmt.setLong(j++, benutzerKonto.getBenutzerId());
				pstmt.setString(j++, benutzerKonto.getBenutzername());
				pstmt.setString(j++, benutzerKonto.getPasswort());
				pstmt.setLong(j++, benutzerKonto.getZentrumId());
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
				throw new DatenbankExceptions(DatenbankExceptions.SCHREIBEN_ERR);
			}
		}
		try {
			ConnectionFactory.getInstanz().closeConnection(con);
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		loggenDaten(benutzerKonto, LogKonstanten.AKTUALISIERE_DATENSATZ);
		return benutzerKonto;
	}

	/**
	 * Speichert bzw. aktualisiert die übergebenen Aktivierungsdaten.
	 * 
	 * @param aktivierung
	 *            welche gespeichert (ohne Id) oder aktualisiert (mit Id) werden
	 *            soll.
	 * @return das gespeicherte Objekt MIT Id, bzw. <code>null</code> falls
	 *         ein Update durchgeführt wurde.
	 * @throws DatenbankExceptions
	 *             wirft Datenbankfehler bei Verbindungs- oder Schreibfehlern.
	 */
	private AktivierungBean schreibenAktivierung(AktivierungBean aktivierung)
			throws DatenbankExceptions {
		Connection con = null;
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		if (aktivierung.getId() == NullKonstanten.NULL_LONG) {
			int i = 1;
			long id = Long.MIN_VALUE;
			sql = "INSERT INTO " + Tabellen.AKTIVIERUNG + " ("
					+ FelderAktivierung.Id + ", " + FelderAktivierung.BENUTZER
					+ ", " + FelderAktivierung.LINK + ", "
					+ FelderAktivierung.VERSANDDATUM + ") "
					+ " VALUES (NULL,?,?,?)";
			try {
				// Statement erzeugen
				pstmt = con.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				pstmt.setLong(i++, aktivierung.getBenutzerkontoId());
				pstmt.setString(i++, aktivierung.getAktivierungsLink());
				pstmt.setTimestamp(i++, new Timestamp(aktivierung
						.getVersanddatum().getTimeInMillis()));
				pstmt.executeUpdate();
				rs = pstmt.getGeneratedKeys();
				rs.next();
				id = rs.getLong(1);
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatenbankExceptions(DatenbankExceptions.SCHREIBEN_ERR);
			}
			aktivierung.setId(id);
			loggenDaten(aktivierung, LogKonstanten.NEUER_DATENSATZ);
			return aktivierung;
		} else {
			int j = 1;
			sql = "UPDATE " + Tabellen.AKTIVIERUNG + " SET "
					+ FelderAktivierung.BENUTZER + "=? , "
					+ FelderAktivierung.LINK + "=? , "
					+ FelderAktivierung.VERSANDDATUM + "=?  " + "WHERE "
					+ FelderAktivierung.Id + "=?";
			try {
				// statement erzeugen
				pstmt = con.prepareStatement(sql);
				pstmt.setLong(j++, aktivierung.getBenutzerkontoId());
				pstmt.setString(j++, aktivierung.getAktivierungsLink());
				pstmt.setTimestamp(j++, new Timestamp(aktivierung
						.getVersanddatum().getTimeInMillis()));
				pstmt.setLong(j++, aktivierung.getId());
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatenbankExceptions(DatenbankExceptions.SCHREIBEN_ERR);
			} finally {
				try {
					ConnectionFactory.getInstanz().closeConnection(con);
				} catch (DatenbankExceptions e) {
					e.printStackTrace();
					throw new DatenbankExceptions(
							DatenbankExceptions.CONNECTION_ERR);
				}
			}
		}
		loggenDaten(aktivierung, LogKonstanten.AKTUALISIERE_DATENSATZ);
		return aktivierung;
	}

	/**
	 * Speichert bzw. aktualisiert die übergebenen Studiendaten.
	 * 
	 * @param studie
	 *            welche gespeichert (ohne Id) oder aktualisiert (mit Id) werden
	 *            soll.
	 * @return das gespeicherte Objekt MIT Id, bzw <code>null</code> falls ein
	 *         Update durchgeführt wurde.
	 * @throws DatenbankExceptions
	 *             wirft Datenbankfehler bei Verbindungs- oder Schreibfehlern.
	 */
	private StudieBean schreibenStudie(StudieBean studie)
			throws DatenbankExceptions {
		Connection con = null;
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
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
				if (!studie.getBeschreibung().equals("")) {
					pstmt.setString(i++, studie.getBeschreibung());
				} else {
					pstmt.setNull(i++, Types.NULL);
				}
				pstmt.setDate(i++, new Date(studie.getStartDatum()
						.getTimeInMillis()));
				pstmt.setDate(i++, new Date(studie.getEndDatum()
						.getTimeInMillis()));
				pstmt.setString(i++, studie.getStudienprotokollpfad());
				pstmt.setString(i++, studie.getRandomisationsart());
				pstmt.setString(i++, studie.getStatus().toString());
				pstmt.executeUpdate();
				rs = pstmt.getGeneratedKeys();
				rs.next();
				id = rs.getLong(1);
				// Speichern der Abhaengigen Zentren
				sql = "DELETE FROM " + Tabellen.STUDIE_ZENTRUM.toString()
						+ " WHERE " + FelderStudieHasZentrum.STUDIENID
						+ toString() + " = ? AND "
						+ FelderStudieHasZentrum.ZENTRUMID + toString()
						+ " NOT IN (";
				String sql2 = "INSERT INTO "
						+ Tabellen.STUDIE_ZENTRUM.toString() + " VALUES ";
				if (studie.getZentren() != null) {
					Iterator<ZentrumBean> it = studie.getZentren().iterator();
					ZentrumBean tmp;
					while (it.hasNext()) {
						tmp = it.next();
						sql += tmp.getId() + ",";
						sql += "(" + id + "," + tmp.getId() + "),";
					}
					sql += "-1)";
					sql2 = sql2.substring(0, sql2.length() - 1)
							+ " ON DUPLICATE KEY UPDATE"; // letztes Komma
															// entfernen
					pstmt = con.prepareStatement(sql);
					pstmt.setLong(1, id);
					pstmt.executeUpdate();
					pstmt = con.prepareStatement(sql2);
					pstmt.executeUpdate();
				}
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				throw new DatenbankExceptions(e, sql,
						DatenbankExceptions.SCHREIBEN_ERR);
			}
			studie.setId(id);
			loggenDaten(studie, LogKonstanten.NEUER_DATENSATZ);
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
				if (!studie.getBeschreibung().equals("")) {
					pstmt.setString(j++, studie.getBeschreibung());
				} else {
					pstmt.setNull(j++, Types.NULL);
				}
				pstmt.setDate(j++, new Date(studie.getStartDatum()
						.getTimeInMillis()));
				pstmt.setDate(j++, new Date(studie.getEndDatum()
						.getTimeInMillis()));
				pstmt.setString(j++, studie.getStudienprotokollpfad());
				pstmt.setString(j++, studie.getRandomisationsart());
				pstmt.setString(j++, studie.getStatus().toString());
				pstmt.setLong(j++, studie.getId());
				pstmt.executeUpdate();
				pstmt.close();
				// Speichern der Abhaengigen Zentren
				sql = "DELETE FROM " + Tabellen.STUDIE_ZENTRUM.toString()
						+ " WHERE "
						+ FelderStudieHasZentrum.STUDIENID.toString()
						+ " = ? AND "
						+ FelderStudieHasZentrum.ZENTRUMID.toString()
						+ " NOT IN (";
				String sql2 = "INSERT INTO "
						+ Tabellen.STUDIE_ZENTRUM.toString() + " VALUES ";
				if (studie.getZentren() != null
						&& studie.getZentren().size() > 0) {
					Iterator<ZentrumBean> it = studie.getZentren().iterator();
					ZentrumBean tmp;
					while (it.hasNext()) {
						tmp = it.next();
						sql += tmp.getId() + ",";
						sql += "(" + studie.getId() + "," + tmp.getId() + "),";
					}
					sql += "-1)";
					sql2 = sql2.substring(0, sql2.length() - 1)
							+ " ON DUPLICATE KEY UPDATE"; // letztes Komma
															// entfernen
					pstmt = con.prepareStatement(sql);
					pstmt.setLong(1, studie.getId());
					pstmt.executeUpdate();
					pstmt = con.prepareStatement(sql2);
					pstmt.executeUpdate();
				}
			} catch (SQLException e) {
				throw new DatenbankExceptions(e, sql,
						DatenbankExceptions.SCHREIBEN_ERR);
			}
			loggenDaten(studie, LogKonstanten.AKTUALISIERE_DATENSATZ);
		}
		try {
			ConnectionFactory.getInstanz().closeConnection(con);
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		return studie;
	}

	/**
	 * Speichert bzw. aktualisiert die übergebenen Studienarmdaten.
	 * 
	 * @param studienarm
	 *            welcher gespeichert (ohne Id) oder aktualisiert (mit Id)
	 *            werden soll.
	 * @return das gespeicherte Objekt MIT Id, bzw. <code>null</code> falls
	 *         ein Update durchgeführt wurde.
	 * @throws DatenbankExceptions
	 *             wirft Datenbankfehler bei Verbindungs- oder Schreibfehlern.
	 */
	private StudienarmBean schreibenStudienarm(StudienarmBean studienarm)
			throws DatenbankExceptions {
		Connection con = null;
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
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
				pstmt.setString(i++, studienarm.getStatus().toString());
				pstmt.setString(i++, studienarm.getBezeichnung());
				if (!studienarm.getBeschreibung().equals("")) {
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
				throw new DatenbankExceptions(DatenbankExceptions.SCHREIBEN_ERR);
			}
			studienarm.setId(id);
			loggenDaten(studienarm, LogKonstanten.NEUER_DATENSATZ);
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
				pstmt.setString(j++, studienarm.getStatus().toString());
				pstmt.setString(j++, studienarm.getBezeichnung());
				if (!studienarm.getBeschreibung().equals("")) {
					pstmt.setString(j++, studienarm.getBeschreibung());
				} else {
					pstmt.setNull(j++, Types.NULL);
				}
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatenbankExceptions(DatenbankExceptions.SCHREIBEN_ERR);
			}
			loggenDaten(studienarm, LogKonstanten.AKTUALISIERE_DATENSATZ);
		}
		try {
			ConnectionFactory.getInstanz().closeConnection(con);
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		return studienarm;
	}

	/**
	 * Speichert bzw. aktualisiert die übergebenen Patientendaten.
	 * 
	 * @param patient
	 *            welche(r) gespeichert (ohne Id) oder aktualisiert (mit Id)
	 *            werden soll.
	 * @return das gespeicherte Objekt MIT Id, bzw. <code>null</code> falls
	 *         ein Update durchgeführt wurde.
	 * @throws DatenbankExceptions
	 *             wirft Datenbankfehler bei Verbindungs- oder Schreibfehlern.
	 */
	private PatientBean schreibenPatient(PatientBean patient)
			throws DatenbankExceptions {
		Connection con = null;
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
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
				pstmt.setLong(i++, patient.getStudienarmId());
				pstmt.setString(i++, patient.getInitialen());
				pstmt.setDate(i++, new Date(patient.getGeburtsdatum()
						.getTimeInMillis()));
				pstmt.setString(i++, Character
						.toString(patient.getGeschlecht()));
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
				throw new DatenbankExceptions(DatenbankExceptions.SCHREIBEN_ERR);
			}
			patient.setId(id);
			loggenDaten(patient, LogKonstanten.NEUER_DATENSATZ);
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
				pstmt = con.prepareStatement(sql);
				pstmt.setLong(j++, patient.getBenutzerkontoId());
				pstmt.setLong(j++, patient.getStudienarmId());
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
				throw new DatenbankExceptions(DatenbankExceptions.SCHREIBEN_ERR);
			}
			loggenDaten(patient, LogKonstanten.AKTUALISIERE_DATENSATZ);
		}
		try {
			ConnectionFactory.getInstanz().closeConnection(con);
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		return patient;
	}

	/**
	 * Speichert bzw. aktualisiert die übergebenen Stratadaten.
	 * 
	 * @param strata
	 *            welche(r) gespeichert (ohne Id) oder aktualisiert (mit Id)
	 *            werden soll.
	 * @return das gespeicherte Objekt MIT Id, bzw. <code>null</code> falls
	 *         ein Update durchgeführt wurde.
	 * @throws DatenbankExceptions
	 *             wirft Datenbankfehler bei Verbindungs- oder Schreibfehlern.
	 */
	private StrataBean schreibenStrata(StrataBean strata)
			throws DatenbankExceptions {
		// TODO Implementierung fehlt.
		return null;
	}

	/**
	 * Dokumentation siehe Schnittstellenbeschreibung
	 * 
	 * @see de.randi2.datenbank.DatenbankSchnittstelle#suchenObjekt(de.randi2.datenbank.Filter)
	 */
	@SuppressWarnings("unchecked")
	public <T extends Filter> Vector<T> suchenObjekt(T zuSuchendesObjekt)
			throws DatenbankExceptions {
		// pruefe ob Argument ungleich null ist
		if (zuSuchendesObjekt == null) {
			throw new DatenbankExceptions(DatenbankExceptions.ARGUMENT_IST_NULL);
		}// pruefe ob Filter auf true gesetzt ist
		if (!zuSuchendesObjekt.isFilter()) {
			throw new DatenbankExceptions(
					DatenbankExceptions.SUCHOBJEKT_IST_KEIN_FILTER);
		}
		// PersonBean
		if (zuSuchendesObjekt instanceof PersonBean) {
			return (Vector<T>) suchenPerson((PersonBean) zuSuchendesObjekt);
		}
		// BenutzerkontoBean
		if (zuSuchendesObjekt instanceof BenutzerkontoBean) {
			return (Vector<T>) suchenBenutzerkonto((BenutzerkontoBean) zuSuchendesObjekt);
		}
		// ZentrumBean
		if (zuSuchendesObjekt instanceof ZentrumBean) {
			return (Vector<T>) suchenZentrum((ZentrumBean) zuSuchendesObjekt);
		}
		// AktivierungBean
		if (zuSuchendesObjekt instanceof AktivierungBean) {
			return (Vector<T>) suchenAktivierung((AktivierungBean) zuSuchendesObjekt);
		}
		// PatientBean
		if (zuSuchendesObjekt instanceof PatientBean) {
			return (Vector<T>) suchenPatient((PatientBean) zuSuchendesObjekt);
		}
		// StudienarmBean
		if (zuSuchendesObjekt instanceof StudienarmBean) {
			return (Vector<T>) suchenStudienarm((StudienarmBean) zuSuchendesObjekt);
		}

		if (zuSuchendesObjekt instanceof StudieBean) {
			return (Vector<T>) suchenStudie((StudieBean) zuSuchendesObjekt);

		}

		return null;
	}

	/**
	 * Sucht alle Personen aus der Tabelle {@link Tabellen#PERSON} welche den
	 * Kritieren den Filters entsprechen
	 * 
	 * @param person
	 *            PersonBean Objekt welches als Filter dient. Es wird nach allen
	 *            Attribute ungleich den Nullkonstanten in der Datenbank gesucht
	 * @return Vector mit gefundenen Personen
	 * @throws DatenbankExceptions
	 *             siehe {@link DatenbankSchnittstelle#suchenObjekt(Filter)}
	 * 
	 */
	private Vector<PersonBean> suchenPerson(PersonBean person)
			throws DatenbankExceptions {
		Connection con;
		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		PreparedStatement pstmt;
		ResultSet rs;
		PersonBean tmpPerson;
		Vector<PersonBean> personen = new Vector<PersonBean>();
		// erstellen der SQL Abfrage
		String sql = "SELECT * FROM " + Tabellen.PERSON.toString();
		int counter = 0;
		if (person.getNachname() != null) {
			sql += " WHERE " + FelderPerson.NACHNAME.toString() + " LIKE ? ";
			counter++;
		}
		if (person.getVorname() != null) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderPerson.VORNAME.toString() + " LIKE ? ";
			counter++;
		}
		if (person.getTitel() != null) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderPerson.TITEL.toString() + " = ? ";
			counter++;
		}
		if (person.getGeschlecht() != NullKonstanten.NULL_CHAR) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderPerson.GESCHLECHT.toString() + " = ? ";
			counter++;
		}
		if (person.getTelefonnummer() != null) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderPerson.TELEFONNUMMER.toString() + " = ? ";
			counter++;
		}
		if (person.getHandynummer() != null) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderPerson.HANDYNUMMER.toString() + " = ? ";
			counter++;
		}
		if (person.getFax() != null) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderPerson.FAX.toString() + " = ? ";
			counter++;
		}
		if (person.getEmail() != null) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderPerson.EMAIL.toString() + "= ?";
			counter++;
		}
		try {
			// Prepared Statement erzeugen
			pstmt = con.prepareStatement(sql);
			int index = 1;
			if (person.getNachname() != null) {
				pstmt.setString(index++, person.getNachname() + "%");
			}
			if (person.getVorname() != null) {
				pstmt.setString(index++, person.getVorname() + "%");
			}
			if (person.getTitel() != null) {
				pstmt.setString(index++, person.getTitel().toString());
			}
			if (person.getGeschlecht() != NullKonstanten.NULL_CHAR) {
				pstmt.setString(index++, Character.toString(person
						.getGeschlecht()));
			}
			if (person.getTelefonnummer() != null) {
				pstmt.setString(index++, person.getTelefonnummer());
			}
			if (person.getHandynummer() != null) {
				pstmt.setString(index++, person.getHandynummer());
			}
			if (person.getFax() != null) {
				pstmt.setString(index++, person.getFax());
			}
			if (person.getEmail() != null) {
				pstmt.setString(index++, person.getEmail());
			}
			rs = pstmt.executeQuery();
			// durchlaufe ResultSet
			while (rs.next()) {
				// erstelle PersonBeans
				long stellvertreterId = rs.getLong(FelderPerson.STELLVERTRETER
						.toString());
				if (stellvertreterId == Types.NULL) {
					stellvertreterId = NullKonstanten.DUMMY_ID;
				}
				tmpPerson = new PersonBean(rs.getLong(FelderPerson.ID
						.toString()), stellvertreterId, rs
						.getString(FelderPerson.NACHNAME.toString()), rs
						.getString(FelderPerson.VORNAME.toString()),
						Titel.parseTitel(rs.getString(FelderPerson.TITEL
								.toString())), rs.getString(
								FelderPerson.GESCHLECHT.toString()).charAt(0),
						rs.getString(FelderPerson.EMAIL.toString()), rs
								.getString(FelderPerson.TELEFONNUMMER
										.toString()),
						rs.getString(FelderPerson.HANDYNUMMER.toString()), rs
								.getString(FelderPerson.FAX.toString()));
				// fuege Person dem Vector hinzu
				personen.add(tmpPerson);
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (BenutzerException f) {
			throw new DatenbankExceptions(DatenbankExceptions.UNGUELTIGE_DATEN);
		} finally {
			try {
				ConnectionFactory.getInstanz().closeConnection(con);
			} catch (DatenbankExceptions e) {
				e.printStackTrace();
				throw new DatenbankExceptions(
						DatenbankExceptions.CONNECTION_ERR);
			}
		}
		return personen;
	}

	/**
	 * Sucht alle Personen aus der {@link Tabellen#BENUTZERKONTO} die den im
	 * Filter Bean gesetzten Kriterien entsprechen
	 * 
	 * @param bk
	 *            BenutzerkontoBean Objekt welches als Filter dient. Es wird
	 *            nach allen Attribute ungleich den Nullkonstanten in der
	 *            Datenbank gesucht
	 * @return Vector mit gefundenen Benutzerkonten
	 * @throws DatenbankExceptions
	 *             SQL Exceptions werden weitergeleitet und automatisch geloggt.
	 *             Probleme beim erstellen der Personen Objekte werden mit
	 *             {@link DatenbankExceptions#UNGUELTIGE_DATEN} dem Benutzer
	 *             mitgeteilt Rechte Verletzungen werden geloggt.
	 */
	private Vector<BenutzerkontoBean> suchenBenutzerkonto(BenutzerkontoBean bk)
			throws DatenbankExceptions {
		Connection con;
		String sql = "";
		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		PreparedStatement pstmt;
		ResultSet rs;
		BenutzerkontoBean tmpBenutzerkonto;
		Vector<BenutzerkontoBean> konten = new Vector<BenutzerkontoBean>();
		// erstellen der SQL Abfrage
		int counter = 0;
		sql = "SELECT * FROM " + Tabellen.BENUTZERKONTO.toString();

		if (bk.getBenutzername() != null) {
			sql += " WHERE " + FelderBenutzerkonto.LOGINNAME.toString()
					+ " LIKE ? ";
			counter++;
		}

		// falls erster und letzter Login gesetzt sind wird der Bereich
		// dazwischen gesucht
		if (bk.getLetzterLogin() != null && bk.getErsterLogin() != null) {
			if (bk.getErsterLogin().after(bk.getLetzterLogin())) {
				throw new DatenbankExceptions(
						DatenbankExceptions.UNGUELTIGE_DATEN);
			}
			if (bk.getErsterLogin() != null) {
				if (counter == 0) {
					sql += " WHERE ";
				} else {
					sql += " AND ";
				}
				sql += FelderBenutzerkonto.ERSTERLOGIN.toString() + " >= ? ";
				counter++;
			}

			if (bk.getLetzterLogin() != null) {
				if (counter == 0) {
					sql += " WHERE ";
				} else {
					sql += " AND ";
				}
				sql += FelderBenutzerkonto.LETZTERLOGIN.toString() + " <= ? ";
				counter++;
			}

		} else {
			if (bk.getErsterLogin() != null) {
				if (counter == 0) {
					sql += " WHERE ";
				} else {
					sql += " AND ";
				}
				sql += FelderBenutzerkonto.ERSTERLOGIN.toString() + " = ? ";
				counter++;
			}
			if (bk.getLetzterLogin() != null) {
				if (counter == 0) {
					sql += " WHERE ";
				} else {
					sql += " AND ";
				}
				sql += FelderBenutzerkonto.LETZTERLOGIN.toString() + " = ? ";
				counter++;
			}
		}
		if (bk.getRolle() != null) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderBenutzerkonto.ROLLEACCOUNT.toString() + " = ? ";
			counter++;
		}

		if (counter == 0) {
			sql += " WHERE ";
		} else {
			sql += " AND ";
		}
		sql += FelderBenutzerkonto.GESPERRT.toString() + " = ? ";
		counter++;

		if (bk.getZentrumId() != NullKonstanten.NULL_LONG) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderBenutzerkonto.ZENTRUMID.toString() + " = ?";
		}
		if (bk.getBenutzerId() != NullKonstanten.NULL_LONG) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderBenutzerkonto.PERSONID.toString() + " = ?";
		}
		try {
			// Prepared Statement erzeugen
			pstmt = con.prepareStatement(sql);
			int index = 1;
			if (bk.getBenutzername() != null) {
				pstmt.setString(index++, bk.getBenutzername() + "%");
			}
			if (bk.getErsterLogin() != null) {
				pstmt.setDate(index++, new Date(bk.getErsterLogin()
						.getTimeInMillis()));
			}
			if (bk.getLetzterLogin() != null) {
				pstmt.setDate(index++, new Date(bk.getLetzterLogin()
						.getTimeInMillis()));
			}
			if (bk.getRolle() != null) {
				pstmt.setString(index++, bk.getRolle().toString());
			}
			pstmt.setBoolean(index++, bk.isGesperrt());
			if (bk.getZentrumId() != NullKonstanten.NULL_LONG) {
				pstmt.setLong(index++, bk.getZentrumId());
			}
			if (bk.getBenutzerId() != NullKonstanten.NULL_LONG) {
				pstmt.setLong(index++, bk.getBenutzerId());
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {

				GregorianCalendar ersterLogin = null;
				GregorianCalendar letzterLogin = null;

				Timestamp ersterLoginDate = rs
						.getTimestamp(FelderBenutzerkonto.ERSTERLOGIN
								.toString());
				Timestamp letzterLoginDate = rs
						.getTimestamp(FelderBenutzerkonto.LETZTERLOGIN
								.toString());

				// Datum nur setzen, wenn Feld in Datenbank != null, dhaehn
				if (ersterLoginDate != null) {
					ersterLogin = new GregorianCalendar();
					ersterLogin.setTime(ersterLoginDate);

				}
				if (letzterLoginDate != null) {
					letzterLogin = new GregorianCalendar();
					letzterLogin.setTime(letzterLoginDate);
				}

				tmpBenutzerkonto = new BenutzerkontoBean(rs
						.getLong(FelderBenutzerkonto.ID.toString()), rs
						.getString(FelderBenutzerkonto.LOGINNAME.toString()),
						rs.getString(FelderBenutzerkonto.PASSWORT.toString()),
						rs.getLong(FelderBenutzerkonto.ZENTRUMID.toString()),
						Rolle.getRolle(rs
								.getString(FelderBenutzerkonto.ROLLEACCOUNT
										.toString())), rs
								.getLong(FelderBenutzerkonto.PERSONID
										.toString()), rs
								.getBoolean(FelderBenutzerkonto.GESPERRT
										.toString()), ersterLogin, letzterLogin);
				System.out.println(tmpBenutzerkonto.toString());

				konten.add(tmpBenutzerkonto);

			}
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new DatenbankExceptions(e, sql,
					DatenbankExceptions.SUCHEN_ERR);
		} catch (BenutzerException f) {
			f.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.UNGUELTIGE_DATEN);
		} catch (SystemException g) {
			g.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.UNGUELTIGE_DATEN);
		} finally {
			try {
				ConnectionFactory.getInstanz().closeConnection(con);
			} catch (DatenbankExceptions e) {
				e.printStackTrace();
				throw new DatenbankExceptions(
						DatenbankExceptions.CONNECTION_ERR);
			}
		}

		return konten;
	}

	/**
	 * Sucht alle Zentren aus der {@link Tabellen#ZENTRUM} die den im Filter
	 * Bean gesetzten Kriterien entsprechen.
	 * 
	 * @param zentrum
	 *            Zentrum mit gesetzten Eigenschaften nach denen gesucht wird.
	 * @return Vector mit gefundenen Zentren
	 * @throws DatenbankExceptions
	 *             falls bei der Suche ein Fehler auftrat
	 */
	private Vector<ZentrumBean> suchenZentrum(ZentrumBean zentrum)
			throws DatenbankExceptions {
		Connection con;
		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		PreparedStatement pstmt;
		ResultSet rs;
		ZentrumBean tmpZentrum;
		Vector<ZentrumBean> zentren = new Vector<ZentrumBean>();

		String sql = "SELECT * FROM " + Tabellen.ZENTRUM.toString();
		int counter = 0;
		if (zentrum.getInstitution() != null) {
			sql += " WHERE " + FelderZentrum.INSTITUTION.toString()
					+ " LIKE ? ";
			counter++;
		}

		if (zentrum.getAbteilung() != null) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderZentrum.ABTEILUNGSNAME.toString() + " LIKE ? ";
			counter++;
		}

		if (zentrum.getOrt() != null) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderZentrum.ORT.toString() + " LIKE ? ";
			counter++;
		}

		if (zentrum.getPlz() != null) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderZentrum.PLZ.toString() + " LIKE ? ";
			counter++;
		}

		if (zentrum.getStrasse() != null) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderZentrum.STRASSE.toString() + " LIKE ? ";
			counter++;
		}

		if (zentrum.getHausnr() != null) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderZentrum.HAUSNUMMER.toString() + " LIKE ? ";
			counter++;
		}

		if (counter == 0) {
			sql += " WHERE ";
		} else {
			sql += " AND ";
		}
		sql += FelderZentrum.AKTIVIERT + " = ? ";

		try {
			pstmt = con.prepareStatement(sql);
			int index = 1;

			if (zentrum.getInstitution() != null) {
				pstmt.setString(index++, zentrum.getInstitution() + "%");
			}
			if (zentrum.getAbteilung() != null) {
				pstmt.setString(index++, zentrum.getAbteilung() + "%");
			}
			if (zentrum.getOrt() != null) {
				pstmt.setString(index++, zentrum.getOrt() + "%");
			}
			if (zentrum.getPlz() != null) {
				pstmt.setString(index++, zentrum.getPlz() + "%");
			}
			if (zentrum.getStrasse() != null) {
				pstmt.setString(index++, zentrum.getStrasse() + "%");
			}
			if (zentrum.getHausnr() != null) {
				pstmt.setString(index++, zentrum.getHausnr() + "%");
			}
			pstmt.setBoolean(index++, zentrum.getIstAktiviert());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tmpZentrum = new ZentrumBean(rs.getLong(FelderZentrum.ID
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
				zentren.add(tmpZentrum);
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.UNGUELTIGE_DATEN);
		} catch (ZentrumException g) {
			g.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.UNGUELTIGE_DATEN);
		} finally {
			try {
				ConnectionFactory.getInstanz().closeConnection(con);
			} catch (DatenbankExceptions e) {
				e.printStackTrace();
				throw new DatenbankExceptions(
						DatenbankExceptions.CONNECTION_ERR);
			}
		}

		return zentren;
	}

	/**
	 * Sucht alle Aktivierungen aus der {@link Tabellen#AKTIVIERUNG} die den im
	 * Filter Bean gesetzten Kriterien entsprechen.
	 * 
	 * @param aktivierung
	 *            Bean mit Suchparametern
	 * @return Vector mit gefundenen Aktivierungsbeans
	 * @throws DatenbankExceptions
	 */
	private Vector<AktivierungBean> suchenAktivierung(
			AktivierungBean aktivierung) throws DatenbankExceptions {
		Connection con;
		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		PreparedStatement pstmt;
		ResultSet rs;
		AktivierungBean tmpAktivierung;
		Vector<AktivierungBean> aktivierungen = new Vector<AktivierungBean>();
		int counter = 0;
		String sql = "SELECT * FROM " + Tabellen.AKTIVIERUNG.toString();

		if (aktivierung.getId() != NullKonstanten.NULL_LONG) {
			sql += " WHERE " + FelderAktivierung.Id.toString() + " = ? ";
			counter++;
		}
		if (aktivierung.getBenutzerkontoId() != NullKonstanten.NULL_LONG) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderAktivierung.BENUTZER.toString() + " = ? ";
			counter++;
		}
		if (aktivierung.getAktivierungsLink() != null) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderAktivierung.LINK.toString() + " = ? ";
			counter++;
		}
		if (aktivierung.getVersanddatum() != null) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderAktivierung.VERSANDDATUM.toString() + " = ? ";
			counter++;
		}

		try {
			counter = 1;
			pstmt = con.prepareStatement(sql);
			if (aktivierung.getId() != NullKonstanten.NULL_LONG) {
				pstmt.setLong(counter++, aktivierung.getId());
			}
			if (aktivierung.getBenutzerkontoId() != NullKonstanten.NULL_LONG) {
				pstmt.setLong(counter++, aktivierung.getBenutzerkontoId());
			}
			if (aktivierung.getAktivierungsLink() != null) {
				pstmt.setString(counter++, aktivierung.getAktivierungsLink());
			}
			if (aktivierung.getVersanddatum() != null) {
				pstmt.setTimestamp(counter++, new Timestamp(aktivierung
						.getVersanddatum().getTimeInMillis()));
			}
			rs = pstmt.executeQuery();

			while (rs.next()) {
				GregorianCalendar versanddatum = new GregorianCalendar();
				versanddatum
						.setTime(rs.getTimestamp(FelderAktivierung.VERSANDDATUM
								.toString()));
				tmpAktivierung = new AktivierungBean(rs
						.getLong(FelderAktivierung.Id.toString()),
						versanddatum, rs.getLong(FelderAktivierung.BENUTZER
								.toString()), rs
								.getString(FelderAktivierung.LINK.toString()));
				aktivierungen.add(tmpAktivierung);
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (AktivierungException e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.UNGUELTIGE_DATEN);
		} finally {
			try {
				ConnectionFactory.getInstanz().closeConnection(con);
			} catch (DatenbankExceptions e) {
				e.printStackTrace();
				throw new DatenbankExceptions(
						DatenbankExceptions.CONNECTION_ERR);
			}
		}
		return aktivierungen;
	}

	/**
	 * Sucht alle Patienten in der DB die den Kriterien im uebergebenen Suchbean
	 * entsprechen.
	 * 
	 * @param patient
	 *            Bean mit gesetztem Filter. Felder die ungleich der
	 *            Nullkonstanten sind werden in die Abfrage mit einbezogen
	 * @return Vector mit gefundenen Patienten
	 * @throws DatenbankExceptions
	 *             Falls Fehler auftreten
	 */
	private Vector<PatientBean> suchenPatient(PatientBean patient)
			throws DatenbankExceptions {
		Connection con;
		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PatientBean pat = null;
		Vector<PatientBean> patienten = new Vector<PatientBean>();
		int counter = 0;
		String sql = "SELECT * FROM " + Tabellen.PATIENT.toString();

		if (patient.getBenutzerkontoId() != NullKonstanten.NULL_LONG) {
			sql += " WHERE " + patient.getBenutzerkontoId() + " = ? ";
			counter++;
		}
		if (patient.getStudienarmId() != NullKonstanten.NULL_LONG) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderPatient.STUDIENARM.toString() + " = ? ";
			counter++;
		}
		if (patient.getInitialen() != null) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderPatient.INITIALEN.toString() + " = ? ";
			counter++;
		}
		if (patient.getGeburtsdatum() != null) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderPatient.GEBURTSDATUM.toString() + " = ?";
			counter++;
		}
		if (patient.getGeschlecht() != NullKonstanten.NULL_CHAR) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderPatient.GESCHLECHT.toString() + " = ?";
			counter++;
		}
		if (patient.getDatumAufklaerung() != null) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderPatient.AUFKLAERUNGSDATUM.toString() + " = ?";
			counter++;
		}
		if (patient.getKoerperoberflaeche() != NullKonstanten.NULL_FLOAT) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderPatient.KOERPEROBERFLAECHE.toString() + " = ? ";
			counter++;
		}
		if (patient.getPerformanceStatus() != NullKonstanten.NULL_INT) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderPatient.PERFORMANCESTATUS.toString() + " = ?";
			counter++;
		}

		try {
			pstmt = con.prepareStatement(sql);
			counter = 1;
			if (patient.getBenutzerkontoId() != NullKonstanten.NULL_LONG) {
				pstmt.setLong(counter++, patient.getBenutzerkontoId());

			}
			if (patient.getStudienarmId() != NullKonstanten.NULL_LONG) {
				pstmt.setLong(counter++, patient.getStudienarmId());
			}
			if (patient.getInitialen() != null) {
				pstmt.setString(counter++, patient.getInitialen());
			}
			if (patient.getGeburtsdatum() != null) {
				pstmt.setDate(counter++, new Date(patient.getGeburtsdatum()
						.getTimeInMillis()));
			}
			if (patient.getGeschlecht() != NullKonstanten.NULL_CHAR) {
				pstmt.setString(counter++, String.valueOf(patient
						.getGeschlecht()));
			}
			if (patient.getDatumAufklaerung() != null) {
				pstmt.setDate(counter++, new Date(patient.getDatumAufklaerung()
						.getTimeInMillis()));
			}
			if (patient.getKoerperoberflaeche() != NullKonstanten.NULL_FLOAT) {
				pstmt.setFloat(counter++, patient.getKoerperoberflaeche());
			}
			if (patient.getPerformanceStatus() != NullKonstanten.NULL_INT) {
				pstmt.setInt(counter++, patient.getPerformanceStatus());
			}
			rs = pstmt.executeQuery();

			GregorianCalendar geburtsdatum = null;
			GregorianCalendar aufklaerungsdatum = null;

			while (rs.next()) {
				geburtsdatum = new GregorianCalendar();
				aufklaerungsdatum = new GregorianCalendar();
				geburtsdatum.setTime(rs.getDate(FelderPatient.GEBURTSDATUM
						.toString()));
				aufklaerungsdatum.setTime(rs
						.getDate(FelderPatient.AUFKLAERUNGSDATUM.toString()));

				pat = new PatientBean(rs.getLong(FelderPatient.ID.toString()),
						rs.getString(FelderPatient.INITIALEN.toString()), rs
								.getString(FelderPatient.GESCHLECHT.toString())
								.toCharArray()[0], geburtsdatum, rs
								.getInt(FelderPatient.PERFORMANCESTATUS
										.toString()), aufklaerungsdatum, rs
								.getInt(FelderPatient.KOERPEROBERFLAECHE
										.toString()), rs
								.getLong(FelderPatient.STUDIENARM.toString()),
						rs.getLong(FelderPatient.BENUTZER.toString()), rs
								.getString(FelderPatient.STRATA_GRUPPE
										.toString()));
				patienten.add(pat);
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.SUCHEN_ERR);
		} catch (PatientException e) {
			throw new DatenbankExceptions(DatenbankExceptions.UNGUELTIGE_DATEN);
		} finally {
			try {
				ConnectionFactory.getInstanz().closeConnection(con);
			} catch (DatenbankExceptions e) {
				e.printStackTrace();
				throw new DatenbankExceptions(
						DatenbankExceptions.CONNECTION_ERR);
			}
		}
		return patienten;
	}

	/**
	 * Sucht alle Studienarme in der DB die den Kriterien im uebergebenen
	 * Suchbean entsprechen.
	 * 
	 * @param arm
	 *            Bean mit gesetztem Filter. Felder die ungleich der
	 *            Nullkonstanten sind werden in die Abfrage mit einbezogen
	 * @return Vector mit gefundenen Studienarmen
	 * @throws DatenbankExceptions
	 *             Falls Fehler auftreten
	 */
	private Vector<StudienarmBean> suchenStudienarm(StudienarmBean arm)
			throws DatenbankExceptions {
		Connection con;
		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StudienarmBean sarm = null;
		Vector<StudienarmBean> studienarme = new Vector<StudienarmBean>();
		int counter = 0;
		String sql = "SELECT * FROM " + Tabellen.STUDIENARM.toString();

		if (arm.getStudieId() != NullKonstanten.NULL_LONG) {
			sql += " WHERE " + FelderStudienarm.STUDIE.toString() + " = ?";
			counter++;
		}
		if (arm.getStatus() != null) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderStudienarm.STATUS.toString() + " = ?";
			counter++;
		}
		if (arm.getBeschreibung() != null) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderStudienarm.BESCHREIBUNG.toString() + " LIKE ?";
			counter++;
		}
		if (arm.getBezeichnung() != null) {
			sql += FelderStudienarm.BEZEICHNUNG.toString() + " LIKE ?";
			counter++;
		}

		try {
			pstmt = con.prepareStatement(sql);
			counter = 1;
			if (arm.getStudieId() != NullKonstanten.NULL_LONG) {
				pstmt.setLong(counter++, arm.getStudieId());
			}
			if (arm.getStatus() != null) {
				pstmt.setString(counter++, arm.getStatus().toString());
			}
			if (arm.getBeschreibung() != null) {
				pstmt.setString(counter++, "%" + arm.getBeschreibung() + "%");
			}
			if (arm.getBezeichnung() != null) {
				pstmt.setString(counter++, "%" + arm.getBezeichnung() + "%");
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				sarm = new StudienarmBean(rs.getLong(FelderStudienarm.ID
						.toString()), rs.getLong(FelderStudienarm.STUDIE
						.toString()), Status.parseStatus(rs
						.getString(FelderStudienarm.STATUS.toString())), rs
						.getString(FelderStudienarm.BEZEICHNUNG.toString()), rs
						.getString(FelderStudienarm.BESCHREIBUNG.toString()));
				studienarme.add(sarm);
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.SUCHEN_ERR);
		} catch (StudienarmException f) {
			throw new DatenbankExceptions(DatenbankExceptions.UNGUELTIGE_DATEN);
		} catch (StudieException g) {
			throw new DatenbankExceptions(DatenbankExceptions.UNGUELTIGE_DATEN);
		} finally {
			try {
				ConnectionFactory.getInstanz().closeConnection(con);
			} catch (DatenbankExceptions e) {
				e.printStackTrace();
				throw new DatenbankExceptions(
						DatenbankExceptions.CONNECTION_ERR);
			}
		}

		return studienarme;
	}

	private Vector<StudieBean> suchenStudie(StudieBean studie)
			throws DatenbankExceptions {
		Connection con;
		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StudieBean tmpStudie = null;
		GregorianCalendar startDatum = null;
		GregorianCalendar endDatum = null;
		Vector<StudieBean> studien = new Vector<StudieBean>();
		int counter = 0;
		String sql = "SELECT * FROM " + Tabellen.STUDIE.toString();
		if (studie.getBenutzerkontoId() != NullKonstanten.NULL_LONG) {
			sql += " WHERE " + FelderStudie.BENUTZER.toString() + " = ?";
			counter++;
		}
		if (studie.getName() != null) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderStudie.NAME.toString() + " LIKE ?";
			counter++;
		}
		if (studie.getBeschreibung() != null) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderStudie.BESCHREIBUNG.toString() + " LIKE ? ";
			counter++;
		}
		// falls Start- und Enddatum gesetzt sind wird der Bereich dazwischen
		// gesucht
		if (studie.getStartDatum() != null && studie.getEndDatum() != null) {
			if (studie.getStartDatum().after(studie.getEndDatum())) {
				throw new DatenbankExceptions(
						DatenbankExceptions.UNGUELTIGE_DATEN);
			}
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderStudie.STARTDATUM.toString() + " >= ? ";
			counter++;
			sql += " AND ";
			sql += FelderStudie.ENDDATUM.toString() + " <= ? ";
			counter++;
		} else {
			if (studie.getStartDatum() != null) {
				if (counter == 0) {
					sql += " WHERE ";
				} else {
					sql += " AND ";
				}
				sql += FelderStudie.STARTDATUM.toString() + " = ? ";
				counter++;
			}
			if (studie.getEndDatum() != null) {
				if (counter == 0) {
					sql += " WHERE ";
				} else {
					sql += " AND ";
				}
				sql += FelderStudie.ENDDATUM.toString() + " = ? ";
				counter++;
			}
		}
		if (studie.getStudienprotokollpfad() != null) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderStudie.PROTOKOLL.toString() + " LIKE ? ";
			counter++;
		}
		if (studie.getRandomisationsart() != null) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderStudie.RANDOMISATIONSART.toString() + " = ? ";
			counter++;
		}
		if (studie.getStatus() != null) {
			if (counter == 0) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += FelderStudie.STATUS.toString() + " = ? ";
			counter++;
		}
		try {
			pstmt = con.prepareStatement(sql);
			counter = 1;
			if (studie.getBenutzerkontoId() != NullKonstanten.NULL_LONG) {
				pstmt.setLong(counter++, studie.getBenutzerkontoId());
			}
			if (studie.getName() != null) {
				pstmt.setString(counter++, studie.getName());
			}
			if (studie.getBeschreibung() != null) {
				pstmt.setString(counter++, studie.getBeschreibung());
			}
			if (studie.getStartDatum() != null) {
				pstmt.setDate(counter++, new Date(studie.getStartDatum()
						.getTimeInMillis()));
			}
			if (studie.getEndDatum() != null) {
				pstmt.setDate(counter++, new Date(studie.getEndDatum()
						.getTimeInMillis()));
			}
			if (studie.getStudienprotokollpfad() != null) {
				pstmt.setString(counter++, studie.getStudienprotokollpfad());
			}
			if (studie.getRandomisationsart() != null) {
				pstmt.setString(counter++, studie.getRandomisationsart());
			}
			if (studie.getStatus() != null) {
				pstmt.setString(counter++, studie.getStatus().toString());
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				startDatum = new GregorianCalendar();
				startDatum.setTime(rs.getDate(FelderStudie.STARTDATUM
						.toString()));
				endDatum = new GregorianCalendar();
				endDatum.setTime(rs.getDate(FelderStudie.ENDDATUM.toString()));
				tmpStudie = new StudieBean(
						rs.getLong(FelderStudie.ID.toString()),
						rs.getString(FelderStudie.BESCHREIBUNG.toString()),
						rs.getString(FelderStudie.NAME.toString()),
						Algorithmen
								.parseAlgorithmen(rs
										.getString(FelderStudie.RANDOMISATIONSALGORITHMUS
												.toString())),
						rs.getLong(FelderStudie.BENUTZER.toString()),
						startDatum,
						endDatum,
						rs.getString(FelderStudie.PROTOKOLL.toString()),
						rs.getString(FelderStudie.RANDOMISATIONSART.toString()),
						Status.parseStatus(rs.getString(FelderStudie.STATUS
								.toString())));
				studien.add(tmpStudie);
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new DatenbankExceptions(e, sql,
					DatenbankExceptions.SUCHEN_ERR);
		} catch (StudieException e) {
			throw new DatenbankExceptions(DatenbankExceptions.UNGUELTIGE_DATEN);
		} catch (RandomisationsException e) {
			throw new DatenbankExceptions(DatenbankExceptions.UNGUELTIGE_DATEN);
		} finally {
			try {
				ConnectionFactory.getInstanz().closeConnection(con);
			} catch (DatenbankExceptions e) {
				e.printStackTrace();
				throw new DatenbankExceptions(
						DatenbankExceptions.CONNECTION_ERR);
			}
		}
		return studien;
	}

	/**
	 * Dokumentation siehe Schnittstellenbeschreibung
	 * 
	 * @see de.randi2.datenbank.DatenbankSchnittstelle#suchenObjektId(long,
	 *      de.randi2.datenbank.Filter)
	 */
	@SuppressWarnings("unchecked")
	public <T extends Filter> T suchenObjektId(long id, T nullObjekt)
			throws DatenbankExceptions {
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
	 * @throws DatenbankExceptions
	 *             Falls bei der Suche ein Fehler auftrat.
	 */
	private PersonBean suchenPersonId(long id) throws DatenbankExceptions {
		Connection con = null;
		PreparedStatement pstmt;
		ResultSet rs = null;
		PersonBean tmpPerson = null;
		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
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
					long stellvertreterId = rs
							.getLong(FelderPerson.STELLVERTRETER.toString());
					if (stellvertreterId == Types.NULL) {
						stellvertreterId = NullKonstanten.DUMMY_ID;
					}
					tmpPerson = new PersonBean(rs.getLong(FelderPerson.ID
							.toString()), stellvertreterId, rs
							.getString(FelderPerson.NACHNAME.toString()), rs
							.getString(FelderPerson.VORNAME.toString()), Titel
							.parseTitel(rs.getString(FelderPerson.TITEL
									.toString())), tmp[0], rs
							.getString(FelderPerson.EMAIL.toString()), rs
							.getString(FelderPerson.TELEFONNUMMER.toString()),
							rs.getString(FelderPerson.HANDYNUMMER.toString()),
							rs.getString(FelderPerson.FAX.toString()));

				} catch (PersonException e) {
					e.printStackTrace();
					throw new DatenbankExceptions(
							DatenbankExceptions.UNGUELTIGE_DATEN);
					// sollte hier lieber die Person Exception weitergeleitet
					// werden? wie sieht es mit logging aus?
				}
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.SUCHEN_ERR);
		}
		try {
			ConnectionFactory.getInstanz().closeConnection(con);
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
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
	 * @throws DatenbankExceptions
	 *             falls bei der Suche ein Fehler auftrat.
	 */
	private ZentrumBean suchenZentrumId(long id) throws DatenbankExceptions {
		Connection con = null;
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ZentrumBean zentrum = null;

		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
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
			throw new DatenbankExceptions(DatenbankExceptions.SUCHEN_ERR);
		} catch (BenutzerException e) {
			throw new DatenbankExceptions(DatenbankExceptions.UNGUELTIGE_DATEN);
		}

		try {
			ConnectionFactory.getInstanz().closeConnection(con);
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
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
	 * @throws DatenbankExceptions
	 *             falls bei der Suche ein Fehler auftrat.
	 */
	private BenutzerkontoBean suchenBenutzerkontoId(long id)
			throws DatenbankExceptions {
		Connection con = null;
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BenutzerkontoBean benutzerkonto = null;
		GregorianCalendar ersterLogin = new GregorianCalendar();
		GregorianCalendar letzterLogin = new GregorianCalendar();

		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}

		sql = "SELECT * FROM " + Tabellen.BENUTZERKONTO + " WHERE "
				+ FelderBenutzerkonto.ID + " =?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getDate(FelderBenutzerkonto.ERSTERLOGIN.toString()) != null) {
					ersterLogin
							.setTime(rs.getDate(FelderBenutzerkonto.ERSTERLOGIN
									.toString()));
				} else {
					ersterLogin = null;
				}
				if (rs.getDate(FelderBenutzerkonto.ERSTERLOGIN.toString()) != null) {
					letzterLogin
							.setTime(rs.getDate(FelderBenutzerkonto.ERSTERLOGIN
									.toString()));
				} else {
					letzterLogin = null;
				}
				try {
					benutzerkonto = new BenutzerkontoBean(rs
							.getLong(FelderBenutzerkonto.ID.toString()),
							rs.getString(FelderBenutzerkonto.LOGINNAME
									.toString()), rs
									.getString(FelderBenutzerkonto.PASSWORT
											.toString()), rs
									.getLong(FelderBenutzerkonto.ZENTRUMID
											.toString()), Rolle.getRolle(rs
									.getString(FelderBenutzerkonto.ROLLEACCOUNT
											.toString())), rs
									.getLong(FelderBenutzerkonto.PERSONID
											.toString()), rs
									.getBoolean(FelderBenutzerkonto.GESPERRT
											.toString()), ersterLogin,
							letzterLogin);
				} catch (BenutzerkontoException e) {
					e.printStackTrace();
					throw new DatenbankExceptions(
							DatenbankExceptions.UNGUELTIGE_DATEN);
				} catch (RechtException e) {
					e.printStackTrace();
					throw new DatenbankExceptions(
							DatenbankExceptions.UNGUELTIGE_DATEN);
				}

				rs.close();
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.SUCHEN_ERR);
		}

		try {
			ConnectionFactory.getInstanz().closeConnection(con);
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
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
	 * @throws DatenbankExceptions
	 *             falls bei der Suche ein Fehler auftrat.
	 */
	private AktivierungBean suchenAktivierungId(long id)
			throws DatenbankExceptions {
		Connection con = null;
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GregorianCalendar cal = new GregorianCalendar();
		AktivierungBean aktivierung = null;

		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}

		sql = "SELECT * FROM " + Tabellen.AKTIVIERUNG + " WHERE "
				+ FelderAktivierung.Id + " =?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cal.setTime(rs.getTimestamp(FelderAktivierung.VERSANDDATUM
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
			throw new DatenbankExceptions(DatenbankExceptions.SUCHEN_ERR);
		} catch (BenutzerException e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.UNGUELTIGE_DATEN);
		}

		try {
			ConnectionFactory.getInstanz().closeConnection(con);
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		return aktivierung;
	}

	/**
	 * Diese Methode sucht in der Datenbank nach der zur Id gehoerigen Studie
	 * und gibt sie als Bean zurueck.
	 * 
	 * @param id
	 *            zu suchende Id.
	 * @return StudienBean, das zur uebergebenen Id gehoert. Null, falls keine
	 *         Studie mit entsprechender Id gefunden wurde.
	 * @throws DatenbankExceptions
	 *             wenn bei der Suche ein Fehler auftrat.
	 */
	private StudieBean suchenStudieId(long id) throws DatenbankExceptions {

		Connection con = null;
		PreparedStatement pstmt;
		ResultSet rs = null;
		StudieBean tmpStudie = null;
		GregorianCalendar startDatum = new GregorianCalendar();
		GregorianCalendar endDatum = new GregorianCalendar();

		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
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
					startDatum.setTime(rs.getDate(FelderStudie.STARTDATUM
							.toString()));
					endDatum.setTime(rs.getDate(FelderStudie.ENDDATUM
							.toString()));

					tmpStudie = new StudieBean(
							rs.getLong(FelderStudie.ID.toString()),
							rs.getString(FelderStudie.BESCHREIBUNG.toString()),
							rs.getString(FelderStudie.NAME.toString()),
							Algorithmen
									.parseAlgorithmen(rs
											.getString(FelderStudie.RANDOMISATIONSALGORITHMUS
													.toString())), rs
									.getLong(FelderStudie.BENUTZER.toString()),
							startDatum, endDatum, rs
									.getString(FelderStudie.PROTOKOLL
											.toString()), rs
									.getString(FelderStudie.RANDOMISATIONSART
											.toString()), Status.parseStatus(rs
									.getString(FelderStudie.STATUS.toString())));

				} catch (BenutzerException e) {
					e.printStackTrace();
					throw new DatenbankExceptions(
							DatenbankExceptions.UNGUELTIGE_DATEN);
				} catch (RandomisationsException e) {
					throw new DatenbankExceptions(
							DatenbankExceptions.UNGUELTIGE_DATEN);

				}
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.SUCHEN_ERR);
		}
		try {
			ConnectionFactory.getInstanz().closeConnection(con);
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		return tmpStudie;
	}

	/**
	 * Diese Methode sucht in der Datenbank nach dem zur Id gehoerigen
	 * Studienarm und gibt ihn als Bean zurueck.
	 * 
	 * @param id
	 *            zu suchende Id.
	 * @return StudienarmBean, das zur uebergebenen Id gehoert. Null, falls kein
	 *         Studienarm mit entsprechender Id gefunden wurde.
	 * @throws DatenbankExceptions
	 *             wenn bei der Suche ein Fehler auftrat.
	 */
	private StudienarmBean suchenStudienarmId(long id)
			throws DatenbankExceptions {

		Connection con = null;
		PreparedStatement pstmt;
		ResultSet rs = null;
		StudienarmBean tmpStudienarm = null;

		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		String sql;
		sql = "SELECT * FROM " + Tabellen.PATIENT + " WHERE "
				+ FelderStudienarm.ID + " = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				try {
					tmpStudienarm = new StudienarmBean(rs
							.getLong(FelderStudienarm.ID.toString()), rs
							.getLong(FelderStudienarm.STUDIE.toString()),
							Status.parseStatus(rs
									.getString(FelderStudienarm.STATUS
											.toString())), rs
									.getString(FelderStudienarm.BEZEICHNUNG
											.toString()), rs
									.getString(FelderStudienarm.BESCHREIBUNG
											.toString()));
				} catch (BenutzerException e) {
					e.printStackTrace();
					throw new DatenbankExceptions(
							DatenbankExceptions.UNGUELTIGE_DATEN);
				}
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.SUCHEN_ERR);
		}
		try {
			ConnectionFactory.getInstanz().closeConnection(con);
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		return tmpStudienarm;

	}

	/**
	 * Diese Methode sucht in der Datenbank nach dem zur Id gehoerigen Patient
	 * und gibt ihn als Bean zurueck.
	 * 
	 * @param id
	 *            zu suchende Id.
	 * @return PatientBean, das zur uebergebenen Id gehoert. Null, falls kein
	 *         Patient mit entsprechender Id gefunden wurde.
	 * @throws DatenbankExceptions
	 *             wenn bei der Suche ein Fehler auftrat.
	 */
	private PatientBean suchenPatientId(long id) throws DatenbankExceptions {

		Connection con = null;
		PreparedStatement pstmt;
		ResultSet rs = null;
		PatientBean tmpPatient = null;
		GregorianCalendar geburtsdatum = new GregorianCalendar();
		GregorianCalendar aufklaerungsdatum = new GregorianCalendar();

		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		String sql;
		sql = "SELECT * FROM " + Tabellen.PATIENT + " WHERE "
				+ FelderPatient.ID + " = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				char[] geschlecht = rs.getString(
						FelderPatient.GESCHLECHT.toString()).toCharArray();
				try {
					geburtsdatum.setTime(rs.getDate(FelderPatient.GEBURTSDATUM
							.toString()));
					aufklaerungsdatum
							.setTime(rs.getDate(FelderPatient.AUFKLAERUNGSDATUM
									.toString()));

					tmpPatient = new PatientBean(rs.getLong(FelderPatient.ID
							.toString()), rs.getString(FelderPatient.INITIALEN
							.toString()), geschlecht[0], geburtsdatum,
							rs.getInt(FelderPatient.PERFORMANCESTATUS
									.toString()), aufklaerungsdatum, rs
									.getInt(FelderPatient.KOERPEROBERFLAECHE
											.toString()),
							rs.getInt(FelderPatient.STUDIENARM.toString()),
							rs.getLong(FelderPatient.BENUTZER.toString()), rs
									.getString(FelderPatient.STRATA_GRUPPE
											.toString()));

				} catch (BenutzerException e) {
					e.printStackTrace();
					throw new DatenbankExceptions(
							DatenbankExceptions.UNGUELTIGE_DATEN);
				}
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.SUCHEN_ERR);
		}
		try {
			ConnectionFactory.getInstanz().closeConnection(con);
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		return tmpPatient;

	}

	/**
	 * Dokumentation siehe Schnittstellenbeschreibung
	 * 
	 * @see de.randi2.datenbank.DatenbankSchnittstelle#suchenMitgliederObjekte(de.randi2.datenbank.Filter,
	 *      de.randi2.datenbank.Filter)
	 */
	@SuppressWarnings("unchecked")
	public <T extends Filter, U extends Filter> Vector<T> suchenMitgliederObjekte(
			U vater, T kind) throws DatenbankExceptions {
		// 1:n V Zentrum: K Benutzerkonto
		if (vater instanceof ZentrumBean && kind instanceof BenutzerkontoBean) {
			BenutzerkontoBean bKonto = (BenutzerkontoBean) kind;
			try {
				bKonto.setZentrumId(((ZentrumBean) vater).getId());
			} catch (BenutzerkontoException e) {
				e.printStackTrace();
				throw new DatenbankExceptions(DatenbankExceptions.ID_FALSCH);
			}
			return (Vector<T>) suchenBenutzerkontoKindZ(bKonto);
		}
		// 1:n V Benutzerkonto : K Patient
		if (vater instanceof BenutzerkontoBean && kind instanceof PatientBean) {
			PatientBean patient = (PatientBean) kind;
			patient.setBenutzerkontoId(((BenutzerkontoBean) vater).getId());
			return (Vector<T>) suchenPatientKindB(patient);
		}
		// 1:n V Studie : K Studienarm
		if (vater instanceof StudieBean && kind instanceof StudienarmBean) {
			StudienarmBean studienarm = (StudienarmBean) kind;
			try {
				studienarm.setStudieId(((StudieBean) vater).getId());
			} catch (StudienarmException e) {
				e.printStackTrace();
				throw new DatenbankExceptions(DatenbankExceptions.ID_FALSCH);
			}
			return (Vector<T>) suchenStudienarmKind(studienarm);
		}
		// 1:n V Studienarm : K Patient
		if (kind instanceof PatientBean && vater instanceof StudienarmBean) {
			PatientBean patient = (PatientBean) kind;
			try {
				patient.setStudienarmId(((StudienarmBean) vater).getId());
			} catch (PatientException e) {
				e.printStackTrace();
				throw new DatenbankExceptions(DatenbankExceptions.ID_FALSCH);
			}
			return (Vector<T>) suchenPatientKindS(patient);
		}
		// 1:n V Studie : K Zentrum
		if (kind instanceof ZentrumBean && vater instanceof StudieBean) {
			ZentrumBean zentrum = (ZentrumBean) kind;
			return (Vector<T>) suchenZentrumKind(zentrum, ((StudieBean) vater)
					.getId());
		}
		// 1:n V Zentrum : K Studie
		if (kind instanceof StudieBean && vater instanceof ZentrumBean) {
			StudieBean studie = (StudieBean) kind;
			return (Vector<T>) suchenStudieKind(studie, ((ZentrumBean) vater)
					.getId());
		}
		return null;
	}

	/**
	 * Methode sucht die Benutzerkonten des zugehoerigen Zentrums.
	 * 
	 * @param konto
	 *            Das leere BenutzerkontoBean mit eventuellen zusätzlichen
	 *            Suchkriterien.
	 * @return Vector mit BenutzerkontoBeans.
	 * @throws DatenbankExceptions
	 *             Falls ein DB Fehler auftritt.
	 */
	private Vector<BenutzerkontoBean> suchenBenutzerkontoKindZ(
			BenutzerkontoBean konto) throws DatenbankExceptions {
		Vector<BenutzerkontoBean> kontoVector = suchenObjekt(konto);
		return kontoVector;
	}

	/**
	 * Methode sucht die Patienten des zugehoerigen Benutzerkontos.
	 * 
	 * @param patient
	 *            Das leere PatientBean mit eventuellen zusätzlichen
	 *            Suchkriterien.
	 * @return Vector mit PatientBeans.
	 * @throws DatenbankExceptions
	 *             Falls ein DB Fehler auftritt.
	 */
	private Vector<PatientBean> suchenPatientKindB(PatientBean patient)
			throws DatenbankExceptions {
		Vector<PatientBean> patientVector = suchenObjekt(patient);
		return patientVector;
	}

	/**
	 * Methode sucht die Studienarme der zugehoerigen Studie.
	 * 
	 * @param studienarm
	 *            Das leere StudienarmBean mit eventuellen zusätzlichen
	 *            Suchkriterien.
	 * @return Vector mit StudienarmBeans.
	 * @throws DatenbankExceptions
	 *             Falls ein DB Fehler auftritt.
	 */
	private Vector<StudienarmBean> suchenStudienarmKind(
			StudienarmBean studienarm) throws DatenbankExceptions {
		Vector<StudienarmBean> studienarmVector = suchenObjekt(studienarm);
		return studienarmVector;
	}

	/**
	 * Methode sucht die Patienten des zugehoerigen Studienarms.
	 * 
	 * @param patient
	 *            Das leere PatientBean mit eventuellen zusätzlichen
	 *            Suchkriterien.
	 * @return Vector mit PatientBeans.
	 * @throws DatenbankExceptions
	 *             Falls ein DB Fehler auftritt.
	 */
	private Vector<PatientBean> suchenPatientKindS(PatientBean patient)
			throws DatenbankExceptions {
		Vector<PatientBean> patientVector = suchenObjekt(patient);
		return patientVector;
	}

	/**
	 * Methode sucht die Zentren der zugehoerigen Studie.
	 * 
	 * @param zentrum
	 *            Das leere ZentrumBean mit eventuellen zusätzlichen
	 *            Suchkriterien.
	 * @param studieId
	 *            Die Id der Studie.
	 * @return Vector mit ZentrumBeans.
	 * @throws DatenbankExceptions
	 *             Falls ein DB Fehler auftritt.
	 */
	private Vector<ZentrumBean> suchenZentrumKind(ZentrumBean zentrum,
			long studieId) throws DatenbankExceptions {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ZentrumBean zentrumBean = new ZentrumBean();
		Vector<ZentrumBean> zVector = new Vector<ZentrumBean>();
		String sql = "";
		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		sql = "SELECT " + Tabellen.ZENTRUM + ".* FROM " + Tabellen.ZENTRUM
				+ "," + Tabellen.STUDIE_ZENTRUM + " WHERE "
				+ Tabellen.STUDIE_ZENTRUM + "."
				+ FelderStudieHasZentrum.STUDIENID + "= ? AND "
				+ Tabellen.STUDIE_ZENTRUM + "."
				+ FelderStudieHasZentrum.ZENTRUMID + "=" + Tabellen.ZENTRUM
				+ "." + FelderZentrum.ID;

		if (zentrum.getInstitution() != null) {
			sql += " AND " + FelderZentrum.INSTITUTION.toString() + " LIKE ? ";
		}
		if (zentrum.getAbteilung() != null) {
			sql += " AND " + FelderZentrum.ABTEILUNGSNAME.toString()
					+ " LIKE ? ";
		}
		if (zentrum.getOrt() != null) {
			sql += " AND " + FelderZentrum.ORT.toString() + " LIKE ? ";
		}
		if (zentrum.getPlz() != null) {
			sql += " AND " + FelderZentrum.PLZ.toString() + " LIKE ? ";
		}
		if (zentrum.getStrasse() != null) {
			sql += " AND " + FelderZentrum.STRASSE.toString() + " LIKE ? ";
		}
		if (zentrum.getHausnr() != null) {
			sql += " AND " + FelderZentrum.HAUSNUMMER.toString() + " LIKE ? ";
		}
		sql += " AND " + FelderZentrum.AKTIVIERT + " = ? ";

		try {
			pstmt = con.prepareStatement(sql);
			int index = 1;
			pstmt.setLong(index++, studieId);
			if (zentrum.getInstitution() != null) {
				pstmt.setString(index++, zentrum.getInstitution() + "%");
			}
			if (zentrum.getAbteilung() != null) {
				pstmt.setString(index++, zentrum.getAbteilung() + "%");
			}
			if (zentrum.getOrt() != null) {
				pstmt.setString(index++, zentrum.getOrt() + "%");
			}
			if (zentrum.getPlz() != null) {
				pstmt.setString(index++, zentrum.getPlz() + "%");
			}
			if (zentrum.getStrasse() != null) {
				pstmt.setString(index++, zentrum.getStrasse() + "%");
			}
			if (zentrum.getHausnr() != null) {
				pstmt.setString(index++, zentrum.getHausnr() + "%");
			}
			pstmt.setBoolean(index++, zentrum.getIstAktiviert());

			rs = pstmt.executeQuery();
			while (rs.next()) {
				try {
					zentrumBean = new ZentrumBean(rs.getLong(FelderZentrum.ID
							.toString()), rs
							.getString(FelderZentrum.INSTITUTION.toString()),
							rs.getString(FelderZentrum.ABTEILUNGSNAME
									.toString()), rs
									.getString(FelderZentrum.ORT.toString()),
							rs.getString(FelderZentrum.PLZ.toString()),
							rs.getString(FelderZentrum.STRASSE.toString()), rs
									.getString(FelderZentrum.HAUSNUMMER
											.toString()), rs
									.getLong(FelderZentrum.ANSPRECHPARTNERID
											.toString()), rs
									.getString(FelderZentrum.PASSWORT
											.toString()), rs
									.getBoolean(FelderZentrum.AKTIVIERT
											.toString()));
				} catch (ZentrumException e) {
					e.printStackTrace();
					throw new DatenbankExceptions(
							DatenbankExceptions.SUCHEN_ERR);
				}
				zVector.add(zentrumBean);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.SUCHEN_ERR);
		} finally {
			try {
				ConnectionFactory.getInstanz().closeConnection(con);
			} catch (DatenbankExceptions e) {
				e.printStackTrace();
				throw new DatenbankExceptions(
						DatenbankExceptions.CONNECTION_ERR);
			}
		}
		return zVector;
	}

	/**
	 * Methode sucht die Studien der zugehoerigen Zentrum.
	 * 
	 * @param studie
	 *            Das leere StudieBean mit eventuellen zusätzlichen
	 *            Suchkriterien.
	 * @param zentrumId
	 *            Die Id des Zentrums. Die Id der Studie.
	 * @return Vector mit StudieBeans.
	 * @throws DatenbankExceptions
	 *             Falls ein DB Fehler auftritt.
	 */
	private Vector<StudieBean> suchenStudieKind(StudieBean studie,
			long zentrumId) throws DatenbankExceptions {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StudieBean studieBean = new StudieBean();
		GregorianCalendar startDatum = new GregorianCalendar();
		GregorianCalendar endDatum = new GregorianCalendar();
		Vector<StudieBean> sVector = new Vector<StudieBean>();
		String sql = "";
		try {
			con = ConnectionFactory.getInstanz().getConnection();
		} catch (DatenbankExceptions e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.CONNECTION_ERR);
		}
		sql = "SELECT " + Tabellen.STUDIE + ".* FROM " + Tabellen.STUDIE + ","
				+ Tabellen.STUDIE_ZENTRUM + " WHERE " + Tabellen.STUDIE_ZENTRUM
				+ "." + FelderStudieHasZentrum.ZENTRUMID + "= ? AND "
				+ Tabellen.STUDIE_ZENTRUM + "."
				+ FelderStudieHasZentrum.STUDIENID + "=" + Tabellen.STUDIE
				+ "." + FelderStudie.ID;
		// Filterung (Copy Paste aus suchenStudie
		if (studie.getBenutzerkontoId() != NullKonstanten.NULL_LONG) {
			sql += " AND " + Tabellen.STUDIE + "."
					+ FelderStudie.BENUTZER.toString() + " = ?";
		}
		if (studie.getName() != null) {
			sql += " AND " + Tabellen.STUDIE + "."
					+ FelderStudie.NAME.toString() + " LIKE ?";
		}
		if (studie.getBeschreibung() != null) {
			sql += " AND " + Tabellen.STUDIE + "."
					+ FelderStudie.BESCHREIBUNG.toString() + " LIKE ? ";
		}
		// falls Start- und Enddatum gesetzt sind wird der Bereich dazwischen
		// gesucht
		if (studie.getStartDatum() != null && studie.getEndDatum() != null) {
			if (studie.getStartDatum().after(studie.getEndDatum())) {
				throw new DatenbankExceptions(
						DatenbankExceptions.UNGUELTIGE_DATEN);
			}
			sql += " AND " + Tabellen.STUDIE + "."
					+ FelderStudie.STARTDATUM.toString() + " >= ? ";
			sql += " AND ";
			sql += Tabellen.STUDIE + "." + FelderStudie.ENDDATUM.toString()
					+ " <= ? ";
		} else {
			if (studie.getStartDatum() != null) {
				sql += " AND " + Tabellen.STUDIE + "."
						+ FelderStudie.STARTDATUM.toString() + " = ? ";
			}
			if (studie.getEndDatum() != null) {
				sql += " AND " + Tabellen.STUDIE + "."
						+ FelderStudie.ENDDATUM.toString() + " = ? ";
			}
		}
		if (studie.getStudienprotokollpfad() != null) {
			sql += " AND " + Tabellen.STUDIE + "."
					+ FelderStudie.PROTOKOLL.toString() + " LIKE ? ";
		}
		if (studie.getRandomisationsart() != null) {
			sql += " AND " + Tabellen.STUDIE + "."
					+ FelderStudie.RANDOMISATIONSART.toString() + " = ? ";
		}
		if (studie.getStatus() != null) {
			sql += " AND " + Tabellen.STUDIE + "."
					+ FelderStudie.STATUS.toString() + " = ? ";
		}
		try {
			pstmt = con.prepareStatement(sql);
			int counter = 1;
			pstmt.setLong(counter++, zentrumId);
			if (studie.getBenutzerkontoId() != NullKonstanten.NULL_LONG) {
				pstmt.setLong(counter++, studie.getBenutzerkontoId());
			}
			if (studie.getName() != null) {
				pstmt.setString(counter++, studie.getName());
			}
			if (studie.getBeschreibung() != null) {
				pstmt.setString(counter++, studie.getBeschreibung());
			}
			if (studie.getStartDatum() != null) {
				pstmt.setDate(counter++, new Date(studie.getStartDatum()
						.getTimeInMillis()));
			}
			if (studie.getEndDatum() != null) {
				pstmt.setDate(counter++, new Date(studie.getEndDatum()
						.getTimeInMillis()));
			}
			if (studie.getStudienprotokollpfad() != null) {
				pstmt.setString(counter++, studie.getStudienprotokollpfad());
			}
			if (studie.getRandomisationsart() != null) {
				pstmt.setString(counter++, studie.getRandomisationsart());
			}
			if (studie.getStatus() != null) {
				pstmt.setString(counter++, studie.getStatus().toString());
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				try {
					startDatum.setTime(rs.getDate(FelderStudie.STARTDATUM
							.toString()));
					endDatum.setTime(rs.getDate(FelderStudie.ENDDATUM
							.toString()));

					studieBean = new StudieBean(
							rs.getLong(FelderStudie.ID.toString()),
							rs.getString(FelderStudie.BESCHREIBUNG.toString()),
							rs.getString(FelderStudie.NAME.toString()),
							Algorithmen
									.parseAlgorithmen(rs
											.getString(FelderStudie.RANDOMISATIONSALGORITHMUS
													.toString())), rs
									.getLong(FelderStudie.BENUTZER.toString()),
							startDatum, endDatum, rs
									.getString(FelderStudie.PROTOKOLL
											.toString()), rs
									.getString(FelderStudie.RANDOMISATIONSART
											.toString()), Status.parseStatus(rs
									.getString(FelderStudie.STATUS.toString())));
				} catch (StudieException e) {
					e.printStackTrace();
					throw new DatenbankExceptions(
							DatenbankExceptions.SUCHEN_ERR);
				} catch (RandomisationsException e) {
					throw new DatenbankExceptions(
							DatenbankExceptions.SUCHEN_ERR);
				}
				sVector.add(studieBean);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatenbankExceptions(DatenbankExceptions.SUCHEN_ERR);
		} finally {
			try {
				ConnectionFactory.getInstanz().closeConnection(con);
			} catch (DatenbankExceptions e) {
				e.printStackTrace();
				throw new DatenbankExceptions(
						DatenbankExceptions.CONNECTION_ERR);
			}
		}
		return sVector;
	}

	/**
	 * Dokumentation siehe Schnittstellenbeschreibung
	 * 
	 * @see de.randi2.datenbank.DatenbankSchnittstelle#suchenMitgliederObjekt(de.randi2.datenbank.Filter,
	 *      de.randi2.datenbank.Filter)
	 */
	@SuppressWarnings("unchecked")
	public <T extends Filter, U extends Filter> T suchenMitgliedEinsZuEins(
			U vater, T kind) throws DatenbankExceptions {
		kind.setFilter(true);
		// 1:1 V Person: K Zentrum
		if (vater instanceof PersonBean && kind instanceof ZentrumBean) {
			Vector<ZentrumBean> zVector = new Vector<ZentrumBean>();
			ZentrumBean zentrum = (ZentrumBean) kind;
			try {
				zentrum.setAnsprechpartnerId(((PersonBean) vater).getId());
			} catch (ZentrumException e) {
				e.printStackTrace();
				throw new DatenbankExceptions(DatenbankExceptions.SUCHEN_ERR);
			}
			zVector = suchenObjekt(zentrum);
			if (zVector.size() == 1) {
				zentrum = zVector.elementAt(0);
				return (T) zentrum;
			} else {
				throw new DatenbankExceptions(
						DatenbankExceptions.VECTOR_RELATION_FEHLER);
			}
		}
		// 1:1 V Person: K Benutzerkonto
		if (vater instanceof PersonBean && kind instanceof BenutzerkontoBean) {
			Vector<BenutzerkontoBean> kVector = new Vector<BenutzerkontoBean>();
			BenutzerkontoBean bKonto = (BenutzerkontoBean) kind;
			try {
				bKonto.setBenutzerId(((PersonBean) vater).getId());
			} catch (BenutzerkontoException e) {
				e.printStackTrace();
				throw new DatenbankExceptions(DatenbankExceptions.SUCHEN_ERR);
			}
			kVector = suchenObjekt(bKonto);
			if (kVector.size() == 1) {
				bKonto = kVector.elementAt(0);
				return (T) bKonto;
			} else {
				throw new DatenbankExceptions(
						DatenbankExceptions.VECTOR_RELATION_FEHLER);
			}
		}
		// 1:1 V Benutzerkonto: K Aktivierung
		if (vater instanceof BenutzerkontoBean
				&& kind instanceof AktivierungBean) {
			Vector<AktivierungBean> aVector = new Vector<AktivierungBean>();
			AktivierungBean aktivierung = (AktivierungBean) kind;
			try {
				aktivierung.setBenutzerkontoId(((BenutzerkontoBean) vater)
						.getId());
			} catch (AktivierungException e) {
				e.printStackTrace();
				throw new DatenbankExceptions(DatenbankExceptions.SUCHEN_ERR);
			}
			aVector = suchenObjekt(aktivierung);
			if (aVector.size() == 1) {
				aktivierung = aVector.elementAt(0);
				return (T) aktivierung;
			} else {
				throw new DatenbankExceptions(
						DatenbankExceptions.VECTOR_RELATION_FEHLER);
			}
		}
		// 1:1 V Benutzerkonto: K Studie
		if (vater instanceof BenutzerkontoBean && kind instanceof StudieBean) {
			Vector<StudieBean> sVector = new Vector<StudieBean>();
			StudieBean studie = (StudieBean) kind;
			try {
				studie.setBenutzerkontoId(((BenutzerkontoBean) vater).getId());
			} catch (StudieException e) {
				e.printStackTrace();
				throw new DatenbankExceptions(
						StudieException.BENUTZERKONTO_ID_FEHLERHAFT);
			}
			sVector = suchenObjekt(studie);
			if (sVector.size() == 1) {
				studie = sVector.elementAt(0);
				return (T) studie;
			} else {
				throw new DatenbankExceptions(
						DatenbankExceptions.VECTOR_RELATION_FEHLER);
			}
		}
		return null;
	}

	/**
	 * Loggt eine Datenaenderung
	 * 
	 * @param <T>
	 *            Filterobjekt.
	 * @param aObjekt
	 *            aktuelles Bean dessen Daten geloggt werden
	 * @param geaenderteDaten
	 *            Enthält die geaenderten Daten in einer HasMap.
	 * @param logart
	 *            1 falls Objekt neu angelegt wurde 2 falls Objekt geandert
	 *            wurde 3 falls Objekt geloescht wurde
	 * @throws DatenbankExceptions
	 *             Fehler beim Loggen.
	 */
	private <T extends Filter> void loggenDaten(T aObjekt, int logart)
			throws DatenbankExceptions {
		String text = null;
		HashMap<String, String> geaenderteDaten = new HashMap<String, String>();

		if (aObjekt instanceof PersonBean) {
			geaenderteDaten.put(FelderPerson.NACHNAME.toString(),
					((PersonBean) aObjekt).getNachname());
			geaenderteDaten.put(FelderPerson.VORNAME.toString(),
					((PersonBean) aObjekt).getVorname());
			geaenderteDaten.put(FelderPerson.GESCHLECHT.toString(), String
					.valueOf(((PersonBean) aObjekt).getGeschlecht()));
			geaenderteDaten.put(FelderPerson.TITEL.toString(),
					((PersonBean) aObjekt).getTitel().toString());
			geaenderteDaten.put(FelderPerson.EMAIL.toString(),
					((PersonBean) aObjekt).getEmail());
			geaenderteDaten.put(FelderPerson.FAX.toString(),
					((PersonBean) aObjekt).getFax());
			geaenderteDaten.put(FelderPerson.TELEFONNUMMER.toString(),
					((PersonBean) aObjekt).getTelefonnummer());
			geaenderteDaten.put(FelderPerson.HANDYNUMMER.toString(),
					((PersonBean) aObjekt).getHandynummer());
			if (((PersonBean) aObjekt).getStellvertreterId() != NullKonstanten.DUMMY_ID) {
				geaenderteDaten.put(FelderPerson.STELLVERTRETER.toString(),
						String.valueOf(((PersonBean) aObjekt)
								.getStellvertreterId()));
			}
		} else if (aObjekt instanceof ZentrumBean) {
			geaenderteDaten.put(FelderZentrum.INSTITUTION.toString(),
					((ZentrumBean) aObjekt).getInstitution());
			geaenderteDaten.put(FelderZentrum.ABTEILUNGSNAME.toString(),
					((ZentrumBean) aObjekt).getAbteilung());
			geaenderteDaten.put(FelderZentrum.ANSPRECHPARTNERID.toString(),
					String.valueOf(((ZentrumBean) aObjekt)
							.getAnsprechpartnerId()));
			geaenderteDaten.put(FelderZentrum.STRASSE.toString(),
					((ZentrumBean) aObjekt).getStrasse());
			geaenderteDaten.put(FelderZentrum.HAUSNUMMER.toString(), String
					.valueOf(((ZentrumBean) aObjekt).getHausnr()));
			geaenderteDaten.put(FelderZentrum.PLZ.toString(), String
					.valueOf(((ZentrumBean) aObjekt).getPlz()));
			geaenderteDaten.put(FelderZentrum.ORT.toString(),
					((ZentrumBean) aObjekt).getOrt());
			geaenderteDaten.put(FelderZentrum.PASSWORT.toString(),
					((ZentrumBean) aObjekt).getPasswort());
			geaenderteDaten.put(FelderZentrum.AKTIVIERT.toString(), String
					.valueOf(((ZentrumBean) aObjekt).getIstAktiviert()));

		} else if (aObjekt instanceof BenutzerkontoBean) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
					Locale.GERMANY);
			geaenderteDaten.put(FelderBenutzerkonto.LOGINNAME.toString(),
					((BenutzerkontoBean) aObjekt).getBenutzername());
			geaenderteDaten.put(FelderBenutzerkonto.PASSWORT.toString(),
					((BenutzerkontoBean) aObjekt).getPasswort());
			geaenderteDaten.put(FelderBenutzerkonto.ZENTRUMID.toString(),
					String
							.valueOf(((BenutzerkontoBean) aObjekt)
									.getZentrumId()));
			geaenderteDaten.put(FelderBenutzerkonto.ROLLEACCOUNT.toString(),
					((BenutzerkontoBean) aObjekt).getRolle().getName());
			if (((BenutzerkontoBean) aObjekt).getErsterLogin() != null) {
				geaenderteDaten.put(FelderBenutzerkonto.ERSTERLOGIN.toString(),
						sdf.format(((BenutzerkontoBean) aObjekt)
								.getErsterLogin().getTime()));
			}
			if (((BenutzerkontoBean) aObjekt).getLetzterLogin() != null) {
				geaenderteDaten.put(
						FelderBenutzerkonto.LETZTERLOGIN.toString(), sdf
								.format(((BenutzerkontoBean) aObjekt)
										.getLetzterLogin().getTime()));
			}
			geaenderteDaten.put(FelderBenutzerkonto.GESPERRT.toString(), String
					.valueOf(((BenutzerkontoBean) aObjekt).isGesperrt()));
			geaenderteDaten.put(FelderBenutzerkonto.PERSONID.toString(), String
					.valueOf(((BenutzerkontoBean) aObjekt).getBenutzerId()));

		} else if (aObjekt instanceof AktivierungBean) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
					Locale.GERMANY);
			geaenderteDaten.put(FelderAktivierung.BENUTZER.toString(), String
					.valueOf(((AktivierungBean) aObjekt).getBenutzerkontoId()));
			geaenderteDaten.put(FelderAktivierung.LINK.toString(),
					((AktivierungBean) aObjekt).getAktivierungsLink());
			geaenderteDaten.put(FelderAktivierung.VERSANDDATUM.toString(), sdf
					.format(((AktivierungBean) aObjekt).getVersanddatum()
							.getTime()));

		} else if (aObjekt instanceof StudieBean) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
					Locale.GERMANY);
			geaenderteDaten.put(FelderStudie.BENUTZER.toString(), String
					.valueOf(((StudieBean) aObjekt).getBenutzerkontoId()));
			geaenderteDaten.put(FelderStudie.NAME.toString(),
					((StudieBean) aObjekt).getName());
			geaenderteDaten.put(FelderStudie.BESCHREIBUNG.toString(),
					((StudieBean) aObjekt).getBeschreibung());
			geaenderteDaten.put(FelderStudie.STARTDATUM.toString(), sdf
					.format(((StudieBean) aObjekt).getStartDatum().getTime()));
			geaenderteDaten.put(FelderStudie.ENDDATUM.toString(), sdf
					.format(((StudieBean) aObjekt).getEndDatum().getTime()));
			geaenderteDaten.put(FelderStudie.RANDOMISATIONSART.toString(),
					((StudieBean) aObjekt).getRandomisationsart());
			geaenderteDaten.put(FelderStudie.STATUS.toString(),
					((StudieBean) aObjekt).getStatus().toString());

		} else if (aObjekt instanceof StudienarmBean) {
			geaenderteDaten.put(FelderStudienarm.STUDIE.toString(), String
					.valueOf(((StudienarmBean) aObjekt).getStudieId()));
			geaenderteDaten.put(FelderStudienarm.STATUS.toString(),
					((StudienarmBean) aObjekt).getStatus().toString());
			geaenderteDaten.put(FelderStudienarm.BEZEICHNUNG.toString(),
					((StudienarmBean) aObjekt).getBezeichnung());
			geaenderteDaten.put(FelderStudienarm.BESCHREIBUNG.toString(),
					((StudienarmBean) aObjekt).getBeschreibung());

		} else if (aObjekt instanceof PatientBean) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
					Locale.GERMANY);
			geaenderteDaten.put(FelderPatient.BENUTZER.toString(), String
					.valueOf(((PatientBean) aObjekt).getBenutzerkontoId()));
			geaenderteDaten.put(FelderPatient.STUDIENARM.toString(), String
					.valueOf(((PatientBean) aObjekt).getStudienarmId()));
			geaenderteDaten.put(FelderPatient.GEBURTSDATUM.toString(), sdf
					.format(((PatientBean) aObjekt).getGeburtsdatum()));
			geaenderteDaten.put(FelderPatient.GESCHLECHT.toString(), String
					.valueOf(((PatientBean) aObjekt).getGeschlecht()));
			geaenderteDaten.put(FelderPatient.AUFKLAERUNGSDATUM.toString(), sdf
					.format(((PatientBean) aObjekt).getDatumAufklaerung()));
			geaenderteDaten.put(FelderPatient.KOERPEROBERFLAECHE.toString(),
					String.valueOf(((PatientBean) aObjekt)
							.getKoerperoberflaeche()));
			geaenderteDaten.put(FelderPatient.PERFORMANCESTATUS.toString(),
					String.valueOf(((PatientBean) aObjekt)
							.getPerformanceStatus()));

		}
		// Benutzerkonto welches die Aktion ausgeloest hat
		BenutzerkontoBean ausfuehrendesBkBean = aObjekt
				.getBenutzerkontoLogging();
		if (ausfuehrendesBkBean == null) {
			throw new DatenbankExceptions(
					DatenbankExceptions.KEIN_BK_EINGETRAGEN);
		}

		switch (logart) {

		case 1:
			text = "neuer Datensatz";
			break;

		case 2:
			text = "geaenderter Datensatz";
			break;

		case 3:
			text = "geloeschter Datensatz";
			break;

		default:
			throw new DatenbankExceptions(DatenbankExceptions.LOG_FEHLER);
		}
		log.info(new LogAktion(text, ausfuehrendesBkBean,
				new LogGeanderteDaten(aObjekt.getId(), aObjekt.getClass()
						.getSimpleName(), geaenderteDaten)));
	}

}