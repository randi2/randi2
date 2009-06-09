package de.randi2.jsf.wrappers;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.DateCriterion;
import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.FreeTextCriterion;
import de.randi2.model.criteria.OrdinalCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;

/**
 * UI Wrapper for the Criterion
 * 
 * @author Lukasz Plotnicki
 * 
 */
public class CriterionWrapper {

	/**
	 * The criterion object which is wrapped by this instance.
	 */
	private AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>> wrappedCriterion = null; 
	
	/**
	 * Flag indicating if the wrapped criterion is also an inclusion constraint or not.
	 */
	private boolean isConstraint = false;

	/**
	 * String ID defining the showed criterion panel.
	 */
	private String panelType = "criterionErrorPanel";

	public CriterionWrapper(AbstractCriterion<?, ?> _criterion) {
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

	/**
	 * Retrurn the l16ed name of an criterion.
	 * @return l16ed string representation of an criterion
	 */
	public String getL16edName() {
		return ResourceBundle.getBundle(
				"de.randi2.jsf.i18n.criteria",
				((LoginHandler) FacesContext.getCurrentInstance()
						.getApplication().getELResolver().getValue(
								FacesContext.getCurrentInstance()
										.getELContext(), null, "loginHandler"))
						.getChosenLocale()).getString(
				wrappedCriterion.getUIName());
	}

	/**
	 * Returns the String ID of an panel whicz need
	 * @return
	 */
	public String getPanelType() {
		if (DateCriterion.class.isInstance(wrappedCriterion))
			panelType = "datePanel";
		else if (DichotomousCriterion.class.isInstance(wrappedCriterion))
			panelType = "dichotomousPanel";
		else if (FreeTextCriterion.class.isInstance(wrappedCriterion))
			panelType = "freeTextPanel";
		else if (OrdinalCriterion.class.isInstance(wrappedCriterion))
			panelType = "ordinalPanel";
		return panelType;
	}

	public void addElement(ActionEvent event) {
		if (OrdinalCriterion.class.isInstance(wrappedCriterion))
			OrdinalCriterion.class.cast(wrappedCriterion).getElements().add("");
	}

	public void removeElement(ActionEvent event) {
		if (OrdinalCriterion.class.isInstance(wrappedCriterion))
			OrdinalCriterion.class.cast(wrappedCriterion).getElements()
					.remove(
							OrdinalCriterion.class.cast(wrappedCriterion).getElements()
									.size() - 1);
	}

	public boolean isElementsEmpty() {
		if (OrdinalCriterion.class.isInstance(wrappedCriterion))
			return OrdinalCriterion.class.cast(wrappedCriterion).getElements()
					.isEmpty();
		return true;
	}
}
