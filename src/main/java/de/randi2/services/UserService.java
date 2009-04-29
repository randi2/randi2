package de.randi2.services;

import de.randi2.model.Login;
import de.randi2.model.Role;

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
	 * Use this method to create new RANDI2 user.
	 * 
	 * @param newObject
	 */
	public void register(Login newObject);
	
	/**
	 * This method creates a brand new user with the investigator role for the self-registration process.
	 * @return complete login object, authorized for the self-registration process.
	 */
	public Login prepareInvestigator();

	/**
	 * Add a specific role to an object. The given role has to be already in the
	 * system.
	 * 
	 * @param Login
	 * @param role
	 */
	public void addRole(Login login, Role role);
	
	/**
	 * Removes the given role from the Login object.
	 * @param login
	 * @param role
	 */
	public void removeRole(Login login, Role role);

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
	
	
	/* Methods for the future:
	 * addRight(long userID, ?)
	 * loginUser(String username, Strin pass)	
	*/

}
