package de.randi2.simulation.model;

import lombok.Data;

@Data
public class SimulationRawDataEntry {

	private int run;
	private int count;
	private String treatmentArm;
	private String trialSite;
	private String stratum;
	

	@Override
	public String toString() {
		return run + ";" + count + ";" + treatmentArm + ";" +trialSite + ";" + stratum;
	}
}
