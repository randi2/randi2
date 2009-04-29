package de.randi2.services;

import de.randi2.model.TrialSite;

public interface TrialSiteService extends AbstractService<TrialSite> {

	/**
	 * Use this method to check if the given trial site password is correct.
	 * 
	 * @param site
	 * @param password
	 */
	public boolean authorize(TrialSite site, String password);

	/**
	 * Use this method to store new TrialSite object in the system.
	 * 
	 * @param newSite
	 */
	public void create(TrialSite newSite);

	/**
	 * Saves the TrialSite object and returns the newest version of the same
	 * object.
	 * 
	 * @param site
	 * @return
	 */
	public TrialSite update(TrialSite site);
}
