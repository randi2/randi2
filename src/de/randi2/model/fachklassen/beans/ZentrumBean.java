package de.randi2.model.fachklassen.beans;

import de.randi2.datenbank.Filter;
import de.randi2.model.exceptions.ZentrumException;
import de.randi2.utility.*;

/**
 * Diese Klasse repraesentiert ein Zentrum.
 * 
 * @author Lukasz Plotnicki <lplotni@stud.hs-heilbronn.de>
 * @version $Id$
 * 
 */
public class ZentrumBean extends Filter {

	/**
	 * Interne ID des Zentrums
	 */
	private int id = NullKonstanten.NULL_INT;

	/**
	 * Name der Institution.
	 */
	private String institution = null;

	/**
	 * Name der Abteilung in der Institution.
	 */
	private String abteilung = null;

	/**
	 * Ort
	 */
	private String ort = null;

	/**
	 * Postleitzahl
	 */
	private String plz = null;

	/**
	 * Strasse
	 */
	private String strasse = null;

	/**
	 * Hausnummer
	 */
	private String hausnr = null;

	/**
	 * Ein PersonBean mit der Daten des Ansprechpartners in dem Zentrum.
	 */
	private PersonBean ansprechpartner = null;

	/**
	 * Passwort für das Zentrum (gehasht)
	 */
	private String passwort = null;

	/**
	 * Der volle Konstruktor dieser Klasse, an den alle Attribute uebergeben
	 * werden.
	 * 
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
	 * @param ansprechpartner
	 *            PersonBean, das dem Ansprechpartner in dem Zentrum entspricht.
	 * @param passwortHash
	 *            String - Passwort bereits gehasht.
	 */
	public ZentrumBean(int id, String institution, String abteilung,
			String ort, String plz, String strasse, String hausnr,
			PersonBean ansprechpartner, String passwortHash) {

		this.setId(id);
		try {
			this.setInstitution(institution);
			this.setAbteilung(abteilung);
			this.setOrt(ort);
			this.setPlz(plz);
			this.setStrasse(strasse);
			this.setHausnr(hausnr);
			this.setAnsprechpartner(ansprechpartner);
			this.setPasswort(passwortHash);
		} catch (ZentrumException e) {
			// TODO Wenn die Vorgehensweise in diesem Fall geklärt wird, wird es
			// auch umgesetzt.
			e.printStackTrace();
		}

	}

	/**
	 * Einfacher Konstruktor von dieser Klasse.
	 */
	public ZentrumBean() {

	}

	/**
	 * @return the abteilung
	 */
	public String getAbteilung() {
		return abteilung;
	}

	/**
	 * @param abteilung
	 *            the abteilung to set
	 * @throws ZentrumException
	 */
	public void setAbteilung(String abteilung) throws ZentrumException {
		if (this.isFilter()) {
			this.abteilung = abteilung;
		} else {
			if (abteilung != null) {
				if (!abteilung.matches(".{3,70}"))
					throw new ZentrumException(
							ZentrumException.ABTEILUNG_FALSCH);
				this.abteilung = abteilung;
			} else {
				throw new ZentrumException(ZentrumException.ABTEILUNG_NULL);
			}
		}

	}

	/**
	 * @return the ansprechpartner
	 */
	public PersonBean getAnsprechpartner() {
		return ansprechpartner;
	}

	/**
	 * @param ansprechpartner
	 *            the ansprechpartner to set
	 */
	public void setAnsprechpartner(PersonBean ansprechpartner) {
		// Die Überprüfung wird schon bei PersonBean durchgeführ - das Objekt,
		// was hier übergeben wird ist auf jeden Fall korrekt.
		this.ansprechpartner = ansprechpartner;
	}

	/**
	 * @return the hausnr
	 */
	public String getHausnr() {
		return hausnr;
	}

	/**
	 * @param hausnr
	 *            the hausnr to set
	 * @throws ZentrumException
	 */
	public void setHausnr(String hausnr) throws ZentrumException {
		if (this.isFilter()) {
			this.hausnr = hausnr;
		} else {
			if (hausnr != null) {
				if (!hausnr.matches("\\d{1,4}[a-b]{0,2}"))
					throw new ZentrumException(ZentrumException.HAUSNR_FALSCH);
				this.hausnr = hausnr;
			} else {
				throw new ZentrumException(ZentrumException.HAUSNR_NULL);
			}
		}
	}

	/**
	 * @return the institution
	 */
	public String getInstitution() {
		return institution;
	}

