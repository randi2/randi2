package de.randi2.jsf.backingBeans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import lombok.Setter;
import de.randi2.jsf.controllerBeans.SimulationHandler;
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
@SessionScoped
public class SimulationSubjectProperty extends AbstractSubjectProperty{

	@ManagedProperty("#{simulationHandler}")
	@Setter
	private SimulationHandler simulationHandler;

	@Override
	public ArrayList<CriterionWrapper<? extends Serializable>> getCriteria() {
		simulationHandler.criterionChanged();
		if (criteria == null)
			criteria = new ArrayList<CriterionWrapper<? extends Serializable>>();
		if (criteria.isEmpty()) {
			for (AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<?>> c : simulationHandler
					.getCurrentObject().getCriteria()) {
				criteria.add(new CriterionWrapper<Serializable>(
						(AbstractCriterion<Serializable, ?>) c, loginHandler
								.getChosenLocale(), this, criterionWrapperId++));
			}
		}
		return super.getCriteria();
	}
	
	

	
}
