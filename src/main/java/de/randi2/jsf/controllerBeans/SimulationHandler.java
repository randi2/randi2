package de.randi2.jsf.controllerBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;
import de.randi2.jsf.backingBeans.Step4;
import de.randi2.jsf.backingBeans.Step5;
import de.randi2.jsf.supportBeans.Randi2;
import de.randi2.jsf.wrappers.ConstraintWrapper;
import de.randi2.jsf.wrappers.CriterionWrapper;
import de.randi2.model.Trial;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.model.enumerations.TrialStatus;
import de.randi2.model.randomization.BiasedCoinRandomizationConfig;
import de.randi2.model.randomization.CompleteRandomizationConfig;
import de.randi2.model.randomization.TruncatedBinomialDesignConfig;
import de.randi2.unsorted.ContraintViolatedException;

public class SimulationHandler {

	@Getter
	@Setter
	private TrialHandler trialHandler;

	@Setter
	private Trial simTrial;

	public Trial getSimTrial() {
		if (simTrial == null)
			simTrial = trialHandler.getShowedObject();
		try {
			/* Leading Trial Site & Sponsor Investigator */
			simTrial.setLeadingSite(trialHandler.getTrialSitesAC()
					.getSelectedObject());
			if (trialHandler.getSponsorInvestigatorsAC().getSelectedObject() != null)
				simTrial.setSponsorInvestigator(trialHandler
						.getSponsorInvestigatorsAC().getSelectedObject()
						.getPerson());

			/* SubjectProperties Configuration - done in Step4 */
			ValueExpression ve1 = FacesContext.getCurrentInstance()
					.getApplication().getExpressionFactory()
					.createValueExpression(
							FacesContext.getCurrentInstance().getELContext(),
							"#{step4}", Step4.class);
			Step4 currentStep4 = (Step4) ve1.getValue(FacesContext
					.getCurrentInstance().getELContext());
			ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> configuredCriteria = new ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>>();
			for (CriterionWrapper<? extends Serializable> cr : currentStep4
					.getCriteria()) {
				/* Strata configuration - done in Step5 */
				if (cr.isStrataFactor()) {
					if (DichotomousCriterion.class.isInstance(cr
							.getWrappedCriterion())) {
						DichotomousCriterion temp = DichotomousCriterion.class
								.cast(cr.getWrappedCriterion());
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
			simTrial.setCriteria(configuredCriteria);
			/* End of SubjectProperites Configuration */

			return simTrial;
		} catch (Exception e) {
			return null;
		}

	}

	  private List<CriterionWrapper<Serializable>> strata;
	
	 @SuppressWarnings("unchecked")
		public List<CriterionWrapper<Serializable>> getStrata(){
	        strata  = new ArrayList<CriterionWrapper<Serializable>>();
	        for(AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>> c : simTrial.getCriteria()){
	            strata.add(new CriterionWrapper<Serializable>((AbstractCriterion<Serializable, ?>) c));
	        }
	        return strata;
	    }
	 
	 
	 /**
	     * Checks if any strata factors are defined.
	     * @return
	     */
	    public boolean isStrataFactorsDefined(){
	    	 
	    	for(AbstractCriterion<?,?> c : simTrial.getCriteria()){
	    		if(c.getStrata()!=null){
	    			if(c.getStrata().size()>0)
	                    return true;
	    		}
	         }
	    	 return false;
	    }
	
	public String simTrial() {
		return Randi2.SUCCESS;
	}

}
