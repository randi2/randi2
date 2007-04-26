/**
 * 
 */
package de.randi2.model.fachklassen.beans;

import java.util.Vector;

import de.randi2.datenbank.Filter;
import de.randi2.utility.NullKonstanten;

/**
 * TODO ProxyPattern noch nich angewandt!
 * 
 * @author hype
 *
 */
public class StudienarmBean extends Filter {
	
	private long id = NullKonstanten.NULL_LONG;

	private int aAktiv = NullKonstanten.NULL_INT;
	
	private String aBezeichnung = null;
	
	private String aBeschreibung = null;
	
	private StudieBean aStudie = null;
	
	private Vector<PatientBean> aPatienten = null;
	
	private Vector<Integer> aPatientenIDs = null;

	public StudienarmBean() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
	public StudienarmBean(long id, StudieBean studie, int aktiv, String bezeichnung, String beschreibung, Vector<PatientBean> patienten) {
		
		super();
		this.setId(id);
		this.setStudie(studie);
		this.setAktiv(aktiv);
		this.setBezeichnung(bezeichnung);
		this.setBeschreibung(beschreibung);
		this.setPatienten(patienten);
		
		// IDs zu den Patienten holen und speichern
		for (int i=0; i<this.getPatienten().size();i++) {
			
			Vector<Long> aPatientenIDs = new Vector<Long>();
			
			aPatientenIDs.add(this.getPatienten().elementAt(i).getId());
			
		}
		
	}
	

	

	public int getAktiv() {
		return aAktiv;
	}

	public void setAktiv(int aktiv) {
		aAktiv = aktiv;
	}

	public String getBeschreibung() {
		return aBeschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		aBeschreibung = beschreibung;
	}

	public String getBezeichnung() {
		return aBezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		aBezeichnung = bezeichnung;
	}

	public StudieBean getStudie() {
		return aStudie;
	}

	public void setStudie(StudieBean studie) {
		aStudie = studie;
	}
	
	
	
	public Vector<PatientBean> getPatienten() {
		return aPatienten;
	}

	public void setPatienten(Vector<PatientBean> patienten) {
		aPatienten = patienten;
	}

	public Vector getPatientenIDs() {
		return aPatientenIDs;
	}

	public void setPatientenIDs(Vector patientenIDs) {
		aPatientenIDs = patientenIDs;
	}

	public int getPatAnzahl() {
		// TODO
		return NullKonstanten.NULL_INT;
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	
}
