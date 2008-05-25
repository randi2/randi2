package de.randi2.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class Role extends AbstractDomainObject{
	
	private String name = "";
	private List<Right> rights = new ArrayList<Right>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Right> getRights() {
		return rights;
	}
	public void setRights(List<Right> rights) {
		this.rights = rights;
	}
	public boolean hasRight(Right righ) {
		return rights.contains(righ);
	}
	
}
