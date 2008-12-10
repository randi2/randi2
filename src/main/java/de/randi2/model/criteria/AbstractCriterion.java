package de.randi2.model.criteria;

import javax.persistence.Entity;
import de.randi2.model.AbstractDomainObject;
import de.randi2.model.SubjectProperty;

@Entity
/**
 * This class maps the needed behaviour of a Trial subject. With the Classes
 * inherited from this class you can define anything you need, referring to the
 * properties a trials subject should have:
 * <ul>
 * <li>Properties that needs to be entered</li>
 * <li>Inclusion Criteria</li>
 * <li>Stratification</li>
 * </ul>
 */
public abstract class AbstractCriterion extends AbstractDomainObject {

	// The name of the criterion i.e. birthday
	protected String name;
	
	protected String description;
	
	protected boolean isStratum = false;

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
	
	public abstract SubjectProperty createPropertyPrototype();

	public abstract void applyConstraints(SubjectProperty prop);

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	/* (non-Javadoc)
	 * @see de.randi2.model.AbstractDomainObject#toString()
	 */
	@Override
	public String toString(){
		return this.getClass().getCanonicalName();
	}
	
}
