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
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import lombok.Setter;

import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.jsf.supportBeans.Randi2;
import de.randi2.jsf.wrappers.CriterionWrapper;
import de.randi2.model.SubjectProperty;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.services.TrialService;
import de.randi2.unsorted.ContraintViolatedException;

/**
 * @author Natalie Waskowzow
 *
 */

@ManagedBean(name = "responseAdd")
@SessionScoped
public class ResponseAdd {

	private Trial currentTrial = null;
	
	private ArrayList<CriterionWrapper<? extends Serializable>> properties = null;
	
	@ManagedProperty(value = "#{loginHandler}")
	@Setter
	private LoginHandler loginHandler;
	
	private List<SelectItem> selectItems = null;
	
	private SubjectProperty<String> responseProperty=null;
	
	private TrialSubject tSubject = new TrialSubject();

	private String tSubjectID="";
	
	private boolean tSubjectIdentified;
	
	@ManagedProperty(value = "#{trialService}")
	@Setter
	private TrialService trialService;
	
	private String responsePropertyValue;
	
	
	
	
	@SuppressWarnings("unchecked")
	public void setCurrentTrial(Trial currentTrial) {
		this.currentTrial = currentTrial;
		properties = new ArrayList<CriterionWrapper<? extends Serializable>>();
		CriterionWrapper<? extends Serializable> cWrapper = null;
		for (AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>> c : currentTrial
				.getCriteria()) {
			cWrapper = new CriterionWrapper<Serializable>(
					(AbstractCriterion<Serializable, ?>) c,
					loginHandler.getChosenLocale());
			properties.add(cWrapper);
		}
	}
	
	
	public String addResponse(){
		
		trialService.addResponse(currentTrial, tSubject);
		resetResponse();
		return Randi2.SUCCESS;
	}
	
	public List<SelectItem> getSelectItems() {
		if (selectItems == null) {
			selectItems = new ArrayList<SelectItem>();
			String option1=currentTrial.getTreatmentResponse().getOption1();
			String option2=currentTrial.getTreatmentResponse().getOption2();
			selectItems.add(new SelectItem(option1,option1));
			selectItems.add(new SelectItem(option2,option2));
		}
		return selectItems;
	}
	
	
	public SubjectProperty<String> getResponseProperty(){
		if (responseProperty == null) {
			responseProperty = new SubjectProperty<String>(currentTrial.getTreatmentResponse());
		}
		return responseProperty;
	}
	
	
	
	public void setResponsePropertyValue(String responsePropertyValue) {
		try {
			this.getResponseProperty().setValue(responsePropertyValue);
			this.responsePropertyValue=responsePropertyValue;
		} catch (ContraintViolatedException e) {
			e.printStackTrace();
		}
	}


	public String getResponsePropertyValue() {
		return responsePropertyValue;
	}


	public boolean istSubjectIdentified() {
		return tSubjectIdentified;
	}

	
	public TrialSubject gettSubject() {
		return tSubject;
	}
	
	public String gettSubjectID(){
		return tSubjectID;
	}
	
	public void settSubjectID (String tSubjectID){
		this.tSubjectID=tSubjectID;
		if(currentTrial!=null){
			List<TrialSubject> tSubjects = currentTrial.getSubjects();
			for(TrialSubject tSubject:tSubjects){
				if(tSubject.getIdentification().equals(tSubjectID)){
					this.tSubject = tSubject;
					if (tSubject.getResponseProperty() == null) {
						this.tSubject.setResponseProperty(this
								.getResponseProperty());
					}
					this.tSubjectIdentified = true;
					break;
				}
			}
		}
	}
	
	public boolean isResponseAdded(String tSubjectId){
		if(currentTrial!=null){
			List<TrialSubject> tSubjects = currentTrial.getSubjects();
			for(TrialSubject tSubject:tSubjects){
				if(tSubject.getIdentification().equals(tSubjectId)){
					if (tSubject.getResponseProperty() == null) {
						return false;
					}else{
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public Trial getCurrentTrial() {
		return currentTrial;
	}
	
	private void resetResponse() {
		tSubject = new TrialSubject();
		tSubjectID = null;
		tSubjectIdentified=false;
	}
	
	
}
