package de.randi2.utility.security;

import java.util.GregorianCalendar;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.AuthenticationException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.providers.dao.DaoAuthenticationProvider;
import org.springframework.security.userdetails.UserDetails;

import de.randi2.model.Login;

public class DaoAuthenticationProviderWithLock extends
		DaoAuthenticationProvider {
	
	
	@Autowired private SessionFactory sessionFactory;
	
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		try{
		super.additionalAuthenticationChecks(userDetails, authentication);
		}catch (BadCredentialsException e) {
			Login user = (Login) userDetails;
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
