package de.randi2.core.utility.logging;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	@Autowired private TestStringUtil stringUtil;
	@Autowired private DomainObjectFactory factory;
	
	private EntityManager entityManager;
	
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	@Test
	public void testLogChange(){
		String username = stringUtil.getWithLength(20);
		ActionType action = ActionType.UPDATE;
		AbstractDomainObject object = factory.getPerson();
		
		int sizeBefore = entityManager.createQuery("from LogEntry").getResultList().size();
		
		logService.logChange(action, username, object);
		assertTrue(entityManager.createQuery("from LogEntry").getResultList().size()>sizeBefore);
		
	}
	
		
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetLogEntriesAll(){
		int sizeBefore = entityManager.createQuery("from LogEntry").getResultList().size();
		for(int i=0;i<10;i++){
			String username = stringUtil.getWithLength(20);
			ActionType action = ActionType.UPDATE;
			logService.logGet(action, username);
		}
		entityManager.flush();
		List<String> entries = entityManager.createQuery("from LogEntry").getResultList();
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
		entityManager.flush();
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
		
		assertEquals(10, logService.getLogEntries(object.getClass(),id).size());
		
	}
	
}
