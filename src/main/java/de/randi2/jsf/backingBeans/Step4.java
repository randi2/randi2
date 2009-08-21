package de.randi2.jsf.backingBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import de.randi2.jsf.controllerBeans.LoginHandler;
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

	private TrialHandler trialHandler;

	public void setTrialHandler(TrialHandler trialHandler) {
		this.trialHandler = trialHandler;
	}

	private LoginHandler loginHandler;

	public void setLoginHandler(LoginHandler loginHandler) {
		this.loginHandler = loginHandler;
	}

	private AutoCompleteObject<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> criteriaAC = null;

	public AutoCompleteObject<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> getCriteriaAC() {
		if (criteriaAC == null) {
			List<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> cList = trialHandler
					.getCriteriaList();
			criteriaAC = new AutoCompleteObject<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>>(
					cList);
			ResourceBundle rb = ResourceBundle.getBundle(
					"de.randi2.jsf.i18n.criteria", loginHandler
							.getChosenLocale());
			for (SelectItem si : criteriaAC.getObjectList()) {
				si.setLabel(rb.getString(si.getLabel()));
			}
		}
		return criteriaAC;
	}

	private ArrayList<CriterionWrapper<? extends Serializable>> criteria = null;

	@SuppressWarnings("unchecked")
	public void addCriterion(ActionEvent event) {
		if (criteriaAC.isObjectSelected())
			try {
				getCriteria().add(
						new CriterionWrapper<Serializable>(
								(AbstractCriterion<Serializable, ?>) criteriaAC
										.getSelectedObject().getClass()
										.newInstance()));
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
	}

	public void removeCriterion(ActionEvent event) {
		getCriteria().remove(this.getCriteria().size() - 1);
	}

	public boolean isCriteriaEmpty() {
		return criteria == null || criteria.isEmpty();
	}

	public ArrayList<CriterionWrapper<? extends Serializable>> getCriteria() {
		if (criteria == null)
			criteria = new ArrayList<CriterionWrapper<? extends Serializable>>();
		return criteria;
	}

}
