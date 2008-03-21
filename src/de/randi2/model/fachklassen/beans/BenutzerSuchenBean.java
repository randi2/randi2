package de.randi2.model.fachklassen.beans;

import de.randi2.datenbank.DBObjekt;
import de.randi2.model.exceptions.BenutzerException;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.fachklassen.Rolle;
import de.randi2.utility.NullKonstanten;

public class BenutzerSuchenBean extends DBObjekt {

	

	/**
	 * Die ID des zugehoerigen Benutzers. Fremdschlueschel zum PersonBean. 1:1
	 * Beziehung
	 */
	private long benutzerId = NullKonstanten.NULL_LONG;
	
	/**
	 * 
	 */
	private long zentrumId=NullKonstanten.NULL_LONG;
	/**
	 * 
	 */
	private long personId=NullKonstanten.NULL_LONG;
	
	/**
	 * Rolle des Benutzerkontos
	 */
	private Rolle aRolle = null;
	private String vorname=null;
	private String nachname=null;
	private String email=null;
	private String loginname=null;
	private String institut=null;
	//Nicht zum Filtern, sondern nur zur Anzeige gedacht
	private boolean gesperrt=false;
	
	
	private int patientenAnzahl=0;
	private long studienID=NullKonstanten.NULL_LONG;
	
	
	/**
	 * Konstruktor für Admin und Sysop
	 * @param benutzerId
	 * @param zentrumId
	 * @param personId
	 * @param vorname
	 * @param nachname
	 * @param email
	 * @param loginname
	 * @param institut
	 * @param gesperrt
	 */
	public BenutzerSuchenBean(long benutzerId, long zentrumId, long personId, String vorname, String nachname, String email, String loginname, String institut, boolean gesperrt) {
		super();
		this.benutzerId = benutzerId;
		this.zentrumId = zentrumId;
		this.personId = personId;
		this.vorname = vorname;
		this.nachname = nachname;
		this.email = email;
		this.loginname = loginname;
		this.institut = institut;
		this.gesperrt=gesperrt;
	}
	/**
	 * Konstruktor für Studienarzt.
	 * @param benutzerId
	 * @param zentrumId
	 * @param personId
	 * @param rolle
	 * @param vorname
	 * @param nachname
	 * @param email
	 * @param loginname
	 * @param institut
	 * @param gesperrt
	 * @param patientenAnzahl
	 * @param studienID
	 */
	public BenutzerSuchenBean(long benutzerId, long zentrumId, long personId, Rolle rolle, String vorname, String nachname, String email, String loginname, String institut, boolean gesperrt, int patientenAnzahl, long studienID) {
		super();
		this.benutzerId = benutzerId;
		this.zentrumId = zentrumId;
		this.personId = personId;
		aRolle = rolle;
		this.vorname = vorname;
		this.nachname = nachname;
		this.email = email;
		this.loginname = loginname;
		this.institut = institut;
		this.gesperrt = gesperrt;
		this.patientenAnzahl = patientenAnzahl;
		this.studienID = studienID;
	}
	public BenutzerSuchenBean() {
	}
	public long getBenutzerId() {
		return benutzerId;
	}
	public void setBenutzerId(long benutzerId) {
		this.benutzerId = benutzerId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getInstitut() {
		return institut;
	}
	public void setInstitut(String institut) {
		this.institut = institut;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public long getZentrumId() {
		return zentrumId;
	}
	public void setZentrumId(long zentrumId) {
		this.zentrumId = zentrumId;
	}
	@Override
	public void validate() throws BenutzerException {
		if(benutzerId==NullKonstanten.NULL_LONG||zentrumId==NullKonstanten.NULL_LONG||personId==NullKonstanten.NULL_LONG||vorname==null||nachname==null||email==null||loginname==null||institut==null)
		{
			throw new PersonException("Daten ungueltig");
		}
	}
	public boolean isGesperrt() {
		return gesperrt;
	}
	public void setGesperrt(boolean gesperrt) {
		this.gesperrt = gesperrt;
	}
	public Rolle getARolle() {
		return aRolle;
	}
	public void setARolle(Rolle rolle) {
		aRolle = rolle;
	}
	public int getPatientenAnzahl() {
		return patientenAnzahl;
	}
	public void setPatientenAnzahl(int patientenAnzahl) {
		this.patientenAnzahl = patientenAnzahl;
	}
	public long getStudienID() {
		return studienID;
	}
	public void setStudienID(long studienID) {
		this.studienID = studienID;
	}

}
