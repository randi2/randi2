package de.randi2.model.fachklassen.beans;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import de.randi2.datenbank.Filter;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.StrataException;

/**
 * Die Klasse StrataBean kapselt die Eigenschaft eines Stratas. Sie kann - wenn
 * benoetigt - auch die aktuelle Auspraegung eines Stratas repraentieren.
 * 
 * @author Johannes Thoenes [jthoenes@stud.hs-heilbronn.de]
 * @author Daniel Haehn [dhaehn@stud.hs-heilbronn.de]
 * @version $Id$
 * 
 */
public class StrataBean extends Filter {

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
	 * Die Liste der moeglichen Auspraegungen zu diesem Strata.
	 */
	private SortedSet<StrataAuspraegungBean> auspraegungen;

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
	// FRAGE Wie sinnvoll ist dieser Konstruktor?
	public StrataBean(long id, String name, String beschreibung)
			throws StrataException, DatenbankExceptions {
		super.setId(id);
		this.setName(name);
		this.setBeschreibung(beschreibung);
	}

	/**
	 * Erzeugt ein StrataBean, ohne anzugeben.
	 * 
	 * @param id
	 *            ID
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
	public StrataBean(long id, String name, String beschreibung,
			Collection<StrataAuspraegungBean> auspraegungen)
			throws StrataException, DatenbankExceptions {
		this(id, name, beschreibung);
		this.setAuspraegungen(auspraegungen);
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
		String s = "";
		s += "Klasse: " + this.getClass().toString() + "\n";
		s += "\t Name:\t" + this.name + "\n";
		s += "\t Beschreibung:\t" + this.beschreibung + "\n";
		for (StrataAuspraegungBean sA : this.getAuspraegungen()) {
			s += "\t" + sA.toString();
		}
		return s;
	}

	/**
	 * Gibt die moeglichen Auspraegungen eines Stratas zurueck.
	 * 
	 * @return Die moeglichen Auspraegungen.
	 */
	public Collection<StrataAuspraegungBean> getAuspraegungen() {
		// FIXME Klaeren ob an dieser Stelle Lazy-Loading sinnvoll ist.
		if (auspraegungen == null) {
			return new Vector<StrataAuspraegungBean>();
		} else {
			return auspraegungen;
		}
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
		this.auspraegungen = new TreeSet<StrataAuspraegungBean>(auspraegungen);

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

}
