package de.randi2.datenbank;

/**
 * <p>
 * <b>FACTORY PATTERN</b><br>
 * Diese Klasse kuemmert sich um die Erzeugung der Objekte von der
 * Datenbankklasse. Sie ist immer anzusprechen, wenn eine Datenbank Instanz
 * gebraucht wird.
 * </p>
 * 
 * @author Lukasz Plotnicki [lplotni@stud.hs-heilbronn.de]
 * @version $Id$
 */
public final class DatenbankFactory {

	/**
	 * Das ist eine akutelle Instanz von der DatenbankFactory Klasse. ( <b>SINGELTON PATTERN</b> )
	 */
	private static DatenbankFactory aDBFactory = null;

	/**
	 * Die Instanz von der Datenbank Klasse ( <b>SINGELTON PATTERN</b> )
	 */
	private static DatenbankSchnittstelle aDB = null;

	/**
	 * Diese Methode ist aufzurufen, wenn ein Objekt von dieser Klasse (DatenbankFactory) benoetigt wird. 
	 * @return DatenbankFactory Es wird immer die aktuellste Instanz dieser Klasse zurueckgeliefert.
	 */
	public static DatenbankFactory getInstanz() {
		if (aDBFactory == null) {
			aDBFactory = new DatenbankFactory();
		}
		return aDBFactory;
	}

	/**
	 * Konstruktor von dieser Klasse.
	 */
	private DatenbankFactory() {

	}

	/**
	 * Diese Methode ist aufzurufen, wenn eine Datenbank Instanz benoetigt wird.
	 * @return DatenbankSchnittstelle  Es wird immer die aktuellste Instanz zureuckgeliefert.
	 */
	public static DatenbankSchnittstelle getAktuelleDBInstanz() {
		if (aDB == null) {
			aDB = new Datenbank();
		}
		return aDB;
	}
}
