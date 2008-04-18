package de.randi2.dao;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.Trial;
import de.randi2.model.Trial.TrialStatus;
import de.randi2.utility.Log4jInit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/de/randi2/applicationContext.xml"})
public class TrialDaoTest {

	
	private Transaction transaction = null; 
	
	@Autowired
	private TrialDao trialDao;
	
	@BeforeClass
	public static void createFixtureBase(){
		// Hier müssten die Datenbank-Fixtueres gesetzt werden.
	}
	
	@Before
	public void beforeTest(){
	//	this.transaction = ((DaoHibernate) trialDao).startTransaction();
	}
	
	@After
	public void afterTest(){
		//this.transaction.rollback();
	}

	public void setTrialDao(TrialDao _trialDao) {
		this.trialDao = _trialDao;
	}

	@Test
	public void testCreateAndSave() {
		Trial t1 = new Trial();

		t1.setName("Studie");
		t1.setDescription("Blaaa Fasel Blubb");
		t1.setStartDate(new GregorianCalendar(2007, Calendar.FEBRUARY, 1));
		t1.setEndDate(new GregorianCalendar(2009, Calendar.SEPTEMBER, 30));
		t1.setStatus(TrialStatus.ACTIVE);

		trialDao.save(t1);

		Trial t2 = trialDao.get(t1.getId());
		assertNotNull(t2);
		assertEquals(t1.getName(), t2.getName());
		assertEquals(t1.getDescription(), t2.getDescription());
	}

}
