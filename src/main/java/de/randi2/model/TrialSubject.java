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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

import de.randi2.unsorted.ContraintViolatedException;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(exclude={"arm"})
public class TrialSubject extends AbstractDomainObject {

	private static final long serialVersionUID = 4469807155833123516L;

	@NotNull
	@NotEmpty
	@Length(max = MAX_VARCHAR_LENGTH)
	private String identification;

	@NotNull
	@NotEmpty
	@Length(max = MAX_VARCHAR_LENGTH)
	private String randNumber;
	
	private int counter;
	
	public int getCounter() {
		return counter;
	}
	
	public void setCounter(int counter) {
		this.counter = counter;
	}

	@ManyToOne
	private TrialSite trialSite;

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	private TreatmentArm arm;

	@OneToMany(cascade = CascadeType.PERSIST)
//	@ManyToOne(cascade = CascadeType.ALL)
	private Set<SubjectProperty<?>> properties = new HashSet<SubjectProperty<?>>();


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
		StringBuffer result = new StringBuffer();
		for (String l : stratum) {
			result.append(l + ";");
		}
		return result.toString();
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
}
