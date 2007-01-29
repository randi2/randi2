package de.randi2.model.fachklassen;

import java.util.HashMap;

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
 * @version 1.0
 * @author Benjamin Theel <btheel@stud.hs-heilbronn.de>
 */
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
     */
    private Recht(Rechtenamen name) {
        this.nameRecht = name;
    }

    /**
     * Liefert die Instanz des Rechtes anhand des Namens in Form des
     * entsprechenden {@link Rechtenamen}-Elementes
     * 
     * @param name
     *            Name des Rechtes (Vgl. {@link Rechtenamen})
     * @return Instanz des entsprechenden Rechtes
     * @throws IllegalArgumentException
     *             Exception wird geworfen, wird <code>null</code> als
     *             Argument &uuml;bergeben
     */
    public static Recht getRecht(Rechtenamen name)
            throws IllegalArgumentException {
        if (name == null)
            throw new IllegalArgumentException("Ungueltiges Argument: 'null'");
        if (rechte.containsKey(name))// Instanz im Map enthalten?
            return rechte.get(name); // Instanz zuruekcgeben
        else {
            rechte.put(name, new Recht(name)); // I)nstanz erzeugen und ablegen
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
     * Liefert den Namen des Rechte als ein String
     * 
     * @return Name des Rechtes
     */
    public Rechtenamen getRechtname() {
        return this.nameRecht;
    }

    /**
     * Liefert den Namen des Rechtes mit dem Pr&auml;fix als <code>String</code>
     */
    public String toString() {
        return "Recht: " + nameRecht.toString();
    }

}
