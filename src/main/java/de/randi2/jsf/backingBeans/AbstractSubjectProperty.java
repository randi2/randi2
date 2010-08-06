package de.randi2.jsf.backingBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;
import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.jsf.utility.AutoCompleteObject;
import de.randi2.jsf.wrappers.CriterionWrapper;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
/**
 * 
 * @author lplotni
 *
 */
public abstract class AbstractSubjectProperty {

	@Getter
	@Setter
	private LoginHandler loginHandler;

	protected AutoCompleteObject<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> criteriaAC = null;

	protected ArrayList<CriterionWrapper<? extends Serializable>> criteria = null;

	@SuppressWarnings("unchecked")
	public void addCriterion(ActionEvent event) {
		if (criteriaAC.isObjectSelected())
			try {
				if(criteria==null)
					getCriteria();
				criteria.add(
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
		criteria.remove(this.getCriteria().size() - 1);
	}

	public boolean isCriteriaEmpty() {
		return getCriteria().isEmpty();
	}

	public ArrayList<CriterionWrapper<? extends Serializable>> getCriteria() {
		if (criteria == null)
			criteria = new ArrayList<CriterionWrapper<? extends Serializable>>();
		return criteria;
	}

	public void clean() {
		criteria = null;
	}

	protected void initCriteriaAC(ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> cList) {
		criteriaAC = new AutoCompleteObject<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>>(
				cList);
		ResourceBundle rb = ResourceBundle.getBundle(
				"de.randi2.jsf.i18n.criteria", getLoginHandler()
						.getChosenLocale());
		for (SelectItem si : criteriaAC.getObjectList()) {
			si.setLabel(rb.getString(si.getLabel()));
		}
	}

}
