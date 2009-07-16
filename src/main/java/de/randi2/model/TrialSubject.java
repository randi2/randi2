package de.randi2.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
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
public class TrialSubject extends AbstractDomainObject {

	private static final long serialVersionUID = 4469807155833123516L;

	private String identification;

	private String randNumber;

	@ManyToOne
	private TrialSite trialSite;

	@ManyToOne
	private TreatmentArm arm;

	@OneToMany(cascade = CascadeType.PERSIST)
	private Set<SubjectProperty<?>> properties = new HashSet<SubjectProperty<?>>();

	@NotNull
	@NotEmpty
	@Length(max = MAX_VARCHAR_LENGTH)
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

	public TrialSite getTrialSite() {
		return trialSite;
	}

	public void setTrialSite(TrialSite trialSite) {
		this.trialSite = trialSite;
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
	@Length(max = MAX_VARCHAR_LENGTH)
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
	/**
	 * Generate the stratum identification string for the actual trial subject.
	 * [criterion_id]_[constraint_id];[criterion_id]_[constraint_id];...
	 */
	public String getStratum() {
		List<String> stratum = new ArrayList<String>();
		for (SubjectProperty p : properties) {
			try {
				stratum.add(p.getCriterion().getId() + "_" + p.getStratum());
			} catch (ContraintViolatedException e) {
				e.printStackTrace();
			}
		}
		Collections.sort(stratum);
		String result = "";
		for (String l : stratum) {
			result += l + ";";
		}
		return result;
	}

	@Override
	public String getUIName() {
		return identification;
	}
	
	@Deprecated
	public String getPropertiesUIString(){
		StringBuilder stringB = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		for(SubjectProperty<?> p : getProperties()){
			stringB.append(p.getCriterion().getName()).append(": ");
			if(GregorianCalendar.class.isInstance(p.getValue()))
				stringB.append(sdf.format(((GregorianCalendar)p.getValue()).getTime()));
			else
				stringB.append(p.getValue().toString());
			stringB.append("|");
		}
		return stringB.toString();
	}
	
	public int getNr(){
		return 1;
	}

}
