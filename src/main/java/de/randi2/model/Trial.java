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
import de.randi2.model.randomization.AbstractRandomizationTempData;
import de.randi2.model.randomization.BaseRandomizationConfig;
import de.randi2.randomization.RandomizationAlgorithm;
import de.randi2.utility.validations.DateDependence;

@Entity
@Configurable
@DateDependence(firstDate = "startDate", secondDate = "endDate")
public class Trial extends AbstractDomainObject {

	@Transient
	private RandomizationAlgorithm<?, ?> algorithm;
	
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

	@OneToMany(cascade = CascadeType.ALL, mappedBy="trial")
	private List<TreatmentArm> treatmentArms = new ArrayList<TreatmentArm>();

	@OneToMany(cascade = CascadeType.ALL)
	private List<AbstractCriterion> inclusionCriteria;

	@OneToOne
	private BaseRandomizationConfig randomizationConfiguration;
	@OneToOne
	private AbstractRandomizationTempData randomizationTempData;

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

	public BaseRandomizationConfig getRandomizationConfiguration() {
		return randomizationConfiguration;
	}

	public void setRandomizationConfiguration(
			BaseRandomizationConfig randomizationConfiguration) {
		this.randomizationConfiguration = randomizationConfiguration;
	}

	public AbstractRandomizationTempData getRandomizationTempData() {
		return randomizationTempData;
	}

	public void setRandomizationTempData(
			AbstractRandomizationTempData randomizationTempData) {
		this.randomizationTempData = randomizationTempData;
	}

	public void setTreatmentArms(List<TreatmentArm> treatmentArms) {
		this.treatmentArms = treatmentArms;
	}

	public List<TreatmentArm> getTreatmentArms() {
		return treatmentArms;
	}

	public void randomize(TrialSubject subject) {
		initAlgorithm();
		algorithm.randomize(subject);
	}

	private void initAlgorithm() {
		if (algorithm == null) {
			algorithm = randomizationConfiguration.getAlgorithm(this);
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "TO_STRING_NOT_IMPLEMENTED";
	}
}