/* 
 * (c) 2008- RANDI2 Core Development Team
 * 
 * This file is part of RANDI2.
 * 
 * RANDI2 is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * RANDI2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * RANDI2. If not, see <http://www.gnu.org/licenses/>.
 */
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

/**
 * The Class Trial.
 */
@Entity
@Configurable
@DateDependence(firstDate = "startDate", secondDate = "endDate")
@EqualsAndHashCode(callSuper=true, exclude={"randomConf", "participatingSites", "sponsorInvestigator", "subjectCriteria"})
public class Trial extends AbstractDomainObject {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2424750074810584832L;

	/** The name. */
	@NotNull()
	@NotEmpty()
	@Length(max = MAX_VARCHAR_LENGTH)
	@Getter 
    @Setter 
	private String name = "";
	
	/** The abbreviation. */
	@Length(max = MAX_VARCHAR_LENGTH)
	@Getter 
    @Setter 
	private String abbreviation = "";
	
	/**
	 * Checks if is stratify trial site.
	 * 
	 * @return true, if is stratify trial site
	 */
	@Getter 
	@Setter 
	private boolean stratifyTrialSite;
	
	/** The description. */
	@Lob
	@Getter 
	@Setter 
	private String description = "";
	
	@Getter 
	@Setter 
	private GregorianCalendar startDate = null;
	
	@Getter
	@Setter 
	private GregorianCalendar endDate = null;
	
	@Getter 
	@Setter 
	private File protocol = null;
	
	/** The sponsor investigator. */
	@NotNull
	@ManyToOne
	
	@Getter 
	@Setter 
	private Person sponsorInvestigator = null;
	
	/** The leading site. */
	@NotNull
	@ManyToOne
	@Getter 
	@Setter 
	private TrialSite leadingSite = null;
	
	/** The status. */
	@Enumerated(value = EnumType.STRING)
	@Getter 
	@Setter 
	private TrialStatus status = TrialStatus.IN_PREPARATION;
	
	/** The participating sites. */
	@ManyToMany
	@Getter 
	@Setter 
	private Set<TrialSite> participatingSites = new HashSet<TrialSite>();
	
	/** The treatment arms. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "trial")
	@Getter @Setter 
	private List<TreatmentArm> treatmentArms = new ArrayList<TreatmentArm>();
	
	/** The subject criteria. */
	@OneToMany(cascade = CascadeType.ALL)
	private List<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> subjectCriteria = new ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>>();
	
	/** The random conf. */
	@OneToOne(cascade = CascadeType.ALL)
	private AbstractRandomizationConfig randomConf;

	/**
	 * If true then the trial subject ids will be generated automatically by the
	 * system.
	 */
	
	/**
	 * Checks if is generate ids.
	 * 
	 * @return true, if is generate ids
	 */
	@Getter 
	@Setter 
	private boolean generateIds = true;


	/**
	 * Get criteria.
	 * 
	 * @return the criteria
	 */
	public List<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> getCriteria() {
		return subjectCriteria;
	}

	/**
	 * Set criteria.
	 * 
	 * @param inclusionCriteria
	 *            the inclusion criteria
	 */
	public void setCriteria(
			List<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> inclusionCriteria) {
		this.subjectCriteria = inclusionCriteria;
	}

	/**
	 * Adds the criterion.
	 * 
	 * @param criterion
	 *            the criterion
	 */
	public void addCriterion(
			AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>> criterion) {
		this.subjectCriteria.add(criterion);
	}

	/**
	 * Adds the participating site.
	 * 
	 * @param participatingSite
	 *            the participating site
	 */
	public void addParticipatingSite(TrialSite participatingSite) {
		this.participatingSites.add(participatingSite);
	}


	/**
	 * Gets the randomization configuration.
	 * 
	 * @return the randomization configuration
	 */
	public AbstractRandomizationConfig getRandomizationConfiguration() {
		return randomConf;
	}
	
	/**
	 * Sets the randomization configuration.
	 * 
	 * @param _randomizationConfiguration
	 *            the new randomization configuration
	 */
	public void setRandomizationConfiguration(
			AbstractRandomizationConfig _randomizationConfiguration) {
		randomConf = _randomizationConfiguration;
		if (randomConf.getTrial() == null) {
			randomConf.setTrial(this);
		}
	}

	/**
	 * Gets the subjects.
	 * 
	 * @return the subjects
	 */
	@Transient
	public List<TrialSubject> getSubjects() {
		List<TrialSubject> subjects = new ArrayList<TrialSubject>();
		for (TreatmentArm arm : treatmentArms) {
			subjects.addAll(arm.getSubjects());
		}
		return subjects;
	}
	
	/**
	 * Gets the total subject amount.
	 * 
	 * @return the total subject amount
	 */
	@Transient
	public int getTotalSubjectAmount(){
		return getSubjects().size();
	}
	
	/**
	 * Specifies if the trial is a fresh trial (without any subjects)
	 * @return
	 */
	@Transient 
	public boolean isFresh(){
		return !(getTotalSubjectAmount()>0);
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.AbstractDomainObject#getUIName()
	 */
	@Override
	public String getUIName() {
		return this.getAbbreviation();
	}
}