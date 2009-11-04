package de.randi2.simulation.model;

import lombok.Data;

@Data
public class SimulationRun {

	private double marginalBalance;
	
	private double debit;
	
	private int[] subjectsPerArms;
	
	private Long time;
	
	public SimulationRun(int armAmount){
		subjectsPerArms= new int[armAmount];
	}
	
}
