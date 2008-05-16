package de.randi2.model;

import java.util.GregorianCalendar;

import javax.persistence.Entity;

import de.randi2.model.exceptions.PersonException;
import de.randi2.utility.NullKonstanten;

@SuppressWarnings("serial")
@Entity
public class User extends AbstractDomainObject {

	
	/**
	 * Die E-Mailadresse der Person.
	 */
	private String aEmail = null;

	/**
	 * Die Faxnummer der Person.
	 */
	private String aFax = null;

	/**
	 * Das Geschlecht der Person. 'm' feur maennlich oder 'w' fuer weiblich.
	 */
	private char aGender = NullKonstanten.NULL_CHAR;

	/**
	 * Die Handynummer der Person.
	 */
	private String aMobilenumber = null;

	/**
	 * Der Nachname der Person.
	 */
	private String aName = null;

	/**
	 * Die Telefonnummer der Person.
	 */
	private String aTelefonnumber = null;

	/**
	 * Der Titel der Person
	 */
	private Titel aTitel = null;

	/**
	 * Enumeration aller Titel
	 * 
	 */
	public static enum Titel {
		/**
		 * Die Person hat keinen Titel.
		 */
		KEIN_TITEL("kein Titel"),

		/**
		 * Die Person hat einen Doktortitel.
		 */
		DR("Dr."),

		/**
		 * Die Person hat eine Professur.
		 */
		PROF("Prof."),

		/**
		 * Die Person ist Professor Doktor.
		 */
		PROF_DR("Prof. Dr.");

		/**
		 * Der Titel als String.
		 */
		private String titel = null;

		/**
		 * Weist den String dem tatsaechlichen Titel zu.
		 * 
		 * @param titel
		 *            Der Parameter enthaelt den Titel-String.
		 */
		private Titel(String titel) {
			this.titel = titel;
		}

		/**
		 * Gibt den Titel als String zurueck.
		 * 
		 * @return den Titel
		 */
		@Override
		public String toString() {
			return this.titel;
		}

		/**
		 * Ueberfuehrt einen String in das entsprechende Titel-Element
		 * 
		 * @param titel
		 *            String Repraesentation eines Titels
		 * 
		 * @return Titelrepraesentation in Form des Enumelementes
		 * @throws PersonException
		 *             Msg == {@link PersonException#TITEL_UNGUELTIG} wenn
		 *             String keinen gueltiges Element repraesentiert
		 * 
		 */
		public static Titel parseTitel(String titel) throws PersonException {

			for (Titel aTitel : Titel.values()) {
				if (titel.equals(aTitel.toString())) {
					return aTitel;
				}

			}

			throw new PersonException(PersonException.TITEL_UNGUELTIG);
		}

	}

	/**
	 * Vorname der Person.
	 */
	private String aVorname = null;


	/**
	 * Benutzername des Kontoinhabers.
	 */
	private String aLoginname = null;

	/**
	 * Zeitpunkt des ersten Logins
	 */
	private GregorianCalendar aFirstLogin = null;

	/**
	 * Ein boolescher Wert, der dem Status gesperrt/entsperrt entspricht.
	 */
	private boolean gesperrt = false;

	/**
	 * Zeitpunkt des letzten Logins
	 */
	private GregorianCalendar aLastLogin = null;

	/**
	 * Passwort (md5 codiert)
	 */
	private String aPassword = null;

	public String getAEmail() {
		return aEmail;
	}

	public void setAEmail(String email) {
		aEmail = email;
	}

	public String getAFax() {
		return aFax;
	}

	public void setAFax(String fax) {
		aFax = fax;
	}

	public char getAGender() {
		return aGender;
	}

	public void setAGender(char gender) {
		aGender = gender;
	}

	public String getAMobilenumber() {
		return aMobilenumber;
	}

	public void setAMobilenumber(String mobilenumber) {
		aMobilenumber = mobilenumber;
	}

	public String getAName() {
		return aName;
	}

	public void setAName(String name) {
		aName = name;
	}

	public String getATelefonnumber() {
		return aTelefonnumber;
	}

	public void setATelefonnumber(String telefonnumber) {
		aTelefonnumber = telefonnumber;
	}

	public Titel getATitel() {
		return aTitel;
	}

	public void setATitel(Titel titel) {
		aTitel = titel;
	}

	public String getAVorname() {
		return aVorname;
	}

	public void setAVorname(String vorname) {
		aVorname = vorname;
	}


	public String getALoginname() {
		return aLoginname;
	}

	public void setALoginname(String loginname) {
		aLoginname = loginname;
	}

	public GregorianCalendar getAFirstLogin() {
		return aFirstLogin;
	}

	public void setAFirstLogin(GregorianCalendar firstLogin) {
		aFirstLogin = firstLogin;
	}

	public boolean isGesperrt() {
		return gesperrt;
	}

	public void setGesperrt(boolean gesperrt) {
		this.gesperrt = gesperrt;
	}

	public GregorianCalendar getALastLogin() {
		return aLastLogin;
	}

	public void setALastLogin(GregorianCalendar lastLogin) {
		aLastLogin = lastLogin;
	}

	public String getAPassword() {
		return aPassword;
	}

	public void setAPassword(String password) {
		aPassword = password;
	}

	
	
	
}
