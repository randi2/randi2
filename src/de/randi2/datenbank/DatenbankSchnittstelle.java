package de.randi2.datenbank;

import java.util.Vector;

import de.randi2.datenbank.exceptions.DatenbankFehlerException;

/**
 * Diese Klasse repraesentiert eine Schnittstelle fuer den Datenbankzugriff.
 * Methoden zum Suchen von Daten sowie zum Schreiben von Daten sind vorhanden.
 * 
 * @version $Id$
 * @author Daniel Haehn [dhaehn@stud.hs-heilbronn.de]
 */
public interface DatenbankSchnittstelle {

	/**
	 * <p>
	 * Diese Methode sucht alle in der Datenbank gespeicherten Objekte, welche
	 * der Filterung, die durch das uebergebene Objekt definiert wurde,
	 * entsprechen. Dabei werden alle Attribute welche != null bzw. !=
	 * null-Konstanten (siehe {@link de.randi2.utility.NullKonstanten}) sind,
	 * als Filter verwendet.
	 * </p>
	 * <p>
	 * Falls keine Objekte der Filterung entsprechen, wird ein leerer Vektor
	 * zurueckgegeben.
	 * </p>
	 * 
	 * @param <T>
	 *            Klasse des Attributes, muss von {@link Filter} erben
	 * @param zuSuchendesObjekt
	 *            das zusuchende Objekt mit Attributen als Filter, darf nicht
	 *            <code>null</code> sein.
	 * @return ein Vektor von auf die Filterung zutreffenden Objekten
	 * @throws DatenbankFehlerException
	 *             Folgende Messages sind moeglich:
	 *             <ul>
	 *             <li>DatenbankFehlerException.ARGUMENT_IST_NULL: Versuchter
	 *             Methodenaufruf mit <code>null</code> </li>
	 *             <li>DatenbankFehlerException.SUCHOBJEKT_IST_KEIN_FILTER: Das
	 *             Objekt, welches zum Suchen eingesetzt wurde, war kein Filter
	 *             (vlg. {@link Filter})</li>
	 *             <li>DatenbankFehlerException.CONNECTION_ERR</li>
	 *             </ul>
	 */
	<T extends Filter> Vector<T> suchenObjekt(T zuSuchendesObjekt)
			throws DatenbankFehlerException;

	/**
	 * <p>
	 * Diese Methode schreibt das uebergebene Objekt mit allen Attributen in die
	 * Datenbank und liefert bei Erfolg das geschriebene Objekt zurueck, bei
	 * welchem dann das Feld id gesetzt wurde.
	 * </p>
	 * 
	 * @param <T>
	 *            Klasse des Attributes, muss von {@link Filter} erben
	 * @param zuSchreibendesObjekt
	 *            das zuschreibende Objekt
	 * @return das geschriebene Objekt. Objekt enthaelt jetzt die ID des
	 *         Datensatzes in der Datenbank
	 * @throws DatenbankFehlerException
	 *             Folgende Messages sind moeglich:
	 *             <ul>
	 *             <li>DatenbankFehlerException.ARGUMENT_IST_NULL: Versuchter
	 *             Methodenaufruf mit <code>null</code> </li>
	 *             <li>DatenbankFehlerException.SCHREIBEN_ERR</li>
	 *             <li>DatenbankFehlerException.CONNECTION_ERR</li>
	 *             </ul>
	 */
	<T extends Filter> T schreibenObjekt(T zuSchreibendesObjekt)
			throws DatenbankFehlerException;

	/**
	 * <p>
	 * Diese Methode liefert gezielt ein Objekt dessen ID bekannt ist.
	 * </p>
	 * 
	 * @param <T>
	 *            Klasse des Attributes, muss von {@link Filter} erben
	 * @param id
	 *            Die ID des zu suchenden Objektes.
	 * @param nullObjekt
	 *            Ein Nullobjekt der jeweiligen Klasse.
	 * @return Das gesuchte Objekt.
	 * @throws DatenbankFehlerException
	 *             Folgende Messages sind moeglich:
	 *             <ul>
	 *             <li>DatenbankFehlerException.ARGUMENT_IST_NULL: Versuchter
	 *             Methodenaufruf mit <code>null</code> </li>
	 *             <li>DatenbankFehlerException.ID_NICHT_VORHANDEN</li>
	 *             <li>DatenbankFehlerException.CONNECTION_ERR</li>
	 *             </ul>
	 */
	<T extends Filter> T suchenObjektID(long id, T nullObjekt)
			throws DatenbankFehlerException;

	/**
	 * <p>
	 * Diese Methode liefert zu dem gegebenen <code>vater</code> eine Liste
	 * der zugehoerigen Kinder. Die Methode bietet wahlweise die Moeglichkeit
	 * ueber gesetzte Suchwoerter in dem uebergebenen Kind-Objekt, die Liste der
	 * Kinder einzuschraenken oder durch Verwendung eines Null-Objekts die
	 * gesamte Liste der Kinder auszugeben.
	 * </p>
	 * 
	 * @param <T>
	 *            Der Typ des Kind-Objekts.
	 * @param <U>
	 *            Der Typ des Vater-Objekts.
	 * @param vater
	 *            Vater-Objekt, dessen Mitglieder/Kinder gesucht werden sollen.
	 *            Das Objekt muss auf jeden Fall die eigene ID enthalten, die es
	 *            in der Datenbank identifiziert.
	 * @param kind
	 *            Muster des zu suchenden Kind-Objektes. Gesetzte Attribute
	 *            beschraenken die Liste auf selbige ein. Ein Null-Objekt
	 *            bewirkt die Rueckgabe der gesamten Liste.
	 * @return Die Liste der gefundenen Kinder.
	 * @throws DatenbankFehlerException
	 * 				Folgende Messages sind moeglich:
	 * 				<ul>
	 * 					<li>DatenbankFehlerException.CONNECTION_ERR</li>
	 * 				</ul>
	 */
	<T extends Filter, U extends Filter> Vector<T> suchenMitgliederObjekte(
			U vater, T kind) throws DatenbankFehlerException;

	/**
	 * <p>
	 * Diese Methode loescht das uebergebene Objekt aus der Datenbank.
	 * </p>
	 * 
	 * @param <T>
	 *            Klasse des Attributes, muss von {@link Filter} erben
	 * @param zuLoeschendesObjekt
	 *            Objekt das geloescht werden soll. Die ID des zuloeschenden
	 *            Objekts muss gesetzt sein.
	 * @throws DatenbankFehlerException
	 *             Folgende Messages sind moeglich:
	 *             <ul>
	 *             <li>DatenbankFehlerException.ARGUMENT_IST_NULL: Versuchter
	 *             Methodenaufruf mit <code>null</code> </li>
	 *             <li>DatenbankFehlerException.LOESCHEN_ERR</li>
	 *             <li>DatenbankFehlerException.CONNECTION_ERR</li>
	 *             </ul>
	 */
	<T extends Filter> void loeschenObjekt(T zuLoeschendesObjekt)
			throws DatenbankFehlerException;

}
