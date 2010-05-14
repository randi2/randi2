package de.randi2.jsf.wrappers;

import de.randi2.model.TrialSite;
import lombok.Data;

@Data
public class TrialSiteRatioWrapper {

	private TrialSite site;
	
	private int ratio = 1;

	public TrialSiteRatioWrapper(TrialSite site) {
		super();
		this.site = site;
	}
	
	
}
