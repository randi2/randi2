package de.randi2.model.fachklassen;

import java.util.Vector;

import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.exceptions.ZentrumException;
import de.randi2.model.fachklassen.beans.ZentrumBean;
import de.randi2.utility.KryptoUtil;

/**
 * Die Klasse Zentrum beinhaltet das ZentrumBean und zugehoerige Methoden.
 * 
 * @version $Id: Zentrum.java 2426 2007-05-06 17:34:32Z twillert $
 * @author Andreas Freudling [afreudling@stud.hs-heilbronn.de]
 * 
 */
public class Zentrum {

	/**
	 * Das zugehoerige ZentrumBean-Objekt.
	 */
	private ZentrumBean aZentrumBean;

	/**
	 * Ein Konstruktor dieser Klasse.
	 * 
	 * @param aZentrum
	 *            das zugeoehrige ZentrumBean
	 */
	public Zentrum(ZentrumBean aZentrum) {
		this.aZentrumBean = aZentrum;
	}

	/**
	 * Diese statische Methode sucht die gewuenschten Objekte und falls sie
	 * vorhanden sind, liefert sie zurueck.
	 * 
	 * @param sZentrum
	 *            ein ZentrumBean mit gesuchten Eigenschaften (alle irrelevante
	 *            Felder entsprechen den Null-Werten aus der
	 *            de.randi2.utility.NullKonstanten Klasse)
	 * @return ein Vector mit gefundenen Objekten
	 * @throws DatenbankFehlerException
	 *             Falls ein Fehler in der Datenbank auftritt.
	 */
	public static Vector<ZentrumBean> suchenZentrum(ZentrumBean sZentrum)
			throws DatenbankFehlerException {

		Vector<ZentrumBean> gefundeneZentren = new Vector<ZentrumBean>();
		gefundeneZentren = DatenbankFactory.getAktuelleDBInstanz()
				.suchenObjekt(sZentrum);

		return gefundeneZentren;
	}

	/**
	 * Diese Methode prueft, ob das uebergebene Passwort (KLARTEXT) richtig ist.
	 * 
	 * @param passwort
	 *            Das Passwort (im KLARTEXT), das ueberprueft werden soll.
	 * @return true, wenn das Passwort richtig ist. False, bei falchem Passwort.
	 */
	public boolean pruefenPasswort(String passwort) {
		if (passwort!=null&&KryptoUtil.getInstance().hashPasswort(passwort).equals(
				this.getZentrumBean().getPasswort())) {
			return true;
		}
		return false;
	}

	/**
	 * Eine typische get() Methode
	 * 
	 * @return ZentrumBean - das aktuelle ZentrumBean, das Daten dieses Zentrums
	 *         enthaelt.
	 */
	public ZentrumBean getZentrumBean() {
		return aZentrumBean;
	}

	/**
	 * Die Methode soll das ZentrumBean Objekt zurueckgeben.
	 * 
	 * @param zentrumId
	 *            Die ID des angeforderten Zentrums.
	 * @return ZentrumBean Ein ZentrumBean wird zurueckgegeben.
	 * @throws DatenbankFehlerException
	 *             Die Exception tritt auf, wenn kein Zentrum zur ID gefunden
	 *             wurde.
	 */
	public static ZentrumBean getZentrum(long zentrumId)
			throws DatenbankFehlerException {
		ZentrumBean aBean = new ZentrumBean();
		aBean.setFilter(true);
		return DatenbankFactory.getAktuelleDBInstanz().suchenObjektId(
				zentrumId, aBean);
	}

	/**
	 * Erzeugt einen String mit allen Daten des Benutzers.
	 * 
	 * @return Der String mit allen Daten des Benutzers.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.aZentrumBean.toString();
	}

	/**
	 * Diese Methode dient zum Verlgeich von 2 Objekten dieser Klasse.
	 * 
	 * @param zuvergleichendesObjekt
	 *            Objekt mit dem verglichen werden soll.
	 * @return true - wenn die beiden Objekte identisch sind, false wenn das
	 *         nicht der Fall ist.
	 */
	public boolean equals(Zentrum zuvergleichendesObjekt) {
		if (this.aZentrumBean.equals(zuvergleichendesObjekt.getZentrumBean())) {
			return true;
		}
		return false;
	}

}
