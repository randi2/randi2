package de.randi2.dao;

import static junit.framework.Assert.*;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.context.ManagedSessionContext;
import org.hibernate.impl.SessionFactoryImpl;
import org.hibernate.impl.SessionImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.security.util.SessionUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.Login;
import de.randi2.test.utility.DomainObjectFactory;
import de.randi2.test.utility.TestStringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring-test.xml" })
public class UserDetailsServiceTest {

	
	@Autowired
	private LoginDao loginDao;
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private DomainObjectFactory factory;
	@Autowired
	private TestStringUtil testStringUtil;
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Before
	public void setUp(){
		userDetailsServiceImpl = new  UserDetailsServiceImpl();
		userDetailsServiceImpl.setSessionFactory(sessionFactory);
		
	}
	
	@Test
	@Transactional
	public void getUsernameTest() {
		
		Login l = factory.getLogin();
		l.setUsername(testStringUtil.getWithLength(10));
		loginDao.create(l);

		Login l2 = (Login) userDetailsServiceImpl.loadUserByUsername(l.getUsername());
		assertEquals(l.getId(), l2.getId());
		assertEquals(l.getUsername(), l2.getUsername());
		try{
			l2 = (Login) userDetailsServiceImpl.loadUserByUsername(testStringUtil.getWithLength(20));
			fail();
		}catch (UsernameNotFoundException e){
		}
	
		
	}
}
