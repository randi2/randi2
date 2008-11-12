package de.randi2.model;

import java.util.ArrayList;
import java.util.List;

import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.unsorted.ContraintViolatedException;
import de.randi2.utility.StratumProc;

public final class SubjectProperty extends AbstractDomainObject{
	
	private String value;
	private AbstractCriterion criterion;
	
	private List<String> possibleValues = new ArrayList<String>();

	private StratumProc stratumProc = StratumProc.noStratification(); 

	public void setStratumComputation(StratumProc p){
		this.stratumProc = p;
	}
	
	public void addPossibleValue(String s){
		possibleValues.add(s);
	}
	
	public int getStratum(){
		return stratumProc.stratify(this.value);
	}
	
	
	// Get- and Set Methods
	public String getValue() {
		return value;
	}

	public void setValue(String value) throws ContraintViolatedException {
		this.checkConstraints(value);
		this.value = value;
	}
	
	// Check-Methoden
	private void checkConstraints(String value) throws ContraintViolatedException{
		this.checkPossibleValues(value);
	}
	
	private void checkPossibleValues(String value) throws ContraintViolatedException{
		if(!possibleValues.isEmpty()){
			if(!possibleValues.contains(value)){
				throw new ContraintViolatedException(); // TODO nice constraint violation message 
			}
		}
	}

	private AbstractCriterion getCriterion() {
		return criterion;
	}

	private void setCriterion(AbstractCriterion criterion) {
		this.criterion = criterion;
		criterion.applyConstraints(this);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "TO_STRING_NOT_IMPLEMENTED";
	}
}
