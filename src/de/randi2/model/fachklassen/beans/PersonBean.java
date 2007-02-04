package de.randi2.model.fachklassen.beans;

import de.randi2.datenbank.Filter;
import de.randi2.utility.NullKonstanten;

/**
 * Diese Klasse repraesentiert eine Person.
 * 
 * @author Lukasz Plotnicki <lplotni@stud.hs-heilbronn.de>
 * @author Daniel Haehn <dhaehn@stud.hs-heilbronn.de>
 * @author Thomas Willert <twillert@stud.hs-heilbronn.de>
 * @version $Id$
 */
public class PersonBean extends Filter {

	/*
	 * Change Log 29.01.2007 Thomas Willert
	 * 
	 * Fehlermeldungen sind rein provisorisch. Die Konstanten fuer das
	 * Geschlecht sind manuell eingetragen, solange die Klasse Konstanten noch
	 * nicht existiert.
	 * 
	 */

	/**
	 * Ein einfacher Konstruktor.
	 */
	public PersonBean() throws IllegalArgumentException {

	}

	/**
	 * Ein Konstruktor, dem alle Eigenschaften uebergeben werden.
	 * 
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
	 */
	public PersonBean(String nachname, String vorname, String titel,
			char geschlecht, String email, String telefonnummer,
			String handynummer, String fax) throws IllegalArgumentException {
		super();
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
	 * Nachname der Person.
	 */
	private String nachname = null;

	/**
	 * Vorname der Person.
	 */
	private String vorname = null;

	/**
	 * Titel der Person.
	 */
	private String titel = null;

	/**
	 * Geschlecht der Person. (Entspricht einer Konstante aus der
	 * de.randi2.utility.Konstanten Klasse)
	 */
	private char geschlecht = NullKonstanten.NULL_CHAR;

	/**
	 * E-Mailadresse der Person.
	 */
	private String email = null;

	/**
	 * Die Telefonnummer der Person.
	 */
	private String telefonnummer = null;

	/**
	 * Die Handynummer der Person.
	 */
	private String handynummer = null;

	/**
	 * Die Faxnummer der Person.
	 */
	private String fax = null;

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) throws IllegalArgumentException {
		if (email == null) {
			throw new IllegalArgumentException(
					"Bitte geben Sie ihre Email-Adresse ein.");
		}
		email = email.trim();
		if (email.length() == 0) {
			throw new IllegalArgumentException(
					"Bitte geben Sie ihre Email-Adresse ein.");
		}
		if (email.length() > 255) {
			throw new IllegalArgumentException(
					"Maximal 255 Zeichen. Bitte geben Sie ihre Email-Adresse erneut ein.");
		}
		if (!email
				.matches("[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-]+(\\.)?)+\\.([a-zA-Z]){2,4}")) {
			throw new IllegalArgumentException(
					"Unerlaubte Zeichen! Bitte geben Sie ihre Email-Adresse erneut ein.");
		}
		this.email = email;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax
	 *            the fax to set
	 */
	public void setFax(String fax) throws IllegalArgumentException {
		fax = fax.trim();
		if (!fax.matches("(\\+\\d{2,3}|0)(\\d){2,10}[-/]?(\\d){3,15}")) {
			throw new IllegalArgumentException(
					"Unerlaubte Zeichen! Bitte geben Sie ihre Faxnummer erneut ein.");
		}
		this.fax = fax;
	}

	/**
	 * @return the geschlecht
	 */
	public char getGeschlecht() {
		return geschlecht;
	}

	/**
	 * @param geschlecht
	 *            the geschlecht to set
	 */
	public void setGeschlecht(char geschlecht) throws IllegalArgumentException {
		if (geschlecht == '\0') {
			throw new IllegalArgumentException(
					"Bitte geben Sie ihr Geschlecht ein.");
		}
		// if(!(geschlecht == Konstanten.MAENNLICH || geschlecht ==
		// Konstanten.WEIBLICH)) {
		if (!(geschlecht == 'm' || geschlecht == 'w')) {
			throw new IllegalArgumentException(
					"Bitte geben Sie ihr Geschlecht korrekt ein.");
		}
		this.geschlecht = geschlecht;
	}

	/**
	 * @return the handynummer
	 */
	public String getHandynummer() {
		return handynummer;
	}

	/**
	 * @param handynummer
	 *            the handynummer to set
	 */
	public void setHandynummer(String handynummer)
			throws IllegalArgumentException {
		handynummer = handynummer.trim();
		if (!handynummer.matches("(\\+\\d{2,3}|0)(\\d){3,10}[-/]?(\\d){3,15}")) {
			throw new IllegalArgumentException(
					"Unerlaubte Zeichen! Bitte geben Sie ihre Handynummer erneut ein.");
		}
		this.handynummer = handynummer;
	}

	/**
	 * @return the nachname
	 */
	public String getNachname() {
		return nachname;
	}

	/**
	 * @param nachname
	 *            the nachname to set
	 */
	public void setNachname(String nachname) throws IllegalArgumentException {
		if (nachname == null)
			throw new IllegalArgumentException(
					"Bitte geben Sie ihren Nachnamen ein.");
		nachname = nachname.trim();
		if (nachname.length() == 0) {
			throw new IllegalArgumentException(
					"Bitte geben Sie ihren Nachnamen ein.");
		}
		if (nachname.length() < 2 || nachname.length() > 50) {
			throw new IllegalArgumentException(
					"Nur 2-50 Zeichen. Bitte geben Sie ihren Nachnamen erneut ein.");
		}
		this.nachname = nachname;
	}

	/**
	 * @return the telefonnummer
	 */
	public String getTelefonnummer() {
		return telefonnummer;
	}

	/**
	 * @param telefonnummer
	 *            the telefonnummer to set
	 */
	public void setTelefonnummer(String telefonnummer)
			throws IllegalArgumentException {
		if (telefonnummer == null) {
			throw new IllegalArgumentException(
					"Bitte geben Sie ihre Telefonnummer ein.");
		}
		telefonnummer = telefonnummer.trim();
		if (telefonnummer.length() == 0) {
			throw new IllegalArgumentException(
					"Bitte geben Sie ihre Telefonnummer ein.");
		}
		if (!telefonnummer.matches("(\\+\\d{2,3}|0)(\\d){2,10}[-/]?(\\d){3,15}")) {
			throw new IllegalArgumentException(
					"Unerlaubte Zeichen! Bitte geben Sie ihre Telefonnummer erneut ein.");
		}
		this.telefonnummer = telefonnummer;
	}

	/**
	 * @return the titel
	 */
	public String getTitel() {
		return titel;
	}

	/**
	 * @param titel
	 *            the titel to set
	 */
	public void setTitel(String titel) throws IllegalArgumentException {
		if (titel!=null) {
			titel = titel.trim();
			if (!titel.matches("(Prof.|Dr.|Prof. Dr.|)")) {
				throw new IllegalArgumentException(
						"Unerlaubte Zeichen! Bitte geben Sie ihren Titel erneut ein.");
			}
		}
		this.titel = titel;
	}

	/**
	 * @return the vorname
	 */
	public String getVorname() {
		return vorname;
	}

	/**
	 * @param vorname
	 *            the vorname to set
	 */
	public void setVorname(String vorname) throws IllegalArgumentException {
		if (vorname == null)
			throw new IllegalArgumentException(
					"Bitte geben Sie ihren Vornamen ein.");
		vorname = vorname.trim();
		if (vorname.length() == 0) {
			throw new IllegalArgumentException(
					"Bitte geben Sie ihren Vornamen ein.");
		}
		if (vorname.length() < 2 || vorname.length() > 50) {
			throw new IllegalArgumentException(
					"Nur 2-50 Zeichen. Bitte geben Sie ihren Vornamen erneut ein.");
		}
		this.vorname = vorname;
	}
}
