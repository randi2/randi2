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
package de.randi2.jsf.wrappers;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import lombok.Getter;
import de.randi2.jsf.backingBeans.AbstractSubjectProperty;
import de.randi2.jsf.backingBeans.SubjectPropertiesConfig;
import de.randi2.jsf.utility.JSFViewUtitlity;
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
	
	@Getter
	private int id;
	
	private final AbstractSubjectProperty handler;

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
				if (panelType.equals(DPANEL))
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
		if (selectItems == null) {
			selectItems = new ArrayList<SelectItem>();
			for (V value : wrappedCriterion.getConfiguredValues()) {
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

	public boolean isConstraint() {
		return isConstraint;
	}

	public void setConstraint(boolean isConstraint) {
		this.isConstraint = isConstraint;
	}

	/**
	 * Flag indicating if the wrapped criterion is also an stratification
	 * factor.
	 */
	private boolean strataFactor = false;

	public boolean isStrataFactor() {
		return strataFactor;
	}

	public void setStrataFactor(boolean strataFactor) {
		this.strataFactor = strataFactor;
	}

	/**
	 * String ID defining the showed criterion panel.
	 */
	@Getter
	private String panelType = "criterionErrorPanel";
	
	private final Locale l;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CriterionWrapper(AbstractCriterion<V, ?> _criterion, Locale l, AbstractSubjectProperty handler, int id) {
		wrappedCriterion = _criterion;
		setPanelType();
		if (wrappedCriterion.getStrata() != null) {
			int stratumNr = 1;
			for (AbstractConstraint<V> c : wrappedCriterion.getStrata()) {
				strata.add(new ConstraintWrapper(stratumNr, c));
				stratumNr++;
			}
		}
		this.l = l;
		this.handler = handler;
		this.id = id;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CriterionWrapper(AbstractCriterion<V, ?> _criterion, Locale l) {
		wrappedCriterion = _criterion;
		setPanelType();
		if (wrappedCriterion.getStrata() != null) {
			int stratumNr = 1;
			for (AbstractConstraint<V> c : wrappedCriterion.getStrata()) {
				strata.add(new ConstraintWrapper(stratumNr, c));
				stratumNr++;
			}
		}
		this.l = l;
		this.handler = null;
		this.id = 0;
	}

	public AbstractCriterion<?, ? extends Serializable> getWrappedCriterion() {
		return wrappedCriterion;
	}

	public void setWrappedCriterion(AbstractCriterion<V, ?> wrappedCriterion) {
		this.wrappedCriterion = wrappedCriterion;
	}

	/**
	 * Retrurn the l16ed name of an criterion.
	 * 
	 * @return l16ed string representation of an criterion
	 */
	public String getL16edName() {
		return ResourceBundle.getBundle("de.randi2.jsf.i18n.criteria", l)
				.getString(wrappedCriterion.getUIName());
	}

	/**
	 * Returns the String ID of an panel which need
	 * 
	 * @return
	 */
	private void setPanelType() {
		if (DateCriterion.class.isInstance(wrappedCriterion))
			panelType = DPANEL;
		else if (DichotomousCriterion.class.isInstance(wrappedCriterion))
			panelType = DICHPANEL;
		else if (FreeTextCriterion.class.isInstance(wrappedCriterion))
			panelType = FREEPANEL;
		else if (OrdinalCriterion.class.isInstance(wrappedCriterion))
			panelType = ORDPANEL;
		else panelType="criterionErrorPanel";
	}

	/**
	 * Add Element (if we're wrapping an ordinal criterion)
	 * 
	 * @param event
	 */
	public void addElement(ActionEvent event) {
		OrdinalCriterion.class.cast(wrappedCriterion).getElements().add("");
		getElements().add(
				OrdinalCriterion.class.cast(wrappedCriterion).getElements()
						.size() - 1);
	}

	/**
	 * Remove Element (if we're wrapping an ordinal criterion)
	 * 
	 * @param event
	 */
	public void removeElement(ActionEvent event) {
		if (getElements().size() > 3) {
			getElements().remove(getElements().size() - 1);
			OrdinalCriterion.class
					.cast(wrappedCriterion)
					.getElements()
					.remove(OrdinalCriterion.class.cast(wrappedCriterion)
							.getElements().size() - 1);
		}
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

	/**
	 * Ordinal criterion possible values/elements.
	 */
	private List<Integer> elements;

	public List<Integer> getElements() {
		if (elements == null) {
			elements = new ArrayList<Integer>();
			for (int i = 0; i < OrdinalCriterion.class.cast(wrappedCriterion)
					.getElements().size(); i++) {
				elements.add(i);
			}
		}
		return elements;
	}

	public void setElements(List<Integer> elements) {
		this.elements = elements;
	}

	@SuppressWarnings("unchecked")
	public void inclusionConstraintChanged(ValueChangeEvent event) {
		try {
			List<V> l = new ArrayList<V>();
			l.add((V) event.getNewValue());
			wrappedCriterion.setInclusionConstraintAbstract(wrappedCriterion
					.getContstraintType().getConstructor(List.class)
					.newInstance(l));
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ContraintViolatedException e) {
			e.printStackTrace();
		}
	}

	private boolean possibleStrata = true;

	public boolean isPossibleStrata() {
		possibleStrata = !FreeTextCriterion.class.isInstance(wrappedCriterion);
		return possibleStrata;
	}

	private List<ConstraintWrapper<V>> strata = new ArrayList<ConstraintWrapper<V>>();

	public List<ConstraintWrapper<V>> getStrata() {
		if (strata.size() == 0) {
			strata.add(new ConstraintWrapper<V>(1));
			strata.add(new ConstraintWrapper<V>(2));
		}
		return strata;
	}

	public void addStrata(ActionEvent event) {
		strata.add(new ConstraintWrapper<V>(strata.size() + 1));
	}

	private List<SelectItem> possibleValues = null;

	public List<SelectItem> getPossibleValues() {
		if (possibleValues == null) {
			possibleValues = new ArrayList<SelectItem>();
			for (V value : wrappedCriterion.getConfiguredValues()) {
				possibleValues.add(new SelectItem(value, value.toString()));
			}
		}
		return possibleValues;
	}
	
	public void removeCriterion(ActionEvent event) {
		handler.getCriteria().remove(this);
		JSFViewUtitlity.refreshJSFPage();
		if(handler instanceof SubjectPropertiesConfig){
			((SubjectPropertiesConfig)handler).getTrialHandler().setTrialCreationTabIndex(3);
		}
	}

}
