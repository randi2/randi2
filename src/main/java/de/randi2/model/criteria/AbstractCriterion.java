/* 
 * (c) 2008- RANDI2 Core Development Team
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
package de.randi2.model.criteria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import de.randi2.model.AbstractDomainObject;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.unsorted.ConstraintViolatedException;

/**
 * This class maps the needed behaviour of a Trial subject. With the Classes
 * inherited from this class you can define anything you need, referring to the
 * properties a trials subject should have:
 * <ul>
 * <li>Properties that needs to be entered</li>
 * <li>Inclusion Criteria</li>
 * <li>Stratification</li>
 * </ul>
 * 
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name="Criterion")
public abstract class AbstractCriterion<V extends Serializable, C extends AbstractConstraint<V>> extends AbstractDomainObject {

	private static final long serialVersionUID = 6845807707883121147L;


	// The name of the criterion i.e. birthday
	@Getter @Setter
	public String name;
	
	@Getter @Setter
	public String description;

	@Transient
	protected List<V> configuredValues;
	
	@Transient
	public abstract List<V> getConfiguredValues();
	
	/**
	 * If the object represents an inclusion criteria, this field has the
	 * constraints.
	 */
	@OneToOne(targetEntity=AbstractConstraint.class, cascade=CascadeType.ALL)
	protected C inclusionConstraint;

	@OneToMany(targetEntity=AbstractConstraint.class, cascade=CascadeType.ALL)
	protected List<C> strata;

	@Transient
	public C getInclusionConstraint(){
		return inclusionConstraint;
	}

	public abstract void setInclusionConstraint(C inclusionConstraint) throws ConstraintViolatedException;
	
	@SuppressWarnings("unchecked")
	public void setInclusionConstraintAbstract(AbstractConstraint<?> inclusionConstraint) throws ConstraintViolatedException{
		setInclusionConstraint((C) inclusionConstraint);
	}
	
	@Transient
	public abstract Class<C> getContstraintType();

	@Transient
	public List<C> getStrata(){
		return strata;
	}

	public void setStrata(List<C> strata){
		this.strata = strata;
	}
	
	public void addStrata(AbstractConstraint<?> abstractConstraint){
		if(this.strata == null){
			this.strata = new ArrayList<C>();
		}
		if(abstractConstraint != null){
			this.strata.add((C) abstractConstraint);
		}
	}
	
	public C stratify(V value) throws ConstraintViolatedException{
		this.isValueCorrect(value);
		if(strata==null || strata.isEmpty())
			return null;
		for(C stratum : strata){
			if(stratum.checkValue(value))
				return stratum;
		}
		throw new ConstraintViolatedException();
//		throw new Randi2Error("Valid value could not be assigned to any stratum.");
	}


	@Transient
	public  boolean isInclusionCriterion(){
		return inclusionConstraint!=null;
	}

	public abstract void isValueCorrect(V value) throws ConstraintViolatedException;

	public boolean checkValue(V value){
		try{
			isValueCorrect(value);
			return true;
		}
		catch(ConstraintViolatedException e){
			return false;
		}
	}
}
