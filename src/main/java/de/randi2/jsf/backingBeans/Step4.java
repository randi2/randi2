package de.randi2.jsf.backingBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import de.randi2.jsf.controllerBeans.TrialHandler;
import de.randi2.jsf.utility.AutoCompleteObject;
import de.randi2.jsf.wrappers.CriterionWrapper;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;

/**
 * <p>
 * This class wrapped the subject property configuration's functionality.
 * </p>
 * 
 * @author Lukasz Plotnicki <l.plotnicki@gmail.com>
 * 
 */
public class Step4 {

	private AutoCompleteObject<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> criteriaAC = null;

	public AutoCompleteObject<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> getCriteriaAC() {
		if (criteriaAC == null) {
			List<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> cList = ((TrialHandler) FacesContext
					.getCurrentInstance().getApplication().getELResolver()
					.getValue(FacesContext.getCurrentInstance().getELContext(),
							null, "trialHandler")).getCriteriaList();
			criteriaAC = new AutoCompleteObject<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>>(
					cList);
		}
		return criteriaAC;
	}

	private ArrayList<CriterionWrapper> criteria = null;

	public void addCriterion(ActionEvent event) {
		if (criteriaAC.isObjectSelected())
			getCriteria().add(
					new CriterionWrapper(criteriaAC.getSelectedObject()));
	}

	public void removeCriterion(ActionEvent event) {
		getCriteria().remove(this.getCriteria().size() - 1);
	}

	public boolean isCriteriaEmpty() {
		return criteria == null || criteria.isEmpty();
	}

	public ArrayList<CriterionWrapper> getCriteria() {
		if (criteria == null)
			criteria = new ArrayList<CriterionWrapper>();
		return criteria;
	}

}
