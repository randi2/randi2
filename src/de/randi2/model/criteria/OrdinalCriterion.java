package de.randi2.model.criteria;

import java.util.HashSet;
import java.util.Set;

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
public class OrdinalCriterion extends AbstractCriterion {

	private enum PrototypeElements {
		red, green, black, white
	};

	/**
	 * Set object storing the possible values.
	 */
	private Set<String> elements = new HashSet<String>();

	/* (non-Javadoc)
	 * @see de.randi2.model.criteria.AbstractCriterion#applyConstraints(de.randi2.model.SubjectProperty)
	 */
	@Override
	public void applyConstraints(SubjectProperty prop) {
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
	public SubjectProperty createPropertyPrototype() {
		SubjectProperty prototype = new SubjectProperty();
		for (PrototypeElements el : PrototypeElements.values()) {
			prototype.addPossibleValue(el.toString());
		}
		applyConstraints(prototype);
		prototype.setCriterion(this);
		return prototype;
	}

}
