package de.randi2.model.criteria.constraints;

import java.util.List;

import javax.persistence.Entity;

import org.hibernate.annotations.CollectionOfElements;

import de.randi2.unsorted.ContraintViolatedException;

@Entity
public class OrdinalConstraint extends AbstractConstraint<String> {

	private static final long serialVersionUID = 3642808577019112783L;

	
	protected OrdinalConstraint(){}
	
	public OrdinalConstraint(List<String> args)
			throws ContraintViolatedException {
		super(args);
		// TODO Auto-generated constructor stub
	}

	@CollectionOfElements
	public List<String> expectedValues;

	public List<String> getExpectedValues() {
		return expectedValues;
	}

	public void setExpectedValues(List<String> expectedValues) {
		this.expectedValues = expectedValues;
	}

	@Override
	public void isValueCorrect(String _value) throws ContraintViolatedException {
		if(!expectedValues.contains(_value)){
			throw new ContraintViolatedException();
		}
		
	}

	@Override
	protected void configure(List<String> args)
			throws ContraintViolatedException {
		this.expectedValues =args;
		
	}
}
