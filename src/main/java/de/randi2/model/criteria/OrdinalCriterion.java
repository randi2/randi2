package de.randi2.model.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import org.hibernate.annotations.CollectionOfElements;

import de.randi2.model.SubjectProperty;

/**
 * <p>
 * This class represents an ordinal scale or a set of some not computable
 * properties of a trial subject. (If you are looking for a criterion meant for
 * numerical data @see de.randi2.model.criteria.NumericCriteion)
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 * 
 */
@Entity
public class OrdinalCriterion extends AbstractCriterion<String> {

	private static final long serialVersionUID = -1596645059608735663L;

	private enum PrototypeElements {
		red, green, black, white
	};

	/**
	 * Set object storing the possible values.
	 */
	@CollectionOfElements
	public List<String> elements = new ArrayList<String>();
	
	/* (non-Javadoc)
	 * @see de.randi2.model.criteria.AbstractCriterion#applyConstraints(de.randi2.model.SubjectProperty)
	 */
	@Override
	public void applyConstraints(SubjectProperty<String> prop) {
		for(Object possibleValue : prop.getPossibleValues()){
			elements.add(possibleValue.toString());
		}
		if (isStratum) {
			// TODO What should we do here?
		}
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.criteria.AbstractCriterion#createPropertyPrototype()
	 */
	@Override
	public SubjectProperty<String> createPropertyPrototype() {
		SubjectProperty<String> prototype = new SubjectProperty<String>();
		for (PrototypeElements el : PrototypeElements.values()) {
			prototype.addPossibleValue(el.toString());
		}
		applyConstraints(prototype);
		prototype.setCriterion(this);
		return prototype;
	}

	public List<String> getElements() {
		return elements;
	}

	public void setElements(List<String> elements) {
		this.elements = elements;
	}

	@Override
	public AbstractConstraints<?> getConstraints() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConstraints(AbstractConstraints<?> _constraints) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getConfiguredValues() {
		return elements;
	}

}
