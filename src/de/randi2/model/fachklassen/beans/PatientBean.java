package de.randi2.model.fachklassen.beans;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

import de.randi2.datenbank.Filter;
import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.exceptions.BenutzerkontoException;
import de.randi2.model.exceptions.PatientException;
import de.randi2.model.fachklassen.Benutzerkonto;
import de.randi2.model.fachklassen.Studienarm;
import de.randi2.utility.NullKonstanten;

/**
 * Diese Klasse repraesentiert ein Patient.
 * 
 * @author Lukasz Plotnicki [lplotni@stud.hs-heilbronn.de]
 * @version $Id$
 */
public class PatientBean extends Filter {
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
	private BenutzerkontoBean aBenutzerkonto = null;

	/**
	 * Die eindeutige ID des Benutzerkontos.
	 */
	private long aBenutzerkontoId = NullKonstanten.NULL_LONG;

	/*
	 * TODO In diesem Bean werden noch die Strate-Eigenschaften des Patienten
	 * gespeicher. Da aber die Vorgehensweise bzgl. der Stratas noch nicht
	 * geklaert wurde, koennte es noch nicht umgesetzt werden. (lplotni)
	 */

	/**
	 * Standardkonstruktor von PatientBean mit Aufruf der Superklasse.
	 */
	public PatientBean() {
	}

