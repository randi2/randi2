package de.randi2test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Person;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.enumerations.TrialStatus;
import de.randi2test.utility.AbstractDomainTest;

public class TrialTest extends AbstractDomainTest<Trial>{

	public TrialTest() {
		super(Trial.class);
	}
	
	private Trial validTrial;

	@Before
	public void setUp() throws Exception {
		// Valides Trial
		validTrial = factory.getTrial();
		hibernateTemplate.save(validTrial.getLeadingSite().getContactPerson());
	}

	@Test
	public void testConstructor() {
		final Trial t = new Trial();
		assertEquals("", t.getName());
		assertEquals("", t.getDescription());
		assertNull(t.getStartDate());
		assertNull(t.getEndDate());
		assertEquals(TrialStatus.IN_PREPARATION, t.getStatus());
		assertNull(t.getProtocol());
		assertEquals(0, t.getParticipatingSites().size());
	}

	@Test
	public void testName() {
		final String nameOK1 = "C";
		final String nameOK2 = stringUtil.getWithLength(100);
		final String nameJustOK = stringUtil.getWithLength(255);
		
		final String nameToLong = stringUtil.getWithLength(256);
		final String nameEmpty = "";
		final String nameNull = null;
		
		validTrial.setName(nameOK1);
		assertEquals(nameOK1, validTrial.getName());
		hibernateTemplate.save(validTrial.getLeadingSite());
		hibernateTemplate.save(validTrial.getSponsorInvestigator());
		assertValid(validTrial);
		
		// Richtiger Name
		validTrial.setName(nameOK2);
		assertEquals(nameOK2, validTrial.getName());
		assertValid(validTrial);

		validTrial.setName(nameNull);
		assertEquals("", validTrial.getName());
		assertInvalid(validTrial, new String[]{""/* validator.notEmpty */});

		validTrial.setName(nameToLong);
		assertEquals(nameToLong, validTrial.getName());
		assertInvalid(validTrial, new String[]{""/* validator.size */});
		
		validTrial.setName(nameJustOK);
		assertEquals(nameJustOK, validTrial.getName());
		assertValid(validTrial);
		
		validTrial.setName(nameEmpty);
		assertEquals(nameEmpty, validTrial.getName());
		assertInvalid(validTrial, new String[]{""/* validator.notEmpty */});
		
	}
	
	@Test
	public void testDescription() {
		final String emptyDescribtion = "";
		final String nullDescribtion = null;
		final String longDescribtion = stringUtil.getWithLength(4000);
		validTrial.setDescription(emptyDescribtion);
		assertEquals(emptyDescribtion, validTrial.getDescription());
		hibernateTemplate.save(validTrial.getLeadingSite());
		hibernateTemplate.save(validTrial.getSponsorInvestigator());
		assertValid(validTrial);

		validTrial.setDescription(nullDescribtion);
		assertEquals("", validTrial.getDescription());
		assertValid(validTrial);

		validTrial.setDescription(longDescribtion);
		assertEquals(longDescribtion, validTrial.getDescription());
		assertValid(validTrial);
	}

	@Test
	public void testStartDate() {
		final GregorianCalendar dateOK1 = new GregorianCalendar(1996,
				Calendar.FEBRUARY, 29);
		final GregorianCalendar dateOK2 = new GregorianCalendar(2006,
				Calendar.NOVEMBER, 3);

		validTrial.setStartDate(dateOK1);
		assertEquals(dateOK1, validTrial.getStartDate());
		hibernateTemplate.save(validTrial.getLeadingSite());
		hibernateTemplate.save(validTrial.getSponsorInvestigator());
		assertValid(validTrial);

		validTrial.setStartDate(dateOK2);
		assertEquals(dateOK2, validTrial.getStartDate());
		assertValid(validTrial);
	}

