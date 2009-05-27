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

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public void isValueCorrect(String value) throws ContraintViolatedException {
		if(value == null || value.length()==0){
			throw new ContraintViolatedException();
		}
		if(inclusionCriterion!=null){
			inclusionCriterion.isValueCorrect(value);
		}
		
	}
	
	@Override
	public List<String> getConfiguredValues() {
		return new ArrayList<String>();
	}
	
	@Override
	public Class<FreeTextConstraint> getContstraintType() {
		// TODO Auto-generated method stub
		return FreeTextConstraint.class;
	}
	
}
