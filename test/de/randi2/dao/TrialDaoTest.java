package de.randi2.dao;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import de.randi2.model.Trial;
import de.randi2.model.Trial.TrialStatus;


public class TrialDaoTest extends AbstractTransactionalDataSourceSpringContextTests{

	private TrialDao trialDao;

	@Override
	protected String[] getConfigLocations() {
		return new String[] { "classpath:/de/randi2/applicationContext.xml" };
	}
	
	public void setTrialDao(TrialDao _trialDao){
		this.trialDao = _trialDao;
	}
	
	
	public void testCreateAndSave(){
		Trial t1 = new Trial();
		
		t1.setName("Studie");
		t1.setDescription("Blaaa Fasel Blubb");
		t1.setStartDate(new GregorianCalendar(2007,Calendar.FEBRUARY,1));
		t1.setEndDate(new GregorianCalendar(2009, Calendar.SEPTEMBER, 30));
		t1.setStatus(TrialStatus.ACTIVE);
		
		trialDao.save(t1);
		
		Trial t2 = trialDao.get(t1.getId());
		assertNotNull(t2);	
	}

}
