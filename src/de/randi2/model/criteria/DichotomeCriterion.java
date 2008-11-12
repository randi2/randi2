package de.randi2.model.criteria;

import de.randi2.model.SubjectProperty;
import de.randi2.utility.StratumProc;

public class DichotomeCriterion extends AbstractCriterion{
	
	private static final String TRUE_STRING = "TRUE";
	private static final String FALSE_STRING = "FALSE";

	private String options[] = new String[2];
	private boolean isBinary = true; 
	
	private boolean isStratum = false;
	

	public void setBinary(){
		this.isBinary = true;
		this.options[0] = null;
		this.options[1] = null;
	}
	
	public void setStringOptions(String option1, String option2){
		this.isBinary = false;
		this.options[0] = option1;
		this.options[1] = option2;
	}
	
	public void setStratum(boolean isStratum){
		this.isStratum = isStratum;
	}
	
	@Override
	public SubjectProperty createPropertyPrototype(){
		SubjectProperty prop = new SubjectProperty();
		applyConstraints(prop);
		return prop;
	}
	
	@Override
	public void applyConstraints(SubjectProperty prop){
		if(this.isBinary){
			prop.addPossibleValue(TRUE_STRING);
			prop.addPossibleValue(FALSE_STRING);
			if(isStratum){
				prop.setStratumComputation(StratumProc.binaryStratification(TRUE_STRING, FALSE_STRING));
			}
		}
		else{
			prop.addPossibleValue(options[0]);
			prop.addPossibleValue(options[1]);
			if(isStratum){
				prop.setStratumComputation(StratumProc.binaryStratification(options[0], options[1]));
			}
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "TO_STRING_NOT_IMPLEMENTED";
	}

}
