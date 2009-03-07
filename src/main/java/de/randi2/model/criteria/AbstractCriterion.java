package de.randi2.model.criteria;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.randi2.model.AbstractDomainObject;
import java.io.Serializable;
import de.randi2.unsorted.ContraintViolatedException;

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
public abstract class AbstractCriterion<V extends Serializable> extends AbstractDomainObject {

	private static final long serialVersionUID = 6845807707883121147L;


	// The name of the criterion i.e. birthday
	public String name;
	
	public String description;
	
	@Transient
	protected List<V> configuredValues;
	
	@Transient
	public abstract List<V> getConfiguredValues();
	
	protected boolean isStratum = false;
	
	protected boolean isInclusionCriterion = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setStratum(boolean isStratum){
		this.isStratum = isStratum;
	}
	
	public boolean isStratum(){
		return this.isStratum;
	}
	
	@Transient
	public abstract AbstractConstraints<V> getConstraints();
	
	public abstract void setConstraints(AbstractConstraints<V> _constraints);
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public abstract boolean isInclusionCriterion();

	public abstract void isValueCorrect(V value) throws ContraintViolatedException;

	public boolean checkValue(V value){
		try{
			isValueCorrect(value);
			return true;
		}
		catch(ContraintViolatedException e){
			return false;
		}
	}
	
	
	/* (non-Javadoc)
	 * @see de.randi2.model.AbstractDomainObject#toString()
	 */
	@Override
	public String toString(){
		return this.getClass().getCanonicalName();
	}

}
