package de.randi2.model;

import java.io.File;
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
import de.randi2.model.enumerations.TrialStatus;
import de.randi2.model.randomization.AbstractRandomizationConfig;
import de.randi2.utility.validations.DateDependence;

@Entity
@Configurable
@DateDependence(firstDate = "startDate", secondDate = "endDate")
public class Trial extends AbstractDomainObject {

	@NotNull()
	@NotEmpty()
	@Length(max = MAX_VARCHAR_LENGTH)
	private String name = "";
	@Lob
	private String description = "";
	private GregorianCalendar startDate = null;
	private GregorianCalendar endDate = null;
	private File protocol = null;
	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	private Person sponsorInvestigator = null;
	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	private TrialSite leadingCenter = null;
	@Enumerated(value = EnumType.STRING)
	private TrialStatus status = TrialStatus.IN_PREPARATION;
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<TrialSite> participatingSites = new HashSet<TrialSite>();
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "trial")
	private List<TreatmentArm> treatmentArms = new ArrayList<TreatmentArm>();
	@OneToMany(cascade = CascadeType.ALL)
	private List<AbstractCriterion> inclusionCriteria;
	@OneToOne
	private AbstractRandomizationConfig randomConf;

	public List<AbstractCriterion> getInclusionCriteria() {
		return inclusionCriteria;
	}

	public TrialSite getLeadingCenter() {
		return leadingCenter;
	}

	public void setInclusionCriteria(List<AbstractCriterion> inclusionCriteria) {
		this.inclusionCriteria = inclusionCriteria;
	}

	public void setLeadingCenter(TrialSite leadingCenter) {
		this.leadingCenter = leadingCenter;
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

	public void setParticipatingSites(Set<TrialSite> participatingCenters) {
		this.participatingSites = participatingCenters;
	}

	public void setLeadingSite(TrialSite center) {
		this.leadingCenter = center;

	}

	public TrialSite getLeadingSite() {
		return this.leadingCenter;
	}

	public void addParticipatingSite(TrialSite participatingCenter) {
		this.participatingSites.add(participatingCenter);
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
			for (TrialSubject subject : arm.getSubjects()) {
				subjects.add(subject);
			}
		}
		return subjects;
	}

	public void randomize(TrialSubject subject) {
		TreatmentArm assignedArm = randomConf.getAlgorithm().randomize(subject);
		subject.setArm(assignedArm);
		assignedArm.addSubject(subject);
	}

	@Override
	public String toString() {
		return "Trial: " + getName();
	}
}