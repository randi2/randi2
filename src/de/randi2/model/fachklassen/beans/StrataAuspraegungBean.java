package de.randi2.model.fachklassen.beans;

import de.randi2.datenbank.Filter;
import de.randi2.model.exceptions.StrataException;

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
	 * @throws StrataException
	 *             Falls der Name der Auspraegung leer ist, mit
	 *             {@link StrataException#STRATA_AUSPRAEGUNG_NAME_LEER}.
	 */
	public StrataAuspraegungBean(String name) throws StrataException {
		this.setName(name);
	}

	/**
	 * Konstrukor.
	 * 
	 * @param name
	 *            Der Name der Strata Auspraegung.
	 * @param strata
	 *            Das Strata zu dem die Auspraegung gehoert.
	 * @throws StrataException
	 *             Falls der Name der Auspraegung leer ist, mit
	 *             {@link StrataException#STRATA_AUSPRAEGUNG_NAME_LEER}.
	 */
	public StrataAuspraegungBean(String name, StrataBean strata)
			throws StrataException {
		this(name);
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
	 */
	public StrataBean getStrata() {
		return strata;
	}

	/**
	 * Setzt das Strata.
	 * 
	 * @param strata
	 *            Das Strata zu dem die Auspraegung gehoert.
	 */
	public void setStrata(StrataBean strata) {
		this.strata = strata;
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
}
