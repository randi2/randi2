package de.randi2.core.utility.logging;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.context.ManagedSessionContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Person;
import de.randi2.testUtility.utility.DomainObjectFactory;
import de.randi2.testUtility.utility.TestStringUtil;
import de.randi2.utility.logging.LogService;
import de.randi2.utility.logging.LogEntry.ActionType;
import static junit.framework.Assert.*;

//import static junit.framework.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/service-test.xml","/META-INF/subconfig/test.xml" })
public class LogServiceTest {

	@Autowired private LogService logService;
	@Autowired private SessionFactory sessionFactory;
	@Autowired private TestStringUtil stringUtil;
	@Autowired private DomainObjectFactory factory;
	
	@Before
	public void setUp(){
		ManagedSessionContext.bind(sessionFactory.openSession());
	}
	
	@Test
	public void testLogChange(){
		String username = stringUtil.getWithLength(20);
		ActionType action = ActionType.UPDATE;
		AbstractDomainObject object = factory.getPerson();
		
		int sizeBefore = sessionFactory.getCurrentSession().createQuery("from LogEntry").list().size();
		
		logService.logChange(action, username, object);
		sessionFactory.getCurrentSession().flush();
		assertTrue(sessionFactory.getCurrentSession().createQuery("from LogEntry").list().size()>sizeBefore);
		
	}
	
		
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetLogEntriesAll(){
		int sizeBefore = sessionFactory.getCurrentSession().createQuery("from LogEntry").list().size();
		for(int i=0;i<10;i++){
			String username = stringUtil.getWithLength(20);
			ActionType action = ActionType.UPDATE;
			logService.logGet(action, username);
		}
		
		sessionFactory.getCurrentSession().flush();
		List<String> entries = sessionFactory.getCurrentSession().createQuery("from LogEntry").list();
		assertTrue(entries.size()>sizeBefore);
		
		assertEquals(entries.size(), logService.getLogEntries().size());
		
	}
	
	@Test
	public void testGetLogEntriesUsername(){
		String username = stringUtil.getWithLength(20);
		
		for(int i=0;i<10;i++){	
			ActionType action = ActionType.UPDATE;
			logService.logGet(action, username);
		}
		
		sessionFactory.getCurrentSession().flush();
		
		assertEquals(10, logService.getLogEntries(username).size());
		
	}
	
	@Test
	public void testGetLogEntriesClassAndId(){
		int id = 1234;
		Person object = factory.getPerson();
		object.setId(id);
		for(int i=0;i<10;i++){	
			String username = stringUtil.getWithLength(20);
			ActionType action = ActionType.UPDATE;
			logService.logChange(action, username, object);
		}
		
		sessionFactory.getCurrentSession().flush();
		
		assertEquals(10, logService.getLogEntries(object.getClass(),id).size());
		
	}
	
}
