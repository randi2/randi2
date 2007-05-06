/**
 * Das Beans-Package
 */
package de.randi2.model.fachklassen.beans;

import java.util.Vector;

import de.randi2.datenbank.Filter;
import de.randi2.model.exceptions.StudienarmException;
import de.randi2.model.fachklassen.Studie;
import de.randi2.model.fachklassen.beans.PatientBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.utility.NullKonstanten;

/**
 * Diese Klasse ist ein Datencontainer fuer einen Studienarm.
 * 
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
	 * Status dieses Studienarms (Wert der ENUM Status der Studien-Fachklasse).
	 */
	private Studie.Status aStatus = null;

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
	 * Die Id der Studier welcher dieser Arm zugeordnet ist.
	 */
	private long aStudieId= NullKonstanten.NULL_LONG;

	/**
	 * Die zugeordneten Patienten als Beans.
	 */
	private Vector<PatientBean> aPatienten = null;

	/**
	 * Der Standardkonstruktor.
	 * 
	 */
	public StudienarmBean() {
		super();
	}

	

	/**
	 * Der Konstruktor eines StudienarmBeans.
	 * 
	 * 
	 * @param id
	 *            Die Id in der Datenbank
	 * @param studieId
	 *            Die Id der Studie zu welcher dieser Arm gehoert
	 * @param status
	 *            Der Status dieses Studienarms, wie Status der uebergeordneten
	 *            Studie
	 * @param bezeichnung
	 *            Die Bezeichnung (Name) dieses Arms
	 * @param beschreibung
	 *            Die laengere Beschreibung dieses Arms
	 * @throws StudienarmException
	 */
	public StudienarmBean(long id, long studieId, Studie.Status status,
			String bezeichnung, String beschreibung) throws StudienarmException{
		super();
		this.setId(id);
		this.setStudieId(studieId);
		this.aStatus = status;
		this.setBezeichnung(bezeichnung);
		this.setBeschreibung(beschreibung);

	}

	/**
	 * Liefert den spezifischen Status dieses Arms.
	 * 
	 * @return Der Status dieses Arms
	 */
	public Studie.Status getAStatus() {
		return aStatus;
	}

	/**
	 * Setzt den spezifischen Status dieses Studienarms.
	 * 
	 * @param status
	 *            Der zusetzende Status.
	 */
	public void setAStatus(Studie.Status status) {
		aStatus = status;
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
		if(aStudie==null) {
			aStudie = Studie.get(aStudieId);
		}
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
		//TODO Proxy Logik
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
	 * Liefert die Anzahl der zugeordneten Patienten.
	 * 
	 * @return die Anzahl der diesem Arm zugeordneten Patienten
	 */
	public int getPatAnzahl() {

		return aPatienten.size();

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
	
	/**
	 * Liefert Id der Studie welcher dieser Arm zugeordnet ist.
	 * @return
	 */
	public long getStudieId() {
		return aStudieId;
	}

	/**
	 * Setzt Id der Studie welcher dieser Arm zugeordnet ist.
	 * @param studieId
	 */
	public void setStudieId(long studieId) {
		aStudieId = studieId;
	}

}
