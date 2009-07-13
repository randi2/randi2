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

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.springframework.beans.factory.annotation.Configurable;

import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.model.enumerations.TrialStatus;
import de.randi2.model.randomization.AbstractRandomizationConfig;
import de.randi2.utility.validations.DateDependence;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Configurable
@DateDependence(firstDate = "startDate", secondDate = "endDate")
public class Trial extends AbstractDomainObject {

	private static final long serialVersionUID = -2424750074810584832L;

	@NotNull()
	@NotEmpty()
	@Length(max = MAX_VARCHAR_LENGTH)
	private String name = "";
	@Length(max = MAX_VARCHAR_LENGTH)
	private String abbreviation = "";

	@Lob
	private String description = "";
	private GregorianCalendar startDate = null;
	private GregorianCalendar endDate = null;
	private File protocol = null;
	@NotNull
	@ManyToOne
	private Person sponsorInvestigator = null;
	@NotNull
	@ManyToOne
	private TrialSite leadingSite = null;
	@Enumerated(value = EnumType.STRING)
	private TrialStatus status = TrialStatus.IN_PREPARATION;
	@ManyToMany
	private Set<TrialSite> participatingSites = new HashSet<TrialSite>();
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "trial")
	private List<TreatmentArm> treatmentArms = new ArrayList<TreatmentArm>();
	@OneToMany(cascade = CascadeType.ALL)
	private List<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> subjectCriteria = new ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>>();
	@OneToOne(cascade = CascadeType.ALL)
	private AbstractRandomizationConfig randomConf;

	/**
	 * If true then the trial subject ids will be generated automatically by the
	 * system.
	 */
	private boolean generateIds = true;

	public boolean isGenerateIds() {
		return generateIds;
	}

	public void setGenerateIds(boolean generateIds) {
		this.generateIds = generateIds;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String _name) {

		if (_name == null) {
			_name = "";
		}

		this.name = _name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		if (abbreviation == null)
			abbreviation = "";
		this.abbreviation = abbreviation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String _description) {
		if (_description == null) {
			_description = "";
		}
		this.description = _description;
	}

	public GregorianCalendar getStartDate() {
		return startDate;
	}

	public void setStartDate(GregorianCalendar startDate) {
		this.startDate = startDate;
	}

	public GregorianCalendar getEndDate() {
		return endDate;
	}

	public void setEndDate(GregorianCalendar endDate) {
		this.endDate = endDate;
	}

	public TrialStatus getStatus() {
		return status;
	}

	public void setStatus(TrialStatus status) {
		this.status = status;
	}

	public File getProtocol() {
		return protocol;
	}

	public void setProtocol(File protocol) {
		this.protocol = protocol;
	}

	public Set<TrialSite> getParticipatingSites() {
		return this.participatingSites;
	}

	public void setParticipatingSites(Set<TrialSite> participatingSites) {
		this.participatingSites = participatingSites;
	}

	public void setLeadingSite(TrialSite leadingSite) {
		this.leadingSite = leadingSite;

	}

	public TrialSite getLeadingSite() {
		return this.leadingSite;
	}

	public void addParticipatingSite(TrialSite participatingSite) {
		this.participatingSites.add(participatingSite);
	}

	public Person getSponsorInvestigator() {
		return sponsorInvestigator;
	}

	public void setSponsorInvestigator(Person sponsorInvestigator) {
		this.sponsorInvestigator = sponsorInvestigator;
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

	public void setTreatmentArms(List<TreatmentArm> treatmentArms) {
		this.treatmentArms = treatmentArms;
	}

	public List<TreatmentArm> getTreatmentArms() {
		return treatmentArms;
	}

	@Transient
	public List<TrialSubject> getSubjects() {
		List<TrialSubject> subjects = new ArrayList<TrialSubject>();
		for (TreatmentArm arm : treatmentArms) {
			subjects.addAll(arm.getSubjects());
		}
		return subjects;
	}

	@Deprecated
	public void randomize(TrialSubject subject) {
		TreatmentArm assignedArm = randomConf.getAlgorithm().randomize(subject);
		subject.setArm(assignedArm);
		assignedArm.addSubject(subject);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(getId()).append(name).append(
				treatmentArms).toString();
	}

	@Override
	public String getUIName() {
		return this.getAbbreviation();
	}
}