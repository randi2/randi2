package de.randi2.model.fachklassen.beans;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Vector;
import de.randi2.datenbank.Filter;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.StudieException;
import de.randi2.model.fachklassen.Benutzerkonto;
import de.randi2.model.fachklassen.Studie;
import de.randi2.model.fachklassen.Studie.Status;
import de.randi2.randomisation.Randomisation;
import de.randi2.utility.NullKonstanten;

/**
 * <p>
 * Die Klasse repraesentiert eine Studie.
 * </p>
 * 
 * @author Susanne Friedrich [sufriedr@stud.hs-heilbronn.de]
 * @author Nadine Zwink [nzwink@stud.hs-heilbronn.de]
 * @version $Id$
 * 
 */
public class StudieBean extends Filter {

	/**
	 * Name der Studie.
	 */
	private String aName = null;

	/**
	 * Beschreibung der Studie.
	 */
	private String aBeschreibung = null;

	/**
	 * Das Startdatum der Studie.
	 */
	private GregorianCalendar aStartDatum = null;

	/**
	 * Das Enddatum der Studie.
	 */
	private GregorianCalendar aEndDatum = null;

	/**
	 * Der Pfad des hinterlegten Protokolls der Studie.
	 */
	private String studienprotokollPfad = null;

	/**
	 * Die verschiedenen Studienarme der vorliegenden Studie.
	 */
	private Vector<StudienarmBean> aStudienarme = null;

	/**
	 * Institution der Studie.
	 */
	private String aInstitution = null;

	/**
	 * Die zu der Studie zugeordnete Zentren.
	 */
	private Vector<ZentrumBean> aZentren = null;

	/**
	 * Strata der Studie.
	 */
	private Vector<StrataBean> aStrata = null;

	/**
	 * Das Benutzerkonto der Studie - Benutzerkonto des Studienleiters.
	 */
	private BenutzerkontoBean aBenutzerkonto = null;

	/**
	 * Id des Benutzerkontos (des Studienleiters)
	 */
	private long aBenutzerkontoId = NullKonstanten.DUMMY_ID;

	/**
	 * Statistiker-Account fuer diese Studie.
	 */
	private BenutzerkontoBean aStatistiker = null;

	/**
	 * ID des Studienleiters.
	 */
	private long aStatistikerId = NullKonstanten.DUMMY_ID;

	/**
	 * Enthaelt den Name des Randomisationsalgorithmus - diese Namen sind in der
	 * enum der Randomisationsklasse zu finden.
	 */
	private Randomisation.Algorithmen aAlgorithmus = null;

	/**
	 * Status der Studie.
	 */
	private Status aStatus = null;
	
	/**
	 * Falls die Studie mit Blockrandomisation konfiguriert ist, wird hier die Blockgroesse gespeichert
	 */
	private int aBlockgroesse= NullKonstanten.NULL_INT;

	/**
	 * Konstruktor mit allen Attributen der Klasse, die aus der Datenbank
	 * ausgelesen werden können. (STUDIE OHNE STATISTIKER)
	 * 
	 * @param id
	 *            Id der Studie
	 * @param beschreibung
	 *            Beschreibung der Studie
	 * @param name
	 *            Name der Studie.
	 * @param algorithmus -
	 *            beschreibt den Randomisationsalgorithmus, der bei dieser
	 *            Studie angewandt wird.
	 * @param benutzerId
	 *            Id des Benutzerkontos des Studienleiters
	 * @param startdatum
	 *            Startdatum der Studie
	 * @param enddatum
	 *            Enddatum der Studie
	 * @param studienprotokollPfad
	 *            Studienprotokollpfad der Studie
	 * @param status
	 *            Status der Studie
	 * @param blockgroesse
	 *            Gibt die Blockgroesse fuer Blockrandomisation an
	 * @throws StudieException
	 *             wenn ein Fehler aufgetreten ist
	 * @throws DatenbankExceptions
	 *             wenn eine inkorrekte Id uebergeben wurde
	 */
	public StudieBean(long id, String beschreibung, String name,
			Randomisation.Algorithmen algorithmus, long benutzerId,
			GregorianCalendar startdatum, GregorianCalendar enddatum,
			String studienprotokollPfad, Status status, int blockgroesse)
			throws StudieException, DatenbankExceptions {

		super.setId(id);
		this.setBeschreibung(beschreibung);
		this.setName(name);
		this.setAlgorithmus(algorithmus);
		this.setBenutzerkontoId(benutzerId);
		this.setStudienZeitraum(startdatum, enddatum);
		this.setStudienprotokollPfad(studienprotokollPfad);
		this.setStatus(status);
		this.setBlockgroesse(blockgroesse);
	}

