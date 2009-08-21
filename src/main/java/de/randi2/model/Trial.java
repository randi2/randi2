package de.randi2.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.springframework.beans.factory.annotation.Configurable;

import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.model.enumerations.TrialStatus;
import de.randi2.model.randomization.AbstractRandomizationConfig;
import de.randi2.utility.validations.DateDependence;

@Entity
@Configurable
@DateDependence(firstDate = "startDate", secondDate = "endDate")
@EqualsAndHashCode(callSuper=true)
public class Trial extends AbstractDomainObject {

	private static final long serialVersionUID = -2424750074810584832L;

	@NotNull()
	@NotEmpty()
	@Length(max = MAX_VARCHAR_LENGTH)
	@Getter @Setter 
	private String name = "";
	
	@Length(max = MAX_VARCHAR_LENGTH)
	@Getter @Setter 
	private String abbreviation = "";
	
	@Getter @Setter 
	private boolean stratifyTrialSite;
	
	@Lob
	@Getter @Setter 
	private String description = "";
	
	@Getter @Setter 
	private GregorianCalendar startDate = null;
	
	@Getter @Setter 
	private GregorianCalendar endDate = null;
	
	@Getter @Setter 
	private File protocol = null;
	
	@NotNull
	@ManyToOne
	@Getter @Setter 
	private Person sponsorInvestigator = null;
	
	@NotNull
	@ManyToOne
	@Getter @Setter 
	private TrialSite leadingSite = null;
	
	@Enumerated(value = EnumType.STRING)
	@Getter @Setter 
	private TrialStatus status = TrialStatus.IN_PREPARATION;
	
	@ManyToMany
	@Getter @Setter 
	private Set<TrialSite> participatingSites = new HashSet<TrialSite>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "trial")
	@Getter @Setter 
	private List<TreatmentArm> treatmentArms = new ArrayList<TreatmentArm>();
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> subjectCriteria = new ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>>();
	
	@OneToOne(cascade = CascadeType.ALL)
	private AbstractRandomizationConfig randomConf;

	/**
	 * If true then the trial subject ids will be generated automatically by the
	 * system.
	 */
	@Getter @Setter 
	private boolean generateIds = true;


	public List<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> getCriteria() {
		return subjectCriteria;
	}

	public void setCriteria(
			List<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> inclusionCriteria) {
		this.subjectCriteria = inclusionCriteria;
	}

	public void addCriterion(
			AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>> criterion) {
		this.subjectCriteria.add(criterion);
	}

	public void addParticipatingSite(TrialSite participatingSite) {
		this.participatingSites.add(participatingSite);
	}


	public AbstractRandomizationConfig getRandomizationConfiguration() {
		return randomConf;
	}
	
	public void setRandomizationConfiguration(
			AbstractRandomizationConfig _randomizationConfiguration) {
		randomConf = _randomizationConfiguration;
		if (randomConf.getTrial() == null) {
			randomConf.setTrial(this);
		}
	}

	@Transient
	public List<TrialSubject> getSubjects() {
		List<TrialSubject> subjects = new ArrayList<TrialSubject>();
		for (TreatmentArm arm : treatmentArms) {
			subjects.addAll(arm.getSubjects());
		}
		return subjects;
	}
	
	@Transient
	public int getTotalSubjectAmount(){
		return getSubjects().size();
	}

	@Override
	public String getUIName() {
		return this.getAbbreviation();
	}
}