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
package de.randi2.dao;

import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import de.randi2.model.Login;

/**
 * The Class UserDetailsServiceImpl.
 */
public class UserDetailsServiceImpl implements UserDetailsService {


	/** The logger. */
	private Logger logger = Logger.getLogger(UserDetailsService.class);
	
	/** The session factory. */
	@Autowired private SessionFactory sessionFactory;
	
	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		logger.info("User " + username + " try to login.");
		String queryS = "from de.randi2.model.Login login where "
			+ "login.username =?";
		
		Query query = sessionFactory.getCurrentSession().createQuery(queryS);
		query.setParameter(0, username);
		List<Login>  loginList =(List) query.list();
		if (loginList.size() ==1){
			Login user = loginList.get(0);
			user.setLastLoggedIn(new GregorianCalendar());
			if(user.getNumberWrongLogins()==Login.MAX_WRONG_LOGINS && ((user.getLockTime().getTimeInMillis()+Login.MILIS_TO_LOCK_USER)< System.currentTimeMillis())){
				byte number = 0;
				user.setNumberWrongLogins(number);
				user.setLockTime(null);
				sessionFactory.getCurrentSession().update(user);
			}
			return user;
		}else{
			throw new UsernameNotFoundException("");
		}
		
	}
	
		/**
		 * Sets the session factory.
		 * 
		 * @param sessionFactory
		 *            the new session factory
		 */
		public void setSessionFactory(SessionFactory sessionFactory) {
			this.sessionFactory = sessionFactory;
		}
}