	/**
	 * Konstruktor mit allen Attributen der Klasse, die aus der Datenbank
	 * ausgelesen werden können. (DATENBANK KONSTRUKTOR)
	 * 
	 * @param id
	 *            Id der Studie
	 * @param beschreibung
	 *            Beschreibung der Studie
	 * @param name
	 *            Name der Studie.
	 * @param algorithmus -
	 *            beschreibt den Randomisationsalgorithmus, der bei dieser
	 *            Studie angewandt wird.
	 * @param benutzerId
	 *            Id des Benutzerkontos des Studienleiters
	 * @param startdatum
	 *            Startdatum der Studie
	 * @param enddatum
	 *            Enddatum der Studie
	 * @param studienprotokollPfad
	 *            Studienprotokollpfad der Studie
	 * @param status
	 *            Status der Studie
	 * @param blockgroesse
	 *            Gibt die Blockgroesse fuer Blockrandomisation an
	 * @param statistikerId
	 *            Id des Benutzerkontos des Statistikers, der fuer diese Studie
	 *            eingerichtet wurde. (WENN KEIN STATISTIKER ZUR STUIDE
	 *            VORHANDEN, BITTE DUMMY_ID UEBERGEBEN!)
	 * @throws StudieException
	 *             wenn ein Fehler aufgetreten ist
	 * @throws DatenbankExceptions
	 *             wenn eine inkorrekte Id uebergeben wurde
	 */
	public StudieBean(long id, String beschreibung, String name,
			Randomisation.Algorithmen algorithmus, long benutzerId,
			GregorianCalendar startdatum, GregorianCalendar enddatum,
			String studienprotokollPfad, Status status, int blockgroesse,
			long statistikerId) throws StudieException, DatenbankExceptions {

		super.setId(id);
		this.setBeschreibung(beschreibung);
		this.setName(name);
		this.setAlgorithmus(algorithmus);
		this.setBenutzerkontoId(benutzerId);
		this.setStudienZeitraum(startdatum, enddatum);
		this.setStudienprotokollPfad(studienprotokollPfad);
		this.setStatus(status);
		this.setBlockgroesse(blockgroesse);
		this.setStatistikerId(statistikerId);
	}

	/**
	 * leerer Konstruktor.
	 */
	public StudieBean() {

	}

	/**
	 * Liefert das Benutzerkonto des Studienleiters.
	 * 
	 * @return benutzerkonto, Benutzerkonto
	 * @throws DatenbankExceptions
	 *             Exception, wenn beim Holen des entsprechendes
	 *             Bentutzerkontoobjektes Probleme vorkamen.
	 */
	public BenutzerkontoBean getBenutzerkonto() throws DatenbankExceptions {
		if (aBenutzerkonto == null) {

			aBenutzerkonto = Benutzerkonto.get(aBenutzerkontoId);
		}
		return aBenutzerkonto;

	}

	/**
	 * Die Methode setzt das Benutzerkonto.
	 * 
	 * @param aBenutzerkonto
	 *            Benutzerkonto
	 * @throws StudieException
	 *             wenn die ID des Beans nicht korrekt ist.
	 */
	public void setBenutzerkonto(BenutzerkontoBean aBenutzerkonto)
			throws StudieException {
		this.setBenutzerkontoId(aBenutzerkonto.getBenutzerId());
		this.aBenutzerkonto = aBenutzerkonto;
	}

