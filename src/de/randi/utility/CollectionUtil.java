package de.randi2.utility;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Dies Klasse CollectionUtil bietet Methoden zum einfachren Umgang mit
 * Collections.
 * 
 * @author Johannes Thoenes [jthoenes@stud.hs-heilbronn.de]
 * @version $Id: CollectionUtil.java 2101 2007-04-23 17:19:38Z jthoenes $
 * 
 */
public final class CollectionUtil {

	/**
	 * Privater Konstruktor, damit Instanzen der Klasse verboten sind.
	 * 
	 */
	private CollectionUtil() {
	}

	/**
	 * Sucht den Schluessel zu einem Wert aus einer Hash-Map und gibt diesen
	 * zurueck.
	 * 
	 * @param <T>
	 *            Der Typ der Key-Objekte.
	 * @param <U>
	 *            Der Typ der Value-Objekte.
	 * @param hashMap
	 *            Die Hash-Map.
	 * @param value
	 *            Der Wert dessen Schluessel gesucht werden soll.
	 * @return Der Key des uebergebenen <code>value</code> oder
	 *         <code>null</code> falls kein Schluessel zu dem Wert gefunden
	 *         wurde.
	 */
	public static <T, U> T getKeyFromHashMap(HashMap<T, U> hashMap, U value) {
		Set<T> keys = hashMap.keySet();
		Iterator<T> it = keys.iterator();
		while (it.hasNext()) {
			T key = it.next();
			if (hashMap.get(key).equals(value)) {
				return key;
			}
		}
		return null;
	}

}
