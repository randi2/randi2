package de.randi2.model.fachklassen;

import de.randi2.model.fachklassen.Recht.Rechtenamen;

/**
 * Zentrales Element der Rechteverwaltung.<br>
 * Jedem Benutzerkonto wird eine Rolle zugeordnet, welche bestimmt, weche ueber
 * welche Rechte das Konto verfuegt. <br>
 * Jede Rolle besitzt eine klar definierte Menge an Rechten, welche eine
 * Untermenge der <code>enum</code> {@link Rechtenamen} ist.<br>
 * Die Menge der Rechte einer Rolle werden ueber die Rollenrechtelisten
 * definiert, welche fest codiert sind und somit nicht zur Laufzeit geaendert
 * werden koennen.<br>
 * <br>
 * Ob eine bestimmte Rolle ein bestimmtes Recht enthaelt, kann zur Laufzeit
 * mittels der Methode {@link #besitzenRolleRecht} ermittelt werden (Vgl.
 * Methodendokumentation).<br>
 * <br>
 * Auf die Namen der einzelnen Rollen kann ueber die <code>enum</code>
 * {@link Rollen} zugegriffen werden. <br>
 * Zugegriffen auf die einzelnen Rollen werden kann mittels der jeweiligen
 * Getter.
 * 
 * @version 1.0
 * @author Benjamin Theel <btheel@stud.hs-heilbronn.de>
 * 
 */
public class Rolle {

    // Konstanten/ Enum
    /**
     * Enthaelt alle Rollennamen, die innerhalt des Programmes zur Verfuegung
     * stehen
     * 
     * @version 1.0
     */
    public static enum Rollen {
        /**
         * Rollenname des Studienarztes
         */
        STUDIENARZT,
        /**
         * Rollenname des Studienleiters
         */
        STUDIENLEITER,
        /**
         * Rollenname des Administrators
         */
        ADMIN,
        /**
         * Rollenname des Systemooperators
         */
        SYSOP,
        /**
         * Rollenname des Statistikers
         */
        STATISTIKER
    }

    // Rollenkonstanten
    /**
     * Instanz der Rolle "Studienarzt"
     */
    private static Rolle STUDIENARZT;

    /**
     * Instanz der Rolle "Studienleiter"
     */
    private static Rolle STUDIENLEITER;

    /**
     * Instanz der Rolle "Admin"
     */
    private static Rolle ADMIN;

    /**
     * Instanz der Rolle "Sysop"
     */
    private static Rolle SYSOP;

    /**
     * Instanz der Rolle "Statistiker"
     */
    private static Rolle STATISTIKER;

    // Rollenrechtelisten
    /**
     * Rollenrechteliste des Studienarztes
     */
    private static Recht[] rechteListe_Studienarzt = {
            Recht.getRecht(Rechtenamen.RECHT_1),
            Recht.getRecht(Rechtenamen.RECHT_4) };

    /**
     * Rollenrechteliste des Studienleiters
     */
    private static Recht[] rechteListe_Studienleiter = {
            Recht.getRecht(Rechtenamen.RECHT_2),
            Recht.getRecht(Rechtenamen.RECHT_3) };

    /**
     * Rollenrechteliste des Administrators
     */
    private static Recht[] rechteListe_Admin = {
            Recht.getRecht(Rechtenamen.RECHT_1),
            Recht.getRecht(Rechtenamen.RECHT_4) };

    /**
     * Rollenrechteliste des Systemoperators
     */
    private static Recht[] rechteListe_Sysop = { Recht
            .getRecht(Rechtenamen.RECHT_4) };

    /**
     * Rollenrechteliste des Statistikers
     */
    private static Recht[] rechteListe_Statistiker = {
            Recht.getRecht(Rechtenamen.RECHT_1),
            Recht.getRecht(Rechtenamen.RECHT_2),
            Recht.getRecht(Rechtenamen.RECHT_3),
            Recht.getRecht(Rechtenamen.RECHT_4) };

