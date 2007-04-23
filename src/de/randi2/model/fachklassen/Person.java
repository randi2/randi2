package de.randi2.model.fachklassen;

import java.util.Vector;

import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.exceptions.DatenbankFehlerException;
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
public class Person {

	// TODO wenn der Zustand sich nicht aendert und diese Klasse nur statische
	// Methoden anbietet, sollte man hier private Konstruktor definieren. (lplotni)
	
	/**
	 * Diese Methode liefert das gewuenschte Objekt, das die uebergebene
	 * eindeutige Id hat.
	 * 
	 * @param id die eindeutige Id des gewuenschten Objektes.
	 * @return PersonBean - das gewuenschte Objekt.
	 * @throws DatenbankFehlerException falls Fehler auf der Datenbankseite auftraten.
	 */
	public static PersonBean get(long id) throws DatenbankFehlerException {
		PersonBean nullBean = new PersonBean();
		nullBean.setFilter(true);
		return DatenbankFactory.getAktuelleDBInstanz().suchenObjektID(id,
				nullBean);
	}
	
	/**
	 * Diese Methode liefert die gesuchten PersonBeans, falls es Objekte
	 * mit gesuchten Eigenschaften gibt.
	 * 
	 * @param gesuchtesBean -
	 *            ein PersonBean, in dem die gesuchten Eigenschaften gesetzt
	 *            sind und alle andere NULL Werte enthalten.
	 * @return ein Vector mit gefundenen PersonBeans.
	 * @throws DatenbankFehlerException
	 *             falls Fehler bei dem Vorgang auftraten.
	 */
	public static Vector<PersonBean> suchen(PersonBean gesuchtesBean) throws DatenbankFehlerException{
		return DatenbankFactory.getAktuelleDBInstanz().suchenObjekt(
				gesuchtesBean);
	}
}
