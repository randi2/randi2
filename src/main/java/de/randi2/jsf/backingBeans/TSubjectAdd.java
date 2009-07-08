package de.randi2.jsf.backingBeans;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.faces.context.FacesContext;

import com.icesoft.faces.context.ByteArrayResource;
import com.icesoft.faces.context.FileResource;
import com.icesoft.faces.context.Resource;

import de.randi2.jsf.controllerBeans.TrialHandler;
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
	
	private Popups popups;
	
	private TrialService trialService;
	
	
	public TrialService getTrialService() {
		return trialService;
	}
	
	public void setTrialService(TrialService trialService) {
		this.trialService = trialService;
	}

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
	
	public TSubjectAdd() {
		popups = ((Popups) FacesContext.getCurrentInstance().getApplication()
				.getELResolver().getValue(
						FacesContext.getCurrentInstance().getELContext(), null,
						"popups"));
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public String addSubject() {
		HashSet<SubjectProperty<?>> tempSet = new HashSet<SubjectProperty<?>>();
		for(CriterionWrapper<? extends Serializable> cw : properties){
			tempSet.add((SubjectProperty) cw.getSubjectProperty());
		}
		getTrialSubject().setProperties(tempSet);
		currentTrial = trialService.randomize(currentTrial, getTrialSubject());
		subjectID = getTrialSubject().getIdentification();
		subjectArm = getTrialSubject().getArm().getUIName();
		popups.showSubjectAddedPopup();
		return Randi2.SUCCESS;
	}
	
	private String subjectArm;
	
	public String getSubjectArm() {
		return subjectArm;
	}
	
	private String subjectID;
	
	public String getSubjectID() {
		return subjectID;
	}
}
