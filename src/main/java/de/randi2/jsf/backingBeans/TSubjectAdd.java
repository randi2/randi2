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
package de.randi2.jsf.backingBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import lombok.Getter;
import lombok.Setter;
import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.jsf.supportBeans.Popups;
import de.randi2.jsf.supportBeans.Randi2;
import de.randi2.jsf.wrappers.CriterionWrapper;
import de.randi2.model.SubjectProperty;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.services.TrialService;

public class TSubjectAdd {
	@Setter
	private Popups popups;

	@Setter
	private LoginHandler loginHandler;

	@Setter
	private TrialService trialService;

	@Getter
	private String addedSubjectArm;
	
	@Getter
	private String addedSubjectID;

	@Getter
	@Setter
	private String subjectID;

	private Trial currentTrial = null;

	public Trial getCurrentTrial() {
		return currentTrial;
	}

	@SuppressWarnings("unchecked")
	public void setCurrentTrial(Trial currentTrial) {
		this.currentTrial = currentTrial;
		properties = new ArrayList<CriterionWrapper<? extends Serializable>>();
		CriterionWrapper<? extends Serializable> cWrapper = null;
		for (AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>> c : currentTrial
				.getCriteria()) {
			cWrapper = new CriterionWrapper<Serializable>(
					(AbstractCriterion<Serializable, ?>) c);
			properties.add(cWrapper);
		}
	}

	@Getter
	private TrialSubject trialSubject = new TrialSubject();


	private ArrayList<CriterionWrapper<? extends Serializable>> properties = null;

	public ArrayList<CriterionWrapper<? extends Serializable>> getProperties() {
		return properties;
	}

	@SuppressWarnings({ "rawtypes" })
	public String addSubject() {
		if (!currentTrial.isGenerateIds())
			trialSubject.setIdentification(subjectID);
		HashSet<SubjectProperty<?>> tempSet = new HashSet<SubjectProperty<?>>();
		for (CriterionWrapper<? extends Serializable> cw : properties) {
			tempSet.add((SubjectProperty) cw.getSubjectProperty());
		}
		trialSubject.setProperties(tempSet);
		currentTrial = trialService.randomize(currentTrial, trialSubject);
		addedSubjectID = trialSubject.getIdentification();
		addedSubjectArm = trialSubject.getArm().getUIName();
		popups.showSubjectAddedPopup();
		resetSubject();
		return Randi2.SUCCESS;
	}

	private void resetSubject() {
		trialSubject = new TrialSubject();
		subjectID = null;
	}

}
