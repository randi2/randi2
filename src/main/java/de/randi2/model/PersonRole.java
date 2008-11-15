package de.randi2.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.NotNull;

@Entity
public class PersonRole extends AbstractDomainObject {

	@ManyToOne
	private Person person = null;
	@ManyToOne
	private Trial trial = null;
	@ManyToOne
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
	
	public List<Right> getRights() {
		return role.getRights();
	}
	
	public boolean hasRight(Right righ) {
		return trial == null && role.hasRight(righ);
	}
	
	public boolean hasRight(Right righ, Trial _trial) {
		return trial != null && trial.equals(_trial) && role.hasRight(righ);
	}
	
	
}
