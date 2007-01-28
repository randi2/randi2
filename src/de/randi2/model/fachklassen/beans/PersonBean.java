package de.randi2.model.fachklassen.beans;

/**
 * Diese Klasse repraesentiert eine Person.
 * 
 * @author Lukasz Plotnicki <lplotni@stud.hs-heilbronn.de>
 * @author Daniel Haehn <dhaehn@stud.hs-heilbronn.de>
 * @version 0.1
 */
public class PersonBean {
	// TODO Anbindung an de.randi2.utility.NullAttribute noch zu realisieren
	
	/**
	 * Ein einfaches Konstruktor.
	 */
	public PersonBean() {

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
			char geschlecht, String email, int telefonnummer, int handynummer,
			int fax) {
		super();
		this.nachname = nachname;
		this.vorname = vorname;
		this.titel = titel;
		this.geschlecht = geschlecht;
		this.email = email;
		this.telefonnummer = telefonnummer;
		this.handynummer = handynummer;
		this.fax = fax;
	}

	/**
	 * Nachname der Person.
	 */
	private String nachname;

	/**
	 * Vorname der Person.
	 */
	private String vorname;

	/**
	 * Titel der Person.
	 */
	private String titel;

	/**
	 * Geschlecht der Person. (Entspricht einer Konstane aus der
	 * de.randi2.utility.Konstanten Klasse)
	 */
	private char geschlecht;

	/**
	 * E-Mailadresse der Person.
	 */
	private String email;

	/**
	 * Die Telefonnummer der Person.
	 */
	private int telefonnummer;

	/**
	 * Die Handynummer der Person.
	 */
	private int handynummer;

	/**
	 * Die Faxnummer der Person.
	 */
	private int fax;

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
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the fax
	 */
	public int getFax() {
		return fax;
	}

	/**
	 * @param fax
	 *            the fax to set
	 */
	public void setFax(int fax) {
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
	public void setGeschlecht(char geschlecht) {
		this.geschlecht = geschlecht;
	}

	/**
	 * @return the handynummer
	 */
	public int getHandynummer() {
		return handynummer;
	}

	/**
	 * @param handynummer
	 *            the handynummer to set
	 */
	public void setHandynummer(int handynummer) {
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
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	/**
	 * @return the telefonnummer
	 */
	public int getTelefonnummer() {
		return telefonnummer;
	}

	/**
	 * @param telefonnummer
	 *            the telefonnummer to set
	 */
	public void setTelefonnummer(int telefonnummer) {
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
	public void setTitel(String titel) {
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
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

}