    // Klassenvariablen

    /**
     * Name der Rolle, Element der <code>enum</code> {@link Rollen}
     */
    private Rollen rollenname;

    /**
     * Liste (ein Array des Types Recht) mit den einzelnen Rechten, ueber die
     * jeweilige Rolle verfuegt. Ein Recht, welches nicht in dieser Liste
     * enthalten ist, kann durch die Rolle nicht ausgefÃ¼hrt werden
     */
    private Recht[] rechte;

    // Konstruktor

    /**
     * Erzeugt eine Instanz einer Rolle mit dem entsprechendem Namen und der
     * Liste der Rechte, die der Rolle eingeraeumt werden sollen
     * 
     * @param name
     *            Name der Rolle {@link Rollen}
     * @param rechte
     *            Liste (entsprechendes Rechte[]) der Rechte, die der Rolle
     *            eingerÃ¤umt werden.
     */
    private Rolle(Rollen name, Recht[] rechte) {
        this.rollenname = name;
        this.rechte = rechte;
    }

    // Methoden

    /**
     * Prueft anhand des Rechtenamens {@link Recht.Rechtenamen}, ob die Rolle
     * das jeweilige Recht besitzt
     * 
     * @param name
     *            Name des Rechtes
     * @return <code>true</code>, sofern die Rolle das Recht besitzt,
     *         anderenfalls <code>false</code>
     */
    public boolean besitzenRolleRecht(Rechtenamen name) {
        for (Recht aRecht : this.rechte) {
            if (name.equals(aRecht.getRechtname()))
                return true;
        }
        return false;
    }

    /**
     * Liefert die Instanz der Rolle Admin
     * 
     * @return Rolle Admin
     */
    public static Rolle getAdmin() {
        if (ADMIN == null)
            ADMIN = new Rolle(Rollen.ADMIN, rechteListe_Admin);
        return ADMIN;
    }

    /**
     * Liefert die Instanz der Rolle Studienarzt
     * 
     * @return Rolle Studienarzt
     */
    public static Rolle getStudienarzt() {
        if (STUDIENARZT == null)
            STUDIENARZT = new Rolle(Rollen.STUDIENARZT, rechteListe_Studienarzt);
        return STUDIENARZT;
    }

    /**
     * Liefert die Instanz der Rolle Studienleiter
     * 
     * @return Rolle Studienleiter
     */
    public static Rolle getStudieleiter() {
        if (STUDIENLEITER == null)
            STUDIENLEITER = new Rolle(Rollen.STUDIENLEITER,
                    rechteListe_Studienleiter);
        return STUDIENLEITER;
    }

    /**
     * Liefert die Instanz der Rolle Statistiker
     * 
     * @return Rolle Statistiker
     */
    public static Rolle getStatistiker() {
        if (STATISTIKER == null)
            STATISTIKER = new Rolle(Rollen.STATISTIKER, rechteListe_Statistiker);
        return STATISTIKER;
    }

    /**
     * Liefert die Instanz der Rolle Systemoperator
     * 
     * @return Rolle Systemoperator
     */
    public static Rolle getSysop() {
        if (SYSOP == null)
            SYSOP = new Rolle(Rollen.SYSOP, rechteListe_Sysop);
        return SYSOP;
    }

    /**
     * Liefert den Namen der Rolle als <code>String<code>
     * @return Name der Rolle
     */
    public String getName() {
        return this.rollenname.toString();
    }

    /**
     * Liefert den Namen der Rolle als Element der <code>enum</code>
     * {@link Rollen}
     * 
     * @return Name der Rolle
     */
    public Rollen getRollenname() {
        return this.rollenname;
    }

    /**
     * Liefert den Namen der Rolle mit dem Praefix "Rolle: " als String
     * 
     * @return Name der Rolle
     */
    public String toString() {
        return "Rolle: " + rollenname.toString();
    }

}
