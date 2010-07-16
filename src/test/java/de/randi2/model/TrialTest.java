package de.randi2.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.model.enumerations.TrialStatus;
import de.randi2.model.randomization.CompleteRandomizationConfig;
import de.randi2.test.utility.AbstractDomainTest;
import de.randi2.unsorted.ContraintViolatedException;
import de.randi2.utility.Pair;
import edu.emory.mathcs.backport.java.util.Arrays;

public class TrialTest extends AbstractDomainTest<Trial> {

	public TrialTest() {
		super(Trial.class);
	}

	private Trial validTrial;

	@Before
	public void setUp() {
		// Valides Trial
		super.setUp();
		validTrial = factory.getTrial();
		hibernateTemplate.save(validTrial.getLeadingSite().getContactPerson());
	}

	@Test
	public void testConstructor() {
		final Trial t = new Trial();
		assertEquals("", t.getName());
		assertEquals("", t.getDescription());
		assertEquals("", t.getAbbreviation());
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
//		final String nameNull = null;

		validTrial.setName(nameOK1);
		assertEquals(nameOK1, validTrial.getName());
		hibernateTemplate.save(validTrial.getLeadingSite());
		hibernateTemplate.save(validTrial.getSponsorInvestigator());
		assertValid(validTrial);

		// Richtiger Name
		validTrial.setName(nameOK2);
		assertEquals(nameOK2, validTrial.getName());
		assertValid(validTrial);

//		validTrial.setName(nameNull);
//		assertEquals("", validTrial.getName());
//		assertInvalid(validTrial, new String[] { ""/* validator.notEmpty */});

		validTrial.setName(nameToLong);
		assertEquals(nameToLong, validTrial.getName());
		assertInvalid(validTrial, new String[] { ""/* validator.size */});

		validTrial.setName(nameJustOK);
		assertEquals(nameJustOK, validTrial.getName());
		assertValid(validTrial);

		validTrial.setName(nameEmpty);
		assertEquals(nameEmpty, validTrial.getName());
		assertInvalid(validTrial, new String[] { ""/* validator.notEmpty */});

	}

	@Test
	public void testAbbreviation() {
		final String abbOK1 = "C";
		final String abbOK2 = stringUtil.getWithLength(100);
		final String abbJustOK = stringUtil.getWithLength(255);

		final String abbToLong = stringUtil.getWithLength(256);
		final String abbEmpty = "";
//		final String abbNull = null;

		validTrial.setAbbreviation(abbOK1);
		assertEquals(abbOK1, validTrial.getAbbreviation());
		hibernateTemplate.save(validTrial.getLeadingSite());
		hibernateTemplate.save(validTrial.getSponsorInvestigator());
		assertValid(validTrial);

		// Richtiger Name
		validTrial.setAbbreviation(abbOK2);
		assertEquals(abbOK2, validTrial.getAbbreviation());
		assertValid(validTrial);

//		validTrial.setAbbreviation(abbNull);
//		assertEquals("", validTrial.getAbbreviation());
//		assertValid(validTrial);

		validTrial.setAbbreviation(abbToLong);
		assertEquals(abbToLong, validTrial.getAbbreviation());
		assertValid(validTrial);

		validTrial.setAbbreviation(abbJustOK);
		assertEquals(abbJustOK, validTrial.getAbbreviation());
		assertValid(validTrial);

		validTrial.setAbbreviation(abbEmpty);
		assertEquals(abbEmpty, validTrial.getAbbreviation());
		assertValid(validTrial);

	}

