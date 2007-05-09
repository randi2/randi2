package de.randi2.model.fachklassen.beans;

import de.randi2.datenbank.Filter;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.exceptions.ZentrumException;
import de.randi2.model.fachklassen.Person;
import de.randi2.utility.KryptoUtil;
import de.randi2.utility.NullKonstanten;

/**
 * <p>
 * Diese Klasse repraesentiert ein Zentrum.
 * </p>
 * 
 * @author Lukasz Plotnicki [lplotni@stud.hs-heilbronn.de]
 * @version $Id: ZentrumBean.java 2383 2007-05-04 08:45:30Z lplotni $
 * 
 */
public class ZentrumBean extends Filter {

	// TODO Wenn die Validierung der Daten in einer Utility-Klasse vorhanden
	// sein wird, werden die set() Methoden dementsprechend angepasst. (lplotni)

	/**
	 * Name der Abteilung in der Institution.
	 */
	private String abteilung = null;

	/**
	 * Ein PersonBean mit der Daten des Ansprechpartners in dem Zentrum.
	 */
	private PersonBean ansprechpartner = null;

	/**
	 * Die eindeutige ID des Ansprechpartners.
	 */
	private long ansprechpartnerId = NullKonstanten.NULL_LONG;

	/**
	 * Hausnummer
	 */
	private String hausnr = null;

	/**
	 * Interne ID des Zentrums
	 */
	private long id = NullKonstanten.NULL_LONG;

	/**
	 * Name der Institution.
	 */
	private String institution = null;

	/**
	 * Ort
	 */
	private String ort = null;

	/**
	 * Passwort für das Zentrum (gehasht)
	 */
	private String passwort = null;

	/**
	 * Postleitzahl
	 */
	private String plz = null;

	/**
	 * Strasse
	 */
	private String strasse = null;

	/**
	 * Ist das Zentrum aktiviert und kann an Studien teilnehmen
	 */
	private boolean istAktiviert = false;

	/**
	 * Einfacher Konstruktor von dieser Klasse.
	 */
	public ZentrumBean() {

	}

	/**
	 * Der volle Konstruktor dieser Klasse, an den alle Attribute uebergeben
	 * werden.
	 * 
	 * @param id
	 *            Die id des Zentrums auf der Datenbank.
	 * @param institution
	 *            String, der der Institution entspricht.
	 * @param abteilung
	 *            String, der der Abteilung in der Institution entspricht.
	 * @param ort
	 *            String, der dem Ort entspricht.
	 * @param plz
	 *            String, der der PLZ entspricht.
	 * @param strasse
	 *            String, der der Strasse entspricht.
	 * @param hausnr
	 *            String, der der Hausnummer entspricht.
	 * @param ansprechpartnerId
	 *            Die eindeutige Id, die dem Ansprechpartner in dem Zentrum
	 *            entspricht.
	 * @param passwortHash
	 *            String - Passwort bereits gehasht.
	 * @param istAktiviert
	 *            Der Status des Zentrum - aktiv / inaktiv.
	 * 
	 * @throws ZentrumException -
	 *             wenn die Daten, die an den Konstruktor uebergeben wurden,
	 *             nicht valide waren
	 */
	public ZentrumBean(long id, String institution, String abteilung,
			String ort, String plz, String strasse, String hausnr,
			long ansprechpartnerId, String passwortHash, boolean istAktiviert)
			throws ZentrumException {

		this.setId(id);

		this.setInstitution(institution);
		this.setAbteilung(abteilung);
		this.setOrt(ort);
		this.setPlz(plz);
		this.setStrasse(strasse);
		this.setHausnr(hausnr);
		this.setAnsprechpartnerId(ansprechpartnerId);
		this.setPasswort(passwortHash);
		this.setIstAktiviert(istAktiviert);
	}

