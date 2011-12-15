package de.randi2.jsf.controllerBeans;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.el.ValueExpression;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import lombok.Getter;
import lombok.Setter;
import de.randi2.jsf.backingBeans.ResponsePropertyConfig;
import de.randi2.jsf.backingBeans.SubjectPropertiesConfig;
import de.randi2.jsf.supportBeans.Popups;
import de.randi2.jsf.wrappers.ConstraintWrapper;
import de.randi2.jsf.wrappers.CriterionWrapper;
import de.randi2.jsf.wrappers.TreatmentArmWrapper;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.unsorted.ConstraintViolatedException;
import de.randi2.utility.ReflectionUtil;

/**
 * <p>
 * This class contains an trial object and some helpful methods to work with
 * this object for the UI.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net> & ds@randi2.de
 */
public abstract class AbstractTrialHandler extends AbstractHandler<Trial> {

	@ManagedProperty(value = "#{loginHandler}")
	@Getter
	@Setter
	protected LoginHandler loginHandler;

	@Getter
	@Setter
	protected List<TreatmentArmWrapper> listArmsWrapper = new ArrayList<TreatmentArmWrapper>();

	private int armWrapperIdSequence = 0;

	/*
	 * Access to the application popups.
	 */
	@ManagedProperty(value = "#{popups}")
	@Setter
	@Getter
	private Popups popups;

	protected ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> criteriaList = null;

	@SuppressWarnings("unchecked")
	public AbstractTrialHandler() {
		criteriaList = new ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>>();
		try {
			/*
			 * Checking which subject properites are supported.
			 */
			for (Class<?> c : ReflectionUtil
					.getClasses("de.randi2.model.criteria")) {
				try {
					if (c.getSuperclass().equals(AbstractCriterion.class))
						criteriaList
								.add((AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>) c
										.getConstructor().newInstance());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Trial createPlainObject() {
		Trial t = new Trial();
		// Start Date will be initialized with the today's date and the end date with the tomarrow's date 
		t.setStartDate(new GregorianCalendar());
		GregorianCalendar endDate = new GregorianCalendar();
		endDate.setTimeInMillis(t.getStartDate().getTimeInMillis()+86400000);
		t.setEndDate(endDate);
		return t;
	}

	@Override
	public String refreshShowedObject() {
		return null; // TODO What should we do at this point?
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.randi2.jsf.controllerBeans.AbstractHandler#saveObject()
	 */
	@Override
	public String saveObject() {
		return null; // Currently there is no posibility to edit the trial
						// object - therefore no implementation of this method
	}

	protected ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> configureCriteriaStep4() {
		/* SubjectProperties Configuration - done in Step4 */
		ValueExpression ve1 = FacesContext
				.getCurrentInstance()
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{subjectPropertiesConfig}",
						SubjectPropertiesConfig.class);
		SubjectPropertiesConfig currentStep4 = (SubjectPropertiesConfig) ve1
				.getValue(FacesContext.getCurrentInstance().getELContext());
		/* End of SubjectProperites Configuration */
		return addAllConfiguredCriteria(currentStep4.getCriteria());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> addAllConfiguredCriteria(
			ArrayList<CriterionWrapper<? extends Serializable>> criteriaList) {
		ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> configuredCriteria = new ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>>();
		for (CriterionWrapper<? extends Serializable> cr : criteriaList) {
			/* Strata configuration */
			if (cr.isStrataFactor()) {
				if (DichotomousCriterion.class.isInstance(cr
						.getWrappedCriterion())) {
					DichotomousCriterion temp = DichotomousCriterion.class
							.cast(cr.getWrappedCriterion());
					temp.setStrata(new ArrayList<DichotomousConstraint>());
					try {
						temp.addStrata(new DichotomousConstraint(Arrays
								.asList(new String[] { temp
										.getConfiguredValues().get(0) })));
						temp.addStrata(new DichotomousConstraint(Arrays
								.asList(new String[] { temp
										.getConfiguredValues().get(1) })));
					} catch (ConstraintViolatedException e) {
						e.printStackTrace();
					}
				} else {
					cr.getWrappedCriterion().setStrata(new ArrayList());
					for (ConstraintWrapper<?> cw : cr.getStrata()) {
						cr.getWrappedCriterion().addStrata(cw.configure());
					}
				}
			}
			/* End of strata configuration */
			configuredCriteria
					.add((AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>) cr
							.getWrappedCriterion());
		}
		return configuredCriteria;
	}
	
	protected DichotomousCriterion configureResponse() {
		ValueExpression ve1 = FacesContext
				.getCurrentInstance()
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{responsePropertyConfig}",
						ResponsePropertyConfig.class);
		ResponsePropertyConfig response = (ResponsePropertyConfig) ve1
				.getValue(FacesContext.getCurrentInstance().getELContext());

		for (CriterionWrapper<? extends Serializable> cr : response
				.getCriteria()) {
			if (cr.isStrataFactor()) {
				if (DichotomousCriterion.class.isInstance(cr
						.getWrappedCriterion())) {
					DichotomousCriterion temp = DichotomousCriterion.class
							.cast(cr.getWrappedCriterion());
					temp.setStrata(new ArrayList<DichotomousConstraint>());
					try {
						temp.addStrata(new DichotomousConstraint(Arrays
								.asList(new String[] { temp
										.getConfiguredValues().get(0) })));
						temp.addStrata(new DichotomousConstraint(Arrays
								.asList(new String[] { temp
										.getConfiguredValues().get(1) })));
					} catch (ContraintViolatedException e) {
						e.printStackTrace();
					}
				}
			}
			return (DichotomousCriterion) cr.getWrappedCriterion();
		}
		return null;
	}

	/**
	 * Action listener for adding a new treatment arm.
	 * 
	 * @param event
	 */
	public void addArm(ActionEvent event) {
		assert (currentObject != null);
		TreatmentArmWrapper armWrapper = new TreatmentArmWrapper(this);
		armWrapper.setArm(new TreatmentArm());
		armWrapper.setId(armWrapperIdSequence++);
		listArmsWrapper.add(armWrapper);
	}

	/**
	 * Provieds the current amount of defined treatment arms.
	 * 
	 * @return
	 */
	public int getTreatmentArmsCount() {
		assert (currentObject != null);
		return currentObject.getTreatmentArms().size();
	}

	public ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> getCriteriaList() {
		return criteriaList;
	}

	protected Set<TreatmentArm> getTreatmentArms() {
		Set<TreatmentArm> arms = new HashSet<TreatmentArm>();
		for (TreatmentArmWrapper armWrapper : listArmsWrapper) {
			arms.add(armWrapper.getArm());
		}
		return arms;
	}

	public void cleanTreatmentArms() {
		listArmsWrapper = new ArrayList<TreatmentArmWrapper>();
		for (int i = 0; i < 2; i++) {
			TreatmentArmWrapper armWrapper = new TreatmentArmWrapper(this);
			armWrapper.setArm(new TreatmentArm());
			armWrapper.setId(armWrapperIdSequence++);
			listArmsWrapper.add(armWrapper);
		}
	}
}
