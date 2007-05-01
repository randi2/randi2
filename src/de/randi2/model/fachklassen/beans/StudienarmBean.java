/**
 * 
 */
package de.randi2.model.fachklassen.beans;

import java.util.Vector;

import de.randi2.datenbank.Filter;
import de.randi2.utility.NullKonstanten;

/**
 * Diese Klasse ist ein Datencontainer fuer einen Studienarm.
 * 
 * @TODO Feld aktiv? was ist das? Nur 0 oder 1? Boolean? Wird der Vector von
 *       PatientenIDs ueberhaupt gebraucht? Wenn ja, eigener Konstruktor?
 * 
 * @author Daniel Haehn [dhaehn@stud.hs-heilbronn.de]
 * @version $Id$
 */
public class StudienarmBean extends Filter {

	/**
	 * Die Id des Studienarms.
	 */
	private long id = NullKonstanten.NULL_LONG;

	/**
	 * ??? noch zu klaeren
	 */
	private int aAktiv = NullKonstanten.NULL_INT;

	/**
	 * Der kurze Name des Arms.
	 */
	private String aBezeichnung = null;

	/**
	 * Die laengere Beschreibung des Arms.
	 */
	private String aBeschreibung = null;

	/**
	 * Die Studie welcher dieser Arm zugeordnet ist.
	 */
	private StudieBean aStudie = null;

	/**
	 * Die zugeordneten Patienten als Beans.
	 */
	private Vector<PatientBean> aPatienten = null;

	/**
	 * Die IDs der zugeordneten Patienten.
	 */
	private Vector<Integer> aPatientenIDs = null;

	/**
	 * Der Standardkonstruktor.
	 * 
	 */
	public StudienarmBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Der Konstruktor eines StudienarmBeans.
	 * 
	 * 
	 * @param id
	 *            Die Id in der Datenbank
	 * @param studie
	 *            Die Studie zu welcher dieser Arm gehoert
	 * @param aktiv
	 *            ??? s.o.
	 * @param bezeichnung
	 *            Die Bezeichnung (Name) dieses Arms
	 * @param beschreibung
	 *            Die laengere Beschreibung dieses Arms
	 * @param patienten
	 *            Der Vector von PatientBeans die diesem Arm zugeordnet sind
	 */
	public StudienarmBean(long id, StudieBean studie, int aktiv,
			String bezeichnung, String beschreibung,
			Vector<PatientBean> patienten) {

		super();
		this.setId(id);
		this.setStudie(studie);
		this.setAktiv(aktiv);
		this.setBezeichnung(bezeichnung);
		this.setBeschreibung(beschreibung);
		this.setPatienten(patienten);

		// IDs zu den Patienten holen und speichern
		for (int i = 0; i < this.getPatienten().size(); i++) {

			Vector<Long> aPatientenIDs = new Vector<Long>();

			aPatientenIDs.add(this.getPatienten().elementAt(i).getId());

		}

	}

	/**
	 * ??? s.o.
	 * 
	 * @return
	 */
	public int getAktiv() {
		return aAktiv;
	}

	/**
	 * ??? s.o.
	 * 
	 * 
	 * @param aktiv
	 */
	public void setAktiv(int aktiv) {
		aAktiv = aktiv;
	}

	/**
	 * Liefert die (laengere) Beschreibung dieses Arms.
	 * 
	 * @return die Beschreibung als String
	 */
	public String getBeschreibung() {
		return aBeschreibung;
	}

	/**
	 * Setzt die (laengere) Beschreibung dieses Arms.
	 * 
	 * @param beschreibung
	 *            die Beschreibung als String
	 */
	public void setBeschreibung(String beschreibung) {
		aBeschreibung = beschreibung;
	}

	/**
	 * Liefert die Bezeichnung/Name dieses Arms.
	 * 
	 * @return die Bezeichnung
	 */
	public String getBezeichnung() {
		return aBezeichnung;
	}

	/**
	 * Setzt die Bezeichnung/Name dieses Arms.
	 * 
	 * @param bezeichnung
	 *            die zusetzende Bezeichnung
	 */
	public void setBezeichnung(String bezeichnung) {
		aBezeichnung = bezeichnung;
	}

	/**
	 * Liefert die Studie der dieser Arm zugeordnet ist.
	 * 
	 * @return die Studie als StudieBean
	 */
	public StudieBean getStudie() {
		return aStudie;
	}

	/**
	 * Setzt die Studie der dieser Arm zugeordnet ist.
	 * 
	 * @param studie
	 *            die zuzuordnende Studie als StudieBean
	 */
	public void setStudie(StudieBean studie) {
		aStudie = studie;
	}

	/**
	 * Liefert die zugeordneten Patienten als Vector von PatientBeans.
	 * 
	 * @return die zugeordneten Patienten als PatientBean
	 */
	public Vector<PatientBean> getPatienten() {
		return aPatienten;
	}

	/**
	 * Setzt die Patienten als Vector von PatientBeans.
	 * 
	 * @param patienten
	 *            die zuzuordnenden Patienten als Vector von PatientBeans
	 */
	public void setPatienten(Vector<PatientBean> patienten) {
		aPatienten = patienten;
	}

	/**
	 * Liefert die IDs der zugeordneten Patienten als Vektor.
	 * 
	 * @return der Vector von PatientenIDs
	 */
	public Vector getPatientenIDs() {
		return aPatientenIDs;
	}

	/**
	 * Setzt den Vector der zugeordneten Patienten.
	 * 
	 * @param patientenIDs
	 *            der Vector von PatientenIDs
	 */
	public void setPatientenIDs(Vector<Integer> patientenIDs) {
		aPatientenIDs = patientenIDs;
	}

	/**
	 * Liefert die Anzahl der zugeordneten Patienten.
	 * 
	 * @return die Anzahl der diesem Arm zugeordneten Patienten
	 */
	public int getPatAnzahl() {
		// TODO
		return NullKonstanten.NULL_INT;

	}

	/**
	 * Liefert die ID dieses Arms in der Datenbank.
	 * 
	 * @return die ID dieses Studienarms
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setzt die ID dieses Arms in der Datenbank.
	 * 
	 * @param id
	 *            die ID dieses Studienarms
	 */
	public void setId(long id) {
		this.id = id;
	}

}
