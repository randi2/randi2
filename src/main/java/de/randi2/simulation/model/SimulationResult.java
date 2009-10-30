package de.randi2.simulation.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SimulationResult {

	private int amountRuns;
	
	private int[] mins;
	private int[] maxs;
	
	private double[] means;
	
	private int median;
	
	private List<SimulationRun> runs = new ArrayList<SimulationRun>();
	
	public void addSimulationRun(SimulationRun run){
		
		runs.add(run);
	}
	
	
}
