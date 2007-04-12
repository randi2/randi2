/**
 * 
 */
package de.randi2.model.fachklassen.beans;

import java.util.GregorianCalendar;
import java.util.Vector;

/**
 * @author 
 *
 */
public class StudieBean {

	private String name = null;
	
	private String beschreibung = null;
	
	private GregorianCalendar startDatum = null;
	
	private GregorianCalendar endDatum = null;
	
	private String studienprotokoll_pfad = null;
	
	private Vector<StudienarmBean> studienarme = null;
	
	private RandomisationBean randomisationseigenschaften = null;
	
	private ZentrumBean zentrum = null;
	
	private BenutzerkontoBean benutzerkonto = null;

	private int status = -1;
	
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
	 * @return the beschreibung
	 */
	public String getBeschreibung() {
		return beschreibung;
	}

	/**
	 * @param beschreibung the beschreibung to set
	 */
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	/**
	 * @return the endDatum
	 */
	public GregorianCalendar getEndDatum() {
		return endDatum;
	}

	/**
	 * @param endDatum the endDatum to set
	 */
	public void setEndDatum(GregorianCalendar endDatum) {
		this.endDatum = endDatum;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the randomisationseigenschaften
	 */
	public RandomisationBean getRandomisationseigenschaften() {
		return randomisationseigenschaften;
	}

	/**
	 * @param randomisationseigenschaften the randomisationseigenschaften to set
	 */
	public void setRandomisationseigenschaften(
			RandomisationBean randomisationseigenschaften) {
		this.randomisationseigenschaften = randomisationseigenschaften;
	}

	/**
	 * @return the startDatum
	 */
	public GregorianCalendar getStartDatum() {
		return startDatum;
	}

	/**
	 * @param startDatum the startDatum to set
	 */
	public void setStartDatum(GregorianCalendar startDatum) {
		this.startDatum = startDatum;
	}

	/**
	 * @return the studienarme
	 */
	public Vector<StudienarmBean> getStudienarme() {
		return studienarme;
	}

	/**
	 * @param studienarme the studienarme to set
	 */
	public void setStudienarme(Vector<StudienarmBean> studienarme) {
		this.studienarme = studienarme;
	}

	/**
	 * @return the studienprotokoll_pfad
	 */
	public String getStudienprotokoll_pfad() {
		return studienprotokoll_pfad;
	}

	/**
	 * @param studienprotokoll_pfad the studienprotokoll_pfad to set
	 */
	public void setStudienprotokoll_pfad(String studienprotokoll_pfad) {
		this.studienprotokoll_pfad = studienprotokoll_pfad;
	}

	/**
	 * @return the zentrum
	 */
	public ZentrumBean getZentrum() {
		return zentrum;
	}

	/**
	 * @param zentrum the zentrum to set
	 */
	public void setZentrum(ZentrumBean zentrum) {
		this.zentrum = zentrum;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