	@Test
	public void testEndDate() {
		validTrial.setStartDate(new GregorianCalendar(2000, Calendar.JANUARY,
				2000));

		final GregorianCalendar dateOK1 = new GregorianCalendar(2007,
				Calendar.JANUARY, 1);
		final GregorianCalendar dateOK2 = new GregorianCalendar(2004,
				Calendar.FEBRUARY, 29);
		final GregorianCalendar dateOK3 = new GregorianCalendar(2010,
				Calendar.DECEMBER, 31);

		validTrial.setStartDate(dateOK1);
		assertEquals(dateOK1, validTrial.getStartDate());
		hibernateTemplate.save(validTrial.getLeadingSite());
		hibernateTemplate.save(validTrial.getSponsorInvestigator());
		assertValid(validTrial);

		validTrial.setStartDate(dateOK2);
		assertEquals(dateOK2, validTrial.getStartDate());
		assertValid(validTrial);

		validTrial.setStartDate(dateOK3);
		assertEquals(dateOK3, validTrial.getStartDate());
		assertValid(validTrial);
	}
	
	@Test
	public void testDateRange(){
		/* Valid Ranges*/
		final GregorianCalendar startOK1 = new GregorianCalendar(2010, Calendar.OCTOBER, 10);
		final GregorianCalendar endOK1 = new GregorianCalendar(2011, Calendar.OCTOBER, 10);
		
		final GregorianCalendar startOK2 = new GregorianCalendar(1996, Calendar.JANUARY, 2);
		final GregorianCalendar endOK2 = new GregorianCalendar(1996, Calendar.JANUARY, 3);
		
		/* Open Ranges*/
		final GregorianCalendar startOKOpen = new GregorianCalendar(2001, Calendar.MARCH, 2);
		// No End set
		final GregorianCalendar endOKOpen = new GregorianCalendar(2020, Calendar.JUNE, 2);
		// No start set
		
		/* Wrong Ranges*/
		// Small difference
		final GregorianCalendar startSD = new GregorianCalendar(1996, Calendar.JANUARY, 2);
		final GregorianCalendar endSD = new GregorianCalendar(1996, Calendar.JANUARY, 2,0,0,1);
		// Same
		final GregorianCalendar startSame = new GregorianCalendar(2006, Calendar.JULY, 2);
		final GregorianCalendar endSame = new GregorianCalendar(2006, Calendar.JULY, 2);
		// Wrong direction
		final GregorianCalendar startWD = new GregorianCalendar(1986, Calendar.AUGUST, 2);
		final GregorianCalendar endWD = new GregorianCalendar(1985, Calendar.MAY, 1);
		// Just under one day differenz
		final GregorianCalendar startJust = new GregorianCalendar(2001, Calendar.SEPTEMBER, 11);
		final GregorianCalendar endJust = new GregorianCalendar(1985, Calendar.SEPTEMBER, 11, 23, 59, 59);
	
		
		hibernateTemplate.save(validTrial.getLeadingSite());
		hibernateTemplate.save(validTrial.getSponsorInvestigator());
		this.setDateRange(startOK1, endOK1);
		assertValid(validTrial);
		this.setDateRange(startOK2, endOK2);
		assertValid(validTrial);
		this.setDateRange(startOKOpen, null);
		assertValid(validTrial);
		this.setDateRange(null, endOKOpen);
		assertValid(validTrial);
		this.setDateRange(null, null);
		assertValid(validTrial);
		
		this.setDateRange(startSame, endSame);
		//assertInvalid(validTrial, new String[]{Trial.DATES_WRONG_RANGE});	
		
		this.setDateRange(startWD, endWD);
		//assertInvalid(validTrial, new String[]{Trial.DATES_WRONG_RANGE});
		
		this.setDateRange(startSD, endSD);
		//assertInvalid(validTrial, new String[]{Trial.DATES_WRONG_RANGE});
		
		this.setDateRange(startJust, endJust);
		//assertInvalid(validTrial, new String[]{Trial.DATES_WRONG_RANGE});
		
		
	}
	
	private void setDateRange(GregorianCalendar start, GregorianCalendar end){
		validTrial.setStartDate(start);
		validTrial.setEndDate(end);
		assertEquals(start, validTrial.getStartDate());
		assertEquals(end, validTrial.getEndDate());
	}

