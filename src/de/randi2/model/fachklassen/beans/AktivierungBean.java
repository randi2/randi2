package de.randi2.model.fachklassen.beans;

import java.util.GregorianCalendar;

import de.randi2.datenbank.Filter;
import de.randi2.model.exceptions.AktivierungException;
import de.randi2.model.exceptions.BenutzerkontoException;
import de.randi2.model.fachklassen.Benutzerkonto;
import de.randi2.utility.NullKonstanten;

/**
 * <p>Dieses Klasse repräsentiert die Aktivierungsmöglichkeit eines Benutzers im System</p>
 * @author Andreas Freudling [afreudling@stud.hs-heilbronn.de]
 * @version $Id: AktivierungBean.java 2448 2007-05-07 13:45:09Z afreudli $
 */
public class AktivierungBean extends Filter {

    /**
     * Die ID der Aktivierung.
     */
    private long	      aktivierungsId   = NullKonstanten.NULL_LONG;

    /**
     * Das Versanddatum, wann die Anmeldungsmail versand wurde.
     */
    private GregorianCalendar versanddatum     = null;

    /**
     * Das Benutzerkonto, mit dem eine Anmeldung möglich sein soll.
     */
    private BenutzerkontoBean benutzerkonto    = null;

    /**
     * Die ID des Benutzerkontos.
     */
    private long	      benutzerkontoId  = NullKonstanten.NULL_LONG;

    /**
     * Der Aktivierungslink für die Vervollständigung der Anmeldung.
     */
    private String	    aktivierungsLink = null;

    /**
     * Get-Methode für die Aktivierungs-ID.
     * @return Liefert die Aktivierungs-ID.
     */
    public long getAktivierungsId() {
	return aktivierungsId;
    }

    /**
     * Set-Methode für die Aktivierungs-ID.
     * @param aktivierungsId Setzt die Aktivierungs-ID.
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
     * @param aktivierungsLink Setzt den Aktivierungslink.
     */
    public void setAktivierungsLink(String aktivierungsLink) {
	this.aktivierungsLink = aktivierungsLink;
    }

    /**
     * Get-Methode für das Benutzerkonto.
     * @return Liefert das Benutzerkonto.
     * @throws BenutzerkontoException Fehler
     */
    public BenutzerkontoBean getBenutzerkonto() throws BenutzerkontoException {
	if (benutzerkonto == null) {
	    benutzerkonto = Benutzerkonto.get(benutzerkontoId);
	}

	return benutzerkonto;
    }

    /**
     * Set-Methode für das Benutzerkonto.
     * @param benutzerkonto Setzt das Benutzerkonto.
     * @throws AktivierungException EIn Benutzerkonto darf nicht null sein
     */
    public void setBenutzerkonto(BenutzerkontoBean benutzerkonto) throws AktivierungException {
	if (benutzerkonto == null) {
	    throw new AktivierungException(AktivierungException.BENUTZERKONTO_NICHT_GESETZT);
	}

	this.benutzerkonto = benutzerkonto;
    }

    /**
     * Get-Methode für die Benutzerkonto-ID.
     * @return Liefert die Benutzerkonto-ID.
     */
    public long getBenutzerkontoId() {
	return benutzerkontoId;
    }

    /**
     * Set-Methode für die Benutzerkonto-Id.
     * @param benutzerkontoId - Setzt die Benutzerkonto-Id.
     * @throws AktivierungException Das Benutzerkonto muss gesetzt sein
     */
    public void setBenutzerkontoId(long benutzerkontoId) throws AktivierungException {
	if (benutzerkontoId == NullKonstanten.NULL_LONG) {
	    throw new AktivierungException(AktivierungException.BENUTZERKONTO_NICHT_GESETZT);
	}
	this.benutzerkontoId = benutzerkontoId;
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
     * @param versanddatum Setzt das Versanddatum.
     */
    public void setVersanddatum(GregorianCalendar versanddatum) {
	this.versanddatum = versanddatum;
    }

    /**
     * Datenbank-Konstruktor.
     * Der komplette Konstruktor an den alle Attribute dieser Klasse übergeben werden, mit der
     * Ausnahme von dem BenutzerkontoBean.
     * @param aktivierungsId 
     * 						Die ID der Aktivierung.
     * @param versanddatum
     * 						Das Versanddatum der Aktivierungsmail.
     * @param benutzerkontoId
     * 						Die ID des Benutzerkontos.
     * @param aktivierungsLink
     * 						Der Aktivierungslink für die Vervollständigung der Anmeldung.
     * @throws AktivierungException		Die Akivierungsdaten sind unzulaessig, Details sieh setMethoden.
     */
    public AktivierungBean(long aktivierungsId, GregorianCalendar versanddatum, long benutzerkontoId, String aktivierungsLink) throws AktivierungException {
	super();
	this.aktivierungsId = aktivierungsId;
	this.versanddatum = versanddatum;
	this.benutzerkontoId = benutzerkontoId;
	this.aktivierungsLink = aktivierungsLink;
    }

    /**
     *Konstruktor für Null-Objekt 
     */
    public AktivierungBean() {

    }

    /**
     * Liefert einen String der alle Parameter formatiert enthaelt.
     * 
     * @return String der alle Parameter formatiert enthaelt.
     * @see java.lang.Object#toString()
     * 
     */
    @Override
    public String toString() {
	return "id:\t"+this.aktivierungsId+"\tlink:\t"+this.aktivierungsLink+"\tversand:\t"+this.versanddatum+"\tbenutzerid\t"+this.benutzerkontoId;
    }

    /**
     * Diese Methode prueft, ob zwei Kontos identisch sind. Zwei Kontos sind
     * identisch, wenn Benutzernamen identisch sind.
     * 
     * @param zuvergleichendesObjekt
     *            das zu vergleichende Objekt vom selben Typ
     * @return <code>true</code>, wenn beide Kontos gleich sind, ansonstenm
     *         <code>false</code>
     */
    @Override
    public boolean equals(Object zuvergleichendesObjekt) {
	if (zuvergleichendesObjekt instanceof AktivierungBean) {
	    AktivierungBean beanZuvergleichen = (AktivierungBean) zuvergleichendesObjekt;
	    if (beanZuvergleichen.aktivierungsId != this.aktivierungsId) {
		return false;
	    }
	    if(beanZuvergleichen.aktivierungsLink==null&&this.aktivierungsLink!=null){
		return false;
	    }
	    else if (!beanZuvergleichen.aktivierungsLink.equals(this.aktivierungsLink)) {
		return false;
	    }
	    if (beanZuvergleichen.benutzerkonto == null && this.benutzerkonto != null) {
		return false;
	    } else if (!beanZuvergleichen.benutzerkonto.equals(this.benutzerkonto)) {
		return false;
	    }
	    if (beanZuvergleichen.benutzerkontoId != this.benutzerkontoId) {
		return false;
	    }
	    if(beanZuvergleichen.versanddatum==null&&this.versanddatum!=null){
		return false;
	    }
	    else if(!beanZuvergleichen.versanddatum.equals(this.versanddatum)){
		return false;
	    }
	    return true;

	}
	return false;
    }
}
