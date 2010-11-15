/* 
 * (c) 2008- RANDI2 Core Development Team
 * 
 * This file is part of RANDI2.
 * 
 * RANDI2 is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * RANDI2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * RANDI2. If not, see <http://www.gnu.org/licenses/>.
 */
package de.randi2.services;

import java.util.List;

import de.randi2.model.Person;
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
	
	
	/**
	 * Get the trial site of the specific person.
	 * @param person the specific person
	 * @return the trial site or null
	 */
	public TrialSite getTrialSiteFromPerson(Person person);
	
	
	/**
	 * Add the person to the trial site.
	 * @param site The trial site.
	 * @param person
	 */
	public void addPerson(TrialSite site, Person person) throws ServiceException;
	
	/**
	 * Change the persons trial site.
	 * @param newSite The new Trial site.
	 * @param person
	 */
	public void changePersonTrialSite(TrialSite newSite, Person person) throws ServiceException;

	/**
	 * 
	 * @param site The trial site.
	 * @return The members of this trial.
	 */
	public List<Person> getMembers(TrialSite site);
	
}
