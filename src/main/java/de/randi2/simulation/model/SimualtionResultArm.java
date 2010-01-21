package de.randi2.simulation.model;

import lombok.Data;
import de.randi2.model.TreatmentArm;

@Data
public class SimualtionResultArm {

	private TreatmentArm arm;
	
	private int min;
	private int max;
	private double mean;
	private double median;
}
