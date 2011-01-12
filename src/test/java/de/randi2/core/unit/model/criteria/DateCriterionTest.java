package de.randi2.core.unit.model.criteria;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.criteria.DateCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
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
	
	
	@Test
	public void testDescriptionNull(){
		criterion.setDescription(null);
		assertEquals(null, criterion.getDescription());
		assertValid(criterion);
	}
	
	@Test
	public void testDescriptionEmpty(){
		criterion.setDescription("");
		assertEquals("", criterion.getDescription());
		assertValid(criterion);
	}
	
	
	@Test
	public void testDescriptionOther(){
		String[] validValues= {"A","Abcsdafasd", "Title" , stringUtil.getWithLength(265) };
		for(String s :validValues){
			criterion.setDescription(s);
			assertEquals(s, criterion.getDescription());
			assertValid(criterion);
		}
	}
	
	
	@Test
	public void testNameNull(){
		criterion.setName(null);
		assertEquals(null, criterion.getName());
		assertValid(criterion);
	}
	
	@Test
	public void testNameEmpty(){
		criterion.setName("");
		assertEquals("", criterion.getName());
		assertValid(criterion);
	}
	
	
	@Test
	public void testNameOther(){
		String[] validValues= {"A","Abcsdafasd", "Title" , stringUtil.getWithLength(265) };
		for(String s :validValues){
			criterion.setName(s);
			assertEquals(s, criterion.getName());
			assertValid(criterion);
		}
	}

	@Test
	public void testAddStrata(){
		AbstractConstraint<GregorianCalendar> constraintA = null;
		AbstractConstraint<GregorianCalendar> constraintB = null;
		try {
			constraintA = new DateConstraint(Arrays.asList(new GregorianCalendar[]{firstDate}));
			constraintB = new DateConstraint(Arrays.asList(new GregorianCalendar[]{secondDate}));
		} catch (ContraintViolatedException e) {
			fail();
		}
		criterion.addStrata(constraintA);
		assertEquals(1,criterion.getStrata().size());
		assertTrue(criterion.getStrata().contains(constraintA));
		criterion.addStrata(constraintB);
		assertEquals(2,criterion.getStrata().size());
		assertTrue(criterion.getStrata().contains(constraintA));
		assertTrue(criterion.getStrata().contains(constraintB));
		
	}
	
	
	@Test
	public void testAddStrataNull(){
		ArrayList<DateConstraint> list = new ArrayList<DateConstraint>();
		criterion.setStrata(list);
		assertEquals(0,criterion.getStrata().size());
		criterion.addStrata(null);
		assertEquals(0,criterion.getStrata().size());
	}
	
	
	@Test
	public void testSetAndGetStrata(){
		ArrayList<DateConstraint> list = new ArrayList<DateConstraint>();
		assertNotSame(list, criterion.getStrata());
		criterion.setStrata(list);
		assertEquals(list, criterion.getStrata());
	}
	
	@Test
	public void testCheckValueCorrect(){
		assertTrue(criterion.checkValue(firstDate));
		assertTrue(criterion.checkValue(secondDate));
	}
	
	@Test
	public void testCheckValueIncorrect() throws ContraintViolatedException{
		criterion.setInclusionConstraint(new DateConstraint(Arrays.asList(new GregorianCalendar[]{secondDate})));
		assertFalse(criterion.checkValue(firstDate));
	}
	
	
	@Test
	public void testGetConstraintType(){
		assertEquals(DateConstraint.class, criterion.getContstraintType());
	}
	
	@Test
	public void testGetConfiguredValues(){
		assertEquals(0, criterion.getConfiguredValues().size());
	}
	
	
	@Test
	public void testInclusionConstraintNull(){
		try {
			DateConstraint constraint = new DateConstraint(Arrays.asList(new GregorianCalendar[]{firstDate}));
			criterion.setInclusionConstraint(constraint);
			assertNotNull(criterion.getInclusionConstraint());
			criterion.setInclusionConstraint(null);
			assertNull(criterion.getInclusionConstraint());
		} catch (ContraintViolatedException e) {
			fail();
		}
	}
	
	@Test
	public void testInclusionConstraintOther(){
		try {
			DateConstraint constraint = new DateConstraint(Arrays.asList(new GregorianCalendar[]{firstDate}));
			criterion.setInclusionConstraint(constraint);
			assertEquals(constraint, criterion.getInclusionConstraint());
		} catch (ContraintViolatedException e) {
			fail();
		}
	}
	
	@Test
	public void testIsInclusionConstraintTrue(){
		try {
			DateConstraint constraint = new DateConstraint(Arrays.asList(new GregorianCalendar[]{firstDate}));
			criterion.setInclusionConstraint(constraint);
			assertTrue(criterion.isInclusionCriterion());
		} catch (ContraintViolatedException e) {
			fail();
		}
	}
	
	@Test
	public void testIsInclusionConstraintFalse(){
			try {
				criterion.setInclusionConstraint(null);
			} catch (ContraintViolatedException e) {
				fail();
			}
			assertFalse(criterion.isInclusionCriterion());
	}
	
	
	@Test
	public void testStratifyConstraintException(){
		AbstractConstraint<GregorianCalendar> constraintA = null;
		AbstractConstraint<GregorianCalendar> constraintB = null;
		try {
			constraintA = new DateConstraint(Arrays.asList(new GregorianCalendar[]{null, firstDate}));
			constraintB = new DateConstraint(Arrays.asList(new GregorianCalendar[]{null, secondDate}));
		} catch (ContraintViolatedException e) {
			fail();
		}
		criterion.addStrata(constraintA);
		criterion.addStrata(constraintB);
		try {
			 criterion.stratify(new GregorianCalendar());
			 fail();
		} catch (ContraintViolatedException e) {
		}
	}
	
	
	@Test
	public void testStratify(){
		AbstractConstraint<GregorianCalendar> constraintA = null;
		AbstractConstraint<GregorianCalendar> constraintB = null;
		try {
			constraintA = new DateConstraint(Arrays.asList(new GregorianCalendar[]{null, firstDate}));
			constraintB = new DateConstraint(Arrays.asList(new GregorianCalendar[]{null, secondDate}));
		} catch (ContraintViolatedException e) {
			fail();
		}
		criterion.addStrata(constraintA);
		criterion.addStrata(constraintB);
		try {
			assertEquals(constraintA, criterion.stratify(firstDate));
			assertEquals(constraintB, criterion.stratify(secondDate));
		} catch (ContraintViolatedException e) {
			fail(e.getMessage());
		}
	}
}