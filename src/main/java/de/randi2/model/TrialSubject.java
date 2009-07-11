package de.randi2.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

import de.randi2.unsorted.ContraintViolatedException;


@Entity
public class TrialSubject extends AbstractDomainObject{

	private static final long serialVersionUID = 4469807155833123516L;
	
	private String identification;

	private String randNumber;
	
	
	@ManyToOne
	private TreatmentArm arm;
	
	@OneToMany(cascade=CascadeType.PERSIST)
	private Set<SubjectProperty<?>> properties =  new HashSet<SubjectProperty<?>>();

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

	public Set<SubjectProperty<?>> getProperties() {
		return properties;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public void setProperties(Set<SubjectProperty<?>> properties) {
		this.properties = properties;
	}

	
	

	@NotNull
	@NotEmpty
	@Length(max=MAX_VARCHAR_LENGTH)
	public String getRandNumber() {
		return randNumber;
	}

	public void setRandNumber(String randNumber) {
		this.randNumber = randNumber;
	}

	@Override
	public String toString() {
		return identification;
	}

	@SuppressWarnings("unchecked")
	@Transient
	public String getStratum(){
		List<Long> stratum = new ArrayList<Long>();
		for(SubjectProperty p:properties){
			try {
				stratum.add(p.getStratum());
			} catch (ContraintViolatedException e) {
				e.printStackTrace();
			}
		}
		Collections.sort(stratum);
		String result = "";
		for(long l : stratum){
			result += "_" +l;
		}
		return result;
	}
	
	
@Override
	public String getUIName() {
		return identification;
	}
	
	
}
