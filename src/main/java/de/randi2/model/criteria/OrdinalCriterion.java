package de.randi2.model.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Transient;

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

	/**
	 * List object storing the possible values.
	 */
	@CollectionOfElements
	public List<String> elements;
	
	/**
	 * If the object represents an inclusion criteria, this field has the
	 * constraints.
	 */
	@Embedded
	private OrdinalConstraints constraints = null;
	
	public OrdinalCriterion(){
		if(elements==null){
			elements = new ArrayList<String>();
			for(int i=0;i<3;i++){
				elements.add(new String());
			}
		}
	}
	
	@Embeddable
	public class OrdinalConstraints extends AbstractConstraints<String> {

		@Transient
		public List<String> expectedValues;

		@Override
		public boolean checkValue(String _value) {
			return expectedValues.contains(_value);
		}

		public List<String> getExpectedValues() {
			return expectedValues;
		}

		public void setExpectedValues(List<String> expectedValues) {
			this.expectedValues = expectedValues;
		}

	}
	
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
		return null;
	}

	public List<String> getElements() {
		return elements;
	}

	public void setElements(List<String> elements) {
		this.elements = elements;
	}

	@Override
	public AbstractConstraints<String> getConstraints() {
		if(constraints==null)
			constraints = new  OrdinalConstraints();
		return constraints;
	}

	@Override
	public void setConstraints(AbstractConstraints<String> _constraints) {
		constraints = (OrdinalConstraints) _constraints;
		
	}

	@Override
	public List<String> getConfiguredValues() {
		boolean configured = true;
		for(String s : elements){
			configured = !(s.isEmpty() || s.equals(""));
		}
		if(configured)
			return elements;
		return null;
	}

}
