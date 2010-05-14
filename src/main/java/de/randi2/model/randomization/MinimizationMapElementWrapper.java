package de.randi2.model.randomization;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CollectionOfElements;

import lombok.Data;
import lombok.EqualsAndHashCode;
import de.randi2.model.AbstractDomainObject;
import de.randi2.model.TreatmentArm;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class MinimizationMapElementWrapper extends AbstractDomainObject {

	private static final long serialVersionUID = -2604596009904498229L;
	
	
	@CollectionOfElements(targetElement=Double.class, fetch=FetchType.EAGER)
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
