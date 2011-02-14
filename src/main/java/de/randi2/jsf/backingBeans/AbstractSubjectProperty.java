package de.randi2.jsf.backingBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedProperty;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;
import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.jsf.controllerBeans.TrialHandler;
import de.randi2.jsf.converters.CriterionConverter;
import de.randi2.jsf.wrappers.CriterionWrapper;
import de.randi2.model.criteria.AbstractCriterion;

/**
 * 
 * @author L. Plotnicki
 * 
 */
public abstract class AbstractSubjectProperty {

	@ManagedProperty(value = "#{loginHandler}")
	@Getter
	@Setter
	protected LoginHandler loginHandler;

	@ManagedProperty(value = "#{trialHandler}")
	@Setter
	protected TrialHandler trialHandler;

	private CriterionConverter criterionConverter;

	public CriterionConverter getCriterionConverter() {
		if (criterionConverter == null)
			criterionConverter = new CriterionConverter(
					loginHandler.getChosenLocale());
		return criterionConverter;
	}

	@Getter
	@Setter
	private AbstractCriterion<?, ?> selectedCriterion;
	
	@Getter
	private String selectedCriterionString;
	
	
	public void setSelectedCriterionString(String selectedCriterionString) {
		this.selectedCriterionString = selectedCriterionString;
		try {
			selectedCriterion = criterionConverter.findGenderForl16edValue(selectedCriterionString);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	

	private List<SelectItem> criteriaItems;

	public List<SelectItem> getCriteriaItems() {
		if (criteriaItems == null) {
			criteriaItems = new ArrayList<SelectItem>();
			ResourceBundle rb = ResourceBundle.getBundle(
					"de.randi2.jsf.i18n.criteria",
					loginHandler.getChosenLocale());
			for (AbstractCriterion<?, ?> c : trialHandler.getCriteriaList()) {
				String s = rb.getString(c.getClass()
						.getName());
				criteriaItems.add(new SelectItem(s, s));
			}
		}
		return criteriaItems;
	}

	protected ArrayList<CriterionWrapper<? extends Serializable>> criteria = null;

	@SuppressWarnings("unchecked")
	public void addCriterion(ActionEvent event) {
		System.out.println("Small test");
		if (selectedCriterion != null)
			try {
				if (criteria == null)
					getCriteria();
				criteria.add(new CriterionWrapper<Serializable>(
						(AbstractCriterion<Serializable, ?>) selectedCriterion
								.getClass().newInstance(), loginHandler
								.getChosenLocale()));
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
}
