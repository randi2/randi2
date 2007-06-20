package de.randi2.model.fachklassen.beans;

import de.randi2.datenbank.Filter;

public class StrataAuspraegungBean extends Filter {
	
	private String name;
	private StrataBean strata;
	
	public StrataAuspraegungBean(){}
	
	public StrataAuspraegungBean(String name, StrataBean strata){
		this.setName(name);
		this.setStrata(strata);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StrataBean getStrata() {
		return strata;
	}

	public void setStrata(StrataBean strata) {
		this.strata = strata;
	}
}
