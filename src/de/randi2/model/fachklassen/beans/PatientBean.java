package de.randi2.model.fachklassen.beans;

import java.util.GregorianCalendar;

import de.randi2.datenbank.Filter;
import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.fachklassen.Studienarm;
import de.randi2.utility.NullKonstanten;

public class PatientBean extends Filter{
	
	private int id = NullKonstanten.NULL_INT;
	
	private String aInitialen = null;
	
	private char aGeschlecht = NullKonstanten.NULL_CHAR;
	
	private GregorianCalendar aGeburtsdatum = null;
	
	private int aPerformanceStatus = NullKonstanten.NULL_INT;
	
	private GregorianCalendar aDatumAufklaerung = null;
	
	private int aKoerperoberflaeche = NullKonstanten.NULL_INT;
	
	private StudienarmBean aStudienarm = null;
	
	private int aStudienarmID = NullKonstanten.NULL_INT;
	
	public PatientBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param initialen
	 * @param geschlecht
	 * @param geburtsdatum
	 * @param performanceStatus
	 * @param datumAufklaerung
	 * @param koerperoberflaeche
	 * @param studienarm
	 */
	public PatientBean(int id, String initialen, char geschlecht, GregorianCalendar geburtsdatum, int performanceStatus, GregorianCalendar datumAufklaerung, int koerperoberflaeche, StudienarmBean studienarm) {
		super();
		
		this.setId(id);
		this.setInitialen(initialen);
		this.setGeschlecht(geschlecht);
		this.setGeburtsdatum(geburtsdatum);
		this.setPerformanceStatus(performanceStatus);
		this.setDatumAufklaerung(datumAufklaerung);
		this.setKoerperoberflaeche(koerperoberflaeche);
		this.setStudienarm(studienarm);
		this.setStudienarmID(studienarm.getId());
		
	}
	
	/**
	 * 
	 * @param initialen
	 * @param geschlecht
	 * @param geburtsdatum
	 * @param performanceStatus
	 * @param datumAufklaerung
	 * @param koerperoberflaeche
	 * @param studienarmID
	 */
	public PatientBean(int id, String initialen, char geschlecht, GregorianCalendar geburtsdatum, int performanceStatus, GregorianCalendar datumAufklaerung, int koerperoberflaeche, int studienarmID) {
		super();
		
		this.setId(id);
		this.setInitialen(initialen);
		this.setGeschlecht(geschlecht);
		this.setGeburtsdatum(geburtsdatum);
		this.setPerformanceStatus(performanceStatus);
		this.setDatumAufklaerung(datumAufklaerung);
		this.setKoerperoberflaeche(koerperoberflaeche);
		this.setStudienarmID(studienarmID);
		
	}	

	public GregorianCalendar getDatumAufklaerung() {
		return aDatumAufklaerung;
	}

	public void setDatumAufklaerung(GregorianCalendar datumAufklaerung) {
		aDatumAufklaerung = datumAufklaerung;
	}

	public GregorianCalendar getGeburtsdatum() {
		return aGeburtsdatum;
	}

	public void setGeburtsdatum(GregorianCalendar geburtsdatum) {
		aGeburtsdatum = geburtsdatum;
	}

	public char getGeschlecht() {
		return aGeschlecht;
	}

	public void setGeschlecht(char geschlecht) {
		aGeschlecht = geschlecht;
	}

	public String getInitialen() {
		return aInitialen;
	}

	public void setInitialen(String initialen) {
		aInitialen = initialen;
	}

	public int getKoerperoberflaeche() {
		return aKoerperoberflaeche;
	}

	public void setKoerperoberflaeche(int koerperoberflaeche) {
		aKoerperoberflaeche = koerperoberflaeche;
	}

	public int getPerformanceStatus() {
		return aPerformanceStatus;
	}

	public void setPerformanceStatus(int performanceStatus) {
		aPerformanceStatus = performanceStatus;
	}

	public StudienarmBean getStudienarm() throws DatenbankFehlerException {
		if (aStudienarm == null) {
			
			if (aStudienarmID==NullKonstanten.NULL_INT) {
				
				// FIXME Exception
				
			} else {
				
				aStudienarm = Studienarm.get(aStudienarmID);
				
			}
			
		}
		
		return aStudienarm;
	}

	public void setStudienarm(StudienarmBean studienarm) {
		aStudienarm = studienarm;
	}

	public int getStudienarmID() {
		return aStudienarmID;
	}

	public void setStudienarmID(int studienarmID) {
		aStudienarmID = studienarmID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
	

}
