package de.randi2.model.criteria;

import javax.persistence.Entity;
import java.util.List;
import de.randi2.model.AbstractDomainObject;
import de.randi2.model.subjectproperties.AbstractProperty;

@Entity
/**
 * This class maps the needed behaviour of a Trial subject. With the Classes
 * inherited from this class you can define anything you need, refering to the
 * properties a trials subject should have:
 * <ul>
 * <li>Properties that needs to be entered</li>
 * <li>Inclusion Criteria</li>
 * <li>Stratification</li>
 * </ul>
 */
public abstract class AbstractCriterion extends AbstractDomainObject {

	// The name of the criterion i.e. birthday
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public abstract void setProvider(Class<? extends AbstractProperty> propertyProvider) throws PropertyProvideNotSupportedException;
	public abstract Class<AbstractProperty> getProvider();
	public abstract List<Class<? extends AbstractProperty>> getPossibleProviders();
	
	
	
}
