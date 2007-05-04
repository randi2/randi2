package de.randi2.model.fachklassen.beans;

import de.randi2.datenbank.Filter;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.fachklassen.Person;
import de.randi2.utility.NullKonstanten;

/**
 * Diese Klasse repraesentiert eine Person.
 * 
 * @author Thomas Willert [twillert@stud.hs-heilbronn.de]
 * @version $Id$
 */
public class PersonBean extends Filter {

	/**
	 * Die eindeutige Id der Person.
	 */
	private long id = NullKonstanten.NULL_LONG;

	/**
	 * Die E-Mailadresse der Person.
	 */
	private String email = null;

	/**
	 * Die Faxnummer der Person.
	 */
	private String fax = null;

	/**
	 * Das Geschlecht der Person. 'm' feur maennlich oder 'w' fuer weiblich.
	 */
	private char geschlecht = NullKonstanten.NULL_CHAR;

	/**
	 * Die Handynummer der Person.
	 */
	private String handynummer = null;

	/**
	 * Der Nachname der Person.
	 */
	private String nachname = null;

	/**
	 * Die Telefonnummer der Person.
	 */
	private String telefonnummer = null;

	/**
	 * Der Titel der Person
	 */
	private Titel titel = Titel.KEIN_TITEL;

	/**
	 * Enumeration aller Titel
	 * 
	 */
	public enum Titel {
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
	private String vorname = null;

	/**
	 * Stellvertreter der Person.
	 */
	private PersonBean stellvertreter = null;

	/**
	 * Eindeutige ID des Stellvertreters.
	 */
	private long stellvertreterID = NullKonstanten.NULL_LONG;

	/**
	 * Ein einfacher Konstruktor.
	 */
	public PersonBean() {

	}

	/**
	 * Ein Konstruktor, dem alle Eigenschaften uebergeben werden.
	 * 
	 * @param id
	 *            Die eindeutige Id des Objektes.
	 * @param nachname
	 *            Nachname der Person
	 * @param vorname
	 *            Vorname der Person
	 * @param titel
	 *            Titel der Person
	 * @param geschlecht
	 *            Geschlecht der Person (entspricht der passenden Konstante aus
	 *            der de.randi2.utility.Konstanten Klasse)
	 * @param email
	 *            E-Mailadresse der Person
	 * @param telefonnummer
	 *            Telefonnummer der Person.
	 * @param handynummer
	 *            Handynummer der Person.
	 * @param fax
	 *            Faxnummer der Person.
	 * @throws PersonException
	 *             Wenn bei der Validierung einer Person Probleme auftreten.
	 */
	public PersonBean(long id, String nachname, String vorname, Titel titel,
			char geschlecht, String email, String telefonnummer,
			String handynummer, String fax) throws PersonException {
		super();
		this.setId(id);
		this.setNachname(nachname);
		this.setVorname(vorname);
		this.setTitel(titel);
		this.setGeschlecht(geschlecht);
		this.setEmail(email);
		this.setTelefonnummer(telefonnummer);
		this.setHandynummer(handynummer);
		this.setFax(fax);
	}

	/**
	 * Gibt die eMail zurueck.
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Gibt die Faxnummer zurueck.
	 * 
	 * @return fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * Gibt das Geschlecht zurueck.
	 * 
	 * @return geschlecht
	 */
	public char getGeschlecht() {
		return geschlecht;
	}

	/**
	 * Gibt die Handynummer zurueck.
	 * 
	 * @return handynummer
	 */
	public String getHandynummer() {
		return handynummer;
	}

	/**
	 * Gibt den Nachnamen zurueck.
	 * 
	 * @return nachname
	 */
	public String getNachname() {
		return nachname;
	}

	/**
	 * Gibt die Telefonnummer zurueck.
	 * 
	 * @return telefonnummer
	 */
	public String getTelefonnummer() {
		return telefonnummer;
	}

	/**
	 * Gibt den Titel zurueck.
	 * 
	 * @return titel
	 */
	public Titel getTitel() {
		return titel;
	}

