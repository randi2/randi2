package de.randi2.model;

import java.util.ArrayList;
import java.util.List;

import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.unsorted.ContraintViolatedException;
import de.randi2.utility.StratumProc;

public final class SubjectProperty extends AbstractDomainObject{
	
	private String value;
	private AbstractCriterion criterion;
	
	private List<Object> possibleValues = new ArrayList<Object>();

	private StratumProc stratumProc = StratumProc.noStratification(); 

	public void setStratumComputation(StratumProc p){
		this.stratumProc = p;
	}
	
	public void addPossibleValue(Object o){
		possibleValues.add(o);
	}
	
	public List<Object> getPossibleValues(){
		return possibleValues;
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

	public AbstractCriterion getCriterion() {
		return criterion;
	}

	public void setCriterion(AbstractCriterion criterion) {
		this.criterion = criterion;
		criterion.applyConstraints(this);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "TO_STRING_NOT_IMPLEMENTED";
	}
}
