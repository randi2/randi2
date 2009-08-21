package de.randi2.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Target;

import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.unsorted.ContraintViolatedException;


@Entity
@EqualsAndHashCode(callSuper=true)
public final class SubjectProperty<V extends Serializable> extends AbstractDomainObject {

	private static final long serialVersionUID = 6795792982229806832L;
	
	@Target(value=Serializable.class)
	@Lob
	private V value;
	
	
	@ManyToOne(targetEntity=AbstractCriterion.class)
	private AbstractCriterion<V,? extends AbstractConstraint<V>> criterion;

	@SuppressWarnings("unchecked")
	public SubjectProperty(AbstractCriterion _criterion) {
		this.criterion = _criterion;
	}
	
	@SuppressWarnings("unused")
	//only for or-mapping
	private SubjectProperty(){
		
	}
	
	@Transient
	public long getStratum() throws ContraintViolatedException {
		AbstractConstraint<?> constraint = criterion.stratify(value);
		if(constraint == null) return -1;
		else return constraint.getId();
	}

	// Get- and Set Methods
	@Transient
	public V getValue() {
		return value;
	}

	public void setValue(V value) throws ContraintViolatedException {
		criterion.isValueCorrect(value);
		this.value = value;
	}

	@Transient
	public AbstractCriterion<V,? extends AbstractConstraint<V>> getCriterion() {
		return criterion;
	}

	//Hibernate only
	protected void setCriterion(AbstractCriterion<V, ? extends AbstractConstraint<V>> criterion) {
		this.criterion = criterion;
	}

}
