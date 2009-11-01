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
package de.randi2.utility.security;

import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.AuthenticationException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.providers.dao.DaoAuthenticationProvider;
import org.springframework.security.userdetails.UserDetails;

import de.randi2.model.Login;
import de.randi2.utility.logging.LogService;
import de.randi2.utility.logging.LogEntry.ActionType;

public class DaoAuthenticationProviderWithLock extends
		DaoAuthenticationProvider {
	
	
	private Logger logger = Logger.getLogger(DaoAuthenticationProviderWithLock.class);
	@Autowired private SessionFactory sessionFactory;
	@Autowired private LogService logService;
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		try{
		super.additionalAuthenticationChecks(userDetails, authentication);
		logger.info("user " + userDetails.getUsername() + "loged in");
		logService.logChange(ActionType.LOGIN, userDetails.getUsername(), ((Login)userDetails));
		}catch (BadCredentialsException e) {
			Login user = (Login) userDetails;
			logger.warn("Wrong password: user=" + user.getUsername());
			if(user.getNumberWrongLogins() <Login.MAX_WRONG_LOGINS){
				byte number =user.getNumberWrongLogins();
				number++;
				user.setNumberWrongLogins(number);
				if(number==Login.MAX_WRONG_LOGINS) user.setLockTime(new GregorianCalendar()); 
				sessionFactory.getCurrentSession().save(user);
				
			}
			throw e;
		}
	}
	

}
