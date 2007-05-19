package de.randi2.model.fachklassen.beans;

import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Vector;
import de.randi2.datenbank.Filter;
import de.randi2.model.exceptions.BenutzerkontoException;
import de.randi2.model.exceptions.StudieException;
import de.randi2.model.fachklassen.Benutzerkonto;
import de.randi2.model.fachklassen.Studie;
import de.randi2.model.fachklassen.Studie.Status;
import de.randi2.utility.NullKonstanten;

/**
 * Die Klasse repraesentiert eine Studie.
 * 
 * @author Susanne Friedrich [sufriedr@stud.hs-heilbronn.de]
 * @author Nadine Zwink [nzwink@stud.hs-heilbronn.de]
 * @version $Id$
 * 
 */
public class StudieBean extends Filter {

	/**
	 * ID der Studie.
	 */
	private long id = NullKonstanten.NULL_LONG;

	/**
	 * Name der Studie.
	 */
	private String name = null;

	/**
	 * Beschreibung der Studie.
	 */
	private String beschreibung = null;

	/**
	 * Das Startdatum der Studie.
	 */
	private GregorianCalendar startDatum = null;

	/**
	 * Das Enddatum der Studie.
	 */
	private GregorianCalendar endDatum = null;

	/**
	 * Der Pfad des hinterlegten Protokolls der Studie.
	 */
	private String studienprotokollPfad = null;

	/**
	 * Die verschiedenen Studienarme der vorliegenden Studie.
	 */
	private Vector<StudienarmBean> aStudienarme = null;

	/**
	 * Typ der Randomisation.
	 */
	private String randomisationsart = null;

	/**
	 * ID der Randomisation.
	 */
	private long randomisationId = NullKonstanten.NULL_LONG;

	/**
	 * Das Zentrum der Studie.
	 */
	private Vector<ZentrumBean> zentren = null;

	/**
	 * Strata der Studie.
	 */
	private Vector<StrataBean> strata = null;

	/**
	 * Das Benutzerkonto der Studie.
	 */
	private BenutzerkontoBean aBenutzerkonto = null;

	/**
	 * Id des Benutzerkontos.
	 */
	private long aBenutzerkontoId = NullKonstanten.NULL_LONG;

	/**
	 * Status der Studie.
	 */
	private Status status = null;

	/**
	 * Konstruktor mit allen Attributen der Klasse.
	 * 
	 * @param id
	 *            Id der Studie
	 * @param beschreibung
	 *            Beschreibung der Studie
	 * @param startdatum
	 *            Startdatum der Studie
	 * @param enddatum
	 *            Enddatum der Studie
	 * @param studienprotokollPfad
	 *            Studienprotokollpfad der Studie
	 * @param randomisationId
	 *            Randomisations-Id
	 * @throws StudieException
	 *             wenn ein Fehler aufgetreten ist
	 */
	public StudieBean(long id, String beschreibung,
			GregorianCalendar startdatum, GregorianCalendar enddatum,
			String studienprotokollPfad, long randomisationId)
			throws StudieException {

		this.setId(id);
		this.setBeschreibung(beschreibung);
		this.setStartDatum(startDatum);
		this.setEndDatum(endDatum);
		this.setStudienprotokollPfad(studienprotokollPfad);
		this.setRandomisationId(randomisationId);
	}

	/**
	 * leerer Konstruktor.
	 */
	public StudieBean() {

	}