	/**
	 * Konstruktor von PatientBean.
	 * 
	 * @param id
	 *            Die eindeutige ID des Patienten.
	 * @param initialen
	 *            Die Initialen des Patienten.
	 * @param geschlecht
	 *            Das Geschlecht des Patienten.
	 * @param geburtsdatum
	 *            Das Geburtstdatum des Patienten.
	 * @param performanceStatus
	 *            Der Performancestatus des Patienten.
	 * @param datumAufklaerung
	 *            Das Datum der Patientenaufklaerung.
	 * @param koerperoberflaeche
	 *            Die Koerperoberflaeche des Patienten.
	 * @param studienarm
	 *            Dem Patient zugeordneter Studienarm.
	 * @throws PatientException -
	 *             wenn ungueltige Daten uebergeben wurden
	 */
	public PatientBean(long id, String initialen, char geschlecht,
			GregorianCalendar geburtsdatum, int performanceStatus,
			GregorianCalendar datumAufklaerung, int koerperoberflaeche,
			StudienarmBean studienarm) throws PatientException {

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
	 * 
	 * @param id
	 *            Die eindeutige ID des Patienten.
	 * @param initialen
	 *            Die Initialen des Patienten.
	 * @param geschlecht
	 *            Das Geschlecht des Patienten.
	 * @param geburtsdatum
	 *            Das Geburtstdatum des Patienten.
	 * @param performanceStatus
	 *            Der Performancestatus des Patienten.
	 * @param datumAufklaerung
	 *            Das Datum der Patientenaufklaerung.
	 * @param koerperoberflaeche
	 *            Die Koerperoberflaeche des Patienten.
	 * @param studienarmId
	 *            Die eindeutige ID des Studienarms.
	 * @param benutzerkontoId
	 *            Die ID des Benutzerkontos.
	 * @throws PatientException -
	 *             wenn die uebergebene Daten nicht valide waren
	 */
	public PatientBean(long id, String initialen, char geschlecht,
			GregorianCalendar geburtsdatum, int performanceStatus,
			GregorianCalendar datumAufklaerung, int koerperoberflaeche,
			int studienarmId, long benutzerkontoId) throws PatientException {

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
	 * 
	 * @return Liefert das Aufklärungsdatum.
	 */
	public GregorianCalendar getDatumAufklaerung() {
		return aDatumAufklaerung;
	}

	/**
	 * Set-Methode fuer das Setzen des Aufklärungsdatums.
	 * 
	 * @param datumAufklaerung
	 *            Setzt das Aufklärungsdatum.
	 */
	public void setDatumAufklaerung(GregorianCalendar datumAufklaerung) {
		aDatumAufklaerung = datumAufklaerung;
	}

	/**
	 * Get-Methode fuer die Rückgabe des Geburtstdatum.
	 * 
	 * @return Liefert das Geburtstdatum.
	 */
	public GregorianCalendar getGeburtsdatum() {
		return aGeburtsdatum;
	}

	/**
	 * Set-Methode fuer das Setzen des Geburtstdatum.
	 * 
	 * @param geburtsdatum
	 *            Setzt das Geburtsdatum.
	 */
	public void setGeburtsdatum(GregorianCalendar geburtsdatum) {
		aGeburtsdatum = geburtsdatum;
	}

	/**
	 * Get-Methode fuer die Rueckgabe des Geschlechts.
	 * 
	 * @return Liefert das Geschlecht.
	 */
	public char getGeschlecht() {
		return aGeschlecht;
	}

	/**
	 * Set-Methode fuer das Setzen des Geschlechts.
	 * 
	 * @param geschlecht
	 *            Setzt das Geschlechts.
	 */
	public void setGeschlecht(char geschlecht) {
		aGeschlecht = geschlecht;
	}

	/**
	 * Get-Methode fuer die Rueckgabe der Initialen.
	 * 
	 * @return Liefert die Initialen.
	 */

	public String getInitialen() {
		return aInitialen;
	}

	/**
	 * Set-Methode fuer das Setzen der Initialen.
	 * 
	 * @param initialen
	 *            Setzt die Initialen.
	 */
	public void setInitialen(String initialen) {
		aInitialen = initialen;
	}

	/**
	 * Get-Methode fuer die Rueckgabe der Koerperoberflaeche.
	 * 
	 * @return Liefert die Koerperoberflaeche.
	 */
	public float getKoerperoberflaeche() {
		return aKoerperoberflaeche;
	}

	/**
	 * Set-Methode fuer das Setzen der Koerperoberflaeche.
	 * 
	 * @param koerperoberflaeche
	 *            Setzt die Koerperoberflaeche.
	 */
	public void setKoerperoberflaeche(float koerperoberflaeche) {
		aKoerperoberflaeche = koerperoberflaeche;
	}

	/**
	 * Get-Methode fuer die Rueckgabe des Performancestatus.
	 * 
	 * @return Liefert den Performancestatus.
	 */
	public int getPerformanceStatus() {
		return aPerformanceStatus;
	}

	/**
	 * Set-Methode fuer das Setzen des Performancestatus.
	 * 
	 * @param performanceStatus
	 *            Setzt den Performancestatus.
	 */
	public void setPerformanceStatus(int performanceStatus) {
		aPerformanceStatus = performanceStatus;
	}

	/**
	 * Get-Methode fuer die Rueckgabe eines StudienarmBeans.
	 * 
	 * @return Liefert das StudienarmBean.
	 * @throws DatenbankFehlerException
	 *             Wirft eine Exception, falls die Studienarm-ID
	 *             <code>null</code> ist.
	 */
	public StudienarmBean getStudienarm() throws DatenbankFehlerException {
		if (aStudienarm == null) {

			if (aStudienarmId == NullKonstanten.NULL_INT) {
				throw new DatenbankFehlerException(
						DatenbankFehlerException.ARGUMENT_IST_NULL);
			} else {
				aStudienarm = Studienarm.get(aStudienarmId);
			}
		}
		return aStudienarm;
	}

	/**
	 * Set-Methode fuer das Setzen des StudienarmBean.
	 * 
	 * @param studienarm
	 *            Setzt das StudienarmBean.
	 */
	public void setStudienarm(StudienarmBean studienarm) {
		aStudienarm = studienarm;
	}

	/**
	 * Get-Methode fuer die Rueckgabe der Studienarm-ID.
	 * 
	 * @return Liefert die Studienarm-ID.
	 */
	public long getStudienarmId() {
		return aStudienarmId;
	}

	/**
	 * Set-Methode fuer das Setzen der Studienarm-ID.
	 * 
	 * @param studienarmId
	 *            Setzt die Studienarm-ID.
	 */
	public void setStudienarmId(long studienarmId) {
		aStudienarmId = studienarmId;
	}

	/**
	 * Get-Methode fuer die Rueckgabe der Patienten-ID.
	 * 
	 * @return Liefert die Petienten-ID.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Set-Methode fuer das Setzen der Patienten-ID.
	 * 
	 * @param id
	 *            Setzt die Patienten-ID.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Get-Methode fuer die Rueckgabe des BenutzerkontoBean.
	 * 
	 * @return Liefert das BenutzerkontoBean.
	 * @throws BenutzerkontoException -
	 *             wenn beim Holen des entsprechendes Bentutzerkontoobjektes
	 *             Probleme vorkamen.
	 */
	public BenutzerkontoBean getBenutzerkonto() throws BenutzerkontoException {
		if (aBenutzerkonto == null) {
			aBenutzerkonto = Benutzerkonto.get(aBenutzerkontoId);
		}
		return aBenutzerkonto;
	}

	/**
	 * Set-Methode fuer das Setzen des BenutzerkontoBean.
	 * 
	 * @param benutzerkonto
	 *            Setzt das BenutzerkontoBean.
	 */
	public void setBenutzerkonto(BenutzerkontoBean benutzerkonto) {
		this.aBenutzerkonto = benutzerkonto;
	}

	/**
	 * Get-Methode fuer die Rueckgabe der Benutzerkonto-ID.
	 * 
	 * @return Liefert die Benutzerkonto-ID.
	 */
	public long getBenutzerkontoId() {
		return aBenutzerkontoId;
	}

	/**
	 * Set-Methode fuer das Setzen der Benutzerkonto-ID.
	 * 
	 * @param benutzerkontoId
	 *            Setzt die Benutzerkonto-ID.
	 */
	public void setBenutzerkontoId(long benutzerkontoId) {
		this.aBenutzerkontoId = benutzerkontoId;
	}

	/**
	 * Liefert einen String, der ID des Objektes, Initiale, Geburtsdatum und das
	 * Geschlecht enthaelt.
	 * 
	 * @return String der alle Parameter formatiert enthaelt.
	 * @see java.lang.Object#toString()
	 * 
	 */
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy",
				Locale.GERMANY);
		return new String(this.getId() + "\t" + this.getInitialen() + "\t"
				+ sdf.format(this.getGeburtsdatum().getTime()) + "\t"
				+ this.getGeschlecht());
	}

