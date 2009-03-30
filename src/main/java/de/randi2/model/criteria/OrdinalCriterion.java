package de.randi2.model.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;

import org.hibernate.annotations.CollectionOfElements;

import de.randi2.model.criteria.constraints.OrdinalConstraint;
import de.randi2.unsorted.ContraintViolatedException;

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
public class OrdinalCriterion extends
		AbstractCriterion<String, OrdinalConstraint> {

	private static final long serialVersionUID = -1596645059608735663L;
	/**
	 * List object storing the possible values.
	 */
	@CollectionOfElements(fetch=FetchType.EAGER)
	private List<String> elements;

	public OrdinalCriterion() {
		elements = new ArrayList<String>();
		for (int i = 0; i < 3; i++) {
			elements.add("");
		}
	}

	public List<String> getElements() {
		return elements;
	}

	public void setElements(List<String> elements) {
		this.elements = elements;
	}

	@Override
	public List<String> getConfiguredValues() {
		if(elements == null || elements.size() == 0){
			return null;
		}
		boolean configured = true;
		for (String s : elements) {
			configured = !(s.isEmpty() || s.equals(""));
		}
		if (configured) {
			return elements;
		}
		return null;
	}

	@Override
	public void isValueCorrect(String value) throws ContraintViolatedException {
		if(!elements.contains(value)){
			throw new ContraintViolatedException();
		}		
	}
	
	@Override
	public Class<OrdinalConstraint> getContstraintType() {
		return OrdinalConstraint.class;
	}


}