	/**
	 * Die Methode uebergibt die Beschreibung der Studie.
	 * 
	 * @return beschreibung, Beschreibung der Studie.
	 */
	public String getBeschreibung() {
		return aBeschreibung;
	}

	/**
	 * Die Methode setzt die Beschreibung der Studie.
	 * 
	 * @param beschreibung
	 *            Beschreibung der Studie.
	 */
	public void setBeschreibung(String beschreibung) {
		this.aBeschreibung = beschreibung;
	}

	/**
	 * Die Methode uebergibt das Enddatum der Studie.
	 * 
	 * @return endDatum, Enddatum der Studie.
	 */
	public GregorianCalendar getEndDatum() {
		return aEndDatum;
	}

	/**
	 * Die Methode uebergibt den Name der Studie.
	 * 
	 * @return name, Name der Studie wird uebergeben.
	 */
	public String getName() {
		return aName;
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
				throw new StudieException(StudieException.STUDIENNAME_LEER);
			}
			if (name.length() == 0) {
				throw new StudieException(StudieException.STUDIENNAME_LEER);
			}

			if (name.length() < 3 || name.length() > 50) {
				throw new StudieException(StudieException.STUDIENNAME_UNGUELTIG);
			}
		}
		this.aName = name;
	}

	/**
	 * Gibt das Startdatum der Studie zurueck.
	 * 
	 * @return startDatum, Start der Studie.
	 */
	public GregorianCalendar getStartDatum() {
		return aStartDatum;
	}

	/**
	 * Methode setzt das Start- und Endedatum einer Studie. Es wird ueberprueft,
	 * ob das uebergebene Startdatum vor dem uebergebenen Enddatum liegt.
	 * 
	 * @param startDatum
	 *            Startdatum der Studie
	 * @param endDatum
	 *            Enddatum der Studie
	 * @throws StudieException
	 *             die Überprüfungen der Parameter schlug fehl.
	 */
	public void setStudienZeitraum(GregorianCalendar startDatum,
			GregorianCalendar endDatum) throws StudieException {
		if (!endDatum.after(startDatum)) {
			throw new StudieException(StudieException.DATUM_FEHLER);
		}
		this.aEndDatum = endDatum;
		this.aStartDatum = startDatum;
	}

	/**
	 * Gibt die Anzahl der Studienarme zurueck.
	 * 
	 * @return studienarme, Anzahl der Studienarme.
	 * @throws DatenbankExceptions
	 *             wenn bei dem Prozess Fehler auftraten
	 */
	public Vector<StudienarmBean> getStudienarme() throws DatenbankExceptions {
		if (aStudienarme == null) {
			aStudienarme = Studie.getStudienarme(this);
		}
		return aStudienarme;
	}

	/**
	 * Setzt die Studienarme
	 * 
	 * @param studienarme
	 *            Studienarme
	 * @throws StudieException
	 *             wenn Studienarme nicht gesetzt wurden
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
	 * @throws DatenbankExceptions
	 *             Exception, wenn zugehoeriges Zentrum nicht gefunden wurden.
	 */
	public Vector<ZentrumBean> getZentren() throws DatenbankExceptions {
		if (aZentren == null) {

			aZentren = Studie.getZugehoerigeZentren(this.getId());

		}
		return aZentren;
	}

	/**
	 * Liefert die Anzahl der zugeordneten Zentren.
	 * 
	 * @return die Anzahl zugeordneten Zentren.
	 */
	public int getAnzahlZentren() {

		if (this.aZentren == null) {

			return 0;

		}

		return aZentren.size();

	}

	/**
	 * Setzt die teilnehmenden Zentren
	 * 
	 * @param zentren
	 *            zu setzenden Zentren
	 * @throws StudieException
	 *             wenn Zentrum nicht gesetzt wurde.
	 */
	public void setZentren(Vector<ZentrumBean> zentren) throws StudieException {
		this.aZentren = zentren;
	}
