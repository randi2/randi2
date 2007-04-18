package de.randi2.model.fachklassen.beans;

import de.randi2.datenbank.Filter;
import de.randi2.model.exceptions.ZentrumException;

public class ZentrumBeanProxy extends Filter {
	
	ZentrumBean zentrum = null;
	
	private void checkProxy(){
		if(zentrum == null){
			// Holen des Proxy-Objekts
			zentrum = new ZentrumBean();
		}
	}
	
	
	public boolean equals(ZentrumBean zentrum){
		this.checkProxy();
		return zentrum.equals(zentrum);
	}
	
	public String getAbteilung() {
		this.checkProxy();
		return zentrum.getAbteilung();
	}
	
	public PersonBean getAnsprechpartner(){
		this.checkProxy();
		return zentrum.getAnsprechpartner();
	}
	
	public String getHausnr() {
		this.checkProxy();
		return zentrum.getHausnr();
	}
	
	public int getId() {
		this.checkProxy();
		return zentrum.getId();
	}
	
	public String getInstitution() {
		this.checkProxy();
		return zentrum.getInstitution();
	}
	
	public String getOrt() {
		this.checkProxy();
		return zentrum.getOrt();
	}
	
	public String getPasswort() {
		this.checkProxy();
		return zentrum.getPasswort();
	}
	
	public String getPlz() {
		this.checkProxy();
		return zentrum.getPlz();
	}
	
	public String getStrasse() {
		this.checkProxy();
		return zentrum.getStrasse();
	}
	
	public void setAbteilung(String abteilung) throws ZentrumException {
		this.checkProxy();
		zentrum.setAbteilung(abteilung);
	}
	
	public void setAnsprechpartner(PersonBean ansprechpartner) {
		this.checkProxy();
		zentrum.setAnsprechpartner(ansprechpartner);
	}
	
	public void setHausnr(String hausnr) throws ZentrumException {
		this.checkProxy();
		zentrum.setHausnr(hausnr);
	}
	
	public void setId(int id) {
		this.checkProxy();
		zentrum.setId(id);
		}
	
	public void setInstitution(String institution) throws ZentrumException {
		this.checkProxy();
		zentrum.setInstitution(institution);
	}
	
	public void setOrt(String ort) throws ZentrumException {
		this.checkProxy();
		zentrum.setOrt(ort);
	}
	
	public void setPasswort(String hash) throws ZentrumException {
		this.checkProxy();
		zentrum.setPasswort(hash);
	}
	
	public void setPasswortKlartext(String klartext) throws ZentrumException {
		this.checkProxy();
		zentrum.setPasswortKlartext(klartext);
	}
	
	public void setPlz(String plz) throws ZentrumException {
		this.checkProxy();
		zentrum.setPlz(plz);
	}
	
	public void setStrasse(String strasse) throws ZentrumException {
		this.checkProxy();
		zentrum.setStrasse(strasse);
	}
	
	public String toString() {
		this.checkProxy();
		return zentrum.toString();
	}
}
