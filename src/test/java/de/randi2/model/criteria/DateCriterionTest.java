package de.randi2.model.criteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.criteria.constraints.DateConstraint;
import de.randi2.model.criteria.constraints.FreeTextConstraint;
import de.randi2.test.utility.AbstractDomainTest;
import de.randi2.unsorted.ContraintViolatedException;
import de.randi2.utility.Randi2Error;

import static junit.framework.Assert.*;

public class DateCriterionTest extends AbstractDomainTest<DateRangeCriterion>{

	
	

	
	public DateCriterionTest(){
		super(DateRangeCriterion.class);
	}
	private DateRangeCriterion criterion;
	private GregorianCalendar firstDate;
	private GregorianCalendar secondDate;
	
	@Before
	public void setUp() throws Exception {
		criterion = new DateRangeCriterion();
		firstDate= new GregorianCalendar(2000,10,10);
		criterion.setFirstDate(firstDate);
		secondDate =new GregorianCalendar(2003,10,10);
		criterion.setSecondDate(secondDate);
	}
	
	@Test
	public void testSimpleCase(){
		assertTrue(criterion.checkValue(firstDate));
		assertTrue(criterion.checkValue(new GregorianCalendar(2001,10,10)));
		assertTrue(criterion.checkValue(new GregorianCalendar(2000,10,22)));
		assertTrue(criterion.checkValue(new GregorianCalendar(2000,12,10)));
		assertTrue(criterion.checkValue(new GregorianCalendar(2003,9,10)));
		assertFalse(criterion.checkValue(new GregorianCalendar(2000,9,10)));
		assertFalse(criterion.checkValue(new GregorianCalendar(2004,9,10)));
		assertFalse(criterion.checkValue(new GregorianCalendar(2003,11,10)));
		
		assertFalse(criterion.isInclusionCriterion());
	}
	
	@Test
	public void testWithInclusionCriterion() {
		criterion.setDescription("test");
		GregorianCalendar date =new GregorianCalendar(2000,12,10) ;
		try {
			criterion.setInclusionCriterion(new DateConstraint(Arrays.asList(new GregorianCalendar[]{date})));
		} catch (ContraintViolatedException e) {
			fail(e.getMessage());
		}

		assertTrue(criterion.checkValue(date));
		
		assertTrue(criterion.isInclusionCriterion());

		try {
			criterion.setInclusionCriterion(new DateConstraint(Arrays.asList(new GregorianCalendar[]{new GregorianCalendar(), new GregorianCalendar()})));
		} catch (ContraintViolatedException e) {
			assertNotNull(e);
		}

	}
	
	@Test
	public void testConfiguredValue(){
		assertNotNull(criterion.getConfiguredValues());
		assertEquals(2,criterion.getConfiguredValues().size());
		assertTrue(criterion.getConfiguredValues().contains(firstDate));
		assertTrue(criterion.getConfiguredValues().contains(secondDate));
		assertEquals(firstDate, criterion.getFirstDate());
		assertEquals(secondDate, criterion.getSecondDate());
		
		criterion.setFirstDate(null);
		assertNull(criterion.getFirstDate());
		criterion.setSecondDate(null);
		assertNull(criterion.getSecondDate());
		
		assertNull(criterion.getConfiguredValues());
	}
	
	@Test
	public void testWithStratification() throws ContraintViolatedException {
		criterion.setDescription("test");
		
		assertNull(criterion.stratify(secondDate));
		List<DateConstraint> temp = new ArrayList<DateConstraint>();
		temp.add(new DateConstraint(Arrays.asList(new GregorianCalendar[]{firstDate})));
		temp.add(new DateConstraint(Arrays.asList(new GregorianCalendar[]{secondDate})));

		criterion.setStrata(temp);
		
		assertTrue(criterion.getStrata().containsAll(temp));
		assertTrue(temp.containsAll(criterion.getStrata()));

		assertEquals(temp.get(0), criterion.stratify(firstDate));
		assertEquals(temp.get(1), criterion.stratify(secondDate));

		try {
			criterion.stratify(new GregorianCalendar());
			fail("AGAIN -> WRONG!");
		} catch (ContraintViolatedException e) {
		}

	}
	
	@Test
	public void databaseIntegrationTest() {
		criterion.setName("name");
		criterion.setDescription("test");
		List<GregorianCalendar> elements = new ArrayList<GregorianCalendar>();
		elements.add(new GregorianCalendar());
		elements.add(new GregorianCalendar());
		try {
			ArrayList<DateConstraint> temp = new ArrayList<DateConstraint>();
			temp.add(new DateConstraint(Arrays.asList(new GregorianCalendar[]{elements.get(0)})));
			temp.add(new DateConstraint(Arrays.asList(new GregorianCalendar[]{elements.get(1)})));
		
			DateConstraint constraint = new DateConstraint(Arrays.asList(elements.get(0)));
			hibernateTemplate.save(constraint);
			assertTrue(constraint.getId()>0);
			criterion.setInclusionCriterion(constraint);


			hibernateTemplate.save(criterion);
			assertTrue(criterion.getId()>0);
			assertEquals(criterion.getInclusionCriterion().getId(), constraint.getId());
			hibernateTemplate.save(temp.get(0));
			hibernateTemplate.save(temp.get(1));
			assertTrue(temp.get(0).getId() > 0);
			assertTrue(temp.get(1).getId() > 0);
			criterion.setStrata(temp);
			hibernateTemplate.update(criterion);
			DateRangeCriterion dbCriterion = (DateRangeCriterion) hibernateTemplate.get(DateRangeCriterion.class,criterion.getId());
			assertEquals(criterion, dbCriterion);
			assertEquals(criterion.getName(), dbCriterion.getName());
			assertEquals(criterion.getDescription(), dbCriterion.getDescription());
			assertEquals(constraint.getId(), dbCriterion.getInclusionCriterion().getId());
			assertEquals(DateConstraint.class, dbCriterion.getContstraintType());

		} catch (ContraintViolatedException e) {
			//fail();
		}
	}
	
}