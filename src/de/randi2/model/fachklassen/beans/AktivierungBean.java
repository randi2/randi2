package de.randi2.model.fachklassen.beans;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

import de.randi2.datenbank.Filter;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.AktivierungException;
import de.randi2.model.exceptions.BenutzerException;
import de.randi2.model.fachklassen.Benutzerkonto;
import de.randi2.utility.KryptoUtil;
import de.randi2.utility.NullKonstanten;

/**
 * <p>
 * Dieses Klasse repräsentiert die Aktivierungsmöglichkeit eines Benutzers im
 * System
 * </p>
 * 
 * @author Andreas Freudling [afreudling@stud.hs-heilbronn.de]
 * @version $Id: AktivierungBean.java 2448 2007-05-07 13:45:09Z afreudli $
 */
public class AktivierungBean extends Filter {

	/**
	 * Das Versanddatum, wann die Anmeldungsmail versand wurde.
	 */
	private GregorianCalendar aVersanddatum = null;

	/**
	 * Das Benutzerkonto, mit dem eine Anmeldung möglich sein soll.
	 */
	private BenutzerkontoBean aBenutzerkonto = null;

	/**
	 * Die ID des Benutzerkontos.
	 */
	private long aBenutzerkontoId = NullKonstanten.DUMMY_ID;

	/**
	 * Der Aktivierungslink für die Vervollständigung der Anmeldung.
	 */
	private String aAktivierungsLink = null;

	/**
	 * Get-Methode für den Aktivierungslink.
	 * 
	 * @return Liefert den Aktivierungslink.
	 */
	public String getAktivierungsLink() {
		return aAktivierungsLink;
	}

	/**
	 * Set-Methode für den Aktivierungslink.
	 * 
	 * @param aktivierungsLink
	 *            Setzt den Aktivierungslink (nur die Id)
	 * @throws AktivierungException
	 *             falls der Aktivierungslink
	 */
	public void setAktivierungsLink(String aktivierungsLink)
			throws AktivierungException {
		if (aktivierungsLink == null
				|| aktivierungsLink.length() != KryptoUtil.AKTIVIERUNGSCODE_LAENGE) {
			throw new AktivierungException(
					AktivierungException.AKTIVIERUNGSLINK_FALSCHE_LAENGE);
		}
		this.aAktivierungsLink = aktivierungsLink;
	}

	/**
	 * Get-Methode für das Benutzerkonto.
	 * 
	 * @return Liefert das Benutzerkonto.
	 * @throws DatenbankExceptions
	 *             Fehler
	 */
	public BenutzerkontoBean getBenutzerkonto() throws DatenbankExceptions {
		if (aBenutzerkonto == null) {
			aBenutzerkonto = Benutzerkonto.get(aBenutzerkontoId);
		}

		return aBenutzerkonto;
	}

	/**
	 * Set-Methode für das Benutzerkonto.
	 * 
	 * @param benutzerkonto
	 *            Setzt das Benutzerkonto.
	 * @throws AktivierungException
	 *             EIn Benutzerkonto darf nicht null sein
	 */
	public void setBenutzerkonto(BenutzerkontoBean benutzerkonto)
			throws AktivierungException {
		if (benutzerkonto == null) {
			throw new AktivierungException(
					AktivierungException.BENUTZERKONTO_NICHT_GESETZT);
		}
		this.setBenutzerkontoId(benutzerkonto.getId());
		this.aBenutzerkonto = benutzerkonto;
	}

	/**
	 * Get-Methode für die Benutzerkonto-ID.
	 * 
	 * @return Liefert die Benutzerkonto-ID.
	 */
	public long getBenutzerkontoId() {
		return aBenutzerkontoId;
	}

	/**
	 * Set-Methode für die Benutzerkonto-Id.
	 * 
	 * @param benutzerkontoId -
	 *            Setzt die Benutzerkonto-Id.
	 * @throws AktivierungException
	 *             Das Benutzerkonto muss gesetzt sein
	 */
	public void setBenutzerkontoId(long benutzerkontoId)
			throws AktivierungException {
		if (benutzerkontoId == NullKonstanten.DUMMY_ID) {
			throw new AktivierungException(
					AktivierungException.BENUTZERKONTO_NICHT_GESPEICHERT);
		}
		this.aBenutzerkontoId = benutzerkontoId;
	}

