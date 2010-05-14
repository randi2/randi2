package de.randi2.simulation.model.helper;

import lombok.Data;

@Data
public class StrataResultWrapper{
	private String strataId;
	private String strataName;
	private String algorithmName;
	private String treatmentName;
	private int minCount;
	private int maxCount;
	private double mean;
}