	/**
	 * Methode die zwei Objekte dieser Klasse bzgl. allen ihrere Eigenschaften
	 * vergleicht.
	 * 
	 * @param zentrumZuVergleichen
	 *            Objekt, das mit aktueller Instanz verglichen werden soll.
	 * @return true, wenn die beide Objekte voellig uebereinstimmen, ansonsten
	 *         false
	 */
	@Override
	public boolean equals(Object zentrumZuVergleichen) {
		ZentrumBean zentrum = null;
		if (zentrumZuVergleichen instanceof ZentrumBean) {
			zentrum = (ZentrumBean) zentrumZuVergleichen;
			if (!zentrum.getInstitution().equals(this.getInstitution())) {
				return false;
			}
			if (!zentrum.getAbteilung().equals(this.getAbteilung())) {
				return false;
			}
			try {
				if (!zentrum.getAnsprechpartner().equals(
						this.getAnsprechpartner())) {
					return false;
				}
			} catch (PersonException e) {
				/*
				 * Wenn das entsprechende Ansprechpartenr-Objekt, nicht gefunden
				 * bzw. nicht geholt werden konnte, sind auch die beiden Objekte
				 * unterschiedlich.
				 */
				return false;
			}
			if (!zentrum.getHausnr().equals(this.getHausnr())) {
				return false;
			}
			if (!zentrum.getOrt().equals(this.getOrt())) {
				return false;
			}
			if (!zentrum.getPasswort().equals(this.getPasswort())) {
				return false;
			}
			if (!zentrum.getPlz().equals(this.getPlz())) {
				return false;
			}
			if (!zentrum.getStrasse().equals(this.getStrasse())) {
				return false;
			}
			if (!(zentrum.getId() == this.getId())) {
				return false;
			}
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Get-Methode fuer die Abteilung.
	 * 
	 * @return String - Abteilung.
	 */
	public String getAbteilung() {
		return abteilung;
	}

	/**
	 * Get-Methode fuer den Ansprechpartner.
	 * 
	 * @return Der Ansprechpartner.
	 * @throws PersonException
	 *             falls ein Fehler auftrat.
	 */
	public PersonBean getAnsprechpartner() throws PersonException {
		if (ansprechpartner == null) {
			ansprechpartner = Person.get(ansprechpartnerId);
		}
		return ansprechpartner;
	}

	/**
	 * Get-Methoder fuer die Hausnummer.
	 * 
	 * @return die Hausnummer.
	 */
	public String getHausnr() {
		return hausnr;
	}

	/**
	 * Get-Methoder fuer die Id aus der Datenbank.
	 * 
	 * @return Die id auf der Datenbank.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Get-Methoder fuer die Institution.
	 * 
	 * @return Die Institution.
	 * 
	 */
	public String getInstitution() {
		return institution;
	}

	/**
	 * Get-Methoder fuer den Ort.
	 * 
	 * @return Den Ort.
	 */
	public String getOrt() {
		return ort;
	}

	/**
	 * Get-Methoder fuer das Passwort.
	 * 
	 * @return Das Passwort.
	 */
	public String getPasswort() {
		return passwort;
	}

	/**
	 * Get-Methoder fuer die PLZ.
	 * 
	 * @return Die Postleitzahl.
	 */
	public String getPlz() {
		return plz;
	}

	/**
	 * Get-Methoder fuer die Strasse.
	 * 
	 * @return Die Strasse.
	 */
	public String getStrasse() {
		return strasse;

	}

	/**
	 * Set-Methode fuer die Abteilung.
	 * 
	 * @param abteilung
	 *            Der Name der Abteilung.
	 * @throws ZentrumException
	 *             Wenn bei der Validierung ein Fehler aufgetreten ist
	 */
	public void setAbteilung(String abteilung) throws ZentrumException {
		if (!this.isFilter()) {
			if (abteilung == null) {
				throw new ZentrumException(ZentrumException.ABTEILUNG_NULL);
			}
			if (!abteilung.matches(".{3,70}")) {
				throw new ZentrumException(ZentrumException.ABTEILUNG_FALSCH);
			}
		}

		this.abteilung = abteilung;
	}

	/**
	 * Set-Methode fuer den Ansprechpartner.
	 * 
	 * @param ansprechpartner
	 *            Der neue Ansprechpartner der Abteilung.
	 * @throws ZentrumException
	 *             diese Exception wird geworfen, wenn das uebergebene
	 *             PersonBean Objekt noch nich in der DB gespeichert wurde.
	 */
	public void setAnsprechpartner(PersonBean ansprechpartner)
			throws ZentrumException {
		if (ansprechpartner.getId() == NullKonstanten.NULL_LONG) {
			throw new ZentrumException(
					ZentrumException.ANSPRECHPARTNER_NOCH_NICHT_GESPEICHERT);
		} else {
			this.ansprechpartner = ansprechpartner;
			this.ansprechpartnerId = ansprechpartner.getId();

		}
	}

	/**
	 * Set-Methode fuer die Id des Ansprechpartners.
	 * 
	 * @param id
	 *            die eindeutige (long) Id des Ansprechpartners aus der
	 *            Datenbank.
	 */
	public void setAnsprechpartnerId(long id) {
		this.ansprechpartnerId = id;
	}

	/**
	 * Set-Methoder fuer die Hausnummer.
	 * 
	 * @param hausnr
	 *            Die Hausnummer.
	 * @throws ZentrumException
	 *             Wenn bei der Validierung ein Fehler aufgetreten ist
	 */
	public void setHausnr(String hausnr) throws ZentrumException {
		if (this.isFilter()) {
			this.hausnr = hausnr;
		} else {
			if (hausnr != null) {
				if (!hausnr.matches("\\d{1,4}[a-b]{0,2}")) {
					throw new ZentrumException(ZentrumException.HAUSNR_FALSCH);
				}
				this.hausnr = hausnr;
			} else {
				throw new ZentrumException(ZentrumException.HAUSNR_NULL);
			}
		}
	}

	/**
	 * Set-Methoder fuer die Id auf der Datenbank.
	 * 
	 * @param id
	 *            Die id auf der Datenbank.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Set-Methoder fuer die Institution.
	 * 
	 * @param institution
	 *            Die Institution.
	 * @throws ZentrumException
	 *             Wenn bei der Validierung ein Fehler aufgetreten ist
	 */
	public void setInstitution(String institution) throws ZentrumException {

		if (this.isFilter()) {
			this.institution = institution;
		} else {
			if (institution != null) {
				if (!institution.matches(".{3,70}")) {
					throw new ZentrumException(
							ZentrumException.INSTITUTION_FALSCH);
				}
				this.institution = institution;
			} else {
				throw new ZentrumException(ZentrumException.INSTITUTION_NULL);
			}
		}
	}

	/**
	 * Set-Methoder fuer den Ort.
	 * 
	 * @param ort
	 *            Den Ort.
	 * @throws ZentrumException
	 *             Wenn bei der Validierung ein Fehler aufgetreten ist
	 */
	public void setOrt(String ort) throws ZentrumException {

		if (this.isFilter()) {
			this.ort = ort;
		} else {
			if (ort != null) {
				if (!ort.matches(".{3,50}")) {
					throw new ZentrumException(ZentrumException.ORT_FALSCH);
				}
				this.ort = ort;
			} else {
				throw new ZentrumException(ZentrumException.ORT_NULL);
			}
		}

	}

	/**
	 * Diese Methode ist zu benutzen, wenn das bereits gehashte Passwort gesetzt
	 * werden soll.
	 * 
	 * @param hash
	 *            String Passwort als hash.
	 * @throws ZentrumException
	 *             wenn null oder leeres String uebergeben wurde
	 * 
	 */
	public void setPasswort(String hash) throws ZentrumException {
		if (hash.length() != 64) {
			throw new ZentrumException(ZentrumException.PASSWORT_NULL);
		}
		this.passwort = hash;
	}

	/**
	 * Diese Methode ist zu benutzten, wenn das Passwort, das gesetzt werden
	 * soll, noch in Klartext Form vorhanden ist und erstmal gehashed werden
	 * muss.
	 * 
	 * @param klartext
	 *            String Passwort - in Klartext-Form
	 * @throws ZentrumException
	 *             wenn die Validierung nicht erfolgreich war
	 */
	public void setPasswortKlartext(String klartext) throws ZentrumException {
		if (this.isFilter()) {
			this.passwort = KryptoUtil.getInstance().hashPasswort(klartext);
		} else {
			if (klartext != null) {
				if (!(klartext.matches(".*[A-Za-z].*")
						&& klartext.matches(".*[0-9].*")
						&& klartext
								.matches(".*[\\^,.\\-#+;:_'*!\"§$@&%/()=?|<>].*") && klartext
						.matches(".{12}"))) {
					throw new ZentrumException(ZentrumException.PASSWORT_FALSCH);
				}
				this.passwort = KryptoUtil.getInstance().hashPasswort(klartext);
			} else {
				throw new ZentrumException(ZentrumException.PASSWORT_NULL);
			}
		}

	}

	/**
	 * Set-Methoder fuer die Postleitzahl.
	 * 
	 * @param plz
	 *            Die Postleitzahl.
	 * @throws ZentrumException
	 *             Wenn bei der Validierung ein Fehler aufgetreten ist
	 */
	public void setPlz(String plz) throws ZentrumException {

		if (this.isFilter()) {
			this.plz = plz;
		} else {
			if (plz != null) {
				if (!plz.matches("\\d{5}")) {
					throw new ZentrumException(ZentrumException.PLZ_FALSCH);
				}
				this.plz = plz;
			} else {
				throw new ZentrumException(ZentrumException.PLZ_NULL);
			}
		}
	}

	/**
	 * Set-Methoder fuer die Strasse.
	 * 
	 * @param strasse
	 *            Die Strasse.
	 * @throws ZentrumException
	 *             Wenn bei der Validierung ein Fehler aufgetreten ist
	 */
	public void setStrasse(String strasse) throws ZentrumException {

		if (this.isFilter()) {
			this.strasse = strasse;
		} else {
			if (strasse != null) {
				if (!strasse.matches(".{3,50}")) {
					throw new ZentrumException(ZentrumException.STRASSE_FALSCH);
				}
				this.strasse = strasse;
			} else {
				throw new ZentrumException(ZentrumException.STRASSE_NULL);
			}
		}
	}

	/**
	 * Erzeugt einen String mit allen Angaben zum Zentrum.
	 * 
	 * @return Ein String mit allen Angaben zum Zentrum.
	 */
	@Override
	public String toString() {
		StringBuffer dummy = new StringBuffer();
		dummy.append("(ZentrumBean) ").append("Institution: ").append(
				this.getInstitution()).append("Abteilung: ").append(
				this.getAbteilung()).append("Ort: ").append(this.getOrt());

		return dummy.toString();
	}

	/**
	 * Getter für Aktivierungszustand
	 * 
	 * @return the istAktiviert
	 */
	public boolean getIstAktiviert() {
		return istAktiviert;
	}

	/**
	 * Setzt den Aktivierungszustand
	 * 
	 * @param istAktiviert
	 *            Setzt den Aktivierungszustand
	 */
	public void setIstAktiviert(boolean istAktiviert) {
		this.istAktiviert = istAktiviert;
	}

	/**
	 * Getter fuer AnsprechpartnerId
	 * 
	 * @return id des Ansprechpartners
	 */
	public long getAnsprechpartnerId() {
		return ansprechpartnerId;
	}

}