	/**
	 * @param institution
	 *            the institution to set
	 * @throws ZentrumException
	 */
	public void setInstitution(String institution) throws ZentrumException {

		if (this.isFilter()) {
			this.institution = institution;
		} else {
			if (institution != null) {
				if (!institution.matches(".{3,70}"))
					throw new ZentrumException(
							ZentrumException.INSTITUTION_FALSCH);
				this.institution = institution;
			} else {
				throw new ZentrumException(ZentrumException.INSTITUTION_NULL);
			}
		}
	}

	/**
	 * @return the ort
	 */
	public String getOrt() {
		return ort;
	}

	/**
	 * @param ort
	 *            the ort to set
	 * @throws ZentrumException
	 */
	public void setOrt(String ort) throws ZentrumException {

		if (this.isFilter()) {
			this.ort = ort;
		} else {
			if (ort != null) {
				if (!ort.matches(".{3,50}"))
					throw new ZentrumException(ZentrumException.ORT_FALSCH);
				this.ort = ort;
			} else {
				throw new ZentrumException(ZentrumException.ORT_NULL);
			}
		}

	}

	/**
	 * @return the passwort
	 */
	public String getPasswort() {
		return passwort;
	}

	/**
	 * Diese Methode ist zu benutzten, wenn das Passwort, das gesetzt werden
	 * soll, noch in Klartext Form vorhanden ist und erstmal gehashed werden
	 * muss.
	 * 
	 * @param passwort
	 *            String Passwort - in Klartext-Form
	 * @throws ZentrumException
	 *             wenn die Validierung nicht erfolgreich war
	 */
	public void setPasswortKlartext(String klartext) throws ZentrumException {
		if (this.isFilter()) {
			this.passwort = PasswortUtil.getInstance().hashPasswort(klartext);
		} else {
			if (klartext != null) {
				if (!(klartext.matches(".*[A-Za-z].*")
						&& klartext.matches(".*[0-9].*")
						&& klartext
								.matches(".*[\\^,.\\-#+;:_'*!\"§$@&%/()=?|<>].*") && klartext
						.matches(".{12}")))
					throw new ZentrumException(ZentrumException.PASSWORT_FALSCH);
				this.passwort = PasswortUtil.getInstance().hashPasswort(
						klartext);
			} else {
				throw new ZentrumException(ZentrumException.PASSWORT_NULL);
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
		if (hash == null || hash.equals("")) {
			throw new ZentrumException(ZentrumException.PASSWORT_NULL);
		}
		this.passwort = hash;
	}

	/**
	 * @return the plz
	 */
	public String getPlz() {
		return plz;
	}

	/**
	 * @param plz
	 *            the plz to set
	 * @throws ZentrumException
	 */
	public void setPlz(String plz) throws ZentrumException {

		if (this.isFilter()) {
			this.plz = plz;
		} else {
			if (plz != null) {
				if (!plz.matches("\\d{5}"))
					throw new ZentrumException(ZentrumException.PLZ_FALSCH);
				this.plz = plz;
			} else {
				throw new ZentrumException(ZentrumException.PLZ_NULL);
			}
		}
	}

	/**
	 * @return the strasse
	 */
	public String getStrasse() {
		return strasse;

	}

	/**
	 * @param strasse
	 *            the strasse to set
	 * @throws ZentrumException
	 */
	public void setStrasse(String strasse) throws ZentrumException {

		if (this.isFilter()) {
			this.strasse = strasse;
		} else {
			if (strasse != null) {
				if (!strasse.matches(".{3,50}"))
					throw new ZentrumException(ZentrumException.STRASSE_FALSCH);
				this.strasse = strasse;
			} else {
				throw new ZentrumException(ZentrumException.STRASSE_NULL);
			}
		}
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer dummy = new StringBuffer();
		dummy.append("(ZentrumBean) ").append("Institution: ").append(
				this.getInstitution()).append("Abteilung: ").append(
				this.getAbteilung()).append("Ort: ").append(this.getOrt());

		return dummy.toString();
	}

	/**
	 * Methode die zwei Objekte dieser Klasse bzgl. allen ihrere Eigenschaften
	 * vergleicht.
	 * 
	 * @param zentrum
	 *            Objekt, das mit aktueller Instanz verglichen werden soll.
	 * @return true, wenn die beide Objekte voellig uebereinstimmen, ansonsten
	 *         false
	 */
	public boolean equals(ZentrumBean zentrum) {
		if (!zentrum.getInstitution().equals(this.getInstitution())) {
			return false;
		}
		if (!zentrum.getAbteilung().equals(this.getAbteilung())) {
			return false;
		}
		if (!zentrum.getAnsprechpartner().equals(this.getAnsprechpartner())) {
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
	}

}
