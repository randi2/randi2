package de.randi2.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

import de.randi2.model.subjectproperties.AbstractProperty;

@Entity
public class TrialSubject extends AbstractDomainObject{

	private String identification;
	
	private TreatmentArm arm;
	
	@Transient
	private List<AbstractProperty> properties;

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

	public List<AbstractProperty> getProperties() {
		return properties;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public void setProperties(List<AbstractProperty> properties) {
		this.properties = properties;
	}
	
	
	
	
	
}