	/**
	 * Gibt den Vornamen zurueck.
	 * 
	 * @return vorname
	 */
	public String getVorname() {
		return vorname;
	}

	/**
	 * Setzt die eMail-Adresse.
	 * 
	 * @param email
	 *            Der Paramter enthaelt die eMail.
	 * @throws PersonException
	 *             Wenn bei der Validierung einer Person Probleme auftreten.
	 */
	public void setEmail(String email) throws PersonException {
		if (!this.isFilter()) {
			if (email == null) {
				throw new PersonException(PersonException.EMAIL_FEHLT);
			}
			email = email.trim();
			if (email.length() == 0) {
				throw new PersonException(PersonException.EMAIL_FEHLT);
			}
			if (email.length() > 255) {
				throw new PersonException(PersonException.EMAIL_UNGUELTIG);
			}
			if (!email
					.matches("[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-]+(\\.)?)+\\.([a-zA-Z]){2,4}")) {
				throw new PersonException(PersonException.EMAIL_UNGUELTIG);
			}
		}
		this.email = email;
	}

	/**
	 * Setzt die Faxnummer.
	 * 
	 * @param fax
	 *            Der Parameter enthaelt die Faxnummer.
	 * @throws PersonException
	 *             Wenn bei der Validierung einer Person Probleme auftreten.
	 */
	public void setFax(String fax) throws PersonException {
		if (fax != null) {
			fax = fax.trim();
			if (!fax.matches("(\\+\\d{2,3}|0)(\\d){2,10}[-/]?(\\d){3,15}")) {
				throw new PersonException(PersonException.FAX_UNGUELTIG);
			}
		}
		this.fax = fax;
	}

	/**
	 * Setzt das Geschlecht.
	 * 
	 * @param geschlecht
	 *            Der Parameter enthaelt das Geschlecht.
	 * @throws PersonException
	 *             Wenn bei der Validierung einer Person Probleme auftreten.
	 */
	public void setGeschlecht(char geschlecht) throws PersonException {
		if (!this.isFilter()) {
			if (geschlecht == NullKonstanten.NULL_CHAR) {
				throw new PersonException(PersonException.GESCHLECHT_FEHLT);
			}
			if (!(geschlecht == 'm' || geschlecht == 'w')) {
				throw new PersonException(PersonException.GESCHLECHT_UNGUELTIG);
			}
		}
		this.geschlecht = geschlecht;
	}

	/**
	 * Setzt die Handynummer.
	 * 
	 * @param handynummer
	 *            Der Parameter enthaelt die Handynummer.
	 * @throws PersonException
	 *             Wenn bei der Validierung einer Person Probleme auftreten.
	 */
	public void setHandynummer(String handynummer) throws PersonException {
		if (handynummer != null) {
			handynummer = handynummer.trim();
			if (!handynummer
					.matches("(\\+\\d{2,3}|0)(\\d){3,10}[-/]?(\\d){3,15}")) {
				throw new PersonException(PersonException.HANDY_UNGUELTIG);
			}
		}
		this.handynummer = handynummer;
	}

	/**
	 * Setzt den Nachnamen.
	 * 
	 * @param nachname
	 *            Der Parameter enthaelt den Nachnamen.
	 * @throws PersonException
	 *             Wenn bei der Validierung einer Person Probleme auftreten.
	 */
	public void setNachname(String nachname) throws PersonException {
		if (!this.isFilter()) {
			if (nachname == null) {
				throw new PersonException(PersonException.NACHNAME_FEHLT);
			}
			nachname = nachname.trim();
			if (nachname.length() == 0) {
				throw new PersonException(PersonException.NACHNAME_FEHLT);
			}
			if (nachname.length() < 2 || nachname.length() > 50) {
				throw new PersonException(PersonException.NACHNAME_UNGUELTIG);
			}
		}
		this.nachname = nachname;
	}

