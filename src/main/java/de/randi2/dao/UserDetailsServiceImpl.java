package de.randi2.dao;

import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import de.randi2.model.Login;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private HibernateTemplate template;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		String query = "from de.randi2.model.Login login where "
			+ "login.username =?";
		List<Login>  loginList =(List) template.find(query, username);
		if (loginList.size() ==1){
			loginList.get(0).setLastLoggedIn(new GregorianCalendar());
			return loginList.get(0);
		}
		else throw new UsernameNotFoundException("");
	}

}
