package de.randi2.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.NotNull;

@Entity
public class PersonRole extends AbstractDomainObject {

	@ManyToOne
	private Person person = null;
	private Trial trial = null;
	private Role role = null;
	
	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public Trial getTrial() {
		return trial;
	}
	public void setTrial(Trial trial) {
		this.trial = trial;
	}
	
	@NotNull
	public Role getRole() {
		return this.role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public List<Righ> getRights() {
		return role.getRights();
	}
	
	public boolean hasRight(Righ righ) {
		return trial == null && role.hasRight(righ);
	}
	
	public boolean hasRight(Righ righ, Trial _trial) {
		return trial != null && trial.equals(_trial) && role.hasRight(righ);
	}
	
	
}
