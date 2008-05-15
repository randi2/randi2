package de.randi2.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.validator.AssertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring.xml", "/META-INF/subconfig/test.xml"})
public class TrialTest extends AbstractDomainTest<Trial>{

	public TrialTest() {
		super(Trial.class);
	}
	
	private Trial validTrial;

	@Before
	public void setUp() throws Exception {
		// Valides Trial
		validTrial = new Trial();
		validTrial.setName("Aspirin vs. Placebo");

	}

	@Test
	public void testConstructor() {
		final Trial t = new Trial();
		assertEquals("", t.getName());
		assertEquals("", t.getDescription());
		assertNull(t.getStartDate());
		assertNull(t.getEndDate());
		assertEquals(Trial.TrialStatus.IN_PREPARATION, t.getStatus());
		assertNull(t.getProtocol());
		assertNotNull(t.getParticipatingCenters());
		assertEquals(0, t.getParticipatingCenters().size());
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
		assertValid(validTrial);

		// Richtiger Name
		validTrial.setName(nameOK2);
		assertEquals(nameOK2, validTrial.getName());
		assertValid(validTrial);

		validTrial.setName(nameToLong);
		assertEquals(nameToLong, validTrial.getName());
		assertInvalid(validTrial, new String[]{Trial.NAME_TO_LONG});
		
		validTrial.setName(nameJustOK);
		assertEquals(nameJustOK, validTrial.getName());
		assertValid(validTrial);
		
		validTrial.setName(nameEmpty);
		assertEquals(nameEmpty, validTrial.getName());
		assertInvalid(validTrial, new String[]{Trial.NAME_EMPTY});
		
		validTrial.setName(nameNull);
		assertEquals("", validTrial.getName());
		assertInvalid(validTrial, new String[]{Trial.NAME_EMPTY});
	}
	
	@Test
	public void testDescription() {
		final String emptyDescribtion = "";
		final String nullDescribtion = null;
		final String longDescribtion = stringUtil.getWithLength(4000);
		validTrial.setDescription(emptyDescribtion);
		assertEquals(emptyDescribtion, validTrial.getDescription());
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
		assertInvalid(validTrial, new String[]{Trial.NAME_WRONG_RANGE});
		this.setDateRange(startWD, endWD);
		assertInvalid(validTrial, new String[]{Trial.NAME_WRONG_RANGE});
		
		this.setDateRange(startSD, endSD);
		assertInvalid(validTrial, new String[]{Trial.NAME_WRONG_RANGE});
		
		this.setDateRange(startJust, endJust);
		assertInvalid(validTrial, new String[]{Trial.NAME_WRONG_RANGE});
		
		
	}
	
	private void setDateRange(GregorianCalendar start, GregorianCalendar end){
		validTrial.setStartDate(start);
		validTrial.setEndDate(end);
		assertEquals(start, validTrial.getStartDate());
		assertEquals(end, validTrial.getEndDate());
	}

	@Test
	public void testStatus() {
		validTrial.setStatus(Trial.TrialStatus.IN_PREPARATION);
		assertEquals(Trial.TrialStatus.IN_PREPARATION, validTrial.getStatus());
		assertValid(validTrial);

		validTrial.setStatus(Trial.TrialStatus.ACTIVE);
		assertEquals(Trial.TrialStatus.ACTIVE, validTrial.getStatus());
		assertValid(validTrial);
		
		validTrial.setStatus(Trial.TrialStatus.PAUSED);
		assertEquals(Trial.TrialStatus.PAUSED, validTrial.getStatus());
		assertValid(validTrial);

		validTrial.setStatus(Trial.TrialStatus.FINISHED);
		assertEquals(Trial.TrialStatus.FINISHED, validTrial.getStatus());
	}
	
	@Test
	@Transactional(propagation=Propagation.REQUIRED)
	public void testParticipatingCenters(){
		List<Center> cs = validTrial.getParticipatingCenters();
		assertNotNull(cs);
		final Center c = new Center();
		c.setName("TestCenter");
		cs.add(c);		
		
		hibernateTemplate.saveOrUpdate(validTrial);
		Trial t = (Trial) hibernateTemplate.get(Trial.class, validTrial.getId());
		assertEquals(1, t.getParticipatingCenters().size());
		assertNotNull(t.getParticipatingCenters().get(0));
		assertNotNull(t.getParticipatingCenters().get(0).getTrials());
		assertNotNull(t.getParticipatingCenters().get(0).getTrials().contains(validTrial));
		assertTrue(t.getParticipatingCenters().get(0).getTrials().contains(validTrial));
		
		
	}

	// @Test
	// TODO Implementing Trial Protocol behavior
	public void testProtocol() {
		fail("Not yet implemented");
	}

}
