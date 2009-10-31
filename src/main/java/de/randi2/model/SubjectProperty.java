/* 
 * (c) 2008-2009 RANDI2 Core Development Team
 * 
 * This file is part of RANDI2.
 * 
 * RANDI2 is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * RANDI2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * RANDI2. If not, see <http://www.gnu.org/licenses/>.
 */
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


/**
 * The Class SubjectProperty.
 */
@Entity
@EqualsAndHashCode(callSuper=true)
public final class SubjectProperty<V extends Serializable> extends AbstractDomainObject {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6795792982229806832L;
	
	/** The value. */
	@Target(value=Serializable.class)
	@Lob
	private V value;
	
	
	/** The criterion. */
	@ManyToOne(targetEntity=AbstractCriterion.class)
	private AbstractCriterion<V,? extends AbstractConstraint<V>> criterion;

	/**
	 * Instantiates a new subject property.
	 * 
	 * @param _criterion
	 *            the _criterion
	 */
	@SuppressWarnings("unchecked")
	public SubjectProperty(AbstractCriterion _criterion) {
		this.criterion = _criterion;
	}
	
	/**
	 * Instantiates a new subject property.
	 */
	@SuppressWarnings("unused")
	//only for or-mapping
	private SubjectProperty(){
		
	}
	
	/**
	 * Gets the stratum.
	 * 
	 * @return the stratum
	 * 
	 * @throws ContraintViolatedException
	 *             the contraint violated exception
	 */
	@Transient
	public long getStratum() throws ContraintViolatedException {
		AbstractConstraint<?> constraint = criterion.stratify(value);
		if(constraint == null) return -1;
		else return constraint.getId();
	}

	// Get- and Set Methods
	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	@Transient
	public V getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value
	 *            the new value
	 * 
	 * @throws ContraintViolatedException
	 *             the contraint violated exception
	 */
	public void setValue(V value) throws ContraintViolatedException {
		criterion.isValueCorrect(value);
		this.value = value;
	}

	/**
	 * Get criterion.
	 * 
	 * @return the criterion
	 */
	@Transient
	public AbstractCriterion<V,? extends AbstractConstraint<V>> getCriterion() {
		return criterion;
	}

	/**
	 * Set criterion (Hibernate only).
	 * 
	 * @param criterion
	 *            the criterion
	 */
	protected void setCriterion(AbstractCriterion<V, ? extends AbstractConstraint<V>> criterion) {
		this.criterion = criterion;
	}

}
