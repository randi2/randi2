package de.randi2.core.unit.model;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertFalse;
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

import de.randi2.model.Person;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.TrialSubject;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.model.enumerations.TrialStatus;
import de.randi2.model.randomization.CompleteRandomizationConfig;
import de.randi2.testUtility.utility.AbstractDomainTest;
import de.randi2.unsorted.ContraintViolatedException;
import de.randi2.utility.Pair;
import java.util.Arrays;

public class TrialTest extends AbstractDomainTest<Trial> {

	public TrialTest() {
		super(Trial.class);
	}

	private Trial validTrial;

	@Before
	public void setUp() {
		// Valides Trial
		validTrial = factory.getTrial();
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
	public void testNameNotNull() {
		validTrial.setName(null);
		assertNull(validTrial.getName());
		assertInvalid(validTrial);
	}

	@Test
	public void testNameNotEmpty() {
		validTrial.setName("");
		assertEquals("", validTrial.getName());
		assertInvalid(validTrial);
	}

	@Test
	public void testNameLongerThan255() {
		String[] invalidValues = { stringUtil.getWithLength(256),
				stringUtil.getWithLength(650) };
		for (String s : invalidValues) {
			validTrial.setName(s);
			assertEquals(s, validTrial.getName());
			assertInvalid(validTrial);
		}
	}

	@Test
	public void testNameCorrect() {
		String[] validValues = { stringUtil.getWithLength(254),
				stringUtil.getWithLength(2), "Name",
				stringUtil.getWithLength(132) };
		for (String s : validValues) {
			validTrial.setName(s);
			assertEquals(s, validTrial.getName());
			assertValid(validTrial);
		}
	}

	@Test
	public void testAbbreviationNull() {
		validTrial.setAbbreviation(null);
		assertNull(validTrial.getAbbreviation());
		assertValid(validTrial);
	}

	@Test
	public void testAbbreviationEmpty() {
		validTrial.setAbbreviation("");
		assertEquals("", validTrial.getAbbreviation());
		assertValid(validTrial);
	}

	@Test
	public void testAbbreviationNotLongerThan255() {
		String[] invalidValue = { stringUtil.getWithLength(256),
				stringUtil.getWithLength(600) };
		for (String s : invalidValue) {
			validTrial.setAbbreviation(s);
			assertEquals(s, validTrial.getAbbreviation());
			assertInvalid(validTrial);
		}
	}
	
	@Test
	public void testAbbreviationCorrect() {
		String[] validValue = { stringUtil.getWithLength(255),
				stringUtil.getWithLength(1), stringUtil.getWithLength(136)};
		for (String s : validValue) {
			validTrial.setAbbreviation(s);
			assertEquals(s, validTrial.getAbbreviation());
			assertValid(validTrial);
		}
	}

	@Test
	public void testDescriptionNull() {
		validTrial.setDescription(null);
		assertNull(validTrial.getDescription());
		assertValid(validTrial);
	}

	@Test
	public void testDescriptionEmpty() {
		validTrial.setDescription("");
		assertEquals("", validTrial.getDescription());
		assertValid(validTrial);
	}

	@Test
	public void testDescriptionAnyLengt() {
		String[] validValues = { stringUtil.getWithLength(8941),
				stringUtil.getWithLength(2), stringUtil.getWithLength(10000000) };
		for (String s : validValues) {
			validTrial.setDescription(s);
			assertEquals(s, validTrial.getDescription());
			assertValid(validTrial);
		}
	}
	
	
	
	@Test
	public void testStartDateNotNull() {
		final GregorianCalendar endDate = new GregorianCalendar(1990,
				Calendar.NOVEMBER, 3);
		validTrial.setEndDate(endDate);
		assertEquals(endDate, validTrial.getEndDate());
		
		validTrial.setStartDate(null);
		assertNull(validTrial.getStartDate());
		assertInvalid(validTrial);
	}
	
	
	@Test
	public void testStartDateNotAfterEndDate() {
		final GregorianCalendar dateOK1 = new GregorianCalendar(1996,
				Calendar.FEBRUARY, 29);
		final GregorianCalendar dateOK2 = new GregorianCalendar(2006,
				Calendar.NOVEMBER, 3);
		final GregorianCalendar endDate = new GregorianCalendar(1990,
				Calendar.NOVEMBER, 3);
		validTrial.setEndDate(endDate);
		assertEquals(endDate, validTrial.getEndDate());
		
		validTrial.setStartDate(dateOK1);
		assertEquals(dateOK1, validTrial.getStartDate());
		assertInvalid(validTrial);

		validTrial.setStartDate(dateOK2);
		assertEquals(dateOK2, validTrial.getStartDate());
		assertInvalid(validTrial);
	}
	
	
	@Test
	public void testStartDateCorrect() {
		final GregorianCalendar dateOK1 = new GregorianCalendar(1996,
				Calendar.FEBRUARY, 29);
		final GregorianCalendar dateOK2 = new GregorianCalendar(2006,
				Calendar.NOVEMBER, 3);
		final GregorianCalendar endDate = new GregorianCalendar(2012,
				Calendar.NOVEMBER, 3);
		validTrial.setEndDate(endDate);
		assertEquals(endDate, validTrial.getEndDate());
		
		validTrial.setStartDate(dateOK1);
		assertEquals(dateOK1, validTrial.getStartDate());
		assertValid(validTrial);

		validTrial.setStartDate(dateOK2);
		assertEquals(dateOK2, validTrial.getStartDate());
		assertValid(validTrial);
	}

	
	@Test
	public void testEndDateNotNull() {
		final GregorianCalendar startDate = new GregorianCalendar(1990,
				Calendar.NOVEMBER, 3);
		validTrial.setStartDate(startDate);
		assertEquals(startDate, validTrial.getStartDate());
		
		validTrial.setEndDate(null);
		assertNull(validTrial.getEndDate());
		assertInvalid(validTrial);
	}
	
	
	@Test
	public void testEndDateCorrect() {
		final GregorianCalendar date = new GregorianCalendar(1996,
				Calendar.FEBRUARY, 29);
		final GregorianCalendar endDate = new GregorianCalendar(2012,
				Calendar.NOVEMBER, 3);
		validTrial.setEndDate(endDate);
		assertEquals(endDate, validTrial.getEndDate());
		
		validTrial.setStartDate(date);
		assertEquals(date, validTrial.getStartDate());
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

		this.setDateRange(startOK1, endOK1);
		assertValid(validTrial);
		
		this.setDateRange(startOK2, endOK2);
		assertValid(validTrial);
		
		this.setDateRange(startOKOpen, null);
		assertInvalid(validTrial);
		
		this.setDateRange(null, endOKOpen);
		assertInvalid(validTrial);
		
		this.setDateRange(null, null);
		assertInvalid(validTrial);
		
		this.setDateRange(startSame, endSame);
		 assertInvalid(validTrial);
		
		 this.setDateRange(startWD, endWD);
		 assertInvalid(validTrial);
		
		 this.setDateRange(startSD, endSD);
		 assertValid(validTrial);
	
		 this.setDateRange(startJust, endJust);
		 assertInvalid(validTrial);

	}

	private void setDateRange(GregorianCalendar start, GregorianCalendar end) {
		validTrial.setStartDate(start);
		validTrial.setEndDate(end);
		assertEquals(start, validTrial.getStartDate());
		assertEquals(end, validTrial.getEndDate());
	}

	@Test
	public void testStatusNotNull(){
		validTrial.setStatus(null);
		assertNull(validTrial.getStatus());
		assertInvalid(validTrial);
	}
	
	@Test
	public void testStatusCorrect() {
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
	public void testSponsorInvestigatorNotNull() {
		validTrial.setSponsorInvestigator(null);
		assertNull(validTrial.getSponsorInvestigator());
		assertInvalid(validTrial);
	}
	
	@Test
	public void testSponsorInvestigatorCorrect() {
		Person p = factory.getPerson();
		validTrial.setSponsorInvestigator(p);
		assertEquals(p, validTrial.getSponsorInvestigator());
		assertValid(validTrial);
	}

	@Test
	public void testLeadingSiteNotNull() {
		validTrial.setLeadingSite(null);
		assertNull(validTrial.getLeadingSite());
		assertInvalid(validTrial);
	}
	
	@Test
	public void testLeadingSiteCorrect() {
		TrialSite c = factory.getTrialSite();
		validTrial.setLeadingSite(c);
		assertEquals(c, validTrial.getLeadingSite());
		assertValid(validTrial);
	}

	@Test
	public void testParticipatingSitesNull() {
		validTrial.setParticipatingSites(null);
		assertNull(validTrial.getParticipatingSites());
		assertValid(validTrial);
	}
	
	@Test
	public void testParticipatingSitesCorrect() {
		Set<TrialSite> cl = validTrial.getParticipatingSites();

		TrialSite c1 = factory.getTrialSite();
		TrialSite c2 = factory.getTrialSite();
		TrialSite c3 = factory.getTrialSite();

		cl.add(c1);
		cl.add(c2);
		cl.add(c3);
		
		assertEquals(3, validTrial.getParticipatingSites().size());

		assertValid(validTrial);
	}
	
	@Test
	public void testAddParticipatingSitesNull(){
		assertTrue(validTrial.getParticipatingSites().isEmpty());
		validTrial.addParticipatingSite(null);
		validTrial.addParticipatingSite(null);
		assertEquals(0,validTrial.getParticipatingSites().size());
		assertValid(validTrial);
	}
	
	@Test
	public void testAddParticipatingSitesCorrect(){
		assertTrue(validTrial.getParticipatingSites().isEmpty());
		TrialSite[] validValues = {factory.getTrialSite(), factory.getTrialSite(), factory.getTrialSite(), factory.getTrialSite(), factory.getTrialSite()};
		for(int i =0;i<validValues.length;i++){
			validTrial.addParticipatingSite(validValues[i]);
			assertEquals(i+1, validTrial.getParticipatingSites().size());
			assertTrue(validTrial.getParticipatingSites().contains(validValues[i]));
			assertValid(validTrial);
		}
	}
	

	@Test
	// TODO Implementing Trial Protocol behavior
	public void testProtocol() {
		// fail("Not yet implemented");
	}

	
	@Test
	public void testTreatmentArmsNull() {
		validTrial.setTreatmentArms(null);
		assertNull(validTrial.getTreatmentArms());
		assertValid(validTrial);
	}	
	
	@Test
	public void testTreatmentArmsCorrect() {
		TreatmentArm arm1 = new TreatmentArm();
		arm1.setName("arm1");
		TreatmentArm arm2 = new TreatmentArm();
		arm2.setName("arm2");
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		arms.add(arm1);
		arms.add(arm2);
		validTrial.setTreatmentArms(arms);
		assertEquals(arms, validTrial.getTreatmentArms());
		assertEquals(2, validTrial.getTreatmentArms().size());
		assertTrue(validTrial.getTreatmentArms().containsAll(arms));
		assertValid(validTrial);
	}

	@Test
	public void testSubjectCriteriaCorrect() {
		DichotomousCriterion criterion1 = new DichotomousCriterion();
		criterion1.setName("criterion1");
		DichotomousCriterion criterion2 = new DichotomousCriterion();
		criterion2.setName("criterion2");
		List<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> criteria = new ArrayList<AbstractCriterion<? extends Serializable,? extends AbstractConstraint<? extends Serializable>>>();
		criteria.add(criterion1);
		criteria.add(criterion2);
		validTrial.setCriteria(criteria);
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
	public void testSubjectCriterionNull(){
		validTrial.setCriteria(null);
		assertNull(validTrial.getCriteria());
		assertValid(validTrial);
	}
	
	
	@Test
	public void testAddSubjectCriteria() {
		DichotomousCriterion criterion1 = new DichotomousCriterion();
		criterion1.setName("criterion1");
		DichotomousCriterion criterion2 = new DichotomousCriterion();
		criterion2.setName("criterion2");
		validTrial.addCriterion(criterion1);
		validTrial.addCriterion(criterion2);
		assertEquals(2, validTrial.getCriteria().size());
		assertTrue(DichotomousCriterion.class.isInstance(validTrial
				.getCriteria().get(0)));
		assertEquals("criterion1", validTrial.getCriteria().get(0).getName());
		assertTrue(DichotomousCriterion.class.isInstance(validTrial
				.getCriteria().get(1)));
		assertEquals("criterion2", validTrial.getCriteria().get(1).getName());
		assertValid(validTrial);
	}
	
	@Test
	public void testAddSubjectCriteriaNull() {
		assertTrue(validTrial.getCriteria().isEmpty());
		validTrial.addCriterion(null);
		assertTrue(validTrial.getCriteria().isEmpty());
		assertValid(validTrial);
	}
	
	
	
	@Test
	public void testRandomizationConfigurationNull() {
		validTrial.setRandomizationConfiguration(null);
		assertNull(validTrial.getRandomizationConfiguration());
		assertValid(validTrial);
	}
	
	@Test
	public void testRandomizationConfigurationCorrect() {
		CompleteRandomizationConfig conf = new CompleteRandomizationConfig();
		conf.setTrial(null);
		assertNull(conf.getTrial());
		validTrial.setRandomizationConfiguration(conf);
		assertEquals(conf, validTrial.getRandomizationConfiguration());
		assertEquals(validTrial, conf.getTrial());
		assertValid(validTrial);
	}

	@Test
	public void testGetRequieredFields() {
		Map<String, Boolean> map = validTrial.getRequiredFields();
		for (String key : map.keySet()) {
			if (key.equals("name")) {
				assertTrue(map.get(key));
			} else if (key.equals("abbreviation")) {
				assertFalse(map.get(key));
			} else if (key.equals("stratifyTrialSite")) {
				assertFalse(map.get(key));
			} else if (key.equals("description")) {
				assertFalse(map.get(key));
			} else if (key.equals("startDate")) {
				assertFalse(map.get(key));
			} else if (key.equals("endDate")) {
				assertFalse(map.get(key));
			} else if (key.equals("protocol")) {
				assertFalse(map.get(key));
			} else if (key.equals("sponsorInvestigator")) {
				assertTrue(map.get(key));
			} else if (key.equals("leadingSite")) {
				assertTrue(map.get(key));
			} else if (key.equals("status")) {
				assertTrue(map.get(key));
			} else if (key.equals("participatingSites")) {
				assertFalse(map.get(key));
			} else if (key.equals("treatmentArms")) {
				assertFalse(map.get(key));
			} else if (key.equals("subjectCriteria")) {
				assertFalse(map.get(key));
			} else if (key.equals("randomConf")) {
				assertFalse(map.get(key));
			} else if (key.equals("generateIds")) {
				assertFalse(map.get(key));
			} else if (key.equals("serialVersionUID")) {
				assertFalse(map.get(key));
			} else if (key.equals("SUBJECT_COUNT_COMPERATOR")) {
				assertFalse(map.get(key));
			}else if (key.equals("$VRc")) {
				assertFalse(map.get(key));
			}else
				fail(key + " not checked");
		}
	}

	@Test
	public void testEqualsHashCode() {
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
	public void testGetSubjects() {
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
		for (int i = 1; i <= 100; i++) {
			TrialSubject sub = new TrialSubject();
			if (i % 2 == 0) {
				arm1.addSubject(sub);
			} else {
				arm2.addSubject(sub);
			}
			assertTrue(validTrial.getSubjects().contains(sub));
		}
	}
	
	@Test
	public void testTotalSubjectAmount() {
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
		for (int i = 1; i <= 100; i++) {
			if (i % 2 == 0) {
				arm1.addSubject(new TrialSubject());
			} else {
				arm2.addSubject(new TrialSubject());
			}
			assertEquals(i, validTrial.getTotalSubjectAmount());
		}
	}

	@Test
	public void testPlannedSubjectAmount() {
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
	public void testUiName() {
		validTrial.setAbbreviation("abbreviation");
		assertEquals("abbreviation", validTrial.getUIName());
	}

	
	@Test
	public void testIsFresh() {
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
	public void testGenerateIds() {
		validTrial.setGenerateIds(true);
		assertTrue(validTrial.isGenerateIds());
		validTrial.setGenerateIds(false);
		assertFalse(validTrial.isGenerateIds());
	}

	@Test
	public void testStratifyTrialSite() {
		validTrial.setStratifyTrialSite(true);
		assertTrue(validTrial.isStratifyTrialSite());
		validTrial.setStratifyTrialSite(false);
		assertFalse(validTrial.isStratifyTrialSite());
	}

	@Test
	public void testStrataNamesAndIdsStrataCriterions() {
		DichotomousCriterion criterion1 = new DichotomousCriterion();
		criterion1.setId(1);
		criterion1.setName("criterion1");
		criterion1.setOption1("option1");
		criterion1.setOption2("option2");
		try {
			DichotomousConstraint d1 = new DichotomousConstraint(Arrays
					.asList(new String[] { "option1" }));
			d1.setId(1);
			criterion1.addStrata(d1);
			DichotomousConstraint d2 = new DichotomousConstraint(Arrays
					.asList(new String[] { "option2" }));
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
			DichotomousConstraint d1 = new DichotomousConstraint(Arrays
					.asList(new String[] { "option1" }));
			d1.setId(1);
			criterion2.addStrata(d1);
			DichotomousConstraint d2 = new DichotomousConstraint(Arrays
					.asList(new String[] { "option2" }));
			d2.setId(2);
			criterion2.addStrata(d2);
		} catch (ContraintViolatedException e) {
			fail();
		}
		validTrial.addCriterion(criterion1);
		validTrial.addCriterion(criterion2);

		Pair<List<String>, List<String>> pair = validTrial
				.getAllStrataIdsAndNames();

		assertEquals(4, pair.first().size());
		assertEquals(4, pair.last().size());

		Collections.sort(pair.first());
		assertEquals("1_1;2_1;", pair.first().get(0));
		assertEquals("1_1;2_2;", pair.first().get(1));
		assertEquals("1_2;2_1;", pair.first().get(2));
		assertEquals("1_2;2_2;", pair.first().get(3));

		Collections.sort(pair.last());
		assertEquals("criterion1_option1;criterion2_option1;", pair.last().get(
				0));
		assertEquals("criterion1_option1;criterion2_option2;", pair.last().get(
				1));
		assertEquals("criterion1_option2;criterion2_option1;", pair.last().get(
				2));
		assertEquals("criterion1_option2;criterion2_option2;", pair.last().get(
				3));
	}

	@Test
	public void testStrataNamesAndIdsStrataCriterionsTrialSite() {

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
			DichotomousConstraint d1 = new DichotomousConstraint(Arrays
					.asList(new String[] { "option1" }));
			d1.setId(1);
			criterion1.addStrata(d1);
			DichotomousConstraint d2 = new DichotomousConstraint(Arrays
					.asList(new String[] { "option2" }));
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
			DichotomousConstraint d1 = new DichotomousConstraint(Arrays
					.asList(new String[] { "option1" }));
			d1.setId(1);
			criterion2.addStrata(d1);
			DichotomousConstraint d2 = new DichotomousConstraint(Arrays
					.asList(new String[] { "option2" }));
			d2.setId(2);
			criterion2.addStrata(d2);
		} catch (ContraintViolatedException e) {
			fail();
		}
		validTrial.addCriterion(criterion1);
		validTrial.addCriterion(criterion2);

		Pair<List<String>, List<String>> pair = validTrial
				.getAllStrataIdsAndNames();

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
		assertEquals("site1 | criterion1_option1;criterion2_option1;", pair
				.last().get(0));
		assertEquals("site1 | criterion1_option1;criterion2_option2;", pair
				.last().get(1));
		assertEquals("site1 | criterion1_option2;criterion2_option1;", pair
				.last().get(2));
		assertEquals("site1 | criterion1_option2;criterion2_option2;", pair
				.last().get(3));
		assertEquals("site2 | criterion1_option1;criterion2_option1;", pair
				.last().get(4));
		assertEquals("site2 | criterion1_option1;criterion2_option2;", pair
				.last().get(5));
		assertEquals("site2 | criterion1_option2;criterion2_option1;", pair
				.last().get(6));
		assertEquals("site2 | criterion1_option2;criterion2_option2;", pair
				.last().get(7));
	}

	@Test
	public void testStrataNamesAndIdsStrataTrialSite() {

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

		Pair<List<String>, List<String>> pair = validTrial
				.getAllStrataIdsAndNames();

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
