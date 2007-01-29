package de.randi2.model.fachklassen.beans;

import java.util.GregorianCalendar;

import de.randi2.model.fachklassen.Rolle;

/**
 * Diese Klasse repraesentiert ein Benutzerkonto.
 * 
 * @author Daniel Haehn <dhaehn@stud.hs-heilbronn.de>
 * @author Lukasz Plotnicki <lplotni@stud.hs-heilbronn.de>
 * @author Thomas Willert <twillert@stud.hs-heilbronn.de>
 * @version 0.2
 */
public class BenutzerkontoBean {

	// TODO Anbindung an de.randi2.utility.NullAttribute noch zu realisieren

	/* Change Log 29.01.2007
	 * Thomas Willert
	 * 
	 * keine besonderen Bemerkungen
	 * 
	 */
	
	/**
	 * Benutzername
	 */
	private String benutzername;

	/**
	 * Passwort (md5)
	 */
	private String passwort;
    
    /**
     * Rolle des Benutzerkontos 
     */
    private Rolle rolle; 
    
	/**
	 * Zugehoeriges PersonBean zu diesem Benutzerkonto.
	 */
	private PersonBean benutzer;

	/**
	 * Zugehoeriger Ansprechparter.
	 */
	private PersonBean ansprechpartner;

	/**
	 * Ein boolescher Wert, der dem Status gesperrt/entsperrt entspricht.
	 */
	private boolean gesperrt;

	/**
	 * Zeitpunkt des ersten Logins
	 */
	private GregorianCalendar ersterLogin;

	/**
	 * Zeitpunkt des letzten Logins
	 */
	private GregorianCalendar letzterLogin;

	/**
	 * Der Standardkonstruktor
	 * 
	 */
	public BenutzerkontoBean() {

	}

	/**
	 * Der Konstruktor mit allen Attributen.
	 * 
	 * @param benutzername
	 *            der Benutzername des Benutzers
	 * @param passwort
	 *            das Passwort des Benutzers
     * @param rolle
     *             die Rolle des Benutzerkontos
	 * @param benutzer
	 *            das PersonBean zu diesem Benutzer
	 * @param ansprechpartner
	 *            das PersonBean das dem Ansprechpartner des Benutzers
	 *            entspricht
	 * @param gesperrt
	 *            ob der Benutzer gesperrt ist
	 * @param ersterLogin
	 *            Zeitpunkt des ersten Logins als GregorianCalendar
	 * @param letzterLogin
	 *            Zeitpunkt des letzten Logins als GregorianCalendar
	 */
	public BenutzerkontoBean(String benutzername, String passwort, Rolle rolle,
			PersonBean benutzer, PersonBean ansprechpartner, boolean gesperrt,
			GregorianCalendar ersterLogin, GregorianCalendar letzterLogin) {
		this.benutzername = benutzername;
		this.passwort = passwort;
		this.rolle= rolle;
        this.benutzer = benutzer;
		this.ansprechpartner = ansprechpartner;
		this.gesperrt = gesperrt;
		this.ersterLogin = ersterLogin;
		this.letzterLogin = letzterLogin;
	}

	/**
	 * Reduzierter Konstruktor, welcher die Attribute ersterLogin und
	 * letzterLogin automatisch setzt.
	 * 
	 * @param benutzername
	 *            der Benutzername des Benutzers
	 * @param passwort
	 *            das Passwort des Benutzers
	 * @param benutzer
	 *            das PersonBean zu diesem Benutzer
	 */
	public BenutzerkontoBean(String benutzername, String passwort,
			PersonBean benutzer) {
		super();
		this.benutzername = benutzername;
		this.passwort = passwort;
		this.benutzer = benutzer;
	}

	/**
	 * @return the benutzer
	 */
	public PersonBean getBenutzer() {
		return benutzer;
	}

	/**
	 * @param benutzer
	 *            the benutzer to set
	 */
	public void setBenutzer(PersonBean benutzer) {
		this.benutzer = benutzer;
	}

	/**
	 * @return the benutzername
	 */
	public String getBenutzername() {
		return benutzername;
	}

	/**
	 * @param benutzername
	 *            the benutzername to set
	 */
	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}

	/**
	 * @return the ersterLogin
	 */
	public GregorianCalendar getErsterLogin() {
		return ersterLogin;
	}

	/**
	 * @param ersterLogin
	 *            the ersterLogin to set
	 */
	public void setErsterLogin(GregorianCalendar ersterLogin) {
		this.ersterLogin = ersterLogin;
	}

	/**
	 * @return the gesperrt
	 */
	public boolean isGesperrt() {
		return gesperrt;
	}

	/**
	 * @param gesperrt
	 *            the gesperrt to set
	 */
	public void setGesperrt(boolean gesperrt) {
		this.gesperrt = gesperrt;
	}

	/**
	 * @return the letzterLogin
	 */
	public GregorianCalendar getLetzterLogin() {
		return letzterLogin;
	}

	/**
	 * @param letzterLogin
	 *            the letzterLogin to set
	 */
	public void setLetzterLogin(GregorianCalendar letzterLogin) {
		this.letzterLogin = letzterLogin;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		// TODO
		return null;

	}

	/**
	 * Diese Methode prueft ob die aktuelle Instanz dem uebergebenen Objekt
	 * entspricht.
	 * 
	 * @param zuvergleichendesObjekt
	 *            das zu vergleichende Objekt vom selben Typ
	 * @return TRUE oder FALSE, je nach Ergebnis des Vergleichs
	 */
	public boolean equals(BenutzerkontoBean zuvergleichendesObjekt) {

		// TODO
		return false;

	}

	/**
	 * @return the ansprechpartner
	 */
	public PersonBean getAnsprechpartner() {
		return ansprechpartner;
	}

	/**
	 * @param ansprechpartner the ansprechpartner to set
	 */
	public void setAnsprechpartner(PersonBean ansprechpartner) {
		this.ansprechpartner = ansprechpartner;
	}

    public void setRolle(Rolle rolle) {
           this.rolle= rolle;        
    }

    public Rolle getRolle(){
        return this.rolle;
    }
    
}
