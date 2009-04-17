package de.randi2.dao;

import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.Login;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private HibernateTemplate template;
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		String query = "from de.randi2.model.Login login where "
			+ "login.username =?";
		List<Login>  loginList =(List) template.find(query, username);
		if (loginList.size() ==1){
			Login user = loginList.get(0);
			user.setLastLoggedIn(new GregorianCalendar());
			if(user.getNumberWrongLogins()==Login.MAX_WRONG_LOGINS && ((user.getLockTime().getTimeInMillis()+Login.MILIS_TO_LOCK_USER)< System.currentTimeMillis())){
				byte number = 0;
				user.setNumberWrongLogins(number);
				user.setLockTime(null);
				template.update(user);
			}
			return user;
		}
		else throw new UsernameNotFoundException("");
	}

}
