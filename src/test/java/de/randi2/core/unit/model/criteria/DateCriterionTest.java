package de.randi2.core.unit.model.criteria;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.criteria.DateCriterion;
import de.randi2.model.criteria.constraints.DateConstraint;
import de.randi2.testUtility.utility.AbstractDomainTest;
import de.randi2.unsorted.ContraintViolatedException;

public class DateCriterionTest extends AbstractDomainTest<DateCriterion>{

	public DateCriterionTest(){
		super(DateCriterion.class);
	}
	private DateCriterion criterion;
	private GregorianCalendar firstDate;
	private GregorianCalendar secondDate;
	
	@Before
	public void setUp(){
		criterion = new DateCriterion();
		firstDate = new GregorianCalendar(1998,7,1);
		secondDate = new GregorianCalendar(2000,7,1);
	}
	
	@Test
	public void testSimpleCase(){
		assertTrue(criterion.checkValue(firstDate));
		assertTrue(criterion.checkValue(new GregorianCalendar(2001,10,10)));
		assertTrue(criterion.checkValue(new GregorianCalendar(2000,10,22)));
		assertTrue(criterion.checkValue(new GregorianCalendar(2000,12,10)));
		assertTrue(criterion.checkValue(new GregorianCalendar(2003,9,10)));
		assertFalse(criterion.checkValue(null));
		
		assertFalse(criterion.isInclusionCriterion());
	}
	
	@Test
	public void testWithInclusionCriterion() {
		criterion.setDescription("test");
		GregorianCalendar date =new GregorianCalendar(2000,12,10) ;
		try {
			criterion.setInclusionConstraint(new DateConstraint(Arrays.asList(new GregorianCalendar[]{date})));
		} catch (ContraintViolatedException e) {
			fail(e.getMessage());
		}
		GregorianCalendar date1 =new GregorianCalendar(2001,12,10) ;
		assertTrue(criterion.checkValue(date1));
		
		assertTrue(criterion.isInclusionCriterion());

	}
	
	@Test
	public void testConfiguredValue(){
		assertNotNull(criterion.getConfiguredValues());
		assertTrue(criterion.getConfiguredValues().isEmpty());
	}
	
	@Test
	public void testWithStratification() throws ContraintViolatedException {
		criterion.setDescription("test");
		
		assertNull(criterion.stratify(secondDate));
		List<DateConstraint> temp = new ArrayList<DateConstraint>();
		temp.add(new DateConstraint(Arrays.asList(new GregorianCalendar[]{firstDate})));
	

		criterion.setStrata(temp);
		
		assertTrue(criterion.getStrata().containsAll(temp));
		assertTrue(temp.containsAll(criterion.getStrata()));

		assertEquals(temp.get(0), criterion.stratify(firstDate));

		try {
			criterion.stratify(new GregorianCalendar(1993,7,1));
			fail("AGAIN -> WRONG!");
		} catch (ContraintViolatedException e) {
		}

	}
	
}