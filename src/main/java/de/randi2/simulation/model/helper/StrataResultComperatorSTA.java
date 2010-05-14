package de.randi2.simulation.model.helper;

import java.util.Comparator;


public class StrataResultComperatorSTA implements Comparator<StrataResultWrapper>{

	@Override
	public int compare(StrataResultWrapper o1, StrataResultWrapper o2) {
		if(o1.getStrataId().compareTo(o2.getStrataId()) == 0){
			if(o1.getTreatmentName().compareTo(o2.getTreatmentName())== 0){
				return o1.getAlgorithmName().compareTo(o2.getAlgorithmName()) ;
			}else return o1.getTreatmentName().compareTo(o2.getTreatmentName());
		}else
			return o1.getStrataId().compareTo(o2.getStrataId()) ;
	}
	
}
