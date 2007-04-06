package de.randi2.model.fachklassen;

import java.util.HashMap;

import de.randi2.model.exceptions.RechtException;

/**
 * Unterste Ebene der Rechteverwaltung.<br>
 * Ein Recht, identifiziert anhand seines Namens, entspricht der Berechtigung,
 * genau eine Aktion durchfuehren zu duerfen. <br>
 * <br>
 * Wurde einer Rolle nicht explizit das Recht (fuer eine Aktion) eingeraeumt, so
 * besitzt diese Rolle dieses Recht <b>nicht</b><br>
 * <br>
 * Die Liste der Rechte, die vergeben werden koennen, werden in der
 * <code>enum</code> {@link Rechtenamen} verwaltet und sind somit hart
 * gecodet. <br>
 * Eine Aenderung bzw. Erweiterung der Rechte ist somit ausschliesslich im
 * Quelltext moeglich.
 * 
 * @version $Id$
 * @author Benjamin Theel [btheel@stud.hs-heilbronn.de]
 */
// TODO Bitte Javadoc-Kommentare auch ausfuellen!
public class Recht {

	// Static Variablen
	/**
	 * Definiert alle existierenden Rechte
	 */
	public static enum Rechtenamen {
		/**
		 * 
		 */
		BK_AENDERN,
		/**
		 * 
		 */
		BK_SPERREN,
		/**
		 * 
		 */
		BK_ANSEHEN,
		/**
		 * 
		 */
		ZENTRUM_ANLEGEN,
		/**
		 * 
		 */
		ZENTRUM_AENDERN,
		/**
		 * 
		 */
		ZENTREN_ANZEIGEN,
		/**
		 * 
		 */
		ZENTRUM_AKTIVIEREN,
		/**
		 * 
		 */
		GRUPPENNACHRICHT_VERSENDEN,
		/**
		 * 
		 */
		STUDIE_ANLEGEN,
		/**
		 * 
		 */
		STUDIE_AENDERN,
		/**
		 * 
		 */
		STUDIE_LOESCHEN,
		/**
		 * 
		 */
		STUDIE_PAUSIEREN,
		/**
		 * 
		 */
		STUDIENARM_BEENDEN,
		/**
		 * 
		 */
		STUDIE_SIMULIEREN,
		/**
		 * 
		 */
		ARCHIV_EINSEHEN,
		/**
		 * 
		 */
		STAT_EINSEHEN,
		/**
		 * 
		 */
		RANDOMISATION_EXPORTIEREN,
		/**
		 * 
		 */
		STUDIENTEILNEHMER_HINZUFUEGEN,
		/**
		 * 
		 */
		STUDIEN_EINSEHEN,
		/**
		 * 
		 */
		STUDIE_RANDOMISIEREN,
		/**
		 * 
		 */
		SYSTEM_SPERREN,
		/**
		 * 
		 */
		ADMINACCOUNTS_VERWALTEN,
		/**
		 * 
		 */
		STULEIACCOUNTS_VERWALTEN
	}

	/**
	 * HashMap, welche die Instanzen der einzelnen Rechte verwaltet.
	 */
	private static HashMap<Rechtenamen, Recht> rechte = new HashMap<Rechtenamen, Recht>();

	// Klassenvariablen
	/**
	 * Name des Rechtes, ist ein Element der <code>enum</code>
	 * {@link Rechtenamen}
	 */
	private Rechtenamen nameRecht;

	/**
	 * Erzeugt eine neue Instanz eines Rechtes mit dem Entsprechenden Namen
	 * 
	 * @param name
	 *            Der Name des REchts.
	 */
	public Recht(Rechtenamen name) {
		this.nameRecht = name;
	}

	/**
	 * Liefert die Instanz des Rechtes anhand des Namens in Form des
	 * entsprechenden {@link Rechtenamen}-Elementes
	 * 
	 * @param name
	 *            Name des Rechtes (Vgl. {@link Rechtenamen})
	 * @return Instanz des entsprechenden Rechtes.
	 */
	public static Recht getRecht(Rechtenamen name) {
		// FIXME siehe bug #18
		if (name == null) {
			throw new IllegalArgumentException(RechtException.NULL_ARGUMENT);
		}
		if (rechte.containsKey(name)) {
			return rechte.get(name); // Instanz zurueckgeben
		} else {
			rechte.put(name, new Recht(name)); // Instanz erzeugen und ablegen
			return rechte.get(name);// Instanz zurueckgeben
		}

	}

	/**
	 * Liefert den Namen des Rechtes in Form des Enum {@link Rechtenamen}-Elementes
	 * zur&uuml;ck
	 * 
	 * @return Name des Rechtes
	 */
	public String getName() {
		return this.nameRecht.toString();
	}

	/**
	 * Liefert den Namen der Rechte als ein String
	 * 
	 * @return Name des Rechtes
	 */
	public Rechtenamen getRechtname() {
		return this.nameRecht;
	}

	/**
	 * Liefert den Namen des Rechtes mit dem Pr&auml;fix als <code>String</code>
	 * 
	 * @return Den Namen des Rechtes.
	 */
	public String toString() {
		return "Recht: " + nameRecht.toString();
	}

}
