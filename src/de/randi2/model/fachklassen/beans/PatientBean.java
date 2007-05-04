package de.randi2.model.fachklassen.beans;

import java.util.GregorianCalendar;

import de.randi2.datenbank.Filter;
import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.exceptions.BenutzerkontoException;
import de.randi2.model.exceptions.PatientException;
import de.randi2.model.fachklassen.Benutzerkonto;
import de.randi2.model.fachklassen.Studienarm;
import de.randi2.utility.NullKonstanten;

/**
 * Diese Klasse repräsentiert ein Patient.
 * @author Lukasz Plotnicki [lplotni@stud.hs-heilbronn.de]
 * @version $Id$
 */
public class PatientBean extends Filter{
	/**
	 * Die eindeutige ID des Patienten.
	 */
	private long id = NullKonstanten.NULL_LONG;
	
	/**
	 * Die Initialen des Patienten.
	 */
	private String aInitialen = null;
	
	/**
	 * Das Geschlecht des Patienten. 
	 */
	private char aGeschlecht = NullKonstanten.NULL_CHAR;
	
	/**
	 * Das Geburtstdatum des Patienten.
	 */
	private GregorianCalendar aGeburtsdatum = null;
	
	/**
	 * Der Performancestatus des Patienten.
	 */
	private int aPerformanceStatus = NullKonstanten.NULL_INT;
	
	/**
	 * Das Datum der Patientenaufklaerung.
	 */
	private GregorianCalendar aDatumAufklaerung = null;
	
	/**
	 * Die Koerperoberflaeche des Patienten.
	 */
	private float aKoerperoberflaeche = NullKonstanten.NULL_FLOAT;
	
	/**
	 * Dem Patient zugeordneter Studienarm. 
	 */
	private StudienarmBean aStudienarm = null;
	
	/**
	 * Die eindeutige ID des Studienarms.
	 */
	private long aStudienarmId = NullKonstanten.NULL_LONG;
	
	/**
	 * Das Benutzerkonto.
	 */
	private BenutzerkontoBean benutzerkonto = null;
	
	/**
	 * Die eindeutige ID des Benutzerkontos. 
	 */
	private long benutzerkontoId = NullKonstanten.NULL_LONG;
	
	
	/**
	 *	Standardkonstruktor von PatientBean mit Aufruf der Superklasse. 
	 */
	public PatientBean() {
		super();
	}
	
	/**
	 * Konstruktor von PatientBean.
	 * @param id Die eindeutige ID des Patienten.
	 * @param initialen Die Initialen des Patienten.
	 * @param geschlecht Das Geschlecht des Patienten. 
	 * @param geburtsdatum Das Geburtstdatum des Patienten.
	 * @param performanceStatus Der Performancestatus des Patienten.
	 * @param datumAufklaerung Das Datum der Patientenaufklaerung.
	 * @param koerperoberflaeche Die Koerperoberflaeche des Patienten.
	 * @param studienarm Dem Patient zugeordneter Studienarm. 
	 * @throws PatientException
	 */
	public PatientBean(long id, String initialen, char geschlecht, GregorianCalendar geburtsdatum, 
			int performanceStatus, GregorianCalendar datumAufklaerung, 
			int koerperoberflaeche, StudienarmBean studienarm) throws PatientException {
		super();
		
		this.setId(id);
		this.setInitialen(initialen);
		this.setGeschlecht(geschlecht);
		this.setGeburtsdatum(geburtsdatum);
		this.setPerformanceStatus(performanceStatus);
		this.setDatumAufklaerung(datumAufklaerung);
		this.setKoerperoberflaeche(koerperoberflaeche);
		this.setStudienarm(studienarm);
		this.setStudienarmId(studienarm.getId());
	}
	
	/**
	 * Konstruktor von PatientBean fuer die Datenbank.
	 * @param id Die eindeutige ID des Patienten.
	 * @param initialen Die Initialen des Patienten.
	 * @param geschlecht Das Geschlecht des Patienten. 
	 * @param geburtsdatum Das Geburtstdatum des Patienten.
	 * @param performanceStatus Der Performancestatus des Patienten.
	 * @param datumAufklaerung Das Datum der Patientenaufklaerung.
	 * @param koerperoberflaeche Die Koerperoberflaeche des Patienten.
	 * @param studienarmId Die eindeutige ID des Studienarms. 
	 * @param benutzerkontoId Die ID des Benutzerkontos.
	 * @throws PatientException
	 */
	public PatientBean(long id, String initialen, char geschlecht, GregorianCalendar geburtsdatum, 
			int performanceStatus, GregorianCalendar datumAufklaerung, int koerperoberflaeche, 
			int studienarmId, long benutzerkontoId) throws PatientException {
		super();
		
		this.setId(id);
		this.setInitialen(initialen);
		this.setGeschlecht(geschlecht);
		this.setGeburtsdatum(geburtsdatum);
		this.setPerformanceStatus(performanceStatus);
		this.setDatumAufklaerung(datumAufklaerung);
		this.setKoerperoberflaeche(koerperoberflaeche);
		this.setStudienarmId(studienarmId);
		this.setBenutzerkontoId(benutzerkontoId);
	}	

	/**
	 * Get-Methode für die Rückgabe des Aufklärungsdatums.
	 * @return Liefert das Aufklärungsdatum.
	 */
	public GregorianCalendar getDatumAufklaerung() {
		return aDatumAufklaerung;
	}

