package de.randi2.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class Role extends AbstractDomainObject{
	
	private String name = "";
	private List<Righ> rights = new ArrayList<Righ>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Righ> getRights() {
		return rights;
	}
	public void setRights(List<Righ> rights) {
		this.rights = rights;
	}
	public boolean hasRight(Righ righ) {
		return rights.contains(righ);
	}
	
}
