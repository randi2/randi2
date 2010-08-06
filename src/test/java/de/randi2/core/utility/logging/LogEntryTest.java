package de.randi2.core.utility.logging;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.TrialSite;
import de.randi2.utility.logging.LogEntry;
import de.randi2.utility.logging.LogEntry.ActionType;

import static junit.framework.Assert.*;
//import static junit.framework.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/service-test.xml" })
public class LogEntryTest {

	
	private LogEntry validEntry;
	
	@Before
	public void setUp(){
		validEntry = new LogEntry();
	}
	
	@Test
	public void testId(){
		assertTrue(validEntry.getId()<=0);
		validEntry.setId(1000);
		assertTrue(validEntry.getId()==1000);
	}
	
	
	@Test
	public void testIdentifier(){
		assertTrue(validEntry.getIdentifier()<=0);
		validEntry.setIdentifier(1000);
		assertTrue(validEntry.getIdentifier()==1000);
	}
	
	@Test
	public void testAction(){
		assertTrue(validEntry.getAction()==null);
		validEntry.setAction(ActionType.LOGIN);
		assertEquals(ActionType.LOGIN, validEntry.getAction());
	}
	
	
	@Test
	public void testClass(){
		assertTrue(validEntry.getClazz()==null);
		validEntry.setClazz(TrialSite.class);
		assertEquals(TrialSite.class, validEntry.getClazz());
	}
	
	
	
	@Test
	public void testTime(){
		assertTrue(validEntry.getTime()!=null);
		assertTrue(validEntry.getTime().compareTo(new GregorianCalendar()) <=0);
		GregorianCalendar date = new GregorianCalendar(2009,8,10);
		validEntry.setTime(date);
		assertEquals(date, validEntry.getTime());
		
	}
	
	@Test
	public void testUsername(){
		assertTrue(validEntry.getUsername()==null);
		validEntry.setUsername("username");
		assertEquals("username", validEntry.getUsername());
	}
	
	@Test
	public void testValue(){
		assertTrue(validEntry.getValue()==null);
		validEntry.setValue("Value");
		assertEquals("Value", validEntry.getValue());
	}
	
	@Test
	public void testToString(){
		validEntry.setValue("Value");
		validEntry.setUsername("username");
		validEntry.setClazz(TrialSite.class);
		validEntry.setAction(ActionType.LOGIN);
		validEntry.setIdentifier(1000);
		assertNotNull(validEntry.toString());
		assertTrue(validEntry.toString().length()>0);
		
		validEntry = new LogEntry();
		validEntry.setUsername("username");
		validEntry.setAction(ActionType.LOGIN);
		assertNotNull(validEntry.toString());
		assertTrue(validEntry.toString().length()>0);
	}
}
