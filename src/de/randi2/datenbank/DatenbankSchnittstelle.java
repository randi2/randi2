package de.randi2.datenbank;

import java.util.Vector;

import de.randi2.datenbank.exceptions.DatenbankFehlerException;

/**
 * Diese Klasse repraesentiert eine Schnittstelle fuer den Datenbankzugriff.
 * Methoden zum Suchen von Daten sowie zum Schreiben von Daten sind vorhanden.
 * 
 * @version 0.1
 * @author Daniel Haehn <dhaehn@stud.hs-heilbronn.de>
 */
public interface DatenbankSchnittstelle {

	/**
	 * <p>
	 * Diese Methode sucht alle in der Datenbank gespeicherten Objekte, welche
	 * der Filterung, die durch das uebergebene Objekt definiert wurde,
	 * entsprechen. Dabei werden alle Attribute welche != null bzw. !=
	 * null-Konstanten (siehe de.randi2.utility.NullKonstanten) sind, als Filter
	 * verwendet.
	 * </p>
	 * <p>
	 * Falls keine Objekte der Filterung entsprechen, wird ein leerer Vektor
	 * zurueckgegeben.
	 * </p>
	 * 
	 * @param zuSuchendesObjekt
	 *            das zusuchende Objekt mit Attributen als Filter
	 * @return ein Vektor von auf die Filterung zutreffenden Objekten
	 * @throws DatenbankFehlerException
	 *             falls ein Fehler beim Datenbankzugriff aufgetreten ist
	 */
	public Vector<Object> suchenObjekt(Object zuSuchendesObjekt)
			throws DatenbankFehlerException;

	/**
	 * <p>
	 * Diese Methode schreibt das uebergebene Objekt mit allen Attributen in die
	 * Datenbank und liefert bei Erfolg das geschriebene Objekt zurueck, bei
	 * welchem dann das Feld id gesetzt wurde.
	 * </p>
	 * 
	 * 
	 * @param zuSchreibendesObjekt
	 *            das zuschreibende Objekt
	 * @return das geschriebene Objekt, bei welchem das Feld id gesetzt wurde
	 * @throws DatenbankFehlerException
	 *             falls ein Fehler beim Datenbankzugriff aufgetreten ist
	 */
	public Object schreibenObjekt(Object zuSchreibendesObjekt)
			throws DatenbankFehlerException;
    
}
