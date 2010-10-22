package de.randi2.jsf.controllerBeans;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;

import javax.el.ValueExpression;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import lombok.Getter;
import lombok.Setter;
import de.randi2.jsf.backingBeans.Step4;
import de.randi2.jsf.backingBeans.Step5;
import de.randi2.jsf.supportBeans.Popups;
import de.randi2.jsf.wrappers.ConstraintWrapper;
import de.randi2.jsf.wrappers.CriterionWrapper;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.model.randomization.AbstractRandomizationConfig;
import de.randi2.model.randomization.BiasedCoinRandomizationConfig;
import de.randi2.model.randomization.CompleteRandomizationConfig;
import de.randi2.model.randomization.TruncatedBinomialDesignConfig;
import de.randi2.unsorted.ContraintViolatedException;
import de.randi2.utility.ReflectionUtil;

/**
 * <p>
 * This class contains an trial object and some helpful 
 * methods to work with this object for the UI.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net> & ds@randi2.de
 */
public abstract class AbstractTrialHandler extends AbstractHandler<Trial>{

	@ManagedProperty(value="#{loginHandler}")
	@Getter
	@Setter
	private LoginHandler loginHandler;
	
	/*
	 * Access to the application popups.
	 */
	@ManagedProperty(value="#{popups}")
	@Setter
	@Getter
	private Popups popups;

	protected ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> criteriaList = null;

	/*
	 * Object needed for the randomization configuration process.
	 */
	@Setter
	@Getter
	private AbstractRandomizationConfig randomizationConfig;
	
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
		// Start & End Date will be initalised with the today's date
		t.setStartDate(new GregorianCalendar());
		t.setEndDate(new GregorianCalendar());
		// Each new Trial has automatic 2 Treatment Arms
		t.getTreatmentArms().add(new TreatmentArm());
		t.getTreatmentArms().add(new TreatmentArm());
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
	
	protected ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> configureCriteriaStep4(){
		/* SubjectProperties Configuration - done in Step4 */
		ValueExpression ve1 = FacesContext.getCurrentInstance()
				.getApplication().getExpressionFactory()
				.createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{step4}", Step4.class);
		Step4 currentStep4 = (Step4) ve1.getValue(FacesContext
				.getCurrentInstance().getELContext());
		/* End of SubjectProperites Configuration */
		return addAllConfiguredCriteria(currentStep4.getCriteria());
	}
	
	protected ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> addAllConfiguredCriteria(ArrayList<CriterionWrapper<? extends Serializable>> criteriaList){
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
					} catch (ContraintViolatedException e) {
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
		};
		return configuredCriteria;
	}
	
	protected void configureAlgorithmWithStep5(){
		/* Algorithm Configuration */
		ValueExpression ve2 = FacesContext.getCurrentInstance()
				.getApplication().getExpressionFactory()
				.createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{step5}", Step5.class);
		Step5 currentStep5 = (Step5) ve2.getValue(FacesContext
				.getCurrentInstance().getELContext());
		if (currentStep5.getSelectedAlgorithmPanelId().equals(
				Step5.AlgorithmPanelId.COMPLETE_RANDOMIZATION.toString())) {
			showedObject
					.setRandomizationConfiguration(new CompleteRandomizationConfig());
		} else if (currentStep5.getSelectedAlgorithmPanelId().equals(
				Step5.AlgorithmPanelId.BIASEDCOIN_RANDOMIZATION.toString())) {
			showedObject
					.setRandomizationConfiguration(new BiasedCoinRandomizationConfig());
		} else if (currentStep5.getSelectedAlgorithmPanelId().equals(
				Step5.AlgorithmPanelId.BLOCK_RANDOMIZATION.toString())) {
			showedObject.setRandomizationConfiguration(getRandomizationConfig());
		} else if (currentStep5.getSelectedAlgorithmPanelId().equals(
				Step5.AlgorithmPanelId.TRUNCATED_RANDOMIZATION.toString())) {
			showedObject
					.setRandomizationConfiguration(new TruncatedBinomialDesignConfig());
		} else if (currentStep5.getSelectedAlgorithmPanelId().equals(
				Step5.AlgorithmPanelId.URN_MODEL.toString())) {
			showedObject.setRandomizationConfiguration(getRandomizationConfig());
		}else if (currentStep5.getSelectedAlgorithmPanelId().equals(
				Step5.AlgorithmPanelId.MINIMIZATION.toString())) {
			showedObject.setRandomizationConfiguration(getRandomizationConfig());
		}
		/* End of the Algorithm Configuration */
	}
	
	/**
	 * Action listener for adding a new treatment arm.
	 * 
	 * @param event
	 */
	public void addArm(ActionEvent event) {
		assert (showedObject != null);
		TreatmentArm temp = new TreatmentArm();
		showedObject.getTreatmentArms().add(temp);
	}

	/**
	 * Action listener for removing an existing treatment arm.
	 * 
	 * @param event
	 */
	public void removeArm(ActionEvent event) {
		assert (showedObject != null);
		showedObject.getTreatmentArms().remove(
				showedObject.getTreatmentArms().size() - 1);
	}

	/**
	 * Provieds the current amount of defined treatment arms.
	 * 
	 * @return
	 */
	public int getTreatmentArmsCount() {
		assert (showedObject != null);
		return showedObject.getTreatmentArms().size();
	}
	
	public ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> getCriteriaList() {
		return criteriaList;
	}
}
