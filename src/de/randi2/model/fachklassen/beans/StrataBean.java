package de.randi2.model.fachklassen.beans;

import java.util.Collection;
import java.util.Vector;

import org.apache.log4j.Logger;

import de.randi2.datenbank.Filter;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.StrataException;
import de.randi2.model.fachklassen.Strata;
import de.randi2.model.fachklassen.Studie;
import de.randi2.utility.NullKonstanten;

/**
 * Die Klasse StrataBean kapselt die Eigenschaft eines Stratas. Sie kann - wenn
 * benoetigt - auch die aktuelle Auspraegung eines Stratas repraentieren.
 * 
 * @author Johannes Thoenes [jthoenes@stud.hs-heilbronn.de]
 * @author Daniel Haehn [dhaehn@stud.hs-heilbronn.de]
 * @version $Id$
 * 
 */
public class StrataBean extends Filter implements Comparable<StrataBean> {

	/**
	 * Der Name des Stratas.
	 */
	private String name;

	/**
	 * Eine Beschreibung die angibt, wie die Strata-Auspraegung eines Patienten
	 * ermittelt werden sollen und evtl. Bezugspunkte (z.B. Alter am 1.1.2007)
	 * angibt.
	 */
	private String beschreibung;

	/**
	 * ID der Studie zu der das StrataBean gehoert
	 */
	private long studienID = NullKonstanten.NULL_LONG;

	/**
	 * Studie zu der das StrataBean gehoert
	 */
	private StudieBean studie = null;

	/**
	 * Die Liste der moeglichen Auspraegungen zu diesem Strata.
	 */
	private Vector<StrataAuspraegungBean> auspraegungen;

	/**
	 * Das Null-Objekt zum Strata-Bean.
	 */
	public static final StrataBean NULL = new StrataBean();

	/**
	 * Default-Konstruktor. Erzeugt ein Null-Objekt.
	 * 
	 */
	public StrataBean() {
	}

	/**
	 * Erzeugt ein StrataBean, ohne anzugeben.
	 * 
	 * @param id
	 *            ID
	 * @param studienID
	 *            ID der Studie zur der das Bean gehoert
	 * @param name
	 *            Der Name des Stratas. Darf nicht leer oder <code>null</code>
	 *            sein.
	 * @param beschreibung
	 *            Eine Beschreibung die angibt, wie die Strata-Auspraegung eines
	 *            Patienten ermittelt werden sollen und evtl. Bezugspunkte (z.B.
	 *            Alter am 1.1.2007) angibt. v
	 * @throws StrataException -
	 *             bei augetretenen Fehlern
	 * @throws DatenbankExceptions -
	 *             bei einer nicht korrekten Id
	 */
	public StrataBean(long id, long studienID, String name, String beschreibung)
			throws StrataException, DatenbankExceptions {
		super.setId(id);
		this.setStudienID(studienID);
		this.setName(name);
		this.setBeschreibung(beschreibung);
	}

	/**
	 * Erzeugt ein StrataBean, ohne anzugeben.
	 * 
	 * @param id
	 *            ID
	 * @param studienID
	 *            ID der Studie zur der das Bean gehoert
	 * @param name
	 *            Der Name des Stratas. Darf nicht leer oder <code>null</code>
	 *            sein.
	 * @param beschreibung
	 *            Eine Beschreibung die angibt, wie die Strata-Auspraegung eines
	 *            Patienten ermittelt werden sollen und evtl. Bezugspunkte (z.B.
	 *            Alter am 1.1.2007) angibt. Darf ggf. auch leer bzw.
	 *            <code>null</code> sein.
	 * @param auspraegungen
	 *            Die Liste der moeglichen Auspraegungen. Darf nicht leer sein.
	 * @throws StrataException -
	 *             bei augetretenen Fehlern
	 * @throws DatenbankExceptions -
	 *             bei einer nicht korrekten Id
	 */
	public StrataBean(long id, long studienID, String name,
			String beschreibung, Collection<StrataAuspraegungBean> auspraegungen)
			throws StrataException, DatenbankExceptions {
		this(id, studienID, name, beschreibung);
		this.setAuspraegungen(auspraegungen);
	}

	/**
	 * Liefert einen String der alle Parameter formatiert enthaelt.
	 * 
	 * @return String der alle Parameter formatiert enthaelt.
	 * @throws DatenbankExceptions
	 * @see java.lang.Object#toString()
	 * 
	 */
	@Override
	public String toString() {
		String s = "";
		s += "Klasse: " + this.getClass().toString() + "\n";
		s += "\t Name:\t" + this.name + "\n";
		s += "\t Beschreibung:\t" + this.beschreibung + "\n";
		try {
			for (StrataAuspraegungBean sA : this.getAuspraegungen()) {
				s += "\t" + sA.toString();
			}
		} catch (DatenbankExceptions e) {
			Logger.getLogger(this.getClass()).debug("Fehler in toString", e);
		}
		return s;
	}

	/**
	 * Gibt die moeglichen Auspraegungen eines Stratas zurueck.
	 * 
	 * @return Die moeglichen Auspraegungen.
	 * @throws DatenbankExceptions
	 *             Wenn ein Fehler in der Datenbank auftritt.
	 */
	public Collection<StrataAuspraegungBean> getAuspraegungen()
			throws DatenbankExceptions {
		if (auspraegungen == null) {
			this.auspraegungen = new Vector<StrataAuspraegungBean>(Strata
					.getAuspraegungen(this));
		}
		return this.auspraegungen;
	}

