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

import de.randi2.model.Login;
import de.randi2.model.Role;
import de.randi2.model.TrialSite;

/**
 * This is the interface specifying the possible operations which can be
 * conducted on RANDI2 login objects.
 * 
 * @author Lukasz Plotnicki & Daniel Schrimpf
 * 
 */
public interface UserService extends AbstractService<Login> {

	/**
	 * Use this method if you want to update an existing object and make the
	 * object's changes persistent.
	 * 
	 * @param loginToSave
	 * @return the newest version of the saved object
	 */
	public Login update(Login changedObject);
	
	/**
	 * Returns the user with the user name or null.
	 * @param username The name of the user.
	 * @return The user or null.
	 */
	public Login getUser(String username);

	/**
	 * Use this method to create new RANDI2 Investigator user (self-registration process).
	 * 
	 * @param newObject
	 * @param site 
	 */
	public void register(Login newObject, TrialSite site);
	
	/**
	 * Use this method to create new RANDI2 user.
	 * 
	 * @param newObject
	 */
	public void create(Login newObject, TrialSite site);
	
	/**
	 * This method creates a brand new user with the investigator role for the self-registration process.
	 * @return complete login object, authorized for the self-registration process.
	 */
	public Login prepareInvestigator();

	/**
	 * Creates a new RANDI2 role.
	 * @param newRole
	 */
	public void createRole(Role newRole);

	/**
	 * Saves role's changes.
	 * @param changedRole
	 */
	public Role updateRole(Role changedRole);

	/**
	 * Deletes the given role from the RANDI2 system.
	 * @param oldRole
	 */
	public void deleteRole(Role oldRole);
	
	/**
	 * Returns the list of all defined roles which a user can have.
	 * @return defined RANDI2 roles
	 */
	public List<Role> getAllRoles(); 
	
	
	/**
	 * Returns the role with the specific name.
	 * @param name The name of the role.
	 * @return The role object.
	 */
	public Role getRole(String name);
	
	/* Methods for the future:
	 * addRight(long userID, ?)
	 * loginUser(String username, Strin pass)	
	*/

}
