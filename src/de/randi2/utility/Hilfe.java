package de.randi2.utility;

import de.randi2.model.exceptions.HilfeException;
import de.randi2.model.exceptions.RandomisationsException;



/**
 * 
 * Die Klasse Hilfe liefert die Inhalte der einzelnen Hilfe-Unterseiten.
 * 
 * @author Daniel Haehn [dhaehn@stud.hs-heilbronn.de]
 * @version $Id$
 *
 */
public class Hilfe {
	
	public static enum Unterseiten {

		/**
		 * Blockrandomisation mit Strata
		 */
		STUDIE_AUSWAEHLEN("Hier kann die Studie ausgew&auml;hlt werden!<br><br>Unter allen zur Verf&uuml;gung stehenden Studien kann gefiltert werden.");

		/**
		 * Der Algorithmus als String.
		 */
		private String unterseite = null;

		/**
		 * Weist den String dem tatsaechlichen Algorithmus zu.
		 * 
		 * @param algorithmus
		 *            Der Parameter enthaelt den Algorithmus-String.
		 */
		private Unterseiten(String unterseite) {
			this.unterseite = unterseite;
		}

		/**
		 * Gibt den Algorithmus als String zurueck.
		 * 
		 * @return der Algorithmus
		 */
		@Override
		public String toString() {
			return this.unterseite;
		}

		/**
		 * Ueberfuehrt einen String in das entsprechende Algorithmus-Element
		 * 
		 * @param algorithmus
		 *            String Repraesentation eines Algorithmuses
		 * 
		 * @return Algorithmusrepraesentation in Form des Enumelementes
		 * @throws RandomisationsException
		 *             Falls ein ungueltiger Algorithmus gewaehlt wurde.
		 * 
		 */
		public static Unterseiten parseUnterseite(String unterseite)
				throws HilfeException {

			for (Unterseiten aUnterseite : Unterseiten.values()) {
				if (unterseite.equals(aUnterseite.toString())) {
					return aUnterseite;
				}

			}

			throw new HilfeException(
					HilfeException.UNTERSEITE_FEHLT);
		}

	}
	
	
}