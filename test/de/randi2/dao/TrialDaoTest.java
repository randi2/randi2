package de.randi2.dao;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.Trial;
import de.randi2.model.Trial.TrialStatus;

import static junit.framework.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/de/randi2/applicationContext.xml" })
public class TrialDaoTest {

	@Autowired
	private TrialDao trialDao;

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
