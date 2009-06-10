package de.randi2.jsf.wrappers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.model.SubjectProperty;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.DateCriterion;
import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.FreeTextCriterion;
import de.randi2.model.criteria.OrdinalCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.unsorted.ContraintViolatedException;

/**
 * UI Wrapper for the Criterion
 * 
 * @author Lukasz Plotnicki
 * 
 */
public class CriterionWrapper<V extends Serializable> {

	private final static String DPANEL = "datePanel";
	private final static String DICHPANEL = "dichotomousPanel";
	private final static String FREEPANEL = "freeTextPanel";
	private final static String ORDPANEL = "ordinalPanel";

	/**
	 * The criterion object which is wrapped by this instance.
	 */
	private AbstractCriterion<V, ? extends AbstractConstraint<V>> wrappedCriterion = null;

	/**
	 * If the wrapper is used during the subject's submission process - this
	 * field contains the corresponding subject property.
	 */
	private SubjectProperty<V> subjectProperty = null;

	@SuppressWarnings("unchecked")
	public SubjectProperty<? extends Serializable> getSubjectProperty() {
		if (subjectProperty == null) {
			subjectProperty = new SubjectProperty<V>(wrappedCriterion);
			try {
				if (getPanelType().equals(DPANEL))
					subjectProperty.setValue((V) new GregorianCalendar());
				// else if(getPanelType().equals(DICHPANEL))
				// subjectProperty.setValue(wrappedCriterion.getConfiguredValues().get(0));
				// else if(getPanelType().equals(FREEPANEL))
				// subjectProperty.setValue((V) new String());
				// else if(getPanelType().equals(ORDPANEL))
				// subjectProperty.setValue(wrappedCriterion.getConfiguredValues().get(0));
			} catch (ContraintViolatedException e) {
				e.printStackTrace();
			}

		}
		return subjectProperty;
	}
	
	private List<SelectItem> selectItems = null;
	
	public List<SelectItem> getSelectItems() {
		if(selectItems==null){
			selectItems = new ArrayList<SelectItem>();
			for(V value : wrappedCriterion.getConfiguredValues()){
				selectItems.add(new SelectItem(value, value.toString()));
			}
		}
		return selectItems;
	}

	/**
	 * Flag indicating if the wrapped criterion is also an inclusion constraint
	 * or not.
	 */
	private boolean isConstraint = false;

	/**
	 * String ID defining the showed criterion panel.
	 */
	private String panelType = "criterionErrorPanel";

	public CriterionWrapper(AbstractCriterion<V, ?> _criterion) {
		wrappedCriterion = _criterion;
	}

	public AbstractCriterion<?, ?> getWrappedCriterion() {
		return wrappedCriterion;
	}

	public void setWrappedCriterion(AbstractCriterion<V, ?> wrappedCriterion) {
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
	 * 
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
	 * Returns the String ID of an panel which need
	 * 
	 * @return
	 */
	public String getPanelType() {
		if (DateCriterion.class.isInstance(wrappedCriterion))
			panelType = DPANEL;
		else if (DichotomousCriterion.class.isInstance(wrappedCriterion))
			panelType = DICHPANEL;
		else if (FreeTextCriterion.class.isInstance(wrappedCriterion))
			panelType = FREEPANEL;
		else if (OrdinalCriterion.class.isInstance(wrappedCriterion))
			panelType = ORDPANEL;
		return panelType;
	}

	/**
	 * Add Element (if we're wrapping an ordinal criterion)
	 * 
	 * @param event
	 */
	public void addElement(ActionEvent event) {
		getElements().add(new StringWrapper(getElements().size(), ""));
	}

	/**
	 * Remove Element (if we're wrapping an ordinal criterion)
	 * 
	 * @param event
	 */
	public void removeElement(ActionEvent event) {
		getElements().remove(getElements().size()-1);
	}

	/**
	 * Check's if we're wrapping an ordinal criterion and if so, if there're any
	 * elements defined.
	 * 
	 * @return true - if an OrdinalCriterion with defined Elements, false if not
	 */
	public boolean isElementsEmpty() {
		if (OrdinalCriterion.class.isInstance(wrappedCriterion))
			return OrdinalCriterion.class.cast(wrappedCriterion).getElements()
					.isEmpty();
		return true;
	}
	
	private List<StringWrapper> elements;
	
	public List<StringWrapper> getElements() {
		if(elements==null){
			int i = 0;
			elements = new ArrayList<StringWrapper>();
			for(String s : OrdinalCriterion.class.cast(wrappedCriterion).getElements()){
				elements.add(new StringWrapper(i, s));
				i++;
			}
		}
		return elements;
	}
	
	public void setElements(List<StringWrapper> elements) {
		this.elements = elements;
	}
	
	public class StringWrapper{
		public int position = 0;
		public String value = null;
		
		public StringWrapper(int p, String v) {
			position = p;
			value = v;
		}
		
		public int getPosition() {
			return position;
		}
		
		public void setPosition(int position) {
			this.position = position;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
	}
}
