package de.randi2.model.criteria;

import de.randi2.unsorted.ContraintViolatedException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.hibernate.annotations.CollectionOfElements;

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
	private List<String> elements;
	/**
	 * If the object represents an inclusion criteria, this field has the
	 * constraints.
	 */
	@Embedded
	private OrdinalConstraints constraints = null;

	public OrdinalCriterion() {
		elements = new ArrayList<String>();
		for (int i = 0; i < 3; i++) {
			elements.add("");
		}
	}

	@Override
	public boolean isInclusionCriterion() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void isValueCorrect(String value) throws ContraintViolatedException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Embeddable
	private class OrdinalConstraints extends AbstractConstraints<String> {

		private static final long serialVersionUID = 3642808577019112783L;

		public OrdinalConstraints(List<String> args)
				throws ContraintViolatedException {
			super(args);
			// TODO Auto-generated constructor stub
		}

		@Transient
		public List<String> expectedValues;

		public List<String> getExpectedValues() {
			return expectedValues;
		}

		public void setExpectedValues(List<String> expectedValues) {
			this.expectedValues = expectedValues;
		}

		@Override
		public void isValueCorrect(String _value) throws ContraintViolatedException {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		protected void configure(List<String> args)
				throws ContraintViolatedException {
			// TODO Auto-generated method stub
			
		}
	}



	public List<String> getElements() {
		return elements;
	}

	public void setElements(List<String> elements) {
		this.elements = elements;
	}

	@Override
	public AbstractConstraints<String> getConstraints() {
		return constraints;
	}

	@Override
	public void setConstraints(AbstractConstraints<String> _constraints) {
		constraints = (OrdinalConstraints) _constraints;

	}

	@Override
	public List<String> getConfiguredValues() {
		boolean configured = true;
		for (String s : elements) {
			configured = !(s.isEmpty() || s.equals(""));
		}
		if (configured) {
			return elements;
		}
		return null;
	}
}
