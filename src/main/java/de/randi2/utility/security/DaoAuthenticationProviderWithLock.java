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
