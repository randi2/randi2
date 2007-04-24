package de.randi2.model.fachklassen.beans;

import de.randi2.datenbank.Filter;
import de.randi2.datenbank.exceptions.DatenbankFehlerException;
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

	/*
	 * Change Log 29.01.2007 Thomas Willert
	 * 
	 * TODO: Fehlermeldungen sind rein provisorisch. Die Konstanten fuer das
	 * Geschlecht sind manuell eingetragen, solange die Klasse Konstanten noch
	 * nicht existiert.
	 */

	// TODO Bitte die Kommentare nochmal nachmachen!
	/**
	 * Die eindeutige Id der Person.
	 */
	private long id = NullKonstanten.NULL_LONG;

	/**
	 * E-Mailadresse der Person.
	 */
	private String email = null;

	/**
	 * Die Faxnummer der Person.
	 */
	private String fax = null;

	/**
	 * Geschlecht der Person. (Entspricht einer Konstante aus der
	 * de.randi2.utility.Konstanten Klasse)
	 */
	private char geschlecht = NullKonstanten.NULL_CHAR;

	/**
	 * Die Handynummer der Person.
	 */
	private String handynummer = null;

	/**
	 * Nachname der Person.
	 */
	private String nachname = null;

	/**
	 * Die Telefonnummer der Person.
	 */
	private String telefonnummer = null;

	/**
	 * Titel der Person
	 */
	private Titel titel = Titel.KEIN_TITEL;

	/**
	 * Enumeration aller Titel
	 * 
	 */
	public enum Titel {
		/**
		 * 
		 */
		KEIN_TITEL("kein Titel"),

		/**
		 * 
		 */
		DR("Dr."),

		/**
		 * 
		 */
		PROF("Prof."),

		/**
		 * 
		 */
		PROF_DR("Prof. Dr.");
		private String titel = null;

		/**
		 * @param titel
		 */
		private Titel(String titel) {
			this.titel = titel;
		}

		/**
		 * @return den Titel
		 */
		@Override
		public String toString() {
			return this.titel;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @return the geschlecht
	 */
	public char getGeschlecht() {
		return geschlecht;
	}

	/**
	 * @return the handynummer
	 */
	public String getHandynummer() {
		return handynummer;
	}

	/**
	 * @return the nachname
	 */
	public String getNachname() {
		return nachname;
	}

	/**
	 * @return the telefonnummer
	 */
	public String getTelefonnummer() {
		return telefonnummer;
	}

	/**
	 * @return the titel
	 */
	public Titel getTitel() {
		return titel;
	}

	/**
	 * @return the vorname
	 */
	public String getVorname() {
		return vorname;
	}

	/**
	 * @param email
	 *            the email to set *
	 * @throws PersonException
	 *             Wenn bei der Validierung einer Person Probleme auftreten.
	 */
	public void setEmail(String email) throws PersonException {
		boolean filter = super.isFilter();
		if (!filter && email == null) {
			throw new PersonException(PersonException.EMAIL_FEHLT);
		} else if (!filter) {
			email = email.trim();
			if (!filter && email.length() == 0) {
				throw new PersonException(PersonException.EMAIL_FEHLT);
			}
			if (!filter && email.length() > 255) {
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
	 * @param fax
	 *            the fax to set
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
	 * @param geschlecht
	 *            the geschlecht to set
	 * @throws PersonException
	 *             Wenn bei der Validierung einer Person Probleme auftreten.
	 */
	public void setGeschlecht(char geschlecht) throws PersonException {
		boolean filter = super.isFilter();
		if (!filter && geschlecht == NullKonstanten.NULL_CHAR) {
			throw new PersonException(PersonException.GESCHLECHT_FEHLT);
		}
		// if(!(geschlecht == Konstanten.MAENNLICH || geschlecht ==
		// Konstanten.WEIBLICH)) {
		if (!filter && !(geschlecht == 'm' || geschlecht == 'w')) {
			throw new PersonException(PersonException.GESCHLECHT_UNGUELTIG);
		}
		this.geschlecht = geschlecht;
	}

	/**
	 * @param handynummer
	 *            the handynummer to set
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
	 * @param nachname
	 *            the nachname to set
	 * @throws PersonException
	 *             Wenn bei der Validierung einer Person Probleme auftreten.
	 */
	public void setNachname(String nachname) throws PersonException {
		boolean filter = super.isFilter();

		if (!filter && nachname == null)
			throw new PersonException(PersonException.NACHNAME_FEHLT);
		if (!filter) {
			nachname = nachname.trim();
		}
		if (!filter && nachname.length() == 0) {
			throw new PersonException(PersonException.NACHNAME_FEHLT);
		}
		if (!filter && (nachname.length() < 2 || nachname.length() > 50)) {
			throw new PersonException(PersonException.NACHNAME_UNGUELTIG);
		}
		this.nachname = nachname;
	}

	/**
	 * @param telefonnummer
	 *            the telefonnummer to set
	 * @throws PersonException
	 *             Wenn bei der Validierung einer Person Probleme auftreten.
	 */
	public void setTelefonnummer(String telefonnummer) throws PersonException {
		boolean filter = super.isFilter();
		if (!filter && telefonnummer == null) {
			throw new PersonException(PersonException.TELEFONNUMMER_FEHLT);
		}
		if (!filter) {
			telefonnummer = telefonnummer.trim();
			if (!filter && telefonnummer.length() == 0) {
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
	 * @param titel
	 *            the titel to set
	 * @throws PersonException
	 *             Wenn bei der Validierung einer Person Probleme auftreten.
	 */
	public void setTitel(Titel titel) throws PersonException {
		boolean filter = super.isFilter();

		// Fehler wenn null übergeben wird, und keine Filterung gewünscht ist
		if (titel == null && !filter) {
			throw new PersonException(PersonException.TITEL_UNGUELTIG);
		}
		this.titel = titel;
	}

	/**
	 * @param vorname
	 *            the vorname to set
	 * @throws PersonException
	 *             Wenn bei der Validierung einer Person Probleme auftreten.
	 */
	public void setVorname(String vorname) throws PersonException {
		boolean filter = super.isFilter();
		if (!filter && vorname == null)
			throw new PersonException(PersonException.VORNAME_FEHLT);
		if (!filter) {
			vorname = vorname.trim();
		}
		if (!filter && vorname.length() == 0) {
			throw new PersonException(PersonException.VORNAME_FEHLT);
		}
		if (!filter && (vorname.length() < 2 || vorname.length() > 50)) {
			throw new PersonException(PersonException.VORNAME_UNGUELTIG);
		}
		this.vorname = vorname;
	}

	/**
	 * Die entsprechende Get-Methode für die Id des Objektes.
	 * 
	 * @return id Die eindeutige Id (long) des Objektes - die auch das Objekt in
	 *         der Datenbank eindeutig indetifiziert.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Die entsprechende Set-Methode für die Id des Objektes.
	 * 
	 * @param id
	 *            Die eindeutige Id (long) des Objektes - die auch das Objekt in
	 *            der Datenbank eindeutig indetifiziert.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Get-Methode fuer den Stellvertreter
	 * @return
	 * 			Der Stellvertreter.
	 * @throws DatenbankFehlerException
	 * 			Fehler in der Datenbank aufgetreten.
	 */
	public PersonBean getStellvertreter() throws DatenbankFehlerException {
		if (stellvertreter == null) {
			stellvertreter = Person.get(stellvertreterID);
		}
		return stellvertreter;
	}

	/**
	 * Set-Methode fuer Stellvertreter.
	 * @param stellvertreter
	 * 			Stellvertreter der der aktuellen Person zugewiesen wird.
	 */
	public void setStellvertreter(PersonBean stellvertreter) {
		this.stellvertreter = stellvertreter;
	}

	/**
	 * Get-Methode fuer ID des Stellvertreters
	 * @return
	 * 		liefert die eindeutige ID des Stellvertreters
	 */
	public long getStellvertreterID() {
		return stellvertreterID;
	}

	/**
	 * Set-Methode fuer Stellvertreter
	 * @param stellvertreterID
	 * 			Die eindeutige Id (long) des Stellvertreters - die auch das Objekt in
	 *            der Datenbank eindeutig indetifiziert.
	 */
	public void setStellvertreterID(long stellvertreterID) {
		this.stellvertreterID = stellvertreterID;
	}
}
