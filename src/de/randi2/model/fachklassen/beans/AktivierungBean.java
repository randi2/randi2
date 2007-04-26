package de.randi2.model.fachklassen.beans;

import java.util.GregorianCalendar;
import de.randi2.datenbank.Filter;
import de.randi2.utility.NullKonstanten;

public class AktivierungBean extends Filter{
    private long aktivierungsId=NullKonstanten.NULL_LONG;
    private GregorianCalendar versanddatum=null;
    private BenutzerkontoBean benutzerkonto=null;
    private long benutzerkontoID=NullKonstanten.NULL_LONG;
    private String aktivierungsLink=null;
    /**
     * @return the aktivierungsId
     */
    public long getAktivierungsId() {
        return aktivierungsId;
    }
    /**
     * @param aktivierungsId the aktivierungsId to set
     */
    public void setAktivierungsId(long aktivierungsId) {
        this.aktivierungsId = aktivierungsId;
    }
    /**
     * @return the aktivierungsLink
     */
    public String getAktivierungsLink() {
        return aktivierungsLink;
    }
    /**
     * @param aktivierungsLink the aktivierungsLink to set
     */
    public void setAktivierungsLink(String aktivierungsLink) {
        this.aktivierungsLink = aktivierungsLink;
    }
    /**
     * @return the benutzerkonto
     */
    public BenutzerkontoBean getBenutzerkonto() {
        return benutzerkonto;
    }
    /**
     * @param benutzerkonto the benutzerkonto to set
     */
    public void setBenutzerkonto(BenutzerkontoBean benutzerkonto) {
        this.benutzerkonto = benutzerkonto;
    }
    /**
     * @return the benutzerkontoID
     */
    public long getBenutzerkontoID() {
        return benutzerkontoID;
    }
    /**
     * @param benutzerkontoID the benutzerkontoID to set
     */
    public void setBenutzerkontoID(long benutzerkontoID) {
        this.benutzerkontoID = benutzerkontoID;
    }
    /**
     * @return the versanddatum
     */
    public GregorianCalendar getVersanddatum() {
        return versanddatum;
    }
    /**
     * @param versanddatum the versanddatum to set
     */
    public void setVersanddatum(GregorianCalendar versanddatum) {
        this.versanddatum = versanddatum;
    }
    /**
     * @param aktivierungsId
     * @param versanddatum
     * @param benutzerkonto
     * @param benutzerkontoID
     * @param aktivierungsLink
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
