package de.randi2.services;

import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;

/**
 * All Trial-relevant services.
 * 
 * @author Lukasz Plotnicki <lp@randi2.de>
 */
public interface TrialService extends AbstractService<Trial> {

	/**
	 * Stores a new trial object in the system.
	 * 
	 * @param newTrial
	 */
	public void create(Trial newTrial);

	/**
	 * Updates the trial object and returns the its newest version.
	 * 
	 * @param trial
	 * @return
	 */
	public Trial update(Trial trial);

	/**
	 * Add a new trial subject to the given trial and randomize it. The
	 * refreshed Trial object will be returned.
	 * 
	 * @param subject
	 * @return
	 */
	public Trial randomize(Trial trial, TrialSubject subject);

}
