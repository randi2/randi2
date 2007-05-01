package de.randi2.model.fachklassen.beans;

import java.util.GregorianCalendar;

import de.randi2.datenbank.Filter;
import de.randi2.utility.NullKonstanten;

/**
 * <p>Dieses Klasse repräsentiert die Aktivierungsmöglichkeit eines Benutzers im System</p>
 * @author //TODO Wer will Autor sein?!
 * @version $Id$
 */
public class AktivierungBean extends Filter{
	
	/**
	 * Die ID der Aktivierung.
	 */
    private long aktivierungsId=NullKonstanten.NULL_LONG;
    /**
     * Das Versanddatum, wann die Anmeldungsmail versand wurde.
     */
    private GregorianCalendar versanddatum=null;
    /**
     * Das Benutzerkonto, mit dem eine Anmeldung möglich sein soll.
     */
    private BenutzerkontoBean benutzerkonto=null;
    /**
     * Die ID des Benutzerkontos.
     */
    private long benutzerkontoID=NullKonstanten.NULL_LONG;
    /**
     * Der Aktivierungslink für die Vervollständigung der Anmeldung.
     */
    private String aktivierungsLink=null;
    
    /**
     * Get-Methode für die Aktivierungs-ID.
     * @return Liefert die Aktivierungs-ID.
     */
    public long getAktivierungsId() {
        return aktivierungsId;
    }
    /**
     * Set-Methode für die Aktivierungs-ID.
     * @param Setzt die Aktivierungs-ID.
     */
    public void setAktivierungsId(long aktivierungsId) {
        this.aktivierungsId = aktivierungsId;
    }
    /**
     * Get-Methode für den Aktivierungslink.
     * @return Liefert den Aktivierungslink.
     */
    public String getAktivierungsLink() {
        return aktivierungsLink;
    }
    /**
     * Set-Methode für den Aktivierungslink.
     * @param Setzt den Aktivierungslink.
     */
    public void setAktivierungsLink(String aktivierungsLink) {
        this.aktivierungsLink = aktivierungsLink;
    }
    /**
     * Get-Methode für das Benutzerkonto.
     * @return Liefert das Benutzerkonto.
     */
    public BenutzerkontoBean getBenutzerkonto() {
        return benutzerkonto;
    }
    /**
     * Set-Methode für das Benutzerkonto.
     * @param Setzt das Benutzerkonto.
     */
    public void setBenutzerkonto(BenutzerkontoBean benutzerkonto) {
        this.benutzerkonto = benutzerkonto;
    }
    /**
     * Get-Methode für die Benutzerkonto-ID.
     * @return Liefert die Benutzerkonto-ID.
     */
    public long getBenutzerkontoID() {
        return benutzerkontoID;
    }
    /**
     * Set-Methode für die Benutzerkonto-ID.
     * @param Setzt die Benutzerkonto-ID.
     */
    public void setBenutzerkontoID(long benutzerkontoID) {
        this.benutzerkontoID = benutzerkontoID;
    }
    /**
     * Get-Methode für das Versanddatum.
     * @return Liefert das Versanddatum.
     */
    public GregorianCalendar getVersanddatum() {
        return versanddatum;
    }
    /**
     * Set-Methode für das Versanddatum.
     * @param Setzt das Versanddatum.
     */
    public void setVersanddatum(GregorianCalendar versanddatum) {
        this.versanddatum = versanddatum;
    }
    /**
     * Der komplette Konstruktor an den alle Attribute dieser Klasse übergeben werden.
     * @param aktivierungsId 
     * 						Die ID der Aktivierung.
     * @param versanddatum
     * 						Das Versanddatum der Aktivierungsmail.
     * @param benutzerkonto
     * 						Das Benutzerkonto, mit dem eine Anmeldung möglich sein soll.
     * @param benutzerkontoID
     * 						Die ID des Benutzerkontos.
     * @param aktivierungsLink
     * 						Der Aktivierungslink für die Vervollständigung der Anmeldung.
     */
    public AktivierungBean(long aktivierungsId, GregorianCalendar versanddatum, BenutzerkontoBean benutzerkonto, long benutzerkontoID, String aktivierungsLink) {
	super();
	this.aktivierungsId = aktivierungsId;
	this.versanddatum = versanddatum;
	this.benutzerkonto = benutzerkonto;
	this.benutzerkontoID = benutzerkontoID;
	this.aktivierungsLink = aktivierungsLink;
    }
    

}
