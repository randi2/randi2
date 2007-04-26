/**
 * 
 */
package de.randi2.model.fachklassen.beans;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Vector;
import de.randi2.model.exceptions.StudieException;
import de.randi2.datenbank.Filter;

/**
 * @author Susanne Friedrich [sufriedr@stud.hs-heilbronn.de]
 * @author Nadine Zwink [nzwink@stud.hs-heilbronn.de]
 * @version $Id$
 */
public class StudieBean extends Filter {

	// TODO Kommentare 

	/**
	 * ID der Studie.
	 */
	private long id = 0;
	
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
	private String studienprotokoll_pfad = null;

	/**
	 * Die verschiedenen Studienarme der vorliegenden Studie.
	 */
	private Vector<StudienarmBean> studienarme = null;

	/**
	 * Die Eigenschaften der Randomisation.
	 */
	private RandomisationBean randomisationseigenschaften = null;

	/**
	 * Das Zentrum der Studie.
	 */
	private ZentrumBean zentrum = null;

	/**
	 * Das Benutzerkonto der Studie.
	 */
	private BenutzerkontoBean benutzerkonto = null;

	/**
	 * Der Status der Studie.
	 */
	private int status = -1;

	/**
	 * @return the benutzerkonto
	 */
	public BenutzerkontoBean getBenutzerkonto() {
		return benutzerkonto;
	}

	/**
	 * 
	 * @param benutzerkonto
	 *            the benutzerkonto to set
	 */
	public void setBenutzerkonto(BenutzerkontoBean benutzerkonto) {
		this.benutzerkonto = benutzerkonto;
	}

	/**
	 * @return the beschreibung
	 */
	public String getBeschreibung() {
		return beschreibung;
	}

	/**
	 * @param beschreibung
	 *            the beschreibung to set
	 */
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	/**
	 * @return the endDatum
	 */
	public GregorianCalendar getEndDatum() {
		return endDatum;
	}

	/**
	 * Überprüfung, ob das Enddatum der Studie in der Zukunft liegt.
	 * 
	 * @param endDatum
	 *            Enddatum der Studie
	 * @throws StudieException
	 *             Wenn bei der Validierung ein Datumfehler aufgetreten ist
	 */
	public void setEndDatum(GregorianCalendar endDatum) throws StudieException {
		// Testen, ob sich das Datum in der Zukunft befindet
		if ((new GregorianCalendar(Locale.GERMANY)).before(endDatum)) {
			throw new StudieException(StudieException.DATUM_FEHLER);
		}
		this.endDatum = endDatum;
	}

	/**
	 * @return the name
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
		boolean filter = super.isFilter();

		if (!filter && name == null)
			throw new StudieException(StudieException.STUDIENNAME_FEHLT);
		if (!filter) {
			name = name.trim();
		}
		if (!filter && name.length() == 0) {
			throw new StudieException(StudieException.STUDIENNAME_FEHLT);
		}

		if (!filter && (name.length() < 3 || name.length() > 50)) {
			throw new StudieException(StudieException.STUDIENNAME_UNGUELTIG);
		}

		this.name = name;
	}

	/**
	 * @return the randomisationseigenschaften
	 */
	public RandomisationBean getRandomisationseigenschaften() {
		return randomisationseigenschaften;
	}

	/**
	 * @param randomisationseigenschaften
	 *            the randomisationseigenschaften to set
	 */
	public void setRandomisationseigenschaften(
			RandomisationBean randomisationseigenschaften) {
		this.randomisationseigenschaften = randomisationseigenschaften;
	}

	/**
	 * @return the startDatum
	 */
	public GregorianCalendar getStartDatum() {
		return startDatum;
	}

	/**
	 * Überprüfung, ob das Startdatum der Studie in der Zukunft liegt.
	 * 
	 * @param startDatum
	 *            Startdatum der Studie
	 * @throws StudieException
	 *             Wenn bei der Validierung ein Datumfehler aufgetreten ist
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
	 * @return the studienarme
	 */
	public Vector<StudienarmBean> getStudienarme() {
		return studienarme;
	}

	/**
	 * Setzt die Studienarme
	 * @param studienarme Studienarme
	 *           
	 * @throws StudieException 
	 */
	public void setStudienarme(Vector<StudienarmBean> studienarme) throws StudieException {
		
		boolean filter = super.isFilter();

		if (!filter && studienarme == null)
			throw new StudieException(StudieException.STUDIENARM_FEHLT);
		//TODO !filter
		if (!filter && studienarme.size() == 0) {
			throw new StudieException(StudieException.STUDIENARM_FEHLT);
		}

		if (!filter && (studienarme.size() < 3 || studienarme.size() > 50)) {
			throw new StudieException(StudieException.STUDIENARM_UNGUELTIG);
		}

		this.studienarme = studienarme;
	}

	/**
	 * @return the studienprotokoll_pfad
	 */
	public String getStudienprotokoll_pfad() {
		return studienprotokoll_pfad;
	}

	/**
	 * @param studienprotokoll_pfad
	 *            the studienprotokoll_pfad to set
	 */
	public void setStudienprotokoll_pfad(String studienprotokoll_pfad) {
		this.studienprotokoll_pfad = studienprotokoll_pfad;
	}

	/**
	 * @return the zentrum
	 */
	public ZentrumBean getZentrum() {
		return zentrum;
	}

	/**
	 * @param zentrum
	 *            the zentrum to set
	 */
	public void setZentrum(ZentrumBean zentrum) {
		this.zentrum = zentrum;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Liefert die ID der Studie.
	 * @return id der Studie
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setzt die ID der Studie.
	 * @param id der Studie.
	 */
	public void setId(long id) {
		this.id = id;
	}

}
