package de.randi2.model.randomization;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.MapKey;
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

	@OneToMany(targetEntity=MinimizationMapElementWrapper.class, cascade=CascadeType.ALL)
	@MapKey
	private Map<TreatmentArm, MinimizationMapElementWrapper> probabilitiesPerPreferredTreatment;
	
	@OneToMany(targetEntity=MinimizationMapElementWrapper.class, cascade=CascadeType.ALL)
	@MapKey
	private Map<AbstractConstraint<?>,MinimizationMapElementWrapper> countConstraints;
	
	@OneToMany(targetEntity=MinimizationMapElementWrapper.class, cascade=CascadeType.ALL)
	@MapKey
	private Map<TrialSite,MinimizationMapElementWrapper> countTrialSites;
}
