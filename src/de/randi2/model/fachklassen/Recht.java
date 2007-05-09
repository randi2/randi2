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
 * @version $Id: Recht.java 2428 2007-05-06 17:50:32Z btheel $
 * @author Benjamin Theel [btheel@stud.hs-heilbronn.de]
 */
// TODO Bitte Javadoc-Kommentare auch ausfuellen!
public class Recht {

//  Static Variablen
    /**
     * Definiert alle existierenden Rechte
     * <a <href="https://wiki.mi.hs-heilbronn.de/index.php/SWP:Visionsdokument_Randomisation">https://wiki.mi.hs-heilbronn.de/index.php/SWP:Visionsdokument_Randomisation</a>
     */
    public static enum Rechtenamen {
        /**
         * Der Benutzer darf sein eigenes Benutzerkonto bearbeiten
         */
        BK_AENDERN,
        /**
         * Der Benutzer darf Benutzer ent-/sperren
         */
        BK_SPERREN,
        /**
         * Der Benutzer darf sich andere Benutzerkonten anzeigen lassen
         */
        BK_ANSEHEN,
        /**
         * Der Benutzer darf neue Zentren anlegen
         */
        ZENTRUM_ANLEGEN,
        /**
         * Der Benutzer darf bestehende Zentren bearbeiten
         */
        ZENTRUM_AENDERN,
        /**
         * Der Benutzer darf sich eine Liste der bestehenden Zentren anzeigen lassen 
         */
        ZENTREN_ANZEIGEN,
        /**
         * Der Benutzer darf Zentren (de-)aktivieren
         */
        ZENTRUM_AKTIVIEREN,
        /**
         * Der Benutzer darf Nachrichten an eine Gruppe von Benutzern versenden
         */
        GRUPPENNACHRICHT_VERSENDEN,
        /**
         * Der Benutzer darf neue Studien anlegen
         */
        STUDIE_ANLEGEN,
        /**
         * Der Benutzer darf bereits bestehende Studien bearbeiten
         */
        STUDIE_AENDERN,
        /**
         * Der Benutzer darf bestehende Studien loeschen
         */
        STUDIE_LOESCHEN,
        /**
         * Der Benutzer darf aktive Studien pausieren, bzw. fortsetzten
         */
        STUDIE_PAUSIEREN,
        /**
         * Der Benutzer darf einzelne Studienarme einer Studie beenden
         */
        STUDIENARM_BEENDEN,
        /**
         * Der Benutzer darf eine Studie simulieren
         */
        STUDIE_SIMULIEREN,
        /**
         * Der Benutzer darf, abhaengig von seiner Rolle, das Archiv der beendeten Studien einsehen.
         */
        ARCHIV_EINSEHEN,
        /**
         * Der Benutzer darf, abhaengig von seiner Rolle, die Statistik der Studien einsehen.
         */
        STAT_EINSEHEN,
        /**
         * Der Benutzer darf, abhaengig von seiner Rolle, die Randomisationsergebnisse der Studien exportieren
         */
        RANDOMISATION_EXPORTIEREN,
        /**
         * Der Benutzer darf einen neuen Probanten  einer bestehenden Studie hinzufuegen
         */
        STUDIENTEILNEHMER_HINZUFUEGEN,
        /**
         * Der Benutzer darf, abhaengig von seiner Rolle, dbestehende Studien einsehen.
         */
        STUDIEN_EINSEHEN,
        /**
         * Der Benutzer darf das Radnomisieren einer Studie anstoßen
         * @deprecated
         */
        STUDIE_RANDOMISIEREN,
        /**
         * Der Benutzer darf das System ent-/sperren 
         */
        SYSTEM_SPERREN,
        /**
         * Der Benutzer darf Admin-Accounts anlegen und/oder loeschen
         */
        ADMINACCOUNTS_VERWALTEN,
        /**
         * Der Benutzer darf neue Studienleiter-Accounts anlegen
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
