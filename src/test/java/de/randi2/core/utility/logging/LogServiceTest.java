package de.randi2.core.utility.logging;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Person;
import de.randi2.testUtility.utility.DomainObjectFactory;
import de.randi2.testUtility.utility.InitializeDatabaseUtil;
import de.randi2.testUtility.utility.TestStringUtil;
import de.randi2.utility.logging.LogEntry.ActionType;
import de.randi2.utility.logging.LogService;

//import static junit.framework.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/service-test.xml","/META-INF/subconfig/test.xml" })
@Transactional
public class LogServiceTest {

	@Autowired private LogService logService;
	@Autowired private TestStringUtil stringUtil;
	@Autowired private DomainObjectFactory factory;
	@Autowired private InitializeDatabaseUtil databaseUtil;
	
	
	private EntityManager entityManager;
	
	
	@Before
	public void setUp() {
		try {
			databaseUtil.setUpDatabaseEmpty();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
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
