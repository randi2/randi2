package de.randi2.model.randomization;

import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.MapKeyColumn;

import lombok.Data;
import lombok.EqualsAndHashCode;
import de.randi2.model.AbstractDomainObject;
import de.randi2.model.TreatmentArm;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class MinimizationMapElementWrapper extends AbstractDomainObject {

	private static final long serialVersionUID = -2604596009904498229L;
	
	@ElementCollection
	@MapKeyColumn
	private Map<TreatmentArm, Double> map;


	public MinimizationMapElementWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public MinimizationMapElementWrapper(Map<TreatmentArm, Double> map) {
		super();
		this.map = map;
	}
	
	
	
}
