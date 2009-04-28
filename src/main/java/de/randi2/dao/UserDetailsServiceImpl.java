package de.randi2.dao;

import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.ManagedSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.Login;

public class UserDetailsServiceImpl implements UserDetailsService {


	@Autowired private SessionFactory sessionFactory;
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		String queryS = "from de.randi2.model.Login login where "
			+ "login.username =?";
		
		//open and bind a new HibernateSession
		 Session session = sessionFactory.openSession();
		 session.setFlushMode(FlushMode.MANUAL);
		ManagedSessionContext.bind((org.hibernate.classic.Session)session);
		 
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
			unbindAndStoreSession(session);
			return user;
		}else{
			unbindAndStoreSession(session);
			throw new UsernameNotFoundException("");
		}
		
	}
	
	private void unbindAndStoreSession(Session session){
		session.flush();
		ManagedSessionContext.unbind(sessionFactory).close();
	}
}
