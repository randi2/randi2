package de.randi2.model.criteria.constraints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.randi2.test.utility.AbstractDomainTest;
import de.randi2.unsorted.ContraintViolatedException;

import static junit.framework.Assert.*;

public class DateConstraintTest extends	AbstractDomainTest<DateConstraint> {

		private DateConstraint constraint;
		private GregorianCalendar element;
		
		public DateConstraintTest(){
			super(DateConstraint.class);
		}
		
		@Before
		public void setUp() throws Exception {
			element= new GregorianCalendar();
			constraint = new DateConstraint(Arrays.asList(new GregorianCalendar[]{element}));
		}
		
		@Test
		public void testConstructor(){
			List<GregorianCalendar> elements = new ArrayList<GregorianCalendar>();
			try {
				constraint = new DateConstraint(elements);
				fail("the list of constraints should be not empty");
			} catch (ContraintViolatedException e) {}
			
			elements.add(new GregorianCalendar(2000,10,10));
			try {
				constraint = new DateConstraint(elements);
				assertTrue(constraint.getExpectedValue().equals(new GregorianCalendar(2000,10,10)));
			} catch (ContraintViolatedException e) {
				fail("the list of constraints is ok");
			}
			elements.add(new GregorianCalendar(2003,14,12));
			try {
				constraint = new DateConstraint(elements);
				fail("the list of constraints has more than two objects");
			} catch (ContraintViolatedException e) {
			}
			try {
				constraint = new DateConstraint(null);
				fail("the list of constraints is null");
			} catch (ContraintViolatedException e) {
			}
			
		}
		
		
		
		@Test
		public void testIsValueCorrect(){
			try {
				constraint.isValueCorrect(element);
			} catch (ContraintViolatedException e) {
				fail("Value is correct");
			}
			try {
				constraint.isValueCorrect(new GregorianCalendar(2003,14,12));
				fail("Value is not correct");
			} catch (ContraintViolatedException e) {		}
		}
		
		@Test
		public void testExpectedValues(){
			assertTrue(constraint.getExpectedValue().equals(element));
			GregorianCalendar test = new GregorianCalendar(2003,14,12);
			constraint.setExpectedValue(test);
			assertTrue(constraint.getExpectedValue().equals(test));
		}
		
		
		@Test
		public void testCheckValue(){
			assertTrue(constraint.checkValue(element));
			assertFalse(constraint.checkValue(new GregorianCalendar(2003,14,12)));
			
		}
		
		@Test
		public void databaseIntegrationTest(){
			hibernateTemplate.persist(constraint);
			assertTrue(constraint.getId()>0);
			
			DateConstraint dbConstraint = (DateConstraint) hibernateTemplate.get(DateConstraint.class, constraint.getId());
			assertEquals(constraint.getId(), dbConstraint.getId());
			assertEquals(constraint.getExpectedValue(), dbConstraint.getExpectedValue());
		}

}
