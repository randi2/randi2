package de.randi2.utility;

import lombok.Data;

@Data
public class StrataNameIDWrapper implements Comparable<StrataNameIDWrapper>{

	private String strataId;
	private String strataName;
	
	
	@Override
	public int compareTo(StrataNameIDWrapper o) {
		return (strataId.compareTo(o.getStrataId()));
	}
}