	@Test
	public void testDescription() {
		final String emptyDescribtion = "";
//		final String nullDescribtion = null;
		final String longDescribtion = stringUtil.getWithLength(4000);
		validTrial.setDescription(emptyDescribtion);
		assertEquals(emptyDescribtion, validTrial.getDescription());
		hibernateTemplate.save(validTrial.getLeadingSite());
		hibernateTemplate.save(validTrial.getSponsorInvestigator());
		assertValid(validTrial);

//		validTrial.setDescription(nullDescribtion);
//		assertEquals("", validTrial.getDescription());
//		assertValid(validTrial);

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
	public void testDateRange() {
		/* Valid Ranges */
		final GregorianCalendar startOK1 = new GregorianCalendar(2010,
				Calendar.OCTOBER, 10);
		final GregorianCalendar endOK1 = new GregorianCalendar(2011,
				Calendar.OCTOBER, 10);

		final GregorianCalendar startOK2 = new GregorianCalendar(1996,
				Calendar.JANUARY, 2);
		final GregorianCalendar endOK2 = new GregorianCalendar(1996,
				Calendar.JANUARY, 3);

		/* Open Ranges */
		final GregorianCalendar startOKOpen = new GregorianCalendar(2001,
				Calendar.MARCH, 2);
		// No End set
		final GregorianCalendar endOKOpen = new GregorianCalendar(2020,
				Calendar.JUNE, 2);
		// No start set

		/* Wrong Ranges */
		// Small difference
		final GregorianCalendar startSD = new GregorianCalendar(1996,
				Calendar.JANUARY, 2);
		final GregorianCalendar endSD = new GregorianCalendar(1996,
				Calendar.JANUARY, 2, 0, 0, 1);
		// Same
		final GregorianCalendar startSame = new GregorianCalendar(2006,
				Calendar.JULY, 2);
		final GregorianCalendar endSame = new GregorianCalendar(2006,
				Calendar.JULY, 2);
		// Wrong direction
		final GregorianCalendar startWD = new GregorianCalendar(1986,
				Calendar.AUGUST, 2);
		final GregorianCalendar endWD = new GregorianCalendar(1985,
				Calendar.MAY, 1);
		// Just under one day differenz
		final GregorianCalendar startJust = new GregorianCalendar(2001,
				Calendar.SEPTEMBER, 11);
		final GregorianCalendar endJust = new GregorianCalendar(1985,
				Calendar.SEPTEMBER, 11, 23, 59, 59);

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
		// assertInvalid(validTrial, new String[]{Trial.DATES_WRONG_RANGE});

		this.setDateRange(startWD, endWD);
		// assertInvalid(validTrial, new String[]{Trial.DATES_WRONG_RANGE});

		this.setDateRange(startSD, endSD);
		// assertInvalid(validTrial, new String[]{Trial.DATES_WRONG_RANGE});

		this.setDateRange(startJust, endJust);
		// assertInvalid(validTrial, new String[]{Trial.DATES_WRONG_RANGE});

	}

	private void setDateRange(GregorianCalendar start, GregorianCalendar end) {
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
	public void testSponsorInvestigator() {
		Person p = factory.getPerson();

		 validTrial.setSponsorInvestigator(p);
		 assertEquals(p, validTrial.getSponsorInvestigator());
	}

	@Test
	public void testLeadingSite() {
		final TrialSite c = factory.getTrialSite();
		hibernateTemplate.save(c.getContactPerson());
		hibernateTemplate.save(c);
		hibernateTemplate.save(validTrial.getSponsorInvestigator());
		validTrial.setLeadingSite(c);
		assertEquals(c, validTrial.getLeadingSite());

		hibernateTemplate.saveOrUpdate(validTrial);
		assertNotSame(AbstractDomainObject.NOT_YET_SAVED_ID, c.getId());
		int version = c.getVersion();

		c.setName(stringUtil.getWithLength(20));
		hibernateTemplate.saveOrUpdate(c);
		assertTrue(version < c.getVersion());

	}

	@Test
	public void testPartSits() {
		Set<TrialSite> cl = validTrial.getParticipatingSites();

		TrialSite c1 = factory.getTrialSite();
		TrialSite c2 = factory.getTrialSite();
		TrialSite c3 = factory.getTrialSite();

		cl.add(c1);
		hibernateTemplate.save(c1);
		cl.add(c2);
		hibernateTemplate.save(c2);
		cl.add(c3);
		hibernateTemplate.save(c3);
		hibernateTemplate.save(validTrial.getLeadingSite());
		hibernateTemplate.save(validTrial.getSponsorInvestigator());
		hibernateTemplate.saveOrUpdate(validTrial);
		assertNotSame(AbstractDomainObject.NOT_YET_SAVED_ID, c1.getId());
		assertNotSame(AbstractDomainObject.NOT_YET_SAVED_ID, c2.getId());
		assertNotSame(AbstractDomainObject.NOT_YET_SAVED_ID, c3.getId());

		// hibernateTemplate.flush();
		// hibernateTemplate.refresh(validTrial);

		assertEquals(3, validTrial.getParticipatingSites().size());

		cl = validTrial.getParticipatingSites();
		cl.remove(c2);
		hibernateTemplate.saveOrUpdate(validTrial);

		// hibernateTemplate.flush();
		// hibernateTemplate.refresh(validTrial);

		// assertEquals(2, validTrial.getParticipatingSites().size());

		// ((TrialSite)
		// validTrial.getParticipatingSites().toArray()[0]).setName(stringUtil.getWithLength(20));
		int version = ((AbstractDomainObject) validTrial
				.getParticipatingSites().toArray()[0]).getVersion();

		hibernateTemplate.update(validTrial);
		Set<TrialSite> trialSites = validTrial.getParticipatingSites();
		assertTrue(version < ((AbstractDomainObject) trialSites.toArray()[0])
				.getId());
	}

	@Test
	// TODO Implementing Trial Protocol behavior
	public void testProtocol() {
		// fail("Not yet implemented");
	}

	@Test
	public void testTreatmentArms() {
		TreatmentArm arm1 = new TreatmentArm();
		arm1.setName("arm1");
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setName("arm2");
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		arms.add(arm1);
		arms.add(arm2);
		validTrial.setTreatmentArms(arms);

		assertEquals(2, validTrial.getTreatmentArms().size());
		assertTrue(validTrial.getTreatmentArms().containsAll(arms));
	}

	@Test
	public void testCriteria() {
		DichotomousCriterion criterion1 = new DichotomousCriterion();
		criterion1.setName("criterion1");
		DichotomousCriterion criterion2 = new DichotomousCriterion();
		criterion2.setName("criterion2");
		// List<AbstractCriterion<? extends Serializable, ? extends
		// AbstractConstraint<? extends Serializable>>> criterions = new
		// ArrayList<AbstractCriterion<? extends Serializable,? extends
		// AbstractConstraint<? extends Serializable>>>();
		// criterions.add(criterion1);
		// criterions.add(criterion2);
		// validTrial.setCriteria(criterions);
		validTrial.addCriterion(criterion1);
		validTrial.addCriterion(criterion2);

		assertEquals(2, validTrial.getCriteria().size());
		assertTrue(DichotomousCriterion.class.isInstance(validTrial
				.getCriteria().get(0)));
		assertEquals("criterion1", validTrial.getCriteria().get(0).getName());
		assertTrue(DichotomousCriterion.class.isInstance(validTrial
				.getCriteria().get(1)));
		assertEquals("criterion2", validTrial.getCriteria().get(1).getName());
		
		
		
		List<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> list = new ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>>();
		validTrial.setCriteria(list);
		assertEquals(list, validTrial.getCriteria());
	}

	@Test
	public void testRandomizationConfiguration() {
		CompleteRandomizationConfig conf = new CompleteRandomizationConfig();
		validTrial.setRandomizationConfiguration(conf);
		TreatmentArm arm1 = new TreatmentArm();
		arm1.setName("arm1");
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setName("arm2");
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		arms.add(arm1);
		arms.add(arm2);
		validTrial.setTreatmentArms(arms);
		assertEquals(conf, validTrial.getRandomizationConfiguration());

		// List<TrialSubject> subjects = new ArrayList<TrialSubject>();
		// for(int i = 0;i<100;i++){
		// TrialSubject subject = new TrialSubject();
		// subject.setIdentification("identification" + i);
		// subjects.add(subject);
		// validTrial.randomize(subject);
		// }
		// assertEquals(subjects.size(), validTrial.getSubjects().size());
		// assertEquals(subjects.size(),
		// arm1.getSubjects().size()+arm2.getSubjects().size());

	}

	@Test
	public void databaseIntegrationTest() {
		TrialSite leadingSite = factory.getTrialSite();
		leadingSite.setContactPerson(factory.getPerson());
		// hibernateTemplate.persist(leadingSite.getContactPerson());
		// hibernateTemplate.flush();
		hibernateTemplate.persist(leadingSite);
		validTrial.setSponsorInvestigator(leadingSite.getContactPerson());
		validTrial.setLeadingSite(leadingSite);

		TrialSite pTrialSite = factory.getTrialSite();
		pTrialSite.setContactPerson(factory.getPerson());
		// hibernateTemplate.persist(pTrialSite.getContactPerson());
		hibernateTemplate.persist(pTrialSite);
		validTrial.addParticipatingSite(pTrialSite);
		CompleteRandomizationConfig conf = new CompleteRandomizationConfig();
		validTrial.setRandomizationConfiguration(conf);
		hibernateTemplate.persist(validTrial);
		TreatmentArm arm1 = new TreatmentArm();
		arm1.setName("arm1");
		arm1.setTrial(validTrial);
		arm1.setPlannedSubjects(100);
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setName("arm2");
		arm2.setTrial(validTrial);
		arm2.setPlannedSubjects(100);
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		arms.add(arm1);
		arms.add(arm2);
		hibernateTemplate.persist(arm1);
		hibernateTemplate.persist(arm2);
		validTrial.setTreatmentArms(arms);

		hibernateTemplate.saveOrUpdate(validTrial);
		assertTrue(validTrial.getId() > 0);
//		validTrial = (Trial) hibernateTemplate.get(Trial.class, validTrial
//				.getId());

		List<TrialSubject> subjects = new ArrayList<TrialSubject>();
		for (int i = 0; i < 100; i++) {
			TrialSubject subject = new TrialSubject();

			TreatmentArm assignedArm = validTrial
					.getRandomizationConfiguration().getAlgorithm().randomize(
							subject);
			subject.setIdentification("identification" + i);
			subject.setArm(assignedArm);
			subject.setRandNumber(assignedArm.getName() + "_"
					+ (assignedArm.getSubjects().size() + 1));
			subject.setCounter((validTrial.getSubjects().size() + 1));
			if (subject.getIdentification() == null)
				subject.setIdentification(subject.getRandNumber());
			subjects.add(subject);
		}
		hibernateTemplate.update(validTrial);
		Trial dbTrial = (Trial) hibernateTemplate.get(Trial.class, validTrial
				.getId());

		assertEquals(validTrial.getId(), dbTrial.getId());
		assertEquals(validTrial.getName(), dbTrial.getName());
		assertEquals(validTrial.getDescription(), dbTrial.getDescription());
		assertEquals(validTrial.getLeadingSite().getName(), dbTrial
				.getLeadingSite().getName());
		assertEquals(validTrial.getRandomizationConfiguration().getId(),
				dbTrial.getRandomizationConfiguration().getId());
		assertEquals(validTrial.getRandomizationConfiguration().getAlgorithm()
				.getClass(), dbTrial.getRandomizationConfiguration()
				.getAlgorithm().getClass());
//		 TrialSite dbTrialSite =
//		 (TrialSite)hibernateTemplate.get(TrialSite.class,
//		 pTrialSite.getId());
//		 assertEquals(1, dbTrialSite.getTrials());
//		 assertEquals(validTrial.getParticipatingSites().size(),
//		 dbTrial.getParticipatingSites().size());
//		assertEquals(validTrial.getTreatmentArms().get(0).getSubjects().size(),
//				dbTrial.getTreatmentArms().get(0).getSubjects().size());
//		assertEquals(validTrial.getTreatmentArms().get(1).getSubjects().size(),
//				dbTrial.getTreatmentArms().get(1).getSubjects().size());

	}
	
	
	@Test
	public void testGetRequieredFields(){
		Map<String, Boolean> map = validTrial.getRequiredFields();
		for(String key : map.keySet()){
			if(key.equals("name")) {assertTrue(map.get(key));} 
			else if(key.equals("abbreviation")) {assertFalse(map.get(key));} 
			else if(key.equals("stratifyTrialSite")) {assertFalse(map.get(key));}  
			else if(key.equals("description")) {assertFalse(map.get(key));} 
			else if(key.equals("startDate")) {assertFalse(map.get(key));} 
			else if(key.equals("endDate")) {assertFalse(map.get(key)); }
			else if(key.equals("protocol")) {assertFalse(map.get(key));} 
			else if(key.equals("sponsorInvestigator")) {assertTrue(map.get(key));} 
			else if(key.equals("leadingSite")) {assertTrue(map.get(key)); }
			else if(key.equals("status")) {assertFalse(map.get(key));}  
			else if(key.equals("participatingSites")) {assertFalse(map.get(key));} 
			else if(key.equals("treatmentArms")) {assertFalse(map.get(key));} 
			else if(key.equals("subjectCriteria")) {assertFalse(map.get(key));}
			else if(key.equals("randomConf")) {assertFalse(map.get(key));} 
			else if(key.equals("generateIds")) {assertFalse(map.get(key));} 
			else if(key.equals("serialVersionUID")) {assertFalse(map.get(key));}
			else if(key.equals("SUBJECT_COUNT_COMPERATOR")) {assertFalse(map.get(key));}
			else if(key.equals("$VRc")) {assertFalse(map.get(key));}
			else fail(key + " not checked");
		}
	}
	
	@Test
	public void testEqualsHashCode(){
		Trial trial1 = new Trial();
		Trial trial2 = new Trial();
		trial1.setId(0);
		trial2.setId(0);
		trial1.setVersion(0);
		trial2.setVersion(0);
		assertEquals(trial1, trial2);
		assertEquals(trial1.hashCode(), trial2.hashCode());
		trial1.setId(1);
		
		assertFalse(trial1.equals(trial2));
		trial1.setId(0);
		assertEquals(trial1, trial2);
		assertEquals(trial1.hashCode(), trial2.hashCode());
		
		trial1.setVersion(1);
		assertFalse(trial1.equals(trial2));
		trial1.setVersion(0);
		assertEquals(trial1, trial2);
		assertEquals(trial1.hashCode(), trial2.hashCode());
		
		trial1.setName("test");
		assertFalse(trial1.equals(trial2));
		trial2.setName("test");
		assertEquals(trial1, trial2);
		assertEquals(trial1.hashCode(), trial2.hashCode());
		
		assertFalse(trial1.equals(null));
		assertFalse(trial1.equals(new TreatmentArm()));
	}
	
	
	@Test
	public void testTotalSubjectAmount(){
		TreatmentArm arm1 = new TreatmentArm();
		arm1.setName("arm1");
		arm1.setTrial(validTrial);
		arm1.setPlannedSubjects(100);
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setName("arm2");
		arm2.setTrial(validTrial);
		arm2.setPlannedSubjects(100);
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		arms.add(arm1);
		arms.add(arm2);
		validTrial.setTreatmentArms(arms);
		for(int i=1;i<=100;i++){
			if(i%2==0){
				arm1.addSubject(new TrialSubject());
			}else{
				arm2.addSubject(new TrialSubject());
			}
			assertEquals(i, validTrial.getTotalSubjectAmount());
		}
	}
	
	@Test
	public void testPlannedSubjectAmount(){
		TreatmentArm arm1 = new TreatmentArm();
		arm1.setName("arm1");
		arm1.setTrial(validTrial);
		arm1.setPlannedSubjects(100);
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setName("arm2");
		arm2.setTrial(validTrial);
		arm2.setPlannedSubjects(100);
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		arms.add(arm1);
		arms.add(arm2);
		validTrial.setTreatmentArms(arms);
		assertEquals(200, validTrial.getPlannedSubjectAmount());
	}
	
	@Test
	public void testUiName(){
		validTrial.setAbbreviation("abbreviation");
		assertEquals("abbreviation", validTrial.getUIName());
	}
	
	@Test
	public void testIsFresh(){
		TreatmentArm arm1 = new TreatmentArm();
		arm1.setName("arm1");
		arm1.setTrial(validTrial);
		arm1.setPlannedSubjects(100);
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setName("arm2");
		arm2.setTrial(validTrial);
		arm2.setPlannedSubjects(100);
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		arms.add(arm1);
		arms.add(arm2);
		validTrial.setTreatmentArms(arms);
		assertTrue(validTrial.isFresh());
		arm1.addSubject(new TrialSubject());
		assertFalse(validTrial.isFresh());
	}
	
	@Test
	public void testGenerateIds(){
		validTrial.setGenerateIds(true);
		assertTrue(validTrial.isGenerateIds());
		validTrial.setGenerateIds(false);
		assertFalse(validTrial.isGenerateIds());
	}
	
	@Test
	public void testStratifyTrialSite(){
		validTrial.setStratifyTrialSite(true);
		assertTrue(validTrial.isStratifyTrialSite());
		validTrial.setStratifyTrialSite(false);
		assertFalse(validTrial.isStratifyTrialSite());
	}
	
	
	@Test
	public void testStrataNamesAndIdsStrataCriterions(){
		DichotomousCriterion criterion1 = new DichotomousCriterion();
		criterion1.setId(1);
		criterion1.setName("criterion1");
		criterion1.setOption1("option1");
		criterion1.setOption2("option2");
		try {
			DichotomousConstraint d1 = new DichotomousConstraint(Arrays.asList(new String[]{"option1"}));
			d1.setId(1);
			criterion1.addStrata(d1);
			DichotomousConstraint d2 = new DichotomousConstraint(Arrays.asList(new String[]{"option2"}));
			d2.setId(2);
			criterion1.addStrata(d2);
		} catch (ContraintViolatedException e) {
			fail();
		}
		DichotomousCriterion criterion2 = new DichotomousCriterion();
		criterion2.setId(2);
		criterion2.setName("criterion2");
		criterion2.setOption1("option1");
		criterion2.setOption2("option2");
		try {
			DichotomousConstraint d1 = new DichotomousConstraint(Arrays.asList(new String[]{"option1"}));
			d1.setId(1);
			criterion2.addStrata(d1);
			DichotomousConstraint d2 = new DichotomousConstraint(Arrays.asList(new String[]{"option2"}));
			d2.setId(2);
			criterion2.addStrata(d2);
		} catch (ContraintViolatedException e) {
			fail();
		}
		validTrial.addCriterion(criterion1);
		validTrial.addCriterion(criterion2);
		
		Pair<List<String>, List<String>> pair = validTrial.getAllStrataIdsAndNames();
		
		assertEquals(4, pair.first().size());
		assertEquals(4, pair.last().size());
		
		Collections.sort(pair.first());
		assertEquals("1_1;2_1;", pair.first().get(0));
		assertEquals("1_1;2_2;", pair.first().get(1));
		assertEquals("1_2;2_1;", pair.first().get(2));
		assertEquals("1_2;2_2;", pair.first().get(3));
		
		Collections.sort(pair.last());
		assertEquals("criterion1_option1;criterion2_option1;", pair.last().get(0));
		assertEquals("criterion1_option1;criterion2_option2;", pair.last().get(1));
		assertEquals("criterion1_option2;criterion2_option1;", pair.last().get(2));
		assertEquals("criterion1_option2;criterion2_option2;", pair.last().get(3));
	}
	
	
	@Test
	public void testStrataNamesAndIdsStrataCriterionsTrialSite(){

		TrialSite site1 = new TrialSite();
		site1.setId(1);
		site1.setName("site1");

		TrialSite site2 = new TrialSite();
		site2.setId(2);
		site2.setName("site2");
		
		validTrial.addParticipatingSite(site1);
		validTrial.addParticipatingSite(site2);
		
		validTrial.setStratifyTrialSite(true);
		
		DichotomousCriterion criterion1 = new DichotomousCriterion();
		criterion1.setId(1);
		criterion1.setName("criterion1");
		criterion1.setOption1("option1");
		criterion1.setOption2("option2");
		try {
			DichotomousConstraint d1 = new DichotomousConstraint(Arrays.asList(new String[]{"option1"}));
			d1.setId(1);
			criterion1.addStrata(d1);
			DichotomousConstraint d2 = new DichotomousConstraint(Arrays.asList(new String[]{"option2"}));
			d2.setId(2);
			criterion1.addStrata(d2);
		} catch (ContraintViolatedException e) {
			fail();
		}
		DichotomousCriterion criterion2 = new DichotomousCriterion();
		criterion2.setId(2);
		criterion2.setName("criterion2");
		criterion2.setOption1("option1");
		criterion2.setOption2("option2");
		try {
			DichotomousConstraint d1 = new DichotomousConstraint(Arrays.asList(new String[]{"option1"}));
			d1.setId(1);
			criterion2.addStrata(d1);
			DichotomousConstraint d2 = new DichotomousConstraint(Arrays.asList(new String[]{"option2"}));
			d2.setId(2);
			criterion2.addStrata(d2);
		} catch (ContraintViolatedException e) {
			fail();
		}
		validTrial.addCriterion(criterion1);
		validTrial.addCriterion(criterion2);
		
		Pair<List<String>, List<String>> pair = validTrial.getAllStrataIdsAndNames();
		
		assertEquals(8, pair.first().size());
		assertEquals(8, pair.last().size());
		
		Collections.sort(pair.first());
		assertEquals("1__1_1;2_1;", pair.first().get(0));
		assertEquals("1__1_1;2_2;", pair.first().get(1));
		assertEquals("1__1_2;2_1;", pair.first().get(2));
		assertEquals("1__1_2;2_2;", pair.first().get(3));
		assertEquals("2__1_1;2_1;", pair.first().get(4));
		assertEquals("2__1_1;2_2;", pair.first().get(5));
		assertEquals("2__1_2;2_1;", pair.first().get(6));
		assertEquals("2__1_2;2_2;", pair.first().get(7));
		
		Collections.sort(pair.last());
		assertEquals("site1 | criterion1_option1;criterion2_option1;", pair.last().get(0));
		assertEquals("site1 | criterion1_option1;criterion2_option2;", pair.last().get(1));
		assertEquals("site1 | criterion1_option2;criterion2_option1;", pair.last().get(2));
		assertEquals("site1 | criterion1_option2;criterion2_option2;", pair.last().get(3));
		assertEquals("site2 | criterion1_option1;criterion2_option1;", pair.last().get(4));
		assertEquals("site2 | criterion1_option1;criterion2_option2;", pair.last().get(5));
		assertEquals("site2 | criterion1_option2;criterion2_option1;", pair.last().get(6));
		assertEquals("site2 | criterion1_option2;criterion2_option2;", pair.last().get(7));
	}
	
	
	@Test
	public void testStrataNamesAndIdsStrataTrialSite(){

		TrialSite site1 = new TrialSite();
		site1.setId(1);
		site1.setName("site1");

		TrialSite site2 = new TrialSite();
		site2.setId(2);
		site2.setName("site2");
		
		TrialSite site3 = new TrialSite();
		site3.setId(3);
		site3.setName("site3");
		
		TrialSite site4 = new TrialSite();
		site4.setId(4);
		site4.setName("site4");
		
		validTrial.addParticipatingSite(site1);
		validTrial.addParticipatingSite(site2);
		validTrial.addParticipatingSite(site3);
		validTrial.addParticipatingSite(site4);
		
		validTrial.setStratifyTrialSite(true);
		
		
		Pair<List<String>, List<String>> pair = validTrial.getAllStrataIdsAndNames();
		
		assertEquals(4, pair.first().size());
		assertEquals(4, pair.last().size());
		
		Collections.sort(pair.first());
		assertEquals("1__", pair.first().get(0));
		assertEquals("2__", pair.first().get(1));
		assertEquals("3__", pair.first().get(2));
		assertEquals("4__", pair.first().get(3));
		
		Collections.sort(pair.last());
		assertEquals("site1", pair.last().get(0));
		assertEquals("site2", pair.last().get(1));
		assertEquals("site3", pair.last().get(2));
		assertEquals("site4", pair.last().get(3));

	}
	
}
