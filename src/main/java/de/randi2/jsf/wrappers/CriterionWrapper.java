package de.randi2.jsf.wrappers;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.DateCriterion;
import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.FreeTextCriterion;
import de.randi2.model.criteria.OrdinalCriterion;


/**
 * UI Wrapper for the Criterion
 * 
 * @author Lukasz Plotnicki
 * 
 */
public class CriterionWrapper {

	private AbstractCriterion<?, ?> wrappedCriterion = null;
	
	private boolean isConstraint = true;
	
	private String panelType = "criterionErrorPanel";
	
	public CriterionWrapper(AbstractCriterion<?, ?> _criterion){
		wrappedCriterion = _criterion;
	}
	
	public AbstractCriterion<?, ?> getWrappedCriterion() {
		return wrappedCriterion;
	}
	
	public void setWrappedCriterion(AbstractCriterion<?, ?> wrappedCriterion) {
		this.wrappedCriterion = wrappedCriterion;
	}
	
	public boolean isConstraint() {
		return isConstraint;
	}
	
	public void setConstraint(boolean isConstraint) {
		this.isConstraint = isConstraint;
	}
	
	public String getL16edName(){
		return ResourceBundle.getBundle( "de.randi2.jsf.i18n.criteria",((LoginHandler) FacesContext.getCurrentInstance()
			    .getApplication().getELResolver().getValue(
			    	      FacesContext.getCurrentInstance().getELContext(), null,
			    	      "loginHandler")).getChosenLocale()).getString(wrappedCriterion.getUIName());
	}

	public String getPanelType() {
		if(DateCriterion.class.isInstance(wrappedCriterion))
			panelType = "datePanel";
		else if(DichotomousCriterion.class.isInstance(wrappedCriterion))
			panelType = "dichotomousPanel";
		else if(FreeTextCriterion.class.isInstance(wrappedCriterion))
			panelType = "freeTextPanel";
		else if(OrdinalCriterion.class.isInstance(wrappedCriterion))
			panelType = "ordinalPanel";
		return panelType;
	}
	
	public void addElement(ActionEvent event) {
		if(OrdinalCriterion.class.isInstance(wrappedCriterion))
			((OrdinalCriterion)wrappedCriterion).getElements().add("");
	}

	public void removeElement(ActionEvent event) {
		if(OrdinalCriterion.class.isInstance(wrappedCriterion))
			((OrdinalCriterion)wrappedCriterion).getElements().remove(((OrdinalCriterion)wrappedCriterion).getElements().size() -1);
	}
	
	public boolean isElementsEmpty(){
		if(OrdinalCriterion.class.isInstance(wrappedCriterion))
			return ((OrdinalCriterion)wrappedCriterion).getElements().isEmpty();
		return true;
	}
}
