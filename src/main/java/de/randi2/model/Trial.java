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

import static de.randi2.utility.ArithmeticUtil.cartesianProduct;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.springframework.beans.factory.annotation.Configurable;

import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.model.enumerations.TrialStatus;
import de.randi2.model.randomization.AbstractRandomizationConfig;
import de.randi2.utility.Pair;
import de.randi2.utility.StrataNameIDWrapper;
import de.randi2.utility.validations.DateDependence;

/**
 * The Class Trial.
 */
@Entity
@Configurable
@DateDependence(firstDate = "startDate", secondDate = "endDate")
@EqualsAndHashCode(callSuper = true, exclude = { "randomConf",
		"participatingSites", "sponsorInvestigator", "subjectCriteria" })
@NamedQuery(name = "trial.AllTrialsWithSpecificParticipatingTrialSite", query = "select trial from Trial as trial join trial.participatingSites site where site.id = ?")
@ToString(callSuper=true, exclude={"participatingSites", "protocol", "treatmentArms", "leadingSite", "subjectCriteria"})
public class Trial extends AbstractDomainObject {

	public static final Comparator<TrialSubject> SUBJECT_COUNT_COMPERATOR = new Comparator<TrialSubject>() {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public int compare(TrialSubject o1, TrialSubject o2) {
			return (o1.getCounter() - o2.getCounter());
		}

	};

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
	@NotNull
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
	@Getter
	@Setter
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
	 * Adds the criterion, if the criterion is equals null nothing happens.
	 * 
	 * @param criterion
	 *            the criterion
	 */
	public void addCriterion(
			AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>> criterion) {
		if(criterion != null)
		this.subjectCriteria.add(criterion);
	}

	/**
	 * Adds the participating site.
	 * 
	 * @param participatingSite
	 *            the participating site
	 */
	public void addParticipatingSite(TrialSite participatingSite) {
		if(participatingSite!=null)
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
	 * Sets the randomization configuration. If the trial object of the
	 * randomisation config is equals null the setter set the this trial.
	 * 
	 * @param _randomizationConfiguration
	 *            the new randomization configuration
	 */
	public void setRandomizationConfiguration(
			AbstractRandomizationConfig _randomizationConfiguration) {
		randomConf = _randomizationConfiguration;
		if (randomConf != null && randomConf.getTrial() == null){
			randomConf.setTrial(this);
		}
	}

	/**
	 * Gets all subjects from this trial.
	 * 
	 * @return the subjects
	 */
	@Transient
	public List<TrialSubject> getSubjects() {
		List<TrialSubject> subjects = new ArrayList<TrialSubject>();
		for (TreatmentArm arm : treatmentArms) {
			subjects.addAll(arm.getSubjects());
		}
		Collections.sort(subjects, SUBJECT_COUNT_COMPERATOR);
		return subjects;
	}

	/**
	 * Gets the total subject amount.
	 * 
	 * @return the total subject amount
	 */
	@Transient
	public int getTotalSubjectAmount() {
		return getSubjects().size();
	}

	@Transient
	public int getPlannedSubjectAmount() {
		int amount = 0;
		for (TreatmentArm arm : treatmentArms) {
			amount += arm.getPlannedSubjects();
		}
		return amount;
	}

	/**
	 * Specifies if the trial is a fresh trial (without any subjects)
	 * 
	 * @return
	 */
	@Transient
	public boolean isFresh() {
		return !(getTotalSubjectAmount() > 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.randi2.model.AbstractDomainObject#getUIName()
	 */
	@Override
	public String getUIName() {
		return this.getAbbreviation();
	}

	@SuppressWarnings("unchecked")
	public Pair<List<String>, List<String>> getAllStrataIdsAndNames() {
		List<String> strataIdsResult = new ArrayList<String>();
		List<String> strataNamesResult = new ArrayList<String>();

		HashMap<AbstractCriterion<?, ?>, List<AbstractConstraint<?>>> temp = new HashMap<AbstractCriterion<?, ?>, List<AbstractConstraint<?>>>();
		for (AbstractCriterion<?, ?> cr : getCriteria()) {
			List<AbstractConstraint<?>> list = new ArrayList<AbstractConstraint<?>>();
			for (AbstractConstraint<?> co : cr.getStrata()) {
				list.add(co);
			}
			temp.put(cr, list);
		}
		Set<Set<StrataNameIDWrapper>> strataIds = new HashSet<Set<StrataNameIDWrapper>>();
		// minimum one constraint
		if (temp.size() >= 1) {
			for (AbstractCriterion<?, ?> cr : temp.keySet()) {
				Set<StrataNameIDWrapper> strataLevel = new HashSet<StrataNameIDWrapper>();
				for (AbstractConstraint<?> co : temp.get(cr)) {
					StrataNameIDWrapper wrapper = new StrataNameIDWrapper();
					wrapper.setStrataId(cr.getId() + "_" + co.getId());
					wrapper.setStrataName(cr.getName() + "_" + co.getUIName());
					strataLevel.add(wrapper);
				}
				if (temp.get(cr).isEmpty()) {
					StrataNameIDWrapper wrapper = new StrataNameIDWrapper();
					wrapper.setStrataId(cr.getId() + "_" + -1);
					wrapper.setStrataName("");
					strataLevel.add(wrapper);
				}
				if (!strataLevel.isEmpty()) {
					strataIds.add(strataLevel);
				}
			}
			// cartesianProduct only necessary for more then one criterions
			if (strataIds.size() >= 2) {
				strataIds = cartesianProduct(strataIds.toArray(new HashSet[0]));
			} else {
				Set<StrataNameIDWrapper> tempStrataIds = strataIds.iterator()
						.next();
				Set<Set<StrataNameIDWrapper>> tempStrataIdsSet = new HashSet<Set<StrataNameIDWrapper>>();
				for (StrataNameIDWrapper wrapper : tempStrataIds) {
					Set<StrataNameIDWrapper> next = new HashSet<StrataNameIDWrapper>();
					next.add(wrapper);
					tempStrataIdsSet.add(next);
				}
				strataIds = tempStrataIdsSet;
			}
			for (Set<StrataNameIDWrapper> set : strataIds) {
				List<StrataNameIDWrapper> stringStrat = new ArrayList<StrataNameIDWrapper>();
				for (StrataNameIDWrapper string : set) {
					stringStrat.add(string);
				}
				Collections.sort(stringStrat);

				String stratId = "";
				String stratName = "";
				for (StrataNameIDWrapper s : stringStrat) {
					stratId += s.getStrataId() + ";";
					if (!s.getStrataName().isEmpty())
						stratName += s.getStrataName() + ";";
				}
				// strata and stratified with trial site
				if (isStratifyTrialSite()) {
					for (TrialSite site : getParticipatingSites()) {
						String strataId = site.getId() + "__" + stratId;
						strataIdsResult.add(strataId);
						strataNamesResult.add(site.getName() + " | "
								+ stratName);
					}

				}
				// strata and stratified without trial site
				else {
					strataIdsResult.add(stratId);
					strataNamesResult.add(stratName);
				}
			}
		} else if (isStratifyTrialSite()) { // stratified only by trial site
			for (TrialSite site : getParticipatingSites()) {
				String strataId = site.getId() + "__";
				strataIdsResult.add(strataId);
				strataNamesResult.add(site.getName());
			}

		}
		return Pair.of(strataIdsResult, strataNamesResult);
	}
}