package de.randi2.jsf.backingBeans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import lombok.Setter;
import de.randi2.jsf.controllerBeans.SimulationHandler;
import de.randi2.jsf.utility.AutoCompleteObject;
import de.randi2.jsf.wrappers.CriterionWrapper;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;

/**
 * <p>
 * This class wrapped the subject property configuration's functionality for the simulation process.
 * </p>
 * 
 * @author Daniel Schrimpf <ds@randi2.de>
 * 
 */
@ManagedBean(name="simulationSubjectProperty")
@RequestScoped
public class SimulationSubjectProperty extends AbstractSubjectProperty{

	@ManagedProperty("#{simulationHandler}")
	@Setter
	private SimulationHandler simulationHandler;


	public AutoCompleteObject<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> getCriteriaAC() {
		if (criteriaAC == null) {
			initCriteriaAC(simulationHandler.getCriteriaList());
		}
		return criteriaAC;
	}
	
	@Override
	public ArrayList<CriterionWrapper<? extends Serializable>> getCriteria() {
		simulationHandler.criterionChanged();
		return super.getCriteria();
	}

	
}
