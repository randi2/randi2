package de.randi2.model.fachklassen;

import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import de.randi2.datenbank.DatenbankDummy;
import de.randi2.datenbank.DatenbankSchnittstelle;
import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.exceptions.BenutzerNichtVorhandenException;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;

/**
 * Diese Klasse bietet Methoden zur Verwaltung eines bestimmten Benutzerkontos
 * an. Beinhaltet auch es.
 * 
 * @version $Id$
 * @author Lukasz Plotnicki <lplotni@stud.hs-heilbronn.de>
 * @author Frederik Reifschneider <Reifschneider@stud.hs-heidelberg.de>
 * 
 * 
 */
public class Benutzerkonto {
	
	/**
	 * Das zugehörige BenutzerkontoBean-Objekt.
	 */
	private BenutzerkontoBean aBenutzerkonto;

	/**
	 * Ein Konstruktor dieser Klasse.
	 * 
	 * @param aBenutzerkonto
	 *            das zugeöhrige BenutzerkontoBean
	 */
	public Benutzerkonto(BenutzerkontoBean aBenutzerkonto) {
		this.aBenutzerkonto = aBenutzerkonto;
	}

	/**
	 * Diese statische Methode sucht die gewünschten Objekte und falls sie
	 * vorhanden sind, liefert sie zurück.
	 * 
	 * @param sBenutzerkonto
	 *            ein BenutzerkontoBean mit gesuchten Eigenschaften (alle
	 *            irrelevante Felder entsprechen den Null-Werten aus der
	 *            de.randi2.utility.NullKonstanten Klasse)
	 * @return ein Vector mit gefundenen Objekten
	 */
	public static Vector<BenutzerkontoBean> suchenBenutzer(BenutzerkontoBean sBenutzerkonto) {
		DatenbankSchnittstelle aDB = new DatenbankDummy();
		Vector<BenutzerkontoBean> gefundeneKonten= new Vector<BenutzerkontoBean>();
		Vector<Object> tmpVector = null;
		try {
			tmpVector = aDB.suchenObjekt(sBenutzerkonto);
		} catch (IllegalArgumentException e) {
			Logger.getLogger("de.randi2.model.Benutzerkonto").error("IllegalArgumentException ist aufgetreten.",e);
		} catch (DatenbankFehlerException e) {
			Logger.getLogger("de.randi2.model.Benutzerkonto").warn("Fehler in Datenbank aufgetreten",e);
		}
		//Der Weg über zwei Vektoren ist ein Workaround. Casten von Vektor mit Typ Object nach BenutzerkontoBean
		//geht nicht. Benni wird generic Methods vorstellen die das Problem beheben sollten.
		Iterator it = tmpVector.iterator();
		while(it.hasNext()) {
			gefundeneKonten.add((BenutzerkontoBean) it.next());
		}
		return gefundeneKonten;
	}

	/**
	 * Mit Hilfe dieser Methode, kann ein Benutzerkonto angelegt werden.
	 * Darunter ist zu verstehen, dass das Benutzerkonto gespeichert wird und
	 * die Aktivierungsnachricht an den entsprechenden Benutzer gesendet wird.
	 * 
	 * @param aBenutzerkonto
	 *            das Bentuzerkonto das angelegt werden soll.
	 * @return
	 */
	public static Benutzerkonto anlegenBenutzer(BenutzerkontoBean aBenutzerkonto) {
		DatenbankSchnittstelle aDB = new DatenbankDummy();
		Benutzerkonto aktualisierterBenutzer=null;
		try {
			aktualisierterBenutzer = (Benutzerkonto) aDB.schreibenObjekt(aBenutzerkonto);
		} catch (IllegalArgumentException e) {
			Logger.getLogger("de.randi2.model.Benutzerkonto").error("IllegalArgumentException ist aufgetreten.",e);
		} catch (DatenbankFehlerException e) {
			Logger.getLogger("de.randi2.model.Benutzerkonto").warn("Fehler in Datenbank aufgetreten",e);
		}
		return aktualisierterBenutzer;
	}

	/**
	 * Diese Methode generiert die Aktivierungsnachricht und schickt dem
	 * Benutzer zu.
	 */
	private void sendenAktivierungsMail() {
		//TODO nicht für Release 1?
	}

	/**
	 * Diese statische Methode liefert das Bentutzerkonto Objekt zu dem
	 * eingegebenenem Benutzername.
	 * 
	 * @param benutzername
	 *            String, der dem Benutzername entspricht.
	 * @return Ein BenutzerkontoBean Objekt zu diesem Benutzername
	 * @throws BenutzerNichtVorhandenException
	 *             wenn kein Benutzer mit diesem Banutzername vorhanden ist
	 */
	public static BenutzerkontoBean getBenutzer(String benutzername) throws BenutzerNichtVorhandenException {
		BenutzerkontoBean bk = new BenutzerkontoBean();
		Vector< BenutzerkontoBean> konten;
		bk.setBenutzername(benutzername);
		konten = suchenBenutzer(bk);
		if(konten==null || konten.size()==0) {
			throw new BenutzerNichtVorhandenException(); //TODO Exception Klassen müssen angepasst werden
		}
		return konten.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return null;
	}

	/**
	 * Diese Methode dient zum Verlgeich von 2 Objekten dieser Klasse.
	 * 
	 * @param zuvergleichendesObjekt
	 *            Objekt mit dem verglichen werden soll.
	 * @return true - wenn die beiden Objekte identisch sind, false wenn das
	 *         nicht der Fall ist.
	 */
	public boolean equals(Benutzerkonto zuvergleichendesObjekt) {
		// TODO
		return false;
	}

	/**
	 * Diese Methode prüft, ob das übergebene Passwort richtig ist.
	 * 
	 * @param passwort
	 *            Das Passwort, das überprüft werden soll.
	 * @return true, wenn das Passwort richtig ist. False, bei falchem Passwort.
	 */
	public boolean pruefenPasswort(String passwort) {
		// TODO
		return false;
	}
}