	/**
	 * Set-Methode für das Setzen des Aufklärungsdatums.
	 * @param datumAufklaerung Setzt das Aufklärungsdatum.
	 */
	public void setDatumAufklaerung(GregorianCalendar datumAufklaerung) {
		aDatumAufklaerung = datumAufklaerung;
	}

	/**
	 * Get-Methode für die Rückgabe des Geburtstdatum.
	 * @return Liefert das Geburtstdatum.
	 */
	public GregorianCalendar getGeburtsdatum() {
		return aGeburtsdatum;
	}

	/**
	 * Set-Methode für das Setzen des Geburtstdatum.
	 * @param geburtsdatum Setzt das Geburtsdatum.
	 */
	public void setGeburtsdatum(GregorianCalendar geburtsdatum) {
		aGeburtsdatum = geburtsdatum;
	}

	/**
	 * Get-Methode für die Rückgabe des Geschlechts.
	 * @return Liefert das Geschlecht.
	 */
	public char getGeschlecht() {
		return aGeschlecht;
	}

	/**
	 * Set-Methode für das Setzen des Geschlechts.
	 * @param geschlecht Setzt das Geschlechts.
	 */
	public void setGeschlecht(char geschlecht) {
		aGeschlecht = geschlecht;
	}

	/**
	 * Get-Methode für die Rückgabe der Initialen.
	 * @return Liefert die Initialen.
	 */

	public String getInitialen() {
		return aInitialen;
	}

	/**
	 * Set-Methode für das Setzen der Initialen.
	 * @param initialen Setzt die Initialen.
	 */
	public void setInitialen(String initialen) {
		aInitialen = initialen;
	}

	/**
	 * Get-Methode für die Rückgabe der Koerperoberflaeche.
	 * @return Liefert die Koerperoberflaeche.
	 */
	public float getKoerperoberflaeche() {
		return aKoerperoberflaeche;
	}

	/**
	 * Set-Methode für das Setzen der Koerperoberflaeche.
	 * @param koerperoberflaeche Setzt die Koerperoberflaeche.
	 */
	public void setKoerperoberflaeche(float koerperoberflaeche) {
		aKoerperoberflaeche = koerperoberflaeche;
	}

	/**
	 * Get-Methode für die Rückgabe des Performancestatus.
	 * @return Liefert den Performancestatus.
	 */
	public int getPerformanceStatus() {
		return aPerformanceStatus;
	}

	/**
	 * Set-Methode für das Setzen des Performancestatus.
	 * @param performanceStatus Setzt den Performancestatus.
	 */
	public void setPerformanceStatus(int performanceStatus) {
		aPerformanceStatus = performanceStatus;
	}

	/**
	 * Get-Methode für die Rückgabe eines StudienarmBeans.
	 * @return Liefert das StudienarmBean.
	 * @throws DatenbankFehlerException Wirft eine Exception, falls die Studienarm-ID <code>null</code> ist.
	 */
	public StudienarmBean getStudienarm() throws DatenbankFehlerException {
		if (aStudienarm == null) {
			
			if (aStudienarmId==NullKonstanten.NULL_INT) {
				throw new DatenbankFehlerException(DatenbankFehlerException.ARGUMENT_IST_NULL);
			} else {
				aStudienarm = Studienarm.get(aStudienarmId);	
			}	
		}
		return aStudienarm;
	}

	/**
	 * Set-Methode für das Setzen des StudienarmBean.
	 * @param studienarm Setzt das StudienarmBean.
	 */
	public void setStudienarm(StudienarmBean studienarm) {
		aStudienarm = studienarm;
	}

	/**
	 * Get-Methode für die Rückgabe der Studienarm-ID.
	 * @return Liefert die Studienarm-ID.
	 */
	public long getStudienarmId() {
		return aStudienarmId;
	}

	/**
	 * Set-Methode für das Setzen der Studienarm-ID.
	 * @param studienarmId Setzt die Studienarm-ID.
	 */
	public void setStudienarmId(long studienarmId) {
		aStudienarmId = studienarmId;
	}

	/**
	 * Get-Methode für die Rückgabe der Patienten-ID.
	 * @return Liefert die Petienten-ID.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Set-Methode für das Setzen der Patienten-ID.
	 * @param id Setzt die Patienten-ID.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Get-Methode für die Rückgabe des BenutzerkontoBean.
	 * @return Liefert das BenutzerkontoBean.
	 * @throws BenutzerkontoException
	 */
	public BenutzerkontoBean getBenutzerkonto() throws BenutzerkontoException {
		if (benutzerkonto == null){
			benutzerkonto = Benutzerkonto.get(benutzerkontoId);
		}
		return benutzerkonto;
	}

	/**
	 * Set-Methode für das Setzen des BenutzerkontoBean.
	 * @param benutzerkonto Setzt das BenutzerkontoBean.
	 */
	public void setBenutzerkonto(BenutzerkontoBean benutzerkonto) {
		this.benutzerkonto = benutzerkonto;
	}

	/**
	 * Get-Methode für die Rückgabe der Benutzerkonto-ID.
	 * @return Liefert die Benutzerkonto-ID.
	 */
	public long getBenutzerkontoId() {
		return benutzerkontoId;
	}

	/**
	 * Set-Methode für das Setzen der Benutzerkonto-ID.
	 * @param benutzerkontoId Setzt die Benutzerkonto-ID.
	 */
	public void setBenutzerkontoId(long benutzerkontoId) {
		this.benutzerkontoId = benutzerkontoId;
	}
	
	
	
	

}
