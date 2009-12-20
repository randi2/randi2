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

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import com.icesoft.faces.context.ByteArrayResource;
import com.icesoft.faces.context.FileResource;
import com.icesoft.faces.context.Resource;

import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.jsf.controllerBeans.TrialHandler;
import de.randi2.jsf.supportBeans.Popups;
import de.randi2.jsf.supportBeans.Randi2;
import de.randi2.jsf.wrappers.CriterionWrapper;
import de.randi2.model.SubjectProperty;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.utility.BoxedException;
import de.randi2.services.TrialService;

public class TSubjectAdd {
	
	@Setter
	private Popups popups;
	
	@Setter
	private LoginHandler loginHandler;

	@Setter
	private TrialService trialService;
	
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
	
	private TrialSubject trialSubject;
	
	public TrialSubject getTrialSubject(){
		if(trialSubject==null)
			trialSubject= new TrialSubject();
		return trialSubject;
	}
	

	private ArrayList<CriterionWrapper<? extends Serializable>> properties = null;
	
	public ArrayList<CriterionWrapper<? extends Serializable>> getProperties() {
		return properties;
	}

	public Resource getTempProtocol() {
		if (currentTrial != null && currentTrial.getProtocol() != null)
			return new FileResource(currentTrial.getProtocol());
		else
			try {
				return new ByteArrayResource(TrialHandler
						.toByteArray(FacesContext.getCurrentInstance()
								.getExternalContext().getResourceAsStream(
										"/protocol.pdf")));
			} catch (MalformedURLException e) {
				BoxedException.throwBoxed(e);
			} catch (IOException e) {
				BoxedException.throwBoxed(e);
			}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public String addSubject() {
		trialSubject = new TrialSubject();
		HashSet<SubjectProperty<?>> tempSet = new HashSet<SubjectProperty<?>>();
		for(CriterionWrapper<? extends Serializable> cw : properties){
			tempSet.add((SubjectProperty) cw.getSubjectProperty());
		}
		trialSubject.setProperties(tempSet);
		currentTrial = trialService.randomize(currentTrial,trialSubject);
		subjectID = trialSubject.getIdentification();
		subjectArm = trialSubject.getArm().getUIName();
		popups.showSubjectAddedPopup();
		return Randi2.SUCCESS;
	}
	
	@Getter
	private String subjectArm;

	@Getter
	private String subjectID;
}
