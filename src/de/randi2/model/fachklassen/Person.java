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
 * @version $Id: Person.java 2426 2007-05-06 17:34:32Z twillert $
 * 
 */
public final class Person {

	/**
	 * Ein privater Konstruktor - somit kann diese Klasse nicht initialisiert
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
	 * @throws DatenbankFehlerException -
	 *             falls das PersonBean Objekt zu der uebergebenen Id nicht
	 *             existiert.
	 */
	public static PersonBean get(long id) throws DatenbankFehlerException {
		PersonBean nullBean = new PersonBean();
		nullBean.setFilter(true);
		return DatenbankFactory.getAktuelleDBInstanz().suchenObjektId(id,
				nullBean);
	}

	/**
	 * Diese Methode liefert die gesuchten PersonBeans, falls es Objekte mit
	 * gesuchten Eigenschaften gibt.
	 * 
	 * @param gesuchtesBean -
	 *            ein PersonBean, in dem die gesuchten Eigenschaften gesetzt
	 *            sind und alle andere NULL Werte enthalten.
	 * @return ein Vector mit gefundenen PersonBeans.
	 * @throws DatenbankFehlerException
	 *             falls Fehler bei dem Vorgang auftraten.
	 */
	public static Vector<PersonBean> suchenPerson(PersonBean gesuchtesBean)
			throws DatenbankFehlerException {
		return DatenbankFactory.getAktuelleDBInstanz().suchenObjekt(
				gesuchtesBean);
	}
}