	/**
	 * Get-Methode für das Versanddatum.
	 * 
	 * @return Liefert das Versanddatum.
	 */
	public GregorianCalendar getVersanddatum() {
		return aVersanddatum;
	}

	/**
	 * Set-Methode für das Versanddatum.
	 * 
	 * @param versanddatum
	 *            Setzt das Versanddatum.
	 */
	public void setVersanddatum(GregorianCalendar versanddatum) {
		this.aVersanddatum = versanddatum;
	}

	/**
	 * Datenbank-Konstruktor. Der komplette Konstruktor an den alle Attribute
	 * dieser Klasse übergeben werden, mit der Ausnahme von dem
	 * BenutzerkontoBean.
	 * 
	 * @param id
	 *            Die ID der Aktivierung.
	 * @param versanddatum
	 *            Das Versanddatum der Aktivierungsmail.
	 * @param benutzerkontoId
	 *            Die ID des Benutzerkontos.
	 * @param aktivierungsLink
	 *            Der Aktivierungslink für die Vervollständigung der Anmeldung.
	 * @throws AktivierungException
	 *             Die Akivierungsdaten sind unzulaessig, Details sieh
	 *             setMethoden.
	 * @throws DatenbankExceptions
	 *             wenn die Id nicht korrekt ist
	 */
	public AktivierungBean(long id, GregorianCalendar versanddatum,
			long benutzerkontoId, String aktivierungsLink)
			throws AktivierungException, DatenbankExceptions {

		super.setId(id);
		this.aVersanddatum = versanddatum;
		this.aBenutzerkontoId = benutzerkontoId;
		this.aAktivierungsLink = aktivierungsLink;
	}

	/**
	 * Konstruktor für Null-Objekt
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
		return "id:\t" + this.getId() + "\tlink:\t" + this.aAktivierungsLink
				+ "\tversand:\t" + this.aVersanddatum + "\tbenutzerid\t"
				+ this.aBenutzerkontoId;
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
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy",
				Locale.GERMANY);

		if (zuvergleichendesObjekt == null) {
			return false;
		}
		if (zuvergleichendesObjekt instanceof AktivierungBean) {
			AktivierungBean beanZuvergleichen = (AktivierungBean) zuvergleichendesObjekt;
			if (beanZuvergleichen.getId() != this.getId()) {
				return false;
			}
			if (beanZuvergleichen.getAktivierungsLink() == null
					&& this.aAktivierungsLink != null) {
				return false;
			} else if (beanZuvergleichen.getAktivierungsLink() != null
					&& !beanZuvergleichen.getAktivierungsLink().equals(
							this.aAktivierungsLink)) {
				return false;
			}
			if (beanZuvergleichen.getBenutzerkontoId() != this.aBenutzerkontoId) {
				return false;
			}
			if (beanZuvergleichen.getVersanddatum() == null
					&& this.aVersanddatum != null) {
				return false;
			} else if (beanZuvergleichen.getVersanddatum() != null
					&& !(sdf.format(beanZuvergleichen.getVersanddatum()
							.getTime()).equals(sdf.format(this
							.getVersanddatum().getTime())))) {
				return false;
			}
			return true;

		}
		return false;
	}

	/**
	 * Liefert den HashCode des Objektes.<br>
	 * Der HashCode entspricht der (Datenbank-)Id des Objektes. Ist das Objekt
	 * noch nicht gespeichert worden, besitzt also die ID
	 * {@link NullKonstanten#DUMMY_ID}, so wird der HashCode von
	 * {@link java.lang.Object#hashCode()} geliefert.
	 * 
	 * @return HashCode des Objektes
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if (this.getId() == NullKonstanten.DUMMY_ID) {
			return super.hashCode();
		}
		return (int) this.getId();
	}

	@Override
	public void validate() throws BenutzerException {
		// TODO Auto-generated method stub
		
	}
}
