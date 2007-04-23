package de.randi2.model.fachklassen;

import java.util.Vector;

import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.fachklassen.beans.StudienarmBean;

/**
 * <p>
 * Studienarm Fachklasse. Diese Klasse stellt nur zwei statische Methoden (get
 * und suchen) zur Verfügung.
 * </p>
 * 
 * @author Daniel Haehn [dhaehn@stud.hs-heilbronn.de]
 * @author Lukasz Plotnicki [lplotni@stud.hs-heilbronn.de]
 * 
 * @version $Id$
 * 
 */
public final class Studienarm {

	// TODO wenn der Zustand sich nicht aendert und diese Klasse nur statische
	// Methoden anbietet, sollte man hier private Konstruktor definieren. (lplotni)
	
	/**
	 * Diese Methode liefert nur das gewuenschte Objekt zurueckt.
	 * 
	 * @param id
	 *            ID des gesuchten Studiearms.
	 * @return StudienarmBean - das gewuenschte Objekt.
	 * @throws DatenbankFehlerException
	 *             falls Fehler bei dem Vorgang auftraten.
	 */
	public static StudienarmBean get(int id) throws DatenbankFehlerException {
		StudienarmBean nullBean = new StudienarmBean();
		nullBean.setFilter(true);
		return DatenbankFactory.getAktuelleDBInstanz().suchenObjektID(id,
				nullBean);

	}

	/**
	 * Diese Methode liefert die gesuchten StudienarmeBeans, falls es Objekte
	 * mit gesuchten Eigenschaften gibt.
	 * 
	 * @param gesuchtesBean -
	 *            ein StudienarmBean, in dem die gesuchten Eigenschaften gesetzt
	 *            sind und alle andere NULL Werte enthalten.
	 * @return ein Vector mit gefundenen StudienarmBeans.
	 * @throws DatenbankFehlerException
	 *             falls Fehler bei dem Vorgang auftraten.
	 */
	public static Vector<StudienarmBean> suchen(StudienarmBean gesuchtesBean)
			throws DatenbankFehlerException {
		return DatenbankFactory.getAktuelleDBInstanz().suchenObjekt(
				gesuchtesBean);
	}

}
