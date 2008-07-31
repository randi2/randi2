package de.randi2.dao;

import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import de.randi2.model.Login;

public class LoginDaoHibernate extends AbstractDaoHibernate<Login> implements LoginDao, UserDetailsService{

	@SuppressWarnings("unchecked")
	public Login get(String username) {
		String query = "from de.randi2.model.Login login where "
			+ "login.username =?";
	 
		List<Login>  loginList =(List) template.find(query, username);
		if (loginList.size() ==1)	return loginList.get(0);
		else return null;
	}

	@Override
	public Class<Login> getModelClass() {
		return Login.class;
	}

	@Override
	@SuppressWarnings("unchecked")
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {

		 System.out.println("Login user");
		String query = "from de.randi2.model.Login login where "
			+ "login.username =?";
	 System.out.println("Login user Dao");
		List<Login>  loginList =(List) template.find(query, username);
		if (loginList.size() ==1){
			loginList.get(0).setLastLoggedIn(new GregorianCalendar());
			return loginList.get(0);
		}
		else throw new UsernameNotFoundException("");
	}

}
