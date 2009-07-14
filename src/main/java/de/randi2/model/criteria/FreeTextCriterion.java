package de.randi2.model.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import de.randi2.model.criteria.constraints.FreeTextConstraint;
import de.randi2.unsorted.ContraintViolatedException;

/**
 * 
 * 
 * @author dschrimpf
 */
@Entity
public class FreeTextCriterion extends AbstractCriterion<String, FreeTextConstraint>{

	private static final long serialVersionUID = 7359750785478879268L;

	@Override
	public void isValueCorrect(String value) throws ContraintViolatedException {
		if(value == null || value.length()==0){
			throw new ContraintViolatedException();
		}
		if(inclusionConstraint!=null){
			inclusionConstraint.isValueCorrect(value);
		}
		
	}
	
	@Override
	public List<String> getConfiguredValues() {
		return new ArrayList<String>();
	}
	
	@Override
	public Class<FreeTextConstraint> getContstraintType() {
		return FreeTextConstraint.class;
	}
	
}
