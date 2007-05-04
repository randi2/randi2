package de.randi2.model.fachklassen;

import java.util.Vector;

import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.fachklassen.beans.PersonBean;

/**
 * <p>
 * Person Fachklasse.
 * </p>
 * 
 * @author Lukasz Plotnicki [lplotni@stud.hs-heilbronn.de]
 * 
 * @version $Id$
 * 
 */
public final class Person {

	/**
	 * Ein privater Konstruktor - somit kann diese Kalsse nicht initialisiert
	 * werden.
	 */
	private Person() {

	}

	/**
	 * Diese Methode liefert das gewuenschte Objekt, das die uebergebene
	 * eindeutige Id hat.
	 * 
	 * @param id
	 *            die eindeutige Id des gewuenschten Objektes.
	 * @return PersonBean - das gewuenschte Objekt.
	 * @throws PersonException -
	 *             falls das PersonBean Objekt zu der uebergebenen Id nicht
	 *             existiert.
	 */
	public static PersonBean get(long id) throws PersonException {
		PersonBean nullBean = new PersonBean();
		nullBean.setFilter(true);
		try {
			return DatenbankFactory.getAktuelleDBInstanz().suchenObjektId(id,
					nullBean);
		} catch (DatenbankFehlerException e) {
			throw new PersonException(PersonException.PERSON_NICHT_GEFUNDEN);
		}
	}

	/**
	 * Diese Methode liefert die gesuchten PersonBeans, falls es Objekte mit
	 * gesuchten Eigenschaften gibt.
	 * 
	 * @param gesuchtesBean -
	 *            ein PersonBean, in dem die gesuchten Eigenschaften gesetzt
	 *            sind und alle andere NULL Werte enthalten.
	 * @return ein Vector mit gefundenen PersonBeans.
	 * @throws PersonException
	 *             falls Fehler bei dem Vorgang auftraten.
	 */
	public static Vector<PersonBean> suchen(PersonBean gesuchtesBean)
			throws PersonException {
		try {
			return DatenbankFactory.getAktuelleDBInstanz().suchenObjekt(
					gesuchtesBean);
		} catch (DatenbankFehlerException e) {
			throw new PersonException(PersonException.DB_FEHLER);
		}
	}
}
