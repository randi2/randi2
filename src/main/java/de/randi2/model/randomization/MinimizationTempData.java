package de.randi2.model.randomization;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.jmx.export.annotation.ManagedOperationParameter;

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

	@Lob
	private Map<TreatmentArm, Map<TreatmentArm, Double>> probabilitiesPerPreferredTreatment;
	@Lob
	private HashMap<AbstractConstraint<?>,HashMap<TreatmentArm, Double>> countConstraints;
	@Lob
	private HashMap<TrialSite,HashMap<TreatmentArm, Double>> countTrialSites;
}
