package de.randi2.services;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.vote.AffirmativeBased;

import de.randi2.dao.LoginDao;
import de.randi2.model.Login;
import de.randi2.model.Role;
import de.randi2.test.utility.DomainObjectFactory;
import de.randi2.test.utility.TestStringUtil;
//@Transactional
//import static junit.framework.Assert.*;
public class UserServiceTest extends AbstractServiceTest{


	@Autowired private UserService userService;
	@Autowired private DomainObjectFactory factory;
	@Autowired private TestStringUtil stringUtil;
	@Autowired private ApplicationContext context;
	@Autowired private LoginDao loginDao;
		

	
	@Test
	public void testInit(){
		assertNotNull(userService);
	}
	

	//FIXME Create a test for the getAllRoles 
	
	@Test
	public void testAddRole(){
		Login login = factory.getLogin();
		sessionFactory.getCurrentSession().persist(login);
		assertTrue(login.getId()>0);
		Role role = factory.getRole();
		sessionFactory.getCurrentSession().persist(role);
		sessionFactory.getCurrentSession().flush();
		assertTrue(role.getId()>0);
		userService.addRole(login, role);
		assertTrue(login.getRoles().contains(role));
		Login login2 = (Login)sessionFactory.getCurrentSession().get(Login.class, login.getId());
		assertNotNull(login2);
		assertTrue(login2.getRoles().contains(role));
		try{
			userService.addRole(null, role);
			fail("should throw exception (login = null)");
		}catch(Exception e){}
		try{
			userService.addRole(login, null);
			fail("should throw exception (role = null)");
		}catch(Exception e){}
		try{
			userService.addRole(login, factory.getRole());
			fail("should throw exception (role not saved)");
		}catch(Exception e){}
	}
	
	@Test
	public void testRemoveRole(){
		Login login = factory.getLogin();
		sessionFactory.getCurrentSession().persist(login);
		assertTrue(login.getId()>0);
		Role role = factory.getRole();
		sessionFactory.getCurrentSession().persist(role);
//		sessionFactory.getCurrentSession().flush();
		assertTrue(role.getId()>0);
		userService.addRole(login, role);
		assertTrue(login.getRoles().contains(role));
		Login login2 = (Login)sessionFactory.getCurrentSession().get(Login.class, login.getId());
		assertNotNull(login2);
		assertTrue(login2.getRoles().contains(role));
		try{
			userService.removeRole(null, role);
			fail("should throw exception (login = null)");
		}catch(Exception e){}
		try{
			userService.removeRole(login, null);
			fail("should throw exception (role = null)");
		}catch(Exception e){}
		try{
			userService.removeRole(factory.getLogin(), role);
			fail("should throw exception (login not saved)");
		}catch(Exception e){}
		try{
			userService.removeRole(login, factory.getRole());
			fail("should throw exception (role not saved)");
		}catch(Exception e){}
		userService.removeRole(login2, role);
		assertFalse(login2.getRoles().contains(role));
	}
	
	@Test
	public void testCreateRole(){
		Role role = factory.getRole();
		userService.createRole(role);
		assertTrue(role.getId()>0);
	}
	
	@Test
	public void testDeleteRole(){
		assertTrue(true);
	}
	
	@Test
	public void testPrepareInvestigator(){
		userService.prepareInvestigator();
		assertNotNull(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		assertTrue(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Login);
		Login login = (Login)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		assertTrue(login.getRoles().contains(Role.ROLE_ANONYMOUS));
	}
	
	
	@Test
	public void testRegister(){
		SecurityContextHolder.getContext().setAuthentication(null);
		Login l = userService.prepareInvestigator();
		l.setUsername(stringUtil.getWithLength(Login.MIN_USERNAME_LENGTH)+"@xyz.com");
		l.setPassword(stringUtil.getWithLength(Login.MIN_PASSWORD_LENGTH)+".ada6");
		l.setPerson(factory.getPerson());
		l.getPerson().setLogin(l);
		l.setLastLoggedIn(new GregorianCalendar());
		l.getPerson().setTrialSite(findLogin("admin@test.de").getPerson().getTrialSite());
		userService.register(l);
		assertTrue(l.getId()>0);
	}
	
	
	@Test
	public void testCreate(){
		Login login = factory.getLogin();
		login.getPerson().setTrialSite(factory.getTrialSite());
		//sessionFactory.getCurrentSession().persist(login.getPerson().getTrialSite().getContactPerson());
		sessionFactory.getCurrentSession().persist(login.getPerson().getTrialSite());
		sessionFactory.getCurrentSession().flush();
		userService.create(login);
		assertTrue(login.getId()>0);
	}
	
	
	@Test
	public void testUpdate(){
		Login login = factory.getLogin();
		login.getPerson().setTrialSite(factory.getTrialSite());
		sessionFactory.getCurrentSession().persist(login.getPerson().getTrialSite().getContactPerson());
		sessionFactory.getCurrentSession().persist(login.getPerson().getTrialSite());
		sessionFactory.getCurrentSession().flush();
		userService.create(login);
		sessionFactory.getCurrentSession().flush();
		assertTrue(login.getId()>0);
		String oldName = login.getUsername();
		login.setUsername(factory.getPerson().getEmail());
		Login login2 = (Login) sessionFactory.getCurrentSession().get(Login.class, login.getId());
		assertEquals(login.getUsername(), login2.getUsername());
		assertFalse(login2.getUsername().equals(oldName));
	}

	
	@Test
	public void testUpdateRole(){
		Role role = factory.getRole();
		sessionFactory.getCurrentSession().persist(role);
		assertTrue(role.getId()>0);
		String oldName = role.getName();
		role.setName(stringUtil.getWithLength(30));
		userService.updateRole(role);
		Role role2 = (Role) sessionFactory.getCurrentSession().get(Role.class,role.getId());
		assertEquals(role.getName(), role2.getName());
		assertFalse(role2.getName().equals(oldName));
	}
	
	
	@Test
	public void testGetAll(){
		for(int i =0; i<10; i++){
			Login login = factory.getLogin();
			login.getPerson().setTrialSite(null);
			loginDao.create(login);
		}
		List<Login> list = userService.getAll();
		assertTrue(list.size()>=10);
	}
	
	
	@Test
	public void testGetObject(){
		((AffirmativeBased)context.getBean("methodAccessDecisionManager")).setAllowIfAllAbstainDecisions(true);
		Login login = factory.getLogin();
		login.getPerson().setTrialSite(null);
		userService.create(login);
		Login login2 = findLogin("admin@test.de");
		rolesAndRights.grantRigths(login, login2.getPerson().getTrialSite());
		Login login3 = userService.getObject(login.getId());
		assertTrue(login3 != null);
	}
	
	
	
	
}
