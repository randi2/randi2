package de.randi2.model;

import java.io.Serializable;

import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.unsorted.ContraintViolatedException;
import javax.persistence.Transient;

public final class SubjectProperty<V extends Serializable> extends AbstractDomainObject {

	private static final long serialVersionUID = 6795792982229806832L;
	
	private V value;
	private AbstractCriterion<V> criterion;

	public SubjectProperty(AbstractCriterion<V> _criterion){
		this.criterion = _criterion;
	}

	@Transient
	public int getStratum() {
		//return criterion.stratify(this.value);
		return 0;
	}

	// Get- and Set Methods
	public V getValue() {
		return value;
	}

	public void setValue(V value) throws ContraintViolatedException {
		criterion.isValueCorrect(value);
		this.value = value;
	}

	public AbstractCriterion<V> getCriterion() {
		return criterion;
	}

	private void setCriterion(AbstractCriterion<V> criterion) {
		this.criterion = (AbstractCriterion<V>) criterion;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "TO_STRING_NOT_IMPLEMENTED";
	}
}
