package de.randi2.datenbank;

import de.randi2.datenbank.exceptions.DatenbankFehlerException;


/**
 * <p>
 * Diese Klasse ermoeglicht es, nach Klassen, welche von Filter erben, in der
 * Datenbak zu speichern, bzw. nach diesen zu suchen.
 * </p>
 * <p>
 * Beans, die als Suchfilter dienen, muessen die Flag {@link #isFilter} auf
 * <code>true</code> gesetzt bekommen, anderenfalls werden sie mit einer
 * {@link DatenbankFehlerException} (Msg:
 * {@link DatenbankFehlerException#SUCHOBJEKT_IST_KEIN_FILTER}) abgewiesen.
 * </p>
 * <p>
 * per Default ist die Flag {@link #isFilter} auf <code>false</code> gesetzt.
 * Deshalb ist es erforderlich, nach dem Erzeugen eines SuchBeans
 * <code>{@link #setFilter(boolean)}</code> mit <code>true</code>
 * aufzurufen.
 * </p>
 * <p>
 * Beispiel:<br>
 * <code> 
 * BenutzerkontoBean sBean = new BenutzerkontoBean();<br>
 * sBean.setFilter(true);<br>
 * sBean.setBenutzername("s");
 * </code>
 * </p>
 * 
 * @author Benjamin Theel <BTheel@stud.hs-heilbronn.de>
 * @author Frederik Reifschneider <Reifschneider@stud.uni-heidelberg.de>
 * 
 * @version $Id$
 * 
 */
public class Filter {
    /**
     * Flag, die anzeigt, ob ein Bean als Filter eingesetzt werden soll oder
     * nicht
     */
    private boolean isFilter = false;

    /**
     * Leerer Standartkonstruktor, {@link #isFilter} entspricht dem Defaultwert
     */
    public Filter() {

    }

    /**
     * Konstrukter Setzt die Flag {@link #isFilter} entsprechend des
     * uebergebenen Parameters
     * 
     * @param filter
     *            Wert, den die Flag {@link #isFilter} annehmen soll
     */
    public Filter(boolean filter) {
        this.isFilter = filter;
    }

    /**
     * Setzt die Flag {@link #isFilter} entsprechend des Parameters
     * @param filter
     *            Wert, den die Flag {@link #isFilter} annehmen soll
     */
    public void setFilter(boolean filter) {
        this.isFilter = filter;
    }

    /**
     * Liefert den Status der Flag 
     * @return <code>true</code>, wenn Objekt als Filter markiert
     */
    public boolean isFilter() {
        return this.isFilter;
    }

}
