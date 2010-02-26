package de.randi2.simulation.model;

import lombok.Getter;
import lombok.Setter;

public class SimulationRun {

	private double marginalBalance = Double.NaN;
	
	private double debit = Double.NaN;
	
	@Getter @Setter
	private int[] subjectsPerArms;
	
	private int[] plannedSubjectsPerArm;
	
	@Getter @Setter
	private Long time;
	
	public SimulationRun(int[] plannedSubjectsPerArm){
		subjectsPerArms= new int[plannedSubjectsPerArm.length];
		this.plannedSubjectsPerArm = plannedSubjectsPerArm;
	}
	
	
	public double getMarginalBalace(){
		if(Double.isNaN(marginalBalance)){
			marginalBalance = 0.0;
			double numerator = 0.0;
			for(int i=0;i<subjectsPerArms.length-1;i++){
				for(int j = i+1;j<subjectsPerArms.length;j++){
					marginalBalance += Math.abs(((subjectsPerArms[i]*1.0) / (plannedSubjectsPerArm[i]*1.0))- ((subjectsPerArms[j]*1.0)/  (plannedSubjectsPerArm[j]*1.0)));
				}
				numerator += ((subjectsPerArms[i]*1.0) / (plannedSubjectsPerArm[i]*1.0));
			}
			numerator+=subjectsPerArms[subjectsPerArms.length-1];
			numerator =(subjectsPerArms.length-1.0) * numerator;
			marginalBalance = marginalBalance/numerator;
			marginalBalance = marginalBalance*1000;
		}
		return marginalBalance;
	}
	
}
