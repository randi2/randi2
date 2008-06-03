package de.randi2.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CollectionOfElements;

@Entity
public class Role extends AbstractDomainObject{
	
	private String name = "";
	
	@CollectionOfElements(fetch=FetchType.EAGER)
	@Enumerated(value=EnumType.STRING) 
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
