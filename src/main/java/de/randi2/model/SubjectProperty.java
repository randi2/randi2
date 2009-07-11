package de.randi2.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Target;

import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.unsorted.ContraintViolatedException;


@Entity
public final class SubjectProperty<V extends Serializable> extends AbstractDomainObject {

	private static final long serialVersionUID = 6795792982229806832L;
	
	@Target(value=Serializable.class)
	@Lob
	private V value;
	
	
	@ManyToOne(targetEntity=AbstractCriterion.class)
	private AbstractCriterion<V,? extends AbstractConstraint<V>> criterion;

	public SubjectProperty(AbstractCriterion<V, ? extends AbstractConstraint<V>> _criterion) {
		this.criterion = _criterion;
	}
	
	@SuppressWarnings("unused")
	//only for or-mapping
	private SubjectProperty(){
		
	}
	
	@Transient
	public long getStratum() throws ContraintViolatedException {
		//return criterion.stratify(this.value);
		return criterion.stratify(this.value).getId();
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

	@SuppressWarnings("unused") //Hibernate only
	private void setCriterion(AbstractCriterion<V, ? extends AbstractConstraint<V>> criterion) {
		this.criterion = criterion;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other instanceof SubjectProperty) {
			SubjectProperty<?> oProp = (SubjectProperty<?>) other;
			return new EqualsBuilder().appendSuper(true).
					append(value, oProp.value).
					append(criterion, oProp.criterion).isEquals();
		}
		return false;
	}

	@Override
	public int hashCode(){
		return new HashCodeBuilder().appendSuper(super.hashCode()).
				append(value).append(criterion).toHashCode();
	}
}