	/**
	 * Setzt die Telefonnummer.
	 * 
	 * @param telefonnummer
	 *            Der Parameter enthaelt die Telefonnummer.
	 * @throws PersonException
	 *             Wenn bei der Validierung einer Person Probleme auftreten.
	 */
	public void setTelefonnummer(String telefonnummer) throws PersonException {
		if (!this.isFilter()) {
			if (telefonnummer == null) {
				throw new PersonException(PersonException.TELEFONNUMMER_FEHLT);
			}
			telefonnummer = telefonnummer.trim();
			if (telefonnummer.length() == 0) {
				throw new PersonException(PersonException.TELEFONNUMMER_FEHLT);
			}
			if (!telefonnummer
					.matches("(\\+\\d{2,3}|0)(\\d){2,10}[-/]?(\\d){3,15}")) {
				throw new PersonException(
						PersonException.TELEFONNUMMER_UNGUELTIG);
			}
		}
		this.telefonnummer = telefonnummer;
	}

	/**
	 * Setzt den Titel.
	 * 
	 * @param titel
	 *            Der Parameter enthaelt den Titel.
	 * @throws PersonException
	 *             Wenn bei der Validierung einer Person Probleme auftreten.
	 */
	public void setTitel(Titel titel) throws PersonException {
		if (!this.isFilter()) {
			// Fehler wenn null übergeben wird, und keine Filterung gewünscht
			// ist (Wessen Kommentar ist dies? Gruss, twillert)
			if (titel == null) {
				throw new PersonException(PersonException.TITEL_UNGUELTIG);
			}
		}
		this.titel = titel;
	}

	/**
	 * Setzt den Vornamen.
	 * 
	 * @param vorname
	 *            Der Parameter enthaelt den Vornamen.
	 * @throws PersonException
	 *             Wenn bei der Validierung einer Person Probleme auftreten.
	 */
	public void setVorname(String vorname) throws PersonException {
		if (!this.isFilter()) {
			if (vorname == null) {
				throw new PersonException(PersonException.VORNAME_FEHLT);
			}
			vorname = vorname.trim();
			if (vorname.length() == 0) {
				throw new PersonException(PersonException.VORNAME_FEHLT);
			}
			if (vorname.length() < 2 || vorname.length() > 50) {
				throw new PersonException(PersonException.VORNAME_UNGUELTIG);
			}
		}
		this.vorname = vorname;
	}

	/**
	 * Die Get-Methode fuer die Id des Objektes.
	 * 
	 * @return id Die eindeutige Id (long) des Objektes, die auch das Objekt in
	 *         der Datenbank eindeutig identifiziert.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Die Set-Methode fuer die Id des Objektes.
	 * 
	 * @param id
	 *            Die eindeutige Id (long) des Objektes, die auch das Objekt in
	 *            der Datenbank eindeutig identifiziert.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Get-Methode fuer den Stellvertreter
	 * 
	 * @return Der Stellvertreter.
	 * @throws PersonException
	 *             Fehler aufgetreten.
	 */
	public PersonBean getStellvertreter() throws PersonException {
		if (stellvertreter == null) {
			stellvertreter = Person.get(stellvertreterID);
		}
		return stellvertreter;
	}

	/**
	 * Set-Methode fuer den Stellvertreter.
	 * 
	 * @param stellvertreter
	 *            Stellvertreter, der der aktuellen Person zugewiesen wird.
	 */
	public void setStellvertreter(PersonBean stellvertreter) {
		this.stellvertreter = stellvertreter;
	}

	/**
	 * Get-Methode fuer die ID des Stellvertreters
	 * 
	 * @return liefert die eindeutige ID des Stellvertreters
	 */
	public long getStellvertreterID() {
		return stellvertreterID;
	}

	/**
	 * Set-Methode fuer Stellvertreter
	 * 
	 * @param stellvertreterID
	 *            Die eindeutige Id (long) des Stellvertreters, die auch das
	 *            Objekt in der Datenbank eindeutig identifiziert.
	 */
	public void setStellvertreterID(long stellvertreterID) {
		this.stellvertreterID = stellvertreterID;
	}
}