	@Test
	public void testStatus() {
		hibernateTemplate.save(validTrial.getLeadingSite());
		hibernateTemplate.save(validTrial.getSponsorInvestigator());
		validTrial.setStatus(TrialStatus.IN_PREPARATION);
		assertEquals(TrialStatus.IN_PREPARATION, validTrial.getStatus());
		assertValid(validTrial);

		validTrial.setStatus(TrialStatus.ACTIVE);
		assertEquals(TrialStatus.ACTIVE, validTrial.getStatus());
		assertValid(validTrial);
		
		validTrial.setStatus(TrialStatus.PAUSED);
		assertEquals(TrialStatus.PAUSED, validTrial.getStatus());
		assertValid(validTrial);

		validTrial.setStatus(TrialStatus.FINISHED);
		assertEquals(TrialStatus.FINISHED, validTrial.getStatus());
	}
	
	@Test 
	public void testLeader(){
		Person p = factory.getPerson();
		
		//validTrial.setLeader(p);
		
		//p.getRollen().contains()
		//fail("Not yet implemented");
	}
	
	@Test
	public void testLeadingSite(){
		final TrialSite c = factory.getTrialSite();
		hibernateTemplate.save(c.getContactPerson());
		hibernateTemplate.save(c);
		hibernateTemplate.save(validTrial.getSponsorInvestigator());
		validTrial.setLeadingSite(c);
		assertEquals(c, validTrial.getLeadingSite());
		
		hibernateTemplate.saveOrUpdate(validTrial);
		assertNotSame(AbstractDomainObject.NOT_YET_SAVED_ID, c.getId());
		int version= c.getVersion();
		
		c.setName(stringUtil.getWithLength(20));
		hibernateTemplate.saveOrUpdate(c);
		assertTrue(version < c.getVersion());
		
	}
	
	@Test
	public void testPartSits(){
		Set<TrialSite> cl = validTrial.getParticipatingSites();
		
		TrialSite c1 = factory.getTrialSite();
		TrialSite c2 = factory.getTrialSite();
		TrialSite c3 = factory.getTrialSite();
		
		
		cl.add(c1);
		hibernateTemplate.save(c1.getContactPerson().getLogin());
		hibernateTemplate.save(c1);
		cl.add(c2);
		hibernateTemplate.save(c2.getContactPerson().getLogin());
		hibernateTemplate.save(c2);
		cl.add(c3);
		hibernateTemplate.save(c3.getContactPerson().getLogin());
		hibernateTemplate.save(c3);
		hibernateTemplate.save(validTrial.getLeadingSite());
		hibernateTemplate.save(validTrial.getSponsorInvestigator());
		hibernateTemplate.saveOrUpdate(validTrial);
		assertNotSame(AbstractDomainObject.NOT_YET_SAVED_ID, c1.getId());
		assertNotSame(AbstractDomainObject.NOT_YET_SAVED_ID, c2.getId());
		assertNotSame(AbstractDomainObject.NOT_YET_SAVED_ID, c3.getId());
		
		//hibernateTemplate.flush();
		//hibernateTemplate.refresh(validTrial);
		
		assertEquals(3, validTrial.getParticipatingSites().size());
		
		cl = validTrial.getParticipatingSites();
		cl.remove(c2);
		hibernateTemplate.saveOrUpdate(validTrial);
		
		//hibernateTemplate.flush();
		//hibernateTemplate.refresh(validTrial);
		
		//assertEquals(2, validTrial.getParticipatingSites().size());
		
		//((TrialSite) validTrial.getParticipatingSites().toArray()[0]).setName(stringUtil.getWithLength(20));
		int version = ((AbstractDomainObject) validTrial.getParticipatingSites().toArray()[0]).getVersion();
		
		hibernateTemplate.update(validTrial);
		Set<TrialSite> trialSites = validTrial.getParticipatingSites();
		assertTrue(version < ((AbstractDomainObject) trialSites.toArray()[0]).getId());
	}
	
	 @Test
	// TODO Implementing Trial Protocol behavior
	public void testProtocol() {
		//fail("Not yet implemented");
	}

}
