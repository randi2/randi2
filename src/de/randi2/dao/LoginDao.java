package de.randi2.dao;

import de.randi2.model.Login;

public interface LoginDao extends AbstractDao<Login> {

	/**
	 * This method returns an login object for a given username.
	 * 
	 * @param username -
	 *            unique username.
	 * @return - a complete login object or <code>null</code> if no such login
	 *         is available.
	 */
	public Login get(String username);

}
