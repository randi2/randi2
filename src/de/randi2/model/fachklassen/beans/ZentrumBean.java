package de.randi2.model.fachklassen.beans;


/**
 * Diese Klasse repraesentiert ein Zentrum.
 * 
 * @author Lukasz Plotnicki <lplotni@stud.hs-heilbronn.de>
 * @version $Id$
 * 
 */
public class ZentrumBean {
	
	/*
	 * TODO Lukasz Plotnicki (02.02.2007):
	 * Die Validierung der Daten muss noch in die entsprechende set()
	 * Methoden eingebaut werden.
	 */
	
	/**
	 * Name der Institution.
	 */
	private String institution = null;

	/**
	 * Name der Abteilung in der Institution.
	 */
	private String abteilung = null;

	/**
	 * Ort
	 */
	private String ort = null;

	/**
	 * Postleitzahl
	 */
	private String plz = null;

	/**
	 * Strasse
	 */
	private String strasse = null;

	/**
	 * Hausnummer
	 */
	private String hausnr = null;

	/**
	 * Ein PersonBean mit der Daten des Ansprechpartners in dem Zentrum.
	 */
	private PersonBean ansprechpartner = null;

	/**
	 * Passwort für das Zentrum (md5)
	 */
	private String passwort = null;

	/**
	 * Der volle Konstruktor dieser Klasse, an den alle Attribute uebergeben
	 * werden.
	 * 
	 * @param institution
	 *            String, der der Institution entspricht.
	 * @param abteilung
	 *            String, der der Abteilung in der Institution entspricht.
	 * @param ort
	 *            String, der dem Ort entspricht.
	 * @param plz
	 *            String, der der PLZ entspricht.
	 * @param strasse
	 *            String, der der Strasse entspricht.
	 * @param hausnr
	 *            String, der der Hausnummer entspricht.
	 * @param ansprechpartner
	 *            PersonBean, das dem Ansprechpartner in dem Zentrum entspricht.
	 * @param passwort
	 *            String - Passwort (md5)
	 */
	public ZentrumBean(String institution, String abteilung, String ort,
			String plz, String strasse, String hausnr,
			PersonBean ansprechpartner, String passwort) {
		super();
		this.institution = institution;
		this.abteilung = abteilung;
		this.ort = ort;
		this.plz = plz;
		this.strasse = strasse;
		this.hausnr = hausnr;
		this.ansprechpartner = ansprechpartner;
		this.passwort = passwort;
	}

	/**
	 * Einfacher Konstruktor von dieser Klasse.
	 */
	public ZentrumBean() {

	}

	/**
	 * @return the abteilung
	 */
	public String getAbteilung() {
		return abteilung;
	}

	/**
	 * @param abteilung
	 *            the abteilung to set
	 */
	public void setAbteilung(String abteilung) {
		this.abteilung = abteilung;
	}

	/**
	 * @return the ansprechpartner
	 */
	public PersonBean getAnsprechpartner() {
		return ansprechpartner;
	}

	/**
	 * @param ansprechpartner
	 *            the ansprechpartner to set
	 */
	public void setAnsprechpartner(PersonBean ansprechpartner) {
		this.ansprechpartner = ansprechpartner;
	}

	/**
	 * @return the hausnr
	 */
	public String getHausnr() {
		return hausnr;
	}

	/**
	 * @param hausnr
	 *            the hausnr to set
	 */
	public void setHausnr(String hausnr) {
		this.hausnr = hausnr;
	}

	/**
	 * @return the institution
	 */
	public String getInstitution() {
		return institution;
	}

	/**
	 * @param institution
	 *            the institution to set
	 */
	public void setInstitution(String institution) {
		this.institution = institution;
	}

	/**
	 * @return the ort
	 */
	public String getOrt() {
		return ort;
	}

	/**
	 * @param ort
	 *            the ort to set
	 */
	public void setOrt(String ort) {
		this.ort = ort;
	}

	/**
	 * @return the passwort
	 */
	public String getPasswort() {
		return passwort;
	}

	/**
	 * @param passwort
	 *            the passwort to set
	 */
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	/**
	 * @return the plz
	 */
	public String getPlz() {
		return plz;
	}

	/**
	 * @param plz
	 *            the plz to set
	 */
	public void setPlz(String plz) {
		this.plz = plz;
	}

	/**
	 * @return the strasse
	 */
	public String getStrasse() {
		return strasse;
	}

	/**
	 * @param strasse
	 *            the strasse to set
	 */
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}
}