	/**
	 * Liefert das Benutzerkonto.
	 * 
	 * @return benutzerkonto, Benutzerkonto
	 * @throws BenutzerkontoException
	 */
	public BenutzerkontoBean getBenutzerkonto() {
		if (aBenutzerkonto == null) {

			try {
				aBenutzerkonto = Benutzerkonto.get(aBenutzerkontoId);
			} catch (BenutzerkontoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return aBenutzerkonto;

	}

	/**
	 * Die Methode setzt das Benutzerkonto.
	 * 
	 * @param aBenutzerkonto
	 *            Benutzerkonto
	 */
	public void setBenutzerkonto(BenutzerkontoBean aBenutzerkonto) {
		this.aBenutzerkonto = aBenutzerkonto;
	}

	/**
	 * Die Methode uebergibt die Beschreibung der Studie.
	 * 
	 * @return beschreibung, Beschreibung der Studie.
	 */
	public String getBeschreibung() {
		return beschreibung;
	}

	/**
	 * Die Methode setzt die Beschreibung der Studie.
	 * 
	 * @param beschreibung
	 *            Beschreibung der Studie.
	 */
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	/**
	 * Die Methode uebergibt das Enddatum der Studie.
	 * 
	 * @return endDatum, Enddatum der Studie.
	 */
	public GregorianCalendar getEndDatum() {
		return endDatum;
	}

	/**
	 * Ueberpruefung, ob das Enddatum der Studie in der Zukunft liegt.
	 * 
	 * @param endDatum
	 *            Enddatum der Studie
	 * @throws StudieException
	 *             Wenn bei der Validierung ein Datumfehler aufgetreten ist.
	 */
	public void setEndDatum(GregorianCalendar endDatum) throws StudieException {
		// Testen, ob sich das Datum in der Zukunft befindet
		if ((new GregorianCalendar(Locale.GERMANY)).before(endDatum)) {
			throw new StudieException(StudieException.DATUM_FEHLER);
		}
		this.endDatum = endDatum;
	}

	/**
	 * Die Methode uebergibt den Name der Studie.
	 * 
	 * @return name, Name der Studie wird uebergeben.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt den Namen der Studie.
	 * 
	 * @param name
	 *            Name der Studie
	 * @throws StudieException
	 *             Wenn bei der Validierung ein Fehler im Studienname
	 *             aufgetreten ist
	 */

	public void setName(String name) throws StudieException {

		if (!this.isFilter()) {

			if (name == null) {
				throw new StudieException(StudieException.STUDIENNAME_FEHLT);
			}
			if (name.length() == 0) {
				throw new StudieException(StudieException.STUDIENNAME_FEHLT);
			}

			if (name.length() < 3 || name.length() > 50) {
				throw new StudieException(StudieException.STUDIENNAME_UNGUELTIG);
			}
			this.name = name;
		}

	}

	/**
	 * Die Methode uebergibt die Randomisationsart der Studie.
	 * 
	 * @return randomisationsart, Randomisationsart der Studie.
	 */
	public String getRandomisationsart() {
		return randomisationsart;
	}

	/**
	 * Setzt die Randomisationsart.
	 * 
	 * @param randomisationsart
	 *            ein String - Konstante aus der entsprechender
	 *            Randomisationsklasse
	 */
	public void setRandomisationseigenschaften(String randomisationsart) {
		this.randomisationsart = randomisationsart;
	}

	/**
	 * Gibt das Startdatum der Studie zurueck.
	 * 
	 * @return startDatum, Start der Studie.
	 */
	public GregorianCalendar getStartDatum() {
		return startDatum;
	}

	/**
	 * Ueberpruefung, ob das Startdatum der Studie in der Zukunft liegt.
	 * 
	 * @param startDatum
	 *            Startdatum der Studie
	 * @throws StudieException
	 *             Wenn bei der Validierung ein Datumfehler aufgetreten ist.
	 */
	public void setStartDatum(GregorianCalendar startDatum)
			throws StudieException {
		// Testen, ob sich das Datum in der Zukunft befindet
		if ((new GregorianCalendar(Locale.GERMANY)).before(startDatum)) {
			throw new StudieException(StudieException.DATUM_FEHLER);
		}
		this.startDatum = startDatum;
	}

	/**
	 * Liefert die ID der Studie.
	 * 
	 * @return id der Studie
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setzt die ID der Studie.
	 * 
	 * @param id
	 *            der Studie.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gibt die Anzahl der Studienarme zurueck.
	 * 
	 * @return studienarme, Anzahl der Studienarme.
	 */
	public Vector<StudienarmBean> getStudienarme() {
		return aStudienarme;
	}

	/**
	 * Setzt die Studienarme
	 * 
	 * @param studienarme
	 *            Studienarme
	 * @throws StudieException
	 *             StudieException
	 */
	public void setStudienarme(Vector<StudienarmBean> studienarme)
			throws StudieException {
		aStudienarme = studienarme;

	}

	/**
	 * Gibt den Pfad des Studienprotokolls zurueck.
	 * 
	 * @return the studienprotokoll_pfad
	 */
	public String getStudienprotokollpfad() {
		return studienprotokollPfad;
	}

	/**
	 * Setzt den Pfad des Studienprotokolls.
	 * 
	 * @param studienprotokollPfad
	 *            Pfad des Studienprotokolls
	 * 
	 */
	public void setStudienprotokollPfad(String studienprotokollPfad) {
		this.studienprotokollPfad = studienprotokollPfad;
	}

	/**
	 * Liefert die Zentren, die an dieser Studie teilnehmen.
	 * 
	 * @return die teilnehmenden Zentren
	 * @throws StudieException
	 *             Exception, wenn zugehoeriges Zentrum nicht gefunden wurden.
	 */
	public Vector<ZentrumBean> getZentren() throws StudieException {
		if (zentren == null) {
			try {
				zentren = Studie.getZugehoerigeZentren(id);
			} catch (StudieException e) {
				throw new StudieException(
						"Zugehöriges Zentrum konnte nicht gefunden werden.");
			}
		}
		return zentren;
	}

	/**
	 * Liefert die Schichten(Strata) der Studie.
	 * 
	 * @return strata Strata der Studie
	 * @throws StudieException
	 *             Exception, wenn keine Schichten gefunden wurden.
	 */
	public Vector<StrataBean> getStrata() throws StudieException {

		if (strata == null) {
			try {
				strata = Studie.getZugehoerigeStrata(id);
			} catch (StudieException e) {
				throw new StudieException("Strata nicht vorhanden.");
			}
		}

		return strata;
	}

	/**
	 * Setzt die teilnehmenden Zentren
	 * 
	 * @param zentren
	 *            zu setzenden Zentren
	 */
	public void setZentren(Vector<ZentrumBean> zentren) {
		this.zentren = zentren;
	}

	/**
	 * Uebergibt den aktuellen Status.
	 * 
	 * @return the status
	 */
	public Status getStatus() {

		return this.status;
	}

	/**
	 * Ueberprueft und setzt den aktuellen Status.
	 * 
	 * @param status
	 *            Status der Studie
	 * @throws StudieException
	 *             StudieException
	 */
	public void setStatus(Status status) throws StudieException {

		if (!this.isFilter()) {

			if (status == null) {
				throw new StudieException(StudieException.STATUSFEHLER);
			}
			this.status = status;

		}
	}

	/**
	 * Uebergibt die Id des Benutzerkonto.
	 * 
	 * @return benutzerkontoId Id des Benutzerkonto.
	 */
	public long getBenutzerkontoId() {
		return aBenutzerkontoId;
	}

	/**
	 * Setzt die Id des Benutzerkonto.
	 * 
	 * @param benutzerkontoId
	 *            Id des Benutzerkonto.
	 */
	public void setBenutzerkontoId(long benutzerkontoId) {
		this.aBenutzerkontoId = benutzerkontoId;
	}
	
	/**
	 * Uebergibt die Id der Randomisation.
	 * 
	 * @return randomisationId Id der Randomisation.
	 */
	public long getRandomisationId() {
		return randomisationId;
	}

	/**
	 * Setzt die Id der Randomisation.
	 * 
	 * @param randomisationId
	 *            Id der Randomisation.
	 */
	public void setRandomisationId(long randomisationId) {
		this.randomisationId = randomisationId;
	}

	/**
	 * Liefert einen String der alle Parameter formatiert enthaelt.
	 * 
	 * @return String der alle Parameter formatiert enthaelt.
	 * @see java.lang.Object#toString()
	 * 
	 */
	@Override
	public String toString() {
		return "id:\t" + this.id + "\trandomistationsId:\t"
				+ this.randomisationId + "\tbeschreibung:\t"
				+ this.beschreibung + "\tstartDatum\t" + this.startDatum
				+ "\tendDatum:\t" + this.endDatum + "\tstudienprotokollPfad\t"
				+ this.studienprotokollPfad;
	}

	/**
	 * Diese Methode prueft, ob zwei Kontos identisch sind. Zwei Kontos sind
	 * identisch, wenn die Studieneigenschaften identisch sind.
	 * 
	 * @param zuvergleichendesObjekt
	 *            das zu vergleichende Objekt vom selben Typ
	 * @return <code>true</code>, wenn beide Kontos gleich sind, ansonsten
	 *         <code>false</code>
	 */

	@Override
	public boolean equals(Object zuvergleichendesObjekt) {

		if (zuvergleichendesObjekt == null) {
			return false;
		}
		if (zuvergleichendesObjekt instanceof StudieBean) {
			StudieBean beanZuvergleichen = (StudieBean) zuvergleichendesObjekt;
			return (this.getId() == beanZuvergleichen.getId()
					&& this.getRandomisationId() == beanZuvergleichen
							.getRandomisationId()
					&& this.getBeschreibung().equals(
							beanZuvergleichen.getBeschreibung())
					&& this.getStartDatum().getTimeInMillis() == beanZuvergleichen
							.getStartDatum().getTimeInMillis()
					&& this.getEndDatum().getTimeInMillis() == beanZuvergleichen
							.getStartDatum().getTimeInMillis() && this
					.getStudienprotokollpfad() == beanZuvergleichen
					.getStudienprotokollpfad());
		}

		return false;
	}

}
