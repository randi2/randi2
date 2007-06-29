package de.randi2.model.fachklassen.beans;

import de.randi2.datenbank.Filter;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.StrataException;
import de.randi2.model.fachklassen.Strata;
import de.randi2.utility.NullKonstanten;

/**
 * Ein Objekt dieser Klasse stellt eine Auspraegung eines Stratas dar.
 * 
 * @author Johannes Thoenes [jthoenes@stud.hs-heilbronn.de]
 * @version $Id$
 * @see StrataBean
 * 
 */
public class StrataAuspraegungBean extends Filter implements
		Comparable<StrataAuspraegungBean> {

	/**
	 * Der Name der Strata Auspraegung.
	 */
	private String name;

	/**
	 * Das Strata zu dem die Auspraegung gehoert.
	 */
	private StrataBean strata;

	/**
	 * ID des zugehoerigen StrataBeans
	 */
	private long strataID = NullKonstanten.NULL_LONG;

	/**
	 * Das Null-Objekt zum Strata-Bean.
	 */
	public static final StrataAuspraegungBean NULL = new StrataAuspraegungBean();

	/**
	 * Leerkonstruktor.
	 * 
	 */
	public StrataAuspraegungBean() {
	}

	/**
	 * Konstrukor.
	 * 
	 * @param name
	 *            Der Name der Strata Auspraegung.
	 * @param id
	 *            ID des in der Datenbank.
	 * @param strataID
	 *            ID des zugehoerigen Stratabeans
	 * @throws StrataException
	 *             Falls der Name der Auspraegung leer ist, mit
	 *             {@link StrataException#STRATA_AUSPRAEGUNG_NAME_LEER}.
	 * @throws DatenbankExceptions
	 *             Falls ein Problem in der Datenbank auftritt.
	 */
	public StrataAuspraegungBean(long id, long strataID, String name)
			throws StrataException, DatenbankExceptions {
		this.strataID=strataID;
		this.setId(id);
		this.setName(name);
	}

	/**
	 * Konstrukor.
	 * 
	 * @param id
	 *            ID des in der Datenbank-
	 * @param name
	 *            Der Name der Strata Auspraegung.
	 * @param strata
	 *            Das Strata zu dem die Auspraegung gehoert.
	 * @throws StrataException
	 *             Falls der Name der Auspraegung leer ist, mit
	 *             {@link StrataException#STRATA_AUSPRAEGUNG_NAME_LEER}.
	 * @throws DatenbankExceptions
	 *             Falls ein Problem in der Datenbank auftritt.
	 */
	public StrataAuspraegungBean(long id,  String name,
			StrataBean strata) throws StrataException, DatenbankExceptions {
		this(id, strata.getId(), name);
		this.setStrata(strata);
	}

	/**
	 * Gibt den Namen zurueck.
	 * 
	 * @return Der Name der Strata Auspraegung.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt den Namen.
	 * 
	 * @param name
	 *            Der Name der Strata Auspraegung.
	 * @throws StrataException
	 *             Falls der Name der Auspraegung leer ist, mit
	 *             {@link StrataException#STRATA_AUSPRAEGUNG_NAME_LEER}.
	 */
	public void setName(String name) throws StrataException {
		if (!this.isFilter()) {
			if (name == null || name.trim().equals("")) {
				throw new StrataException(
						StrataException.STRATA_AUSPRAEGUNG_NAME_LEER);
			}
		}

		this.name = name.trim();
	}

	/**
	 * Gibt das Strata zurueck.
	 * 
	 * @return Das Strata zu dem die Auspraegung gehoert.
	 * @throws DatenbankExceptions
	 *             Falls beim suchen in der DB Fehler auftraten.
	 */
	public StrataBean getStrata() throws DatenbankExceptions {
		if (this.strata == null) {
			this.strata = Strata.get(this.strataID);
		}
		return strata;
	}

	/**
	 * Setzt das Strata.
	 * 
	 * @param strata
	 *            Das Strata zu dem die Auspraegung gehoert.
	 * @throws StrataException
	 *             Falls das uebergebene Bean <code>null</code> ist
	 */
	public void setStrata(StrataBean strata) throws StrataException {
		if (strata == null) {
			throw new StrataException(StrataException.STRATA_NULL);
		}
		this.strataID = strata.getId();
		this.strata = strata;
	}

	/**
	 * Liefert die ID des zugehoerigen StrataBeans
	 * 
	 * @return ID
	 */
	public long getStrataID() {
		return strataID;
	}

	/**
	 * Set-Methode fuer ID des zugehoerigen StrataBeans
	 * 
	 * @param strataID
	 *            besagte ID
	 * @throws StrataException
	 *             Falls die ID der Dummy ID entspricht
	 */
	public void setStrataID(long strataID) throws StrataException {
		if (strataID == NullKonstanten.DUMMY_ID) {
			throw new StrataException(
					StrataException.STRATA_BEAN_NICHT_GESPEICHERT);
		}
		this.strataID = strataID;
	}

	/**
	 * Vergleicht zwei Strata Auspraegungen anhand der Strata-Ids. Strata
	 * Auspraegungen aus dem gleichen Strata werden nicht unterschieden.
	 * 
	 * @param o
	 *            Die zu vergleichende Auspraegung.
	 * @return Einen int-Wert gemaess {@link Comparable#compareTo(Object)}.
	 */
	public int compareTo(StrataAuspraegungBean o) {
		if (strata.getId() < o.strata.getId()) {
			return -1;
		} else if (strata.getId() > o.strata.getId()) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * Erzeugt einen String fuer Debug-Zwecke.
	 * 
	 * @return Einen String mit allen Daten des Objekts.
	 */
	public String toString() {
		String s = "";
		s += "Klasse: " + this.getClass().toString() + "\n";
		s += "\t\t Name: " + this.name + "\n";
		return s;
	}

	/**
	 * Prueft ob alle Pflichtfelder besetzt sind.
	 * 
	 * @throws StrataException
	 *             Falls dem nicht so ist.
	 */
	@Override
	public void validate() throws StrataException {
		if (this.name == null) {
			throw new StrataException(
					StrataException.STRATA_AUSPRAEGUNG_NAME_LEER);
		}

	}
}
