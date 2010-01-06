package de.randi2.simulation.model;

import lombok.Getter;
import lombok.Setter;

public class SimulationRun {

	private double marginalBalance = Double.NaN;
	
	private double debit = Double.NaN;
	
	@Getter @Setter
	private int[] subjectsPerArms;
	
	@Getter @Setter
	private Long time;
	
	public SimulationRun(int armAmount){
		subjectsPerArms= new int[armAmount];
	}
	
	
	public double getMarginalBalace(){
		if(Double.isNaN(marginalBalance)){
			marginalBalance = 0.0;
			double numerator = 0.0;
			for(int i=0;i<subjectsPerArms.length-1;i++){
				for(int j = i+1;j<subjectsPerArms.length;j++){
					marginalBalance += Math.abs(subjectsPerArms[i]-subjectsPerArms[j]);
				}
				numerator += subjectsPerArms[i];
			}
			numerator+=subjectsPerArms[subjectsPerArms.length-1];
			numerator =(subjectsPerArms.length-1.0) * numerator;
			marginalBalance = marginalBalance/numerator;
		}
		return marginalBalance;
	}
	
}