	/**
	 * Setzt die Auspraegungen.
	 * 
	 * @param auspraegungen
	 *            Die Liste der moeglichen Auspraegungen. Darf nicht leer sein.
	 * @throws StrataException
	 *             Falls die Liste leer ist, mit
	 *             {@link StrataException#STRATA_AUSPRAEGUNGEN_LEER}.
	 */
	public void setAuspraegungen(Collection<StrataAuspraegungBean> auspraegungen)
			throws StrataException {
		if (auspraegungen == null || auspraegungen.isEmpty()) {
			throw new StrataException(StrataException.STRATA_AUSPRAEGUNGEN_LEER);
		}
		this.auspraegungen = new Vector<StrataAuspraegungBean>(auspraegungen);

		for (StrataAuspraegungBean sA : this.auspraegungen) {
			sA.setStrata(this);
		}
	}

	/**
	 * Gibt die Beschreibung zurueck.
	 * 
	 * @return Eine Beschreibung die angibt, wie die Strata-Auspraegung eines
	 *         Patienten ermittelt werden sollen und evtl. Bezugspunkte (z.B.
	 *         Alter am 1.1.2007) angibt. Kann ggf. auch <code>null</code>
	 *         sein.
	 */
	public String getBeschreibung() {
		return beschreibung;
	}

	/**
	 * Setzt die Beschreibung.
	 * 
	 * @param beschreibung
	 *            Eine Beschreibung die angibt, wie die Strata-Auspraegung eines
	 *            Patienten ermittelt werden sollen und evtl. Bezugspunkte (z.B.
	 *            Alter am 1.1.2007) angibt. Darf ggf. auch leer bzw.
	 *            <code>null</code> sein.
	 */
	public void setBeschreibung(String beschreibung) {
		// Beschreibung darf leer sein.
		// Wenn sie leer ist, auf null setzen.
		if (beschreibung != null && beschreibung.trim().equals("")) {
			beschreibung = null;
		}
		this.beschreibung = beschreibung;
	}

	/**
	 * Liefert die zugehoerige Studie
	 * 
	 * @return zugehoerige Studie
	 * @throws DatenbankExceptions
	 *             Falls beim Suchen in der DB ein Fehler auftritt.
	 */
	public StudieBean getStudie() throws DatenbankExceptions {
		if (this.studie == null) {
			this.studie = Studie.getStudie(this.studienID);
		}
		return studie;
	}

	/**
	 * Set Methode fuer Studie Attribut der Klasse
	 * 
	 * @param studie
	 *            zu setzende Studie
	 * @throws StrataException
	 *             Falls das uebergebene Bean <code>null</code> ist
	 *             {@link StrataException#STUDIE_NULL}
	 */
	public void setStudie(StudieBean studie) throws StrataException {
		if (studie == null) {
			throw new StrataException(StrataException.STUDIE_NULL);
		}
		this.studienID = studie.getId();
		this.studie = studie;
	}

	/**
	 * Liefert die ID der zugehoerigen Studie
	 * 
	 * @return id der Studie
	 */
	public long getStudienID() {
		return studienID;
	}

	/**
	 * Set Methode fuer die StudienID
	 * 
	 * @param studienID
	 *            zu setzende Id
	 * @throws StrataException
	 *             Falls die Studie noch nicht der Datenbank gespeichert ist.
	 */
	public void setStudienID(long studienID) throws StrataException {
		if (studienID == NullKonstanten.DUMMY_ID) {
			throw new StrataException(
					StrataException.STRATA_BEAN_NICHT_GESPEICHERT);
		}
		this.studienID = studienID;
	}

	/**
	 * Gibt den Namen zurueck.
	 * 
	 * @return Der Name des Stratas.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt den Namen des Stratas.
	 * 
	 * @param name
	 *            Der Name des Stratas. Darf nicht leer oder <code>null</code>
	 *            sein.
	 * @throws StrataException
	 *             Falls der Name leer oder <code>null</code> ist, mit
	 *             {@link StrataException#STRATA_NAME_LEER}.
	 */
	public void setName(String name) throws StrataException {
		if (!super.isFilter()) {
			if (name == null || name.trim().equals("")) {
				throw new StrataException(StrataException.STRATA_NAME_LEER);
			}
		}

		this.name = name.trim();
	}

	/**
	 * Prueft ob alle Pflichtfelder besetzt sind.
	 * 
	 * @throws StrataException
	 *             Falls dem nicht so ist.
	 */
	@Override
	public void validate() throws StrataException {
		if (this.auspraegungen == null) {
			throw new StrataException(StrataException.STRATA_AUSPRAEGUNGEN_LEER);
		}
		if (this.name == null) {
			throw new StrataException(StrataException.STRATA_NAME_LEER);
		}

	}

	/**
	 * Vergleicht zwei StrataBeans anhand der Ids in der Datenbank.
	 * 
	 * @param o
	 *            Das StrataBean mit dem verglichen werden soll.
	 * @return Einen int gemaess {@link Comparable#compareTo(Object)}};
	 */
	public int compareTo(StrataBean o) {
		if (this.getId() < o.getId()) {
			return -1;
		} else if (this.getId() > o.getId()) {
			return 1;
		} else {
			return 0;
		}
	}

}
