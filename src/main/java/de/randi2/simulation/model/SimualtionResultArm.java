package de.randi2.simulation.model;

import java.text.DecimalFormat;

import lombok.Data;
import de.randi2.model.TreatmentArm;

@Data
public class SimualtionResultArm {

	private TreatmentArm arm;
	
	private int min;
	private int max;
	private double mean;
	private double median;
	
	private DecimalFormat f = new DecimalFormat("#0.00"); 
	
	public double getMinPercent() {
		return ((min*100.0)/arm.getPlannedSubjects())-100;
	}
	
	public double getMaxPercent() {
		return ((max*100.0)/arm.getPlannedSubjects())-100;
	}
	
	public String getMinPercentString() {
		return  f.format(getMinPercent()) + "%";
	}
	
	public String getMaxPercentString() {
		return f.format(getMaxPercent())+ "%";
	}
	
}
