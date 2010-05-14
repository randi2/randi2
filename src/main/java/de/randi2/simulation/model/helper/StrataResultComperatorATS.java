package de.randi2.simulation.model.helper;

import java.util.Comparator;


public class StrataResultComperatorATS implements Comparator<StrataResultWrapper>{

	@Override
	public int compare(StrataResultWrapper o1, StrataResultWrapper o2) {
		if(o1.getAlgorithmName().compareTo(o2.getAlgorithmName()) == 0){
			if(o1.getTreatmentName().compareTo(o2.getTreatmentName()) == 0){
				return o1.getStrataId().compareTo(o2.getStrataId());
			}else return o1.getTreatmentName().compareTo(o2.getTreatmentName());
		}else
			return o1.getAlgorithmName().compareTo(o2.getAlgorithmName()) ;
	}
	
}