/**
 * Die Methode fügt ein Zentrum der Studie hinzu
 * @param sZentrum
 * @throws StudieException 
 * @throws DatenbankExceptions 
 */
	public void addZentrum(StudieBean aSession, ZentrumBean sZentrum) throws DatenbankExceptions, StudieException{
		Studie stud = new Studie(aSession);
		stud.zuweisenZentrum(sZentrum);
		
		
		
	}
	/**
	 * Liefert die Schichten(Strata) der Studie.
	 * 
	 * @return strata Strata der Studie
	 * @throws DatenbankExceptions
	 *             Exception, wenn keine Schichten gefunden wurden.
	 */
	public Vector<StrataBean> getStrata() throws DatenbankExceptions {

		if (aStrata == null) {
			aStrata = Studie.getZugehoerigeStrata(this.getId());
		}

		return aStrata;
	}

	/**
	 * Setzt die Strata einer Studie.
	 * 
	 * @param strata
	 *            Strata einer Studie
	 * @throws StudieException
	 *             wenn kein Strata nicht gesetzt wurde.
	 */
	public void setStrata(Vector<StrataBean> strata) throws StudieException {
		this.aStrata = strata;

	}

	/**
	 * Gibt die Anzahl der Strata zu einer Studie zurueck.
	 * 
	 * @return Anzahl der Strata zu einer Studie.
	 */
	public int getAnzahlStrata() {

		if (aStrata == null) {
			return 0;
		}
		return aStrata.size();

	}

	/**
	 * Uebergibt den aktuellen Status.
	 * 
	 * @return the status
	 */
	public Status getStatus() {

		return this.aStatus;
	}

	/**
	 * Ueberprueft und setzt den aktuellen Status.
	 * 
	 * @param status
	 *            Status der Studie
	 * @throws StudieException
	 *             StudieException wenn Status nicht gesetzt wurde.
	 */
	public void setStatus(Status status) throws StudieException {

		if (!this.isFilter()) {

			if (status == null) {
				throw new StudieException(StudieException.STATUSFEHLER);
			}
		}

		this.aStatus = status;
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
	 * @throws StudieException
	 *             wenn die uebergebene Id fehlerhaft ist
	 */
	public void setBenutzerkontoId(long benutzerkontoId) throws StudieException {
		if (benutzerkontoId == NullKonstanten.DUMMY_ID || benutzerkontoId < 0) {
			throw new StudieException(
					StudieException.BENUTZERKONTO_ID_FEHLERHAFT);
		}
		this.aBenutzerkontoId = benutzerkontoId;
	}

	/**
	 * Die Methode uebergibt die Institution der Studie.
	 * 
	 * @return institution, Institution der Studie.
	 */
	public String getInstitution() {
		return aInstitution;
	}

	/**
	 * Setzt die Institution.
	 * 
	 * @param institution
	 *            ein String - Konstante aus der entsprechender
	 *            Institutionsklasse
	 */
	public void setInstitution(String institution) {
		this.aInstitution = institution;
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
		String studieString = "id:\t" + this.getId() + "\name:\t" + this.aName
				+ "\tbeschreibung:\t" + this.aBeschreibung + "\tstartDatum\t"
				+ this.aStartDatum + "\tendDatum:\t" + this.aEndDatum
				+ "\tstudienprotokollPfad\t" + this.studienprotokollPfad
				+ "\trandomisationsalogorithmus\t"
				+ this.getAlgorithmus().toString() + "\tbenutzerkontobject:\t"
				+ this.aBenutzerkonto + "\tbenutzerkontoid:\t"
				+ this.getBenutzerkontoId() + "\tstatus:\t" + this.aStatus;

		if (aStudienarme == null) {
			studieString += "\tstudienarme:\t" + "keine Studienarme";
		} else {
			for (int i = 0; i < aStudienarme.size(); i++) {
				studieString += "\tstudienarme:\t"
						+ aStudienarme.elementAt(i).toString();
			}
		}

		if (aZentren == null) {
			studieString += "\tzentren:\t" + "keine Zentren";
		} else {
			for (int i = 0; i < aZentren.size(); i++) {
				studieString += "\tzentren:\t"
						+ aZentren.elementAt(i).toString();
			}
		}
		if (aStrata == null) {
			studieString += "keine Strata";
		} else {
			for (int i = 0; i < aStrata.size(); i++) {
				studieString += "\tzentren:\t"
						+ aStrata.elementAt(i).toString();
			}
		}

		return studieString;
	}

	/**
	 * Diese Methode prueft, ob zwei Studien identisch sind. Zwei Studien sind
	 * identisch, wenn die Studieneigenschaften identisch sind.
	 * 
	 * @param zuvergleichendesObjekt
	 *            das zu vergleichende Objekt vom selben Typ
	 * @return true, wenn beide Studien gleich sind, ansonsten false
	 */

	@Override
	public boolean equals(Object zuvergleichendesObjekt) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy",
				Locale.GERMANY);
		StudieBean studieBean = null;

		if (zuvergleichendesObjekt == null) {
			return false;
		}
		if (zuvergleichendesObjekt instanceof StudieBean) {
			studieBean = (StudieBean) zuvergleichendesObjekt;

			if (studieBean.getId() != this.getId()) {
				return false;

			}
			if (!studieBean.getName().equals(this.getName())) {
				return false;
			}
			if (!studieBean.getBeschreibung().equals(this.getBeschreibung())) {
				return false;
			}
			if (studieBean.getStartDatum() == null
					&& this.getStartDatum() != null) {
				return false;
			} else if (studieBean.getStartDatum() != null
					&& !(sdf.format(studieBean.getStartDatum().getTime())
							.equals(sdf.format(this.getStartDatum().getTime())))) {
				return false;
			}
			if (studieBean.getEndDatum() == null && this.getEndDatum() != null) {
				return false;
			} else if (studieBean.getEndDatum() != null
					&& !(sdf.format(studieBean.getEndDatum().getTime())
							.equals(sdf.format(this.getEndDatum().getTime())))) {
				return false;
			}
			if (!studieBean.getStudienprotokollpfad().equals(
					this.getStudienprotokollpfad())) {
				return false;
			}
			if (!(studieBean.getAlgorithmus() == this.getAlgorithmus())) {
				return false;
			}
			if (studieBean.getStatus() != this.getStatus()) {
				return false;
			}
			if (studieBean.getAnzahlZentren() != this.getAnzahlZentren()) {
				return false;
			}
			if (studieBean.getAnzahlStrata() != this.getAnzahlStrata()) {
				return false;
			}
			if (studieBean.getBenutzerkontoId() != this.getBenutzerkontoId()) {
				return false;
			}
		}

		return true;

	}

	/**
	 * Diese Methode ueberprueft, ob alle notwendigen Attribute bei einem Objekt
	 * dieser Klasse gesetzt wurden.
	 * 
	 * @throws StudieException -
	 *             wenn die Ueberprufung der Felder fehl schlug
	 */
	@Override
	public void validate() throws StudieException {

		if (this.aAlgorithmus == null) {
			throw new StudieException(StudieException.ALGORITHMUS_UNGUELTIG);
		} else {
			if (this.aAlgorithmus.equals("")) {
				throw new StudieException(StudieException.ALGORITHMUS_UNGUELTIG);
			}
		}
		if (this.aEndDatum == null) {
			throw new StudieException(StudieException.ENDDATUM_LEER);
		} else {
			if (this.aEndDatum.equals("")) {
				throw new StudieException(StudieException.ENDDATUM_LEER);
			}
		}
		if (this.aName == null) {
			throw new StudieException(StudieException.STUDIENNAME_LEER);
		} else {
			if (this.aName.equals("")) {
				throw new StudieException(StudieException.STUDIENNAME_LEER);
			}
		}
		if (this.aStartDatum == null) {
			throw new StudieException(StudieException.STARTDATUM_LEER);
		} else {
			if (this.aStartDatum.equals("")) {
				throw new StudieException(StudieException.STARTDATUM_LEER);
			}
		}
		if (this.studienprotokollPfad == null) {
			throw new StudieException(StudieException.STUDIENPROTOKOLL_LEER);
		} else {
			if (this.studienprotokollPfad.equals("")) {
				throw new StudieException(StudieException.STUDIENPROTOKOLL_LEER);
			}
		}

	}

	/**
	 * Liefert den Randomisationsalgorithmus
	 * 
	 * @return - ein Element aus der Randomisation.Algorithmen enum
	 */
	public Randomisation.Algorithmen getAlgorithmus() {
		return aAlgorithmus;
	}

	/**
	 * Setzt den Algorithmus. Es sind nur Werte der Enum
	 * Randomisation.Algorithmen erlaubt!
	 * 
	 * @param algorithmus
	 *            der gewuenschte Algorithmus
	 * @throws StudieException
	 *             falls ein ungueltiger Algorithmus uebergeben wurde
	 */
	public void setAlgorithmus(Randomisation.Algorithmen algorithmus)
			throws StudieException {

		if (!this.isFilter()) {
			if (algorithmus == null) {

				throw new StudieException(StudieException.ALGORITHMUS_UNGUELTIG);

			}
		}

		aAlgorithmus = algorithmus;
	}

	/**
	 * Get-Methode fuer die Blockgroesse
	 * 
	 * @return Blockgroesse
	 */
	public int getBlockgroesse() {
		return aBlockgroesse;
	}

	/**
	 * Set Methode fuer die Blockgroesse
	 * 
	 * @param blockgroesse
	 *            zu setzende Blockgroesse
	 * @throws StudieException -
	 *             wenn die uebergebene Blockgroesse kleiner als 2 ist.
	 */
	public void setBlockgroesse(int blockgroesse) throws StudieException {
		if(blockgroesse<2 && this.getAlgorithmus()!=Randomisation.Algorithmen.VOLLSTAENDIGE_RANDOMISATION) {
			throw new StudieException(StudieException.BLOCKGROESSE_ZU_KLEIN);
		}
		this.aBlockgroesse = blockgroesse;
	}

	/**
	 * Diese Methode setzt den Statistiker.
	 * 
	 * @param statistiker -
	 *            BenutzerkontoBean, das dem Statistiker gehoert.
	 * @throws StudieException -
	 *             wenn Fehlert beim Prozess auftraten.
	 */
	public void setStatistiker(BenutzerkontoBean statistiker)
			throws StudieException {
		if (statistiker != null) {
			if (statistiker.getId() != NullKonstanten.DUMMY_ID) {
				this.setStatistikerId(statistiker.getId());
			} else {
				throw new StudieException(
						StudieException.BENUTZERKONTO_ID_FEHLERHAFT);
			}
			this.aStatistiker = statistiker;
		} else {
			throw new StudieException(StudieException.BENUTZERKONTOBEAN_NULL);
		}
	}

	/**
	 * Diese Methode setzt die Id des Studienleiters.
	 * 
	 * @param id -
	 *            die eindeutige Id des Studienleiters.
	 */
	public void setStatistikerId(long id) {
		this.aStatistikerId = id;

	}

	/**
	 * Diese Methode liefrt den Statistiker dieser Studie.
	 * 
	 * @return - Statistiker diese Studie.
	 * @throws StudieException -
	 *             wenn die ubergebene Id gleich dem DUMMY_ID ist.
	 * @throws DatenbankExceptions -
	 *             wenn ein Fehler bei der DB auftrat.
	 */
	public BenutzerkontoBean getStatistiker() throws StudieException,
			DatenbankExceptions {
		if (this.aStatistiker == null) {
			if (aStatistikerId != NullKonstanten.DUMMY_ID) {
				this.aStatistiker = Benutzerkonto.get(this.aStatistikerId);
			} else {
				throw new StudieException(
						StudieException.KEIN_STATISTIKER_VORHANDEN);
			}
		}
		return this.aStatistiker;
	}

	/**
	 * Liefert die Id des Statistkiers.
	 * 
	 * @return - eindeutige Id des Statistikers oder die DUMMY_ID falls der
	 *         Statistiker nicht angelegt wurde.
	 */
	public long getStatistikerId() {
		return this.aStatistikerId;
	}

}