	/**
	 * Diese Methode prueft, ob zwei Patienten identisch sind (Objekte sind nur
	 * dann identisch, wenn alle ihre Eigenschaften uebereinstimmen)
	 * 
	 * @param zuvergleichendesObjekt
	 *            das zu vergleichende Objekt vom selben Typ
	 * @return <code>true</code>, wenn beide Objekte gleich sind, ansonsten
	 *         <code>false</code>
	 */
	@Override
	public boolean equals(Object zuvergleichendesObjekt) {
		if (zuvergleichendesObjekt == null) {
			return false;
		}
		if (zuvergleichendesObjekt instanceof PatientBean) {
			PatientBean beanZuvergleichen = (PatientBean) zuvergleichendesObjekt;
			if (this.getId() != beanZuvergleichen.getId()) {
				return false;
			}
			if (!(this.getInitialen().equals(beanZuvergleichen.getInitialen()))) {
				return false;
			}
			if (this.aGeschlecht != beanZuvergleichen.getGeschlecht()) {
				return false;
			}
			if (this.aGeburtsdatum.getTimeInMillis() != beanZuvergleichen
					.getGeburtsdatum().getTimeInMillis()) {
				return false;
			}
			if (this.aPerformanceStatus != beanZuvergleichen
					.getPerformanceStatus()) {
				return false;
			}
			if (this.aDatumAufklaerung.getTimeInMillis() != beanZuvergleichen
					.getDatumAufklaerung().getTimeInMillis()) {
				return false;
			}
			if (this.aKoerperoberflaeche != beanZuvergleichen
					.getKoerperoberflaeche()) {
				return false;
			}
			if (this.aStudienarm != null) {
				try {
					if (!(this.aStudienarm.equals(beanZuvergleichen
							.getStudienarm()))) {
						return false;
					}
				} catch (DatenbankFehlerException e) {
					return false;
				}
			} else {
				if (this.aStudienarmId != beanZuvergleichen.getStudienarmId()) {
					return false;
				}
			}
			if (this.aBenutzerkonto != null) {
				try {
					if (!(this.aBenutzerkonto.equals(beanZuvergleichen
							.getBenutzerkonto()))) {
						return false;
					}
				} catch (BenutzerkontoException e) {
					return false;
				}
			} else {
				if (this.aBenutzerkontoId != beanZuvergleichen
						.getBenutzerkontoId()) {
					return false;
				}
			}
			return true;

		} else {
			return false;
		}

	}

}
