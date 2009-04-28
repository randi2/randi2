package de.randi2.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;


@Entity
public class TrialSubject extends AbstractDomainObject{

	private static final long serialVersionUID = 4469807155833123516L;
	
	private String identification;

	@ManyToOne
	private TreatmentArm arm;
	
	@OneToMany
	private Set<SubjectProperty> properties =  new HashSet<SubjectProperty>();

	@NotNull
	@NotEmpty
	@Length(max=MAX_VARCHAR_LENGTH)
	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	public TreatmentArm getArm() {
		return arm;
	}

	public void setArm(TreatmentArm arm) {
		this.arm = arm;
	}

	public Set<SubjectProperty> getProperties() {
		return properties;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public void setProperties(Set<SubjectProperty> properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return identification;
	}
	
	
	
	
	
	
	
}
