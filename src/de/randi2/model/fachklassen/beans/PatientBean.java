package de.randi2.model.fachklassen.beans;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

import de.randi2.datenbank.DBObjekt;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.BenutzerException;
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
public class PatientBean extends DBObjekt {

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
	private long aStudienarmId = NullKonstanten.DUMMY_ID;

	/**
	 * Das Benutzerkonto.
	 */
	private BenutzerkontoBean aBenutzerkonto = null;

	/**
	 * Die eindeutige ID des Benutzerkontos.
	 */
	private long aBenutzerkontoId = NullKonstanten.DUMMY_ID;

	/**
	 * Serialisierter String, der die Strata Kombination dieses Patienten
	 * repraesentiert
	 */
	private String aStrataGruppe = null;

	/**
	 * Standardkonstruktor von PatientBean mit Aufruf der Superklasse.
	 */
	public PatientBean() {
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
	 * @param strataGruppe -
	 *            ein serialisierter String, die die Stratagruppe des Patienten
	 *            eindeutig beschreibt
	 * @throws PatientException -
	 *             wenn die uebergebene Daten nicht valide waren
	 * @throws DatenbankExceptions -
	 *             wenn eine inkorrekte Id uebergeben wurde
	 */
	public PatientBean(long id, String initialen, char geschlecht,
			GregorianCalendar geburtsdatum, int performanceStatus,
			GregorianCalendar datumAufklaerung, int koerperoberflaeche,
			long studienarmId, long benutzerkontoId, String strataGruppe)
			throws PatientException, DatenbankExceptions {

		this.setId(id);
		this.setInitialen(initialen);
		this.setGeschlecht(geschlecht);
		this.setGeburtsdatum(geburtsdatum);
		this.setPerformanceStatus(performanceStatus);
		this.setDatumAufklaerung(datumAufklaerung);
		this.setKoerperoberflaeche(koerperoberflaeche);
		this.setStudienarmId(studienarmId);
		this.setBenutzerkontoId(benutzerkontoId);
		this.setStrataGruppe(strataGruppe);
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
	 * @throws PatientException -
	 *             wenn das uebergebene Datum in der Zukunft liegt. (oder wenn
	 *             das übergebene Datum vor dem Geburtsdatum liegt)
	 */
	public void setDatumAufklaerung(GregorianCalendar datumAufklaerung)
			throws PatientException {
		if ((new GregorianCalendar(Locale.GERMANY)).before(datumAufklaerung)) {
			throw new PatientException(PatientException.DATUM_IN_DER_ZUKUNFT);
		}
		if (this.aGeburtsdatum == null) {
			this.aDatumAufklaerung = datumAufklaerung;
		} else {
			if (this.aGeburtsdatum.after(datumAufklaerung)) {
				throw new PatientException(
						PatientException.DATUM_VOR_GEBDATUM);
			} else {
				this.aDatumAufklaerung = datumAufklaerung;
			}
		}

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
	 * @throws PatientException -
	 *             wenn das uebergebene Datum in der Zukunft liegt. (oder nach
	 *             dem Datum der Aufklaerung)
	 */
	public void setGeburtsdatum(GregorianCalendar geburtsdatum)
			throws PatientException {
		if ((new GregorianCalendar(Locale.GERMANY)).before(geburtsdatum)) {
			throw new PatientException(PatientException.DATUM_IN_DER_ZUKUNFT);
		}
		if (this.aDatumAufklaerung == null) {
			this.aGeburtsdatum = geburtsdatum;
		} else {
			if (this.aDatumAufklaerung.before(geburtsdatum)) {
				throw new PatientException(
						PatientException.DATUM_IN_DER_ZUKUNFT);
			} else {
				this.aGeburtsdatum = geburtsdatum;
			}
		}
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
	 * @throws PatientException -
	 *             wenn das uebergebene Geschlecht falsch war.
	 */
	public void setGeschlecht(char geschlecht) throws PatientException {
		if (geschlecht != 'm' && geschlecht != 'w') {
			throw new PatientException(PatientException.GESCHLECHT_FALSCH);
		}
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
	 * @throws PatientException -
	 *             wenn der uebergeben Wert negativ ist.
	 */
	public void setKoerperoberflaeche(float koerperoberflaeche)
			throws PatientException {
		if (koerperoberflaeche < 0) {
			throw new PatientException(
					PatientException.KOERPEROBERFLAECHE_NEGATIV);
		}
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
	 * @throws PatientException -
	 *             wenn der uebergebene Wert nicht korrekt ist.
	 */
	public void setPerformanceStatus(int performanceStatus)
			throws PatientException {
		if (performanceStatus < 0 || performanceStatus > 4) {
			throw new PatientException(
					PatientException.PERFORMANCE_STATUS_FALSCH);
		}
		aPerformanceStatus = performanceStatus;
	}

	/**
	 * Get-Methode fuer die Rueckgabe eines StudienarmBeans.
	 * 
	 * @return Liefert das StudienarmBean.
	 * @throws PatientException
	 *             Wirft eine Exception, falls die Studienarm-ID gleich dem
	 *             DUMMY_ID ist o. wenn ein Fehler in der DB auftrat.
	 */
	public StudienarmBean getStudienarm() throws PatientException {
		if (aStudienarm == null) {
			if (aStudienarmId == NullKonstanten.DUMMY_ID) {
				throw new PatientException(
						PatientException.STUDIENARM_NICHT_VORHANDEN);
			} else {
				try {
					aStudienarm = Studienarm.getStudienarm(aStudienarmId);
				} catch (DatenbankExceptions e) {
					throw new PatientException(PatientException.DB_FEHLER);
				}
			}
		}
		return aStudienarm;
	}

	/**
	 * Set-Methode fuer das Setzen des StudienarmBean. Die Verbindung wird auch
	 * automatisch mit im Studienarm geschrieben.
	 * 
	 * @param studienarm
	 *            Setzt das StudienarmBean.
	 * @throws PatientException -
	 *             wenn das uebergebene Objekt noch nicht in der DB gespeichert
	 *             wurde.
	 * @throws DatenbankExceptions
	 *             Falls Probleme in der Datenbank auftreten.
	 */
	public void setStudienarm(StudienarmBean studienarm)
			throws PatientException, DatenbankExceptions {
		if (studienarm == null) {
			throw new PatientException(PatientException.STUDIENARM_NULL);
		}
		this.setStudienarmId(studienarm.getId());
		this.aStudienarm = studienarm;
		studienarm.getPatienten().add(this);
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
	 * @throws PatientException -
	 *             wenn die Id gleich der Dummy_Id ist.
	 */
	public void setStudienarmId(long studienarmId) throws PatientException {
		if (studienarmId == NullKonstanten.DUMMY_ID) {
			throw new PatientException(
					PatientException.STUDIENARM_NOCH_NICHT_GESPEICHERT);
		}
		aStudienarmId = studienarmId;
	}

	/**
	 * Get-Methode fuer die Rueckgabe des BenutzerkontoBean.
	 * 
	 * @return Liefert das BenutzerkontoBean.
	 * @throws DatenbankExceptions -
	 *             wenn beim Holen des entsprechendes Bentutzerkontoobjektes
	 *             Probleme vorkamen.
	 */
	public BenutzerkontoBean getBenutzerkonto() throws DatenbankExceptions {
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
	 * @throws PatientException -
	 *             wenn das uebergebene Objekt noch nicht persistent ist.
	 */
	public void setBenutzerkonto(BenutzerkontoBean benutzerkonto)
			throws PatientException {
		this.setBenutzerkontoId(benutzerkonto.getId());
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
	 * @throws PatientException
	 *             wenn eine falsche Id uebergeben wurde.
	 */
	public void setBenutzerkontoId(long benutzerkontoId)
			throws PatientException {
		if (benutzerkontoId < 0) {
			throw new PatientException(PatientException.BENUTZERKONTOID_FALSCH);
		}
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
				} catch (PatientException e) {
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
				} catch (DatenbankExceptions e) {
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

	/**
	 * Liefert den serialisierten String mit der Strata Kombination des
	 * Patienten
	 * 
	 * @return Strata Kombination
	 */
	public String getStrataGruppe() {
		return aStrataGruppe;
	}

	/**
	 * Setzt die Stratagruppe
	 * 
	 * @param strataGruppe -
	 *            die neue Stratagruppe des Patienten in serlialisiertet Form
	 */
	public void setStrataGruppe(String strataGruppe) {
		this.aStrataGruppe = strataGruppe;
	}

	/**
	 * Liefert den HashCode des Objektes.<br>
	 * Der HashCode entspricht der (Datenbank-)Id des Objektes. Ist das Objekt
	 * noch nicht gespeichert worden, besitzt also die ID
	 * {@link NullKonstanten#DUMMY_ID}, so wird der HashCode von
	 * {@link java.lang.Object#hashCode()} geliefert.
	 * 
	 * @return HashCode des Objektes
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if (this.getId() == NullKonstanten.DUMMY_ID) {
			return super.hashCode();
		}
		return (int) this.getId();
	}

	/**
	 * Diese Methode ueberprueft, ob alle notwendigen Atributte des Objektes
	 * gesetzt wurden. Zu diesen gehoeren: aInitialen aGeburtsdatum aGeschlecht
	 * aDatumAufklaerung aKoerperoberflaeche aPerformanceStatus aBenutzerkontoId
	 * aStudienarmId
	 * 
	 * @throws BenutzerException -
	 *             wenn ein Fehler bei der Ueberpruefung auftrat
	 */
	@Override
	public void validate() throws BenutzerException {
		if (this.getInitialen() == null) {
			throw new PatientException(PatientException.INITIALEN_NULL);
		} else {
			if (this.getInitialen().equals("")) {
				throw new PatientException(PatientException.INITIALEN_LEER);
			}
		}
		if (this.getGeburtsdatum() == null
				|| this.getDatumAufklaerung() == null) {
			throw new PatientException(PatientException.DATUM_NULL);
		}
		if (this.getGeschlecht() == NullKonstanten.NULL_CHAR) {
			throw new PatientException(PatientException.GESCHLECHT_NULL);
		}
		if (this.getKoerperoberflaeche() == NullKonstanten.NULL_FLOAT) {
			throw new PatientException(PatientException.KOERPEROBERFLACHE_NULL);
		}
		if (this.getPerformanceStatus() == NullKonstanten.NULL_INT) {
			throw new PatientException(PatientException.PERFORMANCE_NULL);
		}
		if (this.getBenutzerkontoId() == NullKonstanten.DUMMY_ID) {
			throw new PatientException(PatientException.BENUTZERKONTO_NULL);
		}
		if (this.getStudienarmId() == NullKonstanten.DUMMY_ID) {
			throw new PatientException(PatientException.STUDIENARM_NULL);
		}

	}
}
