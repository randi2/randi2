package de.randi2.services;

import de.randi2.model.TrialSite;

public interface TrialSiteService extends AbstractService<TrialSite>{
	
	/**
	 * Use this method to check if the given trial site password is correct.
	 * @param site
	 * @param password
	 */
	public boolean authorize(TrialSite site, String password);

}
