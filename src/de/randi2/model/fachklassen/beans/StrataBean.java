package de.randi2.model.fachklassen.beans;

import java.util.HashMap;
import java.util.List;

import de.randi2.datenbank.Filter;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.StrataException;
import de.randi2.utility.CollectionUtil;
import de.randi2.utility.NullKonstanten;

/**
 * Die Klasse StrataBean kapselt die Eigenschaft eines Stratas. Sie kann - wenn
 * benoetigt - auch die aktuelle Auspraegung eines Stratas repraentieren.
 * 
 * @author Johannes Thoenes [jthoenes@stud.hs-heilbronn.de]
 * @author Daniel Haehn [dhaehn@stud.hs-heilbronn.de]
 * @version $Id: StrataBean.java 2406 2007-05-04 13:25:10Z kkrupka $
 * 
 */
public class StrataBean extends Filter {

	private String name;

	private String beschreibung;

	private List<StrataAuspraegungBean> auspraegungen;

	/**
	 * Default-Konstruktor. Erzeugt ein Null-Objekt.
	 * 
	 */
	public StrataBean() {
	}

	/**
	 * Erzeugt ein StrataBean, ohne eine aktuelle Auspraegung mit anzugeben.
	 * 
	 * @param id
	 *            ID
	 * @param moeglicheAuspraegungen
	 *            Die moeglichen Auspraegungen eines Stratas als HashMap. Der
	 *            Key-Parameter stellt dabei die id des Stratas auf der
	 *            Datenbank als {@link Long} dar. Der value-Paramater den
	 *            geschriebenen Wert des Stratas als {@link String}.
	 * @throws StrataException -
	 *             bei augetretenen Fehlern
	 * @throws DatenbankExceptions -
	 *             bei einer nicht korrekten Id
	 */
	public StrataBean(long id, String name, String beschreibung)
			throws StrataException, DatenbankExceptions {
		super.setId(id);
		this.name = name;
		this.beschreibung = beschreibung;
	}

	/**
	 * ERzeugt ein Strata, mit einer aktuellen Auspraegung.
	 * 
	 * @param id -
	 *            die Id des Beans
	 * @param moeglicheAuspraegungen
	 *            Die moeglichen Auspraegungen eines Stratas als HashMap. Der
	 *            Key-Parameter stellt dabei die id des Stratas auf der
	 *            Datenbank als {@link Long} dar. Der value-Paramater den
	 *            geschriebenen Wert des Stratas als {@link String}.
	 * @param aAuspragungId
	 *            Die id des Stratas als long.
	 * @throws StrataException -
	 *             bei augetretenen Fehlern
	 * @throws DatenbankExceptions -
	 *             bei einer nicht korrekten Id
	 */
	public StrataBean(long id, String name, String beschreibung,
			List<StrataAuspraegungBean> auspraegung) throws StrataException,
			DatenbankExceptions {
		this(id, name, beschreibung);
		
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
		// FIXME implementieren
		return null;
	}

	/**
	 * Diese Methode prueft, ob zwei Kontos identisch sind. Zwei Kontos sind
	 * identisch, wenn Benutzernamen identisch sind.
	 * 
	 * @param zuvergleichendesObjekt
	 *            das zu vergleichende Objekt vom selben Typ
	 * @return <code>true</code>, wenn beide Kontos gleich sind, ansonstenm
	 *         <code>false</code>
	 */
	@Override
	public boolean equals(Object zuvergleichendesObjekt) {
		if (zuvergleichendesObjekt instanceof StrataBean) {
			StrataBean beanZuvergleichen = (StrataBean) zuvergleichendesObjekt;
			// FIXME Ausimplementieren
			return true;

		}
		return false;
	}

	public List<StrataAuspraegungBean> getAuspraegungen() {
		return auspraegungen;
	}

	public void setAuspraegungen(List<StrataAuspraegungBean> auspraegungen) {
		this.auspraegungen = auspraegungen;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
