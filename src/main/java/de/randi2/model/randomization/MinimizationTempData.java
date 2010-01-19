package de.randi2.model.randomization;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import de.randi2.model.TreatmentArm;
import de.randi2.model.TrialSite;
import de.randi2.model.criteria.constraints.AbstractConstraint;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class MinimizationTempData extends AbstractRandomizationTempData {


	private static final long serialVersionUID = -69397485726955392L;

	 @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	    @JoinTable(
	            name="MinimizationTempData_Probabilities",
	            joinColumns = @JoinColumn( name="treatmentArm_id"),
	            inverseJoinColumns = @JoinColumn( name="minimizationMapElementWrapper_id")
	    )
	private Map<TreatmentArm, MinimizationMapElementWrapper> probabilitiesPerPreferredTreatment;

	 @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	    @JoinTable(
	            name="MinimizationTempData_CountConstraints",
	            joinColumns = @JoinColumn( name="constraints_id"),
	            inverseJoinColumns = @JoinColumn( name="minimizationMapElementWrapper_id")
	    )
	private Map<AbstractConstraint<?>,MinimizationMapElementWrapper> countConstraints;
	 
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	    @JoinTable(
	            name="MinimizationTempData_countTrialSites",
	            joinColumns = @JoinColumn( name="trialSite_id"),
	            inverseJoinColumns = @JoinColumn( name="minimizationMapElementWrapper_id")
	    )
	private Map<TrialSite,MinimizationMapElementWrapper> countTrialSites;
}
