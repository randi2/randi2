package de.randi2.model.fachklassen.beans;

import java.util.GregorianCalendar;

import de.randi2.datenbank.Filter;
import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.fachklassen.Benutzerkonto;
import de.randi2.model.fachklassen.Studienarm;
import de.randi2.utility.NullKonstanten;
/**
 * 
 * @author 
 *
 */
public class PatientBean extends Filter{
	/**
	 * 
	 */
	private long id = NullKonstanten.NULL_LONG;
	/**
	 * 
	 */
	private String aInitialen = null;
	/**
	 * 
	 */
	private char aGeschlecht = NullKonstanten.NULL_CHAR;
	/**
	 * 
	 */
	private GregorianCalendar aGeburtsdatum = null;
	/**
	 * 
	 */
	private int aPerformanceStatus = NullKonstanten.NULL_INT;
	/**
	 * 
	 */
	private GregorianCalendar aDatumAufklaerung = null;
	/**
	 * 
	 */
	private float aKoerperoberflaeche = NullKonstanten.NULL_FLOAT;
	/**
	 * 
	 */
	private StudienarmBean aStudienarm = null;
	/**
	 * 
	 */
	private long aStudienarmId = NullKonstanten.NULL_LONG;
	/**
	 * 
	 */
	private BenutzerkontoBean benutzerkonto = null;
	/**
	 * 
	 */
	private long benutzerkontoId = NullKonstanten.NULL_LONG;
	
	
	/**
	 * 
	 *
	 */
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
	public PatientBean(long id, String initialen, char geschlecht, GregorianCalendar geburtsdatum, 
			int performanceStatus, GregorianCalendar datumAufklaerung, 
			int koerperoberflaeche, StudienarmBean studienarm) {
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
	 * 
	 * @param initialen
	 * @param geschlecht
	 * @param geburtsdatum
	 * @param performanceStatus
	 * @param datumAufklaerung
	 * @param koerperoberflaeche
	 * @param studienarmID
	 */
	public PatientBean(long id, String initialen, char geschlecht, GregorianCalendar geburtsdatum, 
			int performanceStatus, GregorianCalendar datumAufklaerung, int koerperoberflaeche, 
			int studienarmID) {
		super();
		
		this.setId(id);
		this.setInitialen(initialen);
		this.setGeschlecht(geschlecht);
		this.setGeburtsdatum(geburtsdatum);
		this.setPerformanceStatus(performanceStatus);
		this.setDatumAufklaerung(datumAufklaerung);
		this.setKoerperoberflaeche(koerperoberflaeche);
		this.setStudienarmId(studienarmID);
		
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

	public float getKoerperoberflaeche() {
		return aKoerperoberflaeche;
	}

	public void setKoerperoberflaeche(float koerperoberflaeche) {
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
			
			if (aStudienarmId==NullKonstanten.NULL_INT) {
				
				// FIXME Exception
				
			} else {
				
				aStudienarm = Studienarm.get(aStudienarmId);
				
			}
			
		}
		
		return aStudienarm;
	}

	public void setStudienarm(StudienarmBean studienarm) {
		aStudienarm = studienarm;
	}

	public long getStudienarmId() {
		return aStudienarmId;
	}

	public void setStudienarmId(long studienarmId) {
		aStudienarmId = studienarmId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Liefert das Objekt des Benutzerkontos.
	 * @return das Benutzerkonto.
	 */
	public BenutzerkontoBean getBenutzerkonto() {
		if (benutzerkonto == null){
			benutzerkonto = Benutzerkonto.get(benutzerkontoId);
		}
		return benutzerkonto;
	}

	/**
	 * Setzt das Objekt des Benutzerkontos.
	 * @param benutzerkonto mit allen Eigenschaften werden gesetzt.
	 */
	public void setBenutzerkonto(BenutzerkontoBean benutzerkonto) {
		this.benutzerkonto = benutzerkonto;
	}

	/**
	 * 
	 * @return
	 */
	public long getBenutzerkontoId() {
		return benutzerkontoId;
	}

	/**
	 * 
	 * @param benutzerkontoId
	 */
	public void setBenutzerkontoId(long benutzerkontoId) {
		this.benutzerkontoId = benutzerkontoId;
	}
	
	
	
	

}
